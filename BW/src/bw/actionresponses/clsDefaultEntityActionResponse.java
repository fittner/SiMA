/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.actionresponses;

import bw.exceptions.exEntityActionResponseNotImplemented;
import bw.exceptions.exFoodAmountBelowZero;
import bw.utils.tools.clsFood;

/**
 * TODO (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 
 */
public class clsDefaultEntityActionResponse extends clsEntityActionResponses {

	/* (non-Javadoc)
	 * @see bw.actionresponses.clsEntityActionResponses#actionEatResponse(float)
	 */
	@Override
	public clsFood actionEatResponse(float prWeight) throws exEntityActionResponseNotImplemented {
	
		//CHKME cm: only for testing! 
		clsFood oFood = new clsFood();
	
			try {
				oFood.setAmount(1.0f);
			} catch (exFoodAmountBelowZero e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return oFood;
			
		//throw new bw.exceptions.exEntityActionResponseNotImplemented();
	}

}
