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
import bw.utils.container.clsBaseConfig;
import bw.utils.container.clsConfigEnum;
import bw.utils.container.clsConfigList;
import bw.utils.container.clsConfigMap;
import bw.utils.container.clsConfigDouble;
import bw.utils.enums.eConfigEntries;
import bw.utils.enums.eNutritions;
import bw.utils.tools.clsNutritionLevel;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsStomachSystem implements itfStepUpdateInternalState {
    private clsConfigMap moConfig;
    
	private HashMap<Integer, clsNutritionLevel> moNutritions;
	private HashMap<Integer, Double> moFractions;
	private double mrFractionSum;
	private double mrEnergy;
	
	private double mrDefaultMaxLevel;
	private double mrDefaultContent;
	private double mrDefaultLowerBorder;
	private double mrDefaultUpperBorder;
	private double mrDefaultDecreasePerStep;
	private double mrDefaultFraction;
	
	/**
	 * TODO (deutsch) - insert description
	 */
	public clsStomachSystem(clsConfigMap poConfig) {
		moNutritions = new HashMap<Integer, clsNutritionLevel>();
		moFractions = new HashMap<Integer, Double>();
		
		moConfig = getFinalConfig(poConfig);
		applyConfig();	
		
		updateFractionSum();
		updateEnergy();
	}
	
	private void applyConfig() {
		
		clsConfigMap oNutConf = (clsConfigMap)moConfig.get(eConfigEntries.NUTRITIONCONFIG);
		
		mrDefaultMaxLevel = ((clsConfigDouble)oNutConf.get(eConfigEntries.MAXCONTENT)).get();
		mrDefaultContent = ((clsConfigDouble)oNutConf.get(eConfigEntries.CONTENT)).get();
		mrDefaultLowerBorder = ((clsConfigDouble)oNutConf.get(eConfigEntries.LOWERBOUND)).get();
		mrDefaultUpperBorder = ((clsConfigDouble)oNutConf.get(eConfigEntries.UPPERBOUND)).get();
		mrDefaultDecreasePerStep = ((clsConfigDouble)oNutConf.get(eConfigEntries.DECAYRATE)).get();
		mrDefaultFraction = ((clsConfigDouble)oNutConf.get(eConfigEntries.FRACTION)).get();		

		clsConfigList oNutList = (clsConfigList)moConfig.get(eConfigEntries.NUTRITIONS);
		Iterator<clsBaseConfig> i = oNutList.iterator();
		
		while (i.hasNext()) {
			addNutritionType( ((clsConfigEnum)i.next()).ordinal() );
		}

		addEnergy( ((clsConfigDouble)moConfig.get(eConfigEntries.CONTENT)).get() );		
	}

	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		clsConfigMap oNutritionConfig = new clsConfigMap();		
		oNutritionConfig.add(eConfigEntries.MAXCONTENT, new clsConfigDouble(5.0f));
		oNutritionConfig.add(eConfigEntries.CONTENT, new clsConfigDouble(0.0f));
		oNutritionConfig.add(eConfigEntries.LOWERBOUND, new clsConfigDouble(0.5f));
		oNutritionConfig.add(eConfigEntries.UPPERBOUND, new clsConfigDouble(2.5f));
		oNutritionConfig.add(eConfigEntries.DECAYRATE, new clsConfigDouble(0.0001f));
		oNutritionConfig.add(eConfigEntries.FRACTION, new clsConfigDouble(1.0f));
		oDefault.add(eConfigEntries.NUTRITIONCONFIG, oNutritionConfig);		
		
		clsConfigList oNutritions = new clsConfigList();
		oNutritions.add(new clsConfigEnum(eNutritions.FAT));
		oNutritions.add(new clsConfigEnum(eNutritions.PROTEIN));
		oNutritions.add(new clsConfigEnum(eNutritions.VITAMIN));
		oNutritions.add(new clsConfigEnum(eNutritions.CARBOHYDRATE));
		oNutritions.add(new clsConfigEnum(eNutritions.WATER));
		oNutritions.add(new clsConfigEnum(eNutritions.MINERAL));
		oNutritions.add(new clsConfigEnum(eNutritions.TRACEELEMENT));
		oDefault.add(eConfigEntries.NUTRITIONS, oNutritions);
		
		oDefault.add(eConfigEntries.CONTENT, new clsConfigDouble(10.0f));

		return oDefault;
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
				moFractions.put(poId, new Double(mrDefaultFraction));
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
	public double addNutrition(Integer poId, double prAmount) throws bw.exceptions.exNoSuchNutritionType {
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
	public double withdrawNutrition(Integer poId, double prAmount) throws bw.exceptions.exNoSuchNutritionType {
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

		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
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
		
		Iterator<Integer> i = moNutritions.keySet().iterator();
		
		while (i.hasNext()) {
			Integer oKey = i.next();
			
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
	public double getEnergy() {
		return this.mrEnergy;
	}
}
