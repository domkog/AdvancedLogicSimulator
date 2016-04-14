package at.fishkog.als.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.Properties;

import at.fishkog.als.log.ALSLogger;

public class PropertiesManager {
	private Properties props;	
	private Properties defaultProps;
	
	private File dir;
	private File configFile;
	
	private boolean xml;
	
	private final static String BASICNAME = "ADVANCED LOGIC SIMULATOR - CONFIG: ";
	private String title;
	
	/**
	 * Creates an new Instance of a PropertiesManager/ConfigManager
	 * 
	 * @param configFile java.io.File of config file or Java File-Object of the config file directory
	 * @param xml Boolean if u want to save as xml-file. Alternative: *.txt
	 * @param defaultProperties defaultProperties, checking and repairing the configFile-Property
	 * @param name Name of the config File, as String
	 */
	public PropertiesManager(File configFile,Boolean xml,Properties defaultProperties, String name){
		this.title = BASICNAME  + name;
		this.defaultProps = defaultProperties;
		this.xml = xml;
		
		if(!configFile.isDirectory()) {
			this.dir = configFile.getParentFile();
			this.configFile = configFile;
			
		}else{
			this.dir = configFile;
			this.configFile = new File(dir, "config." + (xml? "xml" : "properties"));
		}
		if (!dir.exists()) dir.mkdir();
		
		this.props=loadConfig(this.configFile, xml);
			this.props=checkConfigFile(this.props,this.defaultProps);
		
	}

	/**
	 * Creates an new Instance of a PropertiesManager/ConfigManager
	 * 
	 * @param configFile java.io.File of config file or Java File-Object of the config file directory
	 * @param xml Boolean if u want to save as xml-file. Alternative: *.txt
	 * @param defaultProperties defaultProperties, checking and repairing the configFile-Property
	 */
	public PropertiesManager(File configFile, Boolean xml, Properties defaultProperties) {
		this(configFile, xml, new Properties(), "");
		
	}
	
	/**
	 * Creates an new Instance of a PropertiesManager/ConfigManager
	 * 
	 * @param configFile java.io.File of config file or Java File-Object of the config file directory
	 * @param xml Boolean if u want to save as xml-file. Alternative: *.txt
	 * @param name Name of the config File, as String
	 */
	public PropertiesManager(File configFile, Boolean xml, String name) {
		this(configFile, xml, new Properties(), name);
	}
	
	/**
	 * Creates an new Instance of a PropertiesManager/ConfigManager
	 * 
	 * @param configFile java.io.File of config file or Java File-Object of the config file directory
	 * @param xml Boolean if u want to save as xml-file. Alternative: *.txt
	 */
	public PropertiesManager(File configFile, Boolean xml) {
		this(configFile, xml, new Properties());
	
	}
	
	/** Returns value for key as String 
	 * @param key key for the Entry in the properties
	 * @return  value (as String) of the Entry with the key 'key'
	 * */
	public String get(String key){
		return this.props.getProperty(key.toUpperCase());
		
	}
	
	/** Sets the value for the Entry with the given key
	 * @param key The key of the entry wanted to set
	 * @param value value wanted to set for the entry, casted to a String
	 * 
	 */
	public void set(String key, Object value) {
			this.props.setProperty(key.toUpperCase(), value.toString());
			
	}
	
	private Properties checkConfigFile(Properties testingProps, Properties defaultProps){
		if(testingProps!=null) {
			for(String key : defaultProps.stringPropertyNames()) {
				if(!defaultProps.containsKey(key)){ 
					testingProps.setProperty(key, defaultProps.getProperty(key));	  
				}
			}
		} else {
			testingProps = defaultProps;
		}
		return testingProps;
	}
	
	private Properties loadConfig(File configFile, Boolean xml) {		
		Properties tempProps = new Properties(defaultProps);
		
		try {			
			if(xml) {
				InputStream inputStream = new FileInputStream(configFile);
				tempProps.loadFromXML(inputStream);
				inputStream.close();
				return tempProps;
				
			} else {
				Reader reader = new FileReader(configFile);			
				tempProps.load(reader);
				reader.close();
				return tempProps;
			}
		
		} catch (Exception e) {
			//Can´t log, cause Logger isn´t initialized yet
			return null;
			
		}
	}

	private void writeConfig(File configFile, Properties props, String title) {	
		try {			
			if(this.xml) {
				OutputStream outputStream = new FileOutputStream(configFile);
				props.storeToXML(outputStream, title);
				outputStream.close();
			
			} else {
				FileWriter writer = new FileWriter(configFile);
				props.store(writer, title);
				writer.close();
			}
			
		} catch (FileNotFoundException e) {	
			ALSLogger.logger.warning("ConfigFile couldn´t be found found!");
			
		} catch (IOException e) {
			ALSLogger.logger.warning(e.getMessage());
			
		}
	}
	
	public void terminate() {
		writeConfig(this.configFile,this.props,this.title);
	}
	
	public void reset() {
		this.props = this.defaultProps;
		writeConfig(this.configFile,this.defaultProps,this.title);
		
	}
}
