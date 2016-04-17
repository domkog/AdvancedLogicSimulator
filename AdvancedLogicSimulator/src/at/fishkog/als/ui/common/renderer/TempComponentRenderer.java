package at.fishkog.als.ui.common.renderer;

import com.sun.javafx.geom.BaseBounds;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Data;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.ui.common.CompNode;
import at.fishkog.als.ui.common.MainUI;
import at.fishkog.als.ui.common.MouseInputHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Rotate;

public class TempComponentRenderer {
	public Canvas grid;
	private MainUI mainUI;
	private Canvas compCanvas;
	private PannableCanvas canvas;
	
	private MouseInputHandler nodeGestures;
	
	private static Color gridColor = new Color(Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(), Color.LIGHTGRAY.getBlue(), 0.4);
	
	public TempComponentRenderer(MainUI mainUI, Canvas grid, PannableCanvas canvas) {
		this.grid = grid;
		this.mainUI = mainUI;
		this.canvas = canvas;
		
		this.nodeGestures = new MouseInputHandler(canvas);
	
		drawComponents();
		drawWires();
	}
	
	public void repaint() {
		drawGrid();
		
		
	}
    
	public void drawComponents() {
		Circle circle1 = new Circle( 300, 300, 50);
	    circle1.setStroke(Color.ORANGE);
	    circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
	    circle1.addEventFilter( MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
	    circle1.addEventFilter( MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
	
	    this.canvas.getChildren().addAll(circle1);
	    
	    Group compGroups = new Group();
	    CompNode compBase;    
	    
		for(Data d: AdvancedLogicSimulator.logicCanvas.components) {
			Group compGroup = new Group();
			if (d instanceof Component) {
				Component c = (Component) d;
				
				//TODO IWANN MACH ICH DAS SCHON D:
				if(c.hasTexture()) {
					//compBase = getRotatedImage(c.renderContext.getImage(), (90 * c.basicAttributes.getOrientation().value), c.renderLocation.getIntX(),c.renderLocation.getIntY());
					
				} else {
					//compBase = new Circle(300,300,50);
					
				}
				
				Rectangle hitbox = getHitbox(c);
				
				compGroup.getChildren().setAll(/*compBase,*/hitbox, getConnectorView(c), getExtensionLine(c));
				compGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
				compGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
				
				compGroups.getChildren().addAll(compGroup);
				}
				
			}
		
		this.canvas.getChildren().addAll(compGroups);
	}
	
	public void drawWires() {
		Group wires = new Group();
		Line lineWire = new Line();		
		
		for(Data c: AdvancedLogicSimulator.logicCanvas.components) {
			if (c instanceof Wire){
				Wire wire = (Wire) c;
				
				lineWire = new Line(wire.getStart().getIntX(), wire.getStart().getIntY(), wire.getEnd().getIntX(), wire.getEnd().getIntY());
				lineWire.setStrokeLineCap(StrokeLineCap.ROUND);
				lineWire.setStroke(wire.getColor());
				
				wires.getChildren().add(lineWire);
			}
		}
		this.canvas.getChildren().addAll(wires);
	}
	
	public void drawGrid(){
		double w = grid.getWidth();
        double h = grid.getHeight();
        
        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.clearRect(0, 0, w, h);
        
        gc.setStroke(gridColor);
        gc.setLineWidth(1);

        // draw grid lines
        double offset = 25 * canvas.myScale.doubleValue();

        double x = (canvas.getTranslateX() - grid.getTranslateX()) % offset -offset;
        double y = (canvas.getTranslateY() - grid.getTranslateY()) % offset -offset;
        
        for(int i = 0; i < (w / offset) ;i++) {
			gc.strokeLine(i * offset + x+0.5, y+0.5, i * offset + x+0.5, h + y+0.5);
			
		}
		for(int i = 0; i < (h / offset) ;i++) {
			gc.strokeLine(x+0.5, i * offset + y +0.5, w + x + 0.5, i * offset + y +0.5);
			
		}
		
		grid.toBack();
		
	}
	
	//TODO HOW WHAT
	private void rotate(Node n, double angle, double px, double py) {

        
    }
	
	private Group getConnectorView (Component c) {
		Group cons = new Group();
		Node conNode;
		
		for (Connector con: c.connectors) {
			if(con.isNegated.getValue()){
				conNode = new Circle(con.location.getIntX(), con.location.getIntY(), con.bounds.getIntWidth());
				
			} else {
				conNode = new Line(con.location.getIntX(), con.location.getIntY(), con.bounds.getIntWidth(), con.bounds.getIntHeight());
				
			}
			cons.getChildren().add(conNode);
		}
		return cons;
	}

	private Line getExtensionLine(Component c) {
		Line extLine = new Line();
		Location[] locs = c.getExtansionLocation();
		
		extLine = new Line(locs[0].getIntX(), locs[0].getIntY(), locs[1].getIntX(), locs[1].getIntY());

		
		return extLine;
	}
	
    private ImageView getRotatedImage(Image image, double angle, double tlpx, double tlpy) {
    	ImageView iv = new ImageView(image);
    	iv.setRotate(angle);
    	iv.setX(tlpx);
    	iv.setY(tlpy);
    	
    	return iv;
    	
    }
    
    public Rectangle getHitbox(Component c) {
    	//return new Rectangle(c.renderLocation.getIntX(), c.renderLocation.getIntY(), ((int) c.renderContext.getImage().getHeight()) + c.renderLocation.getIntX(),((int) c.renderContext.getImage().getWidth()) + c.renderLocation.getIntY());
    	return new Rectangle(c.renderLocation.getIntX(), c.renderLocation.getIntY(),c.renderContext.getImage().getWidth(),c.renderContext.getImage().getHeight());

    	
    }
}
