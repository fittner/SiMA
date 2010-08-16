/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I2_10_receive;
import pa.interfaces.receive.I2_11_receive;
import pa.interfaces.receive.I5_4_receive;
import pa.interfaces.send.I2_11_send;
import pa.interfaces.send.I5_4_send;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 * 
 */
public class E21_ConversionToSecondaryProcess extends clsModuleBase implements I2_10_receive, I2_11_send, I5_4_send, itfKnowledgeBaseAccess {

	private ArrayList<clsPrimaryInformation> moGrantedPerception_Input_old;
	private ArrayList<clsSecondaryInformation> moPerception_Output_old;

	private ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_Input; 
	private ArrayList<clsSecondaryDataStructureContainer> moPerception_Output; 
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:38:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E21_ConversionToSecondaryProcess(String poPrefix,
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
	 * 11.08.2009, 14:39:18
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception_old, ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		moGrantedPerception_Input_old = (ArrayList<clsPrimaryInformation>)this.deepCopy(poGrantedPerception_old);
		moGrantedPerception_Input = (ArrayList<clsPrimaryDataStructureContainer>)this.deepCopy(poGrantedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		process_oldDT(); 
//		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer, clsDataStructurePA>>(); 
//		List<ArrayList<clsPair<Double, clsDataStructureContainer>>> oSearchResult = null; 
//		moPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>();  
//		
//		for(clsPrimaryDataStructureContainer oPrimContainer : moGrantedPerception_Input) {
//			oSearchPattern.add(new clsPair<Integer,clsDataStructurePA>(eDataType.WP.nBinaryValue,oPrimContainer.moDataStructure));
//		}
//		
//		oSearchResult = accessKnowledgeBase(oSearchPattern); 
//
//		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResultEntry : oSearchResult){
//			/*HZ: Up to now the best matching result is taken from the ArrayList and add to the variable
//			 * moPerception_Output*/
//			moPerception_Output.add((clsSecondaryDataStructureContainer)oSearchResultEntry.get(0).b); 
//		}
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
		moPerception_Output_old = new ArrayList<clsSecondaryInformation>();
		for( clsPrimaryInformation oPriminfo : moGrantedPerception_Input_old ) {

			if(oPriminfo instanceof clsPrimaryInformationMesh) {
				moPerception_Output_old.add(new clsSecondaryInformationMesh(oPriminfo));
			}
			else if(oPriminfo instanceof clsPrimaryInformation) {
				moPerception_Output_old.add(new clsSecondaryInformation(oPriminfo));
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa.memorymgmt.datatypes
		send_I2_11(moPerception_Output_old, moPerception_Output);
		send_I5_4(moPerception_Output_old, moPerception_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I2_11(ArrayList<clsSecondaryInformation> poPerception_old, ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(moPerception_Output_old, moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I5_4(ArrayList<clsSecondaryInformation> poPerception_old,
			  				ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I5_4_receive)moEnclosingContainer).receive_I5_4(moPerception_Output_old, moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:07
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
	 * 12.07.2010, 10:47:07
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
	 * 12.08.2010, 20:58:22
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> accessKnowledgeBase() {
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:15:47
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType,
			clsDataStructurePA poSearchPattern) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

}
