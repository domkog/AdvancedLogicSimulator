package at.fishkog.als.config;

import java.util.Properties;

public class ConfigDefault {
	public static Properties propsDefault = new Properties();
	public static Properties propsLogDefault = new Properties();
	public static Properties propsWireDefault = new Properties();
	
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
		propsDefault.setProperty("MAXBIT", "64");
		
		//DevOptions
		propsDefault.setProperty("SHOWHITBOX", "FALSE");
		
		//LogOptions
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
		
		
		
		//WIRE PROPS
		propsWireDefault.setProperty("TRUE_R", "0");
		propsWireDefault.setProperty("TRUE_G", "0");
		propsWireDefault.setProperty("TRUE_B", "0");
		propsWireDefault.setProperty("TRUE_OP", "1");

		propsWireDefault.setProperty("FALSE_R", "0");
		propsWireDefault.setProperty("FALSE_G", "0");
		propsWireDefault.setProperty("FALSE_B", "0");
		propsWireDefault.setProperty("FALSE_OP", "1");

		propsWireDefault.setProperty("NULL_R", "0");
		propsWireDefault.setProperty("NULL_G", "0");
		propsWireDefault.setProperty("NULL_B", "0");
		propsWireDefault.setProperty("NULL_OP", "1");
		
		propsWireDefault.setProperty("UNKNOWN_R", "0");
		propsWireDefault.setProperty("UNKNOWN_G", "0");
		propsWireDefault.setProperty("UNKNOWN_B", "0");
		propsWireDefault.setProperty("UNKNOWN_OP", "1");
		
		propsWireDefault.setProperty("ERROR_R", "0");
		propsWireDefault.setProperty("ERROR_G", "0");
		propsWireDefault.setProperty("ERROR_B", "0");
		propsWireDefault.setProperty("ERROR_OP", "1");
		
		propsWireDefault.setProperty("INVISIBLE_R", "0");
		propsWireDefault.setProperty("INVISIBLE_G", "0");
		propsWireDefault.setProperty("INVISIBLE_B", "0");
		propsWireDefault.setProperty("INVISIBLE_OP", "1");

		
	}	
}
