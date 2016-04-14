package at.fishkog.als.sim.component.gates;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Connector.State;
import at.fishkog.als.sim.data.Connector.Type;

public class ORComponent extends Component implements Processable {

	public ORComponent(int x, int y, int width, int height) {
		super(0, Categories.LOGIC, "OR", x, y, width, height);
		
		this.addConnector(new Connector("in1", Type.INPUT, 0, 0));
		this.addConnector(new Connector("in2", Type.INPUT, 0, 0));
		this.addConnector(new Connector("out1", Type.OUTPUT, 0, 0));
		
		this.recalculateConnectorPosition();
	}

	@Override
	public void process() {
		if(this.hasConnectorState("in1", State.TRUE) || this.hasConnectorState("in2", State.TRUE)) this.setConnectorState("out1", State.TRUE);
		else this.setConnectorState("out1", State.FALSE);
	}

}
