package at.fishkog.als.ui.common;

import java.util.ArrayList;

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
	public boolean dragging = false;
	private boolean clickFlag = false;
	public ArrayList<Component> selected = new ArrayList<Component>();
	
	public boolean selector;
	public double selectorRefX, selectorRefY;
	public double selectorX, selectorY;
	public double selectorWidth, selectorHeight;
	
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
				canvas.setCursor(Cursor.DEFAULT);
				AdvancedLogicSimulator.renderer.repaint();
			}
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + "Mouse: X: " + vMouseX + " Y: " + vMouseY));
			
		});
		
		this.canvas.setOnMouseEntered((event) -> {
			hovered = true;
		});
		
		this.canvas.setOnMouseExited((event) -> {
			canvas.setCursor(Cursor.DEFAULT);
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText("X: - Y: - "));
			hovered = false;
		});
		
		this.canvas.setOnMousePressed((event) -> {
			for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
				if(selected.contains(c)) continue;
				if(c.intersects((int)vMouseX, (int)vMouseY)) {
					if(!event.isControlDown()) this.selected.clear();
					this.selected.add(c);
					break;
				}
			}
			mouseX = event.getX();
			mouseY = event.getY();
			if(selected.isEmpty()) {
				selected.clear();
				selector = true;
				selectorX = translateX(mouseX);
				selectorY = translateY(mouseY);
				selectorRefX = selectorX;
				selectorRefY = selectorY;
			}
		});
		
		this.canvas.setOnMouseDragged((event) -> {
			double movedX = event.getX() - mouseX;
			double movedY = event.getY() - mouseY;
			mouseX = event.getX();
			mouseY = event.getY();
			if(event.getButton() == MouseButton.MIDDLE) {
				offsetX += movedX;
				offsetY += movedY;
				if(canvas.getCursor() != Cursor.MOVE) canvas.setCursor(Cursor.MOVE);
			} else if(event.getButton() == MouseButton.PRIMARY) {
				if(!selected.isEmpty() && !selector) {
					if(!dragging) {
						for(Component c: this.selected) {
							if(c.intersects((int)translateX(mouseX), (int)translateY(mouseY))) {
								dragging = true;
								if(canvas.getCursor() != Cursor.MOVE) canvas.setCursor(Cursor.MOVE);
								break;
							}
						}
					}
					if(dragging) {
						for(Component c: this.selected) {
							c.location.x.setValue((int) (c.location.getIntX() + movedX));
							c.location.y.setValue((int) (c.location.getIntY() + movedY));
						}
					}
				} else if(selector) {
					selectorWidth = translateX(mouseX) - selectorRefX;
					selectorHeight = translateY(mouseY) - selectorRefY;
					if(selectorWidth < 0) {
						selectorWidth *= -1;
						selectorX = selectorRefX - selectorWidth;
					}
					if(selectorHeight < 0) {
						selectorHeight *= -1;
						selectorY = selectorRefY - selectorHeight;
					}
					
					for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
						if(c.bounds.isInside(c.location.x.value, c.location.y.value, (int) selectorX,(int) selectorY,(int) selectorWidth,(int) selectorHeight)) {
							if(!selected.contains(c)) selected.add(c);
						} else {
							if(selected.contains(c)) selected.remove(c);
						}
					}
					AdvancedLogicSimulator.renderer.repaint();
				}
			}
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + "Mouse: X: " + translateX(mouseX) + " Y: " + translateY(mouseY)));
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
						MenuItem select = new MenuItem((selected.contains(c)) ? "Select" : "Deselect");
						select.setOnAction((e) -> {
							if(this.selected.contains(c)) this.selected.remove(c);
							else this.selected.add(c);
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem delete = new MenuItem("Delete");
						delete.setOnAction((e) -> {
							for(Component comp: selected) {
								AdvancedLogicSimulator.logicCanvas.remove(comp);
							}
							selected.clear();
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem rotate = new MenuItem("Rotate");
						rotate.setOnAction((e) -> {
							for(Component comp: selected) {
								comp.onRotate();
							}
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem properties = new MenuItem("Properties");
						context.getItems().addAll(select, delete, rotate, properties);
						context.show(canvas, event.getScreenX(),event.getScreenY());
						
						this.context = context;
						break;
					}
				}
			} else if(event.getButton() == MouseButton.PRIMARY) {
				if(dragging) return;
				if(clickFlag) {
					clickFlag = false;
					return;
				}
				int x = (int) translateX(event.getX());
				int y = (int) translateY(event.getY());
				boolean result = false;
				for(Component c: AdvancedLogicSimulator.logicCanvas.components) {
					if(c.intersects(x, y)) {
						if(!event.isControlDown() || !selected.contains(c)) {
							selected.clear();
						}
						if(!selected.contains(c)) {
							selected.add(c);
						}
						AdvancedLogicSimulator.renderer.repaint();
						result = true;
						break;
					}
				}
				if(!result && !selected.isEmpty()) {
					selected.clear();
					AdvancedLogicSimulator.renderer.repaint();
				}
			}
			
		});
		
		this.canvas.setOnMouseReleased((event) -> {
			if(event.getButton() == MouseButton.MIDDLE) {
				canvas.setCursor(Cursor.DEFAULT);
				return;
			} else if(event.getButton() == MouseButton.PRIMARY) {
				if(dragging) {
					canvas.setCursor(Cursor.HAND);
					dragging = false;
					clickFlag = true;
				}
				if(selector) {
					selector = false;
					selectorWidth = 0;
					selectorHeight = 0;
					AdvancedLogicSimulator.renderer.repaint();
					clickFlag = true;
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
