/**
 * @author Benny D�nz
 * 21.06.2009, 09:44:59
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (Benny Dönz) - insert description 
 * 
 * @author Benny D�nz
 * 21.06.2009, 09:44:59
 * 
 */
public class clsPartActionExKill extends clsPartActionEx {

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName="Kill action-executor";

	}

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.ACTIONEX_KILL;

	}

}

