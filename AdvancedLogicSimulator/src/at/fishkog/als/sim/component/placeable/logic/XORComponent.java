package at.fishkog.als.sim.component.placeable.logic;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.gates.BasicGate;

public class XORComponent extends BasicGate implements Processable {

	public XORComponent(int x, int y, int numInputs) {
		super(4, Categories.LOGIC, "XOR", x, y, numInputs, "resources/gates/AND.png", false);

	}

	@Override
	public void process() {	

		
	}

}
