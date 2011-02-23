/**
 * @author deutsch
 * 05.05.2009, 16:37:09
 *
 * temp
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body;

import nao.utils.enums.eBodyType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 05.05.2009, 16:37:09
 * 
 */
public abstract class clsBaseBody implements 	itfStepSensing, itfStepUpdateInternalState, 
												itfStepProcessing, itfStepExecution {

    protected eBodyType meBodyType;
	
	public clsBaseBody() {
		setBodyType();
	}
	
	public eBodyType getBodyType() {
		return meBodyType;
	}
	
	protected abstract void setBodyType();
	
}
