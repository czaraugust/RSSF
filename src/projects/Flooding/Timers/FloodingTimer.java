package projects.Flooding.Timers;

import jsensor.runtime.Jsensor;
import jsensor.nodes.Node;
import jsensor.nodes.events.TimerEvent;
import projects.Flooding.Messages.FloodingMessage;


/**
 *
 * @author Danniel & Matheus
 */
public class FloodingTimer extends TimerEvent{

    @Override
    public void fire() {
    	int nodeID = this.node.getID() + (Jsensor.numNodes/2)+1;
    	if(nodeID > Jsensor.numNodes)
    		nodeID = nodeID - Jsensor.numNodes;
    		
    	
    	Node destination = this.node.getRuntime().getSensorByID(nodeID);
        
        FloodingMessage message = new FloodingMessage(this.node, destination, 0, ""+this.node.getID(), this.node.getChunk());
        
        String messagetext = ""+Integer.toString(this.node.getID()) + " - ";
        
        message.setMsg(messagetext);
    	Jsensor.log("time: "+ Jsensor.currentTime +"\t sensorID: "+this.node.getID()+ "\t sendTo: " +destination.getID());
		
    	//GenerateFilesOmnet.addStartNode(this.node.getID() -1, destination.getID() -1, Jsensor.currentTime);
	    this.node.multicast(message);
    }    
}
