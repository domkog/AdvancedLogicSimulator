package at.fishkog.als.data;

import java.util.HashMap;

import at.fishkog.als.data.meta.MetaValue;

public abstract class Data {

	public abstract HashMap<String, MetaValue<?>> getMetaValues();
	
}
