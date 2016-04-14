package at.fishkog.als.ui.common;

import java.awt.Desktop;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.log.ALSLogger;
import at.fishkog.als.ui.themes.DialogThemesManager;
import at.fishkog.als.utils.FileUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class DialogOptions {
	
	private PropertiesManager config;
	private PropertiesManager logConfig;
	
	private LanguageManager l;
	
	private int gRow;
	
	public DialogOptions() {
		this.config = AdvancedLogicSimulator.config;
		this.logConfig = AdvancedLogicSimulator.logConfig;
		
		this.l = AdvancedLogicSimulator.lang;
		
	}
	
	public void show(){		
		Dialog<Void> dialog = new Dialog<Void>();
		dialog.setTitle(l.getString("Options") + " - Advanced Logic Simulator");
		dialog.setHeaderText(l.getString("Options"));
		
		Window window = dialog.getDialogPane().getScene().getWindow();
		window.setOnCloseRequest(event -> {
			dialog.close();
			window.hide();
			
		});
		Stage stage = (Stage) window;
		stage.getIcons().add(new Image(AdvancedLogicSimulator.class.getClassLoader()
				.getResource("resources/icons/iconMain.png").toExternalForm()));
		
		/*
		 * ==============================================
		 * ========TABS==================================
		 * ============================================== 
		 */
		
		//Language Tab
		//============		
	    Tab tabLang = new Tab();	    

	    tabLang.setText(l.getString("Language"));
	    tabLang.setClosable(false);
	    
	    ImageView iconLang = new ImageView(AdvancedLogicSimulator.class.getClassLoader().getResource("resources/icons/iconLang.png").toExternalForm());
	  
	    iconLang.setFitHeight(20);
	    iconLang.setFitWidth(20);
	    iconLang.setPreserveRatio(true);
	    tabLang.setGraphic(iconLang);
	    
	    GridPane gridLang = new GridPane();
	    gridLang.setAlignment(Pos.CENTER);
	    gridLang.setHgap(10);
	    gridLang.setVgap(10);
	    gridLang.setPadding(new Insets(25,25,25,25));

	    tabLang.setContent(gridLang);
	    
	    gRow = -1;
	    
	    Locale[] availiableLocales = l.getLangs();
	    String[] langCodes = new String[availiableLocales.length];   
	    ObservableList<String> obsLangCodes = FXCollections.observableArrayList();
	    
	    int selectedLang = 0;
	    
	    obsLangCodes.add("--");
	    for (int i = 0; i<availiableLocales.length; i++) {
	    	langCodes[i] = availiableLocales[i].toString();
	    	obsLangCodes.add(availiableLocales[i].getDisplayName(availiableLocales[i]));
	    	
	    	/*TODO
	    	if(l.equals(availiableLocales[i]))
	    	*/
	    	if(availiableLocales[i].getCountry().equals(l.getCountry()) && availiableLocales[i].getLanguage().equals(l.getLanguage())) {
	    			selectedLang = i;
	    	
	    	}
	    }

	    //Choicebox Lang	 
	    ChoiceBox<String> cbLang = new ChoiceBox<String>(obsLangCodes);	  

	    cbLang.getSelectionModel().select(selectedLang + 1);
	    cbLang.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		if(new_value.intValue() != 0) {
		    		config.set("LANG", availiableLocales[new_value.intValue()-1].getLanguage());
		    		config.set("CNTRY", availiableLocales[new_value.intValue()-1].getCountry());
		    		
		    		l.changeLang(availiableLocales[new_value.intValue()-1]);
	    		}
			}
	    });
	    cbLang.setTooltip(new Tooltip(l.getString("SelectLanguage")));

	    gridLang.add(new Label(l.getString("Language")), 0, ++gRow);
	    gridLang.add(cbLang, 1, gRow);
	    
	    Button btnOpen = new Button(l.getString("Open"));
	    btnOpen.setOnAction((e) -> { 
	    	Desktop desktop = Desktop.getDesktop();
            try {
				desktop.open(l.getDir());

	        } catch (Exception ex) {
	            ALSLogger.logger.warning("File/Directory could´t be opened!");
	        	
	        }
	    	
        });
	    
	    gridLang.add(btnOpen, 0, ++gRow, 2,1);
	    //==========
	    
	    //Layout Tab
	    //==========
	    Tab tabLay = new Tab();
	    tabLay.setText(l.getString("Layout"));
	    tabLay.setClosable(false);
	    
	    ImageView iconLay = new ImageView(AdvancedLogicSimulator.class.getClassLoader().getResource("resources/icons/iconLayout.png").toExternalForm());
	    
	    iconLay.setFitHeight(20);
	    iconLay.setFitWidth(20);
	    iconLay.setPreserveRatio(true);
	    tabLay.setGraphic(iconLay);
	    
	    GridPane gridLay = new GridPane();
	    gridLay.setAlignment(Pos.TOP_LEFT);
	    gridLay.setHgap(10);
	    gridLay.setVgap(10);
	    gridLay.setPadding(new Insets(25,25,25,25));
	    
	    tabLay.setContent(gridLay);

	    gRow = -1;
	    
	    Button btnThemes = new Button(l.getString("ThemeManager"));
	    btnThemes.setOnAction((e) -> { 
	    	new DialogThemesManager().show();
	    	
        });      
	    
	    gridLay.add(btnThemes, 0, ++gRow);
	    
	    
	    //===========
	    
	    //Development Tab
	    //==========
	    Tab tabDev = new Tab();
	    tabDev.setText(l.getString("Development"));
	    tabDev.setClosable(false);
	    
	    ImageView iconDev = new ImageView(AdvancedLogicSimulator.class.getClassLoader().getResource("resources/icons/iconDev.png").toExternalForm());
	    
	    iconDev.setFitHeight(20);
	    iconDev.setFitWidth(20);
	    iconDev.setPreserveRatio(true);
	    tabDev.setGraphic(iconDev);
	    
	    GridPane gridDev = new GridPane();
	    gridDev.setAlignment(Pos.CENTER);
	    gridDev.setHgap(10);
	    gridDev.setVgap(10);
	    gridDev.setPadding(new Insets(25,25,25,25));
	    
	    tabDev.setContent(gridDev);
	    
	    gRow = -1;
	    
	    CheckBox cbx_html = new CheckBox("HTML");
	    cbx_html.setSelected(logConfig.get("LogAsHTML").equalsIgnoreCase("TRUE"));
	    cbx_html.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogAsHTML", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    gridDev.add(cbx_html, 0, ++gRow);
	    
	    CheckBox cbx_console = new CheckBox(l.getString("Console"));
	    cbx_console.setSelected(logConfig.get("LogInConsole").equalsIgnoreCase("TRUE"));
	    cbx_console.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogInConsole", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    gridDev.add(cbx_console, 1, gRow, 1, 1);
	    
	    CheckBox cbx_txt = new CheckBox("TXT");
	    cbx_txt.setSelected(logConfig.get("LogAsTXT").equalsIgnoreCase("TRUE"));
	    cbx_txt.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogAsTXT", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    gridDev.add(cbx_txt, 0, ++gRow);
	    
	    CheckBox cbx_pathConsole = new CheckBox(l.getString("LogPathInConsole"));
	    cbx_pathConsole.setSelected(logConfig.get("LogPackageInConsole").equalsIgnoreCase("TRUE"));
	    cbx_pathConsole.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogPackageInConsole", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    gridDev.add(cbx_pathConsole, 0, ++gRow);
	    
	    CheckBox cbx_pathFile = new CheckBox(l.getString("LogPathInLogFile"));
	    cbx_pathFile.setSelected(logConfig.get("LogPackageInLogFile").equalsIgnoreCase("TRUE"));
	    cbx_pathFile.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        public void changed(ObservableValue<? extends Boolean> ov,
	            Boolean old_val, Boolean new_val) {
	        	logConfig.set("LogPackageInLogFile", new_val ? "TRUE" : "FALSE");
	        }
	    });
	    gridDev.add(cbx_pathFile, 1, gRow);

	    Separator sep1 = new Separator();
	    gridDev.add(sep1, 0, ++gRow, 2, 1);
	    
	    //Choicebox Filter Main
        Label lbFilter = new Label(l.getString("SelectFilter"));
        gridDev.add(lbFilter, 0, ++gRow);
        
        ChoiceBox<String> cbFilter = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilter.getSelectionModel().select(Arrays.asList(FilterCode).indexOf(logConfig.get("MainFilter")));
	    cbFilter.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("MainFilter", FilterCode[new_value.intValue()]);

    			}
	    });
	    cbFilter.setTooltip(new Tooltip(l.getString("SelectFilter")));
	    
	    HBox hbCbFilter = new HBox(10);
	    hbCbFilter.setAlignment(Pos.CENTER_RIGHT);
	    hbCbFilter.getChildren().add(cbFilter);
	    
	    gridDev.add(hbCbFilter, 1, gRow);
	    
	    //Choicebox Filter Console
	    Label lbFilterCons = new Label(l.getString("SelectFilterConsole"));
        gridDev.add(lbFilterCons, 0, ++gRow);
	    
	    ChoiceBox<String> cbFilterCons = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterConsCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilterCons.getSelectionModel().select(Arrays.asList(FilterConsCode).indexOf(logConfig.get("ConsoleFilter")));
	    cbFilterCons.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("ConsoleFilter", FilterConsCode[new_value.intValue()]);
    		
    			}
	    });
	    cbFilterCons.setTooltip(new Tooltip(l.getString("SelectFilterConsole")));
	    
	    HBox hbCbConsFilter = new HBox(10);
	    hbCbConsFilter.setAlignment(Pos.CENTER_RIGHT);
	    hbCbConsFilter.getChildren().add(cbFilterCons);
	    
	    gridDev.add(hbCbConsFilter, 1, gRow);
	    
	  //Choicebox Filter Logfile
	    Label lbFilterFile = new Label(l.getString("SelectFilterFile"));
        gridDev.add(lbFilterFile, 0, ++gRow);
	    
	    ChoiceBox<String> cbFilterFile = new ChoiceBox<String>(FXCollections.observableArrayList(
	    	    "ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"));
	    String[] FilterFileCode = new String[]{"ALL", "CONFIG", "INFO", "WARNING", "SEVERE", "OFF"};
	    
	    
	    cbFilterFile.getSelectionModel().select(Arrays.asList(FilterFileCode).indexOf(logConfig.get("LogFileFilter")));
	    cbFilterFile.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
	    	public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
	    		logConfig.set("LogFileFilter", FilterFileCode[new_value.intValue()]);
    		
    			}
	    });
	    cbFilterFile.setTooltip(new Tooltip(l.getString("SelectFilterFile")));
	    
	    HBox hbCbFileFilter = new HBox(10);
	    hbCbFileFilter.setAlignment(Pos.CENTER_RIGHT);
	    hbCbFileFilter.getChildren().add(cbFilterFile);
	    
	    gridDev.add(hbCbFileFilter, 1, gRow);    
	    
	    Separator sep2 = new Separator();
	    gridDev.add(sep2, 0, ++gRow, 2, 1);
	    
	    //Button Lang-Files Refresh	    
	    Button btnLangRefresh = new Button(l.getString("RefreshLangFiles"));	    
        btnLangRefresh.setOnAction((e) -> { 
        	l.refreshLangFiles();
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Information:");
    		alert.setHeaderText(null);
    		alert.setContentText(l.getString("FilesRefreshed") + "!");

    		alert.showAndWait();
        });        
        
        HBox hbBtnLangRefresh = new HBox(10);
	    hbBtnLangRefresh.setAlignment(Pos.CENTER);
	    hbBtnLangRefresh.getChildren().add(btnLangRefresh);	    
	    
        gridDev.add(hbBtnLangRefresh, 0, ++gRow, 2, 1);
        
        Separator sep3 = new Separator();
	    gridDev.add(sep3, 0, ++gRow, 2, 1);
 
	    Button btnReset = new Button(l.getString("Reset"));
	    btnReset.setOnAction((e) -> { 
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(l.getString("Reset"));
    		alert.setHeaderText(l.getString("ResetHeader"));
    		alert.setContentText(l.getString("AppClear"));
    		
    		ButtonType buttonTypeYes = new ButtonType(l.getString("yes"));
    		ButtonType buttonTypeNo = new ButtonType(l.getString("no"));
    		ButtonType buttonTypeCancel = new ButtonType(l.getString("cancel"));
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == buttonTypeYes){
    		    config.reset();
    		    logConfig.reset();
    		    
    		    Alert alert2 = new Alert(AlertType.INFORMATION);
    		    alert2.setTitle("Information:");
    		    alert2.setHeaderText(null);
    		    alert2.setContentText(l.getString("ResetSuc"));

    		    alert2.showAndWait();
    			
    		}
        });
	    
	    gridDev.add(btnReset, 0, ++gRow);
	    
	    Button btnOpenApp = new Button(l.getString("OpenApp"));
	    btnOpenApp.setOnAction((e) -> { 
	    	Desktop desktop = Desktop.getDesktop();
            try {
				desktop.open(AdvancedLogicSimulator.pathAppdata);

	        } catch (Exception ex) {
	            ALSLogger.logger.warning("File/Directory could´t be opened!");
	        	
	        }
	    	
        });
	    
	    gridDev.add(btnOpenApp, 0, ++gRow);
	    
	    Button btnAppClear = new Button(l.getString("AppClear"));
	    btnAppClear.setOnAction((e) -> { 
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle(l.getString("AppClear"));
    		alert.setHeaderText(l.getString("AppClearHeader"));
    		alert.setContentText(l.getString("AppClearCont"));
    		
    		ButtonType buttonTypeYes = new ButtonType(l.getString("yes"));
    		ButtonType buttonTypeNo = new ButtonType(l.getString("no"));
    		ButtonType buttonTypeCancel = new ButtonType(l.getString("cancel"));
    		
    		alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);
    		
    		Optional<ButtonType> result = alert.showAndWait();
    		if (result.get() == buttonTypeYes){
    			FileUtils.deleteDir(AdvancedLogicSimulator.pathAppdata);
    		    
    		    Alert alert2 = new Alert(AlertType.INFORMATION);
    		    alert2.setTitle("Information:");
    		    alert2.setHeaderText(null);
    		    alert2.setContentText(l.getString("AppClearSuc"));

    		    alert2.showAndWait();
    			
    		}
        });
	    
	    gridDev.add(btnAppClear, 1, gRow);
	    //===========
	    
	    
	    
	    /*
		 * ==============================================
		 * ========Main-Panel==================================
		 * ============================================== 
		 */
	    
	    //Add Tabs to TabPane
		TabPane tabPane = new TabPane();
	    tabPane.getTabs().addAll(tabLang,tabLay,tabDev);
	    
	    
	    //Add TabPane to BorderPane	    
	    BorderPane borderPane = new BorderPane();
	    borderPane.setCenter(tabPane); 
	    
	    dialog.getDialogPane().setContent(borderPane);
	    
	    dialog.showAndWait();
	}
}
