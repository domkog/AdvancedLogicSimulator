package at.fishkog.als.sim.data;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import javafx.scene.paint.Color;

public enum State {
	TRUE, FALSE, NULL, UNKNOWN, ERROR, INVISIBLE;

	protected PropertiesManager wireConfig = AdvancedLogicSimulator.wireConfig;
	public Color getColor() {
		double r, g, b, op;
		
		r = Integer.valueOf(this.wireConfig.get(this.name() + "_R"));
		g = Integer.valueOf(this.wireConfig.get(this.name() + "_R"));
		b = Integer.valueOf(this.wireConfig.get(this.name() + "_R"));
		op = Integer.valueOf(this.wireConfig.get(this.name() + "_OP"));
		
		return new Color(r,g,b, op);
	}
	public boolean isTrue() {
		return this.name() == "TRUE";
		
	}
	
	public boolean isFalse(){
		return this.name() == "FALSE";
		
	}
	
	public boolean isValid() {
		return !(this.isFalse() || this.isTrue());
		
	}
	
	public State negate(){
		if(this.name() == "TRUE") {
			return State.FALSE;
			
		} else if(this.name() == "FALSE") {
			return State.TRUE;
			
		} else {
			return this;
			
		}
	}
}	
