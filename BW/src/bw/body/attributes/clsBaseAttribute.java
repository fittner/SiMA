/**
 * clsBaseAttribute.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:44:33
 */
package bw.body.attributes;

import config.clsBWProperties;
import bw.utils.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 14:44:33
 * 
 */
public abstract class  clsBaseAttribute {
	protected eBodyAttributes mnBodyAttribute;
	
    public clsBaseAttribute(String poPrefix, clsBWProperties poProp) {
    	setBodyAttribute();
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
//		String pre = clsBWProperties.addDot(poPrefix);

	}	    

	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		return oProp;
	}	
	
	
	protected abstract void setBodyAttribute();
	
}
