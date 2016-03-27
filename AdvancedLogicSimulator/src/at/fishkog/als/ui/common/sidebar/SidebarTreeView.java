package at.fishkog.als.ui.common.sidebar;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SidebarTreeView<T> extends TreeView<T> {

    public SidebarTreeView() {
        super(new TreeItem<>());

        setShowRoot(false);

        setCellFactory(new SidebarTreeViewCellFactory<>(this));

        getStyleClass().add("treeview-sidebar");
    }

}