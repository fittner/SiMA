/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   Loch Ness
 * $Author::                   deutsch Deutsch
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import config.clsProperties;

import bw.exceptions.exFoodAlreadyNormalized;
import bw.exceptions.exFoodNotFinalized;
import bw.utils.datatypes.clsMutableDouble;
import bw.utils.enums.eNutritions;

/**
 * Food describes a piece of junk that can be transferred from one agent to the other (e.g. plant to ARSIN). It is
 * a composition of nutritionfractions (which sum up to 1.0) and the amount (or weight) of the food piece.
 * 
 * FIXME (deutsch) is not working - debugging needed!
 * 
 * @author deutsch
 * 
 */
public class clsFood {
	public static final String P_WEIGHT = "weight";
	public static final String P_NUMNUTRITIONS = "numnutritions";
	public static final String P_NUTRITIONTYPE = "type";
	public static final String P_NUTRITIONFRACTION = "fraction";
	
	private HashMap<eNutritions, clsMutableDouble> moComposition;
	private double mrWeight;
	private boolean mnFinalized;
	
	/**
	 * 
	 */
	public clsFood() {
		moComposition = new HashMap<eNutritions, clsMutableDouble>();
		mrWeight = 0.0f;
		mnFinalized = false;
	}
	
	
	public clsFood(String poPrefix, clsProperties poProp) {
		moComposition = new HashMap<eNutritions, clsMutableDouble>();
		mrWeight = 0.0f;
		mnFinalized = false;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_WEIGHT, 5.0 );
		oProp.setProperty(pre+P_NUMNUTRITIONS, 6 );
		oProp.setProperty(pre+"0."+P_NUTRITIONTYPE, eNutritions.PROTEIN.name());
		oProp.setProperty(pre+"0."+P_NUTRITIONFRACTION, 0.1);

		oProp.setProperty(pre+"1."+P_NUTRITIONTYPE, eNutritions.FAT.name());
		oProp.setProperty(pre+"1."+P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(pre+"2."+P_NUTRITIONTYPE, eNutritions.VITAMIN.name());
		oProp.setProperty(pre+"2."+P_NUTRITIONFRACTION, 0.1);

		oProp.setProperty(pre+"3."+P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.name());
		oProp.setProperty(pre+"3."+P_NUTRITIONFRACTION, 1.0);

		oProp.setProperty(pre+"4."+P_NUTRITIONTYPE, eNutritions.WATER.name());
		oProp.setProperty(pre+"4."+P_NUTRITIONFRACTION, 2.0);

		oProp.setProperty(pre+"5."+P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+"5."+P_NUTRITIONFRACTION, 0.8);
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		mrWeight = poProp.getPropertyDouble(pre+P_WEIGHT);
		
		int num = poProp.getPropertyInt(pre+P_NUMNUTRITIONS);
		for (int i=0; i<num; i++) {
			String oNutType = poProp.getPropertyString(pre+new Integer(i).toString()+"."+P_NUTRITIONTYPE);
			eNutritions nNutType = eNutritions.valueOf(oNutType);
			double fFraction = poProp.getPropertyDouble(pre+new Integer(i).toString()+"."+P_NUTRITIONFRACTION);
			try {
				addNutritionFraction(nNutType, new clsMutableDouble(fFraction));
			} catch (exFoodAlreadyNormalized e) {
				//unreachable - hopefully - mnFinalized is set to false in this function ...
			}
		}
		
		try {
			finalize();
		} catch (exFoodAlreadyNormalized e) {
			//unreachable - hopefully - mnFinalized is set to false in this function ...
		}
	}	
	
