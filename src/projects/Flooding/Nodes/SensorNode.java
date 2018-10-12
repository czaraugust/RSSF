package projects.Flooding.Nodes;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import jsensor.runtime.Jsensor;
import projects.Flooding.Messages.SampleMessage;

import java.util.LinkedList;
import java.util.Random;


/**
 * @author danniel & Matheus
 */
public class SensorNode extends Node {
    public double energy = 0.5;
    public LinkedList<Long> messagesIDs;
    
    @Override
    public void onCreation() {
    	//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();
    }
    
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasMoreMessages()) {
            Message message = inbox.getNextMessage();
            //System.out.println("Lead: menssage recived " + count);
            if (message instanceof SampleMessage) {
                SampleMessage sampleMessage = (SampleMessage) message;
                
            	Node destination = this.getRandomNode("SinkNode");

                SampleMessage newMessage = new SampleMessage(this, destination, 0, "" + this.getID(), this.getChunk());
                
                newMessage.setMsg("Leade: " + this.getID() + ", " + sampleMessage.getMsg());
                Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destination.getID());

                this.energy -= cost(newMessage, destination.getID());
                this.unicast(newMessage, destination);
            }
        }
    }

    public boolean sendTo(int destinationId) {
    	Random r = new Random();
    	
        Node destination = Jsensor.getNodeByID(destinationId);

        SampleMessage message = new SampleMessage(this, destination, 0, "" + this.getID(), this.getChunk());
        
        int temp = r.nextInt((30 - 25) + 1) + 25;
        String messagetext = "Send from: " + Integer.toString(this.getID()) + ", Temp: " + temp;

        message.setMsg(messagetext);
        Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destination.getID());

        double p = r.nextDouble();

        this.energy -= cost(message, destinationId);
        if (p > 0.2) {
            this.unicast(message, destination);
            return true;
        } return false;
    }

    public double cost(SampleMessage msg, int destinationId) {
        int bits = msg.getMsg().length() * 8;
        jsensor.nodes.Node node = Jsensor.getNodeByID(destinationId);
        double dX = this.position.getPosX() - node.getPosition().getPosX();
        int dY = this.position.getPosY() - node.getPosition().getPosY();
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
        return bits * 5 * 10e-9 * distance;
    }
}
