/**
 * E14_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfGraphCompareInterfaces;
//import inspector.interfaces.itfInspectorForSTM;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import prementalapparatus.symbolization.eSymbolExtType;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import properties.clsProperties;
import testfunctions.clsTester;
import memorymgmt.enums.PsychicSpreadingActivationMode;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eActivationType;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEmotionExpression;
import memorymgmt.enums.eEntityExternalAttributes;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.storage.DT1_PsychicIntensityBuffer;
import memorymgmt.enums.eEmotionType;
import modules.interfaces.I2_3_receive;
import modules.interfaces.I2_4_receive;
import modules.interfaces.I2_6_receive;
import modules.interfaces.I2_6_send;
import modules.interfaces.I5_19_receive;
import modules.interfaces.I5_1_receive;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsActivationComperator;
import base.datahandlertools.clsDataStructureConverter;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationSpatial;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * In this module neurosymbolic contents are transformed into thing presentations. Now, sensor sensations originating in body and 
 * environment sensors can be processed by the mental functions. The generated thing presentations are associated among each others 
 * according to their temporal and spatial vicinity and likeness.<br><br>
 * 
 * <b>INPUT:</b><br>
 * <i>moEnvironmentalData</i> this holds the symbols from the environmental perception (IN I2.3)<br>
 * <i>moBodyData</i> this holds the symbols from the bodily perception (IN I2.4) <br>
 * 
 * <br>
 * <b>OUTPUT:</b><br>
 * <i>moEnvironmentalTP</i> OUT member of F14, this holds the to TP converted symbols of the two perception paths (OUT 2.6)<br>
 * 
 * @author muchitsch
 * 07.05.2012, 14:26:13
 * 
 */
public class F14_ExternalPerception extends clsModuleBaseKB implements 
					I2_3_receive, 
					I2_4_receive,
					I2_6_send,
					I5_1_receive,
					I5_19_receive,
					itfInspectorGenericDynamicTimeChart,
					itfGraphCompareInterfaces
//					, itfInspectorForSTM
					{
	public static final String P_MODULENUMBER = "14";
	
	/** this holds the symbols from the environmental perception (IN I2.3) @since 21.07.2011 11:37:01 */
	private HashMap<eSymbolExtType, itfSymbol> moEnvironmentalData;
	/** this holds the symbols from the bodily perception (IN I2.4)  @since 21.07.2011 11:37:06 */
	private HashMap<String, Double> moBodyData;
	/** OUT member of F14, this holds the converted symbols of the two perception paths and the recognized TPMs (OUT I2.6) @since 20.07.2011 10:26:23 */
	private ArrayList<clsThingPresentationMesh> moCompleteThingPresentationMeshList;
	
	private boolean mnChartColumnsChanged = true;
	//ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP;
	private HashMap<String, Double> moDriveChartData;
	ArrayList<String> Test = new ArrayList<String>();
	ArrayList<String> Test1 = new ArrayList<String>();
	/** Input from Drive System */
	private ArrayList<clsDriveMesh> moDrives_IN;
	private boolean useAttentionMechanism = false;
	double health_old=1.0;
	ArrayList<Double> upleasure_array = new ArrayList<Double>();
	
	private HashMap<String, String> composedActions = new HashMap<String, String>();
	
	ArrayList<clsThingPresentationMesh> moReturnedPhantasy_IN = new ArrayList<clsThingPresentationMesh>();
	List<clsEmotion> moCurrentEmotions = new ArrayList<>(); 
	
	//These two are pass-through parameters that will be sent to F46 without being used
	clsWordPresentationMesh moWordingToContext_IN = null;
    PsychicSpreadingActivationMode moPsychicSpreadingActivationMode_IN = PsychicSpreadingActivationMode.NONE;
    
	//private Logger log = Logger.getLogger(this.getClass());
	
	public static final String P_EMOTIONRECOGNITION_PRIMING_PLEASURE = "EMOTIONRECOGNITION_PRIMING_PLEASURE";//koller
    public static final String P_EMOTIONRECOGNITION_PRIMING_UNPLEASURE = "EMOTIONRECOGNITION_PRIMING_UNPLEASURE";
    public static final String P_EMOTIONRECOGNITION_PRIMING_AGGRESSION = "EMOTIONRECOGNITION_PRIMING_AGGRESSION";
    public static final String P_EMOTIONRECOGNITION_PRIMING_LIBIDO = "EMOTIONRECOGNITION_PRIMING_LIBIDO";
    public static final String P_EMOTIONRECOGNITION_PRIMING_INTENSITY = "EMOTIONRECOGNITION_PRIMING_INTENSITY";
    
    public static final int N_PROXIMITY_DISTANCE = 17; 
    
    private double mrEmotionrecognitionPrimingPleasure;
    private double mrEmotionrecognitionPrimingUnpleasure;
    private double mrEmotionrecognitionPrimingAggression;
    private double mrEmotionrecognitionPrimingLibido;
    private double mrEmotionrecognitionPrimingIntensity;
    
    private clsShortTermMemoryMF moSTM_Learning;
    
    //Kollmann: WORKAROUND: this is a helper instance that will hold a flat copy of the last used emotion state on the self
    //          It is a workaround for a bug in priming where the bodystate recognized on the self is, for some reason, not associated with
    //          an emotion - in that cases, use the last known emotion for priming
    private clsEmotion moFormerSelfBodystateEmotion = null;
    
    boolean boSelfHasExpressionVar = true; //true, if the self should have body expressions for bodystates
	
    private String moBodystateCreation = "";
    ArrayList<clsThingPresentationMesh> moSearchPattern; //kollmann: store this globally to have access to the search pattern for inspection
    
    private ArrayList<clsThingPresentationMesh> oCurrentActions = new ArrayList<clsThingPresentationMesh>();
    
    private DT1_PsychicIntensityBuffer moLibidoBuffer;
    
	/**
	 * Constructor of F14, nothing unusual
	 * 
	 * @author muchitsch
	 * 03.03.2011, 16:15:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F14_ExternalPerception(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT1_PsychicIntensityBuffer poLibidoBuffer, itfModuleMemoryAccess poMemory, clsShortTermMemoryMF poSTM_Learning, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory, pnUid);
		applyProperties(poPrefix, poProp);
		
		mrEmotionrecognitionPrimingPleasure =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_PRIMING_PLEASURE).getParameterDouble(); //koller
        mrEmotionrecognitionPrimingUnpleasure =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_PRIMING_UNPLEASURE).getParameterDouble();
        mrEmotionrecognitionPrimingAggression =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_PRIMING_AGGRESSION).getParameterDouble();
        mrEmotionrecognitionPrimingLibido =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_PRIMING_LIBIDO).getParameterDouble();
        mrEmotionrecognitionPrimingIntensity =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_EMOTIONRECOGNITION_PRIMING_INTENSITY).getParameterDouble();
        moSTM_Learning = poSTM_Learning;
        moLibidoBuffer = poLibidoBuffer;
		
		moDriveChartData =  new HashMap<String, Double>(); //initialize charts
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
		String text = "";
		
		text += toText.mapToTEXT("moBodyData", moBodyData);
		text += toText.listToTEXT("moCompleteThingPresentationMeshList", moCompleteThingPresentationMeshList);
		text += "--- this.moSTM_Learning.getLearningObjectsString() ----\n";
		text += this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjectsString();
		text += "---------------------------------------------------------------------------------------------\n";
		text += "Search pattern:\n";
		
		for(clsThingPresentationMesh oPattern : moSearchPattern) {
		    text += oPattern.getContent().toString() + ":\n";
		    for(clsAssociation oAssociation : oPattern.getInternalAssociatedContent()) {
		        text += oAssociation.getTheOtherElement(oPattern).toString() + "\n";
		    }
		}
		
		return text;
	}
	
	private void setGoToComposition() {
	    composedActions.put("TURN_LEFT", "GOTO");
	    composedActions.put("TURN_RIGHT", "GOTO");
	    composedActions.put("MOVE_FOREWARD", "GOTO");
	    composedActions.put("MOVE_FORWARD", "GOTO");
	    composedActions.put("MOVE_BACKWARD", "GOTO");

	    //composedActions.put("TURN_LEFT", "GOTO");
	    //composedActions.put("TURN_LEFT", "GOTO");
	    
	}
	
	public String debugBodystate(clsThingPresentationMesh poBodystate) {
	    String oText = "Bodystate";
	    clsDataStructurePA oOtherElement = null;
	    clsEmotion oEmotion = null;
	    clsThingPresentationMesh oBodystateSource = null;
	    
	    for(clsAssociationAttribute oAssAttribute : clsAssociation.filterListByType(poBodystate.getInternalAssociatedContent(), clsAssociationAttribute.class)) {
	        oOtherElement = oAssAttribute.getTheOtherElement(poBodystate);
	        if(oOtherElement instanceof clsEmotion) {
	            oEmotion = (clsEmotion) oOtherElement;

	            oText += "Emotion: " + oEmotion.toString() + "\n";
	        }
	    }
	    
	    for(clsAssociationAttribute oAssAttribute : clsAssociation.filterListByType(poBodystate.getInternalAssociatedContent(), clsAssociationAttribute.class)) {
            oOtherElement = oAssAttribute.getTheOtherElement(poBodystate);
            if(oOtherElement instanceof clsThingPresentationMesh && ((clsThingPresentationMesh)oOtherElement).getContentType().equals(eContentType.ENTITY)) {
                oBodystateSource = (clsThingPresentationMesh) oOtherElement;

                oText += "from: " + oBodystateSource.getContent() + "\n\n";
            }
        }
	    
	    return oText;
	}
	
	public String getBodystatesTextual() {
	    String oText = "";
	    String oOwnershipText = "";
	    for(clsThingPresentationMesh oEntity : moCompleteThingPresentationMeshList) {
	        if(oEntity.getContentType().equals(eContentType.ENTITY)) {
	            //it's an entity, now check if it has a bodystate associated
	            for(clsAssociationAttribute oAssAttribute : clsAssociation.filterListByType(oEntity.getExternalAssociatedContent(), clsAssociationAttribute.class)) {
	                if(oAssAttribute.getAssociationElementB().getContentType().equals(eContentType.ENTITY)
	                        && ((clsThingPresentationMesh)oAssAttribute.getAssociationElementB()).getContent().equals("Bodystate")) {
	                    oText += "Bodystate at " + oEntity.getContent() + ":\n";
	                    oText += debugBodystate((clsThingPresentationMesh)oAssAttribute.getAssociationElementB());
	                } else if(oAssAttribute.getAssociationElementA().getContentType().equals(eContentType.ENTITY)
                            && ((clsThingPresentationMesh)oAssAttribute.getAssociationElementA()).getContent().equals("Bodystate")) {
	                    oText += "Bodystate at " + oEntity.getContent() + ": (WARN: might be associated on the wrong end of attribute association)\n";
                        oText += debugBodystate((clsThingPresentationMesh)oAssAttribute.getAssociationElementA());
                    }
	                else if(oAssAttribute.getAssociationElementB().getContentType().equals(eContentType.ISOWNER)) {
	                    oOwnershipText =   oEntity.getContent() +  " is owner of " + ((clsThingPresentation)(oAssAttribute.getAssociationElementB())).getContent() + "\n";
                    }
	                else if(oAssAttribute.getAssociationElementB().getContentType().equals(eContentType.ISOWNED)) {
	                        oOwnershipText =   oEntity.getContent() +  " is owned by " + ((clsThingPresentation)(oAssAttribute.getAssociationElementB())).getContent() + "\n";
	                }
	            }
	        }
	    }
	    
	    oText += "\nBodystate emotion creation:\n" + moBodystateCreation;
	    oText += oOwnershipText;
	    return oText;
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
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_3(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moEnvironmentalData = (HashMap<eSymbolExtType, itfSymbol>) deepCopy(poEnvironmentalData); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<String, Double> poBodyData) {
		moBodyData = poBodyData; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_draft() {

	}
		
	private static String removePrefix(String poName) {
		if (poName.startsWith("get")) {
			poName = poName.substring(3);
		}
				
		return poName;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_6(moCompleteThingPresentationMeshList, moDrives_IN);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:55:55
	 * 
	 * @see pa.interfaces.send.I2_5_send#send_I2_5(java.util.ArrayList)
	 */
	@Override
	public void send_I2_6(ArrayList<clsThingPresentationMesh> poCompleteThingPresentationMeshList, ArrayList<clsDriveMesh> poDrives_IN) {
		((I2_6_receive)moModuleList.get(46)).receive_I2_6(poCompleteThingPresentationMeshList, poDrives_IN, moReturnedPhantasy_IN, moPsychicSpreadingActivationMode_IN, moWordingToContext_IN);
		putInterfaceData(I2_6_send.class, poCompleteThingPresentationMeshList, poDrives_IN);
	}
	
	
	 void AddBodyExpressionTP(clsThingPresentationMesh poEntity, String poContentType, String poContent){ //attach a body expression to poEntity.
         eContentType poeContentType = eContentType.getContentType(poContentType);  
         clsThingPresentation poExpressionTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(poeContentType, poContent));
         clsAssociation poAss = clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ASSOCIATIONATTRIBUTE, poEntity, true, poExpressionTP, 1.0f);
	 }
    
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:34
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_basic() {
	    // 0. reset datastructures for inspectors
	    moBodystateCreation = "";
	    moSearchPattern = new ArrayList<clsThingPresentationMesh>();
        setGoToComposition();

	    
        // 1. Convert Neurosymbols to TPMs
	    ArrayList<clsPrimaryDataStructureContainer> oEnvironmentalTP = convertSymbolToTPM(moEnvironmentalData);
       
        // 2. drives activate exemplars. embodiment categorization criterion: activate entities from hallucinatory wish fulfillment. 
        // since drive objects may be associated to multiple drives, criterion activation in embodiment activation must be done after hallucinatory wishfulfillment (where only source activaiton is done) 
        moCompleteThingPresentationMeshList = searchTPMList(oEnvironmentalTP);
        
        //add current emotions to SELF
//        for(clsThingPresentationMesh oEntity : moCompleteThingPresentationMeshList) {
//            if(oEntity.getContent().equals("SELF")) { //TODO (Kollmann): this is actually not very nice, normally there should be some kind of reference to a SELF that can be used for comparion (or direct access)
//                //go through all received emotions and connect them to the self (internal connection == how the entity feels)
//                for(clsEmotion oEmotion : moCurrentEmotions) {
//                    //generate a new association
//                    oEntity.addInternalAssociations(new ArrayList<>(Arrays.asList(clsDataStructureGenerator.generateASSOCIATIONEMOTION(eContentType.ASSOCIATIONEMOTION, oEmotion, oEntity, 1.0))));
//                    log.debug("    added to self");
//                }   
//            }
//        }
        
        //=== Perform system tests ===//
        boolean status = clsTester.getTester().isActivated();
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignmentTPMArray(moCompleteThingPresentationMeshList);
            } catch (Exception e) {
                log.error("Systemtester has an error in activateMemories in" + this.getClass().getSimpleName(), e);
            }
        }
        clsTester.getTester().setActivated(status);
	    
        
        //attach Body Perception Parameter to Self
        for(clsThingPresentationMesh oEntity : moCompleteThingPresentationMeshList){
            if(oEntity.getContent().equals("SELF")){
                attachBodyPerceptionValuesToSelf(oEntity);
            }
            else continue;
        }
