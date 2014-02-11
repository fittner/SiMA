/**
 * E14_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 */
package primaryprocess.modules;

import inspector.interfaces.itfGraphCompareInterfaces;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;

import prementalapparatus.symbolization.eSymbolExtType;
import prementalapparatus.symbolization.representationsymbol.itfGetDataAccessMethods;
import prementalapparatus.symbolization.representationsymbol.itfGetSymbolName;
import prementalapparatus.symbolization.representationsymbol.itfIsContainer;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import properties.clsProperties;
import testfunctions.clsTester;
import memorymgmt.enums.eActivationType;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEntityExternalAttributes;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.I2_3_receive;
import modules.interfaces.I2_4_receive;
import modules.interfaces.I2_6_receive;
import modules.interfaces.I2_6_send;
import modules.interfaces.I5_1_receive;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsActivationComperator;
import base.datahandlertools.clsDataStructureConverter;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsPrimaryDataStructureContainer;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import bfg.utils.enums.eSide;
import du.enums.eActionTurnDirection;
import du.enums.eSaliency;
import du.itf.sensors.clsInspectorPerceptionItem;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.clsInternalActionTurnVision;
import du.itf.actions.itfInternalActionProcessor;

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
					itfGraphCompareInterfaces
					{
	public static final String P_MODULENUMBER = "14";
	
	/** this holds the symbols from the environmental perception (IN I2.3) @since 21.07.2011 11:37:01 */
	private HashMap<eSymbolExtType, itfSymbol> moEnvironmentalData;
	/** this holds the symbols from the bodily perception (IN I2.4)  @since 21.07.2011 11:37:06 */
	private HashMap<eSymbolExtType, itfSymbol> moBodyData;
	/** OUT member of F14, this holds the converted symbols of the two perception paths and the recognized TPMs (OUT I2.6) @since 20.07.2011 10:26:23 */
	private ArrayList<clsThingPresentationMesh> moCompleteThingPresentationMeshList;
	
	//ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP;
	
	ArrayList<clsInspectorPerceptionItem> moPerceptionSymbolsForInspectors;
	ArrayList<String> Test = new ArrayList<String>();
	ArrayList<String> Test1 = new ArrayList<String>();
	//list of internal actions, fill it with what you want to be shown
	private ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
	/** Input from Drive System */
	private ArrayList<clsDriveMesh> moDrives_IN;
	private boolean useAttentionMechanism = false;

	//private Logger log = Logger.getLogger(this.getClass());
	
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
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poMemory) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory);
		moPerceptionSymbolsForInspectors = new ArrayList<clsInspectorPerceptionItem>();
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
		String text = "";
		
		text += toText.listToTEXT("§§§§§§§§§§§§§§Test", Test);
		text += toText.listToTEXT("§§§§§§§§§§§§§§Test1", Test1);
		text += toText.mapToTEXT("§§§§§§§§§§§§3333333333moEnvironmentalData", moEnvironmentalData);
		text += toText.mapToTEXT("moBodyData", moBodyData);
		text += toText.listToTEXT("moCompleteThingPresentationMeshList", moCompleteThingPresentationMeshList);

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
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moBodyData = (HashMap<eSymbolExtType, itfSymbol>) deepCopy(poBodyData); 
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
	
	
	public ArrayList<clsInspectorPerceptionItem> GetSensorDataForInspectors(){
		return moPerceptionSymbolsForInspectors;
	}
	
