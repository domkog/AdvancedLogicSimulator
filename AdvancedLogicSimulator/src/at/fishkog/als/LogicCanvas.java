package at.fishkog.als;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Component;

public class LogicCanvas {

	public ArrayList<Component> components;
	
	public LogicCanvas() {
		components = new ArrayList<Component>();
	}
	
	public void add(Component c) {
		components.add(c);
	}
	
	public void remove(Component c) {
		components.remove(c);
	}
	
}
