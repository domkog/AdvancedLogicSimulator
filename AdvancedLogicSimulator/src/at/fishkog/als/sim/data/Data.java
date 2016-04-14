package at.fishkog.als.sim.data;

import java.util.HashMap;

import at.fishkog.als.sim.data.meta.MetaValue;

public abstract class Data {

	public abstract HashMap<String, MetaValue<?>> getMetaValues();
	
	public enum State {
		TRUE, FALSE, NULL, UNKOWN, ERROR, INVISIBLE;
	}
	
	public BitWidth bitwidth;
	
}
