package at.fishkog.als.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import at.fishkog.als.component.categories.ComponentCategory;
import at.fishkog.als.component.rendering.RenderContext;
import at.fishkog.als.data.BasicAttributes;
import at.fishkog.als.data.Bounds;
import at.fishkog.als.data.Connector;
import at.fishkog.als.data.Connector.State;
import at.fishkog.als.data.Data;
import at.fishkog.als.data.Location;
import at.fishkog.als.data.meta.MetaValue;

//Base class that all components need to implement
public abstract class Component extends Data {

	public RenderContext renderContext;
	
	public BasicAttributes basicAttributes;
	public Location location;
	public Bounds bounds;
	
	public ArrayList<Connector> connectors;
	
	public Component(int ID, ComponentCategory category, String name, int x, int y, int width, int height) {
		basicAttributes = new BasicAttributes(ID, category.id, name);
		location = new Location(x, y);
		bounds = new Bounds(width, height);
		connectors = new ArrayList<Connector>();
	}
	
	public boolean hasTexture() {
		return renderContext != null;
	}
	
	public Connector getConnector(String id) {
		ArrayList<Connector> result = connectors.stream().filter((Connector c) -> c.id.value.equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public boolean hasConnectorState(Connector c, State state) {
		if(c == null) return false;
		else return c.state.value == state;
	}
	
	public boolean hasConnectorState(String id, State state) {
		Connector c = this.getConnector(id);
		return this.hasConnectorState(c, state);
	}
	
	public void setConnectorState(Connector c, State state) {
		if(c == null) return;
		c.state.setValue(state);
	}
	
	public void setConnectorState(String id, State state) {
		Connector c = this.getConnector(id);
		this.setConnectorState(c, state);
	}
	
	//Todo rotate this shit and recalculate wires
	public void onRotate() {
		
	}
	
	//Todo recalculate wires and connectors
	public void onPositionChanged() {
		
	}
	
	//Check if point intersects with this component
	public boolean intersects(int mouseX, int mouseY) {
		return this.bounds.intersects(this.location.getIntX(), this.location.getIntY(), mouseX, mouseY);
	}
	
	//Check if the component is in the clip of the given dimensions
	public boolean isVisible(int offsetX, int offsetY, int width, int height) {
		return this.bounds.isVisible(this.location.getIntX(), this.location.getIntY(), offsetX, offsetY, width, height);
	}

	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.putAll(basicAttributes.getMetaValues());
		result.putAll(location.getMetaValues());
		result.putAll(bounds.getMetaValues());
		connectors.forEach((c) -> result.putAll(c.getMetaValues()));
		return result;
	}
	
	@Override
	public String toString() {
		return this.basicAttributes.getStringName();
	}
	
}
