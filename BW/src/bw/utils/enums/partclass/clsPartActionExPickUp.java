/**
 * @author Benny Dönz
 * 03.07.2009, 22:00:50
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
 * 03.07.2009, 22:00:50
 * 
 */
public class clsPartActionExPickUp extends clsPartActionEx {

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName="Pick-Up action-executor";

	}

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.ACTIONEX_PICKUP;

	}

}

