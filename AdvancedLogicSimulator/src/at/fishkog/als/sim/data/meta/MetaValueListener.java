package at.fishkog.als.sim.data.meta;

public interface MetaValueListener<T> {

	/**
	 * This method got automatically called when the value of an MetaValue object got changed.
	 * 
	 * @param Old value
	 * @param New value
	 */
	public void change(T from, T to);
	
}
