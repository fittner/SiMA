/**
 * clsFacialExpressions.java: BW - bw.body.intraBodySystems
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 */
package complexbody.intraBodySystems;

import complexbody.expressionVariables.clsExpressionVariable;
import complexbody.expressionVariables.clsExpressionVariableFacialEyeBrows;
import complexbody.expressionVariables.clsExpressionVariableFacialEyes;
import complexbody.expressionVariables.clsExpressionVariableFacialMouth;
import complexbody.io.sensors.datatypes.enums.eAntennaPositions;
import complexbody.io.sensors.datatypes.enums.eEyeSize;
import complexbody.io.sensors.datatypes.enums.eLensShape;
import complexbody.io.sensors.datatypes.enums.eLensSize;

import properties.clsProperties;
import entities.abstractEntities.clsEntity;
import body.clsComplexBody;
import body.itfStepUpdateInternalState;
import body.attributes.clsAttributeAntenna;
import body.attributes.clsAttributeEye;
import body.attributes.clsBodyOrganFacialEyeBrows;
import body.attributes.clsBodyOrganFacialEyes;
import body.attributes.clsBodyOrganFacialMouth;
import body.itfget.itfGetBody;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 13:05:16
 * 
 */
public class clsFacialExpression implements itfStepUpdateInternalState{
	
	public static final String P_FACIALMOUTH = "facialmouth";
	public static final String P_FACIALEYEBROWS = "facialeyebrows";
	public static final String P_FACIALEYES = "facialeyes";

	private clsAttributeEye moEye;
	private clsAttributeAntenna moAntennaLeft;
	private clsAttributeAntenna moAntennaRight;
	
    private clsBodyOrganFacialEyes moBOFacialEyes;
    private clsBodyOrganFacialEyeBrows moBOFacialEyeBrows;
    private clsBodyOrganFacialMouth moBOFacialMouth;
    
    private clsEntity moEntity;
	
