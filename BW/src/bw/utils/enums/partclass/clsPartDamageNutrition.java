/**
 * @author deutsch
 * 20.02.2009, 11:56:21
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
 * 20.02.2009, 11:56:21
 * 
 */
public class clsPartDamageNutrition extends clsPartIntraBodySystem {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:56:21
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName = "Intra-Body-System: Damage Nutrition";

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 11:56:21
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.INTRA_DAMAGE_NUTRITION;


	}

}
