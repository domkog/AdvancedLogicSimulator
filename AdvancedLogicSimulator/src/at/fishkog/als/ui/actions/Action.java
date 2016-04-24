package at.fishkog.als.ui.actions;


import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.data.meta.MetaData;

public class Action {

	public ActionType type;
	
	public Component data;
	public MetaData oldMeta;
	
	public Action(ActionType type, Component data) {
		this.type = type;
		
		this.data = data;
		this.oldMeta = data.getMetaData().clone();
	}
	
	public void end() {
		Actions.instance.add(this);
	}
	
}
