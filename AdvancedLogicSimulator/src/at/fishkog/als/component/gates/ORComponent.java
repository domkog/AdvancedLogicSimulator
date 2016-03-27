package at.fishkog.als.component.gates;

import at.fishkog.als.component.Component;
import at.fishkog.als.component.Processable;
import at.fishkog.als.component.categories.Categories;
import at.fishkog.als.data.Connector;
import at.fishkog.als.data.Connector.State;
import at.fishkog.als.data.Connector.Type;

public class ORComponent extends Component implements Processable {

	public ORComponent(int x, int y, int width, int height) {
		super(0, Categories.LOGIC, "OR", x, y, width, height);
		
		this.connectors.add(new Connector("in1", Type.INPUT));
		this.connectors.add(new Connector("in2", Type.INPUT));
		this.connectors.add(new Connector("out1", Type.OUTPUT));
	}

	@Override
	public void process() {
		if(this.hasConnectorState("in1", State.TRUE) || this.hasConnectorState("in2", State.TRUE)) this.setConnectorState("out1", State.TRUE);
		else this.setConnectorState("out1", State.FALSE);
	}

}
