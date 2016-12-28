package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import javafx.scene.Group;
import javafx.scene.shape.Arc;

public class XORComponent extends BasicComponent implements Processable {

	public XORComponent(int x, int y, int numInputs, int bitwidth) {
		super(4, Categories.LOGIC, "XOR", x, y, numInputs, bitwidth, false);
		this.bounds.width.setValue((int)( this.bounds.width.getValue()*1.4));
	}

	@Override
	public void process() {	

		
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
	@Override
	public BasicComponent createNew(int x, int y, int numInputs, int bitwidth) {
		return new XORComponent(x,y,numInputs,bitwidth);
		
	}
}
