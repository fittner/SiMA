/**
 /**
 * clsSpeechExpression.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 */
package bw.body.intraBodySystems;

import bw.body.attributes.clsAttributeAntenna;
import bw.body.attributes.clsAttributeEye;
import bw.entities.base.clsEntity;
import config.clsProperties;
import du.enums.eAntennaPositions;
import du.enums.eEyeSize;
import du.enums.eLensShape;
import du.enums.eLensSize;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 * 
 */
public class clsSpeechExpression {
	private clsAttributeEye moEye;
	private clsAttributeAntenna moAntennaLeft;
	private clsAttributeAntenna moAntennaRight;
	
	public clsSpeechExpression(String poPrefix, clsProperties poProp, clsEntity poEntity) {

		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
//		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
	// nothing to do
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		// nothing to do
	}
	
	public void setEye(eEyeSize peEyeSize, eLensSize peLensSize, eLensShape peLensShape) {
		moEye.setEye(peLensShape, peLensSize, peEyeSize);
	}
	
	public void setEyeSize(eEyeSize peEyeSize) {
		moEye.setEyeSize(peEyeSize);
	}
	
	public void setLensSize(eLensSize peLensSize) {
		moEye.setLensSize(peLensSize);
	}	
	
	public void setLensShape(eLensShape peLensShape) {
		moEye.setLensShape(peLensShape);
	}		
	
	public void setAntennas(eAntennaPositions peLeft, eAntennaPositions peRight) {
		moAntennaLeft.setPosition(peLeft);
		moAntennaRight.setPosition(peRight);
	}
	
	public void setAntennaLeft(eAntennaPositions pePos) {
		moAntennaLeft.setPosition(pePos);
	}
	
	public void setAntennaRight(eAntennaPositions pePos) {
		moAntennaRight.setPosition(pePos);
	}
}
