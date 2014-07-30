/**
 * clsFacialExpressions.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 */
package complexbody.intraBodySystems;

import complexbody.io.sensors.datatypes.enums.eAntennaPositions;
import complexbody.io.sensors.datatypes.enums.eEyeSize;
import complexbody.io.sensors.datatypes.enums.eLensShape;
import complexbody.io.sensors.datatypes.enums.eLensSize;

import properties.clsProperties;
import body.attributes.clsAttributeAntenna;
import body.attributes.clsAttributeEye;
import entities.abstractEntities.clsEntity;

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
	
	public clsFacialExpression(String poPrefix, clsProperties poProp, clsEntity poEntity) {

		
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
