package at.fishkog.als.sim.data;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.data.meta.MetaData;

public class Data {
	
	public MetaData metaData;	
	public static LanguageManager l;
	
	public Data() {
		this.metaData = new MetaData();
		Data.l = AdvancedLogicSimulator.lang;
	}
	
	public MetaData getMetaData() {
		return this.metaData;
		
	}
	
	
}
