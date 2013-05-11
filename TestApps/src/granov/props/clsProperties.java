package granov.props;

import config.clsRandomProperties;
import config.clsColorParser;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;

/**
 * clsProperties is a specialization of the java.util.Properties class. 
 * 
 * Next to the regular functionalities, include of other property files and generation of random numbers for values is supported. In the bw project 
 * exists a singleton �bw.factories.clsPropertiesGetter�. It provides access to the properties and should be filled with an instance of 
 * clsProperties as soon as possible. Currently, this is done in the constructor sim.creation.clsLoader. 
 * 
 * Currently, the following list of values is supported by clsProperties: String, boolean, int, float, double, semicolon 
 * separated list of any type listed before, excluding lists within lists(e.g. �23;12;43�). corresponding setter and getter functions are available.
 * 
 * Special config tags: 
 * 
 * Include Propertyfile "some.key = @include.me.properties". the string following @ is used as filename to open another property file. �some.key� is 
 * added as prefix to each key of the other file. finally the properties of the other file are merged into the current file and the include line is 
 * removed. this works recursively.
 * 
 * Generate Random Number "some.key = §L1;10" each time the value of some.key is read, a new random value is returned. four different types of random 
 * number generators are available. See clsRandomProperties for details. 
 * 
 * @author deutsch
 * 22.07.2009, 09:48:56
 * 
 */
public class clsProperties extends Properties {
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
	 * TAG denoting that the following value is a hex color #01AFB2 
	 * 
	 * @author deutsch
	 * 23.07.2009, 16:01:06
	 */
	private static final String P_COLOR = "#";
	
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
	@SuppressWarnings("unused") // here for future use
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
	public clsProperties(String poBaseDir) {
		setBaseDir(poBaseDir);
	}
	
