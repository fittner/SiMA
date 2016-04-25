

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FileWriter {

	String fileDestination = "/treeMapData.json";
	/**
	 * Constructor: will clean the file complete JSON-file, where data shall be
	 * written
	 */
	public FileWriter() throws IOException {

		File tempFile = new File("");
		tempFile.getAbsolutePath();
		this.fileDestination = tempFile.getAbsolutePath() + fileDestination;	
		deleteFileContent(fileDestination);

	}

	

	/**
	 * Always erases the file content in order to fill with single-Step
	 * information.
	 * 
	 * @author wilker
	 * @param option
	 * 
	 */
	private void deleteFileContent(String fileDestination ) {
	

		try {
			RandomAccessFile raf = new RandomAccessFile(fileDestination, "rw");

			// sets up the basic layout of the file and fills it with
			// the basic term , needed for JavaScript
			raf.setLength(0);
			raf.seek(0);
				raf.writeBytes("");	
			raf.close();
		} catch (Exception e) {
		}

	}


	public void writeTreemapData(JSONArray treemapFull ) {
		
		deleteFileContent(fileDestination);
	
		try {
			RandomAccessFile raf = new RandomAccessFile(fileDestination, "rw");
		
		
		
			raf.writeBytes(treemapFull.toString() );
		 
			/* for easier use, we initially use a JSONArray as carrier.
			 *  but the array may not persist, so we delete the "[" and "]" at beginning and end of the file 
			 */
			raf.seek(0);
			raf.writeBytes(" ");
			raf.seek(raf.length() - 1); //
			raf.writeBytes(" ");
			//now the correct format
			raf.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


}