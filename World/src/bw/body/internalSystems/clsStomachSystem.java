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

import config.clsBWProperties;
import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsFillLevel;
import bw.utils.tools.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachSystem implements itfStepUpdateInternalState {
    public static final String P_NUMNUTRITIONS = "numnutritions";
	public static final String P_NUTRITIONTYPE = "type";
	public static final String P_NUTRITIONEFFICIENCY = "efficiency";
	public static final String P_NUTRITIONMETABOLISMFACTOR = "metabolismfactor";
	
	private HashMap<eNutritions, clsNutritionLevel> moNutritions;
	private HashMap<eNutritions, Double> moEnergyMetabolismFactor;
	private HashMap<eNutritions, Double> moEnergyEfficiency;
	
	private double mrEnergy;
	
	private double mrMaxWeight;
	private double mrWeight;
	
	public clsStomachSystem(String poPrefix, clsBWProperties poProp) {
		moNutritions = new HashMap<eNutritions, clsNutritionLevel>();
		moEnergyEfficiency = new HashMap<eNutritions, Double>();
		moEnergyMetabolismFactor = new HashMap<eNutritions, Double>();
		
		applyProperties(poPrefix, poProp);
		
		updateEnergy();
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		int i = 0;
		
		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.FAT.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.PROTEIN.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.VITAMIN.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.WATER.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.MINERAL.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.TRACEELEMENT.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 1);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0.1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		i++;

		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		oProp.setProperty(pre+i+"."+clsFillLevel.P_LOWERBOUND, 0.0);
		i++;

		oProp.setProperty(pre+P_NUMNUTRITIONS, i);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
	    String pre = clsBWProperties.addDot(poPrefix);
		
        int num = poProp.getPropertyInt(pre+P_NUMNUTRITIONS);
        for (int i=0; i<num; i++) {
        	String tmp_pre = pre+i+".";
        	
       		clsNutritionLevel oNL = new clsNutritionLevel(tmp_pre, poProp);
       		double rEfficiency = poProp.getPropertyDouble(tmp_pre+P_NUTRITIONEFFICIENCY);
       		double rFactor = poProp.getPropertyDouble(tmp_pre+P_NUTRITIONMETABOLISMFACTOR);
			String oSM = poProp.getPropertyString(tmp_pre+P_NUTRITIONTYPE);
			eNutritions nSM = eNutritions.valueOf(oSM);
			
			addNutritionType(nSM, oNL, rEfficiency, rFactor);
        }
	}		
	
	/**
	 * returns a clone of the complete list of stored values
	 * 
	 * TODO Type safety: Unchecked cast from Object to HashMap<Integer,clsMutableInteger>
	 *
	 * @return moList clone
	 */
	public HashMap<eNutritions, clsNutritionLevel> getList() {
		return new HashMap<eNutritions, clsNutritionLevel>(moNutritions);
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poId
	 */
	public void addNutritionType(eNutritions poId, clsNutritionLevel poNL, double prDefaultEfficiency, double prDefaultFactor) {
		if (!(moNutritions.containsKey(poId))) {
			moNutritions.put(poId, poNL);
			moEnergyEfficiency.put(poId, new Double(prDefaultEfficiency));
			moEnergyMetabolismFactor.put(poId, new Double(prDefaultFactor));
		}
		
		updateEnergy();		
		updateMaxWeight();
	}
	
	public void removeNutritionType(eNutritions poId) {
		if (moNutritions.containsKey(poId)) {
			moNutritions.remove(poId);
			moEnergyEfficiency.remove(poId);
			moEnergyMetabolismFactor.remove(poId);
		}

		updateEnergy();
		updateMaxWeight();
	}
	
	public Double getEnergyEfficency(eNutritions poId) {
		return moEnergyEfficiency.get(poId);
	}
	
	public Double getEnergyMetabolismFactor(eNutritions poId) {
		return moEnergyMetabolismFactor.get(poId);
	}
	
	private void updateMaxWeight() {
		mrMaxWeight = 0;
		
		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			clsNutritionLevel oNL = moNutritions.get(oKey);	
			mrMaxWeight += oNL.getMaxContent();
			
		}		
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poId
	 * @return
	 */
	public clsNutritionLevel getNutritionLevel(eNutritions poId) {
		return moNutritions.get(poId);
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 * @return
	 * @throws bw.exceptions.exNoSuchNutritionType 
	 */
	public double addNutrition(eNutritions poId, double prAmount) throws bw.exceptions.exNoSuchNutritionType {
		double rResult = 0;

		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new bw.exceptions.exNoSuchNutritionType(poId);
		}
		
		double rNutritionLevel = oNL.getContent();
		double rNutritionLevelUpdate = rNutritionLevel;
		
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
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param poId
	 * @param prAmount
	 * @return
	 * @throws bw.exceptions.exNoSuchNutritionType 
	 */
	public double withdrawNutrition(eNutritions poId, double prAmount) throws bw.exceptions.exNoSuchNutritionType {
		double rResult = 0;
		
		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new bw.exceptions.exNoSuchNutritionType(poId);
		}
		
		double rNutritionLevel = oNL.getContent();
		double rNutritionLevelUpdate = rNutritionLevel;
		
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
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @param prAmount
	 * @return
	 */
	public clsChangeEnergyResult withdrawEnergy(double prAmount) {
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		
		if (prAmount > 0) {
			double rEnergyLevel = this.getEnergy();
			
			Iterator<eNutritions> i = moNutritions.keySet().iterator();
			
			while (i.hasNext()) {
				eNutritions oKey = i.next();
				
				clsNutritionLevel oNL = moNutritions.get(oKey);
				
				double rFactor = moEnergyMetabolismFactor.get(oKey);			
				double rContent = oNL.getContent();
				double rFractionPercentage = 1.0f;
				double rDecreaseAmount = prAmount * rFactor;
				
				try {
					oNL.decrease(rDecreaseAmount);	
				} catch (exContentColumnMaxContentExceeded e) {
					try {
						rFractionPercentage = (oNL.getMaxContent() - rContent) / rDecreaseAmount;
					} catch (java.lang.ArithmeticException ee) {				
					}					
				
				} catch (exContentColumnMinContentUnderrun e) {
					try {
						rFractionPercentage = rContent / rDecreaseAmount;
					} catch (java.lang.ArithmeticException ee) {				
					}					
					
				}
				
				oResult.addFraction(oKey, new Double( rFractionPercentage ));
			}		
			
			this.updateEnergy();	
			
			double rEnergyLevelUpdate = this.getEnergy();
			
			try {
				oResult.setTotalPercentage( (rEnergyLevelUpdate-rEnergyLevel) / prAmount );
			} catch (java.lang.ArithmeticException e) {
			}
			
		}
		
		return oResult;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	@Override
	public void stepUpdateInternalState() {
		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			moNutritions.get(i.next()).step();
		}
		
		updateEnergy();
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	private void updateEnergy() {
		mrEnergy = 0.0;
		mrWeight = 0.0;

		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			double rEfficiency = moEnergyEfficiency.get(oKey);
			
			mrWeight += oNL.getContent();
			mrEnergy += oNL.getContent() * rEfficiency;
			
		}
		
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public double getEnergy() {
		return this.mrEnergy;
	}
	
	public double getMaxWeight() {
		return mrMaxWeight;
	}
	
	public double getWeight() {
		return mrWeight;
	}
	
	@Override
	public String toString() {
		return "energy:"+mrEnergy+"; weight:"+getWeight()+"; maxweight:"+getMaxWeight()+" | nutritions:"+moNutritions+" | efficiency:"+moEnergyEfficiency+" | factor:"+moEnergyMetabolismFactor;
	}
}
