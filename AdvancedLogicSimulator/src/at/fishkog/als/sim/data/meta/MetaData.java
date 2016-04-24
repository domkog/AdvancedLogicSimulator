package at.fishkog.als.sim.data.meta;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Stores and manage MetaValue objects.
 * 
 * @author Dominik
 *
 */
public class MetaData implements Cloneable {

	public ArrayList<MetaValue<?>> metaValues;
	
	/**
	 * Creates new MetaData object.
	 */
	public MetaData() {
		metaValues = new ArrayList<>();
	}
	
	/**
	 * Gets all MetaValue objects of this MetaData object with the given id.
	 * 
	 * @param Meta ID
	 * @return List of all found MetaValues with the given ID.
	 */
	public ArrayList<MetaValue<?>> get(String meta) {
		return metaValues.stream().filter((m) -> m.id.equalsIgnoreCase(meta)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * 
	 * Gets the first of all found MetaValue objects of this MetaData object with the given ID.
	 * 
	 * @param Data type
	 * @param Meta ID
	 * @return First found MetaValue object with the given ID.
	 */
	@SuppressWarnings("unchecked")
	public <T> MetaValue<T> getFirst(Class<T> t, String meta) {
		ArrayList<MetaValue<?>> result = metaValues.stream().filter((m) -> m.id.equalsIgnoreCase(meta)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return (MetaValue<T>) result.get(0);
	}
	
	/**
	 * 
	 * Gets all MetaValue object of this MetaData object which starts with the given ID.
	 * 
	 * @param Meta ID to start with
	 * @return List of all found MetaValues which starts with the given ID.
	 */
	public ArrayList<MetaValue<?>> startsWith(String meta) {
		return metaValues.stream().filter((m) -> m.id.startsWith(meta)).collect(Collectors.toCollection(ArrayList::new));
	}
	
	/**
	 * Adds new MetaValue object to this collection.
	 * 
	 * @param MetaValue object
	 */
	public void add(MetaValue<?> meta) {
		metaValues.add(meta);
	}
	
	/**
	 * Adds multiple MetaValue objects to this collection.
	 * 
	 * @param MetaValue objects
	 */
	public void add(MetaValue<?>... meta) {
		for(int i = 0; i < meta.length; i++) metaValues.add(meta[i]);
	}
	
	/**
	 * Adds multiple MetaValue objects to this collection.
	 * 
	 * @param MetaValue objects
	 */
	public void add(ArrayList<MetaValue<?>> meta) {
		metaValues.addAll(meta);
	}
	
	/**
	 * Adds all MetaValue objects of the given MetaData to this collection.
	 * 
	 * @param MetaData object
	 */
	public void add(MetaData data) {
		metaValues.addAll(data.metaValues);
	}
	
	/**
	 * Adds all MetaValue objects of the given MetaData to this Collection.
	 * 
	 * @param MetaData objects
	 */
	public void add(MetaData... data) {
		for(int i = 0; i < data.length; i++) this.add(data[i]);
	}
	
	/**
	 * Removes MetaValue object from this collection.
	 * 
	 * @param MetaValue object
	 */
	public void remove(MetaValue<?> meta) {
		metaValues.add(meta);
	}

	/**
	 * 
	 * @param Meta ID
	 * @return Returns true if the given MetaValue is added in this collection.
	 */
	public boolean contains(String id) {
		return !this.get(id).isEmpty();
	}
	
	/**
	 * 
	 * @param MetaValue object
	 * @return Returns true if the given MetaValue is added in this collection.
	 */
	public boolean contains(MetaValue<?> meta) {
		return this.metaValues.contains(meta);
	}
	
	/**
	 * Creates an exact copy of this MetaData object within its MetaValues.
	 */
	@Override
	public MetaData clone() {
		MetaData meta = new MetaData();
		for(MetaValue<?> value: this.metaValues) {
			meta.add(value.clone());
		}
		return meta;
	}
	
}
