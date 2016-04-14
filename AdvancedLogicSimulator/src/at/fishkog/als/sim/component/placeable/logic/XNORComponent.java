package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class XNORComponent extends BasicGate implements Processable {

	public XNORComponent(int x, int y, int numInputs) {
		super(7, Categories.LOGIC, "XNOR", x, y, numInputs, "resources/gates/AND.png", true);

	}

	@Override
	public void process() {	

		
	}

}
