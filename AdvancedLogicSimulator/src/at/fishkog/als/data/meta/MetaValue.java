package at.fishkog.als.data.meta;

import java.util.ArrayList;

public class MetaValue<T> {

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
	
	private ArrayList<MetaValueListener<T>> listeners;
	
	public MetaType type;
	public T value;
	
	public MetaValue(T value) {
		listeners = new ArrayList<>();
		this.value = value;
		try {
			this.type = MetaType.getMetaType(value);
		} catch (MetaDataTypeException e) {
			e.printStackTrace();
		}
	}
	
	public void setValue(T value) {
		if(!listeners.isEmpty()) {
			listeners.forEach((listener) -> listener.change(this.value, value));
		}
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public void addListener(MetaValueListener<T> listener) {
		if(!listeners.contains(listener)) listeners.add(listener);
	}
	
	public void removeListener(MetaValueListener<T> listener) {
		if(listeners.contains(listener)) listeners.remove(listener);
	}
	
}
