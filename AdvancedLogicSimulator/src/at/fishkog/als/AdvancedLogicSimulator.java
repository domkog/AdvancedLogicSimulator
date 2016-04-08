/*
 * Copyright (c) 2016, Markus Hinkel, DominikKoger.
 * 
 * This file is part of the AdvancedLogicSimulator source code. The latest
 * version is available at xxxx.xxxx.at.
 *
 * AdvancedLogicSimulator is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * AdvancedLogicSimulator is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */

package at.fishkog.als;

import java.io.File;
import at.fishkog.als.component.categories.Categories;
import at.fishkog.als.component.categories.CategoryManager;
import at.fishkog.als.component.gates.ANDComponent;
import at.fishkog.als.component.gates.ORComponent;
import at.fishkog.als.config.ConfigDefault;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.log.ALSLogger;
import at.fishkog.als.ui.UIManager;
import at.fishkog.als.ui.common.MainUI;
import at.fishkog.als.ui.common.renderer.ComponentRenderer;
import javafx.application.Application;
import javafx.stage.Stage;

public class AdvancedLogicSimulator extends Application {
	
	public static final String TITLE = "AdvancedLogicSimulator";
	public static final String VERSION = "InDev-1.1.0";
	public static final int COPYRIGHT_YEAR = 2016;
	
	public static AdvancedLogicSimulator instance;
	
	public Stage stage;
	public static UIManager uiManager;
	public static MainUI mainUi;
	
	public static ComponentRenderer renderer;
	
	public static CategoryManager categoryManager;
	
	public static LogicCanvas logicCanvas;
	
	public static final File mainConfigFile = new File(System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\config.xml");
	public static final File logConfigFile = new File(System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\logConfig.xml");
	
	public static PropertiesManager config;
	public static PropertiesManager logConfig;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ConfigDefault.init();
		config = new PropertiesManager(mainConfigFile, true, ConfigDefault.propsDefault, "Main Options");
		logConfig = new PropertiesManager(logConfigFile, true, ConfigDefault.propsLogDefault, "Logging Options");
		
		ALSLogger.init();
		
		//TODO make this work
		if (logConfig.get(Boolean.class, "LogAsTXT")) {
			ALSLogger.logger.info("geht");
		
		}
		
		LanguageManager lang = new LanguageManager();
		lang.getAllLangs();
		
		instance = this;
		stage = primaryStage;
		
		ALSLogger.logger.info("Setting up JavaFx frame...");
		
		primaryStage.setTitle(TITLE + "-" + VERSION + "-" + COPYRIGHT_YEAR);
		
		categoryManager = new CategoryManager();
		this.registerCategories();
		logicCanvas = new LogicCanvas();
		logicCanvas.add(new ANDComponent(400, 400));
		logicCanvas.add(new ANDComponent(500, 400));
		logicCanvas.add(new ANDComponent(600, 400));
		
		//Initialize UIManager and show default scene
		uiManager = new UIManager();
		this.registerUIs();
		uiManager.show("main");
		
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			this.exit();
			
		});
	}
	
	public void exit() {
		config.terminate();
		logConfig.terminate();
		
	}
	
	private void registerUIs() {
		ALSLogger.logger.info("Adding UIs...");
		uiManager.register(mainUi = new MainUI());
		ALSLogger.logger.info("Registerd " + uiManager.uis.size() + " UIs.");
	}
	
	private void registerCategories() {
		ALSLogger.logger.info("Adding component categories...");
		categoryManager.addCategory(Categories.WIRES);
		categoryManager.addCategory(Categories.GATES);
		categoryManager.addCategory(Categories.LOGIC);
		Categories.LOGIC.components.add(new ANDComponent(0, 0));
		Categories.LOGIC.components.add(new ORComponent(0, 0, 0, 0));
		
		categoryManager.addCategory(Categories.IO);
		categoryManager.addCategory(Categories.STORAGE);
		categoryManager.addCategory(Categories.INPUT);
		ALSLogger.logger.info("Registered " + categoryManager.getCategories().size() + " categories.");
	}
	
}
