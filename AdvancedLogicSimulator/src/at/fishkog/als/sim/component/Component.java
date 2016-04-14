package at.fishkog.als.sim.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.component.rendering.RenderContext;
import at.fishkog.als.sim.data.BasicAttributes;
import at.fishkog.als.sim.data.Bounds;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValueListener;

//Base class that all components need to implement
public abstract class Component extends Data {

	public RenderContext renderContext;
	
	public BasicAttributes basicAttributes;
	public Location location, renderLocation;
	public Bounds bounds;
	
	public ArrayList<Connector> connectors;
	public ArrayList<Connector> inputs;
	public ArrayList<Connector> outputs;
	
	public int extensionWidth;
	public int extensionHeight;
	
	public Component(int ID, ComponentCategory category, String name, int x, int y, int width, int height) {
		basicAttributes = new BasicAttributes(ID, category.id, name);
		location = new Location(x, y);
		renderLocation = new Location(x, y);
		location.x.addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				renderLocation.x.setValue(renderLocation.x.value + (to - from));
				for(Connector c: connectors) {
					c.location.x.setValue(c.location.x.value + (to - from));
				}
			}
		});
		location.y.addListener(new MetaValueListener<Integer>() {
			@Override
			public void change(Integer from, Integer to) {
				renderLocation.y.setValue(renderLocation.y.value + (to - from));
				for(Connector c: connectors) {
					c.location.y.setValue(c.location.y.value + (to - from));
				}
			}
		});
		bounds = new Bounds(width, height);
		connectors = new ArrayList<Connector>();
		inputs = new ArrayList<Connector>();
		outputs = new ArrayList<Connector>();
	}
	
	public void addConnector(Connector c) {
		connectors.add(c);
		if(c.type.value == Connector.Type.INPUT) inputs.add(c);
		else if(c.type.value == Connector.Type.OUTPUT) outputs.add(c);
	}
	
	public void removeConnector(Connector c) {
		if(connectors.contains(c)) connectors.remove(c);
		if(c.type.value == Connector.Type.INPUT && inputs.contains(c)) inputs.remove(c);
		else if(c.type.value == Connector.Type.OUTPUT && outputs.contains(c)) outputs.remove(c);
	}
	
	public boolean hasTexture() {
		return renderContext != null;
	}
	
	public Connector getConnector(String id) {
		ArrayList<Connector> result = connectors.stream().filter((Connector c) -> c.id.value.equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public boolean hasConnectorState(Connector c, int bit, State state) {
		if(c == null) return false;
		else return c.states.get(bit).value == state;
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
	
	//Todo rotate this shit and recalculate wires
	public void onRotate() {
		if(this.basicAttributes.getOrientation().value == 3) {
			this.basicAttributes.getOrientation().setValue(0);
		} else {
			this.basicAttributes.getOrientation().setValue(this.basicAttributes.getOrientation().value + 1);
		}
		
		int width = this.bounds.width.value;
		int height = this.bounds.height.value;
		
		this.bounds.width.setValue(height);
		this.bounds.height.setValue(width);
		if(this.basicAttributes.getOrientation().value == 1) {
			this.renderLocation.x.setValue(this.location.x.value - ((width - height) / 2));
			this.renderLocation.y.setValue(this.location.y.value + ((width - height) / 2));
		} else if(this.basicAttributes.getOrientation().value == 3) {
			this.renderLocation.x.setValue(this.location.x.value - ((width - height) / 2));
			this.renderLocation.y.setValue(this.location.y.value + ((width - height) / 2));
		} else {
			this.renderLocation.x.setValue(this.location.x.value);
			this.renderLocation.y.setValue(this.location.y.value);
		}
		this.recalculateConnectorPosition();
	}
	
	//Todo recalculate wires and connectors
	public void onPositionChanged() {
		
	}

	protected void recalculateConnectorPosition() {
		extensionWidth = (int) ((this.inputs.size() > 2) ? this.bounds.getIntWidth() + ((((this.inputs.size() % 2 != 0) ? this.inputs.size() + 1 : this.inputs.size())) * (this.inputs.get(0).bounds.getIntWidth() * 3)) : this.bounds.getIntWidth());
		extensionHeight = (int) ((this.inputs.size() > 2) ? this.bounds.getIntHeight() + ((((this.inputs.size() % 2 != 0) ? this.inputs.size() + 1 : this.inputs.size())) * (this.inputs.get(0).bounds.getIntHeight() * 3)) : this.bounds.getIntHeight());
		
		int offsetWidth = (extensionWidth - this.bounds.getIntWidth()) / 2;
		int offsetHeight = (extensionHeight - this.bounds.getIntHeight()) / 2;
		
		if(this.basicAttributes.getIntOrientation() == 0) {
			double spaceInput = extensionHeight / (inputs.size() + 1);
			for(int i = 0; i < inputs.size(); i++) {
				Connector c = inputs.get(i);
				c.location.x.setValue(this.location.getIntX() - c.bounds.getIntWidth());
				c.location.y.setValue((int) (this.location.getIntY() + (spaceInput * (i + 1)) + (c.bounds.getIntHeight() / 2)) - offsetHeight);
			}
			double spaceOutput = bounds.getIntHeight() / (outputs.size() + 1);
			for(int i = 0; i < outputs.size(); i++) {
				Connector c = outputs.get(i);
				c.location.x.setValue(this.location.getIntX() + this.bounds.getIntWidth());
				c.location.y.setValue((int) (this.location.getIntY() + (spaceOutput * (i + 1)) - (c.bounds.getIntHeight() / 2)));
			}
		} else if(this.basicAttributes.getIntOrientation() == 1) {
			double spaceInput = extensionWidth / (inputs.size() + 1);
			for(int i = 0; i < inputs.size(); i++) {
				Connector c = inputs.get(i);
				c.location.x.setValue((int) (this.location.getIntX() + (spaceInput * (i + 1)) + (c.bounds.getIntWidth() / 2)) - offsetWidth);
				c.location.y.setValue(this.location.getIntY() - c.bounds.getIntHeight());
			}
			double spaceOutput = bounds.getIntWidth() / (outputs.size() + 1);
			for(int i = 0; i < outputs.size(); i++) {
				Connector c = outputs.get(i);
				c.location.x.setValue((int) (this.location.getIntX() + (spaceOutput * (i + 1)) - (c.bounds.getIntWidth() / 2)));
				c.location.y.setValue(this.location.getIntY() + this.bounds.getIntHeight());
			}
		} else if(this.basicAttributes.getIntOrientation() == 2) {
			double spaceInput = extensionHeight / (inputs.size() + 1);
			for(int i = 0; i < inputs.size(); i++) {
				Connector c = inputs.get(i);
				c.location.x.setValue(this.location.getIntX() + this.bounds.getIntWidth());
				c.location.y.setValue((int) (this.location.getIntY() + (spaceInput * (i + 1)) + (c.bounds.getIntHeight() / 2)) - offsetHeight);
			}
			double spaceOutput = bounds.getIntHeight() / (outputs.size() + 1);
			for(int i = 0; i < outputs.size(); i++) {
				Connector c = outputs.get(i);
				c.location.x.setValue(this.location.getIntX() - c.bounds.getIntWidth());
				c.location.y.setValue((int) (this.location.getIntY() + (spaceOutput * (i + 1)) - (c.bounds.getIntHeight() / 2)));
			}
		} else if(this.basicAttributes.getIntOrientation() == 3) {
			double spaceInput = extensionWidth / (inputs.size() + 1);
			for(int i = 0; i < inputs.size(); i++) {
				Connector c = inputs.get(i);
				c.location.x.setValue((int) (this.location.getIntX() + (spaceInput * (i + 1)) + (c.bounds.getIntWidth() / 2)) - offsetWidth);
				c.location.y.setValue(this.location.getIntY() + this.bounds.getIntHeight());
			}
			double spaceOutput = bounds.getIntWidth() / (outputs.size() + 1);
			for(int i = 0; i < outputs.size(); i++) {
				Connector c = outputs.get(i);
				c.location.x.setValue((int) (this.location.getIntX() + (spaceOutput * (i + 1)) - (c.bounds.getIntWidth() / 2)));
				c.location.y.setValue(this.location.getIntY() - c.bounds.getIntHeight());
			}
		}
		for(Connector c: this.connectors) c.onMoved();
	}
	
	public int getExtansionSize() {
		if(this.inputs.size() < 3) return 0;
		if(this.basicAttributes.getIntOrientation() == 0 || this.basicAttributes.getIntOrientation() == 2) return this.extensionHeight;
		else if(this.basicAttributes.getIntOrientation() == 1 || this.basicAttributes.getIntOrientation() == 3) return this.extensionWidth;
		return 0;
	}
	
	public boolean isConnectorVertical(){
		if(this.basicAttributes.getIntOrientation() == 0 || this.basicAttributes.getIntOrientation() == 2) return true;
		else if(this.basicAttributes.getIntOrientation() == 1 || this.basicAttributes.getIntOrientation() == 3) return false;
		return false;
		
	}
	
	public Location[] getExtansionLocation() {
		Location[] result = new Location[2];
		if(this.basicAttributes.getIntOrientation() == 0) {
			result[0] = new Location(this.location.getIntX(), this.location.getIntY() - (this.getExtansionSize() / 2) + 45);
			result[1] = new Location(this.location.getIntX(), this.location.getIntY() + bounds.getIntHeight() + (this.getExtansionSize() / 2) - 45);
		} else if(this.basicAttributes.getIntOrientation() == 1) {
			result[0] = new Location(this.location.getIntX() - (this.getExtansionSize() / 2) + 45, this.location.getIntY());
			result[1] = new Location(this.location.getIntX() + bounds.getIntWidth() + (this.getExtansionSize() / 2) - 45, this.location.getIntY());
		} else if(this.basicAttributes.getIntOrientation() == 2) {
			result[0] = new Location(this.location.getIntX() + bounds.getIntWidth(), this.location.getIntY() - (this.getExtansionSize() / 2) + 45);
			result[1] = new Location(this.location.getIntX() + bounds.getIntWidth(), this.location.getIntY() + bounds.getIntHeight() + (this.getExtansionSize() / 2) - 45);
		} else if(this.basicAttributes.getIntOrientation() == 3) {
			result[0] = new Location(this.location.getIntX() - (this.getExtansionSize() / 2) + 45, this.location.getIntY() + this.bounds.getIntHeight());
			result[1] = new Location(this.location.getIntX() + bounds.getIntWidth() + (this.getExtansionSize() / 2) - 45, this.location.getIntY() + this.bounds.getIntHeight());
		}
		return result;
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
