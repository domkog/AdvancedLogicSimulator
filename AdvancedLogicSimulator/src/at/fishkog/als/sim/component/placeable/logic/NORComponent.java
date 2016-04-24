package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class NORComponent extends BasicComponent implements Processable {

	public NORComponent(int x, int y, int numInputs, int bitwidth) {
		super(6, Categories.LOGIC, "NOR", x, y, numInputs, bitwidth, true);

	}

	@Override
	public void process() {	

		
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

}
