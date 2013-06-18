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

import org.apache.log4j.Logger;

import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I6_10_receive;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.I6_11_send;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author deutsch 11.08.2012, 14:57:10
 * 
 */
public class F29_EvaluationOfImaginaryActions extends clsModuleBaseKB implements I6_2_receive, I6_9_receive, I6_10_receive, I6_11_send,
        itfInspectorGenericActivityTimeChart {
    public static final String P_MODULENUMBER = "29";
    
	/** Specialized Logger for this class */
	private Logger log = Logger.getLogger(this.getClass());

    private ArrayList<clsWordPresentationMesh> moActionCommands_Input;
    private ArrayList<clsWordPresentationMesh> moActionCommands_Output;

    // Anxiety from F20
    private ArrayList<clsWordPresentationMeshFeeling> moAnxiety_Input;

    private clsWordPresentationMesh moPerceptionalMesh_IN;
    //private clsWordPresentationMesh moPerceptionalMesh_OUT;
    
	private clsShortTermMemory moShortTermMemory;
	
	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
    /**
     * DOCUMENT (perner) - insert description
     * 
     * @author deutsch 03.03.2011, 16:59:54
     * 
     * @param poPrefix
     * @param poProp
     * @param poModuleList
     * @throws Exception
     */
    public F29_EvaluationOfImaginaryActions(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory, clsEnvironmentalImageMemory poTempLocalizationStorage,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
        
        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
        applyProperties(poPrefix, poProp);
        
        this.moShortTermMemory = poShortTermMemory;
        this.moEnvironmentalImageStorage = poTempLocalizationStorage;
        

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 14.04.2011, 17:36:19
     * 
     * @see pa.modules._v38.clsModuleBase#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        String text = "";

        text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
        text += toText.listToTEXT("moActionCommands_Output", moActionCommands_Output);
        text += toText.listToTEXT("moAnxiety_Input", moAnxiety_Input);

        return text;
    }

    public static clsProperties getDefaultProperties(String poPrefix) {
        String pre = clsProperties.addDot(poPrefix);

        clsProperties oProp = new clsProperties();
        oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

        return oProp;
    }

    private void applyProperties(String poPrefix, clsProperties poProp) {
        // String pre = clsProperties.addDot(poPrefix);

        // nothing to do
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 12:09:34
     * 
     * @see pa.modules.clsModuleBase#setProcessType()
     */
    @Override
    protected void setProcessType() {
        mnProcessType = eProcessType.SECONDARY;
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 12:09:34
     * 
     * @see pa.modules.clsModuleBase#setPsychicInstances()
     */
    @Override
    protected void setPsychicInstances() {
        mnPsychicInstances = ePsychicInstances.EGO;
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 14:57:45
     * 
     * @see pa.interfaces.I5_5#receive_I5_5(int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I6_2(ArrayList<clsWordPresentationMeshFeeling> poAnxiety_Input) {
        moAnxiety_Input = (ArrayList<clsWordPresentationMeshFeeling>) deepCopy(poAnxiety_Input);

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 14:57:45
     * 
     * @see pa.interfaces.I7_3#receive_I7_3(int)
     */
    // deepCopy can only perform an unchecked operation
    @Override
    public void receive_I6_9(ArrayList<clsWordPresentationMesh> poActionCommands) {
        //moActionCommands_Input = (ArrayList<clsWordPresentationMesh>) deepCopy(poActionCommands);
    	//INFORMATION: DEEPCOPY REMOVED 
        moActionCommands_Input = poActionCommands;
        //moPerceptionalMesh_IN = poEnvironmentalPerception;
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 27.04.2010, 10:43:32
     * 
     * @see pa.interfaces.I7_6#receive_I7_6(java.util.ArrayList)
     */
    @Override
    public void receive_I6_10(int pnData) {
        // TODO (perner) - Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 16:16:46
     * 
     * @see pa.modules.clsModuleBase#process()
     */
    @Override
    protected void process_basic() {

    	//Receive the perceived image
    	this.moPerceptionalMesh_IN = this.moEnvironmentalImageStorage.findCurrentSingleMemory();
    	
        // AW HACK test, in order to be able to use both WP and plan fragements at the same time
        boolean bPlanFragement = true;

        // normal use of actions -> without AW hack
        if (bPlanFragement == true) {
            // run over all actions and sort out the most appropriate ones
            ArrayList<clsWordPresentationMesh> sortedActions = new ArrayList<clsWordPresentationMesh>();
            int iCursorPos = 0;

            int iIndexOfEat = -1;
            int iIndexOfMoveForward = -1;
            int iIndexOfFlee = -1;
            int iIndexOfOverrideAction = -1; // stores the index of an action which should be used over all others because of interface
                                             // I.6_2
            for (clsWordPresentationMesh oC : moActionCommands_Input) {

                String strAction = oC.getMoContent();

                if (strAction.equalsIgnoreCase("EAT"))
                    iIndexOfEat = iCursorPos;
                if (strAction.equalsIgnoreCase("MOVE_FORWARD"))
                    iIndexOfMoveForward = iCursorPos;
                if (strAction.equalsIgnoreCase("FLEE"))
                    iIndexOfFlee = iCursorPos;

                iCursorPos++;
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
            else
                // use all other actions
                sortedActions = moActionCommands_Input;

            // if agent feels anxiety -> flee and discard all other actions

            ArrayList<clsWordPresentationMesh> moActionCommandsTemp = new ArrayList<clsWordPresentationMesh>();
            for (clsWordPresentationMesh oC : sortedActions) {

                // convert actions back to wordpresentation -> only wordpresentations are allowed to be handled over to motility control
                // if (oC instanceof clsPlanFragment) {
                // clsPlanFragment plFr = (clsPlanFragment) oC;
                String strAction = oC.getMoContent(); // plFr.m_act.m_strAction;
                clsWordPresentationMesh myWP = oC;//new clsWordPresentationMesh(new clsTriple<Integer, eDataType, String>(1, eDataType.WPM, strAction), oC.getMoAssociatedContent(), strAction);

                moActionCommandsTemp.add(myWP);
                // }
            }

            // No nulls are allowed
            moActionCommands_Output = new ArrayList<clsWordPresentationMesh>();

            // Get the first command
            if (moActionCommandsTemp.isEmpty() == false) {
                moActionCommands_Output.add(moActionCommandsTemp.get(0));
            }
        } else {
            moActionCommands_Output = moActionCommands_Input;
        }
        
        //Save action to the short term storage
        if (moActionCommands_Output.isEmpty()==false) {
        	addActionToMentalSituation(moActionCommands_Output.get(0));
        }
        
        
//        //=== TEST ONLY ONE ACTION === //
//		if (clsTester.getTester().isActivated()) {
//			try {
//				eAction poReplaceAction = eAction.STRAFE_LEFT;
//				clsTester.getTester().exeTestAction(moActionCommands_Output, poReplaceAction);
//				log.warn("In test mode the action " + moActionCommands_Output + " was changed to " + poReplaceAction);
//			} catch (Exception e) {
//				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
//			}
//		}
        

    }
    
	private void addActionToMentalSituation(clsWordPresentationMesh poAction) {
		//get the ref of the current mental situation
		clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
		
		//Get the real connection from the reference for the action
		clsWordPresentationMesh oSupportiveDataStructure = clsActionTools.getSupportiveDataStructure(poAction);
		if (oSupportiveDataStructure.getMoContent().equals(eContentType.NULLOBJECT.toString())==false) {
			//get WPMRef
			ArrayList<clsDataStructurePA> oFoundStructures = clsMeshTools.searchDataStructureOverAssociation(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTURE, 0, true, true);
			
			if (oFoundStructures.isEmpty()==false) {
				clsAssociation oAss = (clsAssociation) oFoundStructures.get(0);	
				clsMeshTools.moveAssociation(oSupportiveDataStructure, (clsWordPresentationMesh)oAss.getLeafElement(), oAss, true);
			}
		}
		
		//Add the action to the memory
		clsMentalSituationTools.setAction(oCurrentMentalSituation, poAction);
	}

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 16:16:46
     * 
     * @see pa.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        send_I6_11(moActionCommands_Output);

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 18.05.2010, 17:58:21
     * 
     * @see pa.interfaces.send.I7_4_send#send_I7_4(java.util.ArrayList)
     */
    @Override
    public void send_I6_11(ArrayList<clsWordPresentationMesh> poActionCommands) {
        ((I6_11_receive) moModuleList.get(30)).receive_I6_11(poActionCommands);
        ((I6_11_receive) moModuleList.get(52)).receive_I6_11(poActionCommands);

        putInterfaceData(I6_11_send.class, poActionCommands);
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 12.07.2010, 10:47:52
     * 
     * @see pa.modules.clsModuleBase#process_draft()
     */
    @Override
    protected void process_draft() {
        // TODO (perner) - Auto-generated method stub
        throw new java.lang.NoSuchMethodError();
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 12.07.2010, 10:47:52
     * 
     * @see pa.modules.clsModuleBase#process_final()
     */
    @Override
    protected void process_final() {
        // TODO (perner) - Auto-generated method stub
        throw new java.lang.NoSuchMethodError();
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 03.03.2011, 17:00:00
     * 
     * @see pa.modules._v38.clsModuleBase#setModuleNumber()
     */
    @Override
    protected void setModuleNumber() {
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 15.04.2011, 13:52:57
     * 
     * @see pa.modules._v38.clsModuleBase#setDescription()
     */
    @Override
    public void setDescription() {
        moDescription = "The imaginary actions are evaluated by this module based on the result of the second reality check. The result of this mental rating is a reduced list with the imaginary actions selected for execution. ";
    }

    /*
     * (non-Javadoc)
     * 
     * @author brandstaetter 29.11.2010, 12:00:00
     * 
     * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
     */
    @Override
    public ArrayList<Double> getTimeChartData() {
        ArrayList<Double> oRetVal = new ArrayList<Double>();
        double rNUM_INPUT_ACTIONS = moActionCommands_Input.size();
        oRetVal.add(rNUM_INPUT_ACTIONS);
        return oRetVal;
    }

    /*
     * (non-Javadoc)
     * 
     * @author brandstaetter 29.11.2010, 12:00:00
     * 
     * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
     */
    @Override
    public String getTimeChartAxis() {
        return "Number of Incoming Action Commands";
    }

    /*
     * (non-Javadoc)
     * 
     * @author brandstaetter 29.11.2010, 12:00:00
     * 
     * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
     */
    @Override
    public String getTimeChartTitle() {
        return "Number of Incoming Action Commands";
    }

    /*
     * (non-Javadoc)
     * 
     * @author brandstaetter 29.11.2010, 12:00:00
     * 
     * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
     */
    @Override
    public ArrayList<String> getTimeChartCaptions() {
        ArrayList<String> oCaptions = new ArrayList<String>();

        oCaptions.add("NUM_INPUT_ACTIONS");

        return oCaptions;
    }
}
