package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class NORComponent extends BasicGate implements Processable {

	public NORComponent(int x, int y, int numInputs) {
		super(6, Categories.LOGIC, "NOR", x, y, numInputs, "resources/gates/AND.png", true);

	}

	@Override
	public void process() {	

		
	}

}
