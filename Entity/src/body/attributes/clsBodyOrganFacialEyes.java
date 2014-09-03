
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganFacialEyes extends clsBodyOrgan{
	
	//private double mrEyeSize;
	//private double mrEyePupilSize;
	private double mrCryingIntensity; // 0 no cry, 1.0 max crying
	private double mrMourningIntensity;
	private double mrCryingIntensityTreshhold;
	
	private int mnNumberOfAffectingEmotionsForCryingIntensity;
	
	private final double mrDefaultCryingIntensity = 0; // no cry
	private final double mrDefaultMourningIntensity = 0;
	private final double mrDefaultCryingIntensityTreshold = 0.6;
	
	public clsBodyOrganFacialEyes() {
		// set Emotion factors
		this.setMourningFactor( 1.0 );

		// reset the emotion counters
		this.setNumberOfAffectingEmotionsForCryingIntensity( 0 );
		
		// setting starting values
		mrCryingIntensityTreshhold = mrDefaultCryingIntensityTreshold;
		mrMourningIntensity = mrDefaultMourningIntensity;
		mrCryingIntensity = mrDefaultCryingIntensity;
	}

	public void affectEyes(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames)
	{
		System.out.println("Heart affecting emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "MOURNING" ) ){
				// if too much sadness, then cry
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getMourningFactor());

				addMourningIntensity( poStorageOfEmotionIntensities.get(a) * ( this.getMourningFactor()) );
			} // no crying affection caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addMourningIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		if( getNumberOfAffectingEmotionsForCryingIntensity() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForCryingIntensity( poStorageOfEmotionIntensities.size() );
		}
		else{
			// its not 0. it means the value from previous step counts like an emotion with factor 1.0
			this.setNumberOfAffectingEmotionsForCryingIntensity( poStorageOfEmotionIntensities.size() + 1 );
		}
		
		if(this.getNumberOfAffectingEmotionsForCryingIntensity() != 0){
			this.setMourningIntensity( this.mrMourningIntensity / this.getNumberOfAffectingEmotionsForCryingIntensity() );
		}
		else{
			this.setMourningIntensity( this.mrMourningIntensity ); // to avoid division by 0
		}
	} // end affectEyes
	
	/*
	public double getEyeSize() {
		return mrEyeSize;
	}

	public void setEyeSize(double mrEyeSize) {
		this.mrEyeSize = mrEyeSize;
	}


	public double getEyePupilSize() {
		return mrEyePupilSize;
	}

	public void setEyePupilSize(double mrEyePupilSize) {
		this.mrEyePupilSize = mrEyePupilSize;
	}
	 */

	public double getMourningIntensity() {
		if(mrMourningIntensity < 0.0){
			setMourningIntensity( 0.0 );
		}
		else if( mrMourningIntensity >= 1.0){
			setMourningIntensity( 1.0 );
		}
		
		return mrMourningIntensity;
	}

	private void addMourningIntensity(double prMourningIntensity) {
			this.mrMourningIntensity += prMourningIntensity;
	}
	
	private void setMourningIntensity(double prMourningIntensity) {
		this.mrMourningIntensity = prMourningIntensity;
	}
	
	public double getCryingIntensity() {
		if( this.getMourningIntensity() >= this.mrCryingIntensityTreshhold ){
			this.setCryingIntensity( (this.getMourningIntensity() - this.mrCryingIntensityTreshhold) / (1.0 - this.mrCryingIntensityTreshhold ) );
			return this.mrCryingIntensity;
		}
		else{
			return 0.0;
		}
	}
	
	private void setCryingIntensity(double prCryingIntensity) {
		this.mrCryingIntensity = prCryingIntensity;
	}
	
	public double getCryingIntensityTreshhold() {
		return mrCryingIntensityTreshhold;
	}
	public void setCryingIntensityTreshhold(double prCryingIntensityTreshhold) {
		this.mrCryingIntensityTreshhold = prCryingIntensityTreshhold;
	}
	
	public int getNumberOfAffectingEmotionsForCryingIntensity() {
		return this.mnNumberOfAffectingEmotionsForCryingIntensity;
	}
	private void setNumberOfAffectingEmotionsForCryingIntensity(int pnIntensity) {
		this.mnNumberOfAffectingEmotionsForCryingIntensity = pnIntensity;
	}
}
