/**
 * @author deutsch
 * 20.02.2009, 11:57:35
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.utils.enums.eBodyParts;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 20.02.2009, 11:57:35
 * 
 */
public class clsPartBrain extends clsBasePart {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:57:35
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName = "Brain";

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:57:35
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.BRAIN;

	}

}
