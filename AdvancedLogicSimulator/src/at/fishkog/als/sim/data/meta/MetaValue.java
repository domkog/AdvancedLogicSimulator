package at.fishkog.als.sim.data.meta;

import java.util.ArrayList;

/**
 * 
 * @author Dominik
 *
 * @param <T> Type of this meta object.
 */
public class MetaValue<T> implements Cloneable {

	public enum MetaType {
		BOOLEAN, BYTE, SHORT, INTEGER, LONG, DOUBLE, STRING, ENUM;
		
		public static MetaType getMetaType(Object o) throws MetaDataTypeException {
			if(o instanceof Boolean) return BOOLEAN;
			if(o instanceof Byte) return BYTE;
			if(o instanceof Short) return SHORT;
			if(o instanceof Integer) return INTEGER;
			if(o instanceof Long) return LONG;
			if(o instanceof Double) return DOUBLE;
			if(o instanceof String) return STRING;
			if(o instanceof Enum) return ENUM;
			throw new MetaDataTypeException("Unkown MetaValue type! (" + o + ")");
		}
	}
	
	/**
	 * 
	 * User access level for MetaValues
	 */
	public enum MetaAccess {
		READ_WRITE, READ, HIDDEN;
	}
	
	private ArrayList<MetaValueListener<T>> listeners;
	
	public String id;
	
	public MetaType type;
	private T value;
	
	public MetaAccess access;
	
	/**
	 * Creates new MetaValue instance.
	 * MetaAccess will be set to READ_WRITE by default.
	 * 
	 * @param Meta id
	 * @param Meta value
	 */
	public MetaValue(String id, T value) {
		this.id = id;
		listeners = new ArrayList<>();
		this.value = value;
		this.access = MetaAccess.READ_WRITE;
		try {
			this.type = MetaType.getMetaType(value);
		} catch (MetaDataTypeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates new MetaValue instance.
	 * 
	 * @param Meta id
	 * @param Meta value
	 * @param User access level
	 */	
	public MetaValue(String id, T value, MetaAccess access) {
		this.id = id;
		listeners = new ArrayList<>();
		this.value = value;
		this.access = access;
		try {
			this.type = MetaType.getMetaType(value);
		} catch (MetaDataTypeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets the value for this meta object.
	 * Automatically calls all registered change listeners.
	 * 
	 * @param New value
	 */
	public void setValue(T value) {
		if(!listeners.isEmpty()) {
			listeners.forEach((listener) -> listener.change(this.value, value));
		}
		this.value = value;
	}
	
	/**
	 * Sets the value for this meta object.
	 * Didn't calls change listeners.
	 * 
	 * This method should only be used for actions not caused by the user.
	 * 
	 * @param New value
	 */
	public void setValueSilent(T value) {
		this.value = value;
	}
	
	/**
	 * Gets the current value of this Meta object
	 * 
	 * @return Current value
	 */
	public T getValue() {
		return value;
	}
	
	
	/**
	 * 
	 * Register new change listener which one get called when the value changes.
	 * 
	 * @param listener
	 */
	public void addListener(MetaValueListener<T> listener) {
		if(!listeners.contains(listener)) listeners.add(listener);
	}
	
	/**
	 * Unregister change listener.
	 * 
	 * @param listener
	 */
	public void removeListener(MetaValueListener<T> listener) {
		if(listeners.contains(listener)) listeners.remove(listener);
	}
	
	/**
	 * 
	 * @return Exact copy of this object
	 */
	@Override
	public MetaValue<T> clone() {
		MetaValue<T> meta = new MetaValue<T>(this.id, this.value);
		if(!listeners.isEmpty()) listeners.forEach((listener) -> meta.addListener(listener));
		return meta;
	}
	
}
