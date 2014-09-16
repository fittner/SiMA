
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganLegs extends clsBodyOrgan{
	
	public static final String P_DEFAULTTENSIONINTENSITY = "defaulttensionintensity";

	// basics
	private double mrUpLimit;
	private double mrDownLimit;
	private double mrNormalState;
	private double mrCurrentState;
	
	private double mrTensionIntensity; // 1 max tension, 0 completely relaxed
	/* from wikipedia: Muscle tone
	 * is the continuous and passive partial contraction of the muscles, or the muscle's resistance to 
	 * passive stretch during resting state.
	 * */
	
	private final double mrMaxTensionIntensity = 1.0;
	private final double mrMinTensionIntensity = 0.0;
	
	public clsBodyOrganLegs(String poPrefix, clsProperties poProp) {
		// setting starting values for basics
		mrUpLimit = 5.0;
		mrDownLimit = -2.0;
		mrNormalState = 0.0;
		mrCurrentState = mrNormalState;
		
		// setting starting values
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTTENSIONINTENSITY, "0.0"); // no stress sweat
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);

	    mrTensionIntensity = poProp.getPropertyDouble(pre+P_DEFAULTTENSIONINTENSITY);

		
	}
	
	public void affectMuscleTension( double prIncomingIntensity ){
		this.mrTensionIntensity += prIncomingIntensity;
	}
	
	public void effectLegState( double poChangeLegState ){
		mrCurrentState += poChangeLegState;
	}
	
	public double getTensionIntensity() {
		if(mrTensionIntensity < mrMinTensionIntensity){
			setTensionIntensity( mrMinTensionIntensity );
		}
		else if( mrTensionIntensity >= mrMaxTensionIntensity){
			setTensionIntensity( mrMaxTensionIntensity );
		}
		
		return mrTensionIntensity;
	}
	
	private void setTensionIntensity(double pIntensity){
		this.mrTensionIntensity = pIntensity;
	}

} // end clsBodyOrganLegs


