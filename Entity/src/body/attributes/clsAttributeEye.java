/**
 * clsAttributeEye.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 15:24:18
 */
package body.attributes;

import properties.clsProperties;
import du.enums.eEyeSize;
import du.enums.eLensShape;
import du.enums.eLensSize;
import entities.enums.eBodyAttributes;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 08.09.2009, 15:24:18
 * 
 */
public class clsAttributeEye extends clsBaseAttribute {
	public static final String P_LENSSHAPE = "lensshape";
	public static final String P_EYESIZE = "eyesize";
	public static final String P_LENSSIZE = "lenssize";
	
	protected eLensShape mnLensShape;
	protected eEyeSize mnEyeSize;
	protected eLensSize mnLensSize;
	
    public clsAttributeEye(String poPrefix, clsProperties poProp) {
    	super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		mnLensShape = eLensShape.valueOf( poProp.getPropertyString(pre+P_LENSSHAPE) );
		mnEyeSize = eEyeSize.valueOf( poProp.getPropertyString(pre+P_EYESIZE) );
		mnLensSize = eLensSize.valueOf( poProp.getPropertyString(pre+P_LENSSIZE) );		
	}	    

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_LENSSHAPE, eLensShape.ROUND.name() );
		oProp.setProperty(pre+P_EYESIZE, eEyeSize.MEDIUM.name() );
		oProp.setProperty(pre+P_LENSSIZE, eLensSize.MEDIUM.name() );
		
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
		mnBodyAttribute = eBodyAttributes.EYE;
	}	
		
	public eLensShape getLensShape() {
		return mnLensShape;
	}
	public eEyeSize getEyeSize() {
		return mnEyeSize;
	}
	public eLensSize getLensSize() {
		return mnLensSize;
	}
	
	public void setLensShape(eLensShape pnLensShape) {
		mnLensShape = pnLensShape;
	}
	public void setEyeSize(eEyeSize pnEyeSize) {
		mnEyeSize = pnEyeSize;
	}
	public void setLensSize(eLensSize pnLensSize) {
		mnLensSize = pnLensSize;
	}
	
	public void setEye(eLensShape pnLensShape, eLensSize pnLensSize, eEyeSize pnEyeSize) {
		mnLensShape = pnLensShape;
		mnEyeSize = pnEyeSize;	
		mnLensSize = pnLensSize;		
	}
	
	@Override
	public String toString() {
		String result = "";
		
		result = mnBodyAttribute.name()+": lens (shape:"+mnLensShape.name()+"/size:"+mnLensSize.name()+"); size: "+mnEyeSize.name();
		
		return result;
	}	
}
