
package complexbody.io.actuators.actionCommands;

import java.util.ArrayList;
//import pa._v38.memorymgmt.datatypes.clsEmotion; // <-- Not visible from this class! o.0

public class clsInternalActionFasterHeartPump extends clsInternalActionCommand {
	
	//private double mrIntensity = -1;
	//private String moAffectingEmotion = null;
	
	//private eInternalActionIntensity meInternalActionIntensity;
	
	private ArrayList<Double> moStorageOfEmotionIntensities = null;
	private ArrayList<String> moStorageOfEmotionNames = null;
	
	public clsInternalActionFasterHeartPump(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames) {
		this.moStorageOfEmotionIntensities = poStorageOfEmotionIntensities;
		this.moStorageOfEmotionNames = poStorageOfEmotionNames;
	}

	public ArrayList<Double> getStorageOfEmotionIntensities() {
		return moStorageOfEmotionIntensities;
	}
	public void setStorageOfEmotionIntensities(ArrayList<Double> poStorageOfEmotionIntensities) {
		this.moStorageOfEmotionIntensities = poStorageOfEmotionIntensities;
	}
	
	public ArrayList<String> getStorageOfEmotionNames() {
		return moStorageOfEmotionNames;
	}
	public void setStorageOfEmotionNames(ArrayList<String> poStorageOfEmotionNames) {
		this.moStorageOfEmotionNames = poStorageOfEmotionNames;
	}
	
	/*
	public clsInternalActionFasterHeartPump(double prIntensity) {

		this.setIntensity(prIntensity);
	
	}
	
	public clsInternalActionFasterHeartPump(double prIntensity, String poEmotionType) {
		
        this.setIntensity(prIntensity);
        
		moAffectingEmotion = poEmotionType;// eEmotionType.ANXIETY.toString(); // e.g.
		
	}
	*/

	// getter & setters
	
	/*
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
        
		// remove these 2 lines later. for testing purposes
		System.out.println(	this.getClass().toString() + ".mrIntensity = " + mrIntensity);

		
     // 0...10  -> not enough to take effect
     // 10..40  -> light intensity category
     // 40..70  -> medium intensity category
     // 70..100 -> heavy intensity category

        if( (intensity >= 0.01) && (intensity < 0.33) ){
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
	
	public String getAffectingEmotionName(){
		return moAffectingEmotion;
	}
	*/
	
}