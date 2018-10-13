package projects.Flooding.Nodes;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import jsensor.runtime.Jsensor;
import projects.Flooding.Messages.ControlMessage;
import projects.Flooding.Messages.SampleMessage;

import java.util.LinkedList;
import java.util.Random;


/**
 * @author danniel & Matheus
 */
public class SensorNode extends Node {
    public double energy = 0.5;
    public LinkedList<Long> messagesIDs;
    
    private int cluster;
    private int clusterHead;
    
    @Override
    public void onCreation() {
    	//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();
        
        cluster = 0;
        clusterHead = 0;
    }
    
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasMoreMessages()) {
            Message message = inbox.getNextMessage();
            //System.out.println("Header: menssage recived");
            if (message instanceof SampleMessage) {
                SampleMessage sampleMessage = (SampleMessage) message;
                
            	Node destination = this.getRandomNode("SinkNode");

                SampleMessage newMessage = new SampleMessage(this, destination, 1, "" + this.getID(), this.getChunk());
                
                newMessage.setMsg("Header: " + this.getID() + ", " + sampleMessage.getMsg());
                Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destination.getID());

                this.energy -= cost(newMessage, destination.getID());
                this.unicast(newMessage, destination);
            }
            if(message instanceof ControlMessage) {
            	ControlMessage controlMessage = (ControlMessage) message;
            	if(this.cluster == 0 | this.cluster == controlMessage.getCluster()) {
            		this.cluster = controlMessage.getCluster();
            		this.clusterHead = controlMessage.getClusterHead();
            		
            		//System.out.println("ID: " + this.ID + " Cluster: " + cluster);
            	}
            }
        }
    }

    public boolean send() {
    	Random r = new Random();
    	Node destination;
    	if(this.clusterHead == this.getID() | this.cluster == 0) {
    		destination = this.getRandomNode("SinkNode");
    	}
    	else {
    		destination = Jsensor.getNodeByID(clusterHead);
    	}

        SampleMessage message = new SampleMessage(this, destination, 0, "" + this.getID(), this.getChunk());
        
        int temp = r.nextInt((30 - 25) + 1) + 25;
        String messagetext = "Cluster: " + cluster + ", Send from: " + Integer.toString(this.getID()) + ", Temp: " + temp;

        message.setMsg(messagetext);
        Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destination.getID());

        double p = r.nextDouble();

        this.energy -= cost(message, destination.getID());
        if (p > 0.2) {
            this.unicast(message, destination);
            return true;
        } return false;
    }
    
    public void sendControlTo(int destinationID, int cluster) {
    	this.cluster = cluster;
    	this.clusterHead = this.getID();
    	
    	ControlMessage controlMessage = new ControlMessage(cluster, this.clusterHead, this.getChunk());
    	Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destinationID);
    	
    	Node destination = Jsensor.getNodeByID(destinationID);
    	
    	this.energy -= 128 * 5 * 10e-9;
    	
    	this.unicast(controlMessage, destination);
    }
    
    public void sendControlToCluster() {
    	this.clusterHead = this.getID();
    	
    	ControlMessage controlMessage = new ControlMessage(this.cluster, this.getID(), this.getChunk());
    	Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: cluster " + this.cluster);
    	
    	this.energy -= 128 * 5 * 10e-9;
    	
    	this.multicast(controlMessage);
    }

    public double cost(SampleMessage msg, int destinationId) {
        int bits = msg.getMsg().length() * 8;
        double distance = distanceTo(destinationId);
        return bits * 5 * 10e-9 + distance * 10e-12;
    }
    
    public double distanceTo(int destinationId) {
    	jsensor.nodes.Node node = Jsensor.getNodeByID(destinationId);
        double dX = this.position.getPosX() - node.getPosition().getPosX();
        int dY = this.position.getPosY() - node.getPosition().getPosY();
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
        return distance;
    }
    
    public void setCluster(int cluster) {
    	this.cluster = cluster;
    }
}
