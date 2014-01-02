package PropertyTest.Properties;

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

public class clsProperties extends Properties {
	private static final String P_INCLUDE = "@";
	private static final String P_RANDOM = "§"; //don't blame me - roland states that the law is pure random
	private static final String P_DELIMITER = ";";
	
	private static final String P_ESCAPE = "\\"; // don't change this value - escape is \\
	private static final String P_REGEXP_ESCAPE = "\\\\";
	private static final String P_REGEXP_DELIMITER = "(?<=(("+P_REGEXP_ESCAPE+P_REGEXP_ESCAPE+"){0,9}))"+P_DELIMITER;
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 3052402669291342862L;
	

	private String escapeValue(String value) {
		if (value.startsWith(P_INCLUDE)) {
			value = P_ESCAPE+value;
		} else if (value.startsWith(P_RANDOM)) {
			value = P_ESCAPE+value;
		}
		
		return value;
	}
	
	private String unescapeValue(String value) {
		if (value.startsWith(P_ESCAPE+P_INCLUDE)) {
			value = value.substring( P_ESCAPE.length() ); 
		} else if (value.startsWith(P_ESCAPE+P_RANDOM)) {
			value = value.substring( P_ESCAPE.length() ); 
		}
		
		return value;
	}
	
	@Override
	public Object setProperty(String key, String value) {
		value = escapeValue(value);
		return super.setProperty(key, value );
	}
	public Object setProperty(String key, boolean value) {
		return setProperty(key, Boolean.toString(value));
	}
	public Object setProperty(String key, int value) {
		return setProperty(key, Integer.toString(value));		
	}
	public Object setProperty(String key, float value) {
		return setProperty(key, Float.toString(value));		
	}
	public Object setProperty(String key, double value) {
		return setProperty(key, Double.toString(value));		
	}
	public Object setProperty(String key, List<String> value) {
		return setProperty(key, ListToString(value));
	}

	
	@Override
	public String getProperty(String key) {
		return unescapeValue(super.getProperty(key));
	}
	
	public String getPropertyString(String key) {
		return getProperty(key);
	}
	public boolean getPropertyBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
	public int getPropertyInt(String key) {
		return Integer.parseInt(getProperty(key));
	}		
	public float getPropertyFloat(String key) {
		return Float.parseFloat(getProperty(key));
	}	
	public double getPropertyDouble(String key) {
		return Double.parseDouble(getProperty(key));
	}
	public List<String> getPropertyList(String key) {
		return StringToList(getProperty(key));
	}	
	
	private static String escapeDelim(String value) {
		if (value.contains(P_DELIMITER)) {
			throw new java.lang.UnsupportedOperationException("escaping of strings currently buggy - please don't use the delimiter "+P_DELIMITER+" in your strings");
		}
		// implement correct escaping and unescaping
	//	value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE+P_ESCAPE); // escape the escape sequence - \\ is converted to \\\\
	//	value = value.replaceAll(P_DELIMITER, P_ESCAPE+P_DELIMITER); // escape the delimiter - ; is converted to \\;
		// note: "\\;" is converted to "\\\\\\;" - this has to dealt with in unescape Delim und the regexp has to deal with "\\". this is converted
		// to "\\\\" and in the imploded string the value has a ; attached. e.g. "a", "\\", and "c" are imploded to "a;\\\\;c". thus. the 
		// regexp used for split has to include all cases where an even number of escape sequence in front of a delimiter is found.  
		// "a;\\\\\\;c" equals "a" and "\\;c". "a;\\\\;c" equals "a", "\\", and "c".
		return value;
	}
	private static String unescapeDelim(String value) {
		// implement correct escaping and unescaping		
		// reverse escapeDelim
	//	value = value.replaceAll(P_REGEXP_ESCAPE+P_DELIMITER, P_DELIMITER);
	//	value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE);
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
	
	private static List<String> StringToList(String value) {
		String[] r = value.split(P_REGEXP_DELIMITER);
		List<String> res = new ArrayList<String>();
		
		for (String s:r) {
			res.add( unescapeDelim(s) );
		}
		
		return res;
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return unescapeValue(super.getProperty(key, defaultValue));		
	}
	
	public void addPrefix(String prefix) {
		prefix += ".";
		
		Iterator<String> i = stringPropertyNames().iterator();
		
		while (i.hasNext()) {
			String key = i.next();
			super.setProperty(prefix+key, (String) remove(key));
		}
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
	
	private void insertIncludes(ArrayList<String> replaceCandidates) {
		for (String key:replaceCandidates) {
			insertInclude(key);
		}
	}
	
	private void insertInclude(String key) {
		String includeFileName = (super.getProperty(key)).substring( P_INCLUDE.length() );
		
		clsProperties oOtherProperties = clsProperties.readProperties(includeFileName);
		oOtherProperties.addPrefix(key);
		
		remove(key);
		
		putAll(oOtherProperties);
	}
	
	public static clsProperties readProperties(String poFilename) {
		clsProperties p2 = new clsProperties();
        
	    try
	    {
	        FileInputStream propInFile = new FileInputStream( poFilename );
	        p2.load( propInFile );
	        
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }		
        
        return p2;
	}
	
	public static void writeProperties(clsProperties poProp, String poFilename, String poComments) {
	    try
	    {
	      FileOutputStream propOutFile =
	         new FileOutputStream( poFilename );
	      
	      poProp.store(propOutFile, poComments);
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can’t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }
	}	
}
