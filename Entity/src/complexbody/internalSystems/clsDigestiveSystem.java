/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package complexbody.internalSystems;

import java.util.HashMap;
import java.util.Iterator;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;

import utils.exceptions.exContentColumnMaxContentExceeded;
import utils.exceptions.exContentColumnMinContentUnderrun;
import utils.exceptions.exNoSuchNutritionType;

import entities.enums.eNutritions;
import body.itfStepUpdateInternalState;
import body.utils.clsFillLevel;
import body.utils.clsNutritionLevel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDigestiveSystem implements itfStepUpdateInternalState {
    public static final String P_NUMNUTRITIONS = "numnutritions";
	public static final String P_NUTRITIONTYPE = "type";
	public static final String P_NUTRITIONEFFICIENCY = "efficiency";
	public static final String P_NUTRITIONMETABOLISMFACTOR = "metabolismfactor";
	
	
	public static final String P_MODULE_NAME ="STOMACHSYSTEM";
	
	private HashMap<eNutritions, clsNutritionLevel> moNutritions;
	private HashMap<eNutritions, Double> moEnergyMetabolismFactor;
	private HashMap<eNutritions, Double> moEnergyEfficiency;
	
	private double mrEnergy;
	
	private double mrMaxWeight;
	private double mrWeight;
	
	int moRectumWaitStepCounter = 0;
	
	private clsPersonalityParameterContainer moPersonalityParameterContainer;
	
	public clsDigestiveSystem(String poPrefix, clsProperties poProp, 	clsPersonalityParameterContainer poPersonalityParameterContainer) {
		moPersonalityParameterContainer = poPersonalityParameterContainer;
		
		moNutritions = new HashMap<eNutritions, clsNutritionLevel>();
		moEnergyEfficiency = new HashMap<eNutritions, Double>();
		moEnergyMetabolismFactor = new HashMap<eNutritions, Double>();
		
		applyProperties(poPrefix, poProp);
		
		EmptyRectum();
		
		fillRectumWithDefinedStartingValue();
		fillStomachWithDefinedStartinGValue();
		
		updateEnergy();
	}

	
	/**
	 * @since Apr 17, 2013 1:46:44 PM
	 *
	 */
	private void fillStomachWithDefinedStartinGValue() {
		//for startup

		double startingValue = moPersonalityParameterContainer.getPersonalityParameter(P_MODULE_NAME, "STARTLEVEL_STOMACH").getParameterDouble();

		try{
			this.getNutritionLevel(eNutritions.FAT).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.PROTEIN).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.VITAMIN).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.CARBOHYDRATE).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.WATER).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.MINERAL).setContent(startingValue/7);
			this.getNutritionLevel(eNutritions.TRACEELEMENT).setContent(startingValue/7);
			
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO (herret) - Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO (herret) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * @since Apr 17, 2013 1:46:38 PM
	 *
	 */
	private void fillRectumWithDefinedStartingValue() {
		//for startup

		double startingValue = moPersonalityParameterContainer.getPersonalityParameter(P_MODULE_NAME, "STARTLEVEL_RECTUM").getParameterDouble();

		clsNutritionLevel oExcrement = this.getNutritionLevel(eNutritions.EXCREMENT);
		try {
			oExcrement.setContent(startingValue);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO (herret) - Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO (herret) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
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
		
		oProp.setProperty(pre+i+"."+P_NUTRITIONTYPE, eNutritions.EXCREMENT.toString());
		oProp.setProperty(pre+i+"."+P_NUTRITIONEFFICIENCY, 0);
		oProp.setProperty(pre+i+"."+P_NUTRITIONMETABOLISMFACTOR, 0);
		oProp.putAll( clsNutritionLevel.getDefaultProperties(pre+i+".") );
		oProp.setProperty(pre+i+"."+clsFillLevel.P_LOWERBOUND, 0.0);
		i++;

		oProp.setProperty(pre+P_NUMNUTRITIONS, i);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
		
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
	 * @throws utils.exceptions.exNoSuchNutritionType 
	 */
	public double addNutrition(eNutritions poId, double prAmount) throws utils.exceptions.exNoSuchNutritionType {
		double rResult = 0;

		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new utils.exceptions.exNoSuchNutritionType(poId);
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
	 * @throws utils.exceptions.exNoSuchNutritionType 
	 */
	public double withdrawNutrition(eNutritions poId, double prAmount) throws utils.exceptions.exNoSuchNutritionType {
		double rResult = 0;
		
		clsNutritionLevel oNL = this.getNutritionLevel(poId);
		
		if (oNL == null) {
			throw new utils.exceptions.exNoSuchNutritionType(poId);
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
	 * USE THIS FOR TESTS INSIDE THE MODEL ONLY
	 * This destroys all the energy in the stomach system, thus creates big hunger.
	 * This can be used to force the agents into a abnormal state!
	 *
	 * @since 21.06.2012 10:58:38
	 *
	 * @param prAmount
	 */
	public void DestroyAllEnergyForModelTesting() {
		clsChangeEnergyResult oResult = new clsChangeEnergyResult();
		
			
			Iterator<eNutritions> i = moNutritions.keySet().iterator();
			
			while (i.hasNext()) {
				eNutritions oKey = i.next();
				
				clsNutritionLevel oNL = moNutritions.get(oKey);

				double rContent = oNL.getContent();

				try {
					oNL.decrease(rContent);	
				} catch (exContentColumnMaxContentExceeded e) {
					try {
					} catch (java.lang.ArithmeticException ee) {				
					}					
				
				} catch (exContentColumnMinContentUnderrun e) {
					try {
					} catch (java.lang.ArithmeticException ee) {				
					}					
				}
			}		
			
			this.updateEnergy();	
	
			
			try {
				oResult.setTotalPercentage( 0 );
			} catch (java.lang.ArithmeticException e) {
			}
		
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 */
	@Override
	public void stepUpdateInternalState() {
		Iterator<eNutritions> i = moNutritions.keySet().iterator();
		
		CreateExcrementFromUndigestable();
		
		while (i.hasNext()) {
			moNutritions.get(i.next()).step();
		}
		
		
		
		updateEnergy();

	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 15.11.2012 15:52:37
	 *
	 */
	private void EmptyRectum() {

		//for startup
		clsNutritionLevel oUndigestable = this.getNutritionLevel(eNutritions.UNDIGESTABLE);
		clsNutritionLevel oExcrement = this.getNutritionLevel(eNutritions.EXCREMENT);
		

		double rExcrementContent = oExcrement.getContent();
		double rUndigestableContent = oUndigestable.getContent();

		try {
			oUndigestable.decrease(rUndigestableContent);
			oExcrement.decrease(rExcrementContent);
		} catch (exContentColumnMaxContentExceeded e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		} catch (exContentColumnMinContentUnderrun e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 14.11.2012 13:43:43
	 *
	 */
	private void CreateExcrementFromUndigestable() {
		
		double digestionAmountperStep = moPersonalityParameterContainer.getPersonalityParameter(P_MODULE_NAME, "DIGEST_SPEED").getParameterDouble();
		int oWaitForSteps = 40; // wie viele steps verzoegerung zwischen essen und start der Verdauung
		

		clsNutritionLevel oUndigestable = this.getNutritionLevel(eNutritions.UNDIGESTABLE);
		clsNutritionLevel oExcrement = this.getNutritionLevel(eNutritions.EXCREMENT);
		
		if(oUndigestable.getContent() > 0 && oUndigestable.getContent() > digestionAmountperStep)
		{
			try {
				
//				if(oExcrement.getContent() >= oExcrement.getMaxContent())
//				{
//					//TODO zB schmerz erzeugen, derzeit warten wir damit auf die verdauung wenn excremente voll ist
//					
//				}
//				else
//				{ // undigestable -> excrement
					if(oUndigestable.getContent() >= oUndigestable.getUpperBound()) // wenn undigestable voll -> starte verdauung
					{
						if(moRectumWaitStepCounter >= oWaitForSteps) //warte fuer X steps
						{
							oUndigestable.decrease(digestionAmountperStep);

							this.addNutrition(eNutritions.EXCREMENT, digestionAmountperStep);
							
						}
						else
						{
							moRectumWaitStepCounter++;
						}
						
					}
					

				//}
				
			} catch (exContentColumnMaxContentExceeded e) {
				// TODO (muchitsch) - Auto-generated catch block
				e.printStackTrace();
			} catch (exContentColumnMinContentUnderrun e) {
				// TODO (muchitsch) - Auto-generated catch block
				e.printStackTrace();
			} catch (exNoSuchNutritionType e) {
				// TODO (muchitsch) - Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void ResetRectumWaitCounter(){
		this.moRectumWaitStepCounter = 0;
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
			
			if(oKey == eNutritions.EXCREMENT || oKey == eNutritions.UNDIGESTABLE)	{
			}
			else{
			
				clsNutritionLevel oNL = moNutritions.get(oKey);

				double rEfficiency = moEnergyEfficiency.get(oKey);
				
				mrWeight += oNL.getContent();
				mrEnergy += oNL.getContent() * rEfficiency;
			}
			
			
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
