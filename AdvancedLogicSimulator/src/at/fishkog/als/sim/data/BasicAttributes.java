package at.fishkog.als.sim.data;

import java.util.HashMap;

import at.fishkog.als.sim.data.meta.MetaValue;

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
		this.id = new MetaValue<Integer>(id);
		this.category = new MetaValue<Integer>(category);
		this.orientation = new MetaValue<Integer>(orientation);
		this.descPos = new MetaValue<Integer>(descPos);
	
		this.name = new MetaValue<String>(name);
		this.desc = new MetaValue<String>(desc);
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
	
	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("id", this.id);
		result.put("category", this.category);
		result.put("name", this.name);
		result.put("orientation", this.orientation);
		result.put("desc", this.desc);
		result.put("descPos", this.descPos);
		return result;
	}
	
	
	
}
