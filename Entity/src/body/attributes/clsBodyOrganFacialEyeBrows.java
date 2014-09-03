
package body.attributes;

import java.util.ArrayList;


public class clsBodyOrganFacialEyeBrows extends clsBodyOrgan{
	
	private double mrEyeBrowsCornersUpOrDown; // 0 down, 1 up
	private int mnNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown;
	private double mrEyeBrowsCenterUpOrDown; // 0 down, 1 up
	private int mnNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown;
	
	private final double mrDefaultEyeBrowsCornersUpOrDown = 0.2;
	private final double mrDefaultEyeBrowsCenterUpOrDown = 0.2;

	
	public clsBodyOrganFacialEyeBrows() {
		// set Emotion factors
		this.setElationFactor( 0.5 );
		this.setJoyFactor( 0.7 );
		this.setMourningFactor( 0.3 );
		this.setAnxietyFactor( 0.75 );
		this.setAngerFactor( 0.75 );
		
		// reset the emotion counters.
		this.setNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown( 0 );
		this.setNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown( 0 );

		// setting starting values
		this.mrEyeBrowsCornersUpOrDown = mrDefaultEyeBrowsCornersUpOrDown;
		this.mrEyeBrowsCenterUpOrDown = mrDefaultEyeBrowsCenterUpOrDown;
	}
	
