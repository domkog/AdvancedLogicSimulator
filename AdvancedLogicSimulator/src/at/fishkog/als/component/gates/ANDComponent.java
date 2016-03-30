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
		
		for(int i = 0; i < 31; i++) this.addConnector(new Connector("in" + i, Type.INPUT, 0, 0));
		
		this.addConnector(new Connector("out1", Type.OUTPUT, 0, 0));
		
		this.recalculateConnectorPosition();
	}

	@Override
	public void process() {
		if(this.hasConnectorState("in1", State.TRUE) && this.hasConnectorState("in2", State.TRUE)) this.setConnectorState("out1", State.TRUE);
		else this.setConnectorState("out1", State.FALSE);
	}

}
