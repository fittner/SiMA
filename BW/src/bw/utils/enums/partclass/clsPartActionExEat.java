/**
 * @author Benny Dönz
 * 21.06.2009, 09:44:59
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.utils.enums.eBodyParts;

/**
 * TODO (Benny Dönz) - insert description 
 * 
 * @author Benny Dönz
 * 21.06.2009, 09:44:59
 * 
 */
public class clsPartActionExEat extends clsPartActionEx {

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName="Eat action-executor";

	}

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.ACTIONEX_EAT;

	}

}

