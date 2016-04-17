package at.fishkog.als.ui.common.sidebar;

import java.util.HashMap;
import java.util.LinkedHashMap;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.CategoryManager;
import at.fishkog.als.sim.component.categories.ComponentCategory;
import at.fishkog.als.sim.data.meta.MetaValue;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class Sidebar extends VBox {

	private LinkedHashMap<String, TitledPane> panes;
	private HashMap<String, SidebarTreeView<Component>> content;
	
	private TableView<MetaValue<?>> metaTable;
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
        
        tableUpdater = new MetaTableUpdater(this.metaTable);
        
        metaPane.setContent(metaTable);
        metaBox.getChildren().add(metaPane);
        sidebarContainer.getChildren().add(metaBox);
        
        scrollPane.setContent(sidebarContainer);
    }
     
    @SuppressWarnings("unchecked")
	private void initMetaTable() {
    	TableColumn<MetaValue<?>, String> columnKey = new TableColumn<>(l.getString("Attribute"));
    	columnKey.setEditable(false);
    	
    	columnKey.setCellValueFactory(new Callback<CellDataFeatures<MetaValue<?>, String>, ObservableValue<String>>() {
    		public ObservableValue<String> call(CellDataFeatures<MetaValue<?>, String> p) {
    			if (p.getValue() != null) {
    	            return new SimpleStringProperty(p.getValue().id);
    	        } else {
    	            return new SimpleStringProperty("<no key>");
    	        }
    		}
    	});
    	
    	TableColumn<MetaValue<?>, Object> columnValue = new TableColumn<>(l.getString("Value"));
    	columnValue.setEditable(true);
    	
    	columnValue.setCellValueFactory(new Callback<CellDataFeatures<MetaValue<?>, Object>, ObservableValue<Object>>() {
    		public ObservableValue<Object> call(CellDataFeatures<MetaValue<?>, Object> p) {
    			if (p.getValue() != null) {
    	            return new SimpleObjectProperty<Object>(p.getValue().getValue());
    	        } else {
    	            return new SimpleObjectProperty<Object>("<no value>");
    	        }
    		}
    	});
    	
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
