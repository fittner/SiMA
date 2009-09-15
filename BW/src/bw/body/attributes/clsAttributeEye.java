/**
 * clsAttributeEye.java: BW - bw.body.attributes
 * 
 * @author deutsch
 * 08.09.2009, 15:24:18
 */
package bw.body.attributes;

import bw.utils.enums.eBodyAttributes;
import config.clsBWProperties;
import enums.eEyeSize;
import enums.eLensShape;
import enums.eLensSize;

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
	
    public clsAttributeEye(String poPrefix, clsBWProperties poProp) {
    	super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
	}
    
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mnLensShape = eLensShape.valueOf( poProp.getPropertyString(pre+P_LENSSHAPE) );
		mnEyeSize = eEyeSize.valueOf( poProp.getPropertyString(pre+P_EYESIZE) );
		mnLensSize = eLensSize.valueOf( poProp.getPropertyString(pre+P_LENSSIZE) );		
	}	    

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
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
