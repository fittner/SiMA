
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganFacialEyes extends clsBodyOrgan{
	
	public static final String P_DEFAULTCRYINGINTENSITYTRESHOLH = "defaultcryingintensitytreshold";
	public static final String P_DEFAULTMOURNINGINTENSITY = "defaultmourningintensity";
	public static final String P_DEFAULTCRYINGINTENSITY = "defaultcryingintensity";
	
	//private double mrEyeSize;
	//private double mrEyePupilSize;
	private double mrCryingIntensity; // 0 no cry, 1.0 max crying
	private double mrMourningIntensity;
	private double mrCryingIntensityTreshhold;
	
	private double mrMinMourningIntensity = 0.0;
	private double mrMaxMourningIntensity = 1.0;
	
	public clsBodyOrganFacialEyes(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTCRYINGINTENSITYTRESHOLH, "0.6");
		oProp.setProperty(pre+P_DEFAULTMOURNINGINTENSITY, "0.0");
		oProp.setProperty(pre+P_DEFAULTCRYINGINTENSITY, "0.0");
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
	    
	    this.mrCryingIntensityTreshhold = poProp.getPropertyDouble(pre+P_DEFAULTCRYINGINTENSITYTRESHOLH);
		this.mrMourningIntensity = poProp.getPropertyDouble(pre+P_DEFAULTMOURNINGINTENSITY);
		this.mrCryingIntensity = poProp.getPropertyDouble(pre+P_DEFAULTCRYINGINTENSITY);
		
	}
	
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
		if(mrMourningIntensity < mrMinMourningIntensity){
			setMourningIntensity( mrMinMourningIntensity );
		}
		else if( mrMourningIntensity >= mrMaxMourningIntensity){
			setMourningIntensity( mrMaxMourningIntensity );
		}
		
		return mrMourningIntensity;
	}

	public void addMourningIntensity(double prMourningIntensity) {
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
}
