package at.fishkog.als.sim;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Processable;

public class Processor {
	private ArrayList<Processable> processables;
	
	public void process() {
		for(Processable p: processables) {
			p.process();
			
		}
	}
	
}
