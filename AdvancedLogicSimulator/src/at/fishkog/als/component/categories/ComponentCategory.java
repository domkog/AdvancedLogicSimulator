package at.fishkog.als.component.categories;

import java.util.ArrayList;

import at.fishkog.als.component.Component;

public class ComponentCategory {

	public String name;
	public int id;
	public ArrayList<Component> components;
	
	public ComponentCategory(String name, int id) {
		this.name = name;
		this.id = id;
		this.components = new ArrayList<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getID() {
		return this.id;
	}
	
}
