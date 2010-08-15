/**
 * E16_ManagmentOfMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import du.enums.eEntityType;
import du.enums.pa.eRepressedContentType;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I2_7_receive;
import pa.interfaces.send.I2_7_send;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 * 
 */
public class E16_ManagementOfMemoryTraces extends clsModuleBase implements I2_6_receive, I2_7_send, itfKnowledgeBaseAccess {

	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPerceptPlusRepressed_Input_old;
	public ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> moPerceptPlusMemories_Output_old;
	
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> moPerceptPlusRepressed_Input;
	public ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer,ArrayList<clsPrimaryDataStructureContainer>>> moPerceptPlusMemories_Output;
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:31:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E16_ManagementOfMemoryTraces(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moPerceptPlusMemories_Output_old = new ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>>();
		moPerceptPlusMemories_Output = new ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer,ArrayList<clsPrimaryDataStructureContainer>>>(); 
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
	 * 11.08.2009, 14:31:53
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poPerceptPlusRepressed) {
		moPerceptPlusRepressed_Input_old = (ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>)deepCopy(poPerceptPlusRepressed_old);
		moPerceptPlusRepressed_Input = (ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>>)deepCopy(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		process_oldDT(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 * This method is used while adapting the model from the old datatypes (pa.datatypes) to the
	 * new ones (pa.memorymgmt.datatypes) The method has to be deleted afterwards.
	 * @author zeilinger
	 * 13.08.2010, 09:56:48
	 * @deprecated
	 */
	private void process_oldDT() {
		moPerceptPlusMemories_Output_old = getOutput(moPerceptPlusRepressed_Input_old); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:23:35
	 *
	 * @param moPerceptPlusRepressed_Input2
	 * @return
	 */
	private ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> getOutput(
			         ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_Input) {
		
		ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> oRetVal
								= new ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>>(); 
		
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation>element : poPerceptPlusRepressed_Input){
			oRetVal.add(changeRepressedContent(element)); 
		}
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:48:40
	 *
	 * @param element
	 * @return
	 */
	private clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>> changeRepressedContent(
					clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {
		
		ArrayList<clsPrimaryInformation> oAwareContentList = new ArrayList<clsPrimaryInformation>(); 
		
		if(poInputElement.a != null && poInputElement.b != null){
			oAwareContentList = getAwareContentList(poInputElement); 
			return new clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, poInputElement.b, oAwareContentList); 
		}
		return new clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, poInputElement.b, oAwareContentList); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 15:18:01
	 *
	 * @param poInputElement
	 * @return
	 */
	
	//HashMap<clsPrimaryInformation, clsMutableDouble> oContextResult = moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(mrContextSensitivity);
	
	private ArrayList<clsPrimaryInformation> getAwareContentList(clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {

		HashMap<clsPrimaryInformation, clsMutableDouble> oCurrentContextMap = this.moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(1); 
		ArrayList <clsPrimaryInformation> oAwareContent = new ArrayList<clsPrimaryInformation>(); 
		eEntityType oEntityType = eEntityType.valueOf(poInputElement.a.moTP.moContent.toString()); 
		eRepressedContentType oRepressedContentType = eRepressedContentType.valueOf(poInputElement.b.moTP.moContent.toString()); 
		
		for(clsPrimaryInformation oContextName : oCurrentContextMap.keySet()){
			String oCurrentContextType = oContextName.moTP.moContent.toString();
			
			oAwareContent.add(this.moEnclosingContainer.moMemory.moAwareContentsStore
					    			 .getMappedContent(new clsTripple<String, String, String>(oEntityType.toString(), 
							         oCurrentContextType.toString(),oRepressedContentType.toString()))); 
		}
		
		return oAwareContent; 
	 }
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_7(moPerceptPlusMemories_Output_old, moPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:56:27
	 * 
	 * @see pa.interfaces.send.I2_7_send#send_I2_7(java.util.ArrayList)
	 */
	@Override
	public void send_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output_old,
			  ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer,ArrayList<clsPrimaryDataStructureContainer>>> poPerceptPlusMemories_Output) {
		((I2_7_receive)moEnclosingContainer).receive_I2_7(moPerceptPlusMemories_Output_old, moPerceptPlusMemories_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:39
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
	 * 12.07.2010, 10:46:39
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 12.08.2010, 20:57:42
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> accessKnowledgeBase(
						ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPatternContainer) {
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(poSearchPatternContainer);
	}
}
