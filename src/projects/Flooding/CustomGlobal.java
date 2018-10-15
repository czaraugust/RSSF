package projects.Flooding;

import jsensor.runtime.AbsCustomGlobal;
import jsensor.runtime.Jsensor;
import projects.Flooding.Nodes.SensorNode;
import projects.Flooding.Nodes.SinkNode;

public class CustomGlobal extends AbsCustomGlobal {

    static byte[] key;
    static int rounds = 0;
    static int count = 0;
    int[] heads = {0,0,0,0};
    

    @Override
    public boolean hasTerminated() {
        return false;
    }

    @Override
    public void preRun() {
    	Kmeans km = new Kmeans(4, Jsensor.getNumNodes());
    	int[] nodesCluster = km.run();
    	
    	SinkNode sink = (SinkNode) Jsensor.getNodeByID(51);
    	for(int i = 0; i < nodesCluster.length; i++) {
    		jsensor.nodes.Node node = Jsensor.getNodeByID(i+1);
    		if (node instanceof SensorNode) {
    			if(heads[nodesCluster[i]-1] == 0) {
    				heads[nodesCluster[i]-1] = i+1;
    			}
    			sink.sendControlTo(i+1, nodesCluster[i], heads[nodesCluster[i]-1]);
    		}
    	}
    }

    @Override
    public void preRound() {
    	rounds++;
        if (rounds >= 500) {
        	Jsensor.runtime.setAbort(true);
        }
        System.out.println("r:"+ rounds);
        for(int i = 1; i <= Jsensor.getNumNodes(); i++) {
        	jsensor.nodes.Node node = Jsensor.getNodeByID(i);
        	if (node instanceof SensorNode) {
        		SensorNode sn = (SensorNode) node;
        		boolean result = sn.send();
        		if(result) {
        			count++;
        		}
        	}
        }
        
        for(int i = 0; i < 4; i++) {
        	SensorNode sn = (SensorNode) Jsensor.getNodeByID(heads[i]);
        	heads[i] = sn.sendNewHead();
        	System.out.println("Cluster: " + i + " Head: " + heads[i]);
        }
    }

    @Override
    public void postRound() {
    	
    }

    @Override
    public void postRun() {
        System.out.println(count);
    }
}
