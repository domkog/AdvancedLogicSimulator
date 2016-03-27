package at.fishkog.als.component.rendering;

import javafx.scene.image.Image;

public class RenderContext {

	private String path;
	private Image img;
	
	public RenderContext(String path) {
		this.path = path;
	}
	
	public Image getImage() {
		if(img == null) {
			img = new Image(path);
		}
		return img;
	}
	
}
