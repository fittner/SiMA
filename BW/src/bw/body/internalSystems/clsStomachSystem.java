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
import bw.body.itfStepUpdateInternalState;
import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachSystem implements itfStepUpdateInternalState {
    public static final String P_NUMNUTRITIONS = "numnutritions";
	public static final String P_NUTRITIONTYPE = "type";
	public static final String P_NUTRITIONFRACTION = "fraction";
	
	private HashMap<eNutritions, clsNutritionLevel> moNutritions;
	private HashMap<eNutritions, Double> moFractions;
	private double mrFractionSum;
	private double mrEnergy;
	
	public clsStomachSystem(String poPrefix, clsBWProperties poProp) {
		moNutritions = new HashMap<eNutritions, clsNutritionLevel>();
		moFractions = new HashMap<eNutritions, Double>();
		
		applyProperties(poPrefix, poProp);
		
		updateFractionSum();
		updateEnergy();
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_NUMNUTRITIONS, 8);

		oProp.setProperty(pre+"0."+P_NUTRITIONTYPE, eNutritions.FAT.toString());
		oProp.setProperty(pre+"0."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"0.") );

		oProp.setProperty(pre+"1."+P_NUTRITIONTYPE, eNutritions.PROTEIN.toString());
		oProp.setProperty(pre+"1."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"1.") );

		oProp.setProperty(pre+"2."+P_NUTRITIONTYPE, eNutritions.VITAMIN.toString());
		oProp.setProperty(pre+"2."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"2.") );

		oProp.setProperty(pre+"3."+P_NUTRITIONTYPE, eNutritions.CARBOHYDRATE.toString());
		oProp.setProperty(pre+"3."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"3.") );

		oProp.setProperty(pre+"4."+P_NUTRITIONTYPE, eNutritions.WATER.toString());
		oProp.setProperty(pre+"4."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"4.") );

		oProp.setProperty(pre+"5."+P_NUTRITIONTYPE, eNutritions.MINERAL.toString());
		oProp.setProperty(pre+"5."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"5.") );

		oProp.setProperty(pre+"6."+P_NUTRITIONTYPE, eNutritions.TRACEELEMENT.toString());
		oProp.setProperty(pre+"6."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"6.") );

		oProp.setProperty(pre+"7."+P_NUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+"7."+P_NUTRITIONFRACTION, 1);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+"7.") );
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
	    String pre = clsBWProperties.addDot(poPrefix);
		
        int num = poProp.getPropertyInt(pre+P_NUMNUTRITIONS);
        for (int i=0; i<num; i++) {
        	String tmp_pre = pre+i+".";
        	
       		clsNutritionLevel oNL = new clsNutritionLevel(tmp_pre, poProp);
       		double rFraction = poProp.getPropertyDouble(tmp_pre+P_NUTRITIONFRACTION);
			String oSM = poProp.getPropertyString(tmp_pre+P_NUTRITIONTYPE);
			eNutritions nSM = eNutritions.valueOf(oSM);
			
			addNutritionType(nSM, oNL, rFraction);
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
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 */
	public void addNutritionType(eNutritions poId, clsNutritionLevel poNL, double prDefaultFraction) {
		if (!(moNutritions.containsKey(poId))) {
			moNutritions.put(poId, poNL);
			moFractions.put(poId, new Double(prDefaultFraction));
		}
		
		updateFractionSum();
		updateEnergy();		
	}
	
	public void removeNutritionType(eNutritions poId) {
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
		mrFractionSum = 0.0;
		
		Iterator<eNutritions> i = moFractions.keySet().iterator();
		
		while (i.hasNext()) {
			mrFractionSum += moFractions.get(i.next());
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param poId
	 * @return
	 */
	public clsNutritionLevel getNutritionLevel(eNutritions poId) {
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
	 * TODO (deutsch) - insert description
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
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @return
	 */
	public clsChangeEnergyResult addEnergy(double prAmount) {
		double rAmountFraction = prAmount / this.mrFractionSum;
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		double rEnergyLevel = this.getEnergy();

		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			double rFraction = moFractions.get(oKey).floatValue();
			double rContent = oNL.getContent();
			double rFractionPercentage = 0.0f;
			
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
			
			oResult.addFraction(oKey, new Double( rFractionPercentage ));
			
		}
				
		this.updateEnergy();
		
		double rEnergyLevelUpdate = this.getEnergy();
		
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
	public clsChangeEnergyResult withdrawEnergy(double prAmount) {		
		double rAmountFraction = prAmount / this.mrFractionSum;
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		double rEnergyLevel = this.getEnergy();
		
		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			
			double rFraction = moFractions.get(oKey).floatValue();			
			double rContent = oNL.getContent();
			double rFractionPercentage = 0.0f;

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
			
			oResult.addFraction(oKey, new Double( rFractionPercentage ));
			
		}		
		
		this.updateEnergy();	
		
		double rEnergyLevelUpdate = this.getEnergy();
		
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
	public void stepUpdateInternalState() {
		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			moNutritions.get(i.next()).step();
		}
		
		updateEnergy();
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void updateEnergy() {
		mrEnergy = 0.0;

		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			eNutritions oKey = i.next();
			
			clsNutritionLevel oNL = moNutritions.get(oKey);
			double rFraction = moFractions.get(oKey);
			
			mrEnergy += oNL.getContent() * rFraction;
		}
		
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public double getEnergy() {
		return this.mrEnergy;
	}
}
