/**
 * clsBaseAttribute.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:44:33
 */
package body.attributes;

import properties.clsProperties;
import entities.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 14:44:33
 * 
 */
public abstract class  clsBaseAttribute {
	protected eBodyAttributes mnBodyAttribute;
	
    public clsBaseAttribute(String poPrefix, clsProperties poProp) {
    	setBodyAttribute();
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp) {
//		String pre = clsProperties.addDot(poPrefix);

	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		return oProp;
	}	
	
	
	protected abstract void setBodyAttribute();
	
}
