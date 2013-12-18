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
@Deprecated
public class toHtml {
	public static String wrapHTMLContent(String poContent) {
		return "<html><head></head><body>"+poContent+"</body></html>";
	}
	
	public static String removeHTMLWrap(String poContent) {
		if (poContent != null && poContent.length() > "<html><head></head><body></body></html>".length()) {
			poContent.substring("<html><head></head><body>".length(), poContent.length()-"</body></html>".length());
		}
		return poContent;
	}
	
	public static <E,V> String mapToHTML(String poName, Map<E,V> poMap) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poMap == null) {
			html += "<p><i>null</i></p>";
		} else if (poMap.size() == 0) {
			html += "<p><i>empty</i></p>";
		} else {
			html += "<ul>";
			for (@SuppressWarnings("rawtypes") Map.Entry e:poMap.entrySet()) {
				html +="<li>"+e.getKey()+": "+e.getValue()+"</li>";
			}
			html +="</ul>";
		}
		
		return html;
	}
	
	public static String listToHTML(String poName, @SuppressWarnings("rawtypes") List poList) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poList == null) {
			html += "<p><i>null</i></p>";
		} else if (poList.size() == 0) {
			html += "<p><i>empty</i></p>";
		} else {		
			html += "<ul>";
			for (Object e:poList) {
				html +="<li>"+e+"</li>";
			}
			html +="</ul>";
		}
		
		return html;
	}	
	
	public static String valueToHTML(String poName, Object poValue) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poValue == null) {
			html += "<p><i>null</i></p>";
		} else {		
			html +="<p>"+poValue+"</p>";
		}
		
		return html;
	}
}
