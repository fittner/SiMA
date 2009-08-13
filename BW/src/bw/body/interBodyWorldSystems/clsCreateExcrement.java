/**
 * clsCreateExcrement.java: BW - bw.body.interBodyWorldSystems
 * 
 * @author deutsch
 * 13.08.2009, 10:18:22
 */
package bw.body.interBodyWorldSystems;

import bw.body.internalSystems.clsStomachSystem;
import bw.entities.clsSmartExcrement;
import bw.exceptions.exNoSuchNutritionType;
import bw.utils.enums.eNutritions;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 13.08.2009, 10:18:22
 * 
 */
public class clsCreateExcrement {
	public static final String P_SMARTEXCREMENTS = "smartexcrements";
	public static final String P_GARBAGENUTRITIONTYPE = "garbagenutritiontype";
	public static final String P_WEIGHT = "weight";
	
	private eNutritions mnGarbageNutritionType;
	private clsStomachSystem moStomachSystem; // reference to existing stomach
	private double mrWeight;
	private clsBWProperties moSmartExcrementProps;


	public clsCreateExcrement(String poPrefix, clsBWProperties poProp, clsStomachSystem poStomach) {
		moStomachSystem = poStomach;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_GARBAGENUTRITIONTYPE, eNutritions.UNDIGESTABLE.toString());
		oProp.setProperty(pre+P_WEIGHT, 1);
		oProp.putAll( clsSmartExcrement.getDefaultProperties(pre+P_SMARTEXCREMENTS) );
				
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);

		String temp = poProp.getProperty(pre+P_GARBAGENUTRITIONTYPE);
		mnGarbageNutritionType = eNutritions.valueOf(temp);
		mrWeight = poProp.getPropertyDouble(pre+P_WEIGHT);
		moSmartExcrementProps = poProp.getSubset( pre+P_SMARTEXCREMENTS );
	}
	
	public clsSmartExcrement getSmartExcrements(double prIntensity) {
		double rExcrementWeight = mrWeight * prIntensity;
				
		try {
			double rFraction = moStomachSystem.withdrawNutrition(mnGarbageNutritionType, mrWeight * prIntensity);
			rExcrementWeight *= rFraction;
			
		} catch (exNoSuchNutritionType e) {
			rExcrementWeight = 0;
			
		}
		
		clsSmartExcrement oSh__t = new clsSmartExcrement("", moSmartExcrementProps, rExcrementWeight);
		
		return oSh__t;
	}
}
