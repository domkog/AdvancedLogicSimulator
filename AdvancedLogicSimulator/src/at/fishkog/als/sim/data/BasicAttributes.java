package at.fishkog.als.sim.data;

import at.fishkog.als.sim.data.meta.MetaValue;
import at.fishkog.als.sim.data.meta.MetaValue.MetaAccess;

public class BasicAttributes extends Data{
	
	private final MetaValue<Integer> id, category, orientation, descPos;
	private final MetaValue<String> name, desc;

	/*
	 * id: int
	 * category: int 
	 * name: String 
	 * orientation: int 0 EAST, 1 SOUTH, 2 WEST, 3 NORTH
	 * desc: String Description displayed near the object
	 * desc-pos: int position of the Description relative to the object (same as orientation)
	 */
	public BasicAttributes(int id, int category, String name, int orientation, String desc, int descPos) {
		super();
		
		this.id = new MetaValue<Integer>("ID", id, MetaAccess.HIDDEN);
		this.category = new MetaValue<Integer>("Category", category, MetaAccess.HIDDEN);
		this.orientation = new MetaValue<Integer>("Orientation", orientation);
		this.descPos = new MetaValue<Integer>("DescPos", descPos);
	
		this.name = new MetaValue<String>("Name", name);
		this.desc = new MetaValue<String>("Desc", desc);
		
		this.metaData.add(this.id, this.category, this.name, this.orientation, this.desc, this.descPos);
	}
	
	public BasicAttributes(int id, int category, String name) {
		this(id, category, name, 0, "", 0);
	}
	
	public MetaValue<Integer> getId(){
		return id;
	}
	
	public int getIntId(){
		return id.getValue();		
	}
	
	public MetaValue<Integer> getCategory() {
		return category;
	}
	
	public int getIntCategory(){
		return category.getValue();
	}
	
	public MetaValue<String> getDesc() {
		return desc;
	}
	
	public String getStringDesc(){
		return desc.getValue();
	}
	
	public MetaValue<String> getName() {
		return name;
	}
	
	public String getStringName(){
		return name.getValue();
	}
	
	public MetaValue<Integer> getDescPos() {
		return descPos;
	}
	
	public int getIntDescPos(){
		return descPos.getValue();
	}
	
	public MetaValue<Integer> getOrientation() {
		return orientation;
	}
	
	public int getIntOrientation() {
		return orientation.getValue();
		
	}
	
	public void getNextRotation(boolean clockwise) {
		orientation.setValue(orientation.getValue()+1);
		
	}

}