//        boolean found=false;
//        for(clsThingPresentationMesh oEntity : moCompleteThingPresentationMeshList){
//            double aggrActValue = oEntity.getAggregatedActivationValue();
//            if(aggrActValue<1.0)
//            {
//                //oEntity.setMoContent("NEW_OBJECT");
//                for(clsAssociation intAssCont: oEntity.getInternalAssociatedContent())
//                {
//                    clsDataStructurePA other = intAssCont.getTheOtherElement(oEntity);
//                    if (other instanceof clsThingPresentation){
//                       if (((clsThingPresentation)other).getContentType() == eContentType.Color){
//                            oEntity.setMoContent("NEW_OBJECT");
//                        }
//                    }
//                }
//                this.moSTM_Learning.moShortTermMemoryMF.get(0).setLearningObjects(oEntity);
//            }
//        }
        
//        for(clsThingPresentationMesh oEntity : this.moSTM_Learning.moShortTermMemoryMF.get(1).getLearningObjects())
//        {
//            for(clsThingPresentationMesh oLearningObject : this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects()){
//                if(  oLearningObject.compareTo(oEntity) == 1.0)
//                {
//                    //if(this.moSTM_Learning.getChangedMoment())
//                    {
//                        oLearningObject.setActiveTime(oEntity.getActiveTime()+1);
//                    }
//                }
//            }
//        }
	}
	
	private void attachBodyPerceptionValuesToSelf(clsThingPresentationMesh oSelf){
	    for( Entry<String,Double > oBodyValue : moBodyData.entrySet()){
	        if(!(oBodyValue.getKey().equals("HEALTH")) )
	        {
	            clsThingPresentation oValue = convertBodyValueSymbolToTPM(oBodyValue);
	            oSelf.addExternalAssociation(new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
	                    oSelf, 
	                    oValue)); 
	        }
	        else
	        {
//	            log.error("HEALTH");
	            // generate Unpleasure from Pain
	            double health;
	            double diff;
	            double unpleasure;
	            
	            health = oBodyValue.getValue();
	            health = health/100;
	            diff = 1 - health;
	            
	            unpleasure = (health - health_old) * (1-diff) + diff *diff;
	            unpleasure = unpleasure * 130 * diff ;
//	            unpleasure = 1 - health;
	            if(this.moSTM_Learning.getChangedMoment())
	            {
	                health_old = health;
	            }
	            if (unpleasure > 0.45)
	            {
	                unpleasure = 0.46;
	            }
                if (unpleasure < 0)
                {
                    unpleasure = 0;
                }
	            upleasure_array.add(unpleasure);
	            String olKey = "unpleasure(pain)";
	            if ( !moDriveChartData.containsKey(olKey) ) {
	                mnChartColumnsChanged = true;
	            }
	            moDriveChartData.put(olKey, unpleasure);
	            if ( unpleasure > F63_CompositionOfEmotions.rpain)
	            {
	                F63_CompositionOfEmotions.rpain = unpleasure;
	            }

	        }
	    }
	}
	
	public ArrayList<clsEmotion> getCurrentEmotions(ArrayList<clsDriveMesh> poDrives_IN) {
        if(moCurrentEmotions == null || moCurrentEmotions.isEmpty()) {
            return estimateCurrentEmotion(poDrives_IN);
        } else {
            return (ArrayList<clsEmotion>)moCurrentEmotions;
        }
    }

	protected ArrayList<clsEmotion> estimateCurrentEmotion(ArrayList<clsDriveMesh> poDrives_IN) {
	    ArrayList<clsEmotion> oCurrentEmotions = new ArrayList<clsEmotion>();
        
        double rCurrentP = 0.0;  // set own value
        //     rCurrentU = rCurrentL + rCurrentA;
        double rCurrentL = 0.0;
        double rCurrentA = 0.0;
        int rNumberOfLib = 0;
        int rNumberOfAgg = 0;
        double rQuotaOfAffect = 0.0;
        for(clsDriveMesh DriveMesh : poDrives_IN) {
            if((rQuotaOfAffect = DriveMesh.getQuotaOfAffect())==0.0)
                continue;
            if(DriveMesh.getDriveComponent()==eDriveComponent.LIBIDINOUS) {
                rCurrentL += rQuotaOfAffect;
                rNumberOfLib++;
            }
            else {
                rCurrentA += rQuotaOfAffect;
                rNumberOfAgg++;
            }
        }
        rCurrentL /= rNumberOfLib;
        rCurrentA /= rNumberOfAgg;
        clsEmotion oCurrentEmotion = clsDataStructureGenerator.generateEMOTION(new clsTriple<eContentType,eEmotionType,Object>(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0), rCurrentP, rCurrentL + rCurrentA, rCurrentL, rCurrentA);
        oCurrentEmotions.add(oCurrentEmotion);
      //oCurrentEmotions.add(oCurrentEmotion); // just for test
        
        return oCurrentEmotions;
	}
	
	//search TPM which were received from the receptors in the memory 
	public ArrayList<clsThingPresentationMesh> searchTPMList(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP){
        ArrayList<ArrayList<clsDataStructureContainer>> oRankedCandidateTPMs = new ArrayList<ArrayList<clsDataStructureContainer>>(); 
        ArrayList<clsThingPresentationMesh> oOutputTPMs = new ArrayList<clsThingPresentationMesh>();
        boolean bRemoved = false;
                     
        double rImpactFactorOfCurrentEmotion = 0.5; // Personality Factor for the impact of current emotions on emotional valuation of perceived agents
        ArrayList<clsEmotion> oCurrentEmotions = getCurrentEmotions(moDrives_IN);
        clsEmotion oCurrentEmotionValues = getEmotionValues(oCurrentEmotions);

        // 3. similarity criterion. perceptual activation. memory-search
        oRankedCandidateTPMs = stimulusActivatesEntities(poEnvironmentalTP);            
        
        // 4.  decide category membership
        for(ArrayList<clsDataStructureContainer> oRankedCandidates :oRankedCandidateTPMs) {

            // a. how many exemplars should be used for deciding drive categories
            long k = determineK(oRankedCandidates);
        
            // b. get AssDM of k-exemplars, group AssDM from same drives
            HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization = getKassDMs(k, oRankedCandidates);
            
            // c. get set of graded DMs
            ArrayList<clsPrimaryDataStructure> oDMStimulusList = getStimulusDMs(oAssDMforCategorization);
            
            if(!oRankedCandidates.isEmpty() && !(((clsThingPresentationMesh) oRankedCandidates.get(0).getMoDataStructure()).getContent().equals("Bodystate")))
            { //koller

                // extend object
                clsThingPresentationMesh oInputTPM = (clsThingPresentationMesh) poEnvironmentalTP.get(oRankedCandidateTPMs.indexOf(oRankedCandidates)).getMoDataStructure(); 
                
                if(oRankedCandidates.size()==0){
                    log.error("unable to percept "+oInputTPM);
                    continue;
                }
                
                clsThingPresentationMesh oOutputTPM = (clsThingPresentationMesh) oRankedCandidates.get(0).getMoDataStructure();
                ArrayList<clsDataStructurePA> oAssociatedElementsTP = new ArrayList<clsDataStructurePA>();
                ArrayList<clsDataStructurePA> oAssociatedElementsTPM = new ArrayList<clsDataStructurePA>();
    
                ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult2 = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
                extractStimulusUnknownFeaturesTP(oAssociatedElementsTP, oInputTPM, oOutputTPM);
                extractStimulusUnknownFeaturesTPM(oAssociatedElementsTPM, oInputTPM, oOutputTPM);
                // 
                oSearchResult2 = this.getLongTermMemory().searchEntity(eDataType.UNDEFINED, oAssociatedElementsTP); 
    
                ArrayList<clsThingPresentationMesh> oAssociatedTPMs = searchTPM(oAssociatedElementsTPM);
                addStimulusAttributeAssociations(oSearchResult2, oOutputTPM); 
                addTPMExtern(oAssociatedTPMs, oOutputTPM); 
                
                addAssociatedCoordinates(oAssociatedElementsTP, oOutputTPM);
                // d. replace associated DMs with category-DMs
                // d.1. remove all drive mesh associations
                List<clsAssociationDriveMesh> oAssociationsDriveMesh = clsAssociationDriveMesh.getAllExternAssociationDriveMesh(oOutputTPM);
                clsAssociationDriveMesh.removeAllExternAssociationDriveMesh(oOutputTPM);
                
                // d.2. add all stimulus associations
                for(clsPrimaryDataStructure oDM: oDMStimulusList) {
                    if(oDM instanceof clsDriveMesh){ //koller
                        oOutputTPM.addExternalAssociation(clsDataStructureGenerator.generateASSOCIATIONDM((clsDriveMesh) oDM, oOutputTPM, ((clsDriveMesh) oDM).getQuotaOfAffect())); //koller (casts)
                    }//koller
                }
                
                //if output TPM is SELF, associate current emotions to it
                // is SELF?
                if(oOutputTPM.getContent().equals("SELF")) {
                    //connect current emotions to it
                    //go through all received emotions and connect them to the self (internal connection == how the entity feels)
                    for(clsEmotion oEmotion : oCurrentEmotions) {
                        //generate a new association
                        clsDataStructureGenerator.generateASSOCIATIONEMOTION(eContentType.ASSOCIATIONEMOTION, oEmotion, oOutputTPM, true, 1.0);
                        log.debug("Emotion " + oEmotion.toString() + " added to self");
                    }
                }
                
                // 5. emotion-Valuation of agents, based on memorized emotions (emotion asscociated with agent or similar agents) and current own emotions
                
                // TODO: replace this with an interface to get real current emotion state
    
                
                
                for(clsAssociation oInternalAssociation : ((clsThingPresentationMesh)poEnvironmentalTP.get(oRankedCandidateTPMs.indexOf(oRankedCandidates)).getMoDataStructure()).getInternalAssociatedContent()) {
                   
                    // is oOutputTPM an agent?
    
                    if(oInternalAssociation.getAssociationElementB().getContentType()==eContentType.Alive
                            && ((boolean)((clsThingPresentation)oInternalAssociation.getAssociationElementB()).getContent())==true) {
                        //################################################################
                        double rResultP = 0.0;
                        double rResultU = 0.0;
                        double rResultL = 0.0;
                        double rResultA = 0.0;
                        int rNumberOfExemplarsWithEmotion = 0;
                        
                        // only use k-exemplars
                        for(int i=0; i<k; i++) {
                            
                            // initialize values: Pleasure, Unpleasure, Libid, Aggr
                            double rPleasure = 0.0;
                            double rUnpleasure = 0.0;
                            double rLibid = 0.0;
                            double rAggr = 0.0;
                            
                            // iterate over all associated emotions, sum & mean
                            int rNumberOfEmotions = 0;
                            // sum
                            for(clsAssociation oAssociatedDataStructure : oRankedCandidates.get(i).getMoAssociatedDataStructures()) {
                                // is an emotion?
                                if(oAssociatedDataStructure.getContentType()==eContentType.ASSOCIATIONEMOTION) {
                                    rNumberOfEmotions++;
                                    clsEmotion oEmotionObject = ((clsAssociationEmotion)oAssociatedDataStructure).getEmotion();
                                    rPleasure += oEmotionObject.getSourcePleasure();
                                    rUnpleasure += oEmotionObject.getSourceUnpleasure();
                                    rLibid += oEmotionObject.getSourceLibid();
                                    rAggr += oEmotionObject.getSourceAggr();
                                }
                            }
                            if(rNumberOfEmotions!=0) {
                                // mean
                                rPleasure /= rNumberOfEmotions;
                                rUnpleasure /= rNumberOfEmotions;
                                rLibid /= rNumberOfEmotions;
                                rAggr /= rNumberOfEmotions;
                                
                                // EffectiveActivationValue = ActivationValue + PersonalityFactor * EmotionMatchActivation
                                double rEffectiveActivationValue = ((clsThingPresentationMesh)oRankedCandidates.get(i).getMoDataStructure()).getAggregatedActivationValue() + rImpactFactorOfCurrentEmotion * getEmotionMatchActivation(rPleasure, rUnpleasure, rLibid, rAggr, oCurrentEmotionValues);
                                // accumulate to result
                                rResultP += rPleasure * rEffectiveActivationValue / (1 + rImpactFactorOfCurrentEmotion);
                                rResultU += rUnpleasure * rEffectiveActivationValue / (1 + rImpactFactorOfCurrentEmotion);
                                rResultL += rLibid * rEffectiveActivationValue / (1 + rImpactFactorOfCurrentEmotion);
                                rResultA += rAggr * rEffectiveActivationValue / (1 + rImpactFactorOfCurrentEmotion);
                                rNumberOfExemplarsWithEmotion++;
                            }
                            else
                                continue;
                        }
                        
                        // there has to be at least one emotion for continuing
                        if(rNumberOfExemplarsWithEmotion!=0) {
                            // mean & add result to oOutputTPM with help of clsEmotion
                            clsEmotion oResultEmotionObject = clsDataStructureGenerator.generateEMOTION(new clsTriple<eContentType,eEmotionType,Object>(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, 0.0), rResultP/rNumberOfExemplarsWithEmotion, rResultU/rNumberOfExemplarsWithEmotion, rResultL/rNumberOfExemplarsWithEmotion, rResultA/rNumberOfExemplarsWithEmotion);
                            clsDataStructureGenerator.generateASSOCIATIONEMOTION(eContentType.ASSOCIATIONEMOTION, oResultEmotionObject, oOutputTPM, false, 1.0);
                        }
                        //################################################################
                        break;
                    }
                }
                
                // Reminder: mit self assoziierte emotionen führen anscheinend zu "orphan associations" (wahrscheinlich beim klonen im search space) , d.h. eine assoziation, die in einem objekt (TPM) gespecihert ist, jedoch ist das objekt (TPM) nicht in der assoziation gespeichert. 
                
                oOutputTPMs.add(oOutputTPM);
            }
            else if(!oRankedCandidates.isEmpty()) { //koller add generated emotion, if oDMStimulusList is not empty
                clsThingPresentationMesh oOutputTPM = (clsThingPresentationMesh) oRankedCandidates.get(0).getMoDataStructure();
                // d. associate Emotions to Bodystates
                if(!oDMStimulusList.isEmpty()){
                    if(oDMStimulusList.get(0) instanceof clsEmotion){//koller
                        moBodystateCreation += "Combining " + k + " candidates:\n";
                        
                        for(ArrayList<clsAssociation> oAssList : oAssDMforCategorization.values()) {
                            for(clsAssociation oAssAttribute : oAssList) {
                                moBodystateCreation += oAssAttribute.getAssociationElementB().toString() + "\n";
                                moBodystateCreation += "+++++++++++++++++++++++++++\n";
                            }
                        }
                        
                        moBodystateCreation += "into new emotion " + ((clsEmotion)oDMStimulusList.get(0)).toString() + "\n";
                        
                        //check if there is already an emotion associated - THIS SHOULD NOT HAPPEN - if there is, give an error to inform developers
                        //but remove the faulty association before attached the correct one (to enable the system to keep running)
                        for(clsAssociationAttribute oAttribute : clsAssociation.filterListByType(oOutputTPM.getInternalAssociatedContent(), clsAssociationAttribute.class)) {
                            if(oAttribute.getTheOtherElement(oOutputTPM) instanceof clsEmotion) {
                                log.error("Bodystate {} seems to have an emotion associated even though it is a search result. This should not happen.", oOutputTPM);
                                log.error("The association will be removed to allow the application to continue, but check if this is a possible problem");
                                clsAssociation.removeAssociationCompletely(oAttribute);
                            }
                        }
                        
                        clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ASSOCIATIONEMOTION, oOutputTPM, true, oDMStimulusList.get(0), 1);
                    }
                }

                for(clsThingPresentationMesh oTPM : oOutputTPMs){ //koller attach Bodystates to OutputTPMs
                    
                    boolean boExpressionFound = false;
                    boolean boBodystateFound = false;
                    
                    for(clsAssociation oAss : oTPM.getExternalAssociatedContent()){
                        for(eEmotionExpression e :eEmotionExpression.values()){
                            if(oAss.getAssociationElementB().getContentType().toString() == e.toString()){
                                boExpressionFound = true;                         
                            }
                        }                      
                    }
                    for(clsAssociation oAss : oTPM.getExternalAssociatedContent()){
                         if(oAss.getAssociationElementB() instanceof clsThingPresentationMesh){   
                            if(((clsThingPresentationMesh) oAss.getAssociationElementB()).getContent().equals("Bodystate")){
                                boBodystateFound = true;
                            }
                        }
                    }
                    
                    if((boExpressionFound == true) && (boBodystateFound == false)){
                        moBodystateCreation += "for " + oTPM.toString() + "\n\n";
                        clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ASSOCIATIONATTRIBUTE, oTPM, false, oOutputTPM, 1);
                        break;
                    }
                } 
            }//end koller
        
        }
        
        //Kollmann: deactivate bodystate priming for now
