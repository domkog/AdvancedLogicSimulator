package at.fishkog.als.ui.common;

import java.util.ArrayList;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.ui.common.renderer.PannableCanvas;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
public class MouseInputHandler {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    
    DragContext sceneDragContext = new DragContext();
    DragContext nodeDragContext = new DragContext();
    
  //Mouse position projected to the LogicCanvas
  	public double vMouseX, vMouseY;
  	public boolean hovered = false;;

  	private double mouseX, mouseY;
  	public double offsetX, offsetY;

  	public boolean componentHovered = false;
  	public Component hoveredComponent;
  	public Component dragged;
  	public Connector hoveredConnector;
  	public Connector draggedConnector;
  	public boolean draggingConnector = false;
  	public boolean dragging = false;
  	private boolean clickFlag = false;
  	public ArrayList<Component> selected = new ArrayList<Component>();
  	
  	public boolean selector;
  	public double selectorRefX, selectorRefY;
  	public double selectorX, selectorY;
  	public double selectorWidth, selectorHeight;
  	
  	public ContextMenu context;
  	
  	private LanguageManager l;
        
    PannableCanvas canvas;

    public MouseInputHandler( PannableCanvas canvas) {
        this.canvas = canvas;
        
        this.canvas.setOnMouseMoved((event) -> {
			vMouseX = translateX(event.getX());
			vMouseY = translateY(event.getY());
			boolean result = false;
			boolean resultConnector = false;
			Component c = null;
			for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
				if(d instanceof Component && d!= null) c = (Component) d;
				if(c.intersects((int)vMouseX, (int)vMouseY)) {
					componentHovered = true;
					hoveredComponent = c;
					result = true;
					canvas.setCursor(Cursor.HAND);
					AdvancedLogicSimulator.renderer.repaint();
					break;
				}
				if(!resultConnector) {
					for(Connector con: c.connectors) {
						if(con.intersects((int)vMouseX, (int)vMouseY)) {
							hoveredConnector = con;
							resultConnector = true;
							canvas.setCursor(Cursor.HAND);
							AdvancedLogicSimulator.renderer.repaint();
							break;
						}
					}
				}
			}
			if(componentHovered && !result) {
				componentHovered = false;
				hoveredComponent = null;
				canvas.setCursor(Cursor.DEFAULT);
				AdvancedLogicSimulator.renderer.repaint();
			}
			if(hoveredConnector != null) {
				hoveredConnector = null;
				canvas.setCursor(Cursor.DEFAULT);
				AdvancedLogicSimulator.renderer.repaint();
			}
			//TODO Later Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + l.getString("Mouse") +": X: " + vMouseX + " Y: " + vMouseY));
			
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
			Component c = null;
			for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
				if(selected.contains(d)) continue;
				if(d instanceof Component && d!= null) c = (Component) d;
				//TODO Fix NPE
				if(c.intersects((int)vMouseX, (int)vMouseY)) {
					if(!event.isControlDown()) this.selected.clear();
					this.selected.add(c);
					break;
				}
			}
			mouseX = event.getX();
			mouseY = event.getY();
			if(selected.isEmpty() && hoveredConnector == null) {
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
					//TODO FIX LATER Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + l.getString("Mouse") +": X: " + translateX(mouseX) + " Y: " + translateY(mouseY)));
					AdvancedLogicSimulator.renderer.repaint();
					return;
				}
				if(!selector && !draggingConnector) {
					Component c = null;
					for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
						if (d instanceof Component && d!=null) c = (Component) d;
						for(Connector con: c.connectors) {
							if(con.intersects((int)translateX(mouseX), (int)translateY(mouseY))) {
								draggedConnector = con;
								draggedConnector.onDraggedStarted();
								draggingConnector = true;
								break;
							}
						}
					}
				}
				if(!selector && draggingConnector) {
					draggedConnector.onDragged((int) (draggedConnector.location.getIntX() + movedX), (int) (draggedConnector.location.getIntY() + movedY));
					Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + l.getString("Mouse") +":  X: " + translateX(mouseX) + " Y: " + translateY(mouseY)));
					AdvancedLogicSimulator.renderer.repaint();
					return;
				}
				if(selector) {
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
					
					Component c = null;
					for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
						if (d instanceof Component && d!=null) c = (Component) d;
						if(c.bounds.isInside(c.location.x.value, c.location.y.value, (int) selectorX,(int) selectorY,(int) selectorWidth,(int) selectorHeight)) {
							if(!selected.contains(c)) selected.add(c);
						} else {
							if(selected.contains(c)) selected.remove(c);
						}
					}
					AdvancedLogicSimulator.renderer.repaint();
				}
			}
			//TODO Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText((hoveredComponent != null ? hoveredComponent.basicAttributes.getStringName() + " | " : "") + l.getString("Mouse") +":  X: " + translateX(mouseX) + " Y: " + translateY(mouseY)));
			AdvancedLogicSimulator.renderer.repaint();
		});
		
		this.canvas.setOnMouseClicked((event) -> {
			Component c = null;
			if(context != null) {
				context.hide();
				context = null;
			}
			if(event.getButton() == MouseButton.SECONDARY) {
				int x = (int) translateX(event.getX());
				int y = (int) translateY(event.getY());
				
				for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
					if (d instanceof Component && d!=null) c = (Component) d;
					if(c.intersects(x, y)) {
						ContextMenu context = new ContextMenu();
						MenuItem select = new MenuItem((selected.contains(c)) ? l.getString("Select") : l.getString("Deselect"));

						select.setOnAction((e) -> {
							if(this.selected.contains((Component) d)) this.selected.remove((Component) d);
							else this.selected.add((Component) d);
							AdvancedLogicSimulator.renderer.repaint();
						});
						
						MenuItem delete = new MenuItem(l.getString("Delete"));
						delete.setOnAction((e) -> {
							for(Component comp: selected) {
								AdvancedLogicSimulator.logicCanvas.remove(comp);
							}
							selected.clear();
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem rotate = new MenuItem(l.getString("Rotate"));
						rotate.setOnAction((e) -> {
							for(Component comp: selected) {
								comp.onRotate();
							}
							AdvancedLogicSimulator.renderer.repaint();
						});
						MenuItem properties = new MenuItem(l.getString("Properties"));
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
				for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
					if(d instanceof Component) c = (Component) d;
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
				if(draggingConnector) {
					canvas.setCursor(Cursor.HAND);
					draggedConnector.onDraggedFinish((int) translateX(event.getX()),(int) translateY(event.getY()));
					draggedConnector = null;
					draggingConnector = false;
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

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {

            // right mouse button => panning
            if(event.isSecondaryButtonDown()) {
	            sceneDragContext.mouseAnchorX = event.getSceneX();
	            sceneDragContext.mouseAnchorY = event.getSceneY();
	
	            sceneDragContext.translateAnchorX = canvas.getTranslateX();
	            sceneDragContext.translateAnchorY = canvas.getTranslateY();

         // left mouse button => dragging
            } else if(event.isPrimaryButtonDown() && event.getSource() instanceof Node) {
            	nodeDragContext.mouseAnchorX = event.getSceneX();
	            nodeDragContext.mouseAnchorY = event.getSceneY();
	
	            Node node = (Node) event.getSource();
	
	            if(event.getSource() instanceof DraggAble) {
		            nodeDragContext.translateAnchorX = node.getTranslateX();
		            nodeDragContext.translateAnchorY = node.getTranslateY();
		            AdvancedLogicSimulator.renderer.repaint();
	            }
            } 
        }
    };

    private EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
	        // right mouse button => panning
	        if(event.isSecondaryButtonDown()) {
	            canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
	            canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);
	            AdvancedLogicSimulator.renderer.repaint();
	            event.consume();
	    	
	    	// left mouse button => dragging
	        } else if(event.isPrimaryButtonDown() && event.getSource() instanceof Node) {
	            double scale = canvas.getScale();
	
	            Node node = (Node) event.getSource();
	
	            if(event.getSource() instanceof DraggAble) {
		            node.setTranslateX(nodeDragContext.translateAnchorX + (( event.getSceneX() - nodeDragContext.mouseAnchorX) / scale));
		            node.setTranslateY(nodeDragContext.translateAnchorY + (( event.getSceneY() - nodeDragContext.mouseAnchorY) / scale));
		            AdvancedLogicSimulator.renderer.repaint();
		            event.consume();       
	            }
	        }
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = 1.2;

            double scale = canvas.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp( scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            double dx = (event.getSceneX() - (canvas.getBoundsInParent().getWidth()/2 + canvas.getBoundsInParent().getMinX()));
            double dy = (event.getSceneY() - (canvas.getBoundsInParent().getHeight()/2 + canvas.getBoundsInParent().getMinY()));
            
            canvas.setScale( scale);

            // note: pivot value must be untransformed, i. e. without scaling
            canvas.setPivot(f*dx, f*dy);
            AdvancedLogicSimulator.renderer.repaint();
            event.consume();

        }

    };


    public static double clamp( double value, double min, double max) {

        if( Double.compare(value, min) < 0)
            return min;

        if( Double.compare(value, max) > 0)
            return max;

        return value;
    }    
}