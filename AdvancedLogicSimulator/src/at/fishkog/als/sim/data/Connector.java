package at.fishkog.als.sim.data;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;

public class Connector extends Data {

	public enum Type {
		INPUT, OUTPUT
	}

	public  Component component;
	
	public MetaValue<Boolean> isNegated;
	
	public MetaValue<String> id;
	public MetaValue<Type> type;
	public ArrayList<MetaValue<Data.State>> states;
	
	public Location location;
	public Bounds bounds;
	
	public Wire connectedWire;
	
	public Connector(Component component, String id, Type type, int x, int y, BitWidth bitWidth, boolean isNegated) {
		this.component = component;
		
		this.id = new MetaValue<String>("ID", id, MetaAccess.HIDDEN);
		this.type = new MetaValue<Type>("Type", type, MetaAccess.HIDDEN);
		location = new Location(x, y);
		bounds = new Bounds(7, 7);
		
		this.bitwidth = bitWidth;
		
		states = new ArrayList<MetaValue<Data.State>>();		
		
		for(int i = 0; i < bitwidth.getWidth(); i++) {
			states.add(new MetaValue<Data.State>("State-" + i, State.UNKNOWN, MetaAccess.HIDDEN));
		}
		
		this.isNegated = new MetaValue<Boolean>("isNegated(" + id + ")", isNegated);
		
		location.x.access = MetaAccess.HIDDEN;
		location.y.access = MetaAccess.HIDDEN;
		bounds.width.access = MetaAccess.HIDDEN;
		bounds.height.access = MetaAccess.HIDDEN;
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
	public ArrayList<MetaValue<?>> getMetaValues() {
		ArrayList<MetaValue<?>> result = new ArrayList<MetaValue<?>>();
		result.add(id);
		result.add(isNegated);
		result.add(type);
		if(this.bitwidth != null) {
			for(int i=0; i < this.bitwidth.getWidth(); i++) {
				result.add(this.states.get(i));
			}
		}
		result.addAll(location.getMetaValues());
		result.addAll(bounds.getMetaValues());
		return result;
	}

}
