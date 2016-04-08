package at.fishkog.als.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.config.PropertiesManager;

public class ALSLogger {
	private static final String path = System.getenv("APPDATA") + "\\AdvancedLogicSimulator\\Logs";
	private static File dir = new File(path);

	private static FileHandler txtHandler;
	private static FileHandler HTMLHandler;
	
	public static Logger logger;
	
	private static PropertiesManager logConfig;
	
	@SuppressWarnings("unused")
	private static Formatter formatterHTML;
		
	static public void init() {
		logConfig = AdvancedLogicSimulator.logConfig;
		
		if(!dir.exists()) dir.mkdir();
	
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setUseParentHandlers(false);
		
		if(logConfig.get("MainFilter").equalsIgnoreCase("ALL")) {
			logger.setLevel(Level.ALL);
		
		}else if(logConfig.get("MainFilter").equalsIgnoreCase("CONFIG")) {
			logger.setLevel(Level.CONFIG);
			
		}else if(logConfig.get("MainFilter").equalsIgnoreCase("INFO")) {
			logger.setLevel(Level.INFO);
			
		}else if(logConfig.get("MainFilter").equalsIgnoreCase("WARNING")) {
			logger.setLevel(Level.WARNING);

		}else if(logConfig.get("MainFilter").equalsIgnoreCase("SEVERE")) {
			logger.setLevel(Level.SEVERE);

		}else if(logConfig.get("MainFilter").equalsIgnoreCase("OFF")) {
			logger.setLevel(Level.OFF);
	
		}
			    
		if(logConfig.get("LogInConsole").equalsIgnoreCase("TRUE")) {
			ALSConsoleHandler consoleHandler = new ALSConsoleHandler();
			
			if(logConfig.get("ConsoleFilter").equalsIgnoreCase("ALL")) {
				consoleHandler.setLevel(Level.ALL);
			
			}else if(logConfig.get("ConsoleFilter").equalsIgnoreCase("CONFIG")) {
				consoleHandler.setLevel(Level.CONFIG);
				
			}else if(logConfig.get("ConsoleFilter").equalsIgnoreCase("INFO")) {
				consoleHandler.setLevel(Level.INFO);
				
			}else if(logConfig.get("ConsoleFilter").equalsIgnoreCase("WARNING")) {
				consoleHandler.setLevel(Level.WARNING);

			}else if(logConfig.get("ConsoleFilter").equalsIgnoreCase("SEVERE")) {
				consoleHandler.setLevel(Level.SEVERE);

			}else if(logConfig.get("ConsoleFilter").equalsIgnoreCase("OFF")) {
				consoleHandler.setLevel(Level.OFF);
		
			}
			consoleHandler.setFormatter(new ALSConsoleFormatter());
			logger.addHandler(consoleHandler);			
		}
	    
		try {
			if(logConfig.get("LogAsTXT").equalsIgnoreCase("TRUE")) {
				txtHandler = new FileHandler(path + "\\Log-"+ calcDate(new Date()) +".txt",true);
				txtHandler.setFormatter(new ALSTXTFormatter());
				
				if(logConfig.get("LogFileFilter").equalsIgnoreCase("ALL")) {
					txtHandler.setLevel(Level.ALL);
				
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("CONFIG")) {
					txtHandler.setLevel(Level.CONFIG);
					
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("INFO")) {
					txtHandler.setLevel(Level.INFO);
					
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("WARNING")) {
					txtHandler.setLevel(Level.WARNING);

				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("SEVERE")) {
					txtHandler.setLevel(Level.SEVERE);

				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("OFF")) {
					txtHandler.setLevel(Level.OFF);
					
				}
				
				logger.addHandler(txtHandler);
			}
				
			if(logConfig.get("LogAsHTML").equalsIgnoreCase("TRUE")) {
				HTMLHandler = new FileHandler(path + "\\Log-"+ calcDate(new Date()) +".html",true);
				HTMLHandler.setFormatter(new ALSHTMLFormatter());
				
				if(logConfig.get("LogFileFilter").equalsIgnoreCase("ALL")) {
					HTMLHandler.setLevel(Level.ALL);
				
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("CONFIG")) {
					HTMLHandler.setLevel(Level.CONFIG);
					
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("INFO")) {
					HTMLHandler.setLevel(Level.INFO);
					
				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("WARNING")) {
					HTMLHandler.setLevel(Level.WARNING);

				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("SEVERE")) {
					HTMLHandler.setLevel(Level.SEVERE);

				}else if(logConfig.get("LogFileFilter").equalsIgnoreCase("OFF")) {
					HTMLHandler.setLevel(Level.OFF);
					
				}
				
				logger.addHandler(HTMLHandler);
			}
		
		} catch (IOException e) {
		      e.printStackTrace();
		      throw new RuntimeException("Problems with creating the log files");
	    }

	}
	
	private static String calcDate(Date date) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MMM-dd");
	    return date_format.format(date);
	 }
}
