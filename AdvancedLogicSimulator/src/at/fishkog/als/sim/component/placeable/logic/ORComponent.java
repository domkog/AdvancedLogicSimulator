package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class ORComponent extends BasicGate implements Processable {

	public ORComponent(int x, int y, int numInputs) {
		super(1, Categories.LOGIC, "OR", x, y, numInputs, "resources/gates/AND.png", false);

	}

	@Override
	public void process() {	

		
	}

}
