package projects.Flooding.Messages;

import jsensor.nodes.messages.Message;

public class ControlMessage extends Message{
	
	private int cluster;
	private int clusterHead;
	short chunk;
	
	public ControlMessage(int cluster, int clusterHead, byte chunk) {
		super(chunk);
		this.cluster = cluster;
		this.clusterHead = clusterHead;
		this.chunk = chunk;
	}
	
	public ControlMessage(int cluster, int clusterHead, long ID) {
		this.setID(ID);
		this.cluster = cluster;
		this.clusterHead = clusterHead;
	}
	
	public int getCluster() {
		return cluster;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	public int getClusterHead() {
		return clusterHead;
	}

	public void setClusterHead(int clusterHead) {
		this.clusterHead = clusterHead;
	}
	
	@Override
    public Message clone() {
        return new ControlMessage(cluster, clusterHead, this.getID());
    }
}
