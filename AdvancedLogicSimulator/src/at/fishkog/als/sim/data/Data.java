package at.fishkog.als.sim.data;

import java.util.ArrayList;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.scene.paint.Color;

public abstract class Data {
		
	public abstract ArrayList<MetaValue<?>> getMetaValues();
	
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
		
	}
	
	
	
	public BitWidth bitwidth;
	
}
