
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;



public class clsInternalActionGeneralSweat extends clsInternalActionCommand {
	
	private double mrIntensity = -1;
	private String moAffectingEmotion = null;
	
	private eInternalActionIntensity meInternalActionIntensity;

	
	public clsInternalActionGeneralSweat(double prIntensity) {

		this.setIntensity(prIntensity);
	
	}
	
	public clsInternalActionGeneralSweat(double prIntensity, String poEmotionType) {
		
        this.setIntensity(prIntensity);
        
		moAffectingEmotion = poEmotionType;// eEmotionType.ANXIETY.toString(); // e.g.
		
	}

	// getter & setter for double intensity

	public double getIntensity() {
		return mrIntensity;
	}

	public void setIntensity(double prInternalActionIntensity) {
		this.mrIntensity = prInternalActionIntensity;
		
        /* 0...10  -> not enough to take effect
         * 10..40  -> light intensity category
         * 40..70  -> medium intensity category
         * 70..100 -> heavy intensity category
         */
        if( (prInternalActionIntensity >= 0.1) && (prInternalActionIntensity < 0.4) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prInternalActionIntensity >= 0.4) && (prInternalActionIntensity < 0.7) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prInternalActionIntensity >= 0.7) && (prInternalActionIntensity <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}


	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	private void setInternalActionIntensity(eInternalActionIntensity peIntensity) {
		this.meInternalActionIntensity = peIntensity;
		
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrIntensity < 0) ){
            this.mrIntensity = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrIntensity < 0) ){
        	this.mrIntensity = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrIntensity < 0) ){
        	this.mrIntensity = 0.85;
        }
	}

	/**
	 * 
	 * @return the moAffectingEmotion
	 */
	public String getAffectingEmotionName() {
		return moAffectingEmotion;
	}
	
	
	
}
