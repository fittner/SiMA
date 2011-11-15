/**
 * E29_EvaluationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I6_10_receive;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.I6_11_send;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 * 
 */
public class F29_EvaluationOfImaginaryActions extends clsModuleBase implements 
					I6_2_receive, I6_9_receive, I6_10_receive, I6_11_send {
	public static final String P_MODULENUMBER = "29";
	
	private ArrayList<clsSecondaryDataStructureContainer> moActionCommands_Input; 
	private ArrayList<clsWordPresentation> moActionCommands_Output;
	
	// Anxiety from F20
	private ArrayList<clsPrediction> moAnxiety_Input;
	
	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
	private clsDataStructureContainerPair moEnvironmentalPerception_OUT;
	
	/**
	 * DOCUMENT (perner) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:59:54
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F29_EvaluationOfImaginaryActions(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
		text += toText.listToTEXT("moActionCommands_Output", moActionCommands_Output);
		text += toText.listToTEXT("moAnxiety_Input", moAnxiety_Input);
		
		
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:57:45
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_2(ArrayList<clsSecondaryDataStructureContainer> poAnxiety_Input) {
		moAnxiety_Input = (ArrayList<clsPrediction>)deepCopy(poAnxiety_Input);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:57:45
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(int)
	 */
	@SuppressWarnings("unchecked") //deepCopy can only perform an unchecked operation
	@Override
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainerPair> poAssociatedMemories, clsDataStructureContainerPair poEnvironmentalPerception) {
		moActionCommands_Input = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poActionCommands);
		moEnvironmentalPerception_IN = poEnvironmentalPerception;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:43:32
	 * 
	 * @see pa.interfaces.I7_6#receive_I7_6(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_10(int pnData) {
		// TODO (perner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:46
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//AW HACK test, in order to be able to use both WP and plan fragements at the same time
		boolean bPlanFragement = false;
		for (clsSecondaryDataStructureContainer oC : moActionCommands_Input) {
			if (oC instanceof clsPlanFragment) {
				bPlanFragement = true;
				break;
			}
		}
		
		
		
		// normal use of actions -> without AW hack
		if (bPlanFragement==true) {
			// run over all actions and sort out the most appropriate ones
			ArrayList<clsSecondaryDataStructureContainer> sortedActions  = new ArrayList<clsSecondaryDataStructureContainer> ();
			int iCursorPos = 0;
			
			int iIndexOfEat = -1;
			int iIndexOfMoveForward = -1;
			int iIndexOfFlee = -1;
			int iIndexOfOverrideAction = -1; // stores the index of an action which should be used over all others because of interface I.6_2
			for (clsSecondaryDataStructureContainer oC : moActionCommands_Input) {
				
				
				if (oC instanceof clsPlanFragment) {
					clsPlanFragment plFr = (clsPlanFragment) oC;
					String strAction = plFr.m_act.m_strAction;

					if (strAction.equalsIgnoreCase("EAT")) 
						iIndexOfEat = iCursorPos;
					if (strAction.equalsIgnoreCase("MOVE_FORWARD"))
						iIndexOfMoveForward = iCursorPos;
					if (strAction.equalsIgnoreCase("FLEE"))
						iIndexOfFlee = iCursorPos;
				}
				
				iCursorPos ++;
			}

			if (moAnxiety_Input.size() > 0) {
				for (Object myPred : moAnxiety_Input) {
					
					if (myPred instanceof clsSecondaryDataStructureContainer) {
						String strVal = myPred.toString();
						
						if (strVal.contains("ANXIETY")) {
							iIndexOfOverrideAction = iIndexOfFlee;
						}
					}
				}
			}
			
			
			// order of actions, use eat first, then move forward than all other actions
			// flee is used as override action
			if (iIndexOfOverrideAction > 0) 
				sortedActions.add(moActionCommands_Input.get(iIndexOfOverrideAction));
			else if (iIndexOfEat > 0) // only use eat 
				sortedActions.add(moActionCommands_Input.get(iIndexOfEat));
			else if (iIndexOfMoveForward > 0) // only use move forward
				sortedActions.add(moActionCommands_Input.get(iIndexOfMoveForward));
			else // use all other actions
				sortedActions = moActionCommands_Input;
			
			// if agent feels anxiety -> flee and discard all other actions
			
			
			
			ArrayList<clsWordPresentation> moActionCommandsTemp = new ArrayList<clsWordPresentation>();
			for (clsSecondaryDataStructureContainer oC : sortedActions) {
				
				// convert actions back to wordpresentation -> only wordpresentations are allowed to be handled over to motility control
				if (oC instanceof clsPlanFragment) {
					clsPlanFragment plFr = (clsPlanFragment) oC;
					String strAction = plFr.m_act.m_strAction;
					clsWordPresentation myWP = new clsWordPresentation(new clsTriple(1, eDataType.ACT, strAction), strAction);

					moActionCommandsTemp.add(myWP);
				}
			}
			
			//No nulls are allowed
			moActionCommands_Output = new ArrayList<clsWordPresentation>();
			
			//Get the first command
			if (moActionCommandsTemp.isEmpty()==false) {
				moActionCommands_Output.add(moActionCommandsTemp.get(0));
			}
		} else {
			moActionCommands_Output = getWordPresentations(moActionCommands_Input);
		}

		
		// copy perception
		moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;
	}
	
	//AW 20110629 New function, which converts clsSecondaryDataStructureContainer to clsWordpresentation
	/**
	 * convert the act to a word presentation, temp function!!!
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 02.08.2011 09:50:37
	 *
	 * @param poInput
	 * @return
	 */
	private ArrayList<clsWordPresentation> getWordPresentations(ArrayList<clsSecondaryDataStructureContainer> poInput) {
		ArrayList<clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>();
		
		for (clsSecondaryDataStructureContainer oCont: poInput) {
			clsWordPresentation oWP = clsDataStructureGenerator.generateWP(new clsPair<String, Object>("ACTION", ((clsWordPresentation)oCont.getMoDataStructure()).getMoContent()));
			
			oRetVal.add(oWP);
		}
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:46
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_11(moActionCommands_Output, moEnvironmentalPerception_OUT);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:58:21
	 * 
	 * @see pa.interfaces.send.I7_4_send#send_I7_4(java.util.ArrayList)
	 */
	@Override
	public void send_I6_11(ArrayList<clsWordPresentation> poActionCommands, clsDataStructureContainerPair poEnvironmentalPerception) {
		((I6_11_receive)moModuleList.get(30)).receive_I6_11(poActionCommands, poEnvironmentalPerception);
		
		putInterfaceData(I6_11_send.class, poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:52
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (perner) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:52
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (perner) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 17:00:00
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The imaginary actions are evaluated by this module based on the result of the second reality check. The result of this mental rating is a reduced list with the imaginary actions selected for execution. ";
	}		
}
