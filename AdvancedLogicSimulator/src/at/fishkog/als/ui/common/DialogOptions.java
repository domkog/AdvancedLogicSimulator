package at.fishkog.als.ui.common;

import java.util.Arrays;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Window;

public class DialogOptions {
	
	private PropertiesManager config;
	private PropertiesManager logConfig;
	
	public DialogOptions() {
		this.config = AdvancedLogicSimulator.config;
		this.logConfig = AdvancedLogicSimulator.logConfig;
		
	}
	
	public void show(){
				
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle("Advanced Logic Simulator - Options");
		dialog.setHeaderText(null);
		
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> {
			dialog.close();
			window.hide();
		});
		
		//Language Tab
	    Tab tabLang = new Tab();
	    tabLang.setGraphic(new Circle(0,0,10));
	    tabLang.setText("Language");
	    HBox hboxLang = new HBox();
	    
	    ChoiceBox<String> cbLang = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "English", "Deutsch", "--"));
	    String[] langCode = new String[]{"EN","DE","--"}; 
	    
	    //Choicebox Lang
	    cbLang.getSelectionModel().select(Arrays.asList(langCode).indexOf(config.get("LANG")));
	    cbLang.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
    				config.set("LANG", langCode[new_value.intValue()]);
    		
    			}
	    });
	    cbLang.setTooltip(new Tooltip("Select the language"));
	    
	    hboxLang.getChildren().addAll(new Label("Language"),cbLang);
	    tabLang.setContent(hboxLang);
	    //==========
	    
	    //Layout Tab
	    Tab tabLay = new Tab();
	    tabLay.setGraphic(new Circle(0,0,10));
	    tabLay.setText("Layout");
	    HBox hboxLay = new HBox();
	    hboxLay.getChildren().add(new Label("Layout"));
	    tabLay.setContent(hboxLay);
	    //===========
	    
	    //Layout Tab
	    Tab tabDev = new Tab();
	    tabDev.setGraphic(new Circle(0,0,10));
	    tabDev.setText("Development");
	    HBox hboxDev = new HBox();
	    
	    CheckBox cbx1 = new CheckBox("HTML");
	    cbx1.setSelected(logConfig.get("LogAsHTML").equalsIgnoreCase("TRUE"));
	    cbx1.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogAsHTML", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    
	    CheckBox cbx2 = new CheckBox("TXT");
	    cbx2.setSelected(logConfig.get("LogAsTXT").equalsIgnoreCase("TRUE"));
	    cbx2.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogAsTXT", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    
	    CheckBox cbx3 = new CheckBox("CONSOLE");
	    cbx3.setSelected(logConfig.get("LogInConsole").equalsIgnoreCase("TRUE"));
	    cbx3.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogInConsole", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    
	    CheckBox cbx4 = new CheckBox("Log Path in Console");
	    cbx4.setSelected(logConfig.get("LogPackageInConsole").equalsIgnoreCase("TRUE"));
	    cbx4.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogPackageInConsole", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    
	    CheckBox cbx5 = new CheckBox("Log Path in Log File");
	    cbx5.setSelected(logConfig.get("LogPackageInLogFile").equalsIgnoreCase("TRUE"));
	    cbx5.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogPackageInLogFile", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    
	  //Choicebox Filter Main
	    ChoiceBox<String> cbFilter = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilter.getSelectionModel().select(Arrays.asList(FilterCode).indexOf(logConfig.get("MainFilter")));
	    cbFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("MainFilter", FilterCode[new_value.intValue()]);
    		
    			}
	    });
	    cbFilter.setTooltip(new Tooltip("Select the Filter for the Console"));
	    
	    //Choicebox Filter Console
	    ChoiceBox<String> cbFilterCons = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterConsCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilterCons.getSelectionModel().select(Arrays.asList(FilterConsCode).indexOf(logConfig.get("ConsoleFilter")));
	    cbFilterCons.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("ConsoleFilter", FilterConsCode[new_value.intValue()]);
    		
    			}
	    });
	    cbFilterCons.setTooltip(new Tooltip("Select the Filter for the Console"));
	    
	  //Choicebox Filter Logfile
	    ChoiceBox<String> cbFilterFile = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterFileCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilterFile.getSelectionModel().select(Arrays.asList(FilterFileCode).indexOf(logConfig.get("LogFileFilter")));
	    cbFilterFile.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("LogFileFilter", FilterFileCode[new_value.intValue()]);
    		
    			}
	    });
	    
	    cbFilterFile.setTooltip(new Tooltip("Select the Filter for the LogFile"));
	    
	    hboxDev.getChildren().addAll(cbx1,cbx2,cbx3,cbx4,cbx5,cbFilterCons,cbFilterFile,cbFilter);
	    tabDev.setContent(hboxDev);
	    //===========
	    
	    //Add Tabs to Pane
		TabPane tabPane = new TabPane();
	    tabPane.getTabs().addAll(tabLang,tabLay,tabDev);
	    
	    
	    //Add TabPane to Borderpane
	    BorderPane borderPane = new BorderPane();
	    borderPane.setCenter(tabPane);
	    borderPane.setMinSize(300, 300);
	    
	    dialog.getDialogPane().setContent(borderPane);
	    
	    dialog.showAndWait();
	}
}
