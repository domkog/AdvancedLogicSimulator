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
		propsDefault.setProperty("LANG", "en_GB");
		
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
		
		propsLogDefault.setProperty("MainFilter", "ALL");
		propsLogDefault.setProperty("LogFileFilter", "WARNING");
		propsLogDefault.setProperty("ConsoleFilter", "ALL");
		
		propsLogDefault.setProperty("LogInConsole", "TRUE");
		propsLogDefault.setProperty("LogAsHTML", "TRUE");
		propsLogDefault.setProperty("LogAsTXT", "TRUE");
		
		propsLogDefault.setProperty("LogPackageInConsole", "TRUE");
		propsLogDefault.setProperty("LogPackageInLogFile", "TRUE");
		
	}	
}
