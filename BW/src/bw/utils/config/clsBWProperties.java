package bw.utils.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class clsBWProperties extends Properties {
	private static final String P_DELIMITER = ";";
	private static final String P_ESCAPE = "\\"; // don't change this value - escape is \\
	private static final String P_INCLUDE = "@";
	
	private static final String P_RANDOM = "§"; //don't blame me - roland states that the law is pure random
	private static final String P_REGEXP_ESCAPE = "\\\\";
	private static final String P_REGEXP_DELIMITER = "(?<=(("+P_REGEXP_ESCAPE+P_REGEXP_ESCAPE+"){0,9}))"+P_DELIMITER;
 
	private static final long serialVersionUID = 3052402669291342862L;

	private String moBaseDir = "";
	
	public clsBWProperties(String poBaseDir) {
		setBaseDir(poBaseDir);
	}
	
	public void setBaseDir(String poBaseDir) {
		moBaseDir = poBaseDir;
		if (!moBaseDir.endsWith(System.getProperty("file.separator")) && poBaseDir.length()>0) {
			moBaseDir += System.getProperty("file.separator");
		}
	}
	
	private static String escapeDelim(String value) {
		if (value.contains(P_DELIMITER)) {
			throw new java.lang.UnsupportedOperationException("escaping of strings currently buggy - please don't use the delimiter "+P_DELIMITER+" in your strings");
		}
		//TODO implement correct escaping and unescaping
	//	value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE+P_ESCAPE); // escape the escape sequence - \\ is converted to \\\\
	//	value = value.replaceAll(P_DELIMITER, P_ESCAPE+P_DELIMITER); // escape the delimiter - ; is converted to \\;
		// note: "\\;" is converted to "\\\\\\;" - this has to dealt with in unescape Delim und the regexp has to deal with "\\". this is converted
		// to "\\\\" and in the imploded string the value has a ; attached. e.g. "a", "\\", and "c" are imploded to "a;\\\\;c". thus. the 
		// regexp used for split has to include all cases where an even number of escape sequence in front of a delimiter is found.  
		// "a;\\\\\\;c" equals "a" and "\\;c". "a;\\\\;c" equals "a", "\\", and "c".
		return value;
	}

	private static String ListToString(List<String> value)  {
		String v = "";
		for (String s:value) {
			v+=escapeDelim(s)+P_DELIMITER;
		}
		
		if (v.length()>0){
			v = v.substring(0, v.length() - P_DELIMITER.length() );
		}
		
		return v;
	}
	
	public static clsBWProperties readProperties(String poBaseDir, String poFilename) {
		clsBWProperties p2 = new clsBWProperties(poBaseDir);
        
		if (poFilename.length()>0) {
	    try
	    {
	        FileInputStream propInFile = new FileInputStream( poFilename );
	        p2.load( propInFile );
	        
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }		
		}
		
        return p2;
	}
	
	private static List<String> StringToList(String value) {
		String[] r = value.split(P_REGEXP_DELIMITER);
		List<String> res = new ArrayList<String>();
		
		for (String s:r) {
			res.add( unescapeDelim(s) );
		}
		
		return res;
	}
	private static String unescapeDelim(String value) {
		//TODO implement correct escaping and unescaping		
		// reverse escapeDelim
	//	value = value.replaceAll(P_REGEXP_ESCAPE+P_DELIMITER, P_DELIMITER);
	//	value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE);
		return value;
	}
	public static void writeProperties(clsBWProperties poProp, String poBaseDir, String poFilename, String poComments) {
		if (!poBaseDir.endsWith(System.getProperty("file.separator")) && poBaseDir.length()>0 ) {
			poBaseDir += System.getProperty("file.separator");
		}
		
	    try
	    {
	      FileOutputStream propOutFile =
	         new FileOutputStream( poBaseDir+poFilename );
	      
	      poProp.store(propOutFile, poComments);
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }
	}

	public void addPrefix(String prefix) {
		prefix += ".";
		
		Iterator<String> i = stringPropertyNames().iterator();
		
		while (i.hasNext()) {
			String key = i.next();
			super.setProperty(prefix+key, (String) remove(key));
		}
	}
	private String escapeValue(String value) {
		if (value.startsWith(P_INCLUDE)) {
			value = P_ESCAPE+value;
		} else if (value.startsWith(P_RANDOM)) {
			value = P_ESCAPE+value;
		}
		
		return value;
	}

	
	@Override
	public String getProperty(String key) {
		String res = super.getProperty(key);
		
		if (res.startsWith(P_RANDOM)) {
			//insuperior approach - a double value is converted to a string and to a double back again. 
			//(usually, getPropertyDouble is called in case of random values)
			res = new Double(clsRandomProperties.getRandom( res.substring( P_RANDOM.length() ))).toString();
		} else {
			res = unescapeValue(res);
		}
		
		return res;
	}
	
	@Override
	public String getProperty(String key, String defaultValue) {
		return unescapeValue(super.getProperty(key, defaultValue));		
	}
	public boolean getPropertyBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
	public double getPropertyDouble(String key) {
		return Double.parseDouble(getProperty(key));
	}		
	public float getPropertyFloat(String key) {
		return Float.parseFloat(getProperty(key));
	}	
	public int getPropertyInt(String key) {
		return Integer.parseInt(getProperty(key));
	}
	public List<String> getPropertyList(String key) {
		return StringToList(getProperty(key));
	}	
	
	public String getPropertyString(String key) {
		return getProperty(key);
	}
	private void insertInclude(String key) {
		String includeFileName = (super.getProperty(key)).substring( P_INCLUDE.length() );
		
		clsBWProperties oOtherProperties = clsBWProperties.readProperties(moBaseDir, includeFileName);
		oOtherProperties.addPrefix(key);
		
		remove(key);
		
		putAll(oOtherProperties);
	}
	private void insertIncludes(ArrayList<String> replaceCandidates) {
		for (String key:replaceCandidates) {
			insertInclude(key);
		}
	}
	
	@Override
	public void load(InputStream inStream) throws IOException {
		super.load(inStream);
		
		insertIncludes( scan4Includes() );		
	}

	@Override
	public void load(Reader reader) throws IOException {
		throw new java.lang.UnsupportedOperationException(); //only the load(InputStream) reader is allowed - its the law. TD
		/*
		super.load(reader);
		
		insertIncludes( scan4Includes() );
		*/		
	}
	
	@Override
	public void loadFromXML(InputStream in) throws IOException, InvalidPropertiesFormatException {
		throw new java.lang.UnsupportedOperationException(); //only the load(InputStream) reader is allowed - its the law. TD
		/*
		super.loadFromXML(in);
		
		insertIncludes( scan4Includes() );
		*/
	}
	
	private ArrayList<String> scan4Includes() {
		ArrayList<String> replaceCandidates = new ArrayList<String>();
		Iterator<String> i = stringPropertyNames().iterator();
		
		while (i.hasNext()) {
			String key = i.next();
			String value = super.getProperty(key);
			if (value.startsWith(P_INCLUDE)) {
				replaceCandidates.add(key);
			}
		}		
		
		return replaceCandidates;
	}
	
	public Object setProperty(String key, boolean value) {
		return setProperty(key, Boolean.toString(value));
	}
	
	public Object setProperty(String key, double value) {
		return setProperty(key, Double.toString(value));		
	}
	
	public Object setProperty(String key, float value) {
		return setProperty(key, Float.toString(value));		
	}
	
	public Object setProperty(String key, int value) {
		return setProperty(key, Integer.toString(value));		
	}
	
	public Object setProperty(String key, List<String> value) {
		return setProperty(key, ListToString(value));
	}
	
	@Override
	public Object setProperty(String key, String value) {
		value = escapeValue(value);
		return super.setProperty(key, value );
	}
	
	public void stripPrefix(String prefix) {
		prefix += ".";
		
		Iterator<String> i = stringPropertyNames().iterator();
		
		while (i.hasNext()) {
			String key = i.next();
			String newKey = key;
			
			if (newKey.startsWith(prefix)) {
				newKey = newKey.substring(prefix.length());
			}
			
			super.setProperty(newKey, (String) remove(key));
		}
	}
	
	private String unescapeValue(String value) {
		if (value.startsWith(P_ESCAPE+P_INCLUDE)) {
			value = value.substring( P_ESCAPE.length() ); 
		} else if (value.startsWith(P_ESCAPE+P_RANDOM)) {
			value = value.substring( P_ESCAPE.length() ); 
		}
		
		return value;
	}	
}
