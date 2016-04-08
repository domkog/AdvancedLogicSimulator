package at.fishkog.als.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import at.fishkog.als.AdvancedLogicSimulator;

public class ALSConsoleFormatter extends Formatter{
	
	@Override
	public String format(LogRecord rec) {
		 StringBuilder buf = new StringBuilder();
		 
		 buf.append("[" + calcDate(rec.getMillis()) + "] ");
		 if(AdvancedLogicSimulator.logConfig.get("LogPackageInConsole").equalsIgnoreCase("TRUE")){
			 buf.append(rec.getSourceClassName() + ": " + rec.getSourceMethodName());
			 buf.append("\t \t");
		 }
		 buf.append(rec.getLevel() + ": " + rec.getMessage());
		 buf.append("\r\n");
		 
		 return buf.toString();
		
	}
	
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }
	
	public String getHead(Handler h) {
	      return "ADVANCED LOGIC SIMULATOR CONSOLE-LOG " + (new Date() + "\r\n \r\n");
	    }
}
