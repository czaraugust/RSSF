package projects.Flooding.Nodes;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import jsensor.runtime.Jsensor;
import projects.Flooding.Messages.FloodingMessage;
import projects.Flooding.Messages.SampleMessage;
import projects.Flooding.Timers.FloodingTimer;

import java.util.LinkedList;
import java.util.Random;


/**
 * @author danniel & Matheus
 */
public class SensorNode extends Node {
    public double energy = 0.5;
    public LinkedList<Long> messagesIDs;
    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasMoreMessages()) {
            Message message = inbox.getNextMessage();

            if (message instanceof FloodingMessage) {
                FloodingMessage floodingMessage = (FloodingMessage) message;

            }
        }
    }

    @Override
    public void onCreation() {
//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();

        int time = 1;
        FloodingTimer ft = new FloodingTimer();
        ft.startRelative(time, this);
    }

    public boolean send() {

        Node node = Jsensor.getNodeByID(1);
        Node destination = node.getRandomNode("SinkNode");

        SampleMessage message = new SampleMessage(node, destination, 0, "" + node.getID(), node.getChunk());

        String messagetext = "" + Integer.toString(node.getID()) + " - ";

        message.setMsg(messagetext);
        Jsensor.log("time: " + Jsensor.currentTime + "\t sensorID: " + node.getID() + "\t sendTo: " + destination.getID());

        //GenerateFilesOmnet.addStartNode(this.node.getID() -1, destination.getID() -1, Jsensor.currentTime);

        Random r = new Random();
        double p = r.nextDouble();

        this.energy -= cost(message);
        if (p > 0.5) {
            node.unicast(message, destination);
            return true;
        } return false;
    }

    public double cost(SampleMessage msg) {
        int bits = msg.getMsg().length() * 8;
        return bits * 5 * 10e-9;
    }
}
