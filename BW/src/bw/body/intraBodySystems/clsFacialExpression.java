/**
 * clsFacialExpressions.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 */
package bw.body.intraBodySystems;

import bw.body.attributes.clsAttributeAntenna;
import bw.body.attributes.clsAttributeEye;
import bw.entities.clsEntity;
import bw.utils.enums.eAntennaPositions;
import bw.utils.enums.eEyeSize;
import bw.utils.enums.eLensShape;
import bw.utils.enums.eLensSize;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 * 
 */
public class clsFacialExpression {
	private clsAttributeEye moEye;
	private clsAttributeAntenna moAntennaLeft;
	private clsAttributeAntenna moAntennaRight;
	
	public clsFacialExpression(String poPrefix, clsBWProperties poProp, clsEntity poEntity) {

		
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
//		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
	// nothing to do
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
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
