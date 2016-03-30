package at.fishkog.als.data;

import java.util.HashMap;

import at.fishkog.als.component.Wire;
import at.fishkog.als.data.meta.MetaValue;
import at.fishkog.als.data.meta.MetaValue.MetaAccess;

public class Connector extends Data {

	public enum Type {
		INPUT, OUTPUT
	}
	
	public enum State {
		TRUE, FALSE, NULL, UNKOWN, ERROR;
	}
	
	public MetaValue<String> id;
	public MetaValue<Type> type;
	public MetaValue<State> state;
	
	public Location location;
	public Bounds bounds;
	
	public Wire connectedWire;
	
	public Connector(String id, Type type, int x, int y) {
		this.id = new MetaValue<String>(id);
		this.type = new MetaValue<Type>(type);
		this.state = new MetaValue<State>(State.FALSE);
		location = new Location(x, y);
		bounds = new Bounds(5, 5);
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
		this.connectedWire = null;
	}
	
	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("id", id);
		result.put("type", type);
		result.put("state", state);
		result.putAll(location.getMetaValues());
		result.putAll(bounds.getMetaValues());
		return result;
	}

}
