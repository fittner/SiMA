
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;

public class clsInternalActionAffectHeartRate extends clsInternalActionCommand {
	
	private double mrIntensity = -1;
	
	private eInternalActionIntensity meInternalActionIntensity;
	
	public clsInternalActionAffectHeartRate(double prIntensity) {

		this.setIntensity(prIntensity);
	
	}

	// getter & setters
	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setIntensity(double intensity) {
		
        if( (intensity >= 0) && (intensity < 1.0) ){
        	this.mrIntensity = intensity;
        }
        else if (intensity >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrIntensity = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrIntensity = 0.0;
        }

        if( (intensity >= 0) && (intensity < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (intensity >= 0.33) && (intensity < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (intensity >= 0.66) && (intensity <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}


	public double getIntensity() {
		return mrIntensity;
	}
	
	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;
		
		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
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
	
}