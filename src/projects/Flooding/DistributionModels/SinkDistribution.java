package projects.Flooding.DistributionModels;

import jsensor.nodes.Node;
import jsensor.nodes.models.DistributionModelNode;
import jsensor.utils.Position;

public class SinkDistribution extends DistributionModelNode {
    @Override
    public Position getPosition(Node n) {
        return new Position(1, 1);
    }
}
