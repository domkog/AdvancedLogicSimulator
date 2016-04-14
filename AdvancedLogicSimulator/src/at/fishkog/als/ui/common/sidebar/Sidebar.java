package at.fishkog.als.ui.common.sidebar;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.CategoryManager;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class Sidebar extends VBox {

	private LinkedHashMap<String, TitledPane> panes;
	private HashMap<String, SidebarTreeView<Component>> content;
	
	private TableView metaTable;
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

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setMinWidth(400);
        scrollPane.setMaxWidth(300);
        
        getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox sidebarContainer = new VBox(30);
        sidebarContainer.setMinWidth(300);

        Accordion acc = new Accordion();

        this.initPanes();

        this.updateContent();

        acc.getPanes().addAll(panes.values());
        sidebarContainer.getChildren().add(acc);
        
        VBox metaBox = new VBox();
        TitledPane metaPane = new TitledPane();
        metaPane.setText(l.getString("Attribute"));
        
        metaTable = new TableView<>();
        metaTable.setEditable(true);
        metaTable.setMinWidth(400);
        metaTable.setMinHeight(550);
        
        this.initMetaTable();
        
        this.tableUpdater = new MetaTableUpdater(this.metaTable);
        
        metaPane.setContent(metaTable);
        metaBox.getChildren().add(metaPane);
        sidebarContainer.getChildren().add(metaBox);
        
        scrollPane.setContent(sidebarContainer);
    }
     
    private void initMetaTable() {
    	TableColumn<Map, MetaValue<?>> columnKey = new TableColumn<>(l.getString("Attribute"));
    	columnKey.setEditable(false);
    	
    	TableColumn<Map, MetaValue<?>> columnValue = new TableColumn<>(l.getString("Value"));

    	metaTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	metaTable.getSelectionModel().setCellSelectionEnabled(true);
    	
    	metaTable.getColumns().setAll(columnValue,columnKey);
    	
    	Callback<TableColumn<Map, MetaValue<?>>, TableCell<Map, MetaValue<?>>> cellFactoryForMap = (TableColumn<Map, MetaValue<?>> p) -> 
        new TextFieldTableCell(new StringConverter() {
            @Override
            public String toString(Object t) {
       	 	if(t!=null)  return t.toString(); else return "test";
        	 }
            @Override
            public Object fromString(String string) {
                return string;
                
            }
        });
    	 
        columnKey.setCellFactory(cellFactoryForMap);
        columnValue.setCellFactory(cellFactoryForMap);
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
