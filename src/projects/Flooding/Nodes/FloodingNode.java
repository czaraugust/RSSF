package projects.Flooding.Nodes;

import java.util.LinkedList;

import jsensor.runtime.Jsensor;
import jsensor.nodes.Node;
import jsensor.nodes.messages.Inbox;
import jsensor.nodes.messages.Message;
import projects.Flooding.Messages.FloodingMessage;
import projects.Flooding.Timers.FloodingTimer;


/**
 *
 * @author danniel & Matheus
 */
public class FloodingNode extends Node{
    public LinkedList<Long> messagesIDs; 

    @Override
    public void handleMessages(Inbox inbox) {    	
       while(inbox.hasMoreMessages())
       {
           Message message = inbox.getNextMessage();
          
           if(message instanceof FloodingMessage)
           {
        	   FloodingMessage floodingMessage = (FloodingMessage) message;
        	   
               if(this.messagesIDs.contains(floodingMessage.getID()))
               {
                   continue;
               }
               
               this.messagesIDs.add(floodingMessage.getID());
               if(floodingMessage.getDestination().getID() == this.getID())
               {
            	   Jsensor.log("time: "+ Jsensor.currentTime +
            			   "\t sensorID: " +this.ID+
            			   "\t receivedFrom: " +floodingMessage.getSender().getID()+
            			   "\t hops: "+ floodingMessage.getHops() +
            			   "\t path: " +floodingMessage.getMsg().concat(this.ID+""));
               }
               else
               {
            	   int n = 99999;
                   int cont = 0;
                   int i = 1;
                   while (i <= n) {
                       if (n % i == 0) {
                           ++cont;
                       }
                       ++i;
                   }
                   if (cont <= 0) continue;
            	   
            	   //floodingMessage.setMsg(floodingMessage.getMsg().concat(this.ID+ " - "));
                   this.multicast(floodingMessage);
               }
           }        
       }
    }

    @Override
    public void onCreation() 
    {
    	//initializes the list of messages received by the node.
        this.messagesIDs = new LinkedList<Long>();
 
    	int time = 1;
    	FloodingTimer ft = new FloodingTimer();
        ft.startRelative(time, this);
        
    }
}
