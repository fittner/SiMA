/**
 * E29_EvaluationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 */
package pa._v38.modules;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I6_10_receive;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.I6_11_send;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.ElementNotFoundException;
import pa._v38.tools.toText;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.functionality.PlanningFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;
import config.clsProperties;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author deutsch 11.08.2012, 14:57:10
 * 
 */
public class F29_EvaluationOfImaginaryActions extends clsModuleBaseKB implements I6_2_receive, I6_10_receive, I6_11_send,
        itfInspectorGenericActivityTimeChart {
    public static final String P_MODULENUMBER = "29";

    private ArrayList<String> moTEMPWriteLastActions = new ArrayList<String>();

    private ArrayList<clsWordPresentationMeshPossibleGoal> moSelectableGoals;
    private clsWordPresentationMesh moActionCommand;

    // Anxiety from F20
    private ArrayList<clsWordPresentationMeshFeeling> moAnxiety_Input;

    private clsWordPresentationMesh moPerceptionalMesh_IN;
    
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;
	
	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	private final DecisionEngine moDecisionEngine;
	
	private String moTEMPDecisionString = "";
	
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
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory, clsEnvironmentalImageMemory poTempLocalizationStorage, DecisionEngine decisionEngine,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
        
        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
        applyProperties(poPrefix, poProp);
        
        this.moShortTermMemory = poShortTermMemory;
        this.moEnvironmentalImageStorage = poTempLocalizationStorage;
        this.moDecisionEngine = decisionEngine;
        
        

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

        text += toText.listToTEXT("moSelectableGoals", moSelectableGoals);
        text += toText.valueToTEXT("moActionCommand", moActionCommand);
        text += toText.listToTEXT("moAnxiety_Input", moAnxiety_Input);
        text += toText.valueToTEXT("CURRENT DECISION", this.moTEMPDecisionString);
        text += toText.listToTEXT("Goals and actions", moTEMPWriteLastActions);

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
//    @Override
//    public void receive_I6_9(ArrayList<clsWordPresentationMesh> poActionCommands) {
//        //moActionCommands_Input = (ArrayList<clsWordPresentationMesh>) deepCopy(poActionCommands);
//    	//INFORMATION: DEEPCOPY REMOVED 
//        moActionCommands_Input = poActionCommands;
//        //moPerceptionalMesh_IN = poEnvironmentalPerception;
//    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 27.04.2010, 10:43:32
     * 
     * @see pa.interfaces.I7_6#receive_I7_6(java.util.ArrayList)
     */
    @Override
    public void receive_I6_10(ArrayList<clsWordPresentationMeshPossibleGoal> selectableGoals) {
        moSelectableGoals = selectableGoals;

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
        log.debug("=== module {} start ===", this.getClass().getName());

        //Select the best goal
        //Delete previous plan goals
        try {
            this.moDecisionEngine.removeGoalAsPlanGoal(moSelectableGoals);
        } catch (ElementNotFoundException e2) {
            log.error("Cannot remove conditions", e2);
        }
        
        clsWordPresentationMeshPossibleGoal planGoal = GoalHandlingFunctionality.selectPlanGoal(moSelectableGoals);
        try {
            this.moDecisionEngine.declareGoalAsPlanGoal(planGoal);
        } catch (Exception e1) {
            log.error("Cannot declare goal as plan goal",e1 );
        }
        
        log.info("\n=======================\nDecided goal: " + planGoal + "\nSUPPORTIVE DATASTRUCTURE: " + planGoal.getSupportiveDataStructure().toString() + "\n==============================");
        this.moTEMPDecisionString = setDecisionString(planGoal);
        
        //Get action command from goal
        try {
            moActionCommand = PlanningFunctionality.getActionCommandFromPlanGoal(planGoal);
            log.info("Selected Action: {}", moActionCommand);
        } catch (Exception e) {
            log.error("", e);
        }  
        
    	//Goal to STM
        //The action is already saved within the goal
        ShortTermMemoryFunctionality.addContinuedGoalsToMentalSituation(moSelectableGoals, this.moShortTermMemory);
        ShortTermMemoryFunctionality.setPlanGoalInMentalSituation(planGoal, moShortTermMemory);
        
        //Add action
        ShortTermMemoryFunctionality.setExecutedAction(this.moShortTermMemory, moActionCommand);
        
        //Add text to inspector
        addTextToLastActionsTextSequence(moActionCommand, planGoal);
        eAction selectedAction = eAction.valueOf(clsActionTools.getAction(moActionCommand));
        if (selectedAction.equals(eAction.NONE.toString())==true) {
            log.warn("Erroneous action taken. Action cannot be NONE. This must be an error in the codelets");
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
        
        
        //Get the last 10 goals and actions for the inspector
//        clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
//        clsWordPresentationMesh oGoal = clsMentalSituationTools.getGoal(oCurrentMentalSituation);
        


    }
    
    /**
     * Create an inspector entry with running text
     *
     * @author wendt
     * @since 03.10.2013 14:33:06
     *
     * @param actionMesh
     * @param planGoal
     */
    private void addTextToLastActionsTextSequence(clsWordPresentationMesh actionMesh, clsWordPresentationMeshPossibleGoal planGoal) {
        String oAction = "NONE";
        
        //if (moGoalList_OUT.isEmpty()==false) {
        oAction = actionMesh.getContent();
        //}
        
        if (moTEMPWriteLastActions.size()==10) {
            moTEMPWriteLastActions.remove(0);
        }
        
        //Get System time
        Calendar oCal = Calendar.getInstance();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("HH:mm:ss");
        moTEMPWriteLastActions.add(oDateFormat.format(oCal.getTime()) + "> " + "Goal: " + planGoal.getContent().toString() + ":" + planGoal.getSupportiveDataStructure().getContent() + "; Action: " + oAction);
    }
    
    /**
     * Create a string to list the selected action
     *
     * @author wendt
     * @since 03.10.2013 14:28:22
     *
     * @param poDecidedGoal
     * @return
     */
    private String setDecisionString(clsWordPresentationMeshGoal poDecidedGoal) {
        String oResult = "";
        
        //Get the Goal String
        String oGoalString = poDecidedGoal.getGoalName();
        
        //Get the goal object
        String oGoalObjectString = poDecidedGoal.getGoalObject().toString();
        
        //Get the Goal source
        String oGoalSource = "NONE"; 
        if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
            oGoalSource = "DRIVES (Drive goal not found in perception or acts)";
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
            oGoalSource = "ACT";
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
            oGoalSource = "PERCEPTION";
        }
        
        //Get the AffectLevel
        String oAffectLevel = String.valueOf(poDecidedGoal.getTotalImportance()); 
        //eAffectLevel.convertQuotaOfAffectToAffectLevel(clsGoalTools.getAffectLevel(poDecidedGoal)).toString();
        
        //Get Conditions
        String oGoalConditions = "";
        ArrayList<eCondition> oConditionList = poDecidedGoal.getCondition();
        for (eCondition oC : oConditionList) {
            oGoalConditions += oC.toString() + "; ";
        }               
        
        //Get the Supportive DataStructure
        String oSupportiveDataStructureString = "";
        if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString(); 
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
        }
        
        StringBuilder sb = new StringBuilder();
        
        //Set the current decision string
        sb.append("============================================================================================\n");
        sb.append("[GOAL NAME]\n   " + oGoalString + "\n\n");
        sb.append("[GOAL OBJECT]\n   " + oGoalObjectString + "\n\n");
        sb.append("[GOAL SOURCE]\n   " + oGoalSource + "\n\n");
        sb.append("[IMPORTANCE/PLEASURELEVEL]\n   " + oAffectLevel + "\n\n");
        sb.append("[GOAL CONDITIONS]\n   " + oGoalConditions + "\n\n");
        sb.append("[SUPPORTIVE DATASTRUCTURE]\n   " + oSupportiveDataStructureString + "\n");
        sb.append("============================================================================================\n");
        
        oResult = sb.toString();
        
        return oResult;
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
        send_I6_11(moActionCommand);

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 18.05.2010, 17:58:21
     * 
     * @see pa.interfaces.send.I7_4_send#send_I7_4(java.util.ArrayList)
     */
    @Override
    public void send_I6_11(clsWordPresentationMesh poActionCommands) {
        ((I6_11_receive) moModuleList.get(30)).receive_I6_11(poActionCommands);
        ((I6_11_receive) moModuleList.get(47)).receive_I6_11(poActionCommands);
        //((I6_11_receive) moModuleList.get(52)).receive_I6_11(poActionCommands);

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
        double rNUM_INPUT_ACTIONS = 1;
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
