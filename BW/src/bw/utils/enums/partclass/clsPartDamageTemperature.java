/**
 * @author deutsch
 * 20.02.2009, 12:05:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.enums.partclass;

import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 20.02.2009, 12:05:22
 * 
 */
public class clsPartDamageTemperature extends clsPartIntraBodySystem {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 12:05:22
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName = "Intra-Body-System: Damage Temperature";

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 12:05:22
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.INTRA_DAMAGE_TEMPERATURE;

	}

}
