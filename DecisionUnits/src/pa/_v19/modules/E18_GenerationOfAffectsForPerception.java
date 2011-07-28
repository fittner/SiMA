/**
 * E18_GenerationOfAffectsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 */
package pa._v19.modules;

import java.util.ArrayList;

import config.clsProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_8_receive;
import pa._v19.interfaces.receive.I2_9_receive;
import pa._v19.interfaces.send.I2_9_send;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v19.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
@Deprecated
public class E18_GenerationOfAffectsForPerception extends clsModuleBase implements I2_8_receive, I2_9_send {

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:34:15
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Input;
	public ArrayList<clsPrimaryDataStructureContainer> moNewPrimaryInformation; 
	
	public E18_GenerationOfAffectsForPerception(String poPrefix,
			clsProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
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
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:34:27
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		moMergedPrimaryInformation_Input = (ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>>)deepCopy(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		adaptPleasureValue(); 
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 18.08.2010, 12:44:36
	 *
	 */
	private void adaptPleasureValue() {
		moNewPrimaryInformation = new ArrayList<clsPrimaryDataStructureContainer>(); 
		
		for(clsPair <clsPrimaryDataStructureContainer, clsDriveMesh> oPair : moMergedPrimaryInformation_Input){
			if(oPair.a.getMoDataStructure() instanceof clsThingPresentationMesh){
				for(pa._v19.memorymgmt.datatypes.clsAssociation oAssociation : oPair.a.getMoAssociatedDataStructures()){
					if(oAssociation instanceof clsAssociationDriveMesh){
						clsDriveMesh oDMInput = ((clsAssociationDriveMesh)oAssociation).getDM(); 
						clsDriveMesh oDMRepressed = oPair.b; 
						
						if(oDMInput.getMoContent().intern() == oDMRepressed.getMoContent().intern()){
							oDMInput.setPleasure((oDMInput.getPleasure()+oDMRepressed.getPleasure())/2); 
							oDMInput.setMoContent(oDMRepressed.getMoContent()); 
						}
					}
				}
			}
			
			moNewPrimaryInformation.add(oPair.a); 
		}
	}

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_9(moNewPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:38:33
	 * 
	 * @see pa.interfaces.send.I2_9_send#send_I2_9(java.util.ArrayList)
	 */
	@Override
	public void send_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		((I2_9_receive)moEnclosingContainer).receive_I2_9(moNewPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:50
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
	 * 12.07.2010, 10:46:50
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}
}