private void PrepareSensorInformatinForAttention( HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {

        
        eSaliency moSaliencyObject = eSaliency.UNDEFINED;
        eSide moPositionObject = eSide.UNDEFINED;
        
        for(itfSymbol oSymbol : moEnvironmentalData.values()){
            if(oSymbol!=null){
                for(itfSymbol poSymbolObject : oSymbol.getSymbolObjects()) {
                    
                    if(poSymbolObject instanceof itfIsContainer) {
                    
                    clsInspectorPerceptionItem oInspectorItem = new clsInspectorPerceptionItem();
                    
                    Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();
                    eContentType oContentType = eContentType.valueOf(((itfGetSymbolName)poSymbolObject).getSymbolType());
                    oInspectorItem.moContentType = oContentType.toString();
                    oInspectorItem.moContent = ((itfIsContainer)poSymbolObject).getSymbolMeshContent().toString();
                    
                    if (oContentType.equals(eContentType.POSITIONCHANGE)) {
                        //do nothing, we dont want this sensor info
                    }
                    else
                    {
                    
                        for(Method oM : oMethods){
                            if (oM.getName().equals("getSymbolObjects")) {
                                continue;
                            }
                         
                            eContentType oContentTypeTP = eContentType.DEFAULT; 
                            Object oContentTP = "DEFAULT";
                            
                            oContentTypeTP = eContentType.valueOf(removePrefix(oM.getName())); 
                                
                            if (oContentTypeTP.equals(eContentType.Brightness)) {
                                try {
                                    moSaliencyObject = eSaliency.valueOf((oM.invoke(poSymbolObject,new Object[0]).toString()));
                                    
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
        
                            if(oContentTypeTP.equals(eContentType.ObjectPosition)) {
                                oContentTypeTP = eContentType.POSITION; 
                                try {
                                    
                                    moPositionObject = eSide.valueOf(oM.invoke(poSymbolObject,new Object[0]).toString());
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                
                            }
                            
                            if (oContentTypeTP.equals(eContentType.Distance)) {
                                oContentTypeTP = eContentType.DISTANCE;
                                try {
                                    oInspectorItem.moDistance = oM.invoke(poSymbolObject,new Object[0]).toString();
                                } catch (IllegalArgumentException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                        
                        //calculate angle
                        if((moSaliencyObject == eSaliency.HIGH)||
                            (moSaliencyObject == eSaliency.VERYHIGH))
                            {
                             if(moPositionObject != eSide.UNDEFINED){
                                 double focusAngle = 0.0;
                                 eActionTurnDirection focusDirection = eActionTurnDirection.TURN_LEFT;
                                 if(moPositionObject == eSide.MIDDLE_LEFT){
                                     focusAngle = 50;
                                     focusDirection = eActionTurnDirection.TURN_LEFT;
                                 }
                                 else if(moPositionObject == eSide.LEFT){
                                     focusAngle = 134;
                                     focusDirection = eActionTurnDirection.TURN_LEFT;
                                 }
                                 else if(moPositionObject == eSide.MIDDLE_RIGHT){
                                     focusAngle = 50;
                                     focusDirection = eActionTurnDirection.TURN_RIGHT;
                                 }
                                 else if(moPositionObject == eSide.RIGHT){
                                     focusAngle = 134;
                                     focusDirection = eActionTurnDirection.TURN_RIGHT;
                                 }
                             
                                clsInternalActionTurnVision focus = new clsInternalActionTurnVision(focusDirection, focusAngle);
                                moInternalActions.add( focus );
                             }
                             else{
                                 //return to normal
                                 clsInternalActionTurnVision focus = new clsInternalActionTurnVision(eActionTurnDirection.TURN_LEFT, 0.0);
                                    moInternalActions.add( focus );
                             }
                            }
                        
                        
                        //moPerceptionSymbolsForInspectors.add(oInspectorItem); 
                    }
                        
                        }
                    else {
                        //
                        }               
                    }
                
                }   
            }
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
		((I2_6_receive)moModuleList.get(46)).receive_I2_6(poCompleteThingPresentationMeshList, poDrives_IN);
		putInterfaceData(I2_6_send.class, poCompleteThingPresentationMeshList, poDrives_IN);
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
        // 1. Convert Neurosymbols to TPMs
	    ArrayList<clsPrimaryDataStructureContainer> oEnvironmentalTP= convertSymbolToTPM(moEnvironmentalData);
       
        if(useAttentionMechanism)
            PrepareSensorInformatinForAttention(moEnvironmentalData);

        // 2. drives activate exemplars. embodiment categorization criterion: activate entities from hallucinatory wish fulfillment. 
        // since drive objects may be associated to multiple drives, criterion activation in embodiment activation must be done after hallucinatory wishfulfillment (where only source activaiton is done) 
        moCompleteThingPresentationMeshList = searchTPMList(oEnvironmentalTP);		
        
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
	    
	}
	
	public ArrayList<clsThingPresentationMesh> searchTPMList(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP){
        ArrayList<ArrayList<clsDataStructureContainer>> oRankedCandidateTPMs = new ArrayList<ArrayList<clsDataStructureContainer>>(); 
        ArrayList<clsThingPresentationMesh> oOutputTPMs = new ArrayList<clsThingPresentationMesh>();
                 
        // 3. similarity criterion. perceptual activation. memory-search
        oRankedCandidateTPMs = stimulusActivatesEntities(poEnvironmentalTP);            
        
        // 4.  decide category membership
        for(ArrayList<clsDataStructureContainer> oRankedCandidates :oRankedCandidateTPMs) {
            
            // a. how many exemplars should be used for deciding drive categories
            long k = determineK(oRankedCandidates);
        
            // b. get AssDM of k-exemplars, group AssDM from same drives
            HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization = getKassDMs(k, oRankedCandidates);
            
            // c. get set of graded DMs
            ArrayList<clsDriveMesh> oDMStimulusList = getStimulusDMs(oAssDMforCategorization);
            
            // extend object
            clsThingPresentationMesh oInputTPM = (clsThingPresentationMesh) poEnvironmentalTP.get(oRankedCandidateTPMs.indexOf(oRankedCandidates)).getMoDataStructure(); 
            clsThingPresentationMesh oOutputTPM = (clsThingPresentationMesh) oRankedCandidates.get(0).getMoDataStructure();
            ArrayList<clsDataStructurePA> oAssociatedElementsTP = new ArrayList<clsDataStructurePA>();
             ArrayList<clsDataStructurePA> oAssociatedElementsTPM = new ArrayList<clsDataStructurePA>();

            ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult2 = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
            extractStimulusUnknownFeaturesTP(oAssociatedElementsTP, oInputTPM, oOutputTPM);
            extractStimulusUnknownFeaturesTPM(oAssociatedElementsTPM, oInputTPM, oOutputTPM);
            oSearchResult2 = this.getLongTermMemory().searchEntity(eDataType.UNDEFINED, oAssociatedElementsTP); 

            ArrayList<clsThingPresentationMesh> oAssociatedTPMs = searchTPM(oAssociatedElementsTPM);
            addStimulusAttributeAssociations(oSearchResult2, oOutputTPM); 
            addTPMExtern(oAssociatedTPMs, oOutputTPM); 
            
            // d. associate category-DMs to TPM
            for(clsDriveMesh oDM: oDMStimulusList){
                oOutputTPM.addExternalAssociation(clsDataStructureGenerator.generateASSOCIATIONDM(oDM, oOutputTPM, oDM.getQuotaOfAffect()));
                
            }
            
            oOutputTPMs.add(oOutputTPM);
            
        }
        return oOutputTPMs;
                

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

	private boolean isInternalAttribute(String poAttribute) {
		for(eEntityExternalAttributes eAttr: eEntityExternalAttributes.values()) {
			if  (eAttr.toString().equals(poAttribute)){
				return false;
			}
		}
		return true;
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
	
	
	private ArrayList<ArrayList<clsDataStructureContainer>>  stimulusActivatesEntities(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP){
		
		clsThingPresentationMesh oCandidateTPM = null;
		clsThingPresentationMesh oCandidateTPM_DM = null;
		
		clsDriveMesh oMemorizedDriveMesh = null;
		
		ArrayList<ArrayList<clsDataStructureContainer>> oRankedCandidateTPMs = new ArrayList<ArrayList<clsDataStructureContainer>>(); 
	
		ArrayList<clsAssociation> oRemoveAss = null;
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResults = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
					
		ArrayList<clsThingPresentationMesh> poSearchPattern = new ArrayList<clsThingPresentationMesh>();
						
		clsThingPresentationMesh oUnknownTPM = null;
		
		
		// process EvironmentTPM
				for(clsPrimaryDataStructureContainer oEnvTPM :poEnvironmentalTP) {

					oRemoveAss = new ArrayList<clsAssociation>();
					
					oUnknownTPM = (clsThingPresentationMesh) oEnvTPM.getMoDataStructure();				
													
							// 	separate internal attributes (which identify the entity) from external attributes (which are additional information)
							for (clsAssociation oIntAss: oUnknownTPM.getInternalAssociatedContent()) {
								if (isInternalAttribute(oIntAss.getAssociationElementB().getContentType().toString()) == false) {
									// remove Assoc from internal and put it in external assoc
									oRemoveAss.add(oIntAss);
								}
											
							}
										
							for(clsAssociation oAss: oRemoveAss){
								oUnknownTPM.removeInternalAssociation(oAss);
								oUnknownTPM.addExternalAssociation(oAss);
							}
							
							poSearchPattern.add(oUnknownTPM);			
										
						
				}
								
				oSearchResults = this.getLongTermMemory().searchEntity(eDataType.DM, poSearchPattern);	
				
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

								oMemorizedDriveMesh = (clsDriveMesh)oAssSimilarDrivesAss.getAssociationElementB();
								oCandidateTPM_DM = oMemorizedDriveMesh.getActualDriveObject();
								
								// is it the same TPM?
								if(oCandidateTPM_DM.getDS_ID() == oCandidateTPM.getDS_ID()){
									oCandidateTPM.takeActivationsFromTPM(oCandidateTPM_DM);
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
		}
		else k=0;
		
		
		
		return k;
	}
	
	
	private HashMap<String, ArrayList<clsAssociation>> getKassDMs(long prK, ArrayList<clsDataStructureContainer> poSpecificCandidates){

		ArrayList<clsAssociation> oAssDMList = new ArrayList<clsAssociation>();
		HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization= new HashMap<String, ArrayList<clsAssociation>>();
						
		clsDriveMesh oExemplarDM = null;
		String oDMID = null;
	
		for (int i=0; i<prK; i++) {
			
			// weight qoA with categopry appropriateness
			for (clsAssociation oAssDM: poSpecificCandidates.get(i).getMoAssociatedDataStructures()){
				//set categ appropriatenes as association weight (workaraound?)
				//oAssDM.setMrWeight();
				oExemplarDM = (clsDriveMesh) oAssDM.getAssociationElementA();
				oAssDM.setMrWeight(((clsThingPresentationMesh) poSpecificCandidates.get(i).getMoDataStructure()).getAggregatedActivationValue());
				
				oDMID = oExemplarDM.getActualDriveSourceAsENUM().toString() + oExemplarDM.getDriveComponent();
				if(oAssDMforCategorization.containsKey(oDMID) == false) {
					oAssDMList.add(oAssDM);
					oAssDMforCategorization.put(oDMID, oAssDMList);
				}
				else {
					oAssDMList = oAssDMforCategorization.get(oDMID);
					oAssDMList.add(oAssDM);
					oAssDMforCategorization.put(oDMID, oAssDMList);
				}
				
			}
			
		}
		
		return oAssDMforCategorization;
	}
	
	
	private ArrayList<clsDriveMesh> getStimulusDMs(HashMap<String, ArrayList<clsAssociation>> oAssDMforCategorization) {
		double rQoASum = 0;
		double rMax = 0;
		double rActivationValue = 0;
		clsDriveMesh oDMExemplar = null;
		clsDriveMesh oDMStimulus = null;
		ArrayList<clsDriveMesh> oDMStimulusList = new ArrayList<clsDriveMesh>();
		
		// generate drive meshes (decide graded category membership)
		for (String oDMID_End: oAssDMforCategorization.keySet()){
			for(clsAssociation oAss : oAssDMforCategorization.get(oDMID_End)) {
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
		
		for(clsAssociation oEntry : poPerceptionEntry.getExternalAssociatedContent()){
	 		
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
	
	public void getBodilyReactions(    itfInternalActionProcessor poInternalActionContainer) {
	       
	       for( clsInternalActionCommand oCmd : moInternalActions ) {
	           poInternalActionContainer.call(oCmd);
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
}
