package projects.Flooding;

import jsensor.runtime.AbsCustomGlobal;
import jsensor.runtime.Jsensor;
import projects.Flooding.Nodes.SensorNode;
import projects.Flooding.Nodes.SinkNode;

import javax.xml.soap.Node;

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
        System.out.println("r:"+ rounds);
        if (rounds >= 500)
            Jsensor.runtime.setAbort(true);
        rounds++;
        for (int i = 1; i <= Jsensor.getNumNodes(); i++) {
            jsensor.nodes.Node node = Jsensor.getNodeByID(i);
            if (node instanceof SensorNode) {
                SensorNode sensorNode = (SensorNode) node;
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
        jsensor.nodes.Node node = Jsensor.getNodeByID(1);
        jsensor.nodes.Node sinkNode = node.getRandomNode("SinkNode");
        System.out.println(count);
    }
}
