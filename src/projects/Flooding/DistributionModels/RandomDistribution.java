package projects.Flooding.DistributionModels;

import jsensor.nodes.Node;
import jsensor.nodes.models.DistributionModelNode;
import jsensor.utils.Configuration;
import jsensor.utils.Position;

/**
 *
 * @authorMatheus
 */
public class RandomDistribution extends DistributionModelNode
{
	 @Override
	    public Position getPosition(Node n) {
	        return new Position(n.getRandom().nextInt(Configuration.dimX), n.getRandom().nextInt(Configuration.dimY));
	    }
}
