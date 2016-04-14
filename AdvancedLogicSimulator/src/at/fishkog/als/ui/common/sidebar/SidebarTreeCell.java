package at.fishkog.als.ui.common.sidebar;

import at.fishkog.als.sim.component.Component;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;

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
        		ImageView icon = new ImageView(comp.renderContext.getImage());
        		icon.setPreserveRatio(true);
        		icon.setFitWidth(20);
        		icon.setFitHeight(15);
        		
        		this.setGraphic(icon);
        	}
            setText(" " + item.toString());
        } else {
            setText(null);
        }
    }

}