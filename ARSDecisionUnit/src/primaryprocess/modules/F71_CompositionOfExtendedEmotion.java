/**
 * CHANGELOG
 *
 * 23.02.2015 Kollmann - File created
 *
 */
package primaryprocess.modules;

import inspector.interfaces.itfInspectorCombinedTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import primaryprocess.functionality.superegofunctionality.clsSuperEgoRulesCheck;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictDrive;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_16_receive;
import modules.interfaces.I5_17_receive;
import modules.interfaces.I5_23_receive;
import modules.interfaces.I5_23_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.clsSingletonAnalysisAccessor;
import base.tools.toText;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 23.02.2015, 18:33:30
 * 
 */
public class F71_CompositionOfExtendedEmotion extends clsModuleBase implements I5_16_receive, I5_17_receive, I5_23_send, itfInspectorCombinedTimeChart {
    public static final String P_MODULENUMBER = "71";
    private static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
    private static final String P_SUPER_EGO_RULES_FILE = "SUPER_EGO_RULES_FILE";
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    private static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
     
    private clsWordPresentationMesh moWordingToContext = null;
    private ArrayList<clsEmotion> moEmotions_Input = new ArrayList<>();
    private ArrayList<clsEmotion> moEmotions_Output = new ArrayList<>();
    private ArrayList<clsPrimaryDataStructure> moAffectOnlyList_Input = new ArrayList<>();
    //Kollmann: I prefer using List as working type over ArrayList - I only use ArrayList as working type of an interface (or other demand)
    //          would force me to cast the List to ArrayList
    private ArrayList<clsDriveMesh> moDrives_Input = new ArrayList<>();
    private ArrayList<clsSuperEgoRulesCheck> moRules = new ArrayList<>();
    private clsThingPresentationMesh moPerceptionalMesh_Input = null;
    private double mrModuleStrength = 0;
    private double mrInitialRequestIntensity = 0;
    private double mrPsychicEnergyThreshold = 0;
    private DT3_PsychicIntensityStorage moPsychicEnergyStorage = null;
    
    double oSuperEgoStrength;
    String oFileName;
    
    public clsSuperEgoRulesCheck moSuperEgoRulesCheckInstance; 
    
    private List<clsSuperEgoConflictDrive> moForbiddenDrives = new ArrayList<>();
    private List<clsSuperEgoConflictPerception> moForbiddenPerceptions = new ArrayList<>();
    private List<clsSuperEgoConflictEmotion> moForbiddenEmotions = new ArrayList<>();
    private List<String> moSuperEgoOutputRules = new ArrayList <>();  
    
    double mrRemainingConflict = 0;
    