	public void affectEyeBrows( ArrayList<Double> poStorageOfEmotionIntensities, ArrayList<String> poStorageOfEmotionNames )
	{
		// check to see if we need to count the previous emotion or not.
		if( getNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown() == 0 ){
			// if 0, it means we are affecting for the very first time and default starting value should not be counted.
			this.setNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown( 0 );
		}
		else{
			// its not 0. it means the value from previous step needs to be counted too
			this.setNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown( 1 );
		}
		if( getNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown() == 0 ){
			this.setNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown( 0 );
		}
		else{
			this.setNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown( 1 );
		}
		
		System.out.println("Eye Brows affecting emotions: " + poStorageOfEmotionIntensities.size());
		for(int a = 0; a < poStorageOfEmotionIntensities.size(); a++){
			if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANGER" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAngerFactor());

				this.changeEyeBrowsCorners( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAngerFactor() ) ) ); // 0.75 // plus (+) direction means up
				this.changeEyeBrowsCenter( (-1) * ( poStorageOfEmotionIntensities.get(a) * ( 1 ) ) ); // minus (-) direction means down
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ANXIETY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getAnxietyFactor());

				this.changeEyeBrowsCorners( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getAnxietyFactor() ) ) ); // 0.75
				this.changeEyeBrowsCenter( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( 1 ) ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "MOURNING" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getMourningFactor());

				this.changeEyeBrowsCorners( (-1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getMourningFactor() ) ) ); // 0.3
				this.changeEyeBrowsCenter( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( 1 ) ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "JOY" ) ){
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getJoyFactor());

				this.changeEyeBrowsCorners( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getJoyFactor() ) ) ); // 0.7
				this.changeEyeBrowsCenter( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( 1 ) ) );
			}
			else if ( poStorageOfEmotionNames.get(a).equalsIgnoreCase( "ELATION" ) ){ // lightly smile
				System.out.println( poStorageOfEmotionNames.get(a) + ": " + poStorageOfEmotionIntensities.get(a) + ", factor: " + this.getElationFactor());

				// lightly pull up the eye brows
				this.changeEyeBrowsCorners( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getElationFactor()) ) ); // 0.5
				this.changeEyeBrowsCenter( (+1) * ( poStorageOfEmotionIntensities.get(a) * ( this.getElationFactor()) ) ); // 0.5
			} // no heart rate affection caused by other emotions at the moment..
	/*		else
			{ // for other emotions..
				addIntensity( poStorageOfEmotionIntensities.get(a) );
			}	*/
		}
		
		// set mrEyeBrowsCornersUpOrDown
		if(this.getNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown() != 0){
			this.setEyeBrowsCornersUpOrDown( this.mrEyeBrowsCornersUpOrDown / this.getNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown() );
		}
		else{
			this.setEyeBrowsCornersUpOrDown( this.mrEyeBrowsCornersUpOrDown ); // to avoid division by 0
		}
		// set mrEyeBrowsCenterUpOrDown
		if(this.getNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown() != 0){
			this.setEyeBrowsCenterUpOrDown( this.mrEyeBrowsCenterUpOrDown / this.getNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown() );
		}
		else{
			this.setEyeBrowsCenterUpOrDown( this.mrEyeBrowsCenterUpOrDown ); // to avoid division by 0
		}
	} // end affectMouth

	public void affectEyeBrows( double prIntensity, String poEmotionType )
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

			this.changeEyeBrowsCorners( (+1) * ( prIntensity * ( this.getAngerFactor() ) ) ); // 0.75 // plus (+) direction means up
			this.changeEyeBrowsCenter( (-1) * ( prIntensity * ( 1 ) ) ); // minus (-) direction means down
		}
		else if ( poEmotionType.equalsIgnoreCase( "ANXIETY" ) ){ // fear
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getAnxietyFactor() + ", incoming intensity: " + prIntensity);

			this.changeEyeBrowsCorners( (+1) * ( prIntensity * ( this.getAnxietyFactor() ) ) ); // 0.75
			this.changeEyeBrowsCenter( (+1) * ( prIntensity * ( 1 ) ) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "MOURNING" ) ){ // sadness
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getMourningFactor() + ", incoming intensity: " + prIntensity);

			this.changeEyeBrowsCorners( (-1) * ( prIntensity * ( this.getMourningFactor() ) ) ); // 0.3
			this.changeEyeBrowsCenter( (+1) * ( prIntensity * ( 1 ) ) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "JOY" ) ){
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getJoyFactor() + ", incoming intensity: " + prIntensity);

			this.changeEyeBrowsCorners( (+1) * ( prIntensity * ( this.getJoyFactor() ) ) ); // 0.7
			this.changeEyeBrowsCenter( (+1) * ( prIntensity * ( 1 ) ) );
		}
		else if ( poEmotionType.equalsIgnoreCase( "SATURATION" ) ){
			// F.E. for saturation?
		}
		else if ( poEmotionType.equalsIgnoreCase( "ELATION" ) ){
			System.out.println( this.getClass().toString() + " -> incoming emotion name: " + poEmotionType + "factor: " + this.getElationFactor() + ", incoming intensity: " + prIntensity);

			// lightly pull up the eye brows
			this.changeEyeBrowsCorners( (+1) * ( prIntensity * ( this.getElationFactor()) ) ); // 0.5
			this.changeEyeBrowsCenter( (+1) * ( prIntensity * ( this.getElationFactor()) ) ); // 0.5
		}
		else
		{ // for other emotions..
			// nothing at the moment..
		}
	} // end affectEyeBrows

	public double getEyeBrowsCenterUpOrDown() {
		if(mrEyeBrowsCenterUpOrDown < 0.0){
			setEyeBrowsCenterUpOrDown( 0.0 );
		}
		else if( mrEyeBrowsCenterUpOrDown >= 1.0){
			setEyeBrowsCenterUpOrDown( 1.0 );
		}
		
		return mrEyeBrowsCenterUpOrDown;
	}
	
	private void changeEyeBrowsCenter(double prEyeBrowsCenter) {
		this.mrEyeBrowsCenterUpOrDown += prEyeBrowsCenter;
		this.setNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown( this.getNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown() + 1 );
	}

	private void setEyeBrowsCenterUpOrDown(double prEyeBrowsCenterUpOrDown) {
		this.mrEyeBrowsCenterUpOrDown = prEyeBrowsCenterUpOrDown;
	}

	public double getEyeBrowsCornersUpOrDown() {
		if(mrEyeBrowsCornersUpOrDown < 0.0){
			setEyeBrowsCornersUpOrDown( 0.0 );
		}
		else if( mrEyeBrowsCornersUpOrDown >= 1.0){
			setEyeBrowsCornersUpOrDown( 1.0 );
		}
		
		return mrEyeBrowsCornersUpOrDown;
	}
	
	private void changeEyeBrowsCorners(double prEyeBrowsCorners) {
		this.mrEyeBrowsCornersUpOrDown += prEyeBrowsCorners;
		this.setNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown( this.getNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown() + 1 );
	}

	private void setEyeBrowsCornersUpOrDown(double prEyeBrowsCornersUpOrDown) {
		this.mrEyeBrowsCornersUpOrDown = prEyeBrowsCornersUpOrDown;
	}

	public int getNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown() {
		return mnNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown;
	}
	private void setNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown(int pnNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown) {
		this.mnNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown = pnNumberOfAffectingEmotionsForEyeBrowsCornersUpOrDown;
	}

	public int getNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown() {
		return mnNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown;
	}
	private void setNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown(int pnNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown) {
		this.mnNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown = pnNumberOfAffectingEmotionsForEyeBrowsCenterUpOrDown;
	}
}
