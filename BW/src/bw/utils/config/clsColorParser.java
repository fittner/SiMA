/**
 * @author holleis
 * 23.07.2009, 16:19:58
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.config;

import java.awt.Color;
import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;


/**
 * Reflects java.awt.Color and makes all its static colors ("black", "blue" ...) available. additionally, custom 
 * colors can be used (ars_logo, ars_background, tu_blue, etc.). every color given as string param is converted to lower case.
 * 
 * @author Edgar Holleis
 * 23.07.2009, 16:19:58
 * 
 */
public class clsColorParser  {
	
	private static Map<String,Color> reverseMapping;
	
	static {
		reverseMapping = new HashMap<String,Color>();
		Field[] fs = Color.class.getFields();
		for (int i = 0; i < fs.length; i++) {
			if(fs[i].getType().equals(Color.class)) {
				String s = fs[i].getName().toLowerCase();
				if (! reverseMapping.containsKey(s)) {
					try {
						reverseMapping.put(s, (Color) fs[i].get(null));
					} catch (IllegalAccessException e) { /* Ignore */ }
				}				
			}
		}
		
		/* Define some ARS-Specific Colors */
		reverseMapping.put("ars_logo", new Color(216,68,32));
		reverseMapping.put("ars_background", new Color(242,239,204));
		reverseMapping.put("tu_blue", new Color(0,134,217));
		
	}
	
	public static Color parse(String s) {
		return reverseMapping.get(s.toLowerCase());
	}
}
