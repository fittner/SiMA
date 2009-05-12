/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodAmountBelowZero;
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigFloat;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.eNutritions;
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
	
	public clsFlesh(clsConfigMap poConfig) {
		super();
		moConfig = getFinalConfig(poConfig);
		applyConfig();
	}
	
	private void applyConfig() {
		
		clsConfigMap oNutritions = (clsConfigMap) moConfig.get(eConfigEntries.NUTRITIONS);
		
		Iterator<Integer> i = oNutritions.iterator();
		
		try {
			while (i.hasNext()) {
				Integer oKey = i.next();
				addNutritionFraction(oKey, ((clsConfigFloat)oNutritions.get(oKey)).get());
			}
		} catch (exFoodAlreadyNormalized e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			setAmount( ((clsConfigFloat)moConfig.get(eConfigEntries.CONTENT)).get() );
		} catch (exFoodAmountBelowZero e1) {
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
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigFloat(10.0f));
		
		clsConfigMap oNutritions = new clsConfigMap();
		
		oNutritions.add(eNutritions.FAT, new clsConfigFloat(0.3f));
		oNutritions.add(eNutritions.PROTEIN, new clsConfigFloat(0.1f));
		oNutritions.add(eNutritions.VITAMIN, new clsConfigFloat(0.2f));
		oNutritions.add(eNutritions.CARBOHYDRATE, new clsConfigFloat(0.5f));
		oNutritions.add(eNutritions.WATER, new clsConfigFloat(1.3f));
		oNutritions.add(eNutritions.MINERAL, new clsConfigFloat(0.4f));
		oNutritions.add(eNutritions.TRACEELEMENT, new clsConfigFloat(0.01f));
		
		oDefault.add(eConfigEntries.NUTRITIONS, oNutritions);
		//TODO add default values
		return oDefault;
	}	

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 */
	public void grow(float prWeight) {
		try {
			setAmount(getAmount() + Math.abs(prWeight));
		} catch (exFoodAmountBelowZero e) {
			//can not happen
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prWeight
	 * @throws exFoodAmountBelowZero
	 */
	public void reduce(float prWeight) throws exFoodAmountBelowZero {
		setAmount(getAmount() - Math.abs(prWeight));
	}	
}
