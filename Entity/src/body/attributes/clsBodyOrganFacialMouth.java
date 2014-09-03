
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganFacialMouth extends clsBodyOrgan{
	
	private double mrMouthOpen; // 1 fully opened, 0 completely closed
	private int mnNumberOfAffectingEmotionsForMouthOpen;
	private double mrMouthSidesUpOrDown; // -1 downwards. 0 line shaped, 1 upwards
	private int mnNumberOfAffectingEmotionsForMouthSides;
	private double mrMouthStretchiness; // lip ends' state of stretchiness. 0 normal position, 1 max distance from center
	private int mnNumberOfAffectingEmotionsForMouthStretchiness;
	
	private final double mrDefaultMouthOpen = 0; // closed
	private final double mrDefaultMouthSidesUpOrDown = 0.05; // not line shaped [ __ ] but a little curved [ .__, ] if possible.
	private final double mrDefaultMouthStretchiness = 0; // 0 normal position, 1 max distance from center
	
	public clsBodyOrganFacialMouth() {
		// set Emotion factors
		this.setElationFactor( 0.6 );
		
		// reset the emotion counters
		this.setNumberOfAffectingEmotionsForMouthSides( 0 );
		this.setNumberOfAffectingEmotionsForMouthOpen( 0 );
		this.setNumberOfAffectingEmotionsForMouthStretchiness( 0 );
		
		// setting starting values
		this.mrMouthOpen = mrDefaultMouthOpen;
		this.mrMouthSidesUpOrDown = mrDefaultMouthSidesUpOrDown;
		this.mrMouthStretchiness = mrDefaultMouthStretchiness;
	} 
	
	public void affectMouth( ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames )
	{
		// check to see if we need to count the previous emotion or not.
		if( getNumberOfAffectingEmotionsForMouthSides() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForMouthSides( 0 );
		}
		else{
			// its not 0. it means the value from previous step needs to be counted too
			this.setNumberOfAffectingEmotionsForMouthSides( 1 );
		}
		if( getNumberOfAffectingEmotionsForMouthOpen() == 0 ){
			this.setNumberOfAffectingEmotionsForMouthOpen( 0 );
		}
		else{
			this.setNumberOfAffectingEmotionsForMouthOpen( 1 );
		}
		if( getNumberOfAffectingEmotionsForMouthStretchiness() == 0 ){
			this.setNumberOfAffectingEmotionsForMouthStretchiness( 0 );
		}
		else{
			this.setNumberOfAffectingEmotionsForMouthStretchiness( 1 );
		}
		
		System.out.println("Mouth affecting emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANGER" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAngerFactor());

				this.changeMouthOpen( (-1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAngerFactor()) ) ); // minus (-) direction means closing the mouth
				// no effect on stretchiness
				// no effect on the curves of the lip corners
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANXIETY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAnxietyFactor());

				this.changeMouthOpen( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor()) ) ); // plus (+) direction means opening the mouth
				this.changeMouthStretchiness( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor()) ) ); // plus (+) direction means stretching the mouth back to ears
				this.changeMouthSides( (-1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor()) ) ); // minus (-) direction curves the lip corners downwards
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "MOURNING" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getMourningFactor());

				// no effect on the mouth opening
				// no effect on stretchiness
				this.changeMouthSides( (-1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getMourningFactor()) ) ); // minus (-) direction curves the lip corners downwards
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "JOY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getJoyFactor());

				// no effect on the mouth opening
				// no effect on stretchiness
				this.changeMouthSides( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getJoyFactor()) ) ); // plus (+) direction curves the lip corners upwards
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ELATION" ) ){ // lightly smile
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getElationFactor());

				// no effect on the mouth opening
				// no effect on stretchiness
				this.changeMouthSides( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getElationFactor()) ) ); // plus (+) direction curves the lip corners upwards
			} // no heart rate affection caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		// set mrMouthOpen
		if(this.getNumberOfAffectingEmotionsForMouthOpen() != 0){
			this.setMouthOpen( this.mrMouthOpen / this.getNumberOfAffectingEmotionsForMouthOpen() );
		}
		else{
			this.setMouthOpen( this.mrMouthOpen ); // to avoid division by 0
		}
		// set mrMouthSidesUpOrDown
		if(this.getNumberOfAffectingEmotionsForMouthSides() != 0){
			this.setMouthSides( this.mrMouthSidesUpOrDown / this.getNumberOfAffectingEmotionsForMouthSides() );
		}
		else{
			this.setMouthSides( this.mrMouthSidesUpOrDown ); // to avoid division by 0
		}
		// set mrMouthStretchiness
		if(this.getNumberOfAffectingEmotionsForMouthStretchiness() != 0){
			this.setMouthStretchiness( this.mrMouthStretchiness / this.getNumberOfAffectingEmotionsForMouthStretchiness() );
		}
		else{
			this.setMouthStretchiness( this.mrMouthStretchiness ); // to avoid division by 0
		}
	} // end affectMouth
	
	public double getMouthOpen() {
		if(mrMouthOpen < 0.0){
			setMouthOpen( 0.0 );
		}
		else if( mrMouthOpen >= 1.0){
			setMouthOpen( 1.0 );
		}
		
		return mrMouthOpen;
	}
	
	private void changeMouthOpen(double prMouthOpen) {
		this.mrMouthOpen += prMouthOpen;
		this.setNumberOfAffectingEmotionsForMouthOpen( this.getNumberOfAffectingEmotionsForMouthOpen() + 1 );
	}
	
	private void setMouthOpen(double prMouthOpen) {
		this.mrMouthOpen = prMouthOpen;
	}
	
	public double getMouthSides() {
		if(mrMouthSidesUpOrDown < -1.0){
			setMouthSides( -1.0 );
		}
		else if( mrMouthSidesUpOrDown >= 1.0){
			setMouthSides( 1.0 );
		}
		
		return mrMouthSidesUpOrDown;
	}
	
	private void changeMouthSides(double prMouthSides){
		this.mrMouthSidesUpOrDown += prMouthSides;
		this.setNumberOfAffectingEmotionsForMouthSides( this.getNumberOfAffectingEmotionsForMouthSides() + 1 );
	}
	
	private void setMouthSides(double prMouthSides) {
		this.mrMouthSidesUpOrDown = prMouthSides;
	}
	
	public double getMouthStretchiness() {
		if(mrMouthStretchiness < 0.0){
			setMouthStretchiness( 0.0 );
		}
		else if( mrMouthStretchiness >= 1.0){
			setMouthStretchiness( 1.0 );
		}
		
		return mrMouthStretchiness;
	}
	
	private void changeMouthStretchiness(double prMouthStretchiness) {
		this.mrMouthStretchiness += prMouthStretchiness;
		this.setNumberOfAffectingEmotionsForMouthStretchiness( this.getNumberOfAffectingEmotionsForMouthStretchiness() + 1 );
	}

	private void setMouthStretchiness(double prMouthStretchiness) {
		this.mrMouthStretchiness = prMouthStretchiness;
	}
	
	public int getNumberOfAffectingEmotionsForMouthOpen() {
		return mnNumberOfAffectingEmotionsForMouthOpen;
	}
	private void setNumberOfAffectingEmotionsForMouthOpen(int pnNumberOfAffectingEmotionsForMouthOpen) {
		this.mnNumberOfAffectingEmotionsForMouthOpen = pnNumberOfAffectingEmotionsForMouthOpen;
	}


	public int getNumberOfAffectingEmotionsForMouthSides() {
		return mnNumberOfAffectingEmotionsForMouthSides;
	}
	private void setNumberOfAffectingEmotionsForMouthSides(int pnNumberOfAffectingEmotionsForMouthSides) {
		this.mnNumberOfAffectingEmotionsForMouthSides = pnNumberOfAffectingEmotionsForMouthSides;
	}
	
	public int getNumberOfAffectingEmotionsForMouthStretchiness() {
		return mnNumberOfAffectingEmotionsForMouthStretchiness;
	}
	private void setNumberOfAffectingEmotionsForMouthStretchiness(int pnNumberOfAffectingEmotionsForMouthStretchiness) {
		this.mnNumberOfAffectingEmotionsForMouthStretchiness = pnNumberOfAffectingEmotionsForMouthStretchiness;
	}
}





