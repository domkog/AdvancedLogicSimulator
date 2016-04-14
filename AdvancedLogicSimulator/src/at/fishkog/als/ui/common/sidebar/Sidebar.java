package at.fishkog.als.ui.common.sidebar;

import java.util.HashMap;
import java.util.LinkedHashMap;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.CategoryManager;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {

	public LinkedHashMap<String, TitledPane> panes;
	public HashMap<String, SidebarTreeView<Component>> content;
	
	private LanguageManager l = AdvancedLogicSimulator.lang;
	
    public Sidebar() {
        super();
        
        panes = new LinkedHashMap<>();
        content = new HashMap<>();

        setMinWidth(300);
        setMaxWidth(300);
        setMinHeight(600);

        TextField tfSearch = new TextField();
        tfSearch.setPromptText(l.getString("Search") + "...");
        this.getChildren().add(tfSearch);
        VBox.setMargin(tfSearch, new Insets(10, 20, 10, 20));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinWidth(300);
        scrollPane.setMaxWidth(300);
        
        getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox sidebarContainer = new VBox();
        sidebarContainer.setMinWidth(300);

        Accordion acc = new Accordion();

        this.initPanes();

        this.updateContent();

        acc.getPanes().addAll(panes.values());
        sidebarContainer.getChildren().add(acc);
        
        scrollPane.setContent(sidebarContainer);
    }
    
    private void initPanes() {
    	CategoryManager categoryManager = AdvancedLogicSimulator.categoryManager;
    	for(ComponentCategory cat: categoryManager.getCategories()) {
    		TitledPane tp = new TitledPane();
    		tp.setText(cat.getName());
    		panes.put(cat.getName(), tp);
    		SidebarTreeView<Component> treeView = new SidebarTreeView<>();
    		treeView.setMinHeight(50);
    		tp.setContent(treeView);
    		content.put(cat.getName(), treeView);
    	}
    }
    
    public void updateContent() {
    	CategoryManager categoryManager = AdvancedLogicSimulator.categoryManager;
    	for(ComponentCategory cat: categoryManager.getCategories()) {
    		SidebarTreeView<Component> treeView = content.get(cat.getName());
    		for(Component c: cat.components) {
    			treeView.getRoot().getChildren().add(new TreeItem<>(c));
    		}
    	}
    }
    
}