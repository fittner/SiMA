/**
 * clsAttributeHeight.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 17:03:55
 */
package bw.body.attributes;

import bw.utils.enums.eBodyAttributes;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 17:03:55
 * 
 */
public class clsAttributeHeight extends clsBaseAttribute {
	public static final String P_HEIGHT = "height";
	
	protected double mrHeight;
	
    public clsAttributeHeight(String poPrefix, clsProperties poProp) {
    	super(poPrefix, poProp);
    	
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mrHeight = poProp.getPropertyDouble(pre+P_HEIGHT);
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_HEIGHT, 1.0 );
		
		return oProp;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 14:58:13
	 * 
	 * @see bw.body.attributes.clsBaseAttribute#setBodyAttribute()
	 */
	@Override
	protected void setBodyAttribute() {
		mnBodyAttribute = eBodyAttributes.HEIGHT;
	}	
		
	public double getHeight() {
		return mrHeight;
	}
	
	public void setHeight(double prHeight) {
		mrHeight = prHeight;
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+mrHeight;
		
		return result;
	}
}