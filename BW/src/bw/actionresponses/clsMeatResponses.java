/**
 * @author deutsch
 * 22.04.2009, 16:06:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.actionresponses;

import bw.body.internalSystems.clsFlesh;
import bw.body.itfget.itfGetFlesh;
import bw.entities.clsCake;
import bw.entities.clsEntity;
import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodAmountBelowZero;
import bw.utils.tools.clsFood;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.04.2009, 16:06:20
 * 
 */
public class clsMeatResponses extends clsEntityActionResponses {
	clsFlesh moFlesh;
	
	public clsMeatResponses(itfGetFlesh poEntity) {
		moFlesh = poEntity.getFlesh();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2009, 16:06:20
	 * 
	 * @see bw.actionresponses.clsEntityActionResponses#actionEatResponse(float)
	 */
	@Override
	public clsFood actionEatResponse(float prWeight) {
		clsFood oFood = moFlesh.withdraw(prWeight);
		return oFood;
	}

}
