
package complexbody.io.actuators.actionCommands;

import complexbody.io.sensors.datatypes.enums.eInternalActionIntensity;

public class clsInternalActionRaiseEyeBrowsCorners extends clsInternalActionCommand{

	private double mrTenseMuscles = -1;

	private eInternalActionIntensity meInternalActionIntensity;

	public clsInternalActionRaiseEyeBrowsCorners( double prIntensity ) {
		this.mrTenseMuscles = prIntensity;
	}

	// getter & setter's
	public double getTenseMuscles() {
		return mrTenseMuscles;
	}

	public eInternalActionIntensity getInternalActionIntensity() {
		return meInternalActionIntensity;
	}
	public double getEyeBrowsCornersRaise(){
		return 0.0;
	}

	public void setEmotionalStressSweat(double prTenseMuscles) {

        if( (prTenseMuscles >= 0) && (prTenseMuscles < 1.0) ){
        	this.mrTenseMuscles = prTenseMuscles;
        }
        else if (prTenseMuscles >= 1.0){
        	// error: internal action intensity cant be greater than 1.0. The value must be between [0.0, 1.0]
        	this.mrTenseMuscles = 1.0;
        }
        else
        {
        	// error: internal action intensity cant be negative. value must be between [0.0, 1.0]
        	this.mrTenseMuscles = 0.0;
        }

        if( (prTenseMuscles >= 0) && (prTenseMuscles < 0.33) ){
            this.setInternalActionIntensity(eInternalActionIntensity.LIGHT);
        }
        else if( (prTenseMuscles >= 0.33) && (prTenseMuscles < 0.66) ){
            this.setInternalActionIntensity(eInternalActionIntensity.MEDIUM);
        }
        else if( (prTenseMuscles >= 0.66) && (prTenseMuscles <= 1.0) ){
            this.setInternalActionIntensity(eInternalActionIntensity.HEAVY);
        }
	}

	private void setInternalActionIntensity(eInternalActionIntensity meInternalActionIntensity) {

		this.meInternalActionIntensity = meInternalActionIntensity;

		// in case if its ever needed to set meInternalActionIntensity without having set meIntensity first
        if( (meInternalActionIntensity == eInternalActionIntensity.LIGHT) && (mrTenseMuscles < 0) ){
            this.mrTenseMuscles = 0.25;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.MEDIUM) && (mrTenseMuscles < 0) ){
        	this.mrTenseMuscles = 0.55;
        }
        else if( (meInternalActionIntensity == eInternalActionIntensity.HEAVY) && (mrTenseMuscles < 0) ){
        	this.mrTenseMuscles = 0.85;
        }
	}
}
