package gui.manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.*;

import com.tcg.json.JSONUtils;


public abstract class SaveAndLoadManagerFD {
	
	public static JSONObject loadFlowDiagramFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	public static JSONObject loadGraphicalInfoFromJSON(String path) {
		JSONObject myObj = JSONUtils.getJSONObjectFromFile(path);
		return myObj;
	}
	
	public static void saveTextFileFromString(String text, String pathAndFileName) {
		// Write primitives to an output file
		try (DataOutputStream out = new DataOutputStream( new BufferedOutputStream (new FileOutputStream(pathAndFileName)))){
			out.writeChars(text);
			out.flush();
		} catch (IOException ex) {
       ex.printStackTrace();
		}
	}
	
	public static String getTextStringFromPath(String path) {
		String temp = "";
		
		// Read primitives
		try (DataInputStream in =
			new DataInputStream(
	            new BufferedInputStream(
	                new FileInputStream(path)))) {
	 
			//System.out.print("chars:   ");
	 
	    	// Check file length
	    	File fileIn = new File(path);
	    	//System.out.println("File size is " + fileIn.length() + " bytes");
	     
	    	for (int i = 0; i < fileIn.length()/2; ++i) {
	    		temp = temp + (char)in.readChar();
	    	}
	    	
	    	//System.out.println(temp);
	    	return temp;
		} catch (IOException ex) {
	        ex.printStackTrace();
	    }
		return temp;
	}	
}