	/**
	 * Constructor. base dir is set to "". no Property map is created.
	 * 
	 * @author deutsch
	 * 22.07.2009, 09:59:26
	 *
	 * @param poBaseDir
	 */
	public clsProperties() {
		setBaseDir("");
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
		// implement correct escaping and unescaping
		value = Matcher.quoteReplacement(value); //
		//value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE+P_ESCAPE); // escape the escape sequence - \\ is converted to \\\\
		//value = value.replaceAll(P_DELIMITER, P_ESCAPE+P_DELIMITER); // escape the delimiter - ; is converted to \\;
		// note: "\\;" is converted to "\\\\\\;" - this has to dealt with in unescape Delim und the regexp has to deal with "\\". this is converted
		// to "\\\\" and in the imploded string the value has a ; attached. e.g. "a", "\\", and "c" are imploded to "a;\\\\;c". thus. the 
		// regexp used for split has to include all cases where an even number of escape sequence in front of a delimiter is found.  
		// "a;\\\\\\;c" equals "a" and "\\;c". "a;\\\\;c" equals "a", "\\", and "c".
		if (value.contains(P_DELIMITER)) {
			value = P_ESCAPE + value;
	//		throw new java.lang.UnsupportedOperationException("escaping of strings currently buggy - please don't use the delimiter "+P_DELIMITER+" in your strings");
		}
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
	public static String ListToString(List<String> value)  {
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
	public static List<String> StringToList(String value) {
		// The split function cannot be applied 
		//String[] r = value.split(P_REGEXP_DELIMITER);
		
		List<String> res = new ArrayList<String>();
		
		int i,pi; //Index and previous Index 
		i=pi=0;
		while((i=value.indexOf(P_DELIMITER, pi))!=-1) {
			String s = value.substring(pi, i) ;
			
			if(s.equals(P_REGEXP_ESCAPE+P_ESCAPE)) { //The big one \\\\\\
				int i2 = value.indexOf(P_DELIMITER, i+1);
				if(i2!=-1) {
					s = "\\" + value.substring(i, i2);
					pi = i2+1;
				}
				else {
					s = "\\" + value.substring(i);
					pi = value.length();
				}
			}
			else if(s.equals(P_REGEXP_ESCAPE)) { // 
				pi = i+1;
			}
			else if(s.equals(P_ESCAPE)) { // 
				pi += 3;
			}
			else
				pi = i+1;
			
			res.add( unescapeDelim(s) );
		};
		if (pi<value.length()) 
			res.add(unescapeDelim(value.substring(pi)));
		
	/*	for (String s:r) {
			res.add( unescapeDelim(s) );
		}*/
		
		return res;
	}
	
	private static String unescapeDelim(String value) {
		// implement correct escaping and unescaping		
		// reverse escapeDelim
//		value = value.replaceAll(P_REGEXP_ESCAPE+P_DELIMITER, P_DELIMITER);
//		value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE);
		 
		if (value.equals(P_ESCAPE)) {
			value = P_DELIMITER; }
		
		if (value.equals(P_REGEXP_ESCAPE)) {
			value = P_ESCAPE; }
		
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
	public static clsProperties readProperties(String poBaseDir, String poFilename) {
		clsProperties p2 = new clsProperties(poBaseDir);
		
		poFilename = poBaseDir + System.getProperty("file.separator") + poFilename;
        
		if (poFilename.length()>0) {
	    try
	    {
	        FileInputStream propInFile = new FileInputStream( poFilename );
	        p2.load( propInFile );
	        
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can�t find " + poFilename );
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
	public static void writeProperties(clsProperties poProp, String poBaseDir, String poFilename, String poComments) {
    
		if (!poBaseDir.endsWith(System.getProperty("file.separator")) && poBaseDir.length()>0 ) {
			poBaseDir += System.getProperty("file.separator");
		}
		
	    try
	    {
	      FileOutputStream propOutFile =
	         new FileOutputStream( poBaseDir+poFilename );
	      
	      poProp.store(propOutFile, poComments);
	      
	      ArrayList<String> lines = readLines(poBaseDir+poFilename);
	      lines = sortLines(lines, "#");
	      writeLines(lines, poBaseDir+poFilename);
	    } catch ( FileNotFoundException e ) {
          System.err.println( "Can�t find " + poFilename );
        } catch ( IOException e ) {
	      System.err.println( "I/O failed." );
	    }
	}
	
	private static ArrayList<String> sortLines(ArrayList<String> lines, String skipLinesStartingWith) {
		ArrayList<String> head_lines = new ArrayList<String>();
		
		//fetch all lines which start with # or what every at the top of the file. the first line which is not a comment stops this routine.
		for (String line:lines) {
			if (line.startsWith(skipLinesStartingWith)) {
				head_lines.add(line);
			} else {
				break;
			}
		}
		
		//remove head from the lines to be sorted
		for (int i=0; i<head_lines.size(); i++) {
			lines.remove(0);
		}
		
		Collections.sort(lines);
		head_lines.addAll(lines);
		
		return head_lines;
	}

	private static ArrayList<String> readLines(String filename) throws IOException {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        bufferedReader.close();
        return lines;
    }
	
    private static void writeLines(List<String> list, String filename) throws IOException {
    	FileWriter fileWriter = new FileWriter(filename);
        BufferedWriter out = new BufferedWriter(fileWriter);
        
        for (String line:list) {
        	out.write(line);
        	out.newLine();
        }
        out.close();
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

		if (res == null) {
			throw new java.lang.NullPointerException("Key not found: '"+key+"'");
		}
		
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
	 * Searches for the property with the specified key in this property list. If the key is not found in this property list, the default property list, and its defaults, recursively, are then checked. The method returns null if the property is not found.
	 * two different types of values are accepted: html color like encoding (#12AF32) and java color names (red, white, etc.).
	 *
	 * @author deutsch
	 * 22.07.2009, 10:12:39
	 *
	 * @param key
	 * @return the value in this property list with the specified key value.
	 */	
	public Color getPropertyColor(String key) {
		String value = getProperty(key);
		Color result = null;

		if (value.startsWith(P_COLOR)) {
			if (value.length() != 7) {
				throw new java.lang.IllegalArgumentException();
			}
			result = new Color(
				Integer.valueOf(value.substring(1, 3), 16).intValue(),
				Integer.valueOf(value.substring(3, 5), 16).intValue(),
				Integer.valueOf(value.substring(5, 7), 16).intValue());
		} else {
			result = clsColorParser.parse(value);
		}

		
		return result;
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
		
		clsProperties oOtherProperties = clsProperties.readProperties(moBaseDir, includeFileName);
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
	 * the Color value is safed in html encoding (#AB23C1)
	 *
	 * @author deutsch
	 * 23.07.2009, 16:55:06
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public Object setProperty(String key, Color value) {
		String v = P_COLOR;
		v+=intToHexString(value.getRed());
		v+=intToHexString(value.getGreen());
		v+=intToHexString(value.getBlue());
		return setProperty(key, v);
	}	
	private String intToHexString(int i) {
		String oS = Integer.toString(i & 0xff, 16);
		
		if(oS.length()<2) {
			oS = "0"+oS;
		}
		
		return oS;
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
		prefix = clsProperties.addDot(prefix);
		
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
	
	/**
	 * ensures that the prefix ends with a "." 
	 *
	 * @author deutsch
	 * 23.07.2009, 18:20:34
	 *
	 * @param poPrefix
	 * @return
	 */
	public static String addDot(String poPrefix) {
		if (poPrefix.length()>0 && !poPrefix.endsWith(".")) {
			return poPrefix + ".";
		}
		
		return poPrefix;
	}
	
	/**
	 * removes all entries from the hashmap which starts with a certain prefix. only the matching key and its subkeys are removed
	 * e.g. "bubble.body" removes "bubble.body" and "bubble.body.*" but not "bubble.body_type".
	 *
	 * @author deutsch
	 * 23.07.2009, 19:17:08
	 *
	 * @param poPrefix
	 */
	public void removeKeysStartingWith(String poPrefix) {
		//remove direct match
		this.remove(poPrefix);
		
		//add dot to ensure that only subkeys are removed
		poPrefix = addDot(poPrefix);
		
		Set<Object> oKeyList = this.keySet();
		ArrayList<String> oRemoveCandidates = new ArrayList<String>();
		
		for (Object oKey:oKeyList) {
			String tmp = (String)oKey;
			if (tmp.startsWith(poPrefix)) {
				oRemoveCandidates.add(tmp);
			}
		}
		
		for (String oKey:oRemoveCandidates) {
			this.remove(oKey);
		}
	}
	
	/**
	 * copies all entries which keys start with poPrefix into a new set. the prefix is removed from the keys
	 * in the new set.
	 *
	 * @author deutsch
	 * Jul 26, 2009, 12:26:40 PM
	 *
	 * @param poPrefix
	 * @return
	 */
	public clsProperties getSubset(String poPrefix) {
		clsProperties oSubset = new clsProperties();

		Set<Object> oKeyList = this.keySet();
		
		for (Object oKey:oKeyList) {
			String tmp_key = (String)oKey;
			if (tmp_key.startsWith(poPrefix)) {
				String tmp_value = (String)get(oKey);
				oSubset.put(tmp_key, tmp_value);
			}
		}
		
		oSubset.stripPrefix(poPrefix);
		
		return oSubset;
	}
	
	/**
	 * scans all existing keys and returns true if at least one key starts with the given prefix.
	 *
	 * @author deutsch
	 * Jul 26, 2009, 3:02:11 PM
	 *
	 * @param poPrefix
	 * @return
	 */
	public boolean existsPrefix(String poPrefix) {
		boolean nResult = false;
		
		Set<Object> oKeyList = this.keySet();
		
		for (Object oKey:oKeyList) {
			String tmp_key = (String)oKey;
			if (tmp_key.startsWith(poPrefix)) {
				nResult = true;
				break;
			}
		}
		
		return nResult;
	}
}
