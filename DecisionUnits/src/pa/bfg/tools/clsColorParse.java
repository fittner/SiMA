/**
 * clsColorParse.java: DecisionUnits - bfg.tools
 * 
 * @author langr
 * 16.09.2009, 20:47:54
 */
package pa.bfg.tools;

import java.awt.Color;

/**
 * parses a color hex string of the form #RRGGBB and returns a java.awt.color instance
 * 
 * @author langr
 * 16.09.2009, 20:47:54
 * 
 */
public class clsColorParse {

	public static Color parseHashHexa(String s){

		Color oRetVal = null;
		int r = 0, g = 0, b = 0;
		try{
 
			r = Integer.parseInt(s.substring(1,3), 16);
			g = Integer.parseInt(s.substring(3,5), 16);
			b = Integer.parseInt(s.substring(5,7), 16);
			
		}catch(Throwable e){
			System.out.println("Error parsing color: " + e.getMessage() );
		}
		finally{
			oRetVal = new Color(r, g, b);
		}
		return oRetVal;
	}
}
