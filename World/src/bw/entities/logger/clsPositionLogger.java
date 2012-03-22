/**
 * clsPositionLogger.java: BW - bw.entities.tools
 * 
 * @author deutsch
 * 30.04.2011, 13:46:36
 */
package bw.entities.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;


import ARSsim.physics2D.util.clsPose;
import statictools.clsGetARSPath;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 30.04.2011, 13:46:36
 * 
 */
public class clsPositionLogger {
	public ArrayList<Position> moPositionHistory;
	private final int uid;
	private static final String newline = System.getProperty("line.separator");
	private String moFilename;
	private final int maxArrayLength = 0;
	private boolean writeToFile = true;
	
	public double maxX;
	public double minX;
	public double maxY;
	public double minY;
	
	
	
	public clsPositionLogger(int uid) {
		this.uid = uid;
		moPositionHistory = new ArrayList<Position>();
		
		maxX = 0;
		maxY = 0;

		minX = 0;
		minY = 0;

	}
	
	public Position getLastEntry() {
		return moPositionHistory.get(moPositionHistory.size()-1);
	}
	
	public void add(double x, double y, double a) {
		if (moPositionHistory.size() == 0) {
			maxX = x;
			maxY = y;
			minX = x;
			minY = y;
		} else {
			if (x>maxX) {maxX=x;}
			if (x<minX) {minX=x;}
			if (y>maxY) {maxY=y;}
			if (y<minY) {minY=y;}
		}
		
		Position oPos = new Position(x, y, a);
		moPositionHistory.add(oPos);		
		enforceMaxSize();
		if (writeToFile) {
			writeLineToFile(oPos.toCSV()+newline);
		}
	}
	
	public void add(clsPose oPose) {
		add(oPose.getPosition().x, oPose.getPosition().y, oPose.getAngle().radians);
	}
	
    
	@SuppressWarnings("unused")
	private void enforceMaxSize() {
    	if (maxArrayLength > 0) {
    		while (moPositionHistory.size() > maxArrayLength) {
    			moPositionHistory.remove(0);
    		}
    	}
    }
    
	private String getColumnTitles() {
		return "#step;x;y;a"+newline;
	}
	
	private String getPositionLine(int i) {
		Position oPos = moPositionHistory.get(i);
		return oPos.toCSV()+newline;
	}
	
	public String toCSV() {
		String csv = "";
		
		csv += getColumnTitles();
		
		for (int i=0;i<moPositionHistory.size();i++) {
			csv += getPositionLine(i);
		}
		
		return csv;
	}
	
	private String getFilename() {
		if (moFilename==null || moFilename.length()==0) {
			moFilename = clsGetARSPath.getLogFilename("pos_"+uid);
		}
		return moFilename;
	}
	
	private void writeLineToFile(String poLine) {
		String oFilename = getFilename();
	    try{
	   	    // Create file 
	   	    FileWriter fstream = new FileWriter(oFilename,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        out.write(poLine);
	        out.flush();
	   	    //Close the output stream
	   	    out.close();
	     }catch (Exception e){//Catch exception if any
	   	      System.err.println("Error: " + e.getMessage());
	    }  
	}	

}
