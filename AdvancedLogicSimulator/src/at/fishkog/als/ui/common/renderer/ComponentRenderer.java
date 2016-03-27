package at.fishkog.als.ui.common.renderer;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.component.Component;
import at.fishkog.als.ui.common.MainUI;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ComponentRenderer {

	private MainUI mainUI;
	private Canvas canvas, background;
	
	private Color hovered = Color.rgb((int) (Color.AQUAMARINE.getRed() * 255), (int) (Color.AQUAMARINE.getGreen() * 255), (int) (Color.AQUAMARINE.getBlue() * 255), 0.5);
	private Color selected = Color.rgb((int) (Color.AQUA.getRed() * 255), (int) (Color.AQUA.getGreen() * 255), (int) (Color.AQUA.getBlue() * 255), 0.33);
	
	public ComponentRenderer(MainUI mainUI, Canvas background, Canvas canvas) {
		this.mainUI = mainUI;
		this.canvas = canvas;
		this.background = background;
	}
	
	public void repaint() {
		this.drawComponents();
		this.drawBackground();
	}
	
	public void drawComponents() {
		final GraphicsContext gc2 = canvas.getGraphicsContext2D();
		gc2.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		gc2.setStroke(Color.BLACK);
		for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
			if(!c.isVisible((int) mainUI.inputHandler.offsetX,(int) mainUI.inputHandler.offsetY,(int) this.canvas.getWidth(),(int) this.canvas.getHeight())) {
				System.out.println("Not Visible");
				continue;
			}
			if(c.hasTexture()) {
				gc2.drawImage(c.renderContext.getImage(), c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY);
			} else {
				gc2.strokeRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
			}
			
			if((mainUI.inputHandler.selected != null && mainUI.inputHandler.selected == c) || 
					mainUI.inputHandler.dragged != null && mainUI.inputHandler.dragged == c) {
				gc2.setFill(selected);
				gc2.fillRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
			} else if(mainUI.inputHandler.hoveredComponent != null && mainUI.inputHandler.hoveredComponent == c ) {
				gc2.setFill(hovered);
				gc2.fillRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
			}
		}
	}
	
	public void drawBackground() {
		final GraphicsContext gc1 = background.getGraphicsContext2D();
		gc1.clearRect(0, 0, background.getWidth(), background.getHeight());
		 
		gc1.setFill(Color.BLACK);
		gc1.setLineWidth(0.075);
		gc1.strokeRect(0, 0, background.getWidth(), background.getHeight());
		
		double x = 0, y = 0;
		if(mainUI.inputHandler.offsetX % 25 != 0) x = mainUI.inputHandler.offsetX % 25 - 25;
		if(mainUI.inputHandler.offsetY % 25 != 0) y = mainUI.inputHandler.offsetY % 25 - 25;
		for(int i = 0; i < (background.getWidth() / 25) ;i++) {
			gc1.strokeLine(i * 25 + x, y, i * 25 + x, background.getHeight() + y);
		}
		for(int i = 0; i < (background.getHeight() / 25) ;i++) {
			gc1.strokeLine(x, i * 25 + y, background.getWidth() + x, i * 25 + y);
		}
	}
	
}
