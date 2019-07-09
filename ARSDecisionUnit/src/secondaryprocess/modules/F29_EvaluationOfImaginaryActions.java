/**
 * E29_EvaluationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 */
package secondaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import general.datamanipulation.PrintTools;
import inspector.interfaces.itfInspectorAdvancedStackedBarChart;
import inspector.interfaces.itfInspectorGenericActivityTimeChart;
import logger.clsLogger;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentationMesh;
//import base.datatypes.clsAssociation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshGoal;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.logging.DataCollector;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.ElementNotFoundException;
import base.tools.clsSingletonAnalysisAccessor;
import base.tools.toText;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eActivationType;
import memorymgmt.enums.eCondition;
//import memorymgmt.enums.eContentType;
import memorymgmt.enums.eGoalType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I6_10_receive;
import modules.interfaces.I6_11_receive;
import modules.interfaces.I6_11_send;
import modules.interfaces.I6_2_receive;
import modules.interfaces.eInterfaces;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.functionality.PlanningFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author deutsch 11.08.2012, 14:57:10
 * 
 */
public class F29_EvaluationOfImaginaryActions extends clsModuleBaseKB implements I6_2_receive, I6_10_receive, I6_11_send,
        itfInspectorGenericActivityTimeChart, itfInspectorAdvancedStackedBarChart {
    public static final String P_MODULENUMBER = "29";

    private static final String P_MODULE_STRENGTH = "MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY = "INITIAL_REQUEST_INTENSITY";

    public static final String P_WAIT_THRESHOLD = "WAIT_THRESHOLD";
    public static final String P_INTERACTION_DEBUG  = "INTERACTION_DEBUG";
    
    private static Logger moLearningLogger = clsLogger.getLog("Learning");
    private static Logger logFim = clsLogger.getLog("Fim");
    
    ArrayList<String> moArrayFeelingsInMoments = new ArrayList<String>();
    HashMap<String, ArrayList<clsWordPresentationMeshFeeling>> moArrayFeelingsInMomentsMap = new HashMap<>();
    ArrayList<clsWordPresentationMeshFeeling> moArrayFeelingsInMomentsArray = new ArrayList<>();
    HashMap<String, ArrayList<clsWordPresentationMeshFeeling>> moCurrentMoment = new HashMap<>();
    String moCurrentMoment_String;
    String moCurrentMoments_String;
    private double mrWaitThreshold;
    private boolean mbInteractionDebug;
    
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;

    private ArrayList<String> moTEMPWriteLastActions = new ArrayList<String>();

    private ArrayList<clsWordPresentationMeshPossibleGoal> moSelectableGoals;
    private clsWordPresentationMesh moActionCommand;

    // Anxiety from F20
    private ArrayList<clsWordPresentationMeshFeeling> moAnxiety_Input;

    private clsWordPresentationMesh moPerceptionalMesh_IN;

    private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;

    private clsEnvironmentalImageMemory moEnvironmentalImageStorage;

    private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;

    private final DecisionEngine moDecisionEngine;

    private String moTEMPDecisionString = "";

    private clsWordPresentationMesh moWordingToContext;
    public static clsWordPresentationMesh moAction;
    public static clsThingPresentationMesh moTPM_Action;
    public static clsThingPresentationMesh moTPM_Object;
    
    private clsShortTermMemoryMF moSTM_Learning;

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
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory,
            clsEnvironmentalImageMemory poTempLocalizationStorage, DecisionEngine decisionEngine, DT3_PsychicIntensityStorage poPsychicEnergyStorage,
            clsShortTermMemoryMF poSTM_Learning,
            clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);

        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F29", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity = poPersonalityParameterContainer.getPersonalityParameter("F29", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.mbInteractionDebug = poPersonalityParameterContainer.getPersonalityParameter("F29", P_INTERACTION_DEBUG).getParameterBoolean();
        if(mbInteractionDebug)
            this.mrWaitThreshold = poPersonalityParameterContainer.getPersonalityParameter("F29", P_WAIT_THRESHOLD).getParameterDouble();
        else
            this.mrWaitThreshold = 0.0;
        
        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);

        applyProperties(poPrefix, poProp);

        this.moShortTermMemory = poShortTermMemory;
        this.moEnvironmentalImageStorage = poTempLocalizationStorage;
        this.moDecisionEngine = decisionEngine;
        
        moSTM_Learning = poSTM_Learning;

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
        text += moCurrentMoment_String;
        text += moCurrentMoments_String;
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
    // @Override
    // public void receive_I6_9(ArrayList<clsWordPresentationMesh> poActionCommands) {
    // //moActionCommands_Input = (ArrayList<clsWordPresentationMesh>) deepCopy(poActionCommands);
    // //INFORMATION: DEEPCOPY REMOVED
    // moActionCommands_Input = poActionCommands;
    // //moPerceptionalMesh_IN = poEnvironmentalPerception;
    // }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 27.04.2010, 10:43:32
     * 
     * @see pa.interfaces.I7_6#receive_I7_6(java.util.ArrayList)
     */
    @Override
    public void receive_I6_10(ArrayList<clsWordPresentationMeshPossibleGoal> selectableGoals, clsWordPresentationMesh moWordingToContext2) {
        moSelectableGoals = selectableGoals;
        moWordingToContext = moWordingToContext2;
    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 11.08.2009, 16:16:46
     * 
     * @see pa.modules.clsModuleBase#process()
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void process_basic()
    {
        clsWordPresentationMesh oWaitAction = null;
        
        log.debug("=== module {} start ===", this.getClass().getName());

        // Save old and new PlanGoal to detect Goal Change
        clsWordPresentationMeshPossibleGoal OldPlanGoal;
        clsWordPresentationMeshPossibleGoal NewPlanGoal = null;
        // Save Plan Goal
        OldPlanGoal = this.moDecisionEngine.getPlanGoal(moSelectableGoals);
        // Select the best goal
        // Delete previous plan goals
        try {
            this.moDecisionEngine.removeGoalAsPlanGoal(moSelectableGoals);
        } catch (ElementNotFoundException e2) {
            log.error("Cannot remove conditions", e2);
        }

        clsWordPresentationMeshPossibleGoal planGoal = GoalHandlingFunctionality.selectPlanGoal(moSelectableGoals);
        
//Old!!        if ( !planGoal.isEquivalentDataStructure(OldPlanGoal)
//           )
//        {
//            // Create new start Emotion for Act
//        }
        if(!(planGoal.getSupportiveDataStructure().isEquivalentDataStructure((OldPlanGoal.getSupportiveDataStructure()))))
        {
            // Add new emotion 
            if (clsActTools.checkIfConditionExists(OldPlanGoal, eCondition.SET_FOLLOW_ACT))
//              || OldPlanGoal.isEquivalentDataStructure(clsWordPresentationMeshPossibleGoal.getNullObject()))
           {
               // Set Abbort emotion --> Unpleasure to last image and/or to intention
               // Or set no Emotion to the aborted image?
               NewPlanGoal = OldPlanGoal;
           }
        }

        if(planGoal.getTotalImportance() < mrWaitThreshold) { 
            //Create WAIT goal
            planGoal = clsGoalManipulationTools.createSelectableGoal("WAIT", eGoalType.NULLOBJECT, -1,clsMeshTools.getNullObjectWPM());
            
            //Create WAIT action
            oWaitAction = clsActionTools.createAction(eAction.WAIT);
            
            //Set WAIT action as plan action
            planGoal.setAssociatedPlanAction(oWaitAction);
            
            //Select the WAIT goal
            planGoal.setCondition(eCondition.IS_CONTINUED_GOAL);
        } 
        
            
        logger.clsLogger.getLog("EmotionRange").info("Emotion Match on plangoal: {}", planGoal.getFeelingsMatchImportance());
        
        ArrayList<clsWordPresentationMeshFeeling> moFeeling_IN_F26_before = (ArrayList<clsWordPresentationMeshFeeling>) F26_DecisionMaking.moFeeling_IN.clone();
        ArrayList<clsWordPresentationMeshFeeling> moFeeling_IN_F26 = new ArrayList<>();

        for(clsWordPresentationMeshFeeling Feeling:moFeeling_IN_F26_before)
        {
            if (Feeling.getContent().toString().equals("ANXIETY"))
            {
                moFeeling_IN_F26.add(Feeling);
            }
        }
        for(clsWordPresentationMeshFeeling Feeling:moFeeling_IN_F26_before)
        {
            if (!Feeling.getContent().toString().equals("ANXIETY"))
            {
                moFeeling_IN_F26.add(Feeling);
            }
        }
        //ArrayList<String> moArrayFeelingsInMoments = new ArrayList<String>();
        
        try {
            this.moDecisionEngine.declareGoalAsPlanGoal(planGoal);
        } catch (Exception e1) {
            log.error("Cannot declare goal as plan goal", e1);
        }
        clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(planGoal.getSupportiveDataStructure());
        int Steps = clsShortTermMemoryMF.getActualStep();
        DecimalFormat format = new DecimalFormat("##00000.##");
        if (oMoment.isNullObject()==false) {
            moArrayFeelingsInMoments.add("\nStep: "+format.format(Steps)+" MOMENT: " + oMoment.getContent());
        }
        if (!moArrayFeelingsInMomentsMap.containsKey(oMoment.getContent()))
        {
            for(int i=0; i < moFeeling_IN_F26.size(); i++)
            {
                moFeeling_IN_F26.get(i).setCounter(1);
            }
            moArrayFeelingsInMomentsMap.put(oMoment.getContent(), moFeeling_IN_F26);   
        }
        else if(oMoment.isNullObject()==false)
        {
            ArrayList<clsWordPresentationMeshFeeling> oOldFeelings =  moArrayFeelingsInMomentsMap.get(oMoment.getContent());
            //Compare the feelings
            try
            {
                for (clsWordPresentationMeshFeeling oOldFeeling : oOldFeelings)
                {
            
                    clsWordPresentationMeshFeeling oCurrentFeeling1 = null;
                    boolean found=false;
                    for (clsWordPresentationMeshFeeling oCurrentFeeling : moFeeling_IN_F26)
                    {
                        if(oOldFeeling.getContent().contentEquals(oCurrentFeeling.getContent()))
                        {
                            oOldFeeling.setCounter(oOldFeeling.getCounter()+1);
                            oOldFeeling.setIntensity(((oOldFeeling.getIntensity() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getIntensity())/oOldFeeling.getCounter()));
                            oOldFeeling.setAggression((((oOldFeeling.getAggression() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getAggression())/oOldFeeling.getCounter())));
                            oOldFeeling.setLibido((((oOldFeeling.getLibido() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getLibido())/oOldFeeling.getCounter())));
                            oOldFeeling.setPleasure((((oOldFeeling.getPleasure() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getPleasure())/oOldFeeling.getCounter())));
                            oOldFeeling.setUnpleasure((((oOldFeeling.getUnpleasure() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getUnpleasure())/oOldFeeling.getCounter())));
                            found = true;
                        }
                        oCurrentFeeling1 = oCurrentFeeling;
                    }
                    if(found == false)
                    {
                    //    oOldFeelings.add(oCurrentFeeling1);
                    }
              
                }
            }
            catch (Exception e)
            {
                log.error("", e);
            }
            moArrayFeelingsInMomentsMap.put(oMoment.getContent(),oOldFeelings);
        }
        clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(planGoal.getSupportiveDataStructure());

        if (!moArrayFeelingsInMomentsMap.containsKey(oIntention.getContent()))
        {
            for(int i=0; i < moFeeling_IN_F26.size(); i++)
            {
                moFeeling_IN_F26.get(i).setCounter(1);
            }
            moArrayFeelingsInMomentsMap.put(oIntention.getContent(), moFeeling_IN_F26);   
        }
        else
        {
            ArrayList<clsWordPresentationMeshFeeling> oOldFeelings =  moArrayFeelingsInMomentsMap.get(oIntention.getContent());
            //Compare the feelings
            try {
            for (clsWordPresentationMeshFeeling oOldFeeling : oOldFeelings) {
                clsWordPresentationMeshFeeling oCurrentFeeling1 = null;
                boolean found=false;
                for (clsWordPresentationMeshFeeling oCurrentFeeling : moFeeling_IN_F26) {
                    if(oOldFeeling.getContent().contentEquals(oCurrentFeeling.getContent()))
                    {
                        oOldFeeling.setCounter(oOldFeeling.getCounter()+1);
                        oOldFeeling.setIntensity(((oOldFeeling.getIntensity() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getIntensity())/oOldFeeling.getCounter()));
                        oOldFeeling.setAggression((((oOldFeeling.getAggression() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getAggression())/oOldFeeling.getCounter())));
                        oOldFeeling.setLibido((((oOldFeeling.getLibido() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getLibido())/oOldFeeling.getCounter())));
                        oOldFeeling.setPleasure((((oOldFeeling.getPleasure() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getPleasure())/oOldFeeling.getCounter())));
                        oOldFeeling.setUnpleasure((((oOldFeeling.getUnpleasure() * (oOldFeeling.getCounter()-1) + oCurrentFeeling.getUnpleasure())/oOldFeeling.getCounter())));
                        found = true;
                    }
                    oCurrentFeeling1 = oCurrentFeeling;
                }
                if(found == false)
                {
                //    oOldFeelings.add(oCurrentFeeling1);
                }
            }
            }
            catch (Exception e)
            {
                log.error("", e);
            }
            moArrayFeelingsInMomentsMap.put(oIntention.getContent(),oOldFeelings);
        }
        
        //Better readable String
        String ouput="";
        for (String key :moArrayFeelingsInMomentsMap.keySet())
        {
            if(!(key.equals("NULLOBJECT")))
            {   
                ouput+= "\n";
                for(int i = key.length()-1; i < 30;i++)
                {
                    ouput+=" ";    
                }
                ouput+= key+"=";
                ouput+= moArrayFeelingsInMomentsMap.get(key);
            }
        }
        log.info("moArrayFeelingsInMomentsMap: "+ouput);
        log.info("moArrayFeelingsInMomentsMap: "+moArrayFeelingsInMomentsMap.toString());
        
        
        //logFim.info("moArrayFeelingsInMoments: "+moArrayFeelingsInMomentsMap.toString());
        //logFim.info("moArrayFeelingsInMoments: "+moArrayFeelingsInMoments.toString());
        
        moArrayFeelingsInMoments.add("FEELINGS::" + moFeeling_IN_F26.toString());
        
        log.info("moArrayFeelingsInMoments: "+moArrayFeelingsInMoments.toString());
        
        
        moLearningLogger.debug("\nLEARNING: {}",moArrayFeelingsInMomentsMap.toString());
        moCurrentMoment.put(oMoment.getContent(),moArrayFeelingsInMomentsMap.get(oMoment.getContent()));
        moCurrentMoment_String ="*** ACTUAL MOMENT ***\n";
        
        clsThingPresentationMesh oMomentTPM = clsMeshTools.getPrimaryDataStructureOfWPM(oMoment);
        
       for(clsThingPresentationMesh ImageSTM : this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningImage())
       {
            if(  oMomentTPM.compareTo(ImageSTM) == 1.0)
            {
                    ImageSTM.setCriterionActivationValue(eActivationType.MOMENT_ACTIVATION, ImageSTM.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION) + 1.0);
            }
        }
        
        ArrayList<clsWordPresentationMeshFeeling> List = moArrayFeelingsInMomentsMap.get(oMoment.getContent());
        moCurrentMoment_String += oMoment.getContent() +"\n";
        for(clsWordPresentationMeshFeeling ListElement:List)
        {
            moCurrentMoment_String += ListElement +"\n";
        }
        
        moCurrentMoment.put(oMoment.getContent(),moArrayFeelingsInMomentsMap.get(oMoment.getContent()));
        moCurrentMoments_String ="*** ALL MOMENTS ***\n";
        for (@SuppressWarnings("rawtypes") Map.Entry e:moArrayFeelingsInMomentsMap.entrySet()) {
         
            List = moArrayFeelingsInMomentsMap.get(e.getKey());
            moCurrentMoments_String += e.getKey().toString() +"\n";
            for(clsWordPresentationMeshFeeling ListElement:List)
            {
                moCurrentMoments_String += ListElement +"\n";
            }
        }
       
        
        
        log.debug("Selectable goals: {}", PrintTools.printArrayListWithLineBreaks(this.moSelectableGoals));
        //log.info("\n+++++++++++++++++++++++++++++\n Feelings in Moments: " + moArrayFeelingsInMoments + "\n++++++++++++++++++++++++++++++");
        log.info("\n=======================\nDecided goal: " + planGoal + "\nSUPPORTIVE DATASTRUCTURE: "
                + planGoal.getSupportiveDataStructure().toString() + "\n==============================");
        this.moTEMPDecisionString = setDecisionString(planGoal);
        
        // Get action command from goal
        try {
            moActionCommand = PlanningFunctionality.getActionCommandFromPlanGoal(planGoal);
            log.info("Selected Action: {}", moActionCommand);

        } catch (Exception e) {
            log.error("", e);
        }
        if(this.moSTM_Learning.moShortTermMemoryMF.get(0).getStep()>450)
        {
            for( clsWordPresentationMeshPossibleGoal reachableGoal:moSelectableGoals)
            {
                if(reachableGoal.getSupportiveDataStructure().getContent().equals("A06_BEAT_DOUG_L01"))
                {
                    moActionCommand.setContent("EAT");
                }
            }
        }

        // Goal to STM
        // The action is already saved within the goal
        ShortTermMemoryFunctionality.addContinuedGoalsToMentalSituation(moSelectableGoals, this.moShortTermMemory);
        ShortTermMemoryFunctionality.setPlanGoalInMentalSituation(planGoal, moShortTermMemory);

        // Add action
        ShortTermMemoryFunctionality.setExecutedAction(this.moShortTermMemory, moActionCommand);

        // Add text to inspector
        addTextToLastActionsTextSequence(moActionCommand, planGoal);
        eAction selectedAction = eAction.valueOf(clsActionTools.getAction(moActionCommand));
        if (selectedAction.equals(eAction.NONE.toString()) == true) {
            log.warn("Erroneous action taken. Action cannot be NONE. This must be an error in the codelets");
        }

        clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).putFinalGoals(moSelectableGoals);
        clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).putAction(selectedAction.toString());

        double rRequestedPsychicIntensity = 0.0;

        double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);

        double rConsumedPsychicIntensity = rReceivedPsychicEnergy;

        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);

        for (clsWordPresentationMeshPossibleGoal oGoal : moSelectableGoals) {
            DataCollector.goal(oGoal).putGoalF29(oGoal);
        }
        
        DataCollector.goal(moSelectableGoals.get(0)).finish();
        moAction = moSelectableGoals.get(0).getAssociatedPlanAction();
        try
        {
            if (  ( planGoal.getInternalAssociatedContent().size() > 0)
               && !(((clsWordPresentationMesh)planGoal.getInternalAssociatedContent().get(0).getAssociationElementB()).isNullObject())
               )
            {
                for(int i=0;i< ((clsThingPresentationMesh) ((clsWordPresentationMesh)planGoal.getInternalAssociatedContent().get(0).getAssociationElementB()).getExternalAssociatedContent().get(0).getAssociationElementB()).getExternalAssociatedContent().size();i++)
                {
                    clsDriveMesh DM;
                    DM = (clsDriveMesh)((clsThingPresentationMesh) ((clsWordPresentationMesh)planGoal.getInternalAssociatedContent().get(0).getAssociationElementB()).getExternalAssociatedContent().get(0).getAssociationElementB()).getExternalAssociatedContent().get(i).getAssociationElementA();
                    String moActionString = moAction.getContent();
                    String moDMString = DM.getActualDriveAim().getContent();
                    if(moActionString == moDMString)
                    {
                        moTPM_Action = DM.getActualDriveAim();
                        moTPM_Object = (clsThingPresentationMesh) ((clsWordPresentationMesh)planGoal.getInternalAssociatedContent().get(0).getAssociationElementB()).getExternalAssociatedContent().get(0).getAssociationElementB();
                    }
                    else
                    {
                        moActionString = null;
                    }
                }
            }

        }
        catch (Exception e)
        {
//            log.error("", e);
        }
