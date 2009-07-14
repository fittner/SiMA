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
import java.util.Properties;

public class clsBWProperties extends Properties {
	private static final String P_INCLUDE = "@";
	private static final String P_ESCAPE = "\\";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3052402669291342862L;
	

	private String escapeValue(String value) {
		if (value.startsWith(P_INCLUDE)) {
			value = P_ESCAPE+value;
		}
		
		return value;
	}
	
	private String unescapeValue(String value) {
		if (value.startsWith(P_ESCAPE+P_INCLUDE)) {
			value = value.substring( P_ESCAPE.length() ); 
		}
		
		return value;
	}
	
	@Override
	public Object setProperty(String key, String value) {
		value = escapeValue(value);
		return super.setProperty(key, value );
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
		
		clsBWProperties oOtherProperties = clsBWProperties.readProperties(includeFileName);
		oOtherProperties.addPrefix(key);
		
		remove(key);
		
		putAll(oOtherProperties);
	}
	
	public static clsBWProperties readProperties(String poFilename) {
		clsBWProperties p2 = new clsBWProperties();
        
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
	
	public static void writeProperties(clsBWProperties poProp, String poFilename, String poComments) {
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
