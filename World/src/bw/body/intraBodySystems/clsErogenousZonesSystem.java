/**
 * clsErogenousZonesSystem: BW - bw.body.intraBodySystems
 * 
 * @author muchitsch
 * 05.10.2012, 11:37:42
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
	
	private static double mrPainThresholdRectalMucosa = 1;
	private static double mrPainFactorRectalMucosa = 0.5;	
	

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
		//String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	}			


	public void StimulateOralLibidinousMucosa(double prStimulusIntensity) {
		//generate a stimulation message for the orifice
		if (prStimulusIntensity < mrPainThresholdOralMucosa) {
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTER_ORIFICE_ORAL_LIBIDINOUS_MUCOSA, eBodyParts.BRAIN, prStimulusIntensity);
			}
		}
		else{
			//TODO if intensity is higher then threshhold -> generate pain
			//TODO if stimuli/s is to high -> make numb -> less intensity
		}
	}
	
	public void StimulateOralAggressivMucosa(double prStimulusIntensity) {
		//generate a stimulation message for the orifice
		if (prStimulusIntensity < mrPainThresholdOralMucosa) {
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTER_ORIFICE_ORAL_AGGRESSIV_MUCOSA, eBodyParts.BRAIN, prStimulusIntensity);
			}
		}
		else{
			//TODO if intensity is higher then threshhold -> generate pain
			//TODO if stimuli/s is to high -> make numb -> less intensity
		}
	}
	
	public void StimulateRectalMucosa(double prStimulusIntensity) {
		//generate a stimulation message for the orifice
		if (prStimulusIntensity < mrPainThresholdRectalMucosa) {
			if (moFastMessengerSystem != null) {
				moFastMessengerSystem.addMessage(eBodyParts.INTER_ORIFICE_RECTAL_MUCOSA, eBodyParts.BRAIN, prStimulusIntensity);
			}
		}
		else{
			//TODO if intensity is higher then threshhold -> generate pain
			//TODO if stimuli/s is to high -> make numb -> less intensity
		}
	}


	@Override
	public void stepUpdateInternalState() {
    	
		//TODO alle zonen durchgehen und zonen reizen
		//TODO if stimuli/s is to high -> make numb -> less intensity
		

	}
}
