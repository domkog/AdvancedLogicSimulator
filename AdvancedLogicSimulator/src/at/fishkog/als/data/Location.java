package at.fishkog.als.data;

import java.util.HashMap;

import at.fishkog.als.data.meta.MetaValue;

public class Location extends Data {

	public final MetaValue<Integer> x, y;
	
	public Location (int x, int y) {
		this.x = new MetaValue<Integer>(x);
		this.y = new MetaValue<Integer>(y);
		
	}
	
	/*
	 * returns the GateMetaValue X -coordinate of the object 
	 */
	public MetaValue<Integer> getX() {
		return this.x;
		
	}
	
	/*
	 * returns the x-coordinate of the object 
	 */
	public int getIntX() {
		return this.x.getValue();
	}
	
	/*
	 * returns the GateMetaValue Y -coordinate of the object 
	 */
	public MetaValue<Integer> getY() {
		return this.y;
	
	}
	/*
	 * returns the x-coordinate of the object
	 */
	
	public int getIntY() {
		return this.y.getValue();
	}

	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("x", x);
		result.put("y", y);
		return result;
		
	}
	
}
