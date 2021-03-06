package at.fishkog.als.ui.common;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.ui.UI;
import at.fishkog.als.ui.actions.Actions;
import at.fishkog.als.ui.common.dialogs.DialogOptions;
import at.fishkog.als.ui.common.renderer.ComponentRenderer;
import at.fishkog.als.ui.common.sidebar.Sidebar;
import at.fishkog.als.ui.handlers.SceneInputHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MainUI extends UI {

	public VBox root;
	public Scene scene;
	
	public Canvas canvasBackground;
	public PannableCanvas canvasComponents;
	public SceneInputHandler inputHandler;
	
	public ToolBar footer;
	public Text mousePos;
	
	private LanguageManager l;
	
	public MenuItem menuItemUndo;
	
	@Override
	public String getID() {
		return "main";
	}

	@Override
	public void show() {
		this.l = AdvancedLogicSimulator.lang;
		
		MenuBar menuBar = new MenuBar();
		menuBar.setId("mainMenuBar");
        Menu menuFile = new Menu(l.getString("File"));
        Menu menuEdit = new Menu(l.getString("Edit"));
        
        menuItemUndo = new MenuItem(l.getString("Undo"));  
        menuItemUndo.setId("MainMenuBarSub");
        menuItemUndo.setAccelerator(KeyCombination.keyCombination("CTRL+Z"));
        menuItemUndo.setOnAction((e) -> {
        	//TODO Change Actions location -> Project
        	Actions.instance.undo();
        });
        menuEdit.getItems().addAll(menuItemUndo);
        
        Menu menuView = new Menu(l.getString("View"));
 
      //Help Menu
        Menu menuHelp = new Menu(l.getString("Help"));
        MenuItem menuItemOptions = new MenuItem(l.getString("Options"));  
        menuItemOptions.setId("MainMenuBarSub");
        menuItemOptions.setOnAction((e) -> {
        	new DialogOptions().show();
        });
        
        MenuItem menuItemConsole = new MenuItem(l.getString("Console"));
        menuItemConsole.setAccelerator(KeyCombination.keyCombination("CTRL+SHIFT+C"));
        menuItemConsole.setId("MainMenuBarSub");
        menuItemConsole.setOnAction((event) -> {
        	AdvancedLogicSimulator.cons.show();
        });
        
        menuHelp.getItems().addAll(menuItemOptions,menuItemConsole);
        
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp);
        root.getChildren().add(menuBar);
        
        ToolBar header = new ToolBar(
        		new Button(l.getString("NewProject")),
        		new Button(l.getString("Delete")));
        root.getChildren().add(header);
        
		HBox hWrapper = new HBox(1);
		
		VBox whiteboard = new VBox();
		whiteboard.setPrefHeight(900);
		whiteboard.setPrefWidth(1300);
				
		// create canvas
		this.canvasComponents = new PannableCanvas(1000, 1000);		 		
		Canvas canvasGrid = new Canvas(500,500);
		
		TabPane tabPane = new TabPane();
		Tab main = new Tab();
		main.setText("Main");
		
		Pane canvasPane = new Pane(canvasComponents, canvasGrid);
		canvasPane.setId("Comp-Grid");
		
		Sidebar sidePanel = new Sidebar();
		sidePanel.prefHeightProperty().bind(root.heightProperty());
		hWrapper.getChildren().add(sidePanel);
		
		tabPane.prefHeightProperty().addListener((arg0, from, to) -> {
        	canvasGrid.setHeight(to.doubleValue());
        	AdvancedLogicSimulator.renderer.repaint();
        });
		
		tabPane.prefWidthProperty().addListener((arg0, from, to) -> {
        	canvasGrid.setWidth(to.doubleValue());
        	AdvancedLogicSimulator.renderer.repaint();
        	
        });
		
		main.setContent(canvasPane);        

		AdvancedLogicSimulator.renderer = new ComponentRenderer(this, canvasGrid, canvasComponents); 
		
		tabPane.getTabs().add(main);
		hWrapper.getChildren().add(tabPane);
		hWrapper.getChildren().add(canvasPane);		
		hWrapper.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				canvasPane.setPrefWidth(arg2.doubleValue());
				tabPane.setTabMaxWidth(arg2.doubleValue());
				tabPane.setPrefWidth(arg2.doubleValue());
			}
		});
		
		hWrapper.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				canvasPane.setPrefHeight(arg2.doubleValue());
				tabPane.setTabMaxHeight(arg2.doubleValue());
				tabPane.setPrefHeight(arg2.doubleValue());

			}
		});
		
		root.getChildren().add(hWrapper);
		
		footer = new ToolBar();
		HBox space = new HBox();
		HBox.setHgrow(space, Priority.ALWAYS);
		footer.getItems().add(space);
		footer.getItems().add(mousePos = new Text(l.getString("Mouse") + " : X: - Y: - "));
		root.getChildren().add(footer);
	}

	@Override
	public void hide() {
	}

	@Override
	public void init() {
		AdvancedLogicSimulator.instance.stage.setMinHeight(600);
		AdvancedLogicSimulator.instance.stage.setMinWidth(900);
		root = new VBox();
		root.setPrefSize(1600, 900);
		root.setMinSize(900, 600);
		scene = new Scene(root);
		String css = MainUI.class.getResource("main.css").toExternalForm();
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);

	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Scene getScene() {
		return scene;
	}	
}
