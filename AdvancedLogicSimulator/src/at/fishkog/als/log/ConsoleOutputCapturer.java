package at.fishkog.als.log;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import at.fishkog.als.AdvancedLogicSimulator;

public class ConsoleOutputCapturer extends PrintStream{
	public final StringBuilder buf;
    public final PrintStream underlying;

    ConsoleOutputCapturer(StringBuilder sb, OutputStream os, PrintStream ul) {
        super(os);
        this.buf = sb;
        this.underlying = ul;
    }

    public static ConsoleOutputCapturer create(PrintStream toLog) {
        try {
            final StringBuilder sb = new StringBuilder();
            Field f = FilterOutputStream.class.getDeclaredField("out");
            f.setAccessible(true);
            OutputStream psout = (OutputStream) f.get(toLog);
            return new ConsoleOutputCapturer(sb, new FilterOutputStream(psout) {
                public void write(int b) throws IOException {
                    super.write(b);
                    sb.append((char) b);
                    AdvancedLogicSimulator.cons.print((char) b);
                    
                }
            }, toLog);
        } catch (Exception shouldNotHappen) {
        	
        }
        return null;
    }
}
