/**
 * clsAttributeShape.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 15:05:09
 */
package body.attributes;

import properties.clsProperties;
import sim.physics2D.shape.Shape;
import entities.abstractEntities.clsEntity;
import entities.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 15:05:09
 * 
 */
public class clsAttributeShape extends clsBaseAttribute {
	protected clsEntity moEntity;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 08.09.2009, 15:05:13
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	public clsAttributeShape(String poPrefix, clsProperties poProp, clsEntity poEntity) {
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
	 * 08.09.2009, 15:05:09
	 * 
	 * @see bw.body.attributes.clsBaseAttribute#setBodyAttribute()
	 */
	@Override
	protected void setBodyAttribute() {
		mnBodyAttribute = eBodyAttributes.SHAPE;

	}
	
	public Shape getShape() {
		return moEntity.get2DShape();
	}
	
	public void setShape(Shape poShape) {
		moEntity.set2DShape(poShape, moEntity.getTotalWeight());
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": "+getShape();
		
		return result;
	}		
}
