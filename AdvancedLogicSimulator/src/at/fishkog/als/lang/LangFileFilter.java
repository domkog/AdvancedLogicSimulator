package at.fishkog.als.lang;

import java.io.File;
import java.io.FileFilter;

public class LangFileFilter implements FileFilter{
	@Override
	public boolean accept(File f) {
		String filename = f.getName();
		String extension;
		String name;
		int extensionPos;
		int underlinePos;
		
		if (!f.isDirectory() && filename != null) {
			extensionPos = filename.lastIndexOf('.');
			extension = filename.substring(extensionPos + 1);		
			
			if (extension != null && extension.equals("properties")) {
				underlinePos = filename.indexOf('_');
				
				if (underlinePos > 0) {
					name = filename.substring(0, underlinePos);
					
					if(name != null && name.equals("lang")) {
						return true;
						
					}
				}
			}					
		}
		return false;
	}		
}
