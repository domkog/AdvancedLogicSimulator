package at.fishkog.als.ui.common;


import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.ui.UI;
import at.fishkog.als.ui.common.renderer.ComponentRenderer;
import at.fishkog.als.ui.common.sidebar.Sidebar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
	
	public Canvas canvasBackground, canvasObjects;
	public InputHandler inputHandler;
	
	public ToolBar footer;
	public Text mousePos;
	
	private LanguageManager l;
	
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
        		new Button(l.getString("Delete")), 
        		new Button(l.getString("Undo")));
        root.getChildren().add(header);
        
		HBox hWrapper = new HBox(1);
		Sidebar sidePanel = new Sidebar();
		sidePanel.prefHeightProperty().bind(root.heightProperty());
		VBox whiteboard = new VBox();
		whiteboard.setPrefHeight(900);
		whiteboard.setPrefWidth(1300);
		
		canvasBackground = new Canvas(1300, 900);
		canvasObjects = new Canvas(1300, 900);
		inputHandler = new InputHandler(canvasObjects);
		
		AdvancedLogicSimulator.renderer = new ComponentRenderer(this, canvasBackground, canvasObjects);
		AdvancedLogicSimulator.renderer.repaint();
		
		hWrapper.getChildren().add(sidePanel);
		Pane canvasPane = new Pane(canvasBackground, canvasObjects);
		canvasPane.setId("Comp-Grid");
		hWrapper.getChildren().add(canvasPane);
		hWrapper.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				canvasBackground.setWidth(arg2.doubleValue());
				canvasObjects.setWidth(arg2.doubleValue());
				AdvancedLogicSimulator.renderer.repaint();
			}
		});
		
		hWrapper.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				canvasBackground.setHeight(arg2.doubleValue());
				canvasObjects.setHeight(arg2.doubleValue());
				AdvancedLogicSimulator.renderer.repaint();
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
