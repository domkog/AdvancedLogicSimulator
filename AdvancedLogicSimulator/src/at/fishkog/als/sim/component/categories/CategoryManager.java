package at.fishkog.als.sim.component.categories;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CategoryManager {

	private ArrayList<ComponentCategory> categories;
	
	public CategoryManager() {
		categories = new ArrayList<>();
	}
	
	public ArrayList<ComponentCategory> getCategories() {
		return this.categories;
	}
	
	public ComponentCategory getCategory(String name) {
		ArrayList<ComponentCategory> result = categories.stream().filter((category) -> category.name.equalsIgnoreCase(name)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
	public void addCategory(ComponentCategory category) {
		if(getCategory(category.name) != null) throw new CategoryRegistrationException("Theres always an ComponentCategory registered with this ID! (" + category.getName() + ")");
		categories.add(category);
	}
	
	public void removeCategory(ComponentCategory category) {
		if(getCategory(category.name) != null) categories.remove(category);
	}
	
	public void removeCategory(String name) {
		ComponentCategory category = this.getCategory(name);
		if(category != null) this.categories.remove(category);
	}
	
}
