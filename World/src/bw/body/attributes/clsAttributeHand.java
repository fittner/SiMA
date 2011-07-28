/**
 * clsAttributeHand.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 15:52:36
 */
package bw.body.attributes;

import sim.physics2D.shape.Shape;
import bw.body.itfget.itfGetBotHand;
import bw.utils.enums.eBodyAttributes;
import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 15:52:36
 * 
 */
public class clsAttributeHand extends clsBaseAttribute {
	protected itfGetBotHand moHand;
	
    public clsAttributeHand(String poPrefix, clsProperties poProp, itfGetBotHand poHand, eBodyAttributes mnHandLeftRight) {
    	super(poPrefix, poProp);
    	moHand = poHand;
    	
    	if (mnHandLeftRight != eBodyAttributes.HAND_LEFT && mnHandLeftRight != eBodyAttributes.HAND_RIGHT) {
    		throw new java.lang.IllegalArgumentException("Only eBodyAttributes.ANTENNA_LEFT or eBodyAttributes.ANTENNA_RIGHT as third param.");
    	}
    	mnBodyAttribute = mnHandLeftRight;
    	
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
		mnBodyAttribute = eBodyAttributes.UNDEFINED;
	}	
		
	public Shape getShape() {
		if (mnBodyAttribute == eBodyAttributes.HAND_LEFT) {
			return moHand.getBotHandLeft().getShape();
		} else {
			return moHand.getBotHandRight().getShape();
		}
	}
	
	public void setShape(Shape poShape) {
		if (mnBodyAttribute == eBodyAttributes.HAND_LEFT) {
			moHand.getBotHandLeft().setShape(poShape);
		} else {
			moHand.getBotHandRight().setShape(poShape);
		}		
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+getShape();
		
		return result;
	}	
}
