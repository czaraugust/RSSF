package projects.Flooding.Nodes;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import projects.Flooding.Messages.SampleMessage;
import projects.Flooding.Timers.FloodingTimer;

import java.util.LinkedList;

/**
 * @author danniel & Matheus
 */
public class SinkNode extends Node {
    public int energy;
    public LinkedList<Long> messagesIDs;
    private int count = 0;

    @Override
    public void handleMessages(Inbox inbox) {
        while (inbox.hasMoreMessages()) {
            Message message = inbox.getNextMessage();
           // System.out.println("Sink: menssage recived " + count);
            if (message instanceof SampleMessage) {
                SampleMessage sampleMessage = (SampleMessage) message;
               // System.out.println("Sink: " + sampleMessage.getMsg());
                count++;
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
}
