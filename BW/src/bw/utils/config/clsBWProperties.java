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

/**
 * clsBWProperties is a specialization of the java.util.Properties class. 
 * 
 * Next to the regular functionalities, include of other property files and generation of random numbers for values is supported. In the bw project 
 * exists a singleton “bw.factories.clsPropertiesGetter”. It provides access to the properties and should be filled with an instance of 
 * clsBWProperties as soon as possible. Currently, this is done in the constructor sim.creation.clsLoader. 
 * 
 * Currently, the following list of values is supported by clsBWProperties: String, boolean, int, float, double, semicolon 
 * separated list of any type listed before, excluding lists within lists(e.g. “23;12;43”). corresponding setter and getter functions are available.
 * 
 * Special config tags: 
 * 
 * Include Propertyfile "some.key = @include.me.properties". the string following @ is used as filename to open another property file. “some.key” is 
 * added as prefix to each key of the other file. finally the properties of the other file are merged into the current file and the include line is 
 * removed. this works recursively.
 * 
 * Generate Random Number "some.key = $L1;10" each time the value of some.key is read, a new random value is returned. four different types of random 
 * number generators are available. See clsRandomProperties for details. 
 * 
 * @author deutsch
 * 22.07.2009, 09:48:56
 * 
 */
public class clsBWProperties extends Properties {
	/**
	 * delimiter for list entries 
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:53:38
	 */
	private static final String P_DELIMITER = ";";
	
	/**
	 * escape symbol - if the delimitor is in a string which should be part of a list it has to be escaped. don't change this value - escape is \
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:54:13
	 */
	private static final String P_ESCAPE = "\\"; 
	
	/**
	 * TAG denoting that the following value has to be treated as an include file pointer
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:55:04
	 */
	private static final String P_INCLUDE = "@";
	
	/**
	 * TAG denoting that the following value has to be treated as a random value
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:55:35
	 */
	private static final String P_RANDOM = "§"; //don't blame me - roland states that the law is pure random
	
	
	/**
	 * delimiter escape escaped for regexp usage
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:55:58
	 */
	private static final String P_REGEXP_ESCAPE = "\\\\";
	
	/**
	 * regexp to find which delimitors are escaped and which are denoting the border between two list elements which have to be splitted
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:56:33
	 */
	private static final String P_REGEXP_DELIMITER = "(?<=(("+P_REGEXP_ESCAPE+P_REGEXP_ESCAPE+"){0,99}))"+P_DELIMITER;
 
	private static final long serialVersionUID = 3052402669291342862L;

	/**
	 * points to the root directory of all property files which can be included (including the main file itself) 
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:57:52
	 */
	private String moBaseDir = "";
	
	/**
	 * Constructor - sets base dir. no Property map is created.
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:59:26
	 *
	 * @param poBaseDir
	 */
	public clsBWProperties(String poBaseDir) {
		setBaseDir(poBaseDir);
	}
	
	/**
	 * sets the base dir. guarantees that the given path is either empty or ends with the correct file.separator
	 *
	 * @author deutsch
	 * 22.07.2009, 10:00:00
	 *
	 * @param poBaseDir
	 */
	public void setBaseDir(String poBaseDir) {
		moBaseDir = poBaseDir;
		if (!moBaseDir.endsWith(System.getProperty("file.separator")) && poBaseDir.length()>0) {
			moBaseDir += System.getProperty("file.separator");
		}
	}
	
	/**
	 * prepares a string from a list. every occurances of delimiters or escapes have to be escaped.
	 *
	 * @author deutsch
	 * 22.07.2009, 10:00:54
	 *
	 * @param value
	 * @return escaped string
	 */
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

	/**
	 * converts a list of strings to a string using the delimiter. every string is escaped.
	 *
	 * [23, 12, 43] => "23;12;43"
	 *
	 * @author deutsch
	 * 22.07.2009, 10:02:31
	 *
	 * @param value
	 * @return string with all list entries seperated by a delimiter
	 */
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
	
	/**
	 * converts a string to a list. searches for unescaped delimiters as splitting positions. each element of the list is unescaped.
	 *
	 * "23;12;43" => [23, 12, 43] 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:05:20
	 *
	 * @param value
	 * @return list of unescaped string parts
	 */
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
	
	/**
	 * reads the given property file at the given directory. includes are already processed.
	 *
	 * @author deutsch
	 * 22.07.2009, 10:03:59
	 *
	 * @param poBaseDir
	 * @param poFilename
	 * @return a property map
	 */
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
	

	/**
	 * writes the given property map to a file.
	 *
	 * @author deutsch
	 * 22.07.2009, 10:07:33
	 *
	 * @param poProp
	 * @param poBaseDir
	 * @param poFilename
	 * @param poComments
	 */
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


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.07.2009, 10:11:38
	 * 
	 * @see java.util.Properties#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty(String key) {
		String res = super.getProperty(key);
		
		if (res.startsWith(P_RANDOM)) {
			//insuperior approach - a double value is converted to a string and to a double back again. 
			//(usually, getPropertyDouble is called in case of random values)
			res = new Double(clsRandomProperties.getRandom( res.substring( P_RANDOM.length() ))).toString();
		} else {
			res = unescapeTag(res);
		}
		
