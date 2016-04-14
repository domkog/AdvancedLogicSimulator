package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class NANDComponent extends BasicGate implements Processable {

	public NANDComponent(int x, int y, int numInputs) {
		super(5, Categories.LOGIC, "NAND", x, y, numInputs, "resources/gates/AND.png", true);

	}

	@Override
	public void process() {	

		
	}

}
