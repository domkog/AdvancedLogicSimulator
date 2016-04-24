package at.fishkog.als.ui.common.renderer;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.sim.data.Location;
import at.fishkog.als.ui.common.MainUI;
import at.fishkog.als.ui.common.PannableCanvas;
import at.fishkog.als.ui.customNodes.ComponentGroup;
import at.fishkog.als.ui.customNodes.ConnectorCircle;
import at.fishkog.als.ui.customNodes.ConnectorLine;
import at.fishkog.als.ui.customNodes.CustomGroup;
import at.fishkog.als.ui.customNodes.WireLine;
import at.fishkog.als.ui.handlers.NodeInputHandler;
import at.fishkog.als.ui.handlers.SceneInputHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ComponentRenderer {
	public Canvas grid;
	
	private PannableCanvas canvas;
	
	public int compCount =0;
	public int wiresCount =0;

	private NodeInputHandler nodeHandler;
	private SceneInputHandler inputHandler;
	
	private PropertiesManager config;
	
	private static Color gridColor = new Color(Color.LIGHTGRAY.getRed(), Color.LIGHTGRAY.getGreen(), Color.LIGHTGRAY.getBlue(), 0.4);

	
	public ComponentRenderer(MainUI mainUI, Canvas grid, PannableCanvas canvas) {
		this.grid = grid;
		this.canvas = canvas;

    	this.inputHandler = new SceneInputHandler(this.canvas);
		this.nodeHandler = new NodeInputHandler(canvas);

		this.config = AdvancedLogicSimulator.config;
		
		for(Component d: AdvancedLogicSimulator.logicCanvas.components) {
			if (d instanceof Wire){
				Wire wire = (Wire) d;
				WireLine t_w = createWire(wire);
				this.canvas.getChildren().add(t_w);
				this.wiresCount++;
				
			} else if (d instanceof BasicComponent) {
				BasicComponent c = (BasicComponent) d;
				ComponentGroup t_c = createComponent(c);
				this.canvas.getChildren().add(t_c);
				this.compCount++;
			
			}
		}
		
		this.repaint();
	}
	
	public void repaint() {
		drawGrid();
		addSceneHandlers();
		
		
	}
	
	private void drawGrid(){
		double w = grid.getWidth();
        double h = grid.getHeight();
        
        GraphicsContext gc = grid.getGraphicsContext2D();

        gc.clearRect(0, 0, w, h);
        
        gc.setStroke(gridColor);
        gc.setLineWidth(1);

        //draw grid lines
        //This scale is not perfect grid position is jumping
        
        double offset = 10 * canvas.myScale.doubleValue();

        double x = (canvas.getTranslateX() - grid.getTranslateX()) % offset -offset;
        double y = (canvas.getTranslateY() - grid.getTranslateY()) % offset -offset;
        
        for(int i = 0; i < (w / offset) ;i++) {
			gc.strokeLine(i * offset + x -0.5, y-0.5, i * offset + x-0.5, h + y-0.5);
			
		}
		for(int i = 0; i < (h / offset) ;i++) {
			gc.strokeLine(x-0.5, i * offset + y-0.5, w + x-0.5, i * offset + y-0.5);
			
		}
		
		grid.toBack();
		
	}
	
	private Group getExtensionLine(BasicComponent c) {
		Line extLine;
		Line extLine2;
		Group res = new Group();
		
		if(c.inputs.size() < 4) return new Group();
		int extLength = 0;
		int conNum = c.inputs.size();
				
		if (conNum > 3) {
			if(conNum % 2 == 0) {
				extLength = (conNum -3) * c.getConnector(0).bounds.getIntHeight();
			
			} else {
				extLength = (conNum -4) * c.getConnector(0).bounds.getIntHeight();
				
			}
		}

		Location start = new Location(0, 0 - extLength);
		Location end = new Location(0, 3);
		
		extLine = new Line(start.getIntX(), start.getIntY(), end.getIntX(), end.getIntY());
		extLine.setStyle("-fx-stroke-type: outside; -fx-stroke-width: 1");
		
		Location start2 = new Location(0,-3 +c.bounds.getIntHeight());
		Location end2 = new Location(0,-3 +c.bounds.getIntHeight() + extLength);
		
		extLine2 = new Line(start2.getIntX(), start2.getIntY(), end2.getIntX(), end2.getIntY());
		extLine2.setStyle("-fx-stroke-type: outside; -fx-stroke-width: 1;");
		
		res.getChildren().setAll(extLine, extLine2);
		return res;
	}
    
    private ComponentGroup createComponent(BasicComponent c) {
    	ComponentGroup compGroup = new ComponentGroup(c);
		CustomGroup compBaseGroup = new CustomGroup();
		
		Rectangle hitbox = new Rectangle(0, 0, c.bounds.getIntWidth(), c.bounds.getIntHeight());
		
		hitbox.setFill(Color.TRANSPARENT);
		if(config.isTrue("SHOWHITBOX")) {
			hitbox.setStroke(Color.GREEN);
			
		}
		
		compBaseGroup.getChildren().setAll(c.getRenderparts(), hitbox);
		compBaseGroup.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.nodeHandler.onMouseDraggedDraggable);
		compBaseGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, this.nodeHandler.onMousePressedNode);
		compBaseGroup.setOnDragDetected(this.nodeHandler.onMouseStartedDraggingDraggable);
		
		compGroup.getChildren().setAll(compBaseGroup, createConnectors(c), getExtensionLine(c));
		compGroup.setTranslateX(c.location.getIntX());
		compGroup.setTranslateY(c.location.getIntY());
		compGroup.setRotate(c.basicAttributes.getIntOrientation() * 90);
		compGroup.toFront();
		
		compGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, this.nodeHandler.onMouseClickedNode);       	
		compGroup.addEventFilter(MouseEvent.MOUSE_ENTERED, this.nodeHandler.onMouseHoveredNode);
		compGroup.addEventFilter(MouseEvent.MOUSE_EXITED, this.nodeHandler.onMouseExitedNode);
		compGroup.addEventFilter(MouseEvent.MOUSE_PRESSED, this.nodeHandler.onMousePressedNode);
		compGroup.addEventFilter(MouseEvent.MOUSE_RELEASED, this.nodeHandler.onMouseReleasedNode);
		
		c.setNode(compGroup);
		return compGroup;
    	
    }
	
	private Group createConnectors (BasicComponent c) {
		Group cons = new Group();
		int conY =(c.bounds.getIntHeight() / 2);
		
		for (int i=0; i<c.inputs.size(); i++) {	
			Connector con = c.inputs.get(i);
			int j=0;
			
			if((c.inputs.size() % 2)==0)
				j=1;

			if(i%2==0) {
				conY += (con.bounds.getIntHeight()*2)*(i + j); 
				 
			} else {
				conY -= (con.bounds.getIntHeight()*2)*(i + j);
				
			}
			
			if(con.isNegated.getValue()){
				ConnectorCircle conNode;
				conNode = new ConnectorCircle(-con.bounds.getIntWidth(),conY, con.bounds.getIntWidth()-1, con);
				
				conNode.setStyle("-fx-stroke-type: outside;"
						+ " -fx-stroke-width: 2;"
						+ " -fx-stroke: black;"
						+ " -fx-fill: transparent;");
				
				conNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.nodeHandler.onMouseDraggedConnector);

				con.location.x.setValue(c.location.getIntX()-con.bounds.getIntWidth());
				con.location.y.setValue(c.location.getIntY()-conY);
				con.setNode(conNode);
				cons.getChildren().add(conNode);
				
			} else {
				ConnectorLine conNode;
				
				conNode = new ConnectorLine(-con.bounds.getIntWidth(), conY, con.bounds.getIntWidth()/2 -4, conY, con);
				conNode.setStyle("-fx-stroke-type: outside; "
						+ "-fx-stroke-width: 2; "
						+ "-fx-stroke-line-cap: ROUND");
				
				conNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.nodeHandler.onMouseDraggedConnector);

				con.location.x.setValue(c.location.getIntX()-con.bounds.getIntWidth());
				con.location.y.setValue(c.location.getIntY()-conY);
				
				con.setNode(conNode);
				cons.getChildren().add(conNode);
			}
		}
		Connector cOut = c.outputs.get(0);
		
		if(cOut.isNegated.getValue()){
			ConnectorCircle conNode;
			conNode = new ConnectorCircle(c.bounds.getIntWidth() + cOut.bounds.getIntWidth()/2,c.bounds.getIntHeight() / 2, cOut.bounds.getIntWidth()-2, cOut);
			
			conNode.setStyle("-fx-stroke-type: outside;"
					+ " -fx-stroke-width: 2;"
					+ " -fx-stroke: black;"
					+ " -fx-fill: transparent;");
			
			conNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.nodeHandler.onMouseDraggedConnector);

			cOut.location.x.setValue(c.location.getIntX()+ c.bounds.getIntWidth() + cOut.bounds.getIntWidth()/2);
			cOut.location.y.setValue(c.location.getIntY()+ c.bounds.getIntHeight() / 2);
			cOut.setNode(conNode);
			cons.getChildren().add(conNode);
			
		} else {
			ConnectorLine conNode;
			
			conNode = new ConnectorLine(c.bounds.getIntWidth(), c.bounds.getIntHeight()/2, c.bounds.getIntWidth() + cOut.bounds.getIntWidth(), c.bounds.getIntHeight()/2, cOut);
			conNode.setStyle("-fx-stroke-type: outside; "
					+ "-fx-stroke-width: 2; "
					+ "-fx-stroke-line-cap: ROUND");
			
			conNode.addEventFilter(MouseEvent.MOUSE_DRAGGED, this.nodeHandler.onMouseDraggedConnector);
			
			cOut.location.x.setValue(c.location.getIntX()+ c.bounds.getIntWidth() + cOut.bounds.getIntWidth()/2);
			cOut.location.y.setValue(c.location.getIntY()+ c.bounds.getIntHeight() / 2);
			cOut.setNode(conNode);
			cons.getChildren().add(conNode);
		}
		
		return cons;
	}
	
	public void rerenderComp(Component c){		
		this.removeNode(c.getNode());
		this.addComponent(c);
		this.repaint();
		
	}
	
    private WireLine createWire(Wire wire){
		WireLine lineWire = new WireLine();	
			
		lineWire = new WireLine(wire.getStart().getIntX(), wire.getStart().getIntY(), wire.getEnd().getIntX(), wire.getEnd().getIntY());
		lineWire.setStyle("-fx-stroke-type: outside; -fx-stroke-width: 1; -fx-stroke-line-cap: ROUND");
		lineWire.setStroke(wire.getColor());

		return lineWire;
    }
    
    public void addComponent(Component comp) {
    	if(comp instanceof BasicComponent) {
    		BasicComponent c = (BasicComponent) comp;
    		Group node = createComponent(c);
    		comp.setNode(node);
    		this.canvas.getChildren().add(node);
    		this.compCount++;
    		
    	} else if(comp instanceof Wire) {
    		Wire w = (Wire) comp;
    		WireLine wire = createWire(w);
    		comp.setNode(wire);
    		this.canvas.getChildren().add(wire);
    		this.wiresCount++;
    	}
    	
    	this.repaint();
    }
    
    public void removeNode(Node n) {
    	this.canvas.getChildren().remove(n);
    	
    }

    private void addSceneHandlers(){    	
    	this.canvas.getParent().setOnMousePressed(this.inputHandler.onMousePressedEventHandler);
    	this.canvas.getParent().setOnMouseDragged(this.inputHandler.onMouseDraggedEventHandler);
    	this.canvas.getParent().setOnMouseReleased(this.inputHandler.onMouseReleasedEventHandler);
    	this.canvas.getParent().setOnScroll(this.inputHandler.onScrollEventHandler);
 	
    }
    
}
