package at.fishkog.als.ui.common;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.component.Component;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;

public class InputHandler {

	private Canvas canvas;
	
	//Mouse position projected to the LogicCanvas
	public double vMouseX, vMouseY;
	public boolean hovered = false;;

	private double mouseX, mouseY;
	public double offsetX, offsetY;

	public boolean componentHovered = false;
	public Component hoveredComponent;
	public Component dragged;
	public Component selected;
	
	public ContextMenu context;
	
	public InputHandler(Canvas canvas) {
		this.canvas = canvas;
		
		this.canvas.setOnMouseMoved((event) -> {
			vMouseX = translateX(event.getX());
			vMouseY = translateY(event.getY());
			boolean result = false;
			for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
				if(c.intersects((int)vMouseX, (int)vMouseY)) {
					componentHovered = true;
					hoveredComponent = c;
					result = true;
					canvas.setCursor(Cursor.HAND);
					AdvancedLogicSimulator.renderer.repaint();
					break;
				}
			}
			if(componentHovered && !result) {
				componentHovered = false;
				hoveredComponent = null;
				canvas.setCursor(Cursor.MOVE);
				AdvancedLogicSimulator.renderer.repaint();
			}
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + "Mouse: X: " + vMouseX + " Y: " + vMouseY));
			
		});
		
		this.canvas.setOnMouseEntered((event) -> {
			canvas.setCursor(Cursor.MOVE);
			hovered = true;
		});
		
		this.canvas.setOnMouseExited((event) -> {
			canvas.setCursor(Cursor.DEFAULT);
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText("X: - Y: - "));
			hovered = false;
		});
		
		this.canvas.setOnMousePressed((event) -> {
			for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
				if(c.intersects((int)vMouseX, (int)vMouseY)) {
					dragged = c;
					break;
				}
			}
			mouseX = event.getX();
			mouseY = event.getY();
		});
		
		this.canvas.setOnMouseReleased((event) -> {
			if(dragged != null) dragged = null;
		});
		
		this.canvas.setOnMouseDragged((event) -> {
			double movedX = event.getX() - mouseX;
			double movedY = event.getY() - mouseY;
			mouseX = event.getX();
			mouseY = event.getY();
			if(dragged != null) {
				dragged.location.x.setValue((int) (dragged.location.getIntX() + movedX));
				dragged.location.y.setValue((int) (dragged.location.getIntY() + movedY));
			} else {
				offsetX += movedX;
				offsetY += movedY;
			}
			AdvancedLogicSimulator.renderer.repaint();
		});
		
		this.canvas.setOnMouseClicked((event) -> {
			if(context != null) {
				context.hide();
				context = null;
			}
			if(event.getButton() == MouseButton.SECONDARY) {
				int x = (int) translateX(event.getX());
				int y = (int) translateY(event.getY());
				for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
					if(c.intersects(x, y)) {
						ContextMenu context = new ContextMenu();
						MenuItem select = new MenuItem((selected == c) ? "Deselect" : "Select");
						select.setOnAction((e) -> {
							if(this.selected == c) selected = null;
							else this.selected = c;
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem delete = new MenuItem("Delete");
						delete.setOnAction((e) -> {
							AdvancedLogicSimulator.logicCanvas.remove(c);
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem rotate = new MenuItem("Rotate");
						MenuItem properties = new MenuItem("Properties");
						context.getItems().addAll(select, delete, rotate, properties);
						context.show(canvas, event.getScreenX(),event.getScreenY());
						
						this.context = context;
						break;
					}
				}
			} else if(event.getButton() == MouseButton.PRIMARY) {
				int x = (int) translateX(event.getX());
				int y = (int) translateY(event.getY());
				boolean result = false;
				for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
					if(c.intersects(x, y)) {
						selected = c;
						AdvancedLogicSimulator.renderer.repaint();
						result = true;
						break;
					}
				}
				if(!result && selected != null) {
					selected = null;
					AdvancedLogicSimulator.renderer.repaint();
				}
				
			}
		});
	}
	
	public double translateX(double x) {
		return x - offsetX;
	}
	
	public double translateY(double y) {
		return y - offsetY;
	}
	
}
