package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.State;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeType;

public class ANDComponent extends BasicComponent implements Processable {

	public ANDComponent(int x, int y, int numInputs, int bitwidth) {
		super(0, Categories.LOGIC, "AND", x, y, numInputs, bitwidth, false);

	}

	@Override
	public void process() {	
		Connector cOut = this.outputs.get(0);
		for(int i=0; i<this.bitwidth.getWidth(); i++){
			State temp = State.TRUE;
			for(Connector cIn: this.inputs) {
				if(cIn.hasState(i, State.TRUE) && temp.isTrue()) {
					
				} else if(cIn.hasState(i, State.FALSE)) {
					temp=State.FALSE;
					
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
		double width = this.bounds.getIntWidth()/2;
		
		Group res = new Group();
		
		Polyline base = new Polyline();
		base.setStrokeType(StrokeType.INSIDE);
		base.setStrokeWidth(4);
		base.getPoints().addAll(new Double[]{
				width,0.0,
				0.0,0.0,
				0.0, height,
				width, height,
				
		});
		
		Arc arc = new Arc(width, height/2, width, height/2, 270, 180);
		arc.setStrokeWidth(4);
		arc.setStrokeType(StrokeType.INSIDE);
		arc.setStroke(Color.BLACK);
		arc.setFill(Color.TRANSPARENT);
		
		res.getChildren().setAll(base,arc);
		return res;
	}
}