//            ((clsThingPresentationMesh)moReachableGoalList_IN.get(i).getGoalObject().getAssociationWPOfWPM().getAssociationElementB()).getAggregatedActivationValue();

        //final Logger logFim = logger.clsLogger.getLog("Fim");
        //logFim.info("\n"+setDecisionString(planGoal));
        // //=== TEST ONLY ONE ACTION === //
        // if (clsTester.getTester().isActivated()) {
        // try {
        // eAction poReplaceAction = eAction.STRAFE_LEFT;
        // clsTester.getTester().exeTestAction(moActionCommands_Output, poReplaceAction);
        // log.warn("In test mode the action " + moActionCommands_Output + " was changed to " + poReplaceAction);
        // } catch (Exception e) {
        // log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
        // }
        // }

        // Get the last 10 goals and actions for the inspector
        // clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
        // clsWordPresentationMesh oGoal = clsMentalSituationTools.getGoal(oCurrentMentalSituation);

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

        // if (moGoalList_OUT.isEmpty()==false) {
        oAction = actionMesh.getContent();
        // }

        if (moTEMPWriteLastActions.size() == 10) {
            moTEMPWriteLastActions.remove(0);
        }

        // Get System time
        Calendar oCal = Calendar.getInstance();
        SimpleDateFormat oDateFormat = new SimpleDateFormat("HH:mm:ss");
        moTEMPWriteLastActions.add(oDateFormat.format(oCal.getTime()) + "> " + "Goal: " + planGoal.getContent().toString() + ":"
                + planGoal.getSupportiveDataStructure().getContent() + "; Action: " + oAction);
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

        // Get the Goal String
        String oGoalString = poDecidedGoal.getGoalName();

        // Get the goal object
        String oGoalObjectString = poDecidedGoal.getGoalObject().toString();

        // Get the Goal source
        String oGoalSource = "NONE";
        if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
            oGoalSource = "DRIVES (Drive goal not found in perception or acts)";
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
            oGoalSource = "ACT";
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
            oGoalSource = "PERCEPTION";
        }

        // Get the AffectLevel
        String oAffectLevel = String.valueOf(poDecidedGoal.getTotalImportance());
        // eAffectLevel.convertQuotaOfAffectToAffectLevel(clsGoalTools.getAffectLevel(poDecidedGoal)).toString();

        // Get Conditions
        String oGoalConditions = "";
        ArrayList<eCondition> oConditionList = poDecidedGoal.getCondition();
        for (eCondition oC : oConditionList) {
            oGoalConditions += oC.toString() + "; ";
        }

        // Get the Supportive DataStructure
        String oSupportiveDataStructureString = "";
        if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
        } else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
            oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
        }

        StringBuilder sb = new StringBuilder();

        // Set the current decision string
        //sb.append("============================================================================================\n");
        sb.append("[GOAL NAME]:   " + oGoalString + "\n");
        sb.append("[GOAL OBJECT]: " + oGoalObjectString + "\n");
        sb.append("[GOAL SOURCE]: " + oGoalSource + "\n");
        sb.append("[IMPORTANCE/PLEASURELEVEL]: " + oAffectLevel + "\n");
        sb.append("[GOAL CONDITIONS]:          " + oGoalConditions + "\n");
        sb.append("[SUPPORTIVE DATASTRUCTURE]: " + oSupportiveDataStructureString);
        //sb.append("============================================================================================\n");

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
        send_I6_11(moActionCommand, moWordingToContext);

    }

    /*
     * (non-Javadoc)
     * 
     * @author deutsch 18.05.2010, 17:58:21
     * 
     * @see pa.interfaces.send.I7_4_send#send_I7_4(java.util.ArrayList)
     */
    @Override
    public void send_I6_11(clsWordPresentationMesh poActionCommands, clsWordPresentationMesh moWordingToContext2) {
        ((I6_11_receive) moModuleList.get(30)).receive_I6_11(poActionCommands, moWordingToContext2);
        ((I6_11_receive) moModuleList.get(47)).receive_I6_11(poActionCommands, moWordingToContext2);
        // ((I6_11_receive) moModuleList.get(52)).receive_I6_11(poActionCommands);

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

    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }
   
    /*
     * (non-Javadoc)
     * 
     * @since 20.05.2014
     * 
     * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartTitle()
     */
    @Override
    public String getStackedBarChartTitle() {
        return "Goal evaluation";
    }

    /*
     * (non-Javadoc)
     * 
     * @since 20.05.2014
     * 
     * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartData()
     */
    @Override
    public ArrayList<ArrayList<Double>> getStackedBarChartData() {
        ArrayList<ArrayList<Double>> oData = new ArrayList<>();
        oData.add(new ArrayList<Double>()); // idx 0 = Drive Demand Importance
        oData.add(new ArrayList<Double>()); // idx 1 = Feelings Match Importance
        oData.add(new ArrayList<Double>()); // idx 2 = Feelings Expectation Importance
        oData.add(new ArrayList<Double>()); // idx 3 = Drive Demand Correction Importance
        oData.add(new ArrayList<Double>()); // idx 4 = Effort Impact Importance
        oData.add(new ArrayList<Double>()); // idx 5 = Drive Aim Importance
        oData.add(new ArrayList<Double>()); // idx 6 = Social Rules Importance
        oData.add(new ArrayList<Double>()); // idx 7 = Entities Emotion Valuation Match Importance
        oData.add(new ArrayList<Double>()); // idx 8 = Entities Bodystate Match Importance
        oData.add(new ArrayList<Double>()); // idx 9 = Unknown influence
        
        double rTotalImportance = 0;
        double rTempDriveDemandImportance = 0;
        double rTempFeelingMatchImportance = 0;
        double rTempFeelingExpectationImportance = 0;
        double rTempImportanceSum = 0;
        
        for (clsWordPresentationMeshPossibleGoal oGoal : moSelectableGoals) {
            rTotalImportance = 0;
            
            //Drive demand and feelings importance are combined via non-proportional aggregation, therefore we divide the
            //PP impact factor, according to the relation between drive demand impact and feelings impact
            rTempImportanceSum = oGoal.getDriveDemandImportance() +  oGoal.getFeelingsMatchImportance() + oGoal.getFeelingsExcpactationImportance();
            rTempDriveDemandImportance = oGoal.getPPImportance() * (oGoal.getDriveDemandImportance() / rTempImportanceSum);
            rTempFeelingMatchImportance = oGoal.getPPImportance() * (oGoal.getFeelingsMatchImportance() / rTempImportanceSum);
            rTempFeelingExpectationImportance = oGoal.getPPImportance() * (oGoal.getFeelingsExcpactationImportance() / rTempImportanceSum);
            
            oData.get(0).add(rTempDriveDemandImportance);
            rTotalImportance += rTempDriveDemandImportance;
            oData.get(1).add(rTempFeelingMatchImportance);
            rTotalImportance += rTempFeelingMatchImportance;
            oData.get(2).add(rTempFeelingExpectationImportance);
            rTotalImportance += rTempFeelingExpectationImportance;
            oData.get(3).add(oGoal.getDriveDemandCorrectionImportance());
            rTotalImportance += oGoal.getDriveDemandCorrectionImportance();
            oData.get(4).add(oGoal.getEffortImpactImportance());
            rTotalImportance += oGoal.getEffortImpactImportance();
            oData.get(5).add(oGoal.getDriveAimImportance());
            rTotalImportance += oGoal.getDriveAimImportance();
            oData.get(6).add(oGoal.getSocialRulesImportance());
            rTotalImportance += oGoal.getSocialRulesImportance();
            oData.get(7).add(oGoal.getEntityValuationImportance());
            rTotalImportance += oGoal.getEntityValuationImportance();
            oData.get(8).add(oGoal.getEntityBodystateImportance());
            rTotalImportance += oGoal.getEntityBodystateImportance();
            oData.get(9).add(oGoal.getTotalImportance() - rTotalImportance);
        }

        return oData;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 20.05.2014
     * 
     * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartCategoryCaptions()
     */
    @Override
    public ArrayList<String> getStackedBarChartCategoryCaptions() {
        ArrayList<String> oResult = new ArrayList<String>();

        oResult.add("Drive Demand Importance");
        oResult.add("Feelings Match Importance");
        oResult.add("Feelings Expactation Importance");
        oResult.add("Drive Demand Correction Importance");
        oResult.add("Effort Impact Importance");
        oResult.add("Drive Aim Importance");
        oResult.add("Social Rules Importance");
        oResult.add("Emotion Valuation Match Importance");
        oResult.add("Bodystate Match Importance");
        oResult.add("Unknown factors");
        
        return oResult;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 20.05.2014
     * 
     * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartColumnCaptions()
     */
    @Override
    public ArrayList<String> getStackedBarChartColumnCaptions() {
        String oIdentifier = new String();
        String oPostfix = "";
        int nCount = 2;
        
        ArrayList<String> oResult = new ArrayList<String>();
        for (clsWordPresentationMeshPossibleGoal oGoal : moSelectableGoals) {
            oIdentifier = oGoal.getSupportiveDataStructure().getContent() + ":" + oGoal.getGoalContentIdentifier();
            while(oResult.contains(oIdentifier + oPostfix)) {
                oPostfix = new String("_") + Integer.toString(nCount++);
            }
            
            oResult.add(oIdentifier + oPostfix); 
        }
        
        return oResult;
    }

//    /* (non-Javadoc)
//     *
//     * @since 19.02.2015 13:07:44
//     * 
//     * @see inspector.interfaces.itfInspectorStackedAreaChart#getTitle()
//     */
//    @Override
//    public String getTitle() {
//        return "Eat evaluating EAT over time";
//    }
//
//    /* (non-Javadoc)
//     *
//     * @since 19.02.2015 13:07:44
//     * 
//     * @see inspector.interfaces.itfInspectorStackedAreaChart#getData()
//     */
//    @Override
//    public ArrayList<Double> getData() {
//        double rTotalImportance = 0;
//        double rTempDriveDemandImportance = 0;
//        double rTempFeelingImportance = 0;
//        
//        HashMap<String, ArrayList<Double>> oData = new HashMap<>();
//        ArrayList<Double> oEntry = null;
//        
//        for(clsWordPresentationMeshPossibleGoal oGoal : moSelectableGoals) {
//            rTotalImportance = 0;
//            oEntry = new ArrayList<Double>();
//            
//            //Drive demand and feelings importance are combined via non-proportional aggregation, therefore we divide the
//            //PP impact factor, according to the relation between drive demand impact and feelings impact
//            rTempDriveDemandImportance = oGoal.getPPImportance() * (oGoal.getDriveDemandImportance() / (oGoal.getDriveDemandImportance() +  oGoal.getFeelingsImportance()));
//            rTempFeelingImportance = oGoal.getPPImportance() * (oGoal.getFeelingsImportance() / (oGoal.getDriveDemandImportance() +  oGoal.getFeelingsImportance()));
//            
//            oEntry.add(rTempDriveDemandImportance);
//            rTotalImportance += rTempDriveDemandImportance;
//            oEntry.add(rTempFeelingImportance);
//            rTotalImportance += rTempFeelingImportance;
//            oEntry.add(oGoal.getDriveDemandCorrectionImportance());
//            rTotalImportance += oGoal.getDriveDemandCorrectionImportance();
//            oEntry.add(oGoal.getEffortImpactImportance());
//            rTotalImportance += oGoal.getEffortImpactImportance();
//            oEntry.add(oGoal.getDriveAimImportance());
//            rTotalImportance += oGoal.getDriveAimImportance();
//            oEntry.add(oGoal.getSocialRulesImportance());
//            rTotalImportance += oGoal.getSocialRulesImportance();
//            oEntry.add(oGoal.getTotalImportance() - rTotalImportance);
//            
//            oData.put(oGoal.getSupportiveDataStructure().getContent(), oEntry);
//        }
//
//        return oData.get("A12_EAT_CAKE_L01");
//    }
//
//    /* (non-Javadoc)
//     *
//     * @since 19.02.2015 13:07:44
//     * 
//     * @see inspector.interfaces.itfInspectorStackedAreaChart#getCategoryCaptions()
//     */
//    @Override
//    public ArrayList<String> getCategoryCaptions() {
//        ArrayList<String> oResult = new ArrayList<String>();
//
//        oResult.add("Drive Demand Importance");
//        oResult.add("Feelings Importance");
//        oResult.add("Drive Demand Correction Importance");
//        oResult.add("Effort Impact Importance");
//        oResult.add("Drive Aim Importance");
//        oResult.add("Social Rules Importance");
//        oResult.add("Unknown factors");
//        
//        return oResult;
//    }

}
