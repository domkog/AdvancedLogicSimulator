package at.fishkog.als.ui.common.sidebar;

import java.util.HashMap;
import java.util.LinkedHashMap;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.CategoryManager;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class Sidebar extends VBox {

	private LinkedHashMap<String, TitledPane> panes;
	private HashMap<String, SidebarTreeView<Component>> content;
	
	private final TableView<MetaValue<?>> metaTable;
	public static MetaTableUpdater tableUpdater;
	
	private LanguageManager l = AdvancedLogicSimulator.lang;
	
    public Sidebar() {
        super();
        
        panes = new LinkedHashMap<>();
        content = new HashMap<>();

        setMinWidth(400);
        setMaxWidth(300);
        setMinHeight(600);

        TextField tfSearch = new TextField();
        tfSearch.setPromptText(l.getString("Search") + "...");
        this.getChildren().add(tfSearch);
        VBox.setMargin(tfSearch, new Insets(10, 20, 10, 20));

        TabPane tabPane = new TabPane();
        tabPane.setMinWidth(400);
        tabPane.setMaxWidth(400);
        
        Tab componentsTab = new Tab(l.getString("tabComponents"));
        componentsTab.setClosable(false);
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinWidth(400);
        scrollPane.setMaxWidth(400);
        
        componentsTab.setContent(scrollPane);
        
        getChildren().add(tabPane);
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        VBox sidebarContainer = new VBox(30);
        sidebarContainer.setMinWidth(400);

        Accordion acc = new Accordion();
        
        this.initPanes();

        this.updateContent();

        acc.getPanes().addAll(panes.values());
        sidebarContainer.getChildren().add(acc);
        
        scrollPane.setContent(sidebarContainer);
        
        /*Meta tab*/
        
        Tab metaTab = new Tab(l.getString("tabAttributes"));
        metaTab.setClosable(false);
        
        VBox metaBox = new VBox();
        
        metaTable = new TableView<>();
        metaTable.setMinWidth(400);
        metaTable.setMinHeight(600);
        
        metaTable.prefHeightProperty().bind(metaBox.heightProperty());
        
        this.initMetaTable();
        
        metaBox.getChildren().add(metaTable);
        
        VBox.setVgrow(metaBox, Priority.ALWAYS);
        
        tableUpdater = new MetaTableUpdater(this.metaTable);
        
        metaTab.setContent(metaBox);
        
        /*Project tab*/
        
        Tab projectTab = new Tab(l.getString("tabProjects"));
        projectTab.setClosable(false);
        
        VBox projectBox = new VBox();
        
        TitledPane project1 = new TitledPane();
        project1.setText("Project1");
        
        TreeView<String> treeView1 = new TreeView<String>(new TreeItem<>(""));
        treeView1.setShowRoot(false);
        
        project1.setContent(treeView1);
        TitledPane project2 = new TitledPane();
        project2.setText("Project2");
        
        TreeView<String> treeView2 = new TreeView<String>(new TreeItem<>(""));
        treeView2.setShowRoot(false);
        
        project2.setContent(treeView2);
        
        projectBox.getChildren().addAll(project1, project2);
        VBox.setVgrow(projectBox, Priority.ALWAYS);

        projectTab.setContent(projectBox);
        tabPane.getTabs().addAll(componentsTab, metaTab, projectTab);
        
        treeView1.getRoot().getChildren().add(new TreeItem<>("Test"));
        TreeItem<String> test = new TreeItem<>("Test2");
        test.getChildren().add(new TreeItem<>("Test3"));
        test.getChildren().add(new TreeItem<>("Test4"));
        treeView1.getRoot().getChildren().add(test);
        
    }
     
	private void initMetaTable() {
		TableColumn<MetaValue<?>, ?> columnKey = new TableColumn<>(l.getString("Attribute"));    	
    	TableColumn<MetaValue<?>, ?> columnValue = new TableColumn<>(l.getString("Value"));
    	
    	metaTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	metaTable.getSelectionModel().setCellSelectionEnabled(true);
    	
    	metaTable.getColumns().setAll(columnKey, columnValue);

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
