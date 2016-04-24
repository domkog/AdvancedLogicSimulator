package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.State;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class XNORComponent extends BasicComponent implements Processable {

	public XNORComponent(int x, int y, int numInputs, int bitwidth) {
		super(7, Categories.LOGIC, "XNOR", x, y, numInputs, bitwidth, true);

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
		double width = this.bounds.getIntWidth()/10;
		
		Group res = new Group();
		
		String arcStyle = "-fx-stroke-width: 4; -fx-stroke-type:inside; -fx-stroke: black; -fx-fill: Transparent";
		
		double width2 = width*8;
		double width1=width*2;
		Arc arcBase = new Arc(0, height/2, width2/4, height/2, 270, 180);
		arcBase.setStyle(arcStyle);

		Arc arcBase2 = new Arc(width1, height/2, width2/4, height/2, 270, 180);
		arcBase2.setStyle(arcStyle);
		
		Arc arcSide1 = new Arc(width1, height/2, width2, height/2, 0, 90);
		arcSide1.setStyle(arcStyle);
		
		Arc arcSide2 = new Arc(width1, height/2, width2, height/2, 270, 90);
		arcSide2.setStyle(arcStyle);
		
		res.getChildren().setAll(arcBase, arcBase2, arcSide1, arcSide2);
		return res;
	}

}
