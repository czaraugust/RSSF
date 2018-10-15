package projects.Flooding.Messages;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Message;

/**
 *
 * @author Matheus
 */
public class SampleMessage extends Message{

    private String msg;
    private Node sender;
    private Node destination;
    private int hops;
    private double senderEnergy;
    short chunk;

    public SampleMessage(Node sender, Node destination, int hops, String message, double senderEnergy, byte chunk) {
        //Call to create a new ID
        super(chunk);
        this.msg = message;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
        this.senderEnergy = senderEnergy;
        this.chunk = chunk;
    }

    private SampleMessage(String msg, Node sender, Node destination, int hops, double senderEnergy, long ID) {
        //Call to set the ID
        this.setID(ID);
        this.msg = msg;
        this.sender = sender;
        this.destination = destination;
        this.hops = hops;
        this.senderEnergy = senderEnergy;
    }

    public String getMsg(){
        return this.msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public int getHops() {
        return hops;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }

    public short getChunk() {
        return chunk;
    }

    public Node getSender() {
        return sender;
    }

    public void setSender(Node sender) {
        this.sender = sender;
    }
    
    public double getSenderEnergy() {
        return senderEnergy;
    }

    public void setSenderEnergy(double senderEnergy) {
        this.senderEnergy = senderEnergy;
    }

    @Override
    public Message clone() {
        return new SampleMessage(msg, sender, destination, hops+1, senderEnergy, this.getID());
    }
}