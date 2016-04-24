package at.fishkog.als.sim.data;

import java.util.ArrayList;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;
import javafx.scene.Node;

public class Connector extends Data {

	public enum Type {
		INPUT, OUTPUT
	}

	public  Component component;
	
	public MetaValue<Boolean> isNegated;
	
	public MetaValue<String> id;
	public MetaValue<Type> type;
	public ArrayList<MetaValue<State>> states;
	
	public Location location;
	public Bounds bounds;
	
	public Wire connectedWire;
	
	private Node node;
	
	public BitWidth bitwidth;
	
	public Connector(Component component, String id, Type type, int x, int y, BitWidth bitWidth, boolean isNegated) {
		super();
		
		this.component = component;
		
		this.id = new MetaValue<String>("ID", id, MetaAccess.HIDDEN);
		this.type = new MetaValue<Type>("Type", type, MetaAccess.HIDDEN);
		bounds = new Bounds(5, 5);
		
		this.bitwidth = bitWidth;
		
		states = new ArrayList<MetaValue<State>>();		
		
		this.location = new Location(x, y);
		
		for(int i = 0; i < bitwidth.getWidth(); i++) {
			states.add(new MetaValue<State>("State-" + i, State.UNKNOWN, MetaAccess.HIDDEN));
		}
		
		this.isNegated = new MetaValue<Boolean>("isNegated(" + id + ")", isNegated);
		
		location.x.access = MetaAccess.HIDDEN;
		location.y.access = MetaAccess.HIDDEN;
		bounds.width.access = MetaAccess.HIDDEN;
		bounds.height.access = MetaAccess.HIDDEN;
		
		/*MetaData*/
		metaData.add(this.isNegated, this.type);
		if(this.bitwidth != null) {
			for(int i=0; i < this.bitwidth.getWidth(); i++) {
				metaData.add(this.states.get(i));
			}
		}
		metaData.add(location.getMetaData(), bounds.getMetaData());
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
	
	public void onDraggedStarted() {
	}
	
	public void onDragged(int mouseX, int mouseY) {
	}
	
	public void onDraggedFinish(int mouseX, int mouseY) {
	}
	
	public void onMoved() {
		if(isWireConnected()) connectedWire.onMoved();
	}
	
	public State getState(int id){
		return this.states.get(id).getValue();
		
	}
	
	public void setState(int id, State state) {
		this.states.get(id).setValue(state);
		
	}
	
	public boolean hasState(int id, State state) {
		return this.states.get(id).getValue() == state;
		
	}
	
	public boolean hasValidState(int id) {
		return this.states.get(id).getValue().isValid();
		
	}
	
	public boolean hasNode() {
		return this.node != null;
		
	}
	
	public void setNode(Node node) {
		this.node = node;
		
	}
	
	public Node getNode() {
		return this.node;
		
	}

}
