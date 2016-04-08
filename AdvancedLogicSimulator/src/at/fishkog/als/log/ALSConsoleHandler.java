package at.fishkog.als.log;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;

public class ALSConsoleHandler extends ConsoleHandler{
	protected void setOutputStream(OutputStream out) throws SecurityException {
		super.setOutputStream(System.out);
		
	}
}
