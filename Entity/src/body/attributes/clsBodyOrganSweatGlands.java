
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganSweatGlands extends clsBodyOrgan{
	private double mrSweatIntensity; // between 0.0 (min) and 1.0 (max)
	private double mrStressSweatIntensity; // between 0.0 (min) and 1.0 (max)
	/* from wikipedia: Apocrine sweat gland
	 * Being sensitive to adrenaline, apocrine sweat glands are involved in emotional sweating in humans 
	 * (induced by anxiety, stress, fear, sexual stimulation, and pain)
	 * */
	
	public static final String P_DEFAULTSWEATINTENSITY = "defaultsweatintensity";
	public static final String P_DEFAULTSTRESSSWEATINTENSITY = "defaultstresssweatintensity";
	
	private final double mrMinStressSweatIntensity = 0.0;
	private final double mrMaxStressSweatIntensity = 1.0;
	private final double mrMinSweatIntensity = 0.0;
	private final double mrMaxSweatIntensity = 1.0;
	
	public clsBodyOrganSweatGlands(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTSWEATINTENSITY, "0.0"); // no sweat
		oProp.setProperty(pre+P_DEFAULTSTRESSSWEATINTENSITY, "0.0"); // no stress sweat
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
	    
	    mrSweatIntensity = poProp.getPropertyDouble(pre+P_DEFAULTSWEATINTENSITY);
	    mrStressSweatIntensity = poProp.getPropertyDouble(pre+P_DEFAULTSTRESSSWEATINTENSITY);
		
	}

	public double getSweatIntensity() {
		if(mrSweatIntensity < mrMinSweatIntensity){
			setSweatIntensity( mrMinSweatIntensity );
		}
		else if( mrSweatIntensity >= mrMaxSweatIntensity){
			setSweatIntensity( mrMaxSweatIntensity );
		}
		
		return mrSweatIntensity;
	}
	
	public void setSweatIntensity(double prSweatIntensity) {
		this.mrSweatIntensity = prSweatIntensity;
	}

	private void setStressSweatIntensity(double prIntensity) {
		this.mrStressSweatIntensity = prIntensity;
	}
	
	public double getStressSweatIntensity() {
		if(mrStressSweatIntensity < mrMinStressSweatIntensity){
			setStressSweatIntensity( mrMinStressSweatIntensity );
		}
		else if( mrStressSweatIntensity >= mrMaxStressSweatIntensity){
			setStressSweatIntensity( mrMaxStressSweatIntensity );
		}
		
		return mrStressSweatIntensity;
	}
	
	public void affectStressSweat( double prIntensity) {
		this.mrStressSweatIntensity += prIntensity;
	}

}