	public clsFacialExpression(String poPrefix, clsProperties poProp, clsEntity poEntity) {
		

	    moEntity = poEntity;
		
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();

		oProp.putAll( clsBodyOrganFacialEyes.getDefaultProperties(pre+P_FACIALEYES) );
		oProp.putAll( clsBodyOrganFacialEyeBrows.getDefaultProperties(pre+P_FACIALEYEBROWS) );
		oProp.putAll( clsBodyOrganFacialMouth.getDefaultProperties(pre+P_FACIALMOUTH) );

		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		moBOFacialEyes = new clsBodyOrganFacialEyes(pre+P_FACIALEYES, poProp);
	    moBOFacialEyeBrows = new clsBodyOrganFacialEyeBrows(pre+P_FACIALEYEBROWS, poProp);
	    moBOFacialMouth = new clsBodyOrganFacialMouth(pre+P_FACIALMOUTH, poProp);

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

	public clsBodyOrganFacialEyes getBOFacialEyes() {
		return moBOFacialEyes;
	}
	public void setBOFacialEyes(clsBodyOrganFacialEyes poBOFacialEyes) {
		this.moBOFacialEyes = poBOFacialEyes;
	}

	public clsBodyOrganFacialEyeBrows getBOFacialEyeBrows() {
		return moBOFacialEyeBrows;
	}
	public void setBOFacialForehead(clsBodyOrganFacialEyeBrows moBOFacialForehead) {
		this.moBOFacialEyeBrows = moBOFacialForehead;
	}

	public clsBodyOrganFacialMouth getBOFacialMouth() {
		return moBOFacialMouth;
	}
	public void setBOFacialMouth(clsBodyOrganFacialMouth moBOFacialMouth) {
		this.moBOFacialMouth = moBOFacialMouth;
	}

	@Override
	public void stepUpdateInternalState() {
		// drawing for the expressions will be on ComplexBody.stepUpdateInternalState()
		
		clsComplexBody oCB = (clsComplexBody) ((itfGetBody)moEntity).getBody();
		
		// Set the ExpressionsVariable: Mouth
		int positionOfEVMouth = -1;
		for(clsExpressionVariable ev : oCB.getInternalSystem().getBOrganSystem().getExpressionsList()){
			if( ev instanceof clsExpressionVariableFacialMouth ){
				positionOfEVMouth = oCB.getInternalSystem().getBOrganSystem().getExpressionsList().indexOf(ev);
			}
		}
		
		if(positionOfEVMouth != -1){
			// EV found! no need to create a new one
			((clsExpressionVariableFacialMouth)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVMouth)).setMouthOpen( this.getBOFacialMouth().getMouthOpen() );
			((clsExpressionVariableFacialMouth)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVMouth)).setMouthSidesUpOrDown( this.getBOFacialMouth().getMouthSides() );
			((clsExpressionVariableFacialMouth)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVMouth)).setMouthStretchiness( this.getBOFacialMouth().getMouthStretchiness() );
		}
		else{
			// EV not found! create a new one and add it to the list
			clsExpressionVariableFacialMouth tempMouth = new clsExpressionVariableFacialMouth();
			tempMouth.setMouthOpen( this.getBOFacialMouth().getMouthOpen() );
			tempMouth.setMouthSidesUpOrDown( this.getBOFacialMouth().getMouthSides() );
			tempMouth.setMouthStretchiness( this.getBOFacialMouth().getMouthStretchiness() );
			oCB.getInternalSystem().getBOrganSystem().getExpressionsList().add( tempMouth );
		}

		
		// Set the ExpressionsVariable: EyeBrows
		int positionOfEVEyeBrows = -1;
		for(clsExpressionVariable ev : oCB.getInternalSystem().getBOrganSystem().getExpressionsList()){
			if( ev instanceof clsExpressionVariableFacialEyeBrows ){
				positionOfEVEyeBrows = oCB.getInternalSystem().getBOrganSystem().getExpressionsList().indexOf(ev);
			}
		}
		
		if(positionOfEVEyeBrows != -1){
			// EV found! no need to create a new one
			((clsExpressionVariableFacialEyeBrows)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVEyeBrows)).setEyeBrowsCornersUpOrDown( this.getBOFacialEyeBrows().getEyeBrowsCornersUpOrDown() );
			((clsExpressionVariableFacialEyeBrows)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVEyeBrows)).setEyeBrowsCenterUpOrDown( this.getBOFacialEyeBrows().getEyeBrowsCenterUpOrDown() );
		}
		else{
			// EV not found! create a new one and add it to the list
			clsExpressionVariableFacialEyeBrows tempEyeBrows = new clsExpressionVariableFacialEyeBrows();
			tempEyeBrows.setEyeBrowsCornersUpOrDown( this.getBOFacialEyeBrows().getEyeBrowsCornersUpOrDown() );
			tempEyeBrows.setEyeBrowsCenterUpOrDown( this.getBOFacialEyeBrows().getEyeBrowsCenterUpOrDown() );
			oCB.getInternalSystem().getBOrganSystem().getExpressionsList().add( tempEyeBrows );
		}

		// Set the ExpressionsVariable: Eyes
		if ( this.getBOFacialEyes().getCryingIntensity() > 0.01){
			int positionOfEVEyes = -1;
			for(clsExpressionVariable ev : oCB.getInternalSystem().getBOrganSystem().getExpressionsList()){
				if( ev instanceof clsExpressionVariableFacialEyes ){
					positionOfEVEyes = oCB.getInternalSystem().getBOrganSystem().getExpressionsList().indexOf(ev);
				}
			}
			
			if(positionOfEVEyes != -1){
				// EV found! no need to create a new one
				((clsExpressionVariableFacialEyes)oCB.getInternalSystem().getBOrganSystem().getExpressionsList().get(positionOfEVEyes)).setCrying( this.getBOFacialEyes().getCryingIntensity() );
			}
			else{
				// EV not found! create a new one and add it to the list
				clsExpressionVariableFacialEyes tempEyes = new clsExpressionVariableFacialEyes();
				tempEyes.setCrying( this.getBOFacialEyes().getCryingIntensity() );
				oCB.getInternalSystem().getBOrganSystem().getExpressionsList().add( tempEyes );
			}
		}
	}
}
