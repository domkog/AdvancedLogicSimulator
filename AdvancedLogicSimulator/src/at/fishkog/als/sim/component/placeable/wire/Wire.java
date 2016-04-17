package at.fishkog.als.sim.component.placeable.wire;

import java.util.ArrayList;
import java.util.stream.Collectors;

import at.fishkog.als.sim.component.Processable;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.scene.paint.Color;

public class Wire extends Data implements Processable {	
	public Connector cOut;
	public ArrayList<Connector> cIns;
	
	private final MetaValue<Data.State> state;
	private final MetaValue<Integer> length;
	private final MetaValue<Boolean> isHorizontal;
	
	private Location start;
	private Location end;
	
	public boolean isMultiBit;
	
	public Wire(Connector cOut, int leangth, boolean isHorizontal) {
		this.start = cOut.location;
		this.isHorizontal = new MetaValue<Boolean>("isHorizontal", isHorizontal);
		
		if(isHorizontal) {
			this.end = new Location(cOut.location.getIntX() + leangth, cOut.location.getIntY());
			
		} else {
			this.end = new Location(cOut.location.getIntX(), cOut.location.getIntY() + leangth);
			
		}
		
		this.length = new MetaValue<Integer>("length", Math.abs(this.end.getIntX() - this.start.getIntX()) + Math.abs(this.start.getIntY() - this.end.getIntX()));
		this.state = new MetaValue<Data.State>("state", State.UNKNOWN);
		
	}
	
	public void setOutput(Connector c) {
		cOut = c;
		
	}
	
	public void addInput(Connector c) {
		cIns.add(c);
		
	}

	public void removeConnector(Connector c) {
		if(c == cOut) cOut = null;
		else removeInput(c);
		update();
	}
	
	public void removeInput(Connector c) {
		if(cIns.contains(c)) {
			cIns.remove(c);
			
		}
	}
	
	public Connector getInputs(String id) {
		ArrayList<Connector> result = cIns.stream().filter((Connector c) -> c.id.value.equalsIgnoreCase(id)).collect(Collectors.toCollection(ArrayList::new));
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
	
	//TODO
	private boolean overlaps(Location loc1, Location loc2) {
		return true;
		
	}
	
	
	public void onMoved() {
		
		
	}
	
	//TODO Do all the update shit here
	public void update() {
		
		
	}
	
	public Color getColor(){
		return this.state.getValue().getColor();
		
	}
	
	@Override
	public void process() {
		for(Connector cIn : cIns) {
			for(int i=0; i<this.bitwidth.getWidth(); i++) {
				cIn.states.get(i).setValue(cOut.states.get(i).value);
			}
		}
		if(this.bitwidth.getWidth() == 1){
			this.state.setValue(cOut.states.get(0).value);
		} else {
			this.state.setValue(Data.State.INVISIBLE);
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
	
	@Override
	public ArrayList<MetaValue<?>> getMetaValues() {
		ArrayList<MetaValue<?>> result = new ArrayList<MetaValue<?>>();
		result.add(start.x);
		result.add(start.y);
		result.add(end.x);
		result.add(end.y);
		
		for(int i=0; i<this.bitwidth.getWidth(); i++) {
			result.add(cOut.states.get(i));
			
		}

		return result;
	}

}