package at.fishkog.als.sim.data;

import at.fishkog.als.sim.data.meta.MetaValue;

public class Location extends Data {

	public final MetaValue<Integer> x, y;
	
	public Location (int x, int y) {
		super();
		
		this.x = new MetaValue<Integer>("X", x);
		this.y = new MetaValue<Integer>("Y", y);
		
		this.metaData.add(this.x, this.y);
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
		return (this.x.getValue() >= x && this.x.getValue() <= (x + width)) && (this.y.getValue() >= y && this.y.getValue() <= (y + height));
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
