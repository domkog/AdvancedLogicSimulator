package at.fishkog.als.sim.data;


import at.fishkog.als.sim.data.meta.MetaValue;

public class Bounds extends Data{

	public MetaValue<Integer> width, height;
	
	public static Bounds EMPTY_BOUNDS = new Bounds(0,0);

	public Bounds(int width, int height) {
		super();
		
		this.width = new MetaValue<Integer>("Width", width);
		this.height = new MetaValue<Integer>("Height", height);
		
		this.metaData.add(this.width, this.height);
	}
	
	public MetaValue<Integer> getWidth() {
		return width;
	}

	public int getIntWidth() {
		return width.getValue();
	}

	public MetaValue<Integer> getHeight() {
		return height;
	}
	
	public int getIntHeight() {
		return height.getValue();
	}

	public boolean isInside(int x, int y, int rectX, int rectY, int width, int height) {
		Location[] corners = new Location[]{
			new Location(x, y),
			new Location(x + this.getIntWidth(), y),
			new Location(x, y + this.getIntHeight()),
			new Location(x + this.getIntWidth(), y + this.getIntHeight())
		};
		for(Location loc: corners) {
			if(loc.isInside(rectX, rectY, width, height)) return true;
		}
		return false;
	}
	
}
