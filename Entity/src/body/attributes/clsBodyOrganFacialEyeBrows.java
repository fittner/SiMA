
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganFacialEyeBrows extends clsBodyOrgan{
	
	public static final String P_DEFAULTEYEBROWSCORNERS = "defaulteyebrowscorners";
	public static final String P_DEFAULTEYEBROWSCENTER = "defaulteyebrowscenter";
	
	private double mrEyeBrowsCornersUpOrDown; // 0 down, 1 up
	private double mrEyeBrowsCenterUpOrDown; // 0 down, 1 up
	
	private final double mrMaxEyeBrowsCornersUpOrDown = 1.0;
	private final double mrMinEyeBrowsCornersUpOrDown = 0.0;
	private final double mrMaxEyeBrowsCenterUpOrDown = 1.0;
	private final double mrMinEyeBrowsCenterUpOrDown = 0.0;

	
	public clsBodyOrganFacialEyeBrows(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTEYEBROWSCORNERS, "0.2");
		oProp.setProperty(pre+P_DEFAULTEYEBROWSCENTER, "0.2");
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
	    
	    this.mrEyeBrowsCornersUpOrDown = poProp.getPropertyDouble(pre+P_DEFAULTEYEBROWSCORNERS);
		this.mrEyeBrowsCenterUpOrDown = poProp.getPropertyDouble(pre+P_DEFAULTEYEBROWSCENTER);
		
	}

	public double getEyeBrowsCenterUpOrDown() {
		if(mrEyeBrowsCenterUpOrDown < mrMinEyeBrowsCenterUpOrDown){
			setEyeBrowsCenterUpOrDown( mrMinEyeBrowsCenterUpOrDown );
		}
		else if( mrEyeBrowsCenterUpOrDown > mrMaxEyeBrowsCenterUpOrDown){
			setEyeBrowsCenterUpOrDown( mrMaxEyeBrowsCenterUpOrDown );
		}
		
		return mrEyeBrowsCenterUpOrDown;
	}
	
	public void changeEyeBrowsCenter(double prEyeBrowsCenter) {
		this.mrEyeBrowsCenterUpOrDown += prEyeBrowsCenter;
	}

	private void setEyeBrowsCenterUpOrDown(double prEyeBrowsCenterUpOrDown) {
		this.mrEyeBrowsCenterUpOrDown = prEyeBrowsCenterUpOrDown;
	}

	public double getEyeBrowsCornersUpOrDown() {
		if(mrEyeBrowsCornersUpOrDown < mrMinEyeBrowsCornersUpOrDown){
			setEyeBrowsCornersUpOrDown( mrMinEyeBrowsCornersUpOrDown );
		}
		else if( mrEyeBrowsCornersUpOrDown > mrMaxEyeBrowsCornersUpOrDown){
			setEyeBrowsCornersUpOrDown( mrMaxEyeBrowsCornersUpOrDown );
		}
		
		return mrEyeBrowsCornersUpOrDown;
	}
	
	public void changeEyeBrowsCorners(double prEyeBrowsCorners) {
		this.mrEyeBrowsCornersUpOrDown += prEyeBrowsCorners;
	}

	private void setEyeBrowsCornersUpOrDown(double prEyeBrowsCornersUpOrDown) {
		this.mrEyeBrowsCornersUpOrDown = prEyeBrowsCornersUpOrDown;
	}
}
