package at.fishkog.als;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.ui.actions.Action;
import at.fishkog.als.ui.actions.ActionType;

public class LogicCanvas {

	private ArrayList<Component> components;
	
	public LogicCanvas() {
		components = new ArrayList<Component>();
	}
	
	public ArrayList<Component> getComponents() {
		return this.components;
	}
	
	public void add(Component c) {
		Action a = new Action(ActionType.CREATE, c);
		components.add(c);
		AdvancedLogicSimulator.renderer.addComponent(c);

		a.end();
	}
	
	public void remove(Component c) {
		Action a = new Action(ActionType.DELETE, c);
		components.remove(c);
		if(c instanceof Component) {
			Component comp = (Component) c;
			AdvancedLogicSimulator.renderer.removeNode(comp.getNode());
		} else if (c instanceof Wire) {
			Wire w = (Wire) c;
			AdvancedLogicSimulator.renderer.removeNode(w.getNode());
		}
		a.end();
	}
	
	public void addSilent(Component c) {
		components.add(c);
		AdvancedLogicSimulator.renderer.addComponent(c);
	}
	
	public void removeSilent(Component c) {
		components.remove(c);
		if(c instanceof Component) {
			Component comp = (Component) c;
			AdvancedLogicSimulator.renderer.removeNode(comp.getNode());
		} else if (c instanceof Wire) {
			Wire w = (Wire) c;
			AdvancedLogicSimulator.renderer.removeNode(w.getNode());
		}
	}
	
}
