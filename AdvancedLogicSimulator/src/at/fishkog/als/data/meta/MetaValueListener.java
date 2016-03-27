package at.fishkog.als.data.meta;

public interface MetaValueListener<T> {

	public void change(T from, T to);
	
}
