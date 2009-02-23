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
public abstract class clsEntityActionResponses {
	
	public abstract clsFood actionEatResponse(float prWeight) throws exEntityActionResponseNotImplemented;
}