package projects.Flooding.Nodes;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import jsensor.runtime.Jsensor;
import projects.Flooding.Messages.ControlMessage;
import projects.Flooding.Messages.SampleMessage;
import projects.Flooding.Timers.FloodingTimer;

import java.util.LinkedList;

public class SinkNode extends Node {
    public int energy;
    public LinkedList<Long> messagesIDs;
    
    @Override
    public void onCreation() {
    	//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();

        int time = 1;
        FloodingTimer ft = new FloodingTimer();
        ft.startRelative(time, this);
    }

    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasMoreMessages()) {
            Message message = inbox.getNextMessage();
            //System.out.println("Sink: menssage recived");
            if (message instanceof SampleMessage) {
                SampleMessage sampleMessage = (SampleMessage) message;
                System.out.println("Sink: " + sampleMessage.getMsg());
            }
        }
    }
    
    public void sendControlTo(int destinationID, int cluster, int head) {

    	ControlMessage controlMessage = new ControlMessage(cluster, head, this.getChunk());
    	Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + this.getID() + "\t sendTo: " + destinationID);
    	
    	Node destination = Jsensor.getNodeByID(destinationID);
    	
    	this.unicast(controlMessage, destination);
    }
}
