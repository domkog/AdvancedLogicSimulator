package at.fishkog.als.sim.component.categories;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;

public class Categories {
	
	private static LanguageManager l = AdvancedLogicSimulator.lang;
	
	public static ComponentCategory WIRES = new ComponentCategory(l.getString("Wires"), 0);
	public static ComponentCategory GATES = new ComponentCategory(l.getString("Gates"), 1);
	public static ComponentCategory LOGIC = new ComponentCategory(l.getString("Logic"), 2);
	public static ComponentCategory IO = new ComponentCategory(l.getString("IO"), 3);
	public static ComponentCategory STORAGE = new ComponentCategory(l.getString("Storage"), 4);
	public static ComponentCategory INPUT = new ComponentCategory(l.getString("Input"), 5);
	
}
