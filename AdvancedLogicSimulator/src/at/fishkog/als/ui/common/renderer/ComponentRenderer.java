package at.fishkog.als.ui.common.renderer;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.ui.common.MainUI;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

public class ComponentRenderer {

	private MainUI mainUI;
	private Canvas canvas, background;
	
	private Color hovered = Color.rgb((int) (Color.GRAY.getRed() * 255), (int) (Color.GRAY.getGreen() * 255), (int) (Color.GRAY.getBlue() * 255), 0.33);
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
				drawRotatedImage(gc2, c.renderContext.getImage(), (90 * c.basicAttributes.getOrientation().value), c.renderLocation.getIntX() + mainUI.inputHandler.offsetX, c.renderLocation.getIntY() + mainUI.inputHandler.offsetY);
			} else {
				gc2.strokeRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
			}
			
			for(Connector con: c.connectors) {
				if (con.isNegated) {
					gc2.strokeOval(con.location.getIntX() + mainUI.inputHandler.offsetX, con.location.getIntY() + mainUI.inputHandler.offsetY, con.bounds.getIntWidth(), con.bounds.getIntHeight());
					if(mainUI.inputHandler.hoveredConnector == con) {
						gc2.setFill(selected);
						gc2.fillOval(con.location.getIntX() + mainUI.inputHandler.offsetX, con.location.getIntY() + mainUI.inputHandler.offsetY, con.bounds.getIntWidth(), con.bounds.getIntHeight());
					}
				} else {
					gc2.strokeLine(con.location.getIntX() + mainUI.inputHandler.offsetX + (c.isConnectorVertical() ? 0 : 2.5), con.location.getIntY() + mainUI.inputHandler.offsetY + (c.isConnectorVertical() ? 3 : 0), con.location.getIntX() + mainUI.inputHandler.offsetX + (c.isConnectorVertical() ? 6 : 2.5), con.location.getIntY() + mainUI.inputHandler.offsetY + (c.isConnectorVertical() ? 3 : 6)) ;
					if(mainUI.inputHandler.hoveredConnector == con) {
						gc2.strokeOval(con.location.getIntX() + mainUI.inputHandler.offsetX, con.location.getIntY() + mainUI.inputHandler.offsetY, con.bounds.getIntWidth(), con.bounds.getIntHeight());
					}
				}				
			}
			
			int extansion = c.getExtansionSize();
			if(extansion > 0) {
				Location[] loc = c.getExtansionLocation();
				gc2.strokeLine(loc[0].getIntX() + mainUI.inputHandler.offsetX, loc[0].getIntY() + mainUI.inputHandler.offsetY,
						loc[1].getIntX() + mainUI.inputHandler.offsetX, loc[1].getIntY() + mainUI.inputHandler.offsetY);
			}

			if((mainUI.inputHandler.selected != null && mainUI.inputHandler.selected.contains(c)) || 
					mainUI.inputHandler.dragged != null && mainUI.inputHandler.dragged == c) {
				gc2.setFill(selected);
				gc2.setStroke(selected.darker());
				gc2.fillRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
				gc2.strokeRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
				gc2.setStroke(Color.BLACK);
			} else if(mainUI.inputHandler.hoveredComponent != null && mainUI.inputHandler.hoveredComponent == c ) {
				gc2.setFill(hovered);
				gc2.setStroke(hovered.darker());
				gc2.fillRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
				gc2.strokeRect(c.location.getIntX() + mainUI.inputHandler.offsetX, c.location.getIntY() + mainUI.inputHandler.offsetY, c.bounds.getIntWidth(), c.bounds.getIntHeight());
				gc2.setStroke(Color.BLACK);
			}
		}
		if(mainUI.inputHandler.selector) {
			gc2.setFill(selected.brighter());
			gc2.setStroke(selected.darker().darker());
			int drawX = (int) mainUI.inputHandler.selectorX, drawY = (int) mainUI.inputHandler.selectorY, drawWidth = (int) mainUI.inputHandler.selectorWidth, drawHeight = (int) mainUI.inputHandler.selectorHeight;
			gc2.fillRect(drawX + mainUI.inputHandler.offsetX, drawY + mainUI.inputHandler.offsetY, drawWidth, drawHeight);
			gc2.strokeRect(drawX + mainUI.inputHandler.offsetX, drawY + mainUI.inputHandler.offsetY, drawWidth, drawHeight);
			gc2.setStroke(Color.BLACK);
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
	
    private void rotate(GraphicsContext gc, double angle, double px, double py) {
        Rotate r = new Rotate(angle, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    private void drawRotatedImage(GraphicsContext gc, Image image, double angle, double tlpx, double tlpy) {
        gc.save(); // saves the current state on stack, including the current transform
        rotate(gc, angle, tlpx + image.getWidth() / 2, tlpy + image.getHeight() / 2);
        gc.drawImage(image, tlpx, tlpy);
        gc.restore(); // back to original state (before rotation)
    }
	
}
