package at.fishkog.als.sim.data;

import at.fishkog.als.AdvancedLogicSimulator;

public class BitWidth {
	
	public static final BitWidth UNKNOWN = new BitWidth(0);
	public static final BitWidth ONE = new BitWidth(1);
	
	private int width;
	private final int maxWidth;
	
	public BitWidth(int width) {
		this.width = width;
		this.maxWidth = Integer.parseInt(AdvancedLogicSimulator.config.get("maxBit"));
	}
	
	public BitWidth() {
		this(0);
	}
	
	public void setWidth(int width) {
		this.width = Integer.min(this.maxWidth, width);
		
	}
	
	public int getWidth() {
		return width;
	}
	

	public int getMaxWidth() {
		return maxWidth;
	}
}
