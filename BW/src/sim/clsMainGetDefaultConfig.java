/**
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim;

import bw.entities.clsBubble;
import bw.utils.config.clsBWProperties;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 */
public class clsMainGetDefaultConfig {

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 23.07.2009, 15:51:57
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String oBaseDir = "";
		String oExt = ".default.property";
		
		clsBWProperties.writeProperties(clsBubble.getDefaultProperties(""), oBaseDir, "bubble"+oExt, "");

	}

}
