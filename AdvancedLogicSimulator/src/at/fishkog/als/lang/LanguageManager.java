package at.fishkog.als.lang;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;
import at.fishkog.als.log.ALSLogger;
import at.fishkog.als.utils.FileUtils;

public class LanguageManager {

	private static File dir = new File(System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\Langs");
	
	private static FileFilter fileFilter = new LangFileFilter();
	
	private Locale currentLocale;
	private ResourceBundle messages;
	
	private static PropertiesManager config = AdvancedLogicSimulator.config;
	
	private Locale[] availiableLangs;
	
	private static final String[] DEFAULT_LANGS = {"lang_en_GB", "lang_de_AT"};
	
	/**
	 * Creates a new instance of lang with the currentLocale
	 * @param currentLocale The current Locale
	 */
	public LanguageManager(Locale currentLocale) {				
		this.currentLocale = currentLocale;
		setup();
		this.availiableLangs = getAllLangs();
		
		try {
			URL[] urls = {dir.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			this.messages = ResourceBundle.getBundle("lang", currentLocale, loader);
			
		} catch (MalformedURLException e){
			ALSLogger.logger.warning(e.getMessage());
			
		}
	}
	
	/**
	 * Creates a new instance of lang with the currentLocale, taken out of the config File
	 */
	public LanguageManager() {
		this(new Locale(config.get("LANG"),config.get("CNTRY")));
		
	}
	
	/**
	 * Creates an new Instance of a PropertiesManager/ConfigManager
	 * 
	 * @param key the key for the wanted Message as String
	 * @return Returns the message for the key in the right language
	 */
	public String getString(String key) {
		if(this.messages == null) {
			ALSLogger.logger.config("Missing language file!");
			return "Language Files missing!";
		}
		try {
			return this.messages.getString(key);		
		} catch (MissingResourceException e) {
			ALSLogger.logger.config("Missing translation for key: " + key + " in: " + this.currentLocale.getLanguage());
			return "NO TRANSLATION FOR: " + key;
		}
	}
	
	/**
	 * Returns all availiable Languages
	 * 
	 * @return all availiable Languages as Locale[]
	 */
	public Locale[] getLangs() {
		return availiableLangs;
		
	}
	/**
	 * Returns the current Locale
	 * 
	 * @return the Current locale as Locale[]
	 */
	public Locale getCurrentLocale() {
		return this.currentLocale;
		
	}
	
	/**
	 * Sets the current Locale for this instance
	 * 
	 * DO NOT USE THIS IF YOU WANT TO CHANGE THE LANGUAGE OF THIS INSTANCE!
	 * use changeLang() instead
	 * 
	 * @param newLocale the new locale you want to set
	 */
	public void setCurrentLocale(Locale newLocale) {
		this.currentLocale = newLocale;
		
	}
	
	/**
	 * Returns the current Country
	 * 
	 * 
	 * @return the current Country as String eg.: "GB" or "AT"
	 */
	public String getCountry() {
		return this.currentLocale.getCountry();
		
	}
	
	/**
	 * Returns the current Language
	 * 
	 * 
	 * @return the current Country as String eg.: "en" or "de"
	 */
	public String getLanguage() {
		return this.currentLocale.getLanguage();
		
	}
	
	
	/**
	 * Changes the language of this instance
	 * 
	 * 
	 * @param newLocale the new Locale you want to change this instance to
	 */
	public void changeLang(Locale newLocale) {
		this.currentLocale = newLocale;
		
		try {
			ResourceBundle.clearCache();
			URL[] urls = {dir.toURI().toURL()};
			ClassLoader loader = new URLClassLoader(urls);
			this.messages = ResourceBundle.getBundle("lang", currentLocale, loader);
			
		} catch (MalformedURLException e){
			ALSLogger.logger.warning(e.getMessage());
			
		}
		
	}
	
	private Locale[] getAllLangs() {
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
	
	/**
	 * Replaces all AppData-Langfiles with the default Ones
	 */
	public void refreshLangFiles(){
		String resourceName;
		if(!dir.exists()) dir.mkdir();		
		for(String k: DEFAULT_LANGS) {
			resourceName = k + ".properties";
			try {
				FileUtils.exportResource("resources/langs/" + resourceName, dir, k + ".properties");
				
			} catch (Exception e) {
				ALSLogger.logger.warning(e.getMessage());
				
			}
		}
	}
	
	private void setup(){		
		String resourceName;
		if(!dir.exists()) dir.mkdir();		
		for(String k: DEFAULT_LANGS) {
			resourceName = k + ".properties";
			
			File targetFile = new File(dir.getAbsolutePath() + "/" + k + ".properties");
			if(!targetFile.exists()) {
				try {
					FileUtils.exportResource("resources/langs/" + resourceName, dir, k + ".properties");
					
				} catch (Exception e) {
					ALSLogger.logger.warning(e.getMessage());
					
				}
			}
		}
	}
	
	/**
	 * Returns a name for the locale that is appropriate for display to the user. 
	 * 
	 * @return the current Country as String eg.: "GB" or "AT"
	 */
	@Override
	public String toString() {
		return this.currentLocale.getDisplayName();
		
	}
	
	/**
	 * Returns a name for the locale that is appropriate for display to the user,
	 * in the Format of dispLocale
	 * 
	 * 
	 * @param dispLocale the format u want to use for the display as Local
	 * @return the currentLocale as String eg. (with Locale en_GB: "English (Great Brittain)" or "German (Austria)"
	 * eg. (with Locale de_AT): "Englisch (England)" or "Deutsch (Österreich)
	 */
	public String toString(Locale dispLocale) {
		return this.currentLocale.getDisplayName(dispLocale);
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LanguageManager) {
			LanguageManager lObj = (LanguageManager) obj;
			
			if(lObj.getCountry().equals(this.getCountry()) && lObj.getLanguage().equals(this.getLanguage())) {
				return true;
			}	
		}
		return false;
	}
	
	public File getDir() {
		return dir;
		
	}
}
