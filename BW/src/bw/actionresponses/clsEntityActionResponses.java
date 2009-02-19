/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.actionresponses;

import bw.exceptions.EntityActionResponseNotImplemented;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsEntityActionResponses {
	public clsFood actionEatResponse() throws EntityActionResponseNotImplemented {
		throw new bw.exceptions.EntityActionResponseNotImplemented();
	}
}