package projects.Flooding;

import java.util.Random;

import jsensor.runtime.Jsensor;
import projects.Flooding.Nodes.SensorNode;

public class Kmeans {
	
	private int k;
	private double[][] centroids;
	private int nodes;
	private int[] nodesCluster;
	
	public Kmeans(int k, int nodes){
		this.k = k;
		this.nodes = nodes;
		centroids = new double[k][2];
		nodesCluster = new int[nodes];
	}
	
	public int[] run() {
		Random r = new Random();
		
		for(int i = 0; i < k; i++) {
			centroids[i][0] = 100 * r.nextDouble();
			centroids[i][1] = 100 * r.nextDouble();
		}
		
		asssociateNodes();
		for(int i = 0; i < 10; i++) {
			calculateNewCentroids();
			asssociateNodes();
		}
		
		return nodesCluster;
	}
	
    public double distance(double nx, double ny, double cx, double cy) {
        double dX = nx - cx;
        double dY = ny - cy;
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
        return distance;
    }
    
    private void asssociateNodes() {
    	for(int i = 0; i < nodes; i++) {
			nodesCluster[i] = 1;
			jsensor.nodes.Node node = Jsensor.getNodeByID(i+1);
			if (node instanceof SensorNode) {
				double minDistance = distance(node.getPosition().getPosX(), node.getPosition().getPosY(), centroids[0][0], centroids[0][1]);
				for(int j = 1; j < k; j++) {
					if(minDistance > distance(node.getPosition().getPosX(), node.getPosition().getPosY(), centroids[j][0], centroids[j][1])){
						minDistance = distance(node.getPosition().getPosX(), node.getPosition().getPosY(), centroids[j][0], centroids[j][1]);
						nodesCluster[i] = j+1;
					}
				}
			}
		}
    }
    
    private void calculateNewCentroids() {
    	double newX;
    	double newY;
    	int count;
    	
    	for(int i = 0; i < k; i++) {
    		newX = 0;
    		newY = 0;
    		count = 0;
    		for(int j = 0; j < nodes; j++) {
    			jsensor.nodes.Node node = Jsensor.getNodeByID(j+1);
    			if (node instanceof SensorNode) {
	    			if(nodesCluster[j] == i+1) {
	    				newX += node.getPosition().getPosX();
	    				newY += node.getPosition().getPosY();
	    				count++;
	    			}
    			}
    		}
    		centroids[i][0] = newX/count;
    		centroids[i][1] = newY/count;
    	}
    }

}
