package at.fishkog.als.sim.data;

import java.util.ArrayList;
import java.util.HashMap;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.Wire;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;

public class Connector extends Data {

	public enum Type {
		INPUT, OUTPUT
		
	}

	public  Component component;
	
	public boolean isNegated;
	
	public MetaValue<String> id;
	public MetaValue<Type> type;
	public ArrayList<MetaValue<Data.State>> states;
	
	public Location location;
	public Bounds bounds;
	
	public Wire connectedWire;
	
	public Connector(Component component, String id, Type type, int x, int y, BitWidth bitWidth, boolean isNegated) {
		this.component = component;
		
		this.id = new MetaValue<String>(id);
		this.type = new MetaValue<Type>(type);
		location = new Location(x, y);
		bounds = new Bounds(7, 7);
		
		this.bitwidth = bitWidth;

		ArrayList<MetaValue<Data.State>> states = new ArrayList<MetaValue<Data.State>>();		
		
		for(MetaValue<Data.State> state : states) {
			state.value = State.UNKOWN;
		}
		
		this.isNegated = isNegated;
		
		location.x.access = MetaAccess.READ;
		location.y.access = MetaAccess.READ;
		bounds.width.access = MetaAccess.READ;
		bounds.height.access = MetaAccess.READ;
	}
	
	public boolean isWireConnected() {
		return connectedWire != null;
	}
	
	public void attachWire(Wire wire) {
		this.connectedWire = wire;
	}
	
	public void detachWire() {
		if(this.connectedWire != null) this.connectedWire.removeInput(this); 
		this.connectedWire = null;
	}
	
	public boolean intersects(int mouseX, int mouseY) {
		return bounds.intersects(location.getIntX(), location.getIntY(), mouseX, mouseY);
	}
	
	public void onDraggedStarted() {
	}
	
	public void onDragged(int mouseX, int mouseY) {
	}
	
	public void onDraggedFinish(int mouseX, int mouseY) {
	}
	
	public void onMoved() {
		if(isWireConnected()) connectedWire.onMoved();
	}
	
	public Data.State getState(int id){
		return this.states.get(id).value;
		
	}
	
	public void setState(int id, Data.State state) {
		this.states.get(id).setValue(state);
		
	}
	
	public boolean hasState(int id, Data.State state) {
		return this.states.get(id).value == state;
		
	}
			
	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("id", id);
		result.put("type", type);
		for(int i=0; i<this.bitwidth.getWidth(); i++) {
			result.put("state." + i, this.states.get(i));
			
		}
		result.putAll(location.getMetaValues());
		result.putAll(bounds.getMetaValues());
		return result;
	}

}
