package at.fishkog.als.ui.actions;

import java.util.Stack;

import at.fishkog.als.AdvancedLogicSimulator;

public class Actions {

	//TODO Temp needs to be initialized in each project
	public static final Actions instance = new Actions();
	
	private Stack<Action> actions;
	
	public Actions() {
		actions = new Stack<Action>();
	}
	
	protected void add(Action action) {
		if(actions.isEmpty()) {
			AdvancedLogicSimulator.mainUi.menuItemUndo.setDisable(false);
		}
		this.actions.push(action);
	}
	
	public void undo() {
		if(actions.isEmpty()) return;
		Action action = actions.pop();
		action.type.undo(action);
		if(actions.isEmpty()) {
			AdvancedLogicSimulator.mainUi.menuItemUndo.setDisable(true);
		}
	}
	
}
