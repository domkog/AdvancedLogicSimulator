package at.fishkog.als.log;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

import at.fishkog.als.AdvancedLogicSimulator;

public class ALSConsoleHandler extends ConsoleHandler{
	protected void setOutputStream(OutputStream out) throws SecurityException {
		super.setOutputStream(System.out);
		
	}
	
	@Override
	 public void publish(LogRecord log) {
		AdvancedLogicSimulator.cons.print(new ALSConsoleFormatter().format(log));
		super.publish(log);
	 
	}
}
