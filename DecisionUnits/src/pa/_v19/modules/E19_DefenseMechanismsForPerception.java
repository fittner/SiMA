/**
 * E19_DefenseMechanismsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 */
package pa._v19.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_10_receive;
import pa._v19.interfaces.receive.I2_9_receive;
import pa._v19.interfaces.receive.I3_2_receive;
import pa._v19.interfaces.receive.I4_2_receive;
import pa._v19.interfaces.receive.I5_2_receive;
import pa._v19.interfaces.send.I2_10_send;
import pa._v19.interfaces.send.I4_2_send;
import pa._v19.interfaces.send.I5_2_send;
import pa._v19.memorymgmt.datatypes.clsAssociation;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 * 
 */
@Deprecated
public class E19_DefenseMechanismsForPerception extends clsModuleBase implements I2_9_receive, I3_2_receive, I4_2_send, I2_10_send, I5_2_send {

	public ArrayList<clsPrimaryDataStructureContainer> moSubjectivePerception_Input; 
	public ArrayList<clsPrimaryDataStructureContainer> moFilteredPerception_Output; 
	private ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation> moDeniedThingPresentations;
	private ArrayList<clsAssociationDriveMesh> moDeniedAffects; 
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:36:00
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E19_DefenseMechanismsForPerception(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moFilteredPerception_Output = new ArrayList<clsPrimaryDataStructureContainer>(); 
		//HZ 20.08.2010 All objects that do not have a drive evaluation attached are filtered in a first step =>
		//				This makes sense as it is a problem to evaluate objects by the defense mechanisms that do
		//			    not have drives attached (even this is essential for an evaluation)
		//	 			The question that has to be discussed is if this filtering takes place in E18 or here.
		filterInput(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 12:01:38
	 *
	 * @return
	 */
	private void filterInput() {
		
		for(clsPrimaryDataStructureContainer oContainer : moSubjectivePerception_Input){
			for(clsAssociation oAssociation : oContainer.getMoAssociatedDataStructures()){
				
				//HZ: if statement checks i their exists at least one association to a drive mesh
				if(oAssociation instanceof clsAssociationDriveMesh){
					moFilteredPerception_Output.add(oContainer);
					break; 
				}
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:28:09
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 24.10.2009, 20:01:39
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {

		moSubjectivePerception_Input = (ArrayList<clsPrimaryDataStructureContainer> )this.deepCopy(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v19.memorymgmt.datatypes
		send_I4_2(moFilteredPerception_Output, moDeniedThingPresentations, moDeniedAffects);
		send_I2_10(moFilteredPerception_Output);
		send_I5_2(moDeniedAffects);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I4_2_send#send_I4_2(java.util.ArrayList, java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void send_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		((I4_2_receive)moEnclosingContainer).receive_I4_2(moFilteredPerception_Output, moDeniedThingPresentations, moDeniedAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I2_10_send#send_I2_10(java.util.ArrayList)
	 */
	@Override
	public void send_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		((I2_10_receive)moEnclosingContainer).receive_I2_10(moFilteredPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I5_2_send#send_I5_2(java.util.ArrayList)
	 */
	@Override
	public void send_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		((I5_2_receive)moEnclosingContainer).receive_I5_2(moDeniedAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}

}
