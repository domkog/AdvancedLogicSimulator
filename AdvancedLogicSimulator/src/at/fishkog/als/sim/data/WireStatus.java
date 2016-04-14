package at.fishkog.als.sim.data;

import javafx.scene.paint.Color;

public class WireStatus {
	//TODO Get real color codes from config
	public static final Color TRUE_COLOR = Color.GREEN;
	public static final Color FALSE_COLOR = Color.GRAY;
	public static final Color NULL_COLOR = Color.BLUE;
	public static final Color ERROR_COLOR = Color.RED;
	public static final Color UNKNOWN_COLOR = Color.BLUE;
	
	public enum Status {
		TRUE, FALSE, UNKOWN, ERROR, NULL;
	
		public Color getColor() throws Exception{
			if(this == TRUE) return TRUE_COLOR;
			if(this == FALSE) return FALSE_COLOR;
			if(this == NULL) return NULL_COLOR;
			if(this == ERROR) return ERROR_COLOR;
			if(this == UNKOWN) return UNKNOWN_COLOR;
			
			throw new Exception("Unkown Enum Status! (" + this.name() + ")");
		}
	}
}
