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
 * Sep 11, 2014, 2:25:13 AM
 *
 */
public class clsInternalActionAffectMouthSidesUpOrDown extends clsInternalActionCommand{

	private double mrMouthSidesUpOrDown = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionAffectMouthSidesUpOrDown( double prIntensity ) {
		this.mrMouthSidesUpOrDown = prIntensity;
	}

	// getter & setter's
	public double getMouthSidesUpOrDown() {
		return mrMouthSidesUpOrDown;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setMouthSidesUpOrDown(double prMouthSidesUpOrDown) {

        if( (prMouthSidesUpOrDown >= 0) && (prMouthSidesUpOrDown < 1.0) ){
        	this.mrMouthSidesUpOrDown = prMouthSidesUpOrDown;
        }
        else if (prMouthSidesUpOrDown >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrMouthSidesUpOrDown = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrMouthSidesUpOrDown = 0.0;
        }

        if( (prMouthSidesUpOrDown >= 0) && (prMouthSidesUpOrDown < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prMouthSidesUpOrDown >= 0.33) && (prMouthSidesUpOrDown < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prMouthSidesUpOrDown >= 0.66) && (prMouthSidesUpOrDown <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrMouthSidesUpOrDown < 0) ){
            this.mrMouthSidesUpOrDown = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrMouthSidesUpOrDown < 0) ){
        	this.mrMouthSidesUpOrDown = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrMouthSidesUpOrDown < 0) ){
        	this.mrMouthSidesUpOrDown = 0.85;
        }
	}
}
