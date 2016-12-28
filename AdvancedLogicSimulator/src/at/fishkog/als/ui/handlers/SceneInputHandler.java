package at.fishkog.als.ui.handlers;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.ui.common.PannableCanvas;
import at.fishkog.als.ui.common.SelectionModel;
import at.fishkog.als.ui.customNodes.ComponentGroup;
import at.fishkog.als.ui.customNodes.Rubberband;
import at.fishkog.als.ui.customNodes.WireLine;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
public class SceneInputHandler {

    private static final double MAX_SCALE = 10.0d;
    private static final double MIN_SCALE = .1d;
    
    private DragContext selectDragContext = new DragContext();
    private DragContext sceneDragContext = new DragContext();
    
    private LanguageManager l;
    
  	private PannableCanvas canvas;
  	
  	private SelectionModel selectModel;
  	private Rubberband rubberband;
  	
    public SceneInputHandler(PannableCanvas canvas) {
        this.canvas = canvas;
        
        this.l = AdvancedLogicSimulator.lang;
        
        this.selectModel = canvas.selectModel;
        this.rubberband = canvas.rubberband;
        
		this.canvas.getParent().setOnMouseExited((event) -> {
			canvas.setCursor(Cursor.DEFAULT);
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText("X: - Y: - "));
		
		});
		
		this.canvas.getParent().setOnMouseMoved((event) -> {
			Platform.runLater(() -> AdvancedLogicSimulator.mainUi.mousePos.setText(
					l.getString("Mouse") +": X: " + Math.round(event.getX()-canvas.getTranslateX()) + " Y: " + Math.round(event.getY()-canvas.getTranslateY())));

		});
	}  

    //TODO Don´t pann when hovering Node 
    public EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {        	
            if(event.isSecondaryButtonDown()) {
	            sceneDragContext.mouseAnchorX = event.getSceneX();
	            sceneDragContext.mouseAnchorY = event.getSceneY();
	
	            sceneDragContext.translateAnchorX = canvas.getTranslateX();
	            sceneDragContext.translateAnchorY = canvas.getTranslateY();
	            AdvancedLogicSimulator.renderer.repaint();
	            event.consume();
	            
            } else if(event.isPrimaryButtonDown()) {
	        	selectDragContext.mouseAnchorX = event.getX();
	        	selectDragContext.mouseAnchorY = event.getY();

	        	rubberband.setX(selectDragContext.mouseAnchorX);
	        	rubberband.setY(selectDragContext.mouseAnchorY);
	        	rubberband.setWidth(0);
	        	rubberband.setHeight(0);
	        	canvas.getChildren().add(rubberband);
	        
    	    }
        }
    };
    
    //TODO Rubberband after canvas translate
    public EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {       	
	        if(event.isSecondaryButtonDown()) {
	            canvas.setTranslateX(sceneDragContext.translateAnchorX + event.getSceneX() - sceneDragContext.mouseAnchorX);
	            canvas.setTranslateY(sceneDragContext.translateAnchorY + event.getSceneY() - sceneDragContext.mouseAnchorY);
	            AdvancedLogicSimulator.renderer.repaint();
	            
            } else  if (event.isPrimaryButtonDown()) {

            	double offsetX = event.getX() - selectDragContext.mouseAnchorX;
                double offsetY = event.getY() - selectDragContext.mouseAnchorY;
            	
            	if( offsetX > 0)
            		rubberband.setWidth(offsetX);
            		
                else {
                	rubberband.setX(event.getX());
                	rubberband.setWidth(selectDragContext.mouseAnchorX - rubberband.getX());
               	 
                }

                if( offsetY > 0) {
                	rubberband.setHeight(offsetY);
                	
                } else {
                	rubberband.setY(event.getY());
                	rubberband.setHeight(selectDragContext.mouseAnchorY - rubberband.getY());
               	 
                }
            }
        }
    };

    public EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {
        public void handle(ScrollEvent event) {
            double delta = 1.2;

            double scale = canvas.getScale();
            double oldScale = scale;

            if (event.getDeltaY() < 0)
                scale /= delta;
            else
                scale *= delta;

            scale = clamp( scale, MIN_SCALE, MAX_SCALE);

            double f = (scale / oldScale)-1;

            //TODO fix the point where u are zooming to
            double dx = event.getSceneX();
            double dy = event.getSceneY();
            
            //System.out.println(dx);
            //System.out.println(dy);
            
            canvas.setScale(scale);

            canvas.setPivot(-f*dx, -f*dy);
            
            AdvancedLogicSimulator.renderer.repaint();
            event.consume();

        }
    };
    
    public EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
        	if( !event.isShiftDown() && !event.isControlDown()) {
                selectModel.clear();
                
            }
            for(Node n: canvas.getChildren()) {
                if((n instanceof ComponentGroup || n instanceof WireLine)) {
	            	if(n.getBoundsInParent().intersects(rubberband.getBoundsInParent())) {
	                    if(event.isShiftDown()) {
	                        selectModel.add(n);
	
	                    } else if( event.isControlDown()) {
	
	                        if( selectModel.contains(n)) {
	                        	selectModel.remove(n);
	                        } else {
	                        	selectModel.add(n);
	                        }
	                    } else {
	                    	selectModel.add(n);
	                    }
	            	}
                }
            }

            rubberband.setX(0);
            rubberband.setY(0);
            rubberband.setWidth(0);
            rubberband.setHeight(0);

            canvas.getChildren().remove(rubberband);
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