    /**
     * DOCUMENT - In F71 (Zusammenstellung von erweiterter Emotion) führen Abwehrmechanismen eine Färbung/Akzentuierung der Basisemotionen
     * durch oder assoziieren ein Zusatzattribut. Die konkrete Verarbeitung der Basisemotionen ist bei den spezifischen erweiterten Emotionen
     * unterschiedlich und abhängig von der Situation, der Über-Ich Regeln und der Basisemotionen.
     * Beispiele für erweiterte Emotionen sind Scham, Mitleid, Neid, Schuld.
     *
     * @author Kollmann
     * @since 23.02.2015 18:33:41
     *
     * @param poPrefix
     * @param poProp
     * @param poModuleList
     * @param poInterfaceData
     * @throws Exception
     */
    public F71_CompositionOfExtendedEmotion(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsPersonalityParameterContainer poPersonalityParameterContainer,
            DT3_PsychicIntensityStorage poPsychicEnergyStorage, int pnUid) throws Exception {        
        super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
        
        //Kollmann: this is a bit of a hack, I don't want to pass on the super ego rules so I load them again and for that I access F07s parameters
        //          for the filename and super-ego strength
        oSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_STRENGTH).getParameterDouble();
        oFileName = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_RULES_FILE).getParameter();
        
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_INITIAL_REQUEST_INTENSITY).getParameterDouble();
        mrPsychicEnergyThreshold = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_PSYCHIC_ENERGY_THESHOLD).getParameterDouble();

        moPsychicEnergyStorage = poPsychicEnergyStorage;
        moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
        
        moSuperEgoRulesCheckInstance = new clsSuperEgoRulesCheck();
       // moRules = clsReadSuperEgoRules.fromFile(oSuperEgoStrength, oFileName);
    }
    
    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see inspector.interfaces.itfInspectorInternalState#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        String text ="";
        
        text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
        text += "---------------------------------------------------------------------------------------------";
        text += toText.listToTEXT("moEmotions_Output", moEmotions_Output);
        text += "---------------------------------------------------------------------------------------------";
        text += toText.valueToTEXT("moForbiddenDrives", moForbiddenDrives);     
        text += toText.valueToTEXT("moForbiddenPerceptions", moForbiddenPerceptions);
        text += toText.valueToTEXT("moForbiddenEmotions", moForbiddenEmotions);
        text += "---------------------------------------------------------------------------------------------";
        text += toText.valueToTEXT("Remaining conflict", mrRemainingConflict);
        return text;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_23_send#send_I5_23(java.util.ArrayList, java.util.ArrayList, base.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void send_I5_23(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions,
            clsWordPresentationMesh moWordingToContext2) {
        ((I5_23_receive)moModuleList.get(20)).receive_I5_23(poAffectOnlyList, poEmotions, moWordingToContext2);
        putInterfaceData(I5_23_send.class, poAffectOnlyList, poEmotions, moWordingToContext2);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_17_receive#receive_I5_17(java.util.ArrayList)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsDriveMesh> poDriveList) {
        moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructure>)this.deepCopy(poAffectOnlyList);
        moDrives_Input = poDriveList;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_16_receive#receive_I5_16(java.util.ArrayList, java.util.ArrayList, base.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void receive_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions,
            clsWordPresentationMesh poWordingToContext2, clsThingPresentationMesh poPerceptionalMesh) {
        moWordingToContext = poWordingToContext2;
        moEmotions_Input = poEmotions;
        moPerceptionalMesh_Input = poPerceptionalMesh;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_basic()
     */
    

    
    @Override
    protected void process_basic() {
        double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
        double rConsumedPsychicIntensity = 0;
        clsEmotion oNewGuilt = null;
        mrRemainingConflict = 0;
        
        log.debug("neutralized intensity F71: " + Double.toString(rReceivedPsychicEnergy));

        moEmotions_Output = moEmotions_Input.get(0).generateExtendedEmotions();
        
        if (rReceivedPsychicEnergy > mrPsychicEnergyThreshold) {
            //rConsumedPsychicIntensity = checkInternalizedRules(rReceivedPsychicEnergy);   // check perceptions and drives, and apply internalized rules
            moSuperEgoRulesCheckInstance.setCheckingSuperEgoRuleParameters(moPerceptionalMesh_Input, moEmotions_Input, moDrives_Input);
            
            try {  
                rConsumedPsychicIntensity = moSuperEgoRulesCheckInstance.checkInternalizedRules(rReceivedPsychicEnergy, oSuperEgoStrength, oFileName);
                moSuperEgoOutputRules = moSuperEgoRulesCheckInstance.getSuperEgoOutputRules();
                moForbiddenDrives = moSuperEgoRulesCheckInstance.getForbiddenDrives();
                moForbiddenEmotions = moSuperEgoRulesCheckInstance.getForbiddenEmotions();
                moForbiddenPerceptions = moSuperEgoRulesCheckInstance.getForbiddenPerception();
            }  
            
            catch(Exception e) {
                log.error("Fail of reading super-ego files");
            }
            //TODO: create conflict base class and streamline this here 
            for(clsSuperEgoConflictDrive oConflict : moForbiddenDrives) {
                mrRemainingConflict += oConflict.getConflictTension();
            }
            for(clsSuperEgoConflictPerception oConflict : moForbiddenPerceptions) {
                mrRemainingConflict += oConflict.getConflictTension();
            }
            for(clsSuperEgoConflictEmotion oConflict : moForbiddenEmotions) {
                mrRemainingConflict += oConflict.getConflictTension();
            }
            
            if(mrRemainingConflict > 0) {
                oNewGuilt = generateGuilt(mrRemainingConflict);
                moEmotions_Output.add(oNewGuilt);
                clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).putFactor("GuiltValue", Double.toString(oNewGuilt.getEmotionIntensity()));
            } else {
                clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).putFactor("GuiltValue", "no_conflict");
            }
        } else {
            clsSingletonAnalysisAccessor.getAnalyzerForGroupId(getAgentIndex()).putFactor("GuiltValue", "no_energy");
        }
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, mrInitialRequestIntensity, rConsumedPsychicIntensity);
    }
    
    private clsEmotion generateGuilt(double prConflictIntensity) {
        //Kollmann: not sure how to generate guilt, but I think it should have some unpleasure component, I, for now, use the same pattern as with ANXIETY
        return clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, eEmotionType.GUILT, prConflictIntensity),  0,  prConflictIntensity,  0,  0);
    }
    
    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_draft()
     */
    @Override
    protected void process_draft() {
        // TODO (Kollmann) - Auto-generated method stub

    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_final()
     */
    @Override
    protected void process_final() {
        // TODO (Kollmann) - Auto-generated method stub

    }

    
    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        send_I5_23(moAffectOnlyList_Input, moEmotions_Output, moWordingToContext);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setProcessType()
     */
    @Override
    protected void setProcessType() {
        mnProcessType = eProcessType.PRIMARY;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setPsychicInstances()
     */
    @Override
    protected void setPsychicInstances() {
        mnPsychicInstances = ePsychicInstances.ID;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setModuleNumber()
     */
    @Override
    protected void setModuleNumber() {
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setDescription()
     */
    @Override
    public void setDescription() {
        moDescription = "In F71 (Zusammenstellung von erweiterter Emotion) führen Abwehrmechanismen eine Färbung/Akzentuierung der Basisemotionen "
                + "durch oder assoziieren ein Zusatzattribut. Die konkrete Verarbeitung der Basisemotionen ist bei den spezifischen erweiterten Emotionen "
                + "unterschiedlich und abhängig von der Situation, der Über-Ich Regeln und der Basisemotionen.\n"
                + "Beispiele für erweiterte Emotionen sind Scham, Mitleid, Neid, Schuld.";
    }

    /*************************************************************/
    /***              COMBINED TIME CHART METHODS              ***/
    /*************************************************************/
    
    /* (non-Javadoc)
     *
     * @since Oct 2, 2012 1:31:29 PM
     * 
     * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartAxis()
     */
    @Override
    public String getCombinedTimeChartAxis() {
        return "";
    }

    /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartData()
    */
   @Override
   public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
       ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
       //EMOTIONS
       ArrayList<Double> oAnger =new ArrayList<Double>();
       Double oAngerQoA= 0.0;
       
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ANGER)){
               oAngerQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oAnger.add(oAngerQoA);
       oResult.add(oAnger);
       
       
       ArrayList<Double> oFear =new ArrayList<Double>();
       Double oFearQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ANXIETY)){
               oFearQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oFear.add(oFearQoA);
       oResult.add(oFear);
       
       ArrayList<Double> oGrief =new ArrayList<Double>();
       Double oGriefQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.MOURNING)){
               oGriefQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oGrief.add(oGriefQoA);
       oResult.add(oGrief);
       
       ArrayList<Double> oLoveSa =new ArrayList<Double>();
       Double oLoveSaQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.SATURATION)){
               oLoveSaQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oLoveSa.add(oLoveSaQoA);
       oResult.add(oLoveSa);
       
       ArrayList<Double> oLoveEx =new ArrayList<Double>();
       Double oLoveExQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ELATION)){
               oLoveExQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oLoveEx.add(oLoveExQoA);
       oResult.add(oLoveEx);
       
       ArrayList<Double> oPleasure =new ArrayList<Double>();
       Double oPleasureQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.JOY)){
               oPleasureQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oPleasure.add(oPleasureQoA);
       oResult.add(oPleasure);
       
       ArrayList<Double> oGuilt =new ArrayList<Double>();
       double rGuiltIntensity= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.GUILT)){
               rGuiltIntensity = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oGuilt.add(rGuiltIntensity);
       oResult.add(oGuilt);
       
       return oResult;
   }


   /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
    */
   @Override
   public ArrayList<String> getChartTitles() {
       ArrayList<String> oResult = new ArrayList<String>();
       oResult.add(eEmotionType.ANGER.toString());
       oResult.add(eEmotionType.ANXIETY.toString());
       oResult.add(eEmotionType.MOURNING.toString());
       
       oResult.add(eEmotionType.SATURATION.toString());
       oResult.add(eEmotionType.ELATION.toString());
       oResult.add(eEmotionType.JOY.toString());
       
       oResult.add(eEmotionType.GUILT.toString());
       
       return oResult;
   }

   /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
    */
   @Override
   public ArrayList<ArrayList<String>> getValueCaptions() {
       ArrayList<ArrayList<String>> oResult = new ArrayList<ArrayList<String>>();
       
       //Emotions
       
       //ChartAnger
       ArrayList<String> chartAnger = new ArrayList<String>();
       chartAnger.add("Emotion "+eEmotionType.ANGER.toString());
       oResult.add(chartAnger);
       
       //ChartFear
       ArrayList<String> chartFear = new ArrayList<String>();
       chartFear.add("Emotion "+eEmotionType.ANXIETY.toString());
       oResult.add(chartFear);
       
       //ChartGrief
       ArrayList<String> chartGrief = new ArrayList<String>();
       chartGrief.add("Emotion "+eEmotionType.MOURNING.toString());
       oResult.add(chartGrief);    
       
       //ChartLoveSaturation
       ArrayList<String> chartLoveSaturation = new ArrayList<String>();
       chartLoveSaturation.add("Emotion "+eEmotionType.SATURATION.toString());
       oResult.add(chartLoveSaturation);
       
       //ChartLoveexhilaration
       ArrayList<String> chartLoveExhilaration = new ArrayList<String>();
       chartLoveExhilaration.add("Emotion "+eEmotionType.ELATION.toString());
       oResult.add(chartLoveExhilaration); 
       
       //ChartPleasure
       ArrayList<String> chartPleasure= new ArrayList<String>();
       chartPleasure.add("Emotion "+eEmotionType.JOY.toString());
       oResult.add(chartPleasure); 
       
       //ChartGuilt
       ArrayList<String> chartGuilt= new ArrayList<String>();
       chartGuilt.add("Emotion "+eEmotionType.GUILT.toString());
       oResult.add(chartGuilt);
       
       return oResult;
   }
}
