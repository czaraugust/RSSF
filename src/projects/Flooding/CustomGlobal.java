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
    	
    }

    @Override
    public void preRound() {
    	rounds++;
        if (rounds >= 500)
            Jsensor.runtime.setAbort(true);
        System.out.println("r:"+ rounds);
        double maxEnergy = 0;
        int maxEnergyId = 0;
        
        for (int i = 1; i <= Jsensor.getNumNodes(); i++) {
        	jsensor.nodes.Node node = Jsensor.getNodeByID(i);
        	if (node instanceof SensorNode) {
        		
                SensorNode sensorNode = (SensorNode) node;
                if(sensorNode.energy > maxEnergy) {
                	maxEnergy = sensorNode.energy;
                	maxEnergyId = i;
                }
        	}
        }
       System.out.println("Lider: " + maxEnergyId);
        for (int i = 1; i <= Jsensor.getNumNodes(); i++) {
            jsensor.nodes.Node node = Jsensor.getNodeByID(i);
            if (node instanceof SensorNode && i != maxEnergyId) {
                SensorNode sensorNode = (SensorNode) node;
                boolean result = sensorNode.sendTo(maxEnergyId);
                if(result)
                    count++;
            }
        }
        jsensor.nodes.Node node = Jsensor.getNodeByID(maxEnergyId);
        if (node instanceof SensorNode) {
            SensorNode sensorNode = (SensorNode) node;
            boolean result = sensorNode.sendTo(51);
            if(result)
                count++;
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
