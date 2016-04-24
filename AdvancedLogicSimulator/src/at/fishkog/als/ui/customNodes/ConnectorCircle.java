package at.fishkog.als.ui.customNodes;

import at.fishkog.als.sim.data.Connector;
import javafx.scene.shape.Circle;

public class ConnectorCircle extends Circle{
	private Connector con;
	
	public ConnectorCircle(double d, double e, int intWidth, Connector c) {
		super(d,e,intWidth);
		this.con = c;
	}

	public Connector getCon() {
		return con;
	}	
}
