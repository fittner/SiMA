
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;
public class clsInternalActionEmotionalStressSweat extends clsInternalActionCommand{

	private double mrEmotionalStressSweat = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionEmotionalStressSweat( double prIntensity ) {
		this.mrEmotionalStressSweat = prIntensity;
	}

	// getter & setter's
	public double getEmotionalStressSweat() {
		return mrEmotionalStressSweat;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setEmotionalStressSweat(double prEmotionalStressSweat) {

        if( (prEmotionalStressSweat >= 0) && (prEmotionalStressSweat < 1.0) ){
        	this.mrEmotionalStressSweat = prEmotionalStressSweat;
        }
        else if (prEmotionalStressSweat >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrEmotionalStressSweat = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrEmotionalStressSweat = 0.0;
        }

        if( (prEmotionalStressSweat >= 0) && (prEmotionalStressSweat < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prEmotionalStressSweat >= 0.33) && (prEmotionalStressSweat < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prEmotionalStressSweat >= 0.66) && (prEmotionalStressSweat <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrEmotionalStressSweat < 0) ){
            this.mrEmotionalStressSweat = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrEmotionalStressSweat < 0) ){
        	this.mrEmotionalStressSweat = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrEmotionalStressSweat < 0) ){
        	this.mrEmotionalStressSweat = 0.85;
        }
	}
}
