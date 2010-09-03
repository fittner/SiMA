/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.itfTimeChartInformationContainer;
import pa.interfaces.receive.I6_2_receive;
import pa.interfaces.receive.I7_1_receive;
import pa.interfaces.receive.I7_3_receive;
import pa.interfaces.send.I7_3_send;
import pa.loader.plan.clsPlanAction;
import pa.loader.plan.clsPlanBaseMesh;
import pa.loader.plan.clsPlanStateMesh;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 * 
 */
public class E27_GenerationOfImaginaryActions extends clsModuleBase implements I6_2_receive, I7_1_receive, I7_3_send, itfTimeChartInformationContainer{

	public ArrayList<clsSecondaryInformation> moEnvironmentalPerception;
	public HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateResult_Input_old;
	public ArrayList<clsSecondaryDataStructureContainer> moGoalInput;
	public ArrayList<ArrayList<clsAct>> moPlanInput; 
	
	ArrayList<clsPlanAction> moActions_Output_old;
	private ArrayList<clsWordPresentation> moActions_Output; 
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:55:40
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E27_GenerationOfImaginaryActions(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		//ArrayList<clsPlanAction> moActions_Output = new ArrayList<clsPlanAction>(); //never used!
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
	
	/**
	 * @author zeilinger
	 * 02.09.2010, 19:48:48
	 * 
	 * @return the moActions_Output
	 */
	public ArrayList<clsWordPresentation> getMoActions_Output() {
		return moActions_Output;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:55:51
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput) {
		moPlanInput = (ArrayList<ArrayList<clsAct>>) deepCopy(poPlanOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:55:51
	 * 
	 * @see pa.interfaces.I7_1#receive_I7_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I7_1(HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult_old, 
						ArrayList<clsSecondaryDataStructureContainer> poGoalInput) {
		moTemplateResult_Input_old = ( HashMap<String, clsPair<clsSecondaryInformation, Double>>) deepCopy( poTemplateResult_old);
		moGoalInput = ( ArrayList<clsSecondaryDataStructureContainer> )deepCopy(poGoalInput);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//HZ 2010.08.28
		//E27 should retrieve required acts through E28. However, it can be 
		//doubted if this works without a loop between E27 and E28. In addition the functionality of 
		//E28 has to be discussed as it should only access the memory and retrieve acts. 
		//Reasons for my doubts: Actually E28 receives (like E27) the current goal
		//that is formed out of a drive that should be satisfied and the object 
		//that should be used to satisfy it. Now it can be searched in the memory which
		//actions have to be set to be able to satisfy the drive (e.g. action EAT in 
		//order to NOURISH a CAKE). However, in general the required object is not 
		//in the right position in order to use the action on it (A cake can only be eaten
		//in case it is in the eatable area). Hence other Acts have to be triggered that
		//help to put the agent into the right position. These acts are not part 
		//of the act "eat cake". They would be accomplished before the cake can be
		//eaten. Hence the plan has to be rebuild by single acts that can only be
		//retrieved from the memory in case there is a loop between E27 and E28 or
		//E27 has a memory access on its own => E28 woul dbe senseless.
		//
		//Until this question has been solved, E28 
		//is implemented to retrieve and put acts together which means that it takes over
		//a kind of planning.
		moActions_Output = new ArrayList<clsWordPresentation>();
		moActions_Output = getActions(); 
		process_oldDT();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.09.2010, 19:46:45
	 * @return 
	 *
	 */
	private ArrayList<clsWordPresentation> getActions() {
		ArrayList <clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>(); 
		ArrayList<clsAct> oPlan = evaluatePlans();
		
		loop: 
		for(clsAct oAct : oPlan){
			for(clsSecondaryDataStructure oSD : oAct.moAssociatedContent){
				if(oSD instanceof clsWordPresentation && oSD.moContentType.equals("ACTION")){
					oRetVal.add((clsWordPresentation)oSD); 
					break loop; 
				}
			}			
		}
		
		return oRetVal; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 17:19:37
	 *
	 * @return
	 */
	private ArrayList<clsAct> evaluatePlans() {
		//HZ This method evaluates the retrieved plans. Actually this is rather simple
		// as only the number of acts that are required to fulfill the plan are used 
		// for this evaluation (the plan with the fewest number of acts is selected)
		ArrayList<clsAct> oRetVal = new ArrayList<clsAct>();  
		
		for(ArrayList<clsAct> oEntry : moPlanInput){
			if((oRetVal.size() == 0) || (oRetVal.size() > oEntry.size())){
				oRetVal = oEntry; 
			}
		}
		
		return oRetVal;
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
		moActions_Output_old = this.moEnclosingContainer.moMemory.moTemplatePlanStorage.getReognitionUpdate(moTemplateResult_Input_old);
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 04.11.2009, 19:37:55
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<clsPair<String, Double>> getTimeChartData() {

		ArrayList<clsPair<String, Double>> oRetVal = new ArrayList<clsPair<String, Double>>();
		for( Map.Entry<String,  clsPair<clsSecondaryInformation, Double>> oMatch : moTemplateResult_Input_old.entrySet()) {
			oRetVal.add(new clsPair<String, Double>("TI_"+oMatch.getKey(), oMatch.getValue().b));
		}
		
		for( clsSecondaryInformation oMesh : this.moEnclosingContainer.moMemory.moTemplatePlanStorage.moTemplatePlans) {
			if(oMesh instanceof clsPlanBaseMesh) {
				clsPlanBaseMesh oPlan = (clsPlanBaseMesh)oMesh;
				if(oPlan.moWP.moContent.toString().equals("CAKE_HUNGER") ) { //only display this plan

					for( Map.Entry<Integer, clsPlanStateMesh> oStep : oPlan.moStates.entrySet() ) {
						
						clsPlanStateMesh oState = oStep.getValue();
						
						double oActive = 0;
						if( oState.mnId==oPlan.mnCurrentState ) {
							oActive = 1;
						}
						oRetVal.add(new clsPair<String, Double>("PL_"+oState.moWP.moContent, oActive));
					}
				}
			}
		}
		
		return oRetVal;
	}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I7_3(moActions_Output_old, moActions_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:56:21
	 * 
	 * @see pa.interfaces.send.I7_3_send#send_I7_3(java.util.ArrayList)
	 */
	@Override
	public void send_I7_3(ArrayList<clsPlanAction> poActionCommands_old, ArrayList<clsWordPresentation> poActionCommands) {
		((I7_3_receive)moEnclosingContainer).receive_I7_3(moActions_Output_old, moActions_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:41
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
	 * 12.07.2010, 10:47:41
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
