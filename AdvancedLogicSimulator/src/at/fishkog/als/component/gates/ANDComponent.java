package at.fishkog.als.component.gates;

import at.fishkog.als.component.Component;
import at.fishkog.als.component.Processable;
import at.fishkog.als.component.categories.Categories;
import at.fishkog.als.component.rendering.RenderContext;
import at.fishkog.als.data.Connector;
import at.fishkog.als.data.Connector.State;
import at.fishkog.als.data.Connector.Type;

public class ANDComponent extends Component implements Processable {

	public ANDComponent(int x, int y) {
		super(0, Categories.LOGIC, "AND", x, y, 0, 0);
		
		renderContext = new RenderContext("resources/gates/AND.png");
		
		this.bounds.width.setValue((int) renderContext.getImage().getWidth());
		this.bounds.height.setValue((int) renderContext.getImage().getHeight());
		
		this.connectors.add(new Connector("in1", Type.INPUT));
		this.connectors.add(new Connector("in2", Type.INPUT));
		this.connectors.add(new Connector("out1", Type.OUTPUT));
	}

	@Override
	public void process() {
		if(this.hasConnectorState("in1", State.TRUE) && this.hasConnectorState("in2", State.TRUE)) this.setConnectorState("out1", State.TRUE);
		else this.setConnectorState("out1", State.FALSE);
	}

}
