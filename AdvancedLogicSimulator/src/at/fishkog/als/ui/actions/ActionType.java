package at.fishkog.als.ui.actions;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.meta.MetaValue;

public enum ActionType {

	CREATE, DELETE("X", "Y", "Orientation", "Connectors"), MOVE("X", "Y"), ROTATE("Orientation"), ADD_CONNECTOR("Connectors"), REMOVE_CONNECTOR("Connectors");
	
	private String[] metas;
	
	ActionType(String... metas) {
		this.metas = metas;
	}
	
	@SuppressWarnings("unchecked")
	public void undo(Action action) {
		if(this == ActionType.CREATE) {
			if(action.data instanceof Component) {
				Component comp = (Component) action.data;
				AdvancedLogicSimulator.logicCanvas.removeSilent(comp);
				AdvancedLogicSimulator.renderer.removeNode(comp.getNode());
			} else if(action.data instanceof Wire) {
				Wire comp = (Wire) action.data;
				AdvancedLogicSimulator.logicCanvas.removeSilent(comp);
				AdvancedLogicSimulator.renderer.removeNode(comp.getNode());
			}
		} else if(this == DELETE) {
			if(action.data instanceof Component) {
				Component comp = (Component) action.data;
				resetMeta(action);
				AdvancedLogicSimulator.logicCanvas.addSilent(comp);
				AdvancedLogicSimulator.renderer.addComponent(comp);
			}
		} else if(this == ActionType.ADD_CONNECTOR || this == ActionType.REMOVE_CONNECTOR) {
			resetMeta(action);
			for(MetaValue<?> value: action.data.getMetaData().startsWith("isNegated")) {
				((MetaValue<Boolean>) value).setValue(action.oldMeta.getFirst(Boolean.class, value.id).getValue());
			}
		} else {
			resetMeta(action);
		}
	}
	
	private void resetMeta(Action action) {
		for(int i = 0; i < metas.length; i++) {
			action.data.getMetaData().getFirst(Object.class, metas[i]).setValue(action.oldMeta.getFirst(Object.class, metas[i]).getValue());;
		}
	}
	
}
