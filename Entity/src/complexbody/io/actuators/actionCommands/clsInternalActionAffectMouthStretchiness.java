/**
 * CHANGELOG
 *
 * Sep 11, 2014 volkan - File created
 *
 */
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;

/**
 * DOCUMENT (volkan) - insert description
 *
 * @author volkan
 * Sep 11, 2014, 4:01:28 AM
 *
 */
public class clsInternalActionAffectMouthStretchiness extends clsInternalActionCommand{

	private double mrMouthStretchiness = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionAffectMouthStretchiness( double prIntensity ) {
		this.mrMouthStretchiness = prIntensity;
	}

	// getter & setter's
	public double getMouthStretchiness() {
		return mrMouthStretchiness;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setMouthStretchiness(double prMouthStretchiness) {

        if( (prMouthStretchiness >= 0) && (prMouthStretchiness < 1.0) ){
        	this.mrMouthStretchiness = prMouthStretchiness;
        }
        else if (prMouthStretchiness >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrMouthStretchiness = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrMouthStretchiness = 0.0;
        }

        if( (prMouthStretchiness >= 0) && (prMouthStretchiness < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prMouthStretchiness >= 0.33) && (prMouthStretchiness < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prMouthStretchiness >= 0.66) && (prMouthStretchiness <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrMouthStretchiness < 0) ){
            this.mrMouthStretchiness = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrMouthStretchiness < 0) ){
        	this.mrMouthStretchiness = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrMouthStretchiness < 0) ){
        	this.mrMouthStretchiness = 0.85;
        }
	}
}
