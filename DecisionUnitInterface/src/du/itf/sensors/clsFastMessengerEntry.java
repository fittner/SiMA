/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package du.itf.sensors;

import java.util.Formatter;

import du.enums.eFastMessengerSources;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFastMessengerEntry implements Cloneable {
	protected eFastMessengerSources moSource;
	protected double mrIntensity;
	
	public double getIntensity() {
		return mrIntensity;
	}
	public void setIntensity(double prIntensity) {
		mrIntensity = prIntensity;
	}
	
	public eFastMessengerSources getSource() {
		return moSource;
	}
	public void setSourc(eFastMessengerSources poSource) {
		moSource = poSource;
	}
	
	public clsFastMessengerEntry(eFastMessengerSources poSource, double prIntensity) {
		moSource = poSource;
		mrIntensity = prIntensity;
		moClassName = "FastMessengerEntry";
	}
	
	protected String moClassName;
	

	public String logXML(int pnId) {
		String logEntry = "<Entry id=\""+pnId+"\">";
		
		logEntry += clsDataBase.addXMLTag("Source", moSource.name()); 
		logEntry += clsDataBase.addXMLTag("Intensity", new Double(mrIntensity).toString());
		
		logEntry += "</Entry>";

		return logEntry;		
	}
	
	public String logHTML() {
		String oResult = "";
		Formatter oDoubleFormatter = new Formatter();

		oResult +="source:"+moSource.name();
		oResult +=" intensity:"+oDoubleFormatter.format("%.2f",mrIntensity);
		
		return oResult;
	}

	@Override
	public String toString() {
		String oResult = "";
		oResult += moClassName+": source "+moSource.name()+" | intensity "+mrIntensity;
		return oResult;
	}	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsFastMessengerEntry oClone = (clsFastMessengerEntry)super.clone();
        	oClone.moSource = moSource;

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
