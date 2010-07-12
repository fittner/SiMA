/**
 * E17_FusionOfExternalPerceptionAndMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.receive.I2_7_receive;
import pa.interfaces.receive.I2_8_receive;
import pa.interfaces.send.I2_8_send;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 * 
 */
public class E17_FusionOfExternalPerceptionAndMemoryTraces extends clsModuleBase implements 
						I2_7_receive, I2_8_send
						{

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:33:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> moPerceptPlusAwareContent_Input;
	ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moMergedPrimaryInformation_Output; 
		
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
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusAwareContent_Input) {
		moPerceptPlusAwareContent_Input = (ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>>)deepCopy(poPerceptPlusAwareContent_Input); 
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
		moMergedPrimaryInformation_Output = new ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>(); 
		for(clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>> oElement : moPerceptPlusAwareContent_Input){
			defineOutput(oElement); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.10.2009, 18:23:08
	 *
	 * @param element
	 */
	private void defineOutput(
			clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>> poElement) {
		if(poElement.b != null){
			clsPrimaryInformationMesh oMergedMesh = (clsPrimaryInformationMesh)poElement.a; 
			mergeMesh(oMergedMesh, poElement); 
			moMergedPrimaryInformation_Output.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(oMergedMesh, poElement.b)); 
		}
		else{
			moMergedPrimaryInformation_Output.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(poElement.a, poElement.b)); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.10.2009, 14:32:20
	 * @param poMergedMesh 
	 *
	 * @param element
	 * @return
	 */
	private void mergeMesh(clsPrimaryInformationMesh poMergedMesh, clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>> poElement) {
		getAwareContent(poMergedMesh, poElement.c); 
	}

	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.10.2009, 09:33:07
	 *
	 * @param mergedMesh
	 * @param c
	 */
	private void getAwareContent(clsPrimaryInformationMesh poMergedMesh,
			ArrayList<clsPrimaryInformation> poAwareContentList) {
		
		for(clsPrimaryInformation oAwareContent : poAwareContentList){
			poMergedMesh.moTP.meContentName = poMergedMesh.moTP.meContentName; // + "_" + oAwareContent.moTP.meContentName;
			poMergedMesh.moTP.moContent = poMergedMesh.moTP.moContent +"_" + oAwareContent.moTP.moContent; 
			clsAssociationContent<clsPrimaryInformation> oAssociationContent = new clsAssociationContent<clsPrimaryInformation>(); 
			 
			
			oAssociationContent.moElementA = poMergedMesh; 
			oAssociationContent.moElementB = oAwareContent; 
			oAssociationContent.moWeight = 1.0; 
		
			poMergedMesh.moAssociations.add(oAssociationContent);
		}
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
	public void send_I2_8(
			ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation) {
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
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();	
	}

}