//        oOutputTPMs = PrimingBodystates(oOutputTPMs); //koller
        // zhukova attributed ownership
        oOutputTPMs = determineObjectsOwnership(oOutputTPMs);
        determineActionsOfAnAgents(oOutputTPMs);
        
        if(!oOutputTPMs.isEmpty())
        {   
            for(clsThingPresentationMesh oOutputTPM:oOutputTPMs)
            {   
                if (oOutputTPM.getContentType().equals(eContentType.ACTION))
                {
                    
                    if (  
                          oOutputTPM.getContent() == "PICK_UP"
                       || oOutputTPM.getContent() == "GIVE"
                       || oOutputTPM.getContent() == "DROP"
                       || oOutputTPM.getContent() == "SHARE_FOOD"
                       )
                    {
                        moSTM_Learning.moShortTermMemoryMF.get(0).setSocialRules("DEVIDE");
                    }

                }
                if (oOutputTPM.getContentType().equals(eContentType.ENTITY))
                {
                    if ((oOutputTPM.getContent().equals("CARL")))
                    {
                        ArrayList<clsAssociation> associations;
                        associations = oOutputTPM.getExternalAssociatedContent();
                        for(clsAssociation association:associations)
                        {
                            if (association instanceof clsAssociationPrimary)
                            {
                                clsDataStructurePA action;
                                action = association.getTheOtherElement(oOutputTPM);
                                if(action instanceof clsThingPresentationMesh)
                                {   clsThingPresentationMesh actionTPM = (clsThingPresentationMesh)action;
                                    if (actionTPM.getContentType().equals(eContentType.ACTION))
                                    {
                                        moSTM_Learning.moShortTermMemoryMF.get(0).setLearningAction(actionTPM);
                                    }
                                }
                            }
                            if (association instanceof clsAssociationEmotion)
                            {
                                clsDataStructurePA action;
                                action = association.getTheOtherElement(oOutputTPM);
                                if(action instanceof clsEmotion)
                                {   clsEmotion emotion = (clsEmotion)action;
                                    moSTM_Learning.moShortTermMemoryMF.get(0).setLearningEmotion(emotion);
                                }
                            }
                            if (association instanceof clsAssociationAttribute)
                            {
                                clsDataStructurePA action;
                                action = association.getTheOtherElement(oOutputTPM);
                                if(action instanceof clsThingPresentationMesh)
                                {   clsThingPresentationMesh actionTPM = (clsThingPresentationMesh)action;
                                    if (actionTPM.getContent().equals("Bodystate"))
                                    {
                                        moSTM_Learning.moShortTermMemoryMF.get(0).setLearningBodypart(actionTPM);
                                    }
                                }
                            }

                        }
                    }
                }
            }
            if(moSTM_Learning.moShortTermMemoryMF.get(0).getSocialRules().isEmpty())
            {
                moSTM_Learning.moShortTermMemoryMF.get(0).setSocialRules("NO_DEVIDE");
            }
        }
        
        return oOutputTPMs;
	}
	/* Zhukova 
	 *  Determine the actions of an agent (alive entity): if the new action occur then add it into array of actions
	 *  if none of actions was done before then the action will be wait
	 *  if the entity is non-alive then the entity action should be "none"
	 */
	
	// if we do not have any association primary in the current perceive input then we either will take it from the previous round or (in case if there was any before)
	// generate a new one
	private void determineActionsOfAnAgents(ArrayList<clsThingPresentationMesh> poOutputTPMs) {
	       clsThingPresentationMesh oAssociatedAction = null;
	       boolean bAddAction = false;
	       for(int index = 0; index < poOutputTPMs.size(); index++) {
                bAddAction = false;
                clsThingPresentationMesh oTPM = poOutputTPMs.get(index);
                // we can 
	            if(isAlive(oTPM)) { 
	                ArrayList<clsAssociation> oExternalAssociatedContent = poOutputTPMs.get(index).getExternalAssociatedContent();
	                for(clsAssociation oExtAss : oExternalAssociatedContent) {
	                    if(oExtAss.getAssociationElementB() instanceof clsThingPresentationMesh) { 
	                        clsThingPresentationMesh oAssociatedTPM = (clsThingPresentationMesh) oExtAss.getAssociationElementB();
	                        if(oAssociatedTPM.getContentType().equals(eContentType.ACTION)) {
	                            bAddAction = true;
	                            if(composedActions.containsKey(oAssociatedTPM.getContent())) { 
	                                oAssociatedTPM.setMoContent(composedActions.get(oAssociatedTPM.getContent()));
	                            }
	                            oAssociatedAction = oAssociatedTPM;
	                            
	                            if(index >= oCurrentActions.size()) {
	                                log.error("Attempting to set current action for Arraylist of insufficient size. List size: " + oCurrentActions.size() + " with index: " + index);
	                            } else {
	                                oCurrentActions.set(index, oAssociatedAction);
	                            }
	                            break;
	                        }
	                    }
	                }
	                if(!bAddAction) {
	                    bAddAction = true;

	                    if(index >= oCurrentActions.size() || oCurrentActions.get(index).isNullObject()) { 
	                        oAssociatedAction = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<clsThingPresentation>(),  eAction.WAIT.toString() ));   
	                    }
	                    else {
	                        oAssociatedAction = oCurrentActions.get(index);
	                    }
	                }
	                clsAssociationPrimary oAssPr = clsDataStructureGenerator.generateASSOCIATIONPRI(eContentType.ASSOCIATIONPRI, oTPM, oAssociatedAction , 1.0);
	                poOutputTPMs.get(index).addExternalAssociation(oAssPr);
	                
	            } else if(!oTPM.getContentType().equals(eContentType.ACTION)){ 
	                //if(index >= oCurrentActions.size() || oCurrentActions.get(index).isNullObject()) {
	                    oAssociatedAction = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<clsThingPresentation>(),  eAction.NONE.toString() ));
	                    bAddAction = true;
	                //}
	            }
	            
	            // add new action 
	            if(bAddAction) { 
	                if(index >= oCurrentActions.size())
	                    oCurrentActions.add(index, oAssociatedAction);
	                else
	                    oCurrentActions.set(index, oAssociatedAction);
	            }
	            
	       } 
	}
	
	private boolean isAlive(clsThingPresentationMesh poTPM)  {
	    for(clsAssociation oIntAss : poTPM.getInternalAssociatedContent()) { 
            if(oIntAss.getAssociationElementB().getContentType().equals(eContentType.Alive)) {
                clsThingPresentation associatedProperty = (clsThingPresentation)oIntAss.getAssociationElementB();
                return Boolean.parseBoolean(associatedProperty.getContent().toString());	      
	    }
	    }
	    return false;
	}
	
    /**
   * DOCUMENT (Zhukova) - add associated coordinates to output TMP for ownership implementation
   *
   * @author zhukova
   * 11.08.2015
   *
   * @param poAssociatedTP
   * @param poOutputTPM
   */
	
	private ArrayList<clsThingPresentationMesh>  determineObjectsOwnership(ArrayList<clsThingPresentationMesh> poOutputTPMs) {
	    ArrayList<clsThingPresentationMesh> oAliveEntities = new ArrayList<clsThingPresentationMesh>();
	    ArrayList<clsThingPresentationMesh> oInanimateEntities = new ArrayList<clsThingPresentationMesh>();
	    ArrayList<clsThingPresentationMesh> oOutputTMPs = new ArrayList<clsThingPresentationMesh>();
	    
	    for(clsThingPresentationMesh oEntity : poOutputTPMs) {
	        if(isAlive(oEntity)) {    
	            oAliveEntities.add(oEntity);
	         }
	         else if(!oEntity.getContentType().equals(eContentType.ACTION)) {
	             oInanimateEntities.add(oEntity);
	         }     
	    }
	    
	    for(clsThingPresentationMesh oInanimateEntity : oInanimateEntities) {
	        // Coordinates of inanimate and alive entities
	        double xCoordinateInanimateEntity = 0;
	        double yCoordinateInanimateEntity = 0;
	        double xCoordinateAliveEntity = 0;
	        double yCoordinateAliveEntity = 0;
	        // for whom this object is ownered
	        boolean isOwned = false;
	        String OwnerName = "";
	        String ObjectName = oInanimateEntity.getContent();
	        // the minimum distance to compare, initially equals to minimum 
	        double minDistance = N_PROXIMITY_DISTANCE;
	        
	        clsThingPresentationMesh owner = null, owned = null ;
	        int indexOwner = 0, indexOwned = 0;
	        
	        ArrayList<clsAssociation> oExternalAssociatedContent = oInanimateEntity.getExternalAssociatedContent();
            for(clsAssociation oAss : oExternalAssociatedContent) {
                String contentType = oAss.getAssociationElementB().getContentType().toString();
                if(contentType.contains("DebugX")) {
                    clsThingPresentation tmp = (clsThingPresentation)(oAss.getAssociationElementB());
                    xCoordinateInanimateEntity = (Double)((clsThingPresentation)(oAss.getAssociationElementB())).getContent();
                }
                else if(contentType.contains("DebugY")) {
                    yCoordinateInanimateEntity = (Double)((clsThingPresentation)(oAss.getAssociationElementB())).getContent();
                }
            }
            for(clsThingPresentationMesh oAliveEntity : oAliveEntities) {
                if(oAliveEntity.getContent().contains("SELF")) continue;
                oExternalAssociatedContent = oAliveEntity.getExternalAssociatedContent();
                for(clsAssociation oAss : oExternalAssociatedContent) {
                    String contentType = oAss.getAssociationElementB().getContentType().toString();
                    if(contentType.contains("DebugX")) {
                        xCoordinateAliveEntity = (Double)((clsThingPresentation)(oAss.getAssociationElementB())).getContent();
                    }
                    else if(contentType.contains("DebugY")) {
                        yCoordinateAliveEntity = (Double)((clsThingPresentation)(oAss.getAssociationElementB())).getContent();
                    }
                }
                
                 double distance  = getEuclidianDistance(xCoordinateInanimateEntity, yCoordinateInanimateEntity, xCoordinateAliveEntity, yCoordinateAliveEntity); 
                 if(distance <= minDistance) {
                     isOwned = true;
                     OwnerName = oAliveEntity.getContent();
                     minDistance = distance;
                     for (clsAssociation oAss : oInanimateEntity.getExternalAssociatedContent()) {
                             if (oAss instanceof clsAssociationAttribute) {
                                 if (oAss.getLeafElement().getContentType().equals(eContentType.DISTANCE)) {
                                     ((clsThingPresentation)oAss.getLeafElement()).setMoContent("CARRYING");
                                 }
                             }
                     }
                 }
            }
            
            if(isOwned) {
                for(int index = 0; index < poOutputTPMs.size(); index ++) {
                    clsThingPresentationMesh entity = poOutputTPMs.get(index);
                    if(entity.getContent().equals(OwnerName)) {
                        indexOwner = index;
                    }
                    if(entity.getContent().equals(ObjectName)) {
                        indexOwned = index;    
                    }
                }
                clsAssociation oAssociation = new clsAssociationSpatial(new clsTriple<Integer, eDataType, eContentType>(
                        -1, eDataType.ASSOCIATIONSPATIAL, eContentType.ASSOCIATIONSPATIAL), 
                        poOutputTPMs.get(indexOwner), poOutputTPMs.get(indexOwned));
                
                poOutputTPMs.get(indexOwner).addExternalAssociation(oAssociation);
                poOutputTPMs.get(indexOwned).addExternalAssociation(oAssociation);
            }

	    }
	    return poOutputTPMs;
	}
