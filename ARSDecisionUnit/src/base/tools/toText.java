/**
 * toHtml.java: DecisionUnits - pa._v38.tools
 * 
 * @author deutsch
 * 21.04.2011, 14:50:50
 */
package base.tools;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import base.datatypes.clsDataStructurePA;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 21.04.2011, 14:50:50
 * 
 */
public class toText {
	public static final String newline = System.getProperty("line.separator");
	
	public static String h1(String text) {return "*** "+text+" ***"+newline;}
	public static String h2(String text) {return "** "+text+" **"+newline;}
	public static String h3(String text) {return "* "+text+" *"+newline;}
	public static String li(String text) {return " - "+text+newline;}
	public static String p(String text)  {return text+newline;}
	public static String b(String text)  {return "#"+text+"#";}
	public static String i(String text)  {return "_"+text+"_";}
	
	public static <E,V> String mapToTEXT(String poName, Map<E,V> poMap) {
		String text = h2(poName);
		
		if (poMap == null) {
			text += p(i("null"));
		} else if (poMap.size() == 0) {
			text += p(i("empty"));
		} else {
			for (@SuppressWarnings("rawtypes") Map.Entry e:poMap.entrySet()) {
				text += li(e.getKey()+": "+e.getValue());
			}
		}
		
		return text;
	}
	
	//26.4.2019 delacruz: print ArrayList<clsPair<Double, clsDataStructurePA>> for F19Module
	public static String doubleListToText(String poName, ArrayList<clsPair<Double, clsDataStructurePA>> poDoubleList)
	{
	    String text = h2(poName);
	    
	    if (poDoubleList == null) {
            text += p(i("null"));
        } else if (poDoubleList.size() == 0) {
            text += p(i("empty"));
        } else {        
	    
    	    for(Object e:poDoubleList) {
    	        text+=li(e.toString());
    	    }
        }
	    return text;
	    
	}
	
	public static String listToTEXT(String poName, @SuppressWarnings("rawtypes") List poList) {
		String text =h2(poName);
		
		if (poList == null) {
			text += p(i("null"));
		} else if (poList.size() == 0) {
			text += p(i("empty"));
		} else {		
			for (Object e:poList) {
				text +=li(e.toString());
			}
		}
		
		return text;
	}	
	
	public static String valueToTEXT(String poName, Object poValue) {
		String text =h2(poName);
		
		if (poValue == null) {
			text += p(i("null"));
		} else {	
		    if(poValue instanceof Double){
		        text += p(getValueToString((Double) poValue));
		    }
		    else{
		        text += p(poValue.toString());
		    }
		}
		
		return text;
	}
	
    private static String getValueToString(double value){
        DecimalFormat threeDec = new DecimalFormat("0.000");
        String shortString = (threeDec.format(value));
        return shortString;
    }
}
