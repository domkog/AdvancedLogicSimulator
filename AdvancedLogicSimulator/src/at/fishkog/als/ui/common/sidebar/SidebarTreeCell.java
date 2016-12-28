package at.fishkog.als.ui.common.sidebar;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.sim.component.BasicComponent;
import at.fishkog.als.sim.component.Component;
import javafx.scene.control.TreeCell;

public class SidebarTreeCell<T> extends TreeCell<T> {

	private BasicComponent newComp;
	
    public SidebarTreeCell() {
        super();

        getStyleClass().add("treecell-sidebar");
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty) {
        	if(item instanceof Component) {
        		Component comp = (Component) item;
        		
        		javafx.scene.Group graphic = comp.getRenderparts();
        		if(graphic != null) {
        			graphic.setScaleX(0.5);
        			graphic.setScaleY(0.5);
        		}
        		this.setGraphic(graphic);
        		
        		this.setOnDragDetected((e) -> {         	
                	if(item instanceof BasicComponent) {
                		//TODO Set standard or somewhat other thing
                		newComp = ((BasicComponent) item).createNew((int) e.getSceneX(), (int)e.getSceneY(), 5, 32);
                		AdvancedLogicSimulator.logicCanvas.add(newComp);
                		
                	}
            	});
        		
        		this.setOnMouseDragged((e) -> {
        			if(newComp instanceof BasicComponent) {
                		if(newComp != null) {
                			newComp.location.x.setValue((int) (e.getSceneX()-AdvancedLogicSimulator.mainUi.canvasComponents.getTranslateX()));
	                		newComp.location.y.setValue((int) (e.getSceneY()-AdvancedLogicSimulator.mainUi.canvasComponents.getTranslateY()));

                		}	
                	}
        		});        		
        	}

            setText(" " + item.toString());
        } else {
            setText(null);
        }
       
    }

}