// return euclidian distance between 2 points	
	double getEuclidianDistance(double x1, double y1, double x2, double  y2) {
	    return Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
	}
	
	   /**
     * DOCUMENT (Zhukova) - add associated coordinates to output TMP for ownership implementation
     *
     * @author zhukova
     * 11.08.2015
     *
     * @param poAssociatedTP
     * @param poOutputTPM
     */

	private void addAssociatedCoordinates(ArrayList<clsDataStructurePA> poAssociatedTPs, clsThingPresentationMesh poOutputTPM) {
	    for(clsDataStructurePA oAssociatedDS : poAssociatedTPs) {
	        clsThingPresentation oAssociatedTP =  (clsThingPresentation)oAssociatedDS;
	        if(isCoordinate(oAssociatedTP.getContentType().toString())) {
	            clsAssociation oAssociation = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(
	                    -1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
	                    poOutputTPM, oAssociatedTP);
	            poOutputTPM.addExternalAssociation(oAssociation);
	        }
	    }
	        
	    
	}
	
	public ArrayList<clsThingPresentationMesh> searchTPM(ArrayList<clsDataStructurePA> oAssociatedElementsTPM){
        ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult3 = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
        
        ArrayList<clsPrimaryDataStructureContainer> oEnvTPM = new ArrayList<clsPrimaryDataStructureContainer>();
        for(clsDataStructurePA oDataStructure: oAssociatedElementsTPM){
            oEnvTPM.add(new clsPrimaryDataStructureContainer(oDataStructure,new ArrayList<clsAssociation>()));
        }
        return searchTPMList(oEnvTPM);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:34
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}
	// Internal attributes = body state variables, distance ...
	private boolean isInternalAttribute(String poAttribute) {
		for(eEntityExternalAttributes eAttr: eEntityExternalAttributes.values()) {
			if  (eAttr.toString().equals(poAttribute)){
				return false;
			}
		}
		return true;
	}
	
	// Zhukova
	// check either the property is coordinate
    private boolean isCoordinate(String poAttribute) {
        if(poAttribute.contains("DebugX") || poAttribute.contains("DebugY"))
            return true;
        else
            return false;
    }
	
	public clsEmotion getEmotionValues(ArrayList<clsEmotion> poEmotions) {
        // sum & mean current emotions
        double rCurrentPleasure = 0.0;
        double rCurrentUnpleasure = 0.0;
        double rCurrentLibid = 0.0;
        double rCurrentAggr = 0.0;
        for(clsEmotion oCurrentEmotion : poEmotions) {
            rCurrentPleasure += oCurrentEmotion.getSourcePleasure();
            rCurrentUnpleasure += oCurrentEmotion.getSourceUnpleasure();
            rCurrentLibid += oCurrentEmotion.getSourceLibid();
            rCurrentAggr += oCurrentEmotion.getSourceAggr();
        }
        rCurrentPleasure /= poEmotions.size();
        rCurrentUnpleasure /= poEmotions.size();
        rCurrentLibid /= poEmotions.size();
        rCurrentAggr /= poEmotions.size();
        
        if(poEmotions.isEmpty())
            return null;
        else
            return clsDataStructureGenerator.generateEMOTION(new clsTriple<eContentType,eEmotionType,Object>(eContentType.UNDEFINED, eEmotionType.UNDEFINED, 0.0), rCurrentPleasure, rCurrentUnpleasure, rCurrentLibid, rCurrentAggr);
    }
	
	public double getEmotionMatchActivation(double prPleasure, double prUnpleasure, double prLibid, double prAggr, clsEmotion poCurrentEmotionValues) {
        if(poCurrentEmotionValues==null)
            return 0.0; // no emotions
        
        // calculate deviation
        double rDeviation = 0.0;
        rDeviation += Math.abs(prPleasure - poCurrentEmotionValues.getSourcePleasure());
        rDeviation += Math.abs(prUnpleasure - poCurrentEmotionValues.getSourceUnpleasure());
        rDeviation += Math.abs(prLibid - poCurrentEmotionValues.getSourceLibid());
        rDeviation += Math.abs(prAggr - poCurrentEmotionValues.getSourceAggr());
        rDeviation *= 0.25;
        
        return (1.0 - rDeviation); // return match as activation
    }
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:15:33
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}
	
	/**
	 * Get the first element of the input arraylist
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:08:28
	 *
	 * @param oEntry
	 * @return
	 */
	private clsDataStructureContainer extractBestMatch(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry) {
		
		clsDataStructureContainer oBestMatch = oEntry.get(0).b;; 
		
		return oBestMatch; 
	}
		
	private  ArrayList<clsPrimaryDataStructureContainer> convertSymbolToTPM( HashMap<eSymbolExtType, itfSymbol> poData) {
	    ArrayList<clsPrimaryDataStructureContainer> oEnvironmentalTP = new ArrayList<clsPrimaryDataStructureContainer>(); 
		for(itfSymbol oSymbol : poData.values()){
			if(oSymbol!=null){
				for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
					//convert the symbol to a PDSC/TP
					clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
					oEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
				}	
			}
		}
		
		// FIXME: SSCH delete this, if CM have changed the sensors to avoid the occurence of non-entities in moEnvironmentalData 
		ArrayList<clsPrimaryDataStructureContainer> oRemoveDS = new ArrayList<clsPrimaryDataStructureContainer>();
		for (clsPrimaryDataStructureContainer oEnvEntity : oEnvironmentalTP) {
			clsPrimaryDataStructureContainer oCheckEntity = oEnvEntity;
			if(oCheckEntity.getMoDataStructure().getContentType() != eContentType.ENTITY) {
				oRemoveDS.add(oCheckEntity);
			}
		}		
		for(clsPrimaryDataStructureContainer oDS: oRemoveDS){
		    oEnvironmentalTP.remove(oDS);			
		}
		
		return oEnvironmentalTP;
	}
	
	private clsThingPresentation convertBodyValueSymbolToTPM(Entry<String, Double> oBodySymbol){
	    clsThingPresentation oRetVal=null;
	    
	    if( oBodySymbol.getKey().equals("HEART_BEAT")){
	        oRetVal= clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.HeartBeat,""+oBodySymbol.getValue()));
	    }
	    else if( oBodySymbol.getKey().equals("SWEAT_INTENSITY")){
	            oRetVal= clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.SweatIntensity,""+oBodySymbol.getValue()));
	        }
	       else if( oBodySymbol.getKey().equals("CRYING_INTENSITY")){
               oRetVal= clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.CryingIntensity,""+oBodySymbol.getValue()));
           }
	       else if( oBodySymbol.getKey().equals("MUSCLE_TENSION_ARMS_INTENSITY")){
               oRetVal= clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.MuscleTensionArmsIntensity,""+oBodySymbol.getValue()));
           }
	       else if( oBodySymbol.getKey().equals("MUSCLE_TENSION_Legs_INTENSITY")){
               oRetVal= clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.MuscleTensionLegsIntensity,""+oBodySymbol.getValue()));
           }
	   return oRetVal;
	}
	
	private void drivesActivateEntities(){
		
		clsThingPresentationMesh oCandidateTPM = null;
		
		clsDriveMesh oMemorizedDriveMesh = null;
		
		for (clsDriveMesh oSimulatorDrive : moDrives_IN) {
			for(clsAssociation oAssSimilarDrivesAss : oSimulatorDrive.getExternalAssociatedContent() ) {

				oMemorizedDriveMesh = (clsDriveMesh)oAssSimilarDrivesAss.getAssociationElementB();
				oCandidateTPM = oMemorizedDriveMesh.getActualDriveObject();
				
				// embodiment activation: source activation is already done in F57 (in hallucinatory wishfulfillment). so, just apply criterion activaion function
				// and add activated drive objects to oAppropriateTPMs		
				
				// criterion activation function
				oCandidateTPM.applyCriterionActivation(eActivationType.EMBODIMENT_ACTIVATION);
				
				//oCandidateTPMs.add(oCandidateTPM);
				
			}
		}
	}
	
	// search and rank the enviromental TPM from the external perception 
	
	private ArrayList<ArrayList<clsDataStructureContainer>>  stimulusActivatesEntities(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP){
		
		// list of external associations which we will remove later on (external associations will not needed for search and so on)
		ArrayList<clsAssociation> oRemoveAss = null;
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResultsEnviromentalTP = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResultsBodyState = 
                new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		
		// entities for search from enviroment without external associations			
		ArrayList<clsThingPresentationMesh> poSearchPatternEnviromentalTP = new ArrayList<clsThingPresentationMesh>();
		ArrayList<clsThingPresentationMesh> poSearchPatternBodyState = new ArrayList<clsThingPresentationMesh>();
		
		ArrayList<ArrayList<clsDataStructureContainer>> poRankingResultsEnviromentalTP = null; 
		ArrayList<ArrayList<clsDataStructureContainer>> poRankingResultsBodyStateSearch = null; 
		
		// which bodystate corresponds to which 
		HashMap<String, String> bodyStateToEntityTMP = new HashMap<String, String>();
		
		clsThingPresentationMesh oUnknownTPM = null;
		//clsThingPresentationMesh oUnknownTPMCoordinates = null;
		
		ArrayList<clsThingPresentation> oCoordinates = null;
		// process EvironmentTPM
				for(clsPrimaryDataStructureContainer oEnvTPM :poEnvironmentalTP) {

					oRemoveAss = new ArrayList<clsAssociation>();
					oUnknownTPM = (clsThingPresentationMesh) oEnvTPM.getMoDataStructure();
					// 	separate internal attributes (which identify the entity) from external attributes (which are additional information)
					// remove external attributes		
					for (clsAssociation oIntAss: oUnknownTPM.getInternalAssociatedContent()) {
					            String oAssociationAttributeType = oIntAss.getAssociationElementB().getContentType().toString();
								if (isInternalAttribute(oAssociationAttributeType) == false) {
									// remove Assoc from internal and put it in external assoc
									oRemoveAss.add(oIntAss);
								}				
							}			
							for(clsAssociation oAss: oRemoveAss){
								oUnknownTPM.getInternalAssociatedContent().remove(oAss);
								oUnknownTPM.addExternalAssociation(oAss);
							}
							poSearchPatternEnviromentalTP.add(oUnknownTPM);	
				}
				


				//zhukova update: save the body state values into the   array
                boolean boCheckForBodystate = false;
                clsThingPresentationMesh tpm = null;
                //which variables should we search for defining which emotion it is (shaking, cheeks_redning and so on )
                ArrayList<clsThingPresentation> oArrayListExpressionVarTPForSearch = null;
                clsThingPresentationMesh oTPMForSearch = null;
                int rTPMCount = 0; 
                
                for(clsPrimaryDataStructureContainer pdsc : poEnvironmentalTP){
                    if(pdsc.getMoDataStructure().getContentType() == eContentType.ENTITY){
                        tpm = (clsThingPresentationMesh)pdsc.getMoDataStructure(); 
                        boCheckForBodystate = false;
                        oArrayListExpressionVarTPForSearch = new ArrayList<clsThingPresentation>();
                        //emotion expression - our body states variables    
                        
                        for (clsAssociation exterAssoc : tpm.getExternalAssociatedContent()) { 
                            for(eEmotionExpression e :eEmotionExpression.values()){
                                if(exterAssoc.getAssociationElementB().getContentType().toString() == e.toString()){
                                    clsThingPresentation y = (clsThingPresentation)exterAssoc.getAssociationElementB();
                                    boCheckForBodystate = true;
                                    oArrayListExpressionVarTPForSearch.add(y);                           
                                }
                            }
                        }
                        
                        if(boCheckForBodystate == true){
                            oTPMForSearch = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ENTITY, oArrayListExpressionVarTPForSearch, "BodystateForSearch"+ rTPMCount));
                            poSearchPatternBodyState.add(oTPMForSearch);
                            rTPMCount++;       
                        }
                    }
                   
                }
								
                oSearchResultsEnviromentalTP = this.getLongTermMemory().searchEntity(eDataType.DMTPM, poSearchPatternEnviromentalTP); //koller Suchaufruf mit neuem DMTPM eDataType
                for(ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult : oSearchResultsEnviromentalTP)
                {
                    for(clsPair<Double, clsDataStructureContainer> oSearchItem : oSearchResult)
                    {
                        if(oSearchItem.a < 1.0)
                        {
                            if((oSearchItem.b.getMoDataStructure()) instanceof clsThingPresentationMesh)
                            {   clsThingPresentationMesh TPM = ((clsThingPresentationMesh)(oSearchItem.b.getMoDataStructure()));
                                //TPM.setMoContent("NEW_OBJECT");
                                for(clsThingPresentationMesh oObject : poSearchPatternEnviromentalTP)
                                {
                                    if(oObject.getContent().equals(TPM.getContent()))
                                    {
                                        oObject.setMoContent("NEW_OBJECT");
                                        this.moSTM_Learning.moShortTermMemoryMF.get(0).setLearningObjects(new clsPair(TPM,oObject));
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
                poRankingResultsEnviromentalTP = rankCandidatesTPM(oSearchResultsEnviromentalTP);
                
                // Zhukova
                // Searching the body state for the entity
                
                oSearchResultsBodyState = this.getLongTermMemory().searchEntity(eDataType.DMTPM, poSearchPatternBodyState);

                
                //koller remove TPM-Associations that contain no Bodystates (because the search function cannot distinguish)
                ArrayList<clsAssociation> oAssToRemove = null;
                for(ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult : oSearchResultsBodyState){
                    for(clsPair<Double, clsDataStructureContainer> oSearchItem : oSearchResult){
                        oAssToRemove = new ArrayList<clsAssociation>();
                        for(clsAssociation oAssociation : oSearchItem.b.getMoAssociatedDataStructures()){
                            if(oAssociation.getContentType() != eContentType.ASSOCIATIONDM  &&  oAssociation.getContentType() != eContentType.ASSOCIATIONEMOTION){
                                clsThingPresentationMesh t = (clsThingPresentationMesh) oSearchItem.b.getMoDataStructure();
                                if(t.getContent().equals("Bodystate")){
                                    break;
                                }
                                else{
                                    oAssToRemove.add(oAssociation);  
                                }
                            }
                        }
                        for(clsAssociation oAss : oAssToRemove){
                            oSearchItem.b.getMoAssociatedDataStructures().remove(oAss);
                        }   
                    }
                }
              
                
                // assign entity name to body state
                int currentEntityNumber = 0;
                int currentBodyStateNumber = 0;
                
                String entityName;
                String bodyStateName;
                
                /* kollmann: The stimulus action is also performed for perceived actions AND the targets of perceived actions. But the
                 *           current implementation of perception provides these action targets already as TPM (which is probably wrong).
                 *           This TPM does not have any attributes of the entities in the perception and is actually more of a placeholder.
                 *           Due to this it is possible to have ARSIN entities that do not emit expression variables and therefore do not
                 *           have an bodystate search patterns -> safety check before accessing bodystate search patterns 
                 */
                if(!poSearchPatternEnviromentalTP.isEmpty() && !poRankingResultsEnviromentalTP.isEmpty() && !poSearchPatternBodyState.isEmpty()
                        && (poSearchPatternEnviromentalTP.size() == poRankingResultsEnviromentalTP.size())) {
                    for(clsThingPresentationMesh entitySearchPattern: poSearchPatternEnviromentalTP) {
                        if(entitySearchPattern.getContent().equals("ARSIN")) {
                            entityName = ((clsThingPresentationMesh) poRankingResultsEnviromentalTP.get(currentEntityNumber).get(0).getMoDataStructure()).getContent();
                            bodyStateName = ((clsThingPresentationMesh) poSearchPatternBodyState.get(currentBodyStateNumber)).getContent();
                            bodyStateToEntityTMP.put(bodyStateName, entityName);
                            currentBodyStateNumber ++;
                        }
                        currentEntityNumber++;
                    }
                }
                // message about failure
                else {
                    
                }
                                
                ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchItemsToRemove = new ArrayList<clsPair<Double, clsDataStructureContainer>>();
                boolean bAddToRemoveList;
                int nSearchItemIndex = 0;
                
                // consider similar entities for body search (when there is no associated memory with the agent, not familiar with this agent)
                boolean isFamiliarWithAgent;
                String oArsinName ;
                
                for(ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchResult : oSearchResultsBodyState) {
                    
                    
                    oArsinName = bodyStateToEntityTMP.get(poSearchPatternBodyState.get(nSearchItemIndex).getContent());
                    isFamiliarWithAgent = false;
                    
                    for(clsPair<Double, clsDataStructureContainer> oSearchItem : oSearchResult){ 
                        
                        clsThingPresentationMesh t = (clsThingPresentationMesh) oSearchItem.b.getMoDataStructure();
                        clsThingPresentationMesh u = null;
                        
                        if(t.getContent().equals("Bodystate")){
                            
                            bAddToRemoveList = true;
                            for(clsAssociation oAssociation : oSearchItem.b.getMoAssociatedDataStructures()){
                                if(oAssociation.getAssociationElementA().getContentType().equals(eContentType.ENTITY) && 
                                        ((clsThingPresentationMesh) oAssociation.getAssociationElementA()).getContent().equals(oArsinName)) {
                                    bAddToRemoveList = false;
                                    isFamiliarWithAgent = true;
                                    break;
                                } else if(oAssociation.getAssociationElementB().getContentType().equals(eContentType.ENTITY) &&
                                        ((clsThingPresentationMesh) oAssociation.getAssociationElementB()).getContent().equals(oArsinName)) {
                                    bAddToRemoveList = false;
                                    isFamiliarWithAgent = true;
                                    break;  
                                } 
                            }
                            if(bAddToRemoveList == true){
                                oSearchItemsToRemove.add(oSearchItem);
                            }
                        }
                        
                        
                    }
                    
                    if(isFamiliarWithAgent) {
                        for(clsPair<Double, clsDataStructureContainer> pair : oSearchItemsToRemove){
                            oSearchResult.remove(pair);
                        }
                    }
                    nSearchItemIndex ++;
                } 
                
                poRankingResultsBodyStateSearch = rankCandidatesTPM(oSearchResultsBodyState);
				// end Zhukova
                
				//kollmann: store search pattern for later inspection
				moSearchPattern.addAll(poSearchPatternEnviromentalTP);
				moSearchPattern.addAll(poSearchPatternBodyState);
				
				poRankingResultsEnviromentalTP.addAll(poRankingResultsBodyStateSearch);
				
				return  poRankingResultsEnviromentalTP;

	}
	
	
	private ArrayList<ArrayList<clsDataStructureContainer>> rankCandidatesTPM(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResults) {
	    ArrayList<ArrayList<clsDataStructureContainer>> oRankedCandidateTPMs = new ArrayList<ArrayList<clsDataStructureContainer>>();
	    
	    clsThingPresentationMesh oCandidateTPM = null;
	    clsThingPresentationMesh oCandidateTPM_DM = null;
	    clsDriveMesh oMemorizedDriveMesh = null;  
	    
	  //TODO: embed this code in search function
        for(ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResult :oSearchResults) {
            ArrayList<clsDataStructureContainer> oSpecificCandidates = new ArrayList<clsDataStructureContainer>();
            for(clsPair<Double,clsDataStructureContainer> oSearchItem: oSearchResult){              
                oCandidateTPM = (clsThingPresentationMesh)oSearchItem.b.getMoDataStructure();
                
                // TEST similarity activation: source activation
                
                oCandidateTPM.setCriterionActivationValue(eActivationType.SIMILARITY_ACTIVATION, oSearchItem.a);
            
                
                //  get other activation values. due to cloning, the same objects are different java objects and hence they have to be merged
                for (clsDriveMesh oSimulatorDrive : moDrives_IN) {
                    for(clsAssociation oAssSimilarDrivesAss : oSimulatorDrive.getExternalAssociatedContent() ) {
                        try {
                            oMemorizedDriveMesh = (clsDriveMesh)oAssSimilarDrivesAss.getAssociationElementB();
                            oCandidateTPM_DM = oMemorizedDriveMesh.getActualDriveObject();
                            
                            // is it the same TPM?
                            if(oCandidateTPM_DM.getDS_ID() == oCandidateTPM.getDS_ID()){
                                oCandidateTPM.takeActivationsFromTPM(oCandidateTPM_DM);
                            }
                        } catch (Exception e) {
                            log.error("Errors in the following drives: {} and  {}",  oMemorizedDriveMesh, oCandidateTPM_DM, e);
                            System.exit(-1);
                        }
                        
                    }
                }
                
                // add candidates to ranking
                oSpecificCandidates.add(oSearchItem.b);
            }
            Collections.sort( oSpecificCandidates, new clsActivationComperator() );
            oRankedCandidateTPMs.add(oSpecificCandidates);
        }
        
	    return oRankedCandidateTPMs;
	    
	}
	
	
	
	private long determineK(ArrayList<clsDataStructureContainer> poSpecificCandidates){
		
		double rActivationValueFirst = 0;
		double rActivationValueSecond = 0;
		double rAmbiguousFactor = 0;
		double rSimilarityAcivationFirst = 0;
		long k = 0;
		if(poSpecificCandidates.size()>=2){
    		clsThingPresentationMesh oFirstTPM = (clsThingPresentationMesh) poSpecificCandidates.get(0).getMoDataStructure();
    		clsThingPresentationMesh oSecondTPM = (clsThingPresentationMesh) poSpecificCandidates.get(1).getMoDataStructure();
    		
    		rActivationValueFirst = oFirstTPM.getAggregatedActivationValue();
    		rActivationValueSecond = oSecondTPM.getAggregatedActivationValue();
    		
    		// Ambigous?
    		rAmbiguousFactor = Math.abs(rActivationValueFirst-rActivationValueSecond);
    		rSimilarityAcivationFirst = oFirstTPM.getCriterionActivationValue(eActivationType.SIMILARITY_ACTIVATION);
    		if(rAmbiguousFactor < 0.1 || rSimilarityAcivationFirst != 1) {
    			// generalized drive obj.categ
    			k = Math.round((1-rActivationValueFirst) * poSpecificCandidates.size());
    		}
    		else {
    			// ident. drive obj.categ
    			k=1;
    		}
		} else {
		    if(poSpecificCandidates.size() == 1) {
		        k=1;
		    }
		}
		
		
		
		return k;
	}
	
	//combines the k-number of candidates into one resulting candidate
	private HashMap<String, ArrayList<clsAssociation>> getKassDMs(long prK, ArrayList<clsDataStructureContainer> poSpecificCandidates){

		ArrayList<clsAssociation> oAssDMList = new ArrayList<clsAssociation>();
		HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization = new HashMap<String, ArrayList<clsAssociation>>();
						
		clsDriveMesh oExemplarDM = null;
		String oDMID = null;
        String oBSID = "BodystateKey"; //BodystateID. When bodystates are processed, the function only needs to return one array filled with relevant associations. A hashmap with one key is only used to get the correct return type. 

	
		for (int i = 0; i < prK; i++) {
			
			// weight qoA with category appropriateness
			for (clsAssociation oAssDM: poSpecificCandidates.get(i).getMoAssociatedDataStructures()){
				//set category appropriateness as association weight (workaround?)
				//oAssDM.setMrWeight();
			    
			    if(!(oAssDM.getAssociationElementA() instanceof clsDriveMesh))
                    continue;
				oExemplarDM = (clsDriveMesh) oAssDM.getAssociationElementA();
				oAssDM.setMrWeight(((clsThingPresentationMesh) poSpecificCandidates.get(i).getMoDataStructure()).getAggregatedActivationValue());
				
				//generate key value for entry (DMs will be summed up, depending on this key)
				oDMID = oExemplarDM.getDriveIdentifier();
				if(oAssDMforCategorization.containsKey(oDMID) == false) {
					oAssDMList = new ArrayList<clsAssociation>();
					oAssDMList.add(oAssDM);
					oAssDMforCategorization.put(oDMID, oAssDMList);
				}
				else {
					oAssDMList = oAssDMforCategorization.get(oDMID);
					oAssDMList.add(oAssDM);
					oAssDMforCategorization.put(oDMID, oAssDMList);
				}
				
//				if (oAssDM.getMoDataStructureType() == eDataType.ASSOCIATIONDM){ //koller
//                    oExemplarDM = (clsDriveMesh) oAssDM.getAssociationElementA();
//                    oAssDM.setMrWeight(((clsThingPresentationMesh) poSpecificCandidates.get(i).getMoDataStructure()).getAggregatedActivationValue());
//                
//                    oDMID = oExemplarDM.getActualDriveSourceAsENUM().toString() + oExemplarDM.getDriveComponent();
//                    if (oAssDMforCategorization.containsKey(oDMID) == false) {
//                        oAssDMList.add(oAssDM);
//                        oAssDMforCategorization.put(oDMID, oAssDMList);
//                    } else {
//                        oAssDMList = oAssDMforCategorization.get(oDMID);
//                        oAssDMList.add(oAssDM);
//                        oAssDMforCategorization.put(oDMID, oAssDMList);
//                    }
//                }//koller
				
			}
			
			for (clsAssociation oAssTPM : poSpecificCandidates.get(i).getMoAssociatedDataStructures()) { //koller getKassTPM für Bodystates
                if ((oAssTPM.getMoDataStructureType() == eDataType.ASSOCIATIONATTRIBUTE) && (((clsThingPresentationMesh) oAssTPM.getAssociationElementA()).getContent().equals("Bodystate")) && (oAssTPM.getAssociationElementB().getContentType().equals(eContentType.BASICEMOTION))){ //hier noch abfrage ob moContent  //es muss auch eine emotion in der assoc sein (es gibt ja auch noch die ass mit self
                    oAssTPM.setMrWeight(((clsThingPresentationMesh) poSpecificCandidates.get(i).getMoDataStructure()).getAggregatedActivationValue());
                    //koller oAssTPM sind Associations zwischen einem Bodystate und einer Emotion. 
                    //oBSID hat den Namen der Emotion
                    
                    if (oAssDMforCategorization.containsKey(oBSID) == false) { 
                        oAssDMList.add(oAssTPM);
                        oAssDMforCategorization.put(oBSID, oAssDMList);
                    } else {
                        oAssDMList = oAssDMforCategorization.get(oBSID);
                        oAssDMList.add(oAssTPM);
                        oAssDMforCategorization.put(oBSID, oAssDMList);
                    }
                }
            } //end koller
			
		}
		
		return oAssDMforCategorization;
	}
	
	private double nonProportionalAggregation (double rBaseValue, double rAddValue) {
        rBaseValue = rBaseValue + (1 - rBaseValue) * rAddValue;
        return rBaseValue;
    }
	
	private ArrayList<clsPrimaryDataStructure> getStimulusDMs(HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization) {
		double rQoASum = 0;
		double rMax = 0;
		double rActivationValue = 0;
		clsDriveMesh oDMExemplar = null;
		clsDriveMesh oDMStimulus = null;
		
		double rSrcAggr = 0; //koller
        double rSrcLib = 0;
        double rSrcPle = 0;
        double rSrcUnple = 0;
        double rIntensity = 0;
        
        clsEmotion oEmoExemplar = null;  
        clsEmotion oEmoStimulus = null;
        
        ArrayList<clsPrimaryDataStructure> oDMStimulusList = new ArrayList<clsPrimaryDataStructure>(); //koller
        
		
		// generate drive meshes (decide graded category membership)
		for (String oDMID_End: oAssDMforCategorization.keySet()){
		    ArrayList<clsAssociation> oDMAssociations = oAssDMforCategorization.get(oDMID_End);
		    rQoASum = 0;
	        rMax = 0;
            if(oAssDMforCategorization.get(oDMID_End).get(0).getAssociationElementA() instanceof clsDriveMesh){//koller

                for(clsAssociation oAss : oDMAssociations) {
    				// category appropriateness * QoA
    				rActivationValue =  oAss.getMrWeight();
    				oDMExemplar = (clsDriveMesh) oAss.getAssociationElementA();
    				rQoASum +=  rActivationValue* (oDMExemplar).getQuotaOfAffect();
    				rMax += rActivationValue;
    			}
    			
    			// generate DM with graded QoA
    				
    			//oDMStimulus = new clsDriveMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.DM, eContentType.MEMORIZEDDRIVEREPRESENTATION), oDMExemplar.getInternalAssociatedContent(), rQoASum/rMax, "debugInfo:", oDMExemplar.getDriveComponent(), oDMExemplar.getPartialDrive());;
    	        oDMStimulus = new clsDriveMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.DM, eContentType.MEMORIZEDDRIVEREPRESENTATION), oDMExemplar, rQoASum/rMax, "debugInfo:");
    
    			oDMStimulusList.add(oDMStimulus);
            }
		}
		
		for (String oDMID_End: oAssDMforCategorization.keySet()){//koller
            if(oAssDMforCategorization.get(oDMID_End).get(0).getAssociationElementB() instanceof clsEmotion){
                for(clsAssociation oAss : oAssDMforCategorization.get(oDMID_End)) {
                    // category appropriateness * QoA
                    rActivationValue =  oAss.getMrWeight();
                    oEmoExemplar = (clsEmotion) oAss.getAssociationElementB();
    
                    rSrcAggr = nonProportionalAggregation(rSrcAggr, rActivationValue * (oEmoExemplar).getSourceAggr());
                    rSrcLib = nonProportionalAggregation(rSrcLib, rActivationValue * (oEmoExemplar).getSourceLibid());
                    rSrcPle = nonProportionalAggregation(rSrcPle, rActivationValue * (oEmoExemplar).getSourcePleasure());
                    rSrcUnple = nonProportionalAggregation(rSrcUnple, rActivationValue * (oEmoExemplar).getSourceUnpleasure());

                    //kollmann: at this point the intensity should not be relevant anyway, but still, lets calculate it for completeness sake 
                    rIntensity += rActivationValue * (oEmoExemplar).getEmotionIntensity();
                    rMax += rActivationValue;
                }

                //oEmoStimulus = new clsEmotion(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.EMOTION, eContentType.BASICEMOTION), rIntensity/rMax, eEmotionType.UNDEFINED, rSrcPle, rSrcUnple, rSrcLib, rSrcAggr);
                oEmoStimulus = clsDataStructureGenerator.generateEMOTION(eContentType.BASICEMOTION, eEmotionType.UNDEFINED, rIntensity/rMax, rSrcPle, rSrcUnple, rSrcLib, rSrcAggr);
                
                oDMStimulusList.add(oEmoStimulus);
            }
        }//end koller
		
		return oDMStimulusList;
		
	}
	
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 02.10.2012, 10:27:04
	 *
	 * @param oUnknownData
	 * @param poPerceptionEntry
	 * @param poNewImage 
	 */
	private void extractStimulusUnknownFeaturesTP(ArrayList<clsDataStructurePA> poUnknownData,
			clsThingPresentationMesh poPerceptionEntry, 
			clsThingPresentationMesh poNewImage) {
		
		for(clsAssociation oEntry : poPerceptionEntry.getInternalAssociatedContent()){
	 		
	 		if( !poNewImage.contain(oEntry.getAssociationElementB())){
	 			if(oEntry.getAssociationElementB() instanceof clsThingPresentation){
	 			    poUnknownData.add(oEntry.getAssociationElementB()); 
	 			}
	 		}
	 	}
		
		ArrayList<clsAssociation> temp = poPerceptionEntry.getExternalAssociatedContent();
		for(clsAssociation oEntry : temp) { //poPerceptionEntry.getExternalAssociatedContent()){
	 		
	 		if( !poNewImage.contain(oEntry.getAssociationElementB())){
	 		   if(oEntry.getAssociationElementB() instanceof clsThingPresentation){
	 		       poUnknownData.add(oEntry.getAssociationElementB());
	 		   }
	 		}
	 	}
	}
	
	   /**
     * DOCUMENT (schaat) - insert description
     *
     * @author schaat
     * 02.10.2012, 10:27:04
     *
     * @param oUnknownData
     * @param poPerceptionEntry
     * @param poNewImage 
     */
    private void extractStimulusUnknownFeaturesTPM(ArrayList<clsDataStructurePA> poUnknownData,
            clsThingPresentationMesh poPerceptionEntry, 
            clsThingPresentationMesh poNewImage) {
        
        for(clsAssociation oEntry : poPerceptionEntry.getInternalAssociatedContent()){
            
            if( !poNewImage.contain(oEntry.getAssociationElementB())){
                if(oEntry.getAssociationElementB() instanceof clsThingPresentationMesh){
                    poUnknownData.add(oEntry.getAssociationElementB()); 
                }
            }
        }
        
        for(clsAssociation oEntry : poPerceptionEntry.getExternalAssociatedContent()){
            
            if( !poNewImage.contain(oEntry.getAssociationElementB())){
               if(oEntry.getAssociationElementB() instanceof clsThingPresentationMesh){
                   poUnknownData.add(oEntry.getAssociationElementB());
               }
            }
        }
    }
    
    private double primeCalc(double pnValueSelf, double pnValueOther, double pnPrimeFactor) {
        return (pnPrimeFactor * pnValueSelf) + ((1 - pnPrimeFactor) * pnValueOther);
    }
 
    //If the agent has a bodystate, that bodystates influences the perception of other agents' bodystates   
    ArrayList<clsThingPresentationMesh> PrimingBodystates(ArrayList<clsThingPresentationMesh> oOutputTPMs){ //koller
        
        clsThingPresentationMesh oSelfBodystate = null;
        for(clsThingPresentationMesh oOPTPM : oOutputTPMs){
            if(oOPTPM.getContent().equals("SELF")){
                for(clsAssociation oA : oOPTPM.getExternalAssociatedContent()){
                    if(oA.getAssociationElementB() instanceof clsThingPresentationMesh){
                        if(((clsThingPresentationMesh) oA.getAssociationElementB()).getContent().equals("Bodystate")){
                            oSelfBodystate = (clsThingPresentationMesh) oA.getAssociationElementB();
                        }
                    }
                            
                }
            }
        }

        clsEmotion oFoundEmotion = null;
        clsEmotion oSelfBodystateEmotion = null;
        double rPle;
        double rUnple;
        double rAggr;
        double rLib;
        double rIntensity;
        //koller Priming process
        if(oSelfBodystate != null){
            for(clsThingPresentationMesh oOPTPM : oOutputTPMs){
                if(!oOPTPM.getContent().equals("SELF")){//koller Priming only applies to TPMs that are not SELF
                    for(clsAssociation oA : oOPTPM.getExternalAssociatedContent()){
                        if(oA.getAssociationElementB() instanceof clsThingPresentationMesh){
                            if(((clsThingPresentationMesh) oA.getAssociationElementB()).getContent().equals("Bodystate")){
                                for(clsAssociation oAs : ((clsThingPresentationMesh) oA.getAssociationElementB()).getInternalAssociatedContent()){
                                    if(oAs.getAssociationElementB() instanceof clsEmotion){
                                        oFoundEmotion = (clsEmotion) oAs.getAssociationElementB();
                                        for(clsAssociation oAss : oSelfBodystate.getInternalAssociatedContent()){
                                            if(oAss.getAssociationElementB() instanceof clsEmotion){
                                                oSelfBodystateEmotion = (clsEmotion) oAss.getAssociationElementB();
                                            }
                                        }
                                        
//                                        rPle = mrEmotionrecognitionPrimingPleasure * oFoundEmotion.getSourcePleasure() * oSelfBodystateEmotion.getSourcePleasure();
//                                        rUnple = mrEmotionrecognitionPrimingUnpleasure * oFoundEmotion.getSourceUnpleasure() * oSelfBodystateEmotion.getSourceUnpleasure();
//                                        rAggr = mrEmotionrecognitionPrimingAggression * oFoundEmotion.getSourceAggr() * oSelfBodystateEmotion.getSourceAggr();
//                                        rLib = mrEmotionrecognitionPrimingLibido * oFoundEmotion.getSourceLibid() * oSelfBodystateEmotion.getSourceLibid();
                                        
                                        if(oSelfBodystateEmotion == null && moFormerSelfBodystateEmotion != null) {
                                            oSelfBodystateEmotion = moFormerSelfBodystateEmotion;
                                            log.error("Bodystate {} does not have any emotion associated with it - this should never happen, for now, we will use the last known emotion associated to the last bodystate. But this should be fixed!");
                                        } else {
                                            
                                            moFormerSelfBodystateEmotion = oSelfBodystateEmotion.flatCopy();
                                            
                                            rPle = primeCalc(oSelfBodystateEmotion.getSourcePleasure(), oFoundEmotion.getSourcePleasure(), mrEmotionrecognitionPrimingPleasure);
                                            rUnple = primeCalc(oSelfBodystateEmotion.getSourceUnpleasure(), oFoundEmotion.getSourceUnpleasure(), mrEmotionrecognitionPrimingUnpleasure);
                                            rAggr = primeCalc(oSelfBodystateEmotion.getSourceAggr(), oFoundEmotion.getSourceAggr(), mrEmotionrecognitionPrimingAggression);
                                            rLib = primeCalc(oSelfBodystateEmotion.getSourceLibid(), oFoundEmotion.getSourceLibid(), mrEmotionrecognitionPrimingLibido);
                                      
                                        //kollmann: intensity does not need to be primed as long as intensity does not have influence in PP
                                        //rIntensity = mrEmotionrecognitionPrimingIntensity * oFoundEmotion.getEmotionIntensity() * oSelfBodystateEmotion.getEmotionIntensity();
                                        //oFoundEmotion.setEmotionIntensity(rIntensity);
                                        
                                            oFoundEmotion.setSourcePleasure(rPle);
                                            oFoundEmotion.setSourceUnpleasure(rUnple);
                                            oFoundEmotion.setSourceAggr(rAggr);
                                            oFoundEmotion.setSourceLibid(rLib);
                                        }
                                    }
                                        
                                }
                            }
                        }
                    }
                }
            }
        }
        return oOutputTPMs;
    }//end koller

	
	/**
	 * Add corresponding attribute associations to a TPM
	 *
	 * @author schaat
	 * 03.103.2012, 10:25:24
	 *
	 * @param oSearchResult
	 * @param poOutputTPM
	 */
	private void addStimulusAttributeAssociations(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
						clsThingPresentationMesh poOutputTPM) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				clsPrimaryDataStructureContainer oBestMatch = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry);
				clsThingPresentation oTPBestMatch = (clsThingPresentation)oBestMatch.getMoDataStructure();
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(
							-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
							poOutputTPM, oTPBestMatch);
				
				if(isInternalAttribute(oTPBestMatch.getContentType().toString())){
					poOutputTPM.assignDataStructure(oAssociation);
				}
				else{
					poOutputTPM.addExternalAssociation(oAssociation);
				}
			}
		}
	}
	
	private void addTPMExtern(ArrayList<clsThingPresentationMesh> poTPMList, clsThingPresentationMesh poOutputTPM){
	    for(clsThingPresentationMesh oTPM: poTPMList){
	        clsAssociation oAssociation = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(
                    -1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
                    poOutputTPM, oTPM);
	        poOutputTPM.addExternalAssociation(oAssociation);
	        //oTPM.addExternalAssociation(oAssociation);

	    }
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
		moDescription = "Neurosymbolic contents are transformed into thing presentations. Now, sensor sensations originating in body and environment sensors can be processed by the mental functions. The generated thing presentations are associated among each others according to their temporal and spacial vicinity and likeness.";
	}

	/* (non-Javadoc)
	 *
	 * @since 15.05.2012 10:58:38
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_1(
			ArrayList<clsDriveMesh> poDrives) {
		moDrives_IN = (ArrayList<clsDriveMesh>)deepCopy(poDrives);
		
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 3:22:40 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesRecv() {
		ArrayList<eInterfaces> oResult = new ArrayList<eInterfaces>();
		oResult.add(eInterfaces.I2_3);
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 2, 2012 3:22:40 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesSend() {
		return getInterfacesSend();
	}

    /* (non-Javadoc)
     *
     * @since 07.10.2014 15:20:36
     * 
     * @see modules.interfaces.I5_19_receive#receive_I5_19(java.util.ArrayList, memorymgmt.enums.PsychicSpreadingActivationMode, base.datatypes.clsWordPresentationMesh)
     */
	@Override
	public void receive_I5_19(ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode mode, clsWordPresentationMesh moWordingToContext2, List<clsEmotion> poCurrentEmotions) {
        moWordingToContext_IN = moWordingToContext2;
        moReturnedPhantasy_IN = (ArrayList<clsThingPresentationMesh>)deepCopy(poReturnedMemory);
        moPsychicSpreadingActivationMode_IN = mode;
        moCurrentEmotions = poCurrentEmotions;
	}

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
     */
    @Override
    public double getTimeChartUpperLimit() {
        // TODO (nocks) - Auto-generated method stub
        return 1.1;
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
     */
    @Override
    public double getTimeChartLowerLimit() {
        // TODO (nocks) - Auto-generated method stub
        return -0.1;
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
     */
    @Override
    public String getTimeChartAxis() {
        // TODO (nocks) - Auto-generated method stub
        return "0 to 1";
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
     */
    @Override
    public String getTimeChartTitle() {
        // TODO (nocks) - Auto-generated method stub
        return "Unpleasure from body pain";
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartData()
     */
    @Override
    public ArrayList<Double> getTimeChartData() {
        // TODO (nocks) - Auto-generated method stub
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moDriveChartData.values());
		return oResult;

    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
     */
    @Override
    public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
     */
    @Override
    public clsTimeChartPropeties getProperties() {
        // TODO (nocks) - Auto-generated method stub
        return new clsTimeChartPropeties(true);
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
     */
    @Override
    public boolean chartColumnsChanged() {
        // TODO (nocks) - Auto-generated method stub
        return mnChartColumnsChanged;
    }

    /* (non-Javadoc)
     *
     * @since 18.06.2019 13:58:44
     * 
     * @see inspector.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
     */
    @Override
    public void chartColumnsUpdated() {
        // TODO (nocks) - Auto-generated method stub
        mnChartColumnsChanged = false;
    }

    /* (non-Javadoc)
     *
     * @since 01.07.2019 13:46:43
     * 
     * @see inspector.interfaces.itfInspectorForSTM#getDriverules()
     */
//    @Override
//    public ArrayList<clsThingPresentationMesh> getData() {
//        ArrayList <clsThingPresentationMesh> test = new ArrayList <clsThingPresentationMesh>();
//        
////        test.add(clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<>(), "Test1")));
////        test.add(clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<>(), "Test1")));
////        test.add(clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ACTION, new ArrayList<>(), "Test1")));
//        test = this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects();
//        // TODO (nocks) - Auto-generated method stub
//        return test;
//    }	
}
