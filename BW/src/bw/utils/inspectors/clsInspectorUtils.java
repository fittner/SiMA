/**
 * @author muchitsch
 * Jul 22, 2009, 6:53:29 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors;

import java.util.Formatter;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * Jul 22, 2009, 6:53:29 PM
 * 
 */
public class clsInspectorUtils {
	
	public static String FormatDouble(double pnVal)
	{
		Formatter oDoubleFormatter = new Formatter();
		return oDoubleFormatter.format("%.2f",pnVal).toString();
	}

}