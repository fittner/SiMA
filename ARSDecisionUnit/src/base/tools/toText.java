/**
 * toHtml.java: DecisionUnits - pa._v38.tools
 * 
 * @author deutsch
 * 21.04.2011, 14:50:50
 */
package base.tools;

import java.util.List;
import java.util.Map;

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
			text += p(poValue.toString());
		}
		
		return text;
	}
}
