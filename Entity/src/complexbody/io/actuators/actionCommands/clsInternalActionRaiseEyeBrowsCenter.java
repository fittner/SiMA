/**
 * CHANGELOG
 *
 * Sep 10, 2014 volkan - File created
 *
 */
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;

/**
 * DOCUMENT (volkan) - insert description
 *
 * @author volkan
 * Sep 10, 2014, 6:11:32 AM
 *
 */
public class clsInternalActionRaiseEyeBrowsCenter extends clsInternalActionCommand{

	private double mrEyeBrowsCenterRaise = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionRaiseEyeBrowsCenter( double prIntensity ) {
		this.mrEyeBrowsCenterRaise = prIntensity;
	}

	// getter & setter's
	public double getEyeBrowsCenterRaise() {
		return mrEyeBrowsCenterRaise;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}

	public void setEyeBrowsCenterRaise(double prEyeBrowsCenterRaise) {

        if( (prEyeBrowsCenterRaise >= 0) && (prEyeBrowsCenterRaise < 1.0) ){
        	this.mrEyeBrowsCenterRaise = prEyeBrowsCenterRaise;
        }
        else if (prEyeBrowsCenterRaise >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrEyeBrowsCenterRaise = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrEyeBrowsCenterRaise = 0.0;
        }

        if( (prEyeBrowsCenterRaise >= 0) && (prEyeBrowsCenterRaise < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prEyeBrowsCenterRaise >= 0.33) && (prEyeBrowsCenterRaise < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prEyeBrowsCenterRaise >= 0.66) && (prEyeBrowsCenterRaise <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrEyeBrowsCenterRaise < 0) ){
            this.mrEyeBrowsCenterRaise = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrEyeBrowsCenterRaise < 0) ){
        	this.mrEyeBrowsCenterRaise = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrEyeBrowsCenterRaise < 0) ){
        	this.mrEyeBrowsCenterRaise = 0.85;
        }
	}
}
