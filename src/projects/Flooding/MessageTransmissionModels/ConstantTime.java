package projects.Flooding.MessageTransmissionModels;

import jsensor.nodes.Node;
import jsensor.nodes.messages.Message;
import jsensor.nodes.models.MessageTransmissionModel;

public class ConstantTime extends MessageTransmissionModel{

	@Override
	public float timeToReach(Node nodeSource, Node nodeDestination, Message msg) {
		return 2;
	}

	
}
