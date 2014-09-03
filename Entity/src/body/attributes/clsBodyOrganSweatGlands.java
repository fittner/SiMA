
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganSweatGlands extends clsBodyOrgan{
	private double mrSweatIntensity; // between 0.0 (min) and 1.0 (max)
	private double mrStressSweatIntensity; // between 0.0 (min) and 1.0 (max)
	/* from wikipedia: Apocrine sweat gland
	 * Being sensitive to adrenaline, apocrine sweat glands are involved in emotional sweating in humans 
	 * (induced by anxiety, stress, fear, sexual stimulation, and pain)
	 * */
	
	private int mnNumberOfAffectingEmotionsForStressSweatIntensity;
	
	private final double mrDefaultSweatIntensity = 0.0; // no sweat
	private final double mrDefaultStressSweatIntensity = 0.0; // no stress sweat
	
	public clsBodyOrganSweatGlands() {
		// set Emotion factors
		setAngerFactor(0.75);
		setAnxietyFactor(1.0);
		
		// reset the emotion counters
		this.setNumberOfAffectingEmotionsForStressSweatIntensity( 0 );
		
		// setting starting values
		mrSweatIntensity = mrDefaultSweatIntensity; // starting value for sweat intensity
		mrStressSweatIntensity = mrDefaultStressSweatIntensity; // starting value for emotional stress sweat intensity
	}
	
	public void affectStressSweat(ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames)	{
		if( getNumberOfAffectingEmotionsForStressSweatIntensity() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForStressSweatIntensity( poStorageOfEmotionIntensities.size() );
		}
		else{
			// its not 0. it means the value from previous step counts like an emotion with factor 1.0
			this.setNumberOfAffectingEmotionsForStressSweatIntensity( poStorageOfEmotionIntensities.size() + 1 );
		}
		
		System.out.println("Emotional Stress sweat causing emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANGER" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAngerFactor());

				addStressSweatIntensity( poStorageOfEmotionIntensities.get(a) * ( this.getAngerFactor() ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANXIETY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAnxietyFactor());

				addStressSweatIntensity( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor() ) );
			} // no emotional stress sweat caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addStressSweatIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		if(this.getNumberOfAffectingEmotionsForStressSweatIntensity() != 0){
			this.setStressSweatIntensity( this.mrStressSweatIntensity / this.getNumberOfAffectingEmotionsForStressSweatIntensity() );
		}
		else{
			this.setStressSweatIntensity( this.mrStressSweatIntensity ); // to avoid division by 0
		}
	} // end affectStressSweat

	public double getSweatIntensity() {
		return mrSweatIntensity;
	}
	
	public void setSweatIntensity(double prSweatIntensity) {
		this.mrSweatIntensity = prSweatIntensity;
	}

	private void setStressSweatIntensity(double prIntensity) {
		this.mrStressSweatIntensity = prIntensity;
	}

	public void affectStressSweat( double prIntensity, String poEmotionType )
	{
		registerStepNo();
		
		if(prIntensity > 1.0){
			prIntensity = 1.0;
		}
		if(prIntensity < 0.0){
			prIntensity = 0.0;
		}
		
		if ( poEmotionType.equalsIgnoreCase( "ANGER" ) ){
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getAngerFactor() + ", incoming intensity: " + prIntensity);

			addStressSweatIntensity( prIntensity * ( this.getAngerFactor()) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "ANXIETY" ) ){
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getAnxietyFactor() + ", incoming intensity: " + prIntensity);

			addStressSweatIntensity( prIntensity * ( this.getAnxietyFactor()) );
		}/* no emotional stress sweating caused by the emotions below..
		else if ( poEmotionType.equalsIgnoreCase( "MOURNING" ) ){
			addStressSweatIntensity( poIntensity * (this.getMourningFactor()) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "JOY" ) ){
			addStressSweatIntensity( poIntensity * (this.getJoyFactor()) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "SATURATION" ) ){
			addStressSweatIntensity( poIntensity * (this.getSaturationFactor()) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "ELATION" ) ){
			addStressSweatIntensity( poIntensity * (this.getElationFactor()) );
		}*/
		else
		{ // for other emotions..
			addStressSweatIntensity( prIntensity );
		}
	}
	
	public double getStressSweatIntensity() {
		if(mrStressSweatIntensity < 0.0){
			setStressSweatIntensity( 0.0 );
		}
		else if( mrStressSweatIntensity >= 1.0){
			setStressSweatIntensity( 1.0 );
		}
		
		return mrStressSweatIntensity;
	}
	
	private void addStressSweatIntensity( double prIntensity) {
		this.mrStressSweatIntensity += prIntensity;
	}
	
	public int getNumberOfAffectingEmotionsForStressSweatIntensity() {
		return this.mnNumberOfAffectingEmotionsForStressSweatIntensity;
	}
	private void setNumberOfAffectingEmotionsForStressSweatIntensity(int pnIntensity) {
		this.mnNumberOfAffectingEmotionsForStressSweatIntensity = pnIntensity;
	}

}
