
package body.attributes;

import properties.clsProperties;


public class clsBodyOrganFacialMouth extends clsBodyOrgan{
	
	public static final String P_DEFAULTMOUTHOPEN = "defaultmouthopen";
	public static final String P_DEFAULTMOUTHSIDES = "defaultmouthsides";
	public static final String P_DEFAULTMOUTHSTRETCHINESS = "defaultmouthstretchiness";

	
	private double mrMouthOpen; // 1 fully opened, 0 completely closed
	private double mrMouthSidesUpOrDown; // -1 downwards. 0 line shaped, 1 upwards
	private double mrMouthStretchiness; // lip ends' state of stretchiness. 0 normal position, 1 max distance from center
	
	private final double mrMaxMouthOpen = 1.0;
	private final double mrMinMouthOpen = 0.0;
	private final double mrMaxMouthSidesUpOrDown = 1.0;
	private final double mrMinMouthSidesUpOrDown = -1.0;
	private final double mrMaxMouthStretchiness = 1.0;
	private final double mrMinMouthStretchiness = 0.0;
	
	public clsBodyOrganFacialMouth(String poPrefix, clsProperties poProp) {
		
		applyProperties(poPrefix, poProp);
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_DEFAULTMOUTHOPEN, "0.0");
		oProp.setProperty(pre+P_DEFAULTMOUTHSIDES, "0.05");
		oProp.setProperty(pre+P_DEFAULTMOUTHSTRETCHINESS, "0.0");
		
		return oProp;

	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
	    String pre = clsProperties.addDot(poPrefix);
	    
	    this.mrMouthOpen = poProp.getPropertyDouble(pre+P_DEFAULTMOUTHOPEN);
		this.mrMouthSidesUpOrDown = poProp.getPropertyDouble(pre+P_DEFAULTMOUTHSIDES);
		this.mrMouthStretchiness = poProp.getPropertyDouble(pre+P_DEFAULTMOUTHSTRETCHINESS);
		
	}

	public double getMouthOpen() {
		if(mrMouthOpen < mrMinMouthOpen){
			setMouthOpen( mrMinMouthOpen );
		}
		else if( mrMouthOpen >= mrMaxMouthOpen){
			setMouthOpen( mrMaxMouthOpen );
		}
		
		return mrMouthOpen;
	}

	public void changeMouthOpen(double prMouthOpen) {
		this.mrMouthOpen += prMouthOpen;
	}

	private void setMouthOpen(double prMouthOpen) {
		this.mrMouthOpen = prMouthOpen;
	}

	public double getMouthSides() {
		if(mrMouthSidesUpOrDown < mrMinMouthSidesUpOrDown){
			setMouthSides( mrMinMouthSidesUpOrDown );
		}
		else if( mrMouthSidesUpOrDown >= mrMaxMouthSidesUpOrDown){
			setMouthSides( mrMaxMouthSidesUpOrDown );
		}
		
		return mrMouthSidesUpOrDown;
	}

	public void changeMouthSides(double prMouthSides){
		this.mrMouthSidesUpOrDown += prMouthSides;
	}

	private void setMouthSides(double prMouthSides) {
		this.mrMouthSidesUpOrDown = prMouthSides;
	}

	public double getMouthStretchiness() {
		if(mrMouthStretchiness < mrMinMouthStretchiness){
			setMouthStretchiness( mrMinMouthStretchiness );
		}
		else if( mrMouthStretchiness >= mrMaxMouthStretchiness){
			setMouthStretchiness( mrMaxMouthStretchiness );
		}
		
		return mrMouthStretchiness;
	}

	public void changeMouthStretchiness(double prMouthStretchiness) {
		this.mrMouthStretchiness += prMouthStretchiness;
	}

	private void setMouthStretchiness(double prMouthStretchiness) {
		this.mrMouthStretchiness = prMouthStretchiness;
	}
}





