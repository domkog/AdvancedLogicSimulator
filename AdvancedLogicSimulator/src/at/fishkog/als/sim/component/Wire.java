package at.fishkog.als.sim.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.meta.MetaValue;

public class Wire extends Data implements Processable {	
	public Connector cOut;
	public ArrayList<Connector> cIns;
	
	private MetaValue<Data.State> state;
	
	public boolean isMultiBit;
	
	public Wire(Connector cOut) {
		this.cOut = cOut;

	}
	
	public void onMoved() {
		
	}
	
	//TODO Do all the update shit here
	public void update() {
		
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
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		for(int i=0; i<this.bitwidth.getWidth(); i++) {
			result.put("state." + i, cOut.states.get(i));
		}
		return result;
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

}