/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.receive.I1_7_receive;
import pa.interfaces.receive.I2_13_receive;
import pa.interfaces.receive.I3_3_receive;
import pa.interfaces.receive.I5_5_receive;
import pa.interfaces.receive.I7_1_receive;
import pa.interfaces.receive.I7_2_receive;
import pa.interfaces.send.I7_1_send;
import pa.interfaces.send.I7_2_send;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 * 
 */
public class E26_DecisionMaking extends clsModuleBase implements I1_7_receive, I2_13_receive, I3_3_receive, I5_5_receive, I7_1_send, I7_2_send {

	private ArrayList<clsSecondaryInformation> moDriveList;
	private ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> moRealityPerception;
	
	HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateImageResult;
	HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateScenarioResult;
	
	HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateResult_Output;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:52:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E26_DecisionMaking(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moTemplateImageResult = new HashMap<String, clsPair<clsSecondaryInformation, Double>>();
		moTemplateScenarioResult = new HashMap<String, clsPair<clsSecondaryInformation, Double>>();
		moTemplateResult_Output = new HashMap<String, clsPair<clsSecondaryInformation, Double>>();
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
		mnProcessType = eProcessType.SECONDARY;
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
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 * 
	 * by this interface a list of drives, which represent the current wishes
	 * fills moDriveList
	 *   
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 * 
	 * by this interface a set of reality information, filtered by E24 (reality check), is received
	 * fills moRealityPerception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_13(ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> poRealityPerception) {
		moRealityPerception = (ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>>)deepCopy(poRealityPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@Override
	public void receive_I3_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@Override
	public void receive_I5_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 * 
	 * this module sends the perception input to module E27, E28 just bypasses the information and sends an additional counter which is not used
	 *  
	 * 
	 */
	@Override
	protected void process_basic() {

		// moDriveList -> drives from primary process (wishes)
		// moRealityPerception -> reality filtered by external perception
		
		ArrayList<clsSecondaryInformation> oCompletePerception = new ArrayList<clsSecondaryInformation>();
		
		// combine wishes and external perception
		
		// first wishes
		oCompletePerception.addAll(moDriveList);
		
		// dirty hack -> moRealityPerception only contains "a" part of the clsPair
		for(clsPair<clsSecondaryInformation, clsSecondaryInformationMesh> oReal :moRealityPerception) {
			oCompletePerception.add(oReal.a);
		}
		
		// compare perception with template images to map real perceptive input to stored template information 
		// return value is a set of recognized and compared images weighted by a match-factor  
		moTemplateImageResult = moEnclosingContainer.moMemory.moTemplateImageStorage.compare(oCompletePerception);
		
		// calculates (maybe) the current state of a matched scenario 
		moTemplateScenarioResult = moEnclosingContainer.moMemory.moTemplateScenarioStorage.getReognitionUpdate(moTemplateImageResult);
		
		moTemplateResult_Output.putAll(	moTemplateImageResult ); 
		moTemplateResult_Output.putAll(	moTemplateScenarioResult );
		
//		int i =0; //never used!
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I7_1(moTemplateResult_Output);
		send_I7_2(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I7_1(
			HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult) {
		((I7_1_receive)moEnclosingContainer).receive_I7_1(moTemplateResult_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_2_send#send_I7_2(int)
	 */
	@Override
	public void send_I7_2(int pnData) {
		((I7_2_receive)moEnclosingContainer).receive_I7_2(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:36
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
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}

