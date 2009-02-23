/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.internalSystems;

import java.util.HashMap;
import java.util.Iterator;

import bw.body.itfStep;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachSystem implements itfStep {
	private HashMap<Integer, clsNutritionLevel> moNutritions;
	private HashMap<Integer, Float> moFractions;
	private float mrFractionSum;
	private float mrEnergy;
	
	private float mrDefaultMaxLevel = 5.0f; //pseudo const for init purposes
	private float mrDefaultContent = 0.0f; //pseudo const for init purposes
	private float mrDefaultLowerBorder = 0.5f; //pseudo const for init purposes
	private float mrDefaultUpperBorder = 2.5f; //pseudo const for init purposes
	private float mrDefaultDecreasePerStep = 0.0001f; //pseudo const for init purposes
	private float mrDefaultFraction = 1.0f; //pseudo const for init purposes
	
	/**
	 * TODO (deutsch) - insert description
	 */
	public clsStomachSystem() {
		super();
		
		moNutritions = new HashMap<Integer, clsNutritionLevel>();
		moFractions = new HashMap<Integer, Float>();
		updateFractionSum();
		updateEnergy();
	}
	
	/**
	 * returns a clone of the complete list of stored values
	 * 
	 * TODO Type safety: Unchecked cast from Object to HashMap<Integer,clsMutableInteger>
	 *
	 * @return moList clone
	 */
	public HashMap<Integer, clsNutritionLevel> getList() {
		return new HashMap<Integer, clsNutritionLevel>(moNutritions);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 */
	public void addNutritionType(Integer poId) {
		if (!(moNutritions.containsKey(poId))) {
			try {
				clsNutritionLevel oNL = new clsNutritionLevel(mrDefaultContent, mrDefaultMaxLevel, mrDefaultLowerBorder, 
						mrDefaultUpperBorder, mrDefaultDecreasePerStep);

				moNutritions.put(poId, oNL);
				moFractions.put(poId, new Float(mrDefaultFraction));
			} catch (exContentColumnMaxContentExceeded e) {
			} catch (exContentColumnMinContentUnderrun e) {
			}			
		}
		
		updateFractionSum();
		updateEnergy();		
	}
	
	public void removeNutritionType(Integer poId) {
		if (moNutritions.containsKey(poId)) {
			moNutritions.remove(poId);
		}
		
		updateFractionSum();
		updateEnergy();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void updateFractionSum() {
		mrFractionSum = 0.0f;
		
		Iterator<Integer> i = moFractions.keySet().iterator();
		
		while (i.hasNext()) {
			mrFractionSum += (Float)moFractions.get(i.next()).floatValue();
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @return
	 */
	public clsNutritionLevel getNutritionLevel(Integer poId) {
		return moNutritions.get(poId);
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 * @return
	 * @throws bw.exceptions.exNoSuchNutritionType 
	 */
	public float addNutrition(Integer poId, float prAmount) throws bw.exceptions.exNoSuchNutritionType {
		float rResult = 0;

		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new bw.exceptions.exNoSuchNutritionType(poId);
		}
		
		float rNutritionLevel = oNL.getContent();
		float rNutritionLevelUpdate = rNutritionLevel;
		
		if (oNL != null) {
			try {
				rNutritionLevelUpdate = oNL.increase(prAmount);
			} catch (exContentColumnMaxContentExceeded e) {
			} catch (exContentColumnMinContentUnderrun e) {
			}
		}
		this.updateEnergy();	
		
		try {
			rResult = (rNutritionLevelUpdate - rNutritionLevel) / prAmount;
		} catch (java.lang.ArithmeticException e) {
			rResult = 0.0f;
		}
		
		return rResult;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 * @return
	 * @throws bw.exceptions.exNoSuchNutritionType 
	 */
	public float withdrawNutrition(Integer poId, float prAmount) throws bw.exceptions.exNoSuchNutritionType {
		float rResult = 0;
		
		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new bw.exceptions.exNoSuchNutritionType(poId);
		}
		
		float rNutritionLevel = oNL.getContent();
		float rNutritionLevelUpdate = rNutritionLevel;
		
		if (oNL != null) {
			try {
				oNL.decrease(prAmount);
			} catch (exContentColumnMaxContentExceeded e) {
			} catch (exContentColumnMinContentUnderrun e) {
			}
		}
		this.updateEnergy();
		
		try {
			rResult = (rNutritionLevelUpdate - rNutritionLevel) / prAmount;
		} catch (java.lang.ArithmeticException e) {
			rResult = 0.0f;
		}
		
		return rResult;		
	}	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @return
	 */
	public clsChangeEnergyResult addEnergy(float prAmount) {
		float rAmountFraction = prAmount / this.mrFractionSum;
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		float rEnergyLevel = this.getEnergy();

		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			float rFraction = moFractions.get(oKey).floatValue();
			float rContent = oNL.getContent();
			float rFractionPercentage = 0.0f;
			
			try {
				oNL.increase(rFraction * rAmountFraction);
				rFractionPercentage = 1.0f;
				
			} catch (exContentColumnMaxContentExceeded e) {
				try {
					rFractionPercentage = (oNL.getMaxContent() - rContent) / prAmount;
				} catch (java.lang.ArithmeticException ee) {				
				}					
			
			} catch (exContentColumnMinContentUnderrun e) {
				try {
					rFractionPercentage = rContent / prAmount;
				} catch (java.lang.ArithmeticException ee) {				
				}					
				
			}
			
			oResult.addFraction(oKey, new Float( rFractionPercentage ));
			
		}
				
		this.updateEnergy();
		
		float rEnergyLevelUpdate = this.getEnergy();
		
		try {
			oResult.setTotalPercentage( (rEnergyLevelUpdate-rEnergyLevel) / prAmount );
		} catch (java.lang.ArithmeticException e) {
		}
		
		return oResult;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @return
	 */
	public clsChangeEnergyResult withdrawEnergy(float prAmount) {		
		float rAmountFraction = prAmount / this.mrFractionSum;
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		float rEnergyLevel = this.getEnergy();
		
		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			float rFraction = moFractions.get(oKey).floatValue();			
			float rContent = oNL.getContent();
			float rFractionPercentage = 0.0f;

			try {
				oNL.decrease(rFraction * rAmountFraction);
				rFractionPercentage = 1.0f;
				
			} catch (exContentColumnMaxContentExceeded e) {
				try {
					rFractionPercentage = (oNL.getMaxContent() - rContent) / prAmount;
				} catch (java.lang.ArithmeticException ee) {				
				}					
			
			} catch (exContentColumnMinContentUnderrun e) {
				try {
					rFractionPercentage = rContent / prAmount;
				} catch (java.lang.ArithmeticException ee) {				
				}					
				
			}
			
			oResult.addFraction(oKey, new Float( rFractionPercentage ));
			
		}		
		
		this.updateEnergy();	
		
		float rEnergyLevelUpdate = this.getEnergy();
		
		try {
			oResult.setTotalPercentage( (rEnergyLevelUpdate-rEnergyLevel) / prAmount );
		} catch (java.lang.ArithmeticException e) {
		}
		
		return oResult;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void step() {
		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			((clsNutritionLevel)moNutritions.get(i.next())).step();
		}
		
		updateEnergy();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void updateEnergy() {
		mrEnergy = 0.0f;

		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			float rFraction = moFractions.get(oKey).floatValue();
			
			mrEnergy += oNL.getContent() * rFraction;
		}
		
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public float getEnergy() {
		return this.mrEnergy;
	}
}
