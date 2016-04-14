package at.fishkog.als.sim.data.meta;

public interface MetaValueListener<T> {

	public void change(T from, T to);
	
}
