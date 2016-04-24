package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.data.Connector;
import javafx.scene.Group;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeType;

public class NOTComponent extends BasicComponent implements Processable {

	public NOTComponent(int x, int y, int numInputs, int bitwidth) {
		super(3, Categories.LOGIC, "NOT", x, y, numInputs,bitwidth, true);
		this.bounds.height.setValue(this.bounds.height.getValue()/2);
		this.bounds.width.setValue(this.bounds.width.getValue()/2);
		
	}

	@Override
	public void process() {	
		Connector cOut = this.outputs.get(0);
		for(int i=0; i<this.bitwidth.getWidth(); i++){
			cOut.setState(i, inputs.get(0).getState(i).negate());				
			
		}
	}

	@Override
	public Group getRenderparts() {
		double height = this.bounds.getIntHeight();
		double width = this.bounds.getIntWidth();
		
		Group res = new Group();
		
		Polyline base = new Polyline();
		base.setStrokeType(StrokeType.INSIDE);
		base.setStrokeWidth(4);
		base.getPoints().addAll(new Double[]{
				0.0,0.0,
				width, height/2,
				0.0, height,
				0.0,0.0
				
		});
				
		res.getChildren().setAll(base);
		return res;
	}

}
