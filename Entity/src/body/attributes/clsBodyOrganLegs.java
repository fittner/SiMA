
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganLegs extends clsBodyOrgan{

	// basics
	private double mrUpLimit;
	private double mrDownLimit;
	private double mrNormalState;
	private double mrCurrentState;
	
	private double mrTensionIntensity;
	/* from wikipedia: Muscle tone
	 * is the continuous and passive partial contraction of the muscles, or the muscle's resistance to 
	 * passive stretch during resting state.
	 * */
	
	private int mnNumberOfAffectingEmotionsForTensionIntensity;
	
	private final double mrDefaultTensionIntensity = 0.0; // no tension
	
	public clsBodyOrganLegs() {
		// set Emotion factors
		setAngerFactor(0.75);
		setAnxietyFactor(1.0);
		
		// reset the emotion counters
		this.setNumberOfAffectingEmotionsForTensionIntensity( 0 );
		
		// setting starting values for basics
		mrUpLimit = 5.0;
		mrDownLimit = -2.0;
		mrNormalState = 0.0;
		mrCurrentState = mrNormalState;
		
		// setting starting values
		mrTensionIntensity = mrDefaultTensionIntensity;
	}
	
	public void affectMuscleTension(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames)	{
		if( getNumberOfAffectingEmotionsForTensionIntensity() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForTensionIntensity( poStorageOfEmotionIntensities.size() );
		}
		else{
			// its not 0. it means the value from previous step counts like an emotion with factor 1.0
			this.setNumberOfAffectingEmotionsForTensionIntensity( poStorageOfEmotionIntensities.size() + 1 );
		}
		
		System.out.println("Muscle tension affecting emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANGER" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAngerFactor());

				addTensionIntensity( poStorageOfEmotionIntensities.get(a) * ( this.getAngerFactor() ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANXIETY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAnxietyFactor());

				addTensionIntensity( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor() ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ELATION" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getElationFactor());

				addTensionIntensity( poStorageOfEmotionIntensities.get(a) * (-1) * (this.getElationFactor()) );
			} // no heart rate affection caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addTensionIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		if(this.getNumberOfAffectingEmotionsForTensionIntensity() != 0){
			this.setTensionIntensity( this.mrTensionIntensity / this.getNumberOfAffectingEmotionsForTensionIntensity() );
		}
		else{
			this.setTensionIntensity( this.mrTensionIntensity ); // to avoid division by 0
		}
	} // end affectMuscleTension
	
	public void effectLegState( double poChangeLegState ){
		mrCurrentState += poChangeLegState;
	}
	
	public double getTensionIntensity() {
		if(mrTensionIntensity < 0.0){
			setTensionIntensity( 0.0 );
		}
		else if( mrTensionIntensity >= 1.0){
			setTensionIntensity( 1.0 );
		}
		
		return mrTensionIntensity;
	}
	private void addTensionIntensity(double pIntensity) {
		this.mrTensionIntensity += pIntensity;
	}
	
	private void setTensionIntensity(double pIntensity){
		this.mrTensionIntensity = pIntensity;
	}
	
	public int getNumberOfAffectingEmotionsForTensionIntensity() {
		return this.mnNumberOfAffectingEmotionsForTensionIntensity;
	}
	private void setNumberOfAffectingEmotionsForTensionIntensity(int pnIntensity) {
		this.mnNumberOfAffectingEmotionsForTensionIntensity = pnIntensity;
	}

} // end class


