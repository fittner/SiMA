/**
 * clsAttributeColor.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 14:56:15
 */
package bw.body.attributes;

import java.awt.Color;

import bw.entities.base.clsEntity;
import bw.utils.enums.eBodyAttributes;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 14:56:15
 * 
 */
public class clsAttributeColor extends clsBaseAttribute {
	protected clsEntity moEntity;
	
    public clsAttributeColor(String poPrefix, clsProperties poProp, clsEntity poEntity) {
    	super(poPrefix, poProp);
    	moEntity = poEntity;
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
		return (Color) moEntity.get2DShape().getPaint();
	}
	
	public void setColor(Color poColor) {
		moEntity.get2DShape().setPaint(poColor);
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+getColor();
		
		return result;
	}	
}
