package projects.Flooding.ConnectivityModels;

import jsensor.nodes.Node;
import jsensor.nodes.models.ConnectivityModel;
import projects.Flooding.Nodes.SensorNode;
import projects.Flooding.Nodes.SinkNode;


/**
 *
 * @author danniel & Matheus
 */
public class FloodingNodeConectivity extends ConnectivityModel
{
    @Override
    public boolean isConnected(Node from, Node to) {
//        return true;
        if(to instanceof SinkNode && from instanceof SensorNode){
            return true;
        }
    	return false;
    }
    
    
    @Override
    public boolean isNear(Node s1, Node s2) {
    	return super.isNear(s1, s2);
    }
    
}
