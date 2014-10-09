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
 * Sep 11, 2014, 3:59:53 AM
 *
 */
public class clsInternalActionAffectMouthOpen extends clsInternalActionCommand{

	private double mrMouthOpen = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionAffectMouthOpen( double prIntensity ) {
		this.mrMouthOpen = prIntensity;
	}

	// getter & setter's
	public double getMouthOpen() {
		return mrMouthOpen;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setMouthOpen(double prMouthOpen) {

        if( (prMouthOpen >= 0) && (prMouthOpen < 1.0) ){
        	this.mrMouthOpen = prMouthOpen;
        }
        else if (prMouthOpen >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrMouthOpen = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrMouthOpen = 0.0;
        }

        if( (prMouthOpen >= 0) && (prMouthOpen < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prMouthOpen >= 0.33) && (prMouthOpen < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prMouthOpen >= 0.66) && (prMouthOpen <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrMouthOpen < 0) ){
            this.mrMouthOpen = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrMouthOpen < 0) ){
        	this.mrMouthOpen = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrMouthOpen < 0) ){
        	this.mrMouthOpen = 0.85;
        }
	}
}
