package at.fishkog.als.ui.common.dialogs;

import java.awt.Desktop;
import java.io.File;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.log.ALSLogger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Window;

public class DialogThemesManager {
	private PropertiesManager config;
	private PropertiesManager logConfig;
	
	private LanguageManager l;
	
	private static File dir = new File(System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\Themes");
	
	public DialogThemesManager() {
		this.config = AdvancedLogicSimulator.config;
		this.logConfig = AdvancedLogicSimulator.logConfig;
		
		this.l = AdvancedLogicSimulator.lang;
	}
	
	public void show(){		
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("Advanced Logic Simulator - " + l.getString("ThemeManager"));
		dialog.setHeaderText(null);
		dialog.setResizable(true);
		
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> {
			dialog.close();
			window.hide();
			
		});
		
		AnchorPane mainPane = new AnchorPane();
		
		SplitPane splitPane = new SplitPane();
		splitPane.setPrefWidth(800);
		splitPane.setPrefHeight(600);
		
		AnchorPane.setBottomAnchor(splitPane, 0.0);
		AnchorPane.setTopAnchor(splitPane, 0.0);
		AnchorPane.setLeftAnchor(splitPane, 0.0);
		AnchorPane.setRightAnchor(splitPane, 0.0);
		
		Pane sp1 = new StackPane();
		sp1.setMinWidth(200);
		
		Pane sp2 = new StackPane();
		sp2.setMinWidth(400);
		
		//StackPane left
		TableView table = new TableView();
		TableColumn colTheme = new TableColumn("Theme");

        table.getColumns().addAll(colTheme);
        
        sp1.getChildren().add(table);
        
        
        //StackPane right
        GridPane grid = new GridPane();
	    grid.setAlignment(Pos.CENTER_LEFT);
	    grid.setHgap(10);
	    grid.setVgap(10);
	    grid.setPadding(new Insets(25,25,25,25));

	    int gRow = -1;
	    
	    Label lbColorBase = new Label(l.getString("ColorPickerBase"));
	    grid.add(lbColorBase, 0, ++gRow);
	    
	    Label lbVColorBase = new Label();
	    grid.add(lbVColorBase, 1, gRow);
	    
	    Label lbColorBG = new Label(l.getString("ColorPickerBG"));
	    grid.add(lbColorBG, 0, ++gRow);
	    
	    Label lbVColorBG = new Label();
	    grid.add(lbVColorBG, 1, gRow);
	    
	    Label lbColorGrid = new Label(l.getString("ColorPickerGrid"));
	    grid.add(lbColorGrid, 0, ++gRow);
	    
	    Label lbVColorGrid = new Label();
	    grid.add(lbVColorGrid, 1, gRow);
	    
	    Label lbFontSize = new Label(l.getString("FontSize"));
	    grid.add(lbFontSize, 0, ++gRow);
	    
	    Label lbVFontSize = new Label();
	    grid.add(lbVFontSize, 1, gRow);
	    
	    
	    //ButtonBar
	    ButtonBar btnbar = new ButtonBar();
	    
	    Button btnNew = new Button(l.getString("New") + "...");
	    Button btnEdit = new Button(l.getString("Edit") + "...");
	    Button btnDel = new Button(l.getString("Delete"));
	    Button btnOpen = new Button(l.getString("Open") + "...");
	    
	    btnOpen.setOnAction((e) -> { 
	    	Desktop desktop = Desktop.getDesktop();
            try {
				desktop.open(this.dir);

	        } catch (Exception ex) {
	            ALSLogger.logger.warning("File/Directory could´t be opened!");
	        	
	        }
	    	
        }); 
        
	    btnbar.getButtons().addAll(btnNew, btnEdit, btnOpen, btnDel);
	    
	    
	    AnchorPane rightAnchorPane = new AnchorPane();
	    AnchorPane.setBottomAnchor(btnbar, 10.0);
	    AnchorPane.setRightAnchor(btnbar, 10.0);
	    
	    AnchorPane.setLeftAnchor(grid, 5.0);
	    AnchorPane.setRightAnchor(grid, 5.0);
	    AnchorPane.setTopAnchor(grid, 15.0);
	    
	    rightAnchorPane.getChildren().addAll(grid, btnbar);
	    
	    sp2.getChildren().addAll(rightAnchorPane);
	    
		splitPane.getItems().addAll(sp1, sp2);
		splitPane.setDividerPositions(0.6f);
		
		mainPane.getChildren().add(splitPane);
		dialog.getDialogPane().setContent(mainPane);
	    dialog.showAndWait();
	}
}

/*
Label lbColorBase = new Label(l.getString("ColorPickerBase"));
grid.add(lbColorBase, 0, ++gRow);

ColorPicker colorPickerBase = new ColorPicker();
grid.add(colorPickerBase, 1, gRow);

Label lbColorBG = new Label(l.getString("ColorPickerBG"));
grid.add(lbColorBG, 0, ++gRow);

ColorPicker colorPickerBG = new ColorPicker();
grid.add(colorPickerBG, 1, gRow);

Label lbColorGrid = new Label(l.getString("ColorPickerGrid"));
grid.add(lbColorGrid, 0, ++gRow);

ColorPicker colorPickerGrid = new ColorPicker();
grid.add(colorPickerGrid, 1, gRow);
*/
