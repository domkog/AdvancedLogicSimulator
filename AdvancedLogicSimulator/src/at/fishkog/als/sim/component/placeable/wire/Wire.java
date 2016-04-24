package at.fishkog.als.sim.component.placeable.wire;

import java.util.ArrayList;
import java.util.stream.Collectors;

import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.placeable.logic.ANDComponent;
import at.fishkog.als.sim.data.BitWidth;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Connector.Type;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.sim.data.State;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public class Wire extends Component implements Processable {	

	private final MetaValue<Integer> length;
	private final MetaValue<Boolean> isHorizontal;
	
	private Location start;
	private Location end;
	
	public boolean isMultiBit;
	
	private Connector cOut;
	
	public Wire(int id, Connector cOut, int leangth, boolean isHorizontal) {
		super(id, Categories.WIRES, l.getString("Wire"));
		this.start = cOut.location;
		this.isHorizontal = new MetaValue<Boolean>("isHorizontal", isHorizontal);
		
		this.cOut = cOut;
		this.outputs.add(cOut);
		
		if(isHorizontal) {
			this.end = new Location(cOut.location.getIntX() + leangth, cOut.location.getIntY());
			
		} else {
			this.end = new Location(cOut.location.getIntX(), cOut.location.getIntY() + leangth);
			
		}
		
		this.length = new MetaValue<Integer>("length", Math.abs(this.end.getIntX() - this.start.getIntX()) + Math.abs(this.start.getIntY() - this.end.getIntX()));
		this.state = new MetaValue<State>("state", State.UNKNOWN);
		
		this.metaData.add(this.start.getMetaData(), this.end.getMetaData());
		if(bitwidth != null) {
			for(int i=0; i<this.bitwidth.getWidth(); i++) {
				this.metaData.add(cOut.states.get(i));
			}
		}
	}
	
	/**
	 * 
	 * THIS IS ONLY FOR REGISTRATION PURPOSE, NEVER USE THIS!
	 * 
	 */
	public Wire() {
		this(0,new Connector(new ANDComponent(0, 0, 0, 0),"",Type.INPUT,0,0,new BitWidth(),false),0, false);

	}
	
	public void setOutput(Connector c) {
		cOut = c;
		
	}
	
	public void addInput(Connector c) {
		inputs.add(c);
		
	}

	public void removeConnector(Connector c) {
		if(c == cOut) cOut = null;
		else removeInput(c);
		update();
	}
	
	public void removeInput(Connector c) {
		if(inputs.contains(c)) {
			inputs.remove(c);
			
		}
	}
	
	public Connector getInputs(String id) {
		ArrayList<Connector> result = inputs.stream().filter((Connector c) -> c.id.getValue().equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
		
	}

	public int getIntLength() {
		return this.length.getValue();
		
	}
	
	public MetaValue<Integer> getLength() {
		return length;
	
	}
	
	public Location getStart() {
		return start;
		
	}
	
	public Location getEnd() {
		return end;
		
	}
	
	public boolean isHorizontal() {
		return this.isHorizontal.getValue();
		
	}
	
	public boolean hasSameEnd(Wire wire) {
		return (wire.end.equals(this.end) || wire.end.equals(this.start) || wire.start.equals(this.end) || wire.start.equals(this.start));
		
	}
	
	public boolean overlaps(Wire wire) {
		return overlaps(wire.start, wire.end);
		
	}
	
	//TODO return if it overlaps with another wire
	private boolean overlaps(Location loc1, Location loc2) {
		return true;
		
	}
	
	
	public void onMoved() {
		
		
	}
	
	public void update() {
		
		
	}
	
	public Color getColor(){
		return this.state.getValue().getColor();
		
	}
	
	@Override
	public void process() {
		for(Connector cIn : inputs) {
			for(int i=0; i<this.bitwidth.getWidth(); i++) {
				cIn.states.get(i).setValue(cOut.states.get(i).getValue());
			}
		}
		if(this.bitwidth.getWidth() == 1){
			this.state.setValue(cOut.states.get(0).getValue());
		} else {
			this.state.setValue(State.INVISIBLE);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Wire) {
			Wire extWire = (Wire)obj;
			return (extWire.end.equals(this.end) && extWire.start.equals(this.start) || extWire.start.equals(this.end) && extWire.end.equals(this.start));
			
		} else {
			return false;
			
		}
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public Group getRenderparts() {
		return null;
		
	}

}