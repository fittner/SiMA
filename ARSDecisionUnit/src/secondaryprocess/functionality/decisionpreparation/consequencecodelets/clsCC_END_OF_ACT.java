/**
 * CHANGELOG
 *
 * 27.09.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import base.datatypes.clsAssociation;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eOrgan;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsPair;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eDrive;
import memorymgmt.enums.eDriveComponent;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsDataStructureTools;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import secondaryprocess.functionality.decisionpreparation.clsConditionGroup;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2012, 14:51:02
 * 
 */
public class clsCC_END_OF_ACT extends clsConsequenceCodelet {

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.09.2012 14:51:16
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 * @param poCodeletHandler
	 */

	public clsCC_END_OF_ACT(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#processGoal()
	 */
	@Override
	protected void processGoal() {
	    
	    clsWordPresentationMesh dataStructure = moGoal.getSupportiveDataStructure();
        clsWordPresentationMesh intention = clsActDataStructureTools.getIntention(dataStructure);
        clsWordPresentationMesh goalAction = clsActionTools.getActionFromInention(intention);
        clsWordPresentationMesh goalObject = clsActDataStructureTools.getGoalObject(moGoal);
        
        ArrayList<clsDriveMesh> oDrives = new ArrayList<clsDriveMesh>();
        
        clsThingPresentationMesh oTPM = clsDataStructureTools.getTPMfromWPM(goalObject);
        for (clsAssociation oAssoc :oTPM.getExternalAssociatedContent()){
            if(oAssoc.getAssociationElementA() instanceof clsDriveMesh ){
               clsThingPresentationMesh oDriveAim =  ((clsDriveMesh) oAssoc.getAssociationElementA()).getActualDriveAim();
               if(oDriveAim.getContent().equals(goalAction.getContent()))oDrives.add((clsDriveMesh) oAssoc.getAssociationElementA());
            }
        }
        
        DT1_PsychicIntensityBuffer buffer = moCodeletHandler.getLibidoBuffer();
        
        for(clsDriveMesh oDriveMesh: oDrives){
            eDrive oDrive;
            if (!oDriveMesh.getPartialDrive().equals(ePartialDrive.UNDEFINED)) {
                // if DM is sexual drive
                oDrive = eDrive.valueOf(oDriveMesh.getPartialDrive().toString());
            } else {
                // if DM is self preservation drive
                if (oDriveMesh.getActualDriveSourceAsENUM()==eOrgan.UNDEFINED)
                    continue;
                oDrive = eDrive.valueOf(oDriveMesh.getActualDriveSourceAsENUM().toString());
            }
                    if (oDriveMesh.getDriveComponent().equals(eDriveComponent.AGGRESSIV)) {
                        buffer.receive_D1_3(oDrive,
                                new clsPair<Double, Double>(oDriveMesh.getPsychicSatisfactionValue(), 0.0));
                    } else {
                        buffer.receive_D1_3(oDrive,
                                new clsPair<Double, Double>(0.0, oDriveMesh.getPsychicSatisfactionValue()));
                    }
        }
        
        
        
  	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPreconditions()
	 */
	@Override
	protected void setPreconditions() {
		this.moPreconditionGroupList.add(new clsConditionGroup(eCondition.ACT_FINISHED));
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.09.2012 14:51:18
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setPostConditions()
	 */
	@Override
	protected void setPostConditions() {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 01.10.2012 15:36:29
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#removeTriggerCondition()
	 */
	@Override
	protected void removeTriggerCondition() {
	    try {
            this.moGoal.removeCondition(eCondition.ACT_FINISHED);
            
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.12.2012 12:12:17
	 * 
	 * @see pa._v38.decisionpreparation.clsCodelet#setDescription()
	 */
	@Override
	protected void setDescription() {
		this.moCodeletDescription = "Executes the consequence of the end of the act.";
		
	}

}
