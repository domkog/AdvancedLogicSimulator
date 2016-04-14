package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class ANDComponent extends BasicGate implements Processable {

	public ANDComponent(int x, int y, int numInputs) {
		super(0, Categories.LOGIC, "AND", x, y, numInputs, "resources/gates/AND.png", false);

	}

	@Override
	public void process() {	

		
	}

}
