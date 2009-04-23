/**
 * @author deutsch
 * 22.04.2009, 16:06:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.actionresponses;

import bw.entities.clsCake;
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
public class clsCakeResponses extends clsEntityActionResponses {
	clsCake moCake;
	
	public clsCakeResponses(clsCake poCake) {
		moCake = poCake;
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
		//CHKME cm: only for testing! what else?
		clsFood oFood = new clsFood();

		try {
			float rWeight = moCake.withdraw(prWeight);
			
			oFood.addNutritionFraction(1, 1.0f);
			oFood.setAmount(rWeight);
			oFood.finalize();
		} catch (exFoodAmountBelowZero e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (exFoodAlreadyNormalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return oFood;
	}

}
