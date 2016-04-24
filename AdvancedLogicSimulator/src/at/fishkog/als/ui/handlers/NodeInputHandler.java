package at.fishkog.als.ui.handlers;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.sim.data.Connector;
import at.fishkog.als.ui.actions.Action;
import at.fishkog.als.ui.actions.ActionType;
import at.fishkog.als.ui.common.PannableCanvas;
import at.fishkog.als.ui.common.SelectionModel;
import at.fishkog.als.ui.common.sidebar.Sidebar;
import at.fishkog.als.ui.customNodes.ComponentGroup;
import at.fishkog.als.ui.customNodes.ConnectorCircle;
import at.fishkog.als.ui.customNodes.ConnectorLine;
import at.fishkog.als.ui.customNodes.CustomGroup;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class NodeInputHandler {
    private DragContext nodeDragContext = new DragContext();
    private DragContext wireDragContext = new DragContext();
    
  	private PannableCanvas canvas;
    
  	private LanguageManager l;
  	
  	private SelectionModel selectModel;
  	
  	private ContextMenu context;
	
  	public Node hoveredNode = null;
  	
  	private Action currentMoveAction;
  	
	public NodeInputHandler(PannableCanvas canvas) {
		this.canvas = canvas;

		this.l = AdvancedLogicSimulator.lang;
        
		this.selectModel = canvas.selectModel;

	}
	
	/*
     * 
     * NODE EVENT HANDLER
     * 
     */
    
    public EventHandler<MouseEvent> onMousePressedNode = new EventHandler<MouseEvent>() {
    	public void handle(MouseEvent event) {
	    	//TODO get the correct Mouse Position on draggin on the component
    		nodeDragContext.mouseAnchorX = event.getSceneX();
	    	nodeDragContext.mouseAnchorY = event.getSceneY();
    		
    		event.consume();
	    	
    	}
    };
    
    
    public EventHandler<MouseEvent> onMouseClickedNode = new EventHandler<MouseEvent>() {
    	public void handle(MouseEvent event) {
    		Node n = (Node) event.getSource();
    		if (event.getSource() instanceof ComponentGroup) {
				ComponentGroup comp = (ComponentGroup) event.getSource();
    		
	        	if(event.getButton() == MouseButton.SECONDARY) {
	        		if(context != null) {
	    				context.hide();
	    				context = null;
	    				
	    			}
	        		
	        		ContextMenu cont = new ContextMenu();
					
					MenuItem delete = new MenuItem(l.getString("Delete"));
					delete.setOnAction((e) -> {					
						if(selectModel.contains(n)){
							for(Node sn: selectModel.getSelection()) {
								if(sn instanceof ComponentGroup) {									
									Component l_comp = ((ComponentGroup) sn).getComp();
									AdvancedLogicSimulator.logicCanvas.remove(l_comp);
									AdvancedLogicSimulator.renderer.removeNode(sn);
								}
							}
						} else {
							AdvancedLogicSimulator.logicCanvas.remove(comp.getComp());
							AdvancedLogicSimulator.renderer.removeNode(n);
							
						}
						selectModel.clear();
						
					});
	
					MenuItem rotate = new MenuItem(l.getString("Rotate"));
					rotate.setOnAction((e) -> {						
						if(selectModel.contains(n)){
							for(Node sn: selectModel.getSelection()) {
								if(sn instanceof ComponentGroup) {									
									Component l_comp = ((ComponentGroup) sn).getComp();
									Action a = new Action(ActionType.ROTATE, l_comp);
									l_comp.basicAttributes.getNextRotation(true);
									a.end();
								}
							}
						} else {
							Action a = new Action(ActionType.ROTATE, comp.getComp());
							comp.getComp().basicAttributes.getNextRotation(true);
							a.end();
						}
					});
					MenuItem properties = new MenuItem(l.getString("Properties"));
					properties.setOnAction((e) -> {
						Sidebar.tableUpdater.showData(comp.getComp());
					});
					
					cont.getItems().addAll(delete, rotate, properties);
					cont.show(canvas, event.getScreenX(), event.getScreenY());
									
					context = cont;
				
	        	} else if(event.getButton() == MouseButton.PRIMARY) {
	        		Sidebar.tableUpdater.showData(comp.getComp());
	        		if(!event.isShiftDown())
	        			selectModel.clear();
	        		if(!selectModel.contains(n)) {
	        			selectModel.add(n);
	        			
	        		}	
	        	}
        	}
        	event.consume();
        }
    };
    
    public EventHandler<MouseEvent> onMouseHoveredNode = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
        	canvas.setCursor(Cursor.HAND);
        	hoveredNode = (Node) event.getSource();
        	event.consume();

        }
    };
    
    public EventHandler<MouseEvent> onMouseExitedNode = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
        	canvas.setCursor(Cursor.DEFAULT);
        	hoveredNode = null;
        	event.consume();
        	
        }
    };
    
    public EventHandler<MouseEvent> onMouseDraggedConnector = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
        	if(event.isPrimaryButtonDown()) {            
	            if(event.getSource() instanceof ConnectorLine || event.getSource() instanceof ConnectorCircle) {
	            	Connector con = null;
	            	int leangthX = (int) (event.getX() - wireDragContext.mouseAnchorX);
	            	//int leangthY = (int) (event.getY() - wireDragContext.mouseAnchorY);
	            	
	            	if(event.getSource() instanceof ConnectorLine) {
	            		con = ((ConnectorLine) event.getSource()).getCon();
	            		
	            	} else if(event.getSource() instanceof ConnectorCircle)
	            		con = ((ConnectorCircle) event.getSource()).getCon();
	            	
	            	Wire w = new Wire(AdvancedLogicSimulator.renderer.wiresCount + 1,con, leangthX, true);
	            	AdvancedLogicSimulator.logicCanvas.add(w);
	            	
	            }
        	}
        }
    };
    
    public EventHandler<MouseEvent> onMouseStartedDraggingDraggable = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if(event.isPrimaryButtonDown()) {
				Node node = (Node) event.getSource();
				
	            if((event.getSource() instanceof CustomGroup && node.getParent() instanceof ComponentGroup)) {
	            	CustomGroup grp = (CustomGroup) event.getSource();
	            	ComponentGroup compNode = (ComponentGroup) grp.getParent();
	            	
	            	currentMoveAction = new Action(ActionType.MOVE, compNode.getComp());
	            }
			}
		}
    };
    
    public EventHandler<MouseEvent> onMouseDraggedDraggable = new EventHandler<MouseEvent>() {
        @Override
    	public void handle(MouseEvent event) {
        	if(event.isPrimaryButtonDown()) {
        		Node node = (Node) event.getSource();
        		
        		if((event.getSource() instanceof CustomGroup && node.getParent() instanceof ComponentGroup)) {
        			CustomGroup grp = (CustomGroup) event.getSource();
	            	ComponentGroup compNode = (ComponentGroup) grp.getParent();
	            	
        			if(selectModel.contains(compNode)){
						for(Node sn: selectModel.getSelection()) {
							if(sn instanceof ComponentGroup) {									
								Component l_comp = ((ComponentGroup) sn).getComp();
								
								if(l_comp instanceof BasicComponent) {
									((BasicComponent)l_comp).location.x.setValue((int) (((BasicComponent)l_comp).location.x.getValue() + event.getX()));
									((BasicComponent)l_comp).location.y.setValue((int) (((BasicComponent)l_comp).location.y.getValue() + event.getY()));
									
								} else {
									
								}
							}
						} 	
					} else {

						if(compNode.getComp() instanceof BasicComponent) {
							((BasicComponent)compNode.getComp()).location.x.setValue((int) (((BasicComponent)compNode.getComp()).location.x.getValue() + event.getX()));
							((BasicComponent)compNode.getComp()).location.y.setValue((int) (((BasicComponent)compNode.getComp()).location.y.getValue() + event.getY()));
						
						} else {
							
							
						}
					}
	            	AdvancedLogicSimulator.renderer.repaint();
	            	
	            }
        	event.consume();
        	}	
        } 	
    };
    
    public EventHandler<MouseEvent> onMouseReleasedNode = new EventHandler<MouseEvent>() {
    	public void handle(MouseEvent event) {
    		if(currentMoveAction != null) {
    			currentMoveAction.end();
    			currentMoveAction = null;
    		}
    		event.consume();
    	}
    };
}
