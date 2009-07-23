/**
 * @author deutsch
 * 22.07.2009, 10:32:43
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.utils.config;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import bw.utils.config.clsBWProperties;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.07.2009, 10:32:43
 * 
 */
public class tstBWProperties {

	/**
	 * Test method for {@link bw.utils.config.clsBWProperties#ListToString(java.util.List)}.
	 */
	@Test
	public void testListToString() {
	    ArrayList<String> oA = new ArrayList<String>(); 
	    String res = "";
	    
	    res = clsBWProperties.ListToString(oA);
	    assertTrue(res.equals(""));
	    
	    oA.add("1");
	    oA.add("2");
	    
	    res = clsBWProperties.ListToString(oA);
	    assertTrue(res.equals("1;2"));
	    
	    try {
	    	oA.add(";");
	    	oA.add("3");	    
	    	res = clsBWProperties.ListToString(oA);
	    	assertTrue(res.equals("1;2;\\;;3"));
	    } catch (java.lang.Exception e) {
	    	fail(e.toString());
	    }
	    
	    try {	    
		    oA.add("\\;");
		    oA.add("4");	    
		    res = clsBWProperties.ListToString(oA);
		    assertTrue(res.equals("1;2;\\;;3;\\\\\\;;4"));
	    } catch (java.lang.Exception e) {
	    	fail(e.toString());
	    }	    
	}

	/**
	 * Test method for {@link bw.utils.config.clsBWProperties#StringToList(java.lang.String)}.
	 */
	@Test
	public void testStringToList() {
		//	value = value.replaceAll(P_REGEXP_ESCAPE, P_ESCAPE+P_ESCAPE); // escape the escape sequence - \\ is converted to \\\\
		//	value = value.replaceAll(P_DELIMITER, P_ESCAPE+P_DELIMITER); // escape the delimiter - ; is converted to \\;
			// note: "\\;" is converted to "\\\\\\;" - this has to dealt with in unescape Delim und the regexp has to deal with "\\". this is converted
			// to "\\\\" and in the imploded string the value has a ; attached. e.g. "a", "\\", and "c" are imploded to "a;\\\\;c". thus. the 
			// regexp used for split has to include all cases where an even number of escape sequence in front of a delimiter is found.  
			// "a;\\\\\\;c" equals "a" and "\\;c". "a;\\\\;c" equals "a", "\\", and "c".
		
	    ArrayList<String> res = new ArrayList<String>(); 
	    String oA = "";
	    String t = "";
	    
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[]"));
	    
	    oA = "1;2";
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[1, 2]"));	    
	    
	    oA = "1;2;\\;;3";
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[1, 2, ;, 3]"));	    

	    oA = "1;2;\\;;3;\\\\\\;;4";
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[1, 2, ;, 3, \\;, 4]"));
	    
	    oA = "a;\\\\;c";
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[a, \\, c]"));	   
	    
	    oA = "a;\\\\\\;c";
	    res = (ArrayList<String>) clsBWProperties.StringToList(oA);
	    t = res.toString();
	    assertTrue(t.equals("[a, \\;c]"));	 	    
	}

}