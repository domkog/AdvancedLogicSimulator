package at.fishkog.als.ui.customNodes;

import at.fishkog.als.sim.component.Component;
import javafx.scene.Group;

public class ComponentGroup extends Group{
	private Component comp;
	
	public ComponentGroup(Component comp) {
		super();
		this.comp = comp;
		
	}

	public Component getComp() {
		return comp;
	}	
}
