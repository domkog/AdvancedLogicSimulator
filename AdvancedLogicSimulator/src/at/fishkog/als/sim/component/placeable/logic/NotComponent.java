package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class NotComponent extends BasicGate implements Processable {

	public NotComponent(int x, int y, int numInputs) {
		super(3, Categories.LOGIC, "NOT", x, y, numInputs, "resources/gates/AND.png", true);

	}

	@Override
	public void process() {	

		
	}

}