		return res;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.07.2009, 10:11:58
	 * 
	 * @see java.util.Properties#getProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public String getProperty(String key, String defaultValue) {
		return unescapeTag(super.getProperty(key, defaultValue));		
	}
	
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:09
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public boolean getPropertyBoolean(String key) {
		return Boolean.parseBoolean(getProperty(key));
	}
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:18
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public double getPropertyDouble(String key) {
		return Double.parseDouble(getProperty(key));
	}		
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:25
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public float getPropertyFloat(String key) {
		return Float.parseFloat(getProperty(key));
	}	
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:33
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public int getPropertyInt(String key) {
		return Integer.parseInt(getProperty(key));
	}
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:35
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public List<String> getPropertyList(String key) {
		return StringToList(getProperty(key));
	}	
	/**
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:39
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */
	public String getPropertyString(String key) {
		return getProperty(key);
	}
	
	/**
	 * replaces key/value pairs with the content of the property file given after the @ tag. the keys of the file to included get the key of the line to
	 * be replaced as prefix. the key/value pair is removed from the property file.
	 *
	 * @author deutsch
	 * 22.07.2009, 10:14:20
	 *
	 * @param key
	 */
	private void insertInclude(String key) {
		String includeFileName = (super.getProperty(key)).substring( P_INCLUDE.length() );
		
		clsBWProperties oOtherProperties = clsBWProperties.readProperties(moBaseDir, includeFileName);
		oOtherProperties.addPrefix(key);
		
		remove(key);
		
		putAll(oOtherProperties);
	}
	
	/**
	 * all values of the current property map are scanned if hey begin with the include tag (currently @). 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:15:57
	 *
	 * @return list of all key/value pares which should be replaced by the content of another property file.
	 */
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
	
	/**
	 * parses the list returned by scan4includes and passes the elements to insertInclude
	 *
	 * @author deutsch
	 * 22.07.2009, 10:16:59
	 *
	 * @param replaceCandidates
	 */
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
	

	
	/**
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. Enforces use of strings for property keys and values. The value returned is the result of the Hashtable call to put. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:18:07
	 *
	 * @param key
	 * @param value
	 * @return the previous value of the specified key in this property list, or null if it did not have one.
	 */
	public Object setProperty(String key, boolean value) {
		return setProperty(key, Boolean.toString(value));
	}
	
	/**
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. Enforces use of strings for property keys and values. The value returned is the result of the Hashtable call to put. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:18:10
	 *
	 * @param key
	 * @param value
	 * @return the previous value of the specified key in this property list, or null if it did not have one.
	 */
	public Object setProperty(String key, double value) {
		return setProperty(key, Double.toString(value));		
	}
	
	/**
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. Enforces use of strings for property keys and values. The value returned is the result of the Hashtable call to put. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:18:12
	 *
	 * @param key
	 * @param value
	 * @return the previous value of the specified key in this property list, or null if it did not have one.
	 */
	public Object setProperty(String key, float value) {
		return setProperty(key, Float.toString(value));		
	}
	
	/**
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. Enforces use of strings for property keys and values. The value returned is the result of the Hashtable call to put. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:18:17
	 *
	 * @param key
	 * @param value
	 * @return the previous value of the specified key in this property list, or null if it did not have one.
	 */
	public Object setProperty(String key, int value) {
		return setProperty(key, Integer.toString(value));		
	}
	
	/**
	 * Calls the Hashtable method put. Provided for parallelism with the getProperty method. Enforces use of strings for property keys and values. The value returned is the result of the Hashtable call to put. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:18:19
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setProperty(String key, List<String> value) {
		return setProperty(key, ListToString(value));
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.07.2009, 10:19:55
	 * 
	 * @see java.util.Properties#setProperty(java.lang.String, java.lang.String)
	 */
	@Override
	public Object setProperty(String key, String value) {
		value = escapeTag(value);
		return super.setProperty(key, value );
	}
	
	
	/**
	 * each key is exchanged by prefix+"."+key 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:10:37
	 *
	 * @param prefix
	 */
	public void addPrefix(String prefix) {
		prefix += ".";
		
		Iterator<String> i = stringPropertyNames().iterator();
		
		while (i.hasNext()) {
			String key = i.next();
			super.setProperty(prefix+key, (String) remove(key));
		}
	}
	
	/**
	 * from each key the prefix will be substracted from the start of the key
	 *
	 * @author deutsch
	 * 22.07.2009, 10:20:01
	 *
	 * @param prefix
	 */
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
	
	/**
	 * if an entry starts with an escape - it has to be removed.
	 *
	 * @author deutsch
	 * 22.07.2009, 10:21:14
	 *
	 * @param value
	 * @return
	 */
	private String unescapeTag(String value) {
		if (value.startsWith(P_ESCAPE)) {
			value = value.substring( P_ESCAPE.length() ); 
		}
		
		return value;
	}
	
	/**
	 * entries which start with one of the tags like include, escape, or random have to be escaped. 
	 *
	 * @author deutsch
	 * 22.07.2009, 10:21:16
	 *
	 * @param value
	 * @return
	 */
	private String escapeTag(String value) {
		if (value.startsWith(P_INCLUDE)) {
			value = P_ESCAPE+value;
		} else if (value.startsWith(P_RANDOM)) {
			value = P_ESCAPE+value;
		} else if (value.startsWith(P_ESCAPE)) {
			value = P_ESCAPE+value;
		}		
		
		return value;
	}	
}
