/**
 * clsAttributeColor.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:56:15
 */
package bw.body.attributes;

import java.awt.Color;

import bw.entities.clsEntity;
import bw.utils.enums.eBodyAttributes;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 14:56:15
 * 
 */
public class clsAttributeColor extends clsBaseAttribute {
	protected clsEntity moEntity;
	
    public clsAttributeColor(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {
    	super(poPrefix, poProp);
    	moEntity = poEntity;
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 08.09.2009, 14:58:13
	 * 
	 * @see bw.body.attributes.clsBaseAttribute#setBodyAttribute()
	 */
	@Override
	protected void setBodyAttribute() {
		mnBodyAttribute = eBodyAttributes.COLOR;
	}	
		
	public Color getColor() {
		return (Color) moEntity.getShape().getPaint();
	}
	
	public void setColor(Color poColor) {
		moEntity.getShape().setPaint(poColor);
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+getColor();
		
		return result;
	}	
}
