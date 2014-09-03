
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganHeart extends clsBodyOrgan {
	// basics
	private int mnMaxHeartRate;
	private int mnNormalHeartRate;
	private int mnCurrentHeartRate;
	
	private double mrIntensity; // between 0.0 (min) and 1.0 (max), min can be changed later for lower heart rates.
	private int mnNumberOfAffectingEmotionsForHeartIntensity;
	
	private final double mrDefaultIntensity = 0.0; // no tension
	private final int mnDefaultNormalHeartRate = 80; // set the default normal (resting) heart rate
	private final int mnDefaultMaxHeartRate = 220; // set the default max heart rate
	
	public clsBodyOrganHeart() {
		// set Emotion factors
		setAngerFactor(1.0);
		setAnxietyFactor(0.75);
		
		// reset the emotion counters
		this.setNumberOfAffectingEmotionsForHeartIntensity( 0 );
		
		// setting starting values
		this.mrIntensity = mrDefaultIntensity;
		this.mnNormalHeartRate = mnDefaultNormalHeartRate;
		this.mnMaxHeartRate = mnDefaultMaxHeartRate;
		updateHeartRate();
	}
	
	public double getIntensity() {
		if(mrIntensity < 0.0){
			setIntensity( 0.0 );
		}
		else if( mrIntensity >= 1.0){
			setIntensity( 1.0 );
		}
		
		return mrIntensity;
	}
	
	private void setIntensity(double prIntensity) {
		this.mrIntensity = prIntensity;
	}
	
	private void addIntensity(double prIntensity) {
		this.mrIntensity += prIntensity;
	}
	
	public void affectHeart(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames)	{
		if( getNumberOfAffectingEmotionsForHeartIntensity() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForHeartIntensity( poStorageOfEmotionIntensities.size() );
		}
		else{
			// its not 0. it means the value from previous step counts like an emotion with factor 1.0
			this.setNumberOfAffectingEmotionsForHeartIntensity( poStorageOfEmotionIntensities.size() + 1 );
		}
		
		System.out.println("Heart affecting emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANGER" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAngerFactor());

				addIntensity( poStorageOfEmotionIntensities.get(a) * this.getAngerFactor() );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANXIETY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAnxietyFactor());

				addIntensity( poStorageOfEmotionIntensities.get(a) * this.getAnxietyFactor() );
			} // no heart rate affection caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		if(this.getNumberOfAffectingEmotionsForHeartIntensity() != 0){
			this.setIntensity( this.mrIntensity / this.getNumberOfAffectingEmotionsForHeartIntensity() );
		}
		else{
			this.setIntensity( this.mrIntensity ); // to avoid division by 0
		}
		
		updateHeartRate();
	} // end affectHeart

	public void updateHeartRate(){
		if(!(this.mrIntensity < 0.0)){
			this.setHeartRate( mnNormalHeartRate + (int)(this.getIntensity() * (mnMaxHeartRate - mnNormalHeartRate)) );
		}
	}
	
	public int getNumberOfAffectingEmotionsForHeartIntensity() {
		return this.mnNumberOfAffectingEmotionsForHeartIntensity;
	}
	private void setNumberOfAffectingEmotionsForHeartIntensity(int pnIntensity) {
		this.mnNumberOfAffectingEmotionsForHeartIntensity = pnIntensity;
	}

	/**
	 * 
	 * @return the mnMaxHeartRate
	 */
	public int getMaxHeartRate() {
		return mnMaxHeartRate;
	}

	/**
	 * 
	 * @return the mnNormalHeartRate
	 */
	public int getNormalHeartRate() {
		return mnNormalHeartRate;
	}

	public int getHeartRate() {
		updateHeartRate();
		return mnCurrentHeartRate;
	}
	private void setHeartRate(int pnCurrentHeartRate) {
		this.mnCurrentHeartRate = pnCurrentHeartRate;
	}
}
