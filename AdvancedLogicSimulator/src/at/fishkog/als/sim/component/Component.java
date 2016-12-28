package at.fishkog.als.sim.component;

import java.util.ArrayList;
import java.util.stream.Collectors;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.data.BasicAttributes;
import at.fishkog.als.sim.data.BitWidth;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.State;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.scene.Group;
import javafx.scene.Node;

public abstract class Component extends Data{
	public ArrayList<Connector> connectors;
	public ArrayList<Connector> inputs;
	public ArrayList<Connector> outputs;
	
	public BasicAttributes basicAttributes;
	
	public BitWidth bitwidth;
	
	public Node node;
	
	public MetaValue<State> state;
	
	public Component(int ID, ComponentCategory category, String name) {
		
		basicAttributes = new BasicAttributes(ID, category.id, name);
		
		connectors = new ArrayList<Connector>();
		inputs = new ArrayList<Connector>();
		outputs = new ArrayList<Connector>();
			
		connectors.forEach((c) -> this.metaData.add(c.getMetaData()));

	}

	public void rerenderComponents(){
		AdvancedLogicSimulator.renderer.rerenderComp(this);
		
	}
	
	public void addConnector(Connector c) {
		connectors.add(c);
		if(c.type.getValue() == Connector.Type.INPUT) inputs.add(c);
		else if(c.type.getValue() == Connector.Type.OUTPUT) outputs.add(c);
	
	}
	
	public void removeConnector(Connector c) {
		if(connectors.contains(c)) connectors.remove(c);
		if(c.type.getValue() == Connector.Type.INPUT && inputs.contains(c)) inputs.remove(c);
		else if(c.type.getValue() == Connector.Type.OUTPUT && outputs.contains(c)) outputs.remove(c);
	
	}
	
	public Connector getConnector(String id) {
		ArrayList<Connector> result = connectors.stream().filter((Connector c) -> c.id.getValue().equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public Connector getConnector(int id) {
		return connectors.get(id);
				
	}
	
	public boolean hasConnectorState(Connector c, int bit, State state) {
		if(c == null) return false;
		else return c.states.get(bit).getValue() == state;
	}
	
	public boolean hasConnectorState(String id, int bit, State state) {
		Connector c = this.getConnector(id);
		return this.hasConnectorState(c, state);
	}
	
	public void setConnectorState(Connector c, int bit, State state) {
		if(c == null) return;
		c.states.get(bit).setValue(state);
	}
	
	public void setConnectorState(String id, int bit, State state) {
		Connector c = this.getConnector(id);
		this.setConnectorState(c, state);
	}
	
	public boolean hasConnectorState(Connector c, State state) {
		return this.hasConnectorState(c,0, state);
		
	}
	
	public boolean hasConnectorState(String id, State state) {
		return this.hasConnectorState(id, 0, state);
		
	}
	
	public void setConnectorState(Connector c, State state) {
		this.setConnectorState(c, 0, state);
	}
	
	public void setConnectorState(String id, State state) {
		this.setConnectorState(id, 0, state);
	}
	
	//TODO recalculate wires
	public void onPositionChanged() {
		
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
		
	@Override
	public String toString() {
		return this.basicAttributes.getStringName();
	}
	
	public abstract Group getRenderparts();	
}
