/**
 * E17_FusionOfExternalPerceptionAndMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 */
package pa._v19.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_7_receive;
import pa._v19.interfaces.receive.I2_8_receive;
import pa._v19.interfaces.send.I2_8_send;
import pa._v19.memorymgmt.datatypes.clsAssociation;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsPair;
import pa._v19.tools.clsTripple;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 * 
 */
@Deprecated
public class E17_FusionOfExternalPerceptionAndMemoryTraces extends clsModuleBase implements 
						I2_7_receive, I2_8_send
						{

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:33:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> moPerceptPlusAwareContent_Input; 
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Output; 
	
	public E17_FusionOfExternalPerceptionAndMemoryTraces(String poPrefix,
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
	 * 11.08.2009, 14:33:13
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output) {
		moPerceptPlusAwareContent_Input = (ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>>>)deepCopy(poPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:54
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moMergedPrimaryInformation_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>>();
		addDriveContentToObject();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 11:28:46
	 *
	 * @return
	 */
	private void addDriveContentToObject() {
		for(clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh,ArrayList<clsDriveMesh>> oEntry : moPerceptPlusAwareContent_Input){
				for(clsDriveMesh oDM : oEntry.c){
						clsAssociationDriveMesh oAssociation = new clsAssociationDriveMesh(
									new clsTripple <Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, eDataType.ASSOCIATIONDM.toString()), 
									oDM,
									(clsPrimaryDataStructure)oEntry.a.getMoDataStructure()); 
						
						mergeDM(oEntry, oAssociation); 
			}
			
			moMergedPrimaryInformation_Output.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oEntry.a, oEntry.b)); 
		}
	}


	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.09.2010, 23:55:39
	 *
	 * @param oEntry
	 * @param oDM
	 */
	private void mergeDM(
			clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>> oEntry,
			clsAssociationDriveMesh poAssociation) {
		
			for(clsAssociation oElement : oEntry.a.getMoAssociatedDataStructures()){
				if(oElement.getLeafElement().getMoContentType().intern() == poAssociation.getDM().getMoContentType().intern()){
					//TODO Here some calculations of the new pleasure values have to be done
					return; 
				}
			}
				
		    oEntry.a.getMoAssociatedDataStructures().add(poAssociation); 
	}

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:54
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v19.memorymgmt.datatypes
		send_I2_8(moMergedPrimaryInformation_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:57:08
	 * 
	 * @see pa.interfaces.send.I2_8_send#send_I2_8(java.util.ArrayList)
	 */
	@Override
	public void send_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_8_receive)moEnclosingContainer).receive_I2_8(moMergedPrimaryInformation_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:45
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
	 * 12.07.2010, 10:46:45
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();	
	}

}