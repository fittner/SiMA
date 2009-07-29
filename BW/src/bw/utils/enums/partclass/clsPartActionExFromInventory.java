/**
 * @author Benny D�nz
 * 03.07.2009, 22:00:50
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
 * 03.07.2009, 22:00:50
 * 
 */
public class clsPartActionExFromInventory extends clsPartActionEx {

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName="To-Inventory action-executor";

	}

	/* (non-Javadoc)
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.ACTIONEX_FROMINVENTORY;

	}

}

