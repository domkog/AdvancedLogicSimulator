package at.fishkog.als.ui.common.sidebar;

import at.fishkog.als.sim.component.Component;
import javafx.scene.control.TreeCell;

public class SidebarTreeCell<T> extends TreeCell<T> {

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
        		
        	}
            setText(" " + item.toString());
        } else {
            setText(null);
        }
    }

}