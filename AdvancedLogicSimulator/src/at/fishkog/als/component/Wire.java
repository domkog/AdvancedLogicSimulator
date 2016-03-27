package at.fishkog.als.component;

import java.util.HashMap;

import at.fishkog.als.data.Connector;
import at.fishkog.als.data.Data;
import at.fishkog.als.data.WireStatus;
import at.fishkog.als.data.Connector.State;
import at.fishkog.als.data.meta.MetaValue;

public class Wire extends Data implements Processable {

	public MetaValue<WireStatus.Status> wireStatus;

	public Connector c1, c2;
	
	public Wire(Connector c1, Connector c2) {
		this.c1 = c1;
		this.c2 = c2;
	}
	
	@Override
	public void process() {
		if(c1.state.value == State.TRUE || c2.state.value == State.TRUE) {
			c1.state.setValue(State.TRUE);
			c2.state.setValue(State.TRUE);
		} else {
			c1.state.setValue(State.FALSE);
			c2.state.setValue(State.FALSE);
		}
	}
	
	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("status", wireStatus);
		return result;
	}

}