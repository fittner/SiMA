/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I6_2_receive;
import pa._v30.interfaces.modules.I7_1_receive;
import pa._v30.interfaces.modules.I7_3_receive;
import pa._v30.interfaces.modules.I7_3_send;
import pa._v30.memorymgmt.datatypes.clsAct;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eActState;
import pa._v30.tools.toText;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 * 
 */
public class E27_GenerationOfImaginaryActions extends clsModuleBase implements I6_2_receive, I7_1_receive, I7_3_send {
	public static final String P_MODULENUMBER = "27";

	//HZ Not used up to now 16.03.2011
	private ArrayList<clsSecondaryDataStructureContainer> moGoalInput;
	private ArrayList<ArrayList<clsAct>> moPlanInput; 
	private ArrayList<clsWordPresentation> moActions_Output;
	
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:55:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E27_GenerationOfImaginaryActions(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		
		//ArrayList<clsPlanAction> moActions_Output = new ArrayList<clsPlanAction>(); //never used!

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.listToTEXT("moPlanInput", moPlanInput);
		text += toText.listToTEXT("moActions_Output", moActions_Output);
		text += toText.listToTEXT("moGoalInput", moGoalInput);
		
		return text;
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
	public void receive_I7_1(ArrayList<clsSecondaryDataStructureContainer> poGoalInput) {
		//HZ Not used up to now 16.03.2011
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
	}
	
	/**
	 *
	 *
	 * @author zeilinger
	 * 02.09.2010, 19:46:45
	 * @return 
	 *
	 */
	private ArrayList<clsWordPresentation> getActions() {
		ArrayList <clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>(); 
		ArrayList<clsAct> oPlan = evaluatePlans();
		
		for(clsAct oAct : oPlan){
			for(clsSecondaryDataStructure oSD : oAct.getMoAssociatedContent()){
				if(oSD instanceof clsWordPresentation && oSD.getMoContentType().equals(eActState.ACTION.name())){
					oRetVal.add((clsWordPresentation)oSD); 
					return oRetVal; 
				}
			}			
		}
		
		return oRetVal; 
	}

	/**
	 *
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


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I7_3(moActions_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:56:21
	 * 
	 * @see pa.interfaces.send.I7_3_send#send_I7_3(java.util.ArrayList)
	 */
	@Override
	public void send_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		((I7_3_receive)moModuleList.get(47)).receive_I7_3(poActionCommands);
		((I7_3_receive)moModuleList.get(29)).receive_I7_3(poActionCommands);
		((I7_3_receive)moModuleList.get(33)).receive_I7_3(poActionCommands);
		((I7_3_receive)moModuleList.get(34)).receive_I7_3(poActionCommands);
		
		putInterfaceData(I7_3_send.class, poActionCommands);
		
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
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:55:51
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "By combination of the motives provided by {E26} and the experiences retrieved by {E28}, {E27} generates a set of imaginary actions. Before actions are passed to {E30} they are solely psychic contents and thus imaginary. An imaginary action (-plan) defines a more or less complex sequence of actions on how to satisfy a need based on actions taken in similar situations. ";
	}		
}
