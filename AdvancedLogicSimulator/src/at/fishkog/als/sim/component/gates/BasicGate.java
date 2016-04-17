package at.fishkog.als.sim.component.gates;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.component.rendering.RenderContext;
import at.fishkog.als.sim.data.BitWidth;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Connector.Type;

public abstract class BasicGate extends Component{
	
	private boolean isNegated;
	
	public BasicGate(int ID, ComponentCategory category, String name, int x, int y, int numInputs, String imagePath, boolean isNegated) {
		super(ID, category, name, x, y, 0, 0);
		this.bitwidth = new BitWidth(32);
		this.createConnectors(numInputs, isNegated);
		this.isNegated = isNegated;
		
		renderContext = new RenderContext(imagePath);
		
		this.bounds.width.setValue((int) renderContext.getImage().getWidth());
		this.bounds.height.setValue((int) renderContext.getImage().getHeight());

		this.recalculateConnectorPosition();
	}

	public void setNumInputs(int num) {
		this.connectors.clear();		
		this.createConnectors(num, this.isNegated);
	}
	
	private void createConnectors(int num, boolean isNegated) {
		this.addConnector(new Connector(this, "out1", Type.OUTPUT, 0, 0, this.bitwidth, isNegated));
		for(int i = 0; i < num; i++) this.addConnector(new Connector(this, "in" + i, Type.INPUT, 0, 0, this.bitwidth, true));
		
		
	}
	
}
