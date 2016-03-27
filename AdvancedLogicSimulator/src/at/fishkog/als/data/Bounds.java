package at.fishkog.als.data;

import java.util.HashMap;

import at.fishkog.als.data.meta.MetaValue;

public class Bounds extends Data{

	public MetaValue<Integer> width, height;
	
	public static Bounds EMPTY_BOUNDS = new Bounds(0,0);

	public Bounds(int width, int height) {
		this.width = new MetaValue<Integer>(width);
		this.height = new MetaValue<Integer>(height);
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

	public boolean intersects(int x, int y, int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width.value && mouseY >= y && mouseY <= y + height.value;
	}
	
	//Fix this not working
	public boolean isVisible(int x, int y, int offsetX, int offsetY, int screenWidth, int screenHeight) {
		return true;
		//return ((x + width.value) > offsetX && x < (offsetX + screenWidth)) || ((y + height.value) > offsetY && y < (offsetY + screenHeight)); 
	}
	
	@Override
	public HashMap<String, MetaValue<?>> getMetaValues() {
		HashMap<String, MetaValue<?>> result = new HashMap<String, MetaValue<?>>();
		result.put("height", this.height);
		result.put("width", this.width);
		return result;
		
	}
}
