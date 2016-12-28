package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeType;

public class NANDComponent extends BasicComponent implements Processable {

	public NANDComponent(int x, int y, int numInputs, int bitwidth) {
		super(5, Categories.LOGIC, "NAND", x, y, numInputs, bitwidth,true);

	}

	@Override
	public void process() {	

		
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
	@Override
	public BasicComponent createNew(int x, int y, int numInputs, int bitwidth) {
		return new NANDComponent(x,y,numInputs,bitwidth);
		
	}
	
}