	public clsFood(clsFood poFood) {
		mrWeight = poFood.mrWeight;
		mnFinalized = poFood.mnFinalized;
		moComposition = new HashMap<eNutritions, clsMutableDouble>(); //Added by BD
		
		Iterator<eNutritions> i = poFood.moComposition.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			clsMutableDouble oValue = new clsMutableDouble(poFood.moComposition.get(oKey));
			moComposition.put(oKey, oValue);
		}
	}
	
	/**
	 * set the weight of the food piece (0.0 <= x < FLOATMAX).
	 *
	 * @param prWeight
	 * @throws bw.exceptions.exFoodWeightBelowZero 
	 */
	public void setWeight(double prWeight) throws bw.exceptions.exFoodWeightBelowZero {
		mrWeight = prWeight;
		
		if (mrWeight < 0.0f) {
			mrWeight = 0.0f;
			
			throw new bw.exceptions.exFoodWeightBelowZero();
		}
	}
	
	/**
	 * returns the weight of the food piece
	 *
	 * @return the mrWeight
	 */
	public double getWeight() {
		return mrWeight;
	}
	
	/**
	 * Returns the total weight of a certain nutrition within this piece of food.
	 * 
	 * @param poNutritionId
	 * @return mrWeight * mrNutritionFraction
	 * @throws exFoodNotFinalized 
	 */
	public double getNutritionWeight(eNutritions poNutritionId) throws bw.exceptions.exFoodNotFinalized {
		if (!mnFinalized) {
			throw new bw.exceptions.exFoodNotFinalized();
		}
		
		double rValue = moComposition.get(poNutritionId).doubleValue();
		double rTemp = mrWeight * rValue;
		return rTemp;
		
//		return mrWeight * moComposition.get(poNutritionId).floatValue();
	}
	
	/**
	 * Similar to getNutritionWeight - only this time, the function returns a HashMap containting all
	 * total weights of all nutritions within this type of food.
	 *
	 * @return the HashMap<Integer, clsMutableFloat>
	 * @throws bw.exceptions.exFoodNotFinalized
	 */
	public HashMap<eNutritions, clsMutableDouble> getNutritionWeights() throws bw.exceptions.exFoodNotFinalized {
		if (!mnFinalized) {
			throw new bw.exceptions.exFoodNotFinalized();
		}
		
		HashMap<eNutritions, clsMutableDouble> oComposition = new HashMap<eNutritions, clsMutableDouble>();
		
		Iterator<eNutritions> i =  moComposition.keySet().iterator();
		while (i.hasNext()) {
			eNutritions oKey = (eNutritions) i.next();
			clsMutableDouble oFraction = moComposition.get(oKey);
			oComposition.put(oKey, new clsMutableDouble(oFraction.doubleValue() * mrWeight));
		}		
		
		return oComposition;
	}
	
	/**
	 * This functions adds another piece of food to the current one. In fact the results of getNutritonWeights()
	 * of both pieces are added and afterwards normalized. The weights are simply added.
	 *
	 * @param poFood
	 * @throws exFoodNotFinalized 
	 * @throws exFoodAlreadyNormalized 
	 */
	public void addFood(clsFood poFood) throws exFoodNotFinalized, exFoodAlreadyNormalized {
		double rWeight = this.getWeight() + poFood.getWeight();
		
		HashMap<eNutritions, clsMutableDouble> oSetA = poFood.getNutritionWeights();
		HashMap<eNutritions, clsMutableDouble> oSetB = this.getNutritionWeights();
		
		//look for each entry of setA if there is a matching entry in setB. if yes, add value of setB to setA
		Iterator<eNutritions> i = oSetA.keySet().iterator();
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			clsMutableDouble oValue = oSetA.get(oKey);
			
			if (oSetB.containsKey(oKey)) {
				oValue.add( oSetB.get(oKey) );
			}
		}
		
		//add all entries from setB which have not corresponding entry in setA to setA
		Iterator<eNutritions> j = oSetB.keySet().iterator();
		while (j.hasNext()) {
			eNutritions oKey = j.next();
			
			if (!oSetA.containsKey(oKey)) {
				clsMutableDouble oValue = oSetB.get(oKey);
				oSetA.put(oKey, oValue);
			}
		}
		
		//reset this food
		this.mnFinalized = false;
		this.mrWeight = rWeight;
		this.moComposition = new HashMap<eNutritions, clsMutableDouble>(oSetA);
		this.finalize();
	}
	

	/**
	 * Adds a new nutrition type with its fraction to the object. Already existing nutrition type entries
	 * will be overwritten by the new fraction.
	 * 
	 * @param poId
	 * @param poFraction
	 */
	public void addNutritionFraction(eNutritions poNutritionId, clsMutableDouble poFraction) throws bw.exceptions.exFoodAlreadyNormalized {
		if (mnFinalized) {
			throw new bw.exceptions.exFoodAlreadyNormalized();
		}
		
		if (poFraction.doubleValue() < 0.0f) {
			poFraction.set(0.0f);
		}
		
		moComposition.put(poNutritionId, poFraction);
	}
	
	/**
	 * Normalize all fractions to a total sum of 1.0f
	 *
	 */
	private void normalizeFractions() throws bw.exceptions.exFoodAlreadyNormalized {
		if (mnFinalized) {
			throw new bw.exceptions.exFoodAlreadyNormalized();
		}
		
		double rFractionSum = 0.0f;
		Iterator<eNutritions> i =  moComposition.keySet().iterator();
			
		while (i.hasNext()) {
			rFractionSum += moComposition.get(i.next()).doubleValue();
		}
			
		double rInvFSum = 1.0f/rFractionSum;
			
		i = moComposition.keySet().iterator();
		while (i.hasNext()) {
			clsMutableDouble oFraction = moComposition.get(i.next());
			oFraction.mul(rInvFSum);
		}
	}
	
	@Override
	public void finalize() throws bw.exceptions.exFoodAlreadyNormalized {
		normalizeFractions();
		mnFinalized = true;
	}

	@Override
	public String toString() {
		String oR = "";
		
		oR += "weight:"+mrWeight+" | ";
		for (Map.Entry<eNutritions, clsMutableDouble> entry: moComposition.entrySet()) {
			oR+=entry.getKey().name()+":"+entry.getValue()+", ";
		}
		oR = oR.substring(0, oR.length()-2);
		return oR;	
	}
}
