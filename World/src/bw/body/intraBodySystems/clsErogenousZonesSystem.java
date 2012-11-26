/**
 * clsStomachDamageTension.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 05.10.2009, 11:37:42
 */
package bw.body.intraBodySystems;

import config.clsProperties;
import bw.body.itfStepUpdateInternalState;
import bw.body.internalSystems.clsFastMessengerSystem;
import bw.entities.clsEntity;
import bw.utils.enums.eBodyParts;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 05.11.2012, 11:37:42
 * 
 */
public class clsErogenousZonesSystem implements itfStepUpdateInternalState  {
	
	private static double mrPainThresholdOralMucosa = 1;
	private static double mrPainFactorOralMucosa = 0.5;	
	

	private clsFastMessengerSystem moFastMessengerSystem;
	

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 *
	 * @since 26.11.2012 13:26:20
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEntity
	 * @param fastMessengerSystem
	 */
	public clsErogenousZonesSystem(String poPrefix, clsProperties poProp,
			clsEntity poEntity, clsFastMessengerSystem poFastMessengerSystem) {
		moFastMessengerSystem = poFastMessengerSystem;
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	}			

	public void StimulateOralMucosa() {
		
	}
	

	public void StimulateOralMucosa(double prStimulusIntensity) {
		//generate a stimulation message for the orifice
		if (prStimulusIntensity < mrPainThresholdOralMucosa) {
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTER_ORIFICE_ORAL_MUCOSA, eBodyParts.BRAIN, prStimulusIntensity);
			}
		}
		else{
			//TODO if intensity is higher then threshhold -> generate pain
		}
	}


	@Override
	public void stepUpdateInternalState() {
    	
		//TODO alle zonen durchgehen und zonen reizen
		
		
		//double rPenaltySum = moStomachSystem.getWeight() / moStomachSystem.getMaxWeight();
    	
    	//pain(rPenaltySum);
	}
}
