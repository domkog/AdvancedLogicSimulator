package at.fishkog.als.config;

import java.util.Properties;

public class ConfigDefault {
	public static Properties propsDefault = new Properties();
	public static Properties propsLogDefault = new Properties();
	
	public static void init(){
		//Layout Options
		propsDefault.setProperty("TRUE_COLOR", "Color.GREEN");
		propsDefault.setProperty("FALSE_COLOR", "Color.GREEN");
		propsDefault.setProperty("NULL_COLOR", "Color.BLUE");
		propsDefault.setProperty("ERROR_COLOR", "Color.RED");
		propsDefault.setProperty("UNKNOWN_COLOR", "Color.BLUE");
		
		//Language Options
		propsDefault.setProperty("LANG", "en");
		propsDefault.setProperty("CNTRY", "GB");
		
		//Comp Options
		propsDefault.setProperty("maxBit", "64");
		
		//DevOptions
		 /*
		 *ALL
		 *CONFIG
		 *INFO
		 *WARNING
		 *SEVERE
		 *OFF
		 * 
		 * Loggs everything Higher than the choosen
		 */
		
		propsLogDefault.setProperty("MAINFILTER", "ALL");
		propsLogDefault.setProperty("LOGFILEFILTER", "WARNING");
		propsLogDefault.setProperty("CONSOLEFILTER", "ALL");
		
		propsLogDefault.setProperty("LOGINCONSOLE", "TRUE");
		propsLogDefault.setProperty("LOGASHTML", "TRUE");
		propsLogDefault.setProperty("LOGASTXT", "TRUE");
		
		propsLogDefault.setProperty("LOGPACKAGEINCONSOLE", "TRUE");
		propsLogDefault.setProperty("LOGPACKAGEINLOGFILE", "TRUE");

	}	
}
