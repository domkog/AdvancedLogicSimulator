package at.fishkog.als.lang;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.log.ALSLogger;

public class LanguageManager {

	private static File dir = new File(System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\langs");
	
	private static FileFilter fileFilter = new LangFileFilter();
	
	private Locale currentLocale;
	private ResourceBundle messages;
	
	private static PropertiesManager config = AdvancedLogicSimulator.config;
	
	public LanguageManager(Locale currentLocale) {				
		this.currentLocale = currentLocale;
		
		try {
			this.messages = ResourceBundle.getBundle("resources.langs.lang",this.currentLocale);
			
		} catch (NullPointerException e) {
			ALSLogger.logger.config("Missing language file!: " + e.getMessage());
			this.messages = null;
			
		}
	}
	
	public LanguageManager() {
		this(new Locale(config.get("LANG")));
		
	}
	
	public String getString(String key) {
		if(this.messages == null) {
			ALSLogger.logger.config("Missing language file!");
			return "xXLanguage Files missing!Xx";
			
		}
		try {
			return this.messages.getString(key.toUpperCase());		
			
		} catch (MissingResourceException e) {
			ALSLogger.logger.config("Missing translation for key: " + key + " in: " + this.currentLocale.getLanguage());
			return "xXNO TRANSLATION FOR: " + key + "Xx";
			
		}
	}
	
	public Locale[] getAllLangs() {
		File[] files = dir.listFiles(fileFilter);
		
		int length;
		
		Locale[] locales;
		
		String tmp;
		
		if (files != null) {
			length = files.length;
			locales = new Locale[length];
			
			for (int i = 0; i < length; i++) {
				tmp = files[i] .getName();
				locales[i] = new Locale(tmp.substring(5,7),tmp.substring(8,10));
			}			
			return locales;
			
		}
		return null;
	}
		
	//TODO check appdata for lang files; copy missing from resource folder
	private void setup(){
		if(!dir.exists()) dir.mkdir();
		
		 
	}
}
