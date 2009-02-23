/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.actionresponses;

import bw.exceptions.exEntityActionResponseNotImplemented;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsBubbleResponses extends clsEntityActionResponses {
	
	public clsFood actionEatResponse(float prWeight) throws exEntityActionResponseNotImplemented {
		throw new bw.exceptions.exEntityActionResponseNotImplemented();
	}
}
