package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.State;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class ORComponent extends BasicComponent implements Processable {

	public ORComponent(int x, int y, int numInputs, int bitwidth) {
		super(1, Categories.LOGIC, "OR", x, y, numInputs, bitwidth, false);
	}

	@Override
	public void process() {	
		Connector cOut = this.outputs.get(0);
		for(int i=0; i<this.bitwidth.getWidth(); i++){
			State temp = State.FALSE;
			for(Connector cIn: this.inputs) {
				if(cIn.hasState(i, State.FALSE) && temp.isFalse()) {
					
				} else if(cIn.hasState(i, State.TRUE)) {
					temp=State.TRUE;
					
				} else if(!cIn.hasValidState(i)){
					cOut.setState(i, cIn.getState(i));
					break;
					
				}
			}
			cOut.setState(i, temp);
		}
	}

	@Override
	public Group getRenderparts() {
		double height = this.bounds.getIntHeight();
		double width = this.bounds.getIntWidth();
		
		Group res = new Group();
		
		String arcStyle = "-fx-stroke-width: 4; -fx-stroke-type:inside; -fx-stroke: black; -fx-fill: Transparent";
		Arc arcBase = new Arc(0, height/2, width/4, height/2, 270, 180);
		arcBase.setStyle(arcStyle);
		
		Arc arcSide1 = new Arc(0, height/2, width, height/2, 0, 90);
		arcSide1.setStyle(arcStyle);
		
		Arc arcSide2 = new Arc(0, height/2, width, height/2, 270, 90);
		arcSide2.setStyle(arcStyle);
		
		res.getChildren().setAll(arcBase, arcSide1, arcSide2);
		return res;
	}
	@Override
	public BasicComponent createNew(int x, int y, int numInputs, int bitwidth) {
		return new ORComponent(x,y,numInputs,bitwidth);
		
	}
}
