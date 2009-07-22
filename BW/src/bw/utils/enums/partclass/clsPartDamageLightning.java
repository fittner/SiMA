/**
 * @author deutsch
 * 20.02.2009, 13:45:29
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
 * 20.02.2009, 13:45:29
 * 
 */
public class clsPartDamageLightning extends clsPartInterBodyWorldSystem {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 13:45:29
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setName()
	 */
	@Override
	protected void setName() {
		moName = "Inter-Body-World: Lightning Damage";

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 20.02.2009, 13:45:29
	 * 
	 * @see bw.utils.enums.partclass.clsBasePart#setPartId()
	 */
	@Override
	protected void setPartId() {
		mePartId = eBodyParts.INTER_DAMAGE_LIGHTNING;

	}

}
