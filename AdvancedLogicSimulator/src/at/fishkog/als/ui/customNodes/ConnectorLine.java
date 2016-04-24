package at.fishkog.als.ui.customNodes;

import at.fishkog.als.sim.data.Connector;
import javafx.scene.shape.Line;

public class ConnectorLine extends Line{
	private Connector con;
	public ConnectorLine(double d, double e, double f, double g, Connector c) {
		super(d,e,f,g);
		this.con = c;
	}
	
	public Connector getCon() {
		return con;
	}
}
