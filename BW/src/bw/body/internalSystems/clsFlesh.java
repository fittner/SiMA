/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodWeightBelowZero;
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigDouble;
import bw.utils.enums.eConfigEntries;
import bw.utils.tools.clsFood;
import java.lang.Math;
import java.util.Iterator;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFlesh extends clsFood {
    private clsConfigMap moConfig;	
    private boolean mnTotallyConsumed;
	
	public clsFlesh(clsConfigMap poConfig) {
		super();
		moConfig = getFinalConfig(poConfig);
		mnTotallyConsumed = false;
		applyConfig();
	}
	
	private void applyConfig() {
		
		clsConfigMap oNutritions = (clsConfigMap) moConfig.get(eConfigEntries.NUTRITIONS);
		
		Iterator<Integer> i = oNutritions.iterator();
		
		try {
			while (i.hasNext()) {
				Integer oKey = i.next();
				addNutritionFraction(oKey, ((clsConfigDouble)oNutritions.get(oKey)).get());
			}
		} catch (exFoodAlreadyNormalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			setWeight( ((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get() );
		} catch (exFoodWeightBelowZero e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			finalize();
		} catch (exFoodAlreadyNormalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(10.0f));
		
		clsConfigMap oNutritions = new clsConfigMap();
		
		oNutritions.add(eConfigEntries.FAT, new clsConfigDouble(0.3f));
		oNutritions.add(eConfigEntries.PROTEIN, new clsConfigDouble(0.1f));
		oNutritions.add(eConfigEntries.VITAMIN, new clsConfigDouble(0.2f));
		oNutritions.add(eConfigEntries.CARBOHYDRATE, new clsConfigDouble(0.5f));
		oNutritions.add(eConfigEntries.WATER, new clsConfigDouble(1.3f));
		oNutritions.add(eConfigEntries.MINERAL, new clsConfigDouble(0.4f));
		oNutritions.add(eConfigEntries.TRACEELEMENT, new clsConfigDouble(0.01f));
		
		oDefault.add(eConfigEntries.NUTRITIONS, oNutritions);
		//TODO add default values
		return oDefault;
	}	

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 */
	public void grow(double prWeight) {
		try {
			setWeight(getWeight() + Math.abs(prWeight));
			mnTotallyConsumed = false;
		} catch (exFoodWeightBelowZero e) {
			//can not happen
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 * @throws exFoodWeightBelowZero
	 */
	public void reduce(double prWeight) throws exFoodWeightBelowZero {
		setWeight(getWeight() - Math.abs(prWeight));
	}	
	
	public clsFood withdraw(double prAmount) {		
		clsFood oResult = new clsFood(this);
		double rWeight = getWeight();
		
		try {
			reduce(prAmount);
			oResult.setWeight(prAmount);
		} catch (exFoodWeightBelowZero e) {
			// TODO Auto-generated catch block
			try {
				oResult.setWeight(rWeight);
			} catch (exFoodWeightBelowZero e1) {
				// 
			}
			mnTotallyConsumed = true;
		}
		
		return oResult;
	}
	
	public boolean getTotallyConsumed() {
		return mnTotallyConsumed;
	}
	
	@Override
	public void setWeight(double prAmount) throws bw.exceptions.exFoodWeightBelowZero {
		super.setWeight(prAmount);
		
		if (getWeight() == 0.0) {
			mnTotallyConsumed = true;
		} else {
			mnTotallyConsumed = false;
		}
	}
}
