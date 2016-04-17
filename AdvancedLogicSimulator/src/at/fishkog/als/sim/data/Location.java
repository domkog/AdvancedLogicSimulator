package at.fishkog.als.sim.data;

import java.util.ArrayList;

import at.fishkog.als.sim.data.meta.MetaValue;

public class Location extends Data {

	public final MetaValue<Integer> x, y;
	
	public Location (int x, int y) {
		this.x = new MetaValue<Integer>("X", x);
		this.y = new MetaValue<Integer>("Y", y);
		
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

	public boolean isInside(int x, int y, int width, int height) {
		if(width < 0) {
			width *= -1;
			x -= width;
		}
		if(height < 0) {
			height *= -1;
			y -= height;
		}
		return (this.x.value >= x && this.x.value <= (x + width)) && (this.y.value >= y && this.y.value <= (y + height));
	}
	
	@Override
	public ArrayList<MetaValue<?>> getMetaValues() {
		ArrayList<MetaValue<?>> result = new ArrayList<MetaValue<?>>();
		result.add(x);
		result.add(y);
		return result;
		
	}	
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Location) {
			Location extLoc = (Location) obj;
			
			return(extLoc.getIntX() == this.getIntX() && extLoc.getIntY() == this.getIntY());
			
		} else {
			return false;
			
		}
		
	}
}
