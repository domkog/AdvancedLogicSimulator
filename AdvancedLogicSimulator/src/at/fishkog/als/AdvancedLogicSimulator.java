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

import at.fishkog.als.config.ConfigDefault;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.lang.LanguageManager;
import at.fishkog.als.log.ALSLogger;
import at.fishkog.als.sim.component.Component;
import at.fishkog.als.sim.component.categories.Categories;
import at.fishkog.als.sim.component.categories.CategoryManager;
import at.fishkog.als.sim.component.placeable.logic.ANDComponent;
import at.fishkog.als.sim.component.placeable.logic.NANDComponent;
import at.fishkog.als.sim.component.placeable.logic.NORComponent;
import at.fishkog.als.sim.component.placeable.logic.NotComponent;
import at.fishkog.als.sim.component.placeable.logic.ORComponent;
import at.fishkog.als.sim.component.placeable.logic.XNORComponent;
import at.fishkog.als.sim.component.placeable.logic.XORComponent;
import at.fishkog.als.sim.component.placeable.wire.Wire;
import at.fishkog.als.ui.UIManager;
import at.fishkog.als.ui.common.MainUI;
import at.fishkog.als.ui.common.dialogs.DialogConsole;
import at.fishkog.als.ui.common.renderer.TempComponentRenderer;
import at.fishkog.als.ui.common.sidebar.Sidebar;
import at.fishkog.als.utils.ConsoleOutputCapturer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdvancedLogicSimulator extends Application {
	
	public static final String TITLE = "AdvancedLogicSimulator";
	public static final String VERSION = "InDev-1.1.0";
	public static final int COPYRIGHT_YEAR = 2016;
	
	public static AdvancedLogicSimulator instance;
	
	public Stage stage;
	public static UIManager uiManager;
	public static MainUI mainUi;
	
	public static TempComponentRenderer renderer;
	
	public static CategoryManager categoryManager;
	
	public static LogicCanvas logicCanvas;

	public static final String pathAppdata = System.getenv("APPDATA") + "\\AdvancedLogicSimulator";
	private static final File mainConfigFile = new File(pathAppdata + "\\config.xml");
	private static final File logConfigFile = new File(pathAppdata + "\\logConfig.xml");
	private static final File wireConfigFile = new File(pathAppdata + "\\wireConfig.xml");
	
	public static PropertiesManager config;
	public static PropertiesManager logConfig;
	public static PropertiesManager wireConfig;
	
	public static LanguageManager lang;
	
	public static ConsoleOutputCapturer cocOut;
	
	public static DialogConsole cons;
	
	
	//TODO TESTING
	public static Wire testWire;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Configs
		ConfigDefault.init();
		config = new PropertiesManager(mainConfigFile, true, ConfigDefault.propsDefault, "Main Options");	
		logConfig = new PropertiesManager(logConfigFile, true, ConfigDefault.propsLogDefault, "Logging Options");
		wireConfig = new PropertiesManager(wireConfigFile, true, ConfigDefault.propsWireDefault, "Wire-Color Options");
		
		//Logger		
		ALSLogger.init();
		
		//Language
		lang = new LanguageManager();
		
		cons = new DialogConsole();
		
		//Custom Console
		cocOut = ConsoleOutputCapturer.create(System.out);
		System.setOut(cocOut);
		
		instance = this;
		stage = primaryStage;

		ALSLogger.logger.info("Setting up JavaFx frame...");
		
		primaryStage.setTitle(TITLE + "-" + VERSION + "-" + COPYRIGHT_YEAR);
		
		categoryManager = new CategoryManager();
		this.registerCategories();
		logicCanvas = new LogicCanvas();
		logicCanvas.add(new ANDComponent(400, 400, 21));
		logicCanvas.add(new ORComponent(500, 400, 20));
		
		Component test = new ORComponent(500, 400, 20);
		
		logicCanvas.add(new Wire(test.getConnector(0),30,true));
		
		logicCanvas.add(test);
		//Initialize UIManager and show default scene
		uiManager = new UIManager();
		this.registerUIs();
		uiManager.show("main");
		
		primaryStage.getIcons().add(new Image(AdvancedLogicSimulator.class.getClassLoader()
				.getResource("resources/icons/iconMain.png").toExternalForm()));
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			this.exit();
			
		});		
		
		//TODO This throws a NPE, fix later
		Sidebar.tableUpdater.showData(test);

	}
	
	public void exit() {
		config.terminate();
		logConfig.terminate();
		wireConfig.terminate();
		cons.hide();
		
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
		Categories.LOGIC.components.add(new ANDComponent(0, 0, 0));
		Categories.LOGIC.components.add(new ORComponent(0, 0, 0));
		Categories.LOGIC.components.add(new	NotComponent(0, 0, 0));
		Categories.LOGIC.components.add(new XORComponent(0, 0, 0));
		Categories.LOGIC.components.add(new NANDComponent(0, 0, 0));
		Categories.LOGIC.components.add(new NORComponent(0, 0, 0));
		Categories.LOGIC.components.add(new XNORComponent(0, 0, 0));
		
		categoryManager.addCategory(Categories.IO);
		categoryManager.addCategory(Categories.STORAGE);
		categoryManager.addCategory(Categories.INPUT);
		ALSLogger.logger.info("Registered " + categoryManager.getCategories().size() + " categories.");
	}
	
}
