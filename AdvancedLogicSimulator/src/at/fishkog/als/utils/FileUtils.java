package at.fishkog.als.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import at.fishkog.als.AdvancedLogicSimulator;
import at.fishkog.als.log.ALSLogger;

public class FileUtils {
	
	/**
     * Export a resource embedded into a Jar file to the local file path.
     *
     * @param resourceName ie.: "/SmartLibrary.dll"
     * @param targetDir Target directory as File
     * @param targetName Targetname of the File
     * @throws Exception
     */
	public static void exportResource(String resourceName, File targetDir, String targetName) throws IOException, FileNotFoundException{
        InputStream stream;
        OutputStream resStreamOut;

        stream = AdvancedLogicSimulator.class.getClassLoader().getResourceAsStream(resourceName);
        
        if(stream == null){
        	ALSLogger.logger.warning("Stream equals null");
        }
        
        int readBytes;
        byte[] buffer = new byte[4096];
		resStreamOut = new FileOutputStream(targetDir + "\\" + targetName);
		
        while ((readBytes = stream.read(buffer)) > 0) {
            resStreamOut.write(buffer, 0, readBytes);
        }

        stream.close();
        resStreamOut.close();

    }
	
	public static boolean deleteDir(File dir) 
	{ 
	  if (dir.isDirectory()) 
		{ 
		  String[] children = dir.list(); 
		  for (int i=0; i<children.length; i++)
		  { 
		    boolean success = deleteDir(new File(dir, children[i])); 
		    if (!success) 
		    {  
		      return false; 
		    } 
		  } 
		}  
	  return dir.delete(); 
	} 

}
