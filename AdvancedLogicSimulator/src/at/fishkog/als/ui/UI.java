/* Copyright (c) 2016, Markus Hinkel, Dominik Koger. License information is located in the
 * at.fishkog.als.AdvancedLogicSimulator source code. */

package at.fishkog.als.ui;

import javafx.scene.Scene;

public abstract class UI{

	public abstract String getID();
	
	public abstract void show();
	public abstract void hide();
	
	public abstract void init();
	public abstract void dispose();
	
	public abstract Scene getScene();
	
}
