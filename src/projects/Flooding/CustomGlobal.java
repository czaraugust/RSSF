package projects.Flooding;

import jsensor.runtime.AbsCustomGlobal;
import jsensor.runtime.Jsensor;
import projects.Flooding.Nodes.SensorNode;

/**
 * @author danniel & Matheus
 */
public class CustomGlobal extends AbsCustomGlobal {

    static byte[] key;
    static int rounds = 0;
    static int count = 0;

    @Override
    public boolean hasTerminated() {
        return false;
    }

    @Override
    public void preRun() {
    	SensorNode sn1 = (SensorNode) Jsensor.getNodeByID(1);
    	for(int i = 2; i <= 50; i++) {
    		sn1.sendControlTo(i, 1);
    	}
    	
    	for(int i = 1; i <= Jsensor.getNumNodes(); i++) {
    		jsensor.nodes.Node node = Jsensor.getNodeByID(i);
        	if (node instanceof SensorNode) {
        		System.out.print("Node: " + i + " Neighbors: ");
        		for(int j = 1; j <= Jsensor.getNumNodes(); j++) {
        			jsensor.nodes.Node neighbor = Jsensor.getNodeByID(i);
                	if (neighbor instanceof SensorNode) {
                		if(((SensorNode) node).distanceTo(j) <= 25) {
                			System.out.print(j + ", ");
                		}
                	}
        		}
        		System.out.println("");
        	}
    	}
    }

    @Override
    public void preRound() {
    	rounds++;
        if (rounds >= 500)
            Jsensor.runtime.setAbort(true);
        //System.out.println("r:"+ rounds);
        double maxEnergy = 0;
        int maxEnergyId = 0;
        
        for (int i = 1; i <= Jsensor.getNumNodes(); i++) {
        	jsensor.nodes.Node node = Jsensor.getNodeByID(i);
        	if (node instanceof SensorNode) {
        		
                SensorNode sensorNode = (SensorNode) node;
                //System.out.println("Id: " + i + ", energy: " + sensorNode.energy);
                if(sensorNode.energy > maxEnergy) {
                	maxEnergy = sensorNode.energy;
                	maxEnergyId = i;
                }
        	}
        }
        SensorNode sn = (SensorNode) Jsensor.getNodeByID(maxEnergyId);
        for (int i = 1; i <= Jsensor.getNumNodes(); i++) {
            jsensor.nodes.Node node = Jsensor.getNodeByID(i);
            if (node instanceof SensorNode) {
                SensorNode sensorNode = (SensorNode) node;
                sn.sendControlTo(i, 1);
                boolean result = sensorNode.send();
                if(result)
                    count++;
            }
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
