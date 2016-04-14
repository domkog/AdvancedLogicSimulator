package at.fishkog.als.sim.component.gates;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.rendering.RenderContext;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Connector.State;
import at.fishkog.als.sim.data.Connector.Type;

public class ANDComponent extends Component implements Processable {

	public ANDComponent(int x, int y) {
		super(0, Categories.LOGIC, "AND", x, y, 0, 0);
		
		renderContext = new RenderContext("resources/gates/AND.png");
		
		this.bounds.width.setValue((int) renderContext.getImage().getWidth());
		this.bounds.height.setValue((int) renderContext.getImage().getHeight());
		
		for(int i = 0; i < 2; i++) this.addConnector(new Connector("in" + i, Type.INPUT, 0, 0));
		
		this.addConnector(new Connector("out1", Type.OUTPUT, 0, 0));
		
		this.recalculateConnectorPosition();
	}

	@Override
	public void process() {
		if(this.hasConnectorState("in1", State.TRUE) && this.hasConnectorState("in2", State.TRUE)) this.setConnectorState("out1", State.TRUE);
		else this.setConnectorState("out1", State.FALSE);
	}

}
