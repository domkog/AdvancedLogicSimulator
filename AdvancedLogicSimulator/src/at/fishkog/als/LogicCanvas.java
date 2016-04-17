package at.fishkog.als;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.data.Data;

public class LogicCanvas {

	public ArrayList<Data> components;
	
	public LogicCanvas() {
		components = new ArrayList<Data>();
	}
	
	public void add(Data c) {
		components.add(c);
	}
	
	public void remove(Data c) {
		components.remove(c);
	}
	
}
