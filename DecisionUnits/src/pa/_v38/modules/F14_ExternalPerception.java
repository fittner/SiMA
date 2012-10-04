/**
 * E14_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 */
package pa._v38.modules;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import du.enums.eDistance;
import du.itf.sensors.clsInspectorPerceptionItem;
import pa._v38.interfaces.modules.I2_3_receive;
import pa._v38.interfaces.modules.I2_4_receive;
import pa._v38.interfaces.modules.I2_6_receive;
import pa._v38.interfaces.modules.I2_6_send;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsActivationComperator;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eActivationType;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eEntityExternalAttributes;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.symbolization.eSymbolExtType;
import pa._v38.symbolization.representationsymbol.itfGetDataAccessMethods;
import pa._v38.symbolization.representationsymbol.itfGetSymbolName;
import pa._v38.symbolization.representationsymbol.itfIsContainer;
import pa._v38.symbolization.representationsymbol.itfSymbol;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

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
					I5_1_receive
					{
	public static final String P_MODULENUMBER = "14";
	
	/** this holds the symbols from the environmental perception (IN I2.3) @since 21.07.2011 11:37:01 */
	private HashMap<eSymbolExtType, itfSymbol> moEnvironmentalData;
	/** this holds the symbols from the bodily perception (IN I2.4)  @since 21.07.2011 11:37:06 */
	private HashMap<eSymbolExtType, itfSymbol> moBodyData;
	/** OUT member of F14, this holds the converted symbols of the two perception paths and the recognized TPMs (OUT I2.6) @since 20.07.2011 10:26:23 */
	private ArrayList<clsThingPresentationMesh> moCompleteThingPresentationMeshList;
	
	ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP;
	
	ArrayList<clsInspectorPerceptionItem> moPerceptionSymbolsForInspectors;
	
	/** Input from Drive System */
	private ArrayList<clsDriveMesh> moDrives_IN;

	
	
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
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
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
		
		text += toText.mapToTEXT("moEnvironmentalData", moEnvironmentalData);
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
	protected void process_basic() {
	 
		
		PrepareSensorInformatinForInspector(moEnvironmentalData);
		
		
		//here also the body data should be processed, but nothing is coming from this path until now.
		
		// 1. Convert Neurosymbols to TPs/TPMs
		
		moEnvironmentalTP = new ArrayList<clsPrimaryDataStructureContainer>(); 
		for(itfSymbol oSymbol : moEnvironmentalData.values()){
			if(oSymbol!=null){
				for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
					//convert the symbol to a PDSC/TP
					clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
					moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
				}	
			}
		}
		
		// FIXME: delete this, if CM have changed the sensors to avoid the occurence of non-entites in moEnvironmentalData 
		ArrayList<clsPrimaryDataStructureContainer> oRemoveDS = new ArrayList<clsPrimaryDataStructureContainer>();
		for (clsPrimaryDataStructureContainer oEnvEntity : moEnvironmentalTP) {
			clsPrimaryDataStructureContainer oCheckEntity = oEnvEntity;
			if(oCheckEntity.getMoDataStructure().getMoContentType() != eContentType.ENTITY) {
				oRemoveDS.add(oCheckEntity);
			}
		}		
		for(clsPrimaryDataStructureContainer oDS: oRemoveDS){
			moEnvironmentalTP.remove(oDS);			
		}
		
		
		//add the perception of the floor, as we dont have a sensor detecting the floor
		
		//AW 20120522: Outcommented this part as it is never used later
		//clsPrimaryDataStructure oFloorDataStructure = (clsPrimaryDataStructure) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("FLOOR", "EMPTYSPACE"));
		//moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oFloorDataStructure,null));
		
		//prepared, but nothing is coming through so not much to do
		//TODO: bodyData should be associated to the self-entity as external associations 
		for(itfSymbol oSymbol : moBodyData.values()){
			if(oSymbol!=null){
				for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
					//convert the symbol to a PDSC/TP
					clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
					moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
				}	
			}
		}
		
		//AW 20120522: Add the SELF to the perception. Actually it should be added before and origin from the body
		//TODO @CM: Please adapt the SELF for your needs. 
		clsPrimaryDataStructure oSelfDataStructure = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<eContentType, Object, Object>(eContentType.ENTITY, new ArrayList<clsPhysicalRepresentation>(), eContent.SELF.toString())); 
		clsPrimaryDataStructureContainer oSelfContainer = new clsPrimaryDataStructureContainer(oSelfDataStructure,new ArrayList<clsAssociation>());
		//Add Position to SELF
		clsThingPresentation oPos = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.POSITION, ePhiPosition.CENTER.toString()));
		clsAssociationAttribute oPosAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.POSITIONASSOCIATION), oSelfDataStructure, oPos);
		oSelfContainer.addMoAssociatedDataStructure(oPosAss);
		((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oPosAss);
				
		//Add Distance to SELF
		clsThingPresentation oDist = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.DISTANCE, eRadius.NODISTANCE.toString()));
		clsAssociationAttribute oDistAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.DISTANCEASSOCIATION), oSelfDataStructure, oDist);
		oSelfContainer.addMoAssociatedDataStructure(oDistAss);
		((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oDistAss);
		
		//Add color and shape
		clsThingPresentation oColor = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.Color, new Color(255, 255, 191)));
		clsAssociationAttribute oColorAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), oSelfDataStructure, oColor);
		oSelfContainer.addMoAssociatedDataStructure(oColorAss);
		((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oColorAss);
		
		clsThingPresentation oShape = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ShapeType, "CIRCLE"));
		clsAssociationAttribute oShapeAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), oSelfDataStructure, oShape);
		oSelfContainer.addMoAssociatedDataStructure(oShapeAss);
		((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oShapeAss);
		
		moEnvironmentalTP.add(oSelfContainer);		
		
		
		//Variables
		ArrayList<clsPrimaryDataStructureContainer> oContainerWithTypes;
				
		//Workaround of Bug Eatable/Manipulatable sensors bug
		//TODO CM: Remove this function, if the eatable area objects are working.
		solveBUGFIXEATABLEAREA(moEnvironmentalTP);
				
		/* Construction of perceived images*/
		/* Assign objects from storage to perception */
		oContainerWithTypes = retrieveImages(moEnvironmentalTP);	
		
		//Convert all objects to enhanced TPMs 
		moCompleteThingPresentationMeshList = retrieveImagesTPM(oContainerWithTypes);
		
	}
	
	public ArrayList<clsInspectorPerceptionItem> GetSensorDataForInspectors(){
		return moPerceptionSymbolsForInspectors;
	}

	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 19.09.2012 16:00:47
	 *
	 * @param moEnvironmentalData2
	 */
	private void PrepareSensorInformatinForInspector( HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {

		moPerceptionSymbolsForInspectors.clear();
		
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
							
							if (oM.getName().equals("getExactDebugX")) {
								try {
									oInspectorItem.moExactX = Double.parseDouble( (String) oM.invoke(poSymbolObject,new Object[0]).toString() );
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
							
							if (oM.getName().equals("getExactDebugY")) {
								try {
									oInspectorItem.moExactY = Double.parseDouble((String) oM.invoke(poSymbolObject,new Object[0]).toString());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
							
							
							if (oM.getName().equals("getDebugSensorArousal")) {
								try {
									oInspectorItem.moSensorArousal = 1;
									//oInspectorItem.moSensorArousal = Double.parseDouble((String) oM.invoke(poSymbolObject,new Object[0]).toString());
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
//								} catch (IllegalAccessException e) {
//									e.printStackTrace();
//								} catch (InvocationTargetException e) {
//									e.printStackTrace();
								}
							}
							
							eContentType oContentTypeTP = eContentType.DEFAULT; 
							Object oContentTP = "DEFAULT";
							
							oContentTypeTP = eContentType.valueOf(removePrefix(oM.getName())); 
									
		
							if(oContentTypeTP.equals(eContentType.ObjectPosition)) {
								oContentTypeTP = eContentType.POSITION; 
								try {
									oInspectorItem.moPosition = oM.invoke(poSymbolObject,new Object[0]).toString();
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
						
						moPerceptionSymbolsForInspectors.add(oInspectorItem);	
					}
						
						}
					else {
						//TODO)
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
		send_I2_6(moCompleteThingPresentationMeshList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:55:55
	 * 
	 * @see pa.interfaces.send.I2_5_send#send_I2_5(java.util.ArrayList)
	 */
	@Override
	public void send_I2_6(ArrayList<clsThingPresentationMesh> poCompleteThingPresentationMeshList) {
		((I2_6_receive)moModuleList.get(46)).receive_I2_6(poCompleteThingPresentationMeshList);
		putInterfaceData(I2_6_send.class, poCompleteThingPresentationMeshList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:34
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {

		clsThingPresentationMesh oCandidateTPM = null;
		clsThingPresentationMesh oCandidateTPM_DM = null;
		
		clsDriveMesh oMemorizedDriveMesh = null;
		
		ArrayList<ArrayList<clsThingPresentationMesh>> oRankedCandidateTPMs = new ArrayList<ArrayList<clsThingPresentationMesh>>(); 
	
		ArrayList<clsAssociation> oRemoveAss = new ArrayList<clsAssociation>();
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResults = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
					
		ArrayList<clsThingPresentationMesh> poSearchPattern = new ArrayList<clsThingPresentationMesh>();
						
		clsThingPresentationMesh oUnknownTPM = null;
		
		ArrayList<clsPrimaryDataStructureContainer> oContainerWithTypes;
		
		// 1. Convert Neurosymbols to TPs/TPMs
		
				moEnvironmentalTP = new ArrayList<clsPrimaryDataStructureContainer>(); 
				for(itfSymbol oSymbol : moEnvironmentalData.values()){
					if(oSymbol!=null){
						for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
							//convert the symbol to a PDSC/TP
							clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
							moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
						}	
					}
				}
				
				// FIXME: delete this, if CM have changed the sensors to avoid the occurence of non-entities in moEnvironmentalData 
				ArrayList<clsPrimaryDataStructureContainer> oRemoveDS = new ArrayList<clsPrimaryDataStructureContainer>();
				for (clsPrimaryDataStructureContainer oEnvEntity : moEnvironmentalTP) {
					clsPrimaryDataStructureContainer oCheckEntity = oEnvEntity;
					if(oCheckEntity.getMoDataStructure().getMoContentType() != eContentType.ENTITY) {
						oRemoveDS.add(oCheckEntity);
					}
				}		
				for(clsPrimaryDataStructureContainer oDS: oRemoveDS){
					moEnvironmentalTP.remove(oDS);			
				}
		
				//AW 20120522: Add the SELF to the perception. Actually it should be added before and origin from the body
				//TODO @CM: Please adapt the SELF for your needs. 
				clsPrimaryDataStructure oSelfDataStructure = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<eContentType, Object, Object>(eContentType.ENTITY, new ArrayList<clsPhysicalRepresentation>(), eContent.SELF.toString())); 
				clsPrimaryDataStructureContainer oSelfContainer = new clsPrimaryDataStructureContainer(oSelfDataStructure,new ArrayList<clsAssociation>());
				//Add Position to SELF
				clsThingPresentation oPos = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.POSITION, ePhiPosition.CENTER.toString()));
				clsAssociationAttribute oPosAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.POSITIONASSOCIATION), oSelfDataStructure, oPos);
				oSelfContainer.addMoAssociatedDataStructure(oPosAss);
				((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oPosAss);
						
				//Add Distance to SELF
				clsThingPresentation oDist = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.DISTANCE, eRadius.NODISTANCE.toString()));
				clsAssociationAttribute oDistAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.DISTANCEASSOCIATION), oSelfDataStructure, oDist);
				oSelfContainer.addMoAssociatedDataStructure(oDistAss);
				((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oDistAss);
				
				//Add color and shape
				clsThingPresentation oColor = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.Color, new Color(255, 255, 191)));
				clsAssociationAttribute oColorAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), oSelfDataStructure, oColor);
				oSelfContainer.addMoAssociatedDataStructure(oColorAss);
				((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oColorAss);
				
				clsThingPresentation oShape = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.ShapeType, "CIRCLE"));
				clsAssociationAttribute oShapeAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), oSelfDataStructure, oShape);
				oSelfContainer.addMoAssociatedDataStructure(oShapeAss);
				((clsThingPresentationMesh)oSelfDataStructure).assignDataStructure(oShapeAss);
				
		moEnvironmentalTP.add(oSelfContainer);
				
		//Workaround of Bug Eatable/Manipulatable sensors bug
		//TODO CM: Remove this function, if the eatable area objects are working.
		solveBUGFIXEATABLEAREA(moEnvironmentalTP);
				
				
		// 2. drives activate exemplars. embodiment categorization criterion: activate entities from hallucinatory wish fulfillment 
		for (clsDriveMesh oSimulatorDrive : moDrives_IN) {
			for(clsAssociation oAssSimilarDrivesAss : oSimulatorDrive.getExternalMoAssociatedContent() ) {

				oMemorizedDriveMesh = (clsDriveMesh)oAssSimilarDrivesAss.getMoAssociationElementB();
				oCandidateTPM = oMemorizedDriveMesh.getActualDriveObject();
				
				// embodiment activation: source activation is already done in F57 (in hallucinatory wishfulfillment). so, just apply criterion activaion function
				// and add activated drive objects to oAppropriateTPMs		
				
				// criterion activation function
				oCandidateTPM.applyCriterionActivation(eActivationType.EMBODIMENT_ACTIVATION);
				
				//oCandidateTPMs.add(oCandidateTPM);
				
			}
		}
				
				
		// 3. similarity criterion. perceptual activation. memory-search
					
		// process EvironmentTPM
		for(clsPrimaryDataStructureContainer oEnvTPM :moEnvironmentalTP) {

			oUnknownTPM = (clsThingPresentationMesh) oEnvTPM.getMoDataStructure();				
											
					// 	separate internal attributes (which identify the entity) from external attributes (which are additional information)
					for (clsAssociation oIntAss: oUnknownTPM.getMoInternalAssociatedContent()) {
						if (isInternalAttribute(oIntAss.getMoAssociationElementB().getMoContentType().toString()) == false) {
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
						
		search(eDataType.TPM, poSearchPattern, oSearchResults);	
		
		//TODO: embed this code in search function
		for(ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResult :oSearchResults) {
			ArrayList<clsThingPresentationMesh> oSpecificCandidates = new ArrayList<clsThingPresentationMesh>();
			for(clsPair<Double,clsDataStructureContainer> oSearchItem: oSearchResult){				
				oCandidateTPM = (clsThingPresentationMesh)oSearchItem.b.getMoDataStructure();
				
				// TEST similarity activation: source activation
				
				oCandidateTPM.setCriterionActivationValue(eActivationType.SIMILARITY_ACTIVATION, oSearchItem.a);
			
				
				//  get other activation values. due to cloning, the same objects are different java objects and hence they have to be merged
				for (clsDriveMesh oSimulatorDrive : moDrives_IN) {
					for(clsAssociation oAssSimilarDrivesAss : oSimulatorDrive.getExternalMoAssociatedContent() ) {

						oMemorizedDriveMesh = (clsDriveMesh)oAssSimilarDrivesAss.getMoAssociationElementB();
						oCandidateTPM_DM = oMemorizedDriveMesh.getActualDriveObject();
						
						// is it the same TPM?
						if(oCandidateTPM_DM.getMoDS_ID() == oCandidateTPM.getMoDS_ID()){
							oCandidateTPM.takeActivationsFromTPM(oCandidateTPM_DM);
						}
						
					}
				}
				
				// add candidates to ranking
				oSpecificCandidates.add(oCandidateTPM);
			}
			Collections.sort( oSpecificCandidates, new clsActivationComperator() );
			oRankedCandidateTPMs.add(oSpecificCandidates);
		}
		
		System.out.println("");
		// 4. associatove activation. context criterion
		/*for(clsThingPresentationMesh oTPM : oAppropriateTPMs) {
			for(clsAssociation oTPMAss :oTPM.getExternalMoAssociatedContent()){
				if(oTPMAss.getMoContentType() == eContentType.ASSOCIATIONPRI || oTPMAss.getMoContentType() == eContentType.ASSOCIATIONTEMP ) {
					((clsThingPresentationMesh)oTPMAss.getMoAssociationElementB()).applyActivation(eActivationType.ASSOCIATIVE_ACTIVATION, oTPM.getOverallActivationLevel(), oTPMAss.getMrWeight());
				}
			}
			
		}*/
		
		// 5. Rank appropriate TPMs --> not needed, since a TreeSet+Comparator is used. but in the course of ranking, the aggregation function is applied

		
//		for(clsThingPresentationMesh oCandidate : oCandidateTPMs) {
//			
//		}
				
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


	/**
	 * This is a temporary bugfix for the problem if there are objects in the eatable area. They are not loaded with all attributes and are recognized false
	 * 
	 * (wendt)
	 *
	 * @since 21.11.2011 16:30:06
	 *
	 * @param poEnvironmentalPerception_IN
	 */
	private void solveBUGFIXEATABLEAREA(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalPerception_IN) {
		//Exchange all objects in the EATABLE AREA with the objects in the MANIPULATEABLE AREA
		ArrayList<clsPrimaryDataStructureContainer> oEatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		//ArrayList<clsPrimaryDataStructureContainer> oManipulatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		
		//Search in the input for an object with location EATABLE and add them to a new list
		for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception_IN) {
			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
				//Go through all associated structures
				for (clsAssociation oAss : ((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoInternalAssociatedContent()) {
					if (oAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE)==true && (((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals(eDistance.EATABLE)==true)) {
						oEatableList.add(oContainer);
					}
				}
				
			}
		}
		
//		Search for all objects with the area MANIPULATABLE and add them to a new list
//		for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception_IN) {
//			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
//				//Go through all associated structures
//				for (clsAssociation oAss : ((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoAssociatedContent()) {
//					if (oAss.getLeafElement().getMoContentType().equals("LOCATION")==true && ((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals("MANIPULATABLE")==true) {
//						oManipulatableList.add(oContainer);
//					}
//				}
//				
//			}
//		}
		
		//Search for all elements in the EATABLE area for the same content in the MANIPULATABLE area
		for (clsPrimaryDataStructureContainer oEContainer : oEatableList) {
			boolean bFound=false;
			for (clsPrimaryDataStructureContainer oMContainer : poEnvironmentalPerception_IN) {
				//When found, add all TP, which are not location to the EATABLE area
				if (oMContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
					if (((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoContent().equals(((clsThingPresentationMesh)oMContainer.getMoDataStructure()).getMoContent())) {
						for (clsAssociation oAss : ((clsThingPresentationMesh)oMContainer.getMoDataStructure()).getMoInternalAssociatedContent()) {
							if (oAss.getLeafElement().getMoContentType().equals("Color")) {
								((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoInternalAssociatedContent().add(oAss);
								bFound=true;
								break;
							}
						}
					}
				}

				if (bFound==true) {
					break;
				}
			}
		
			//Replace the EATABLE through another distance and position
			boolean bconv=false;
			for (clsAssociation oAss : ((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoInternalAssociatedContent()) {
				if (oAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE)==true && ((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals(eDistance.EATABLE)==true) {
					((clsThingPresentation)oAss.getLeafElement()).setMoContent(eDistance.NEAR);
					bconv=true;
				}
			}
			
			if (bconv==true) {
				clsThingPresentation oPos = clsDataStructureGenerator.generateTP(new clsPair<eContentType,Object>(eContentType.POSITION, ePhiPosition.CENTER));
				clsAssociationAttribute oPosAss = (clsAssociationAttribute) clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.POSITIONASSOCIATION, (clsPrimaryDataStructure) oEContainer.getMoDataStructure(), oPos, 1.0);
				((clsThingPresentationMesh)oEContainer.getMoDataStructure()).getMoInternalAssociatedContent().add(oPosAss);
			}
		}
		
		
		ArrayList<clsPrimaryDataStructureContainer> oManipulatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		//ArrayList<clsPrimaryDataStructureContainer> oManipulatableList = new ArrayList<clsPrimaryDataStructureContainer>();
		
		//Search in the input for an object with location EATABLE and add them to a new list
		for (clsPrimaryDataStructureContainer oContainer : poEnvironmentalPerception_IN) {
			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
				//Go through all associated structures
				for (clsAssociation oAss : ((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoInternalAssociatedContent()) {
					if (oAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE)==true && (((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals(eDistance.MANIPULATEABLE)==true)) {
						((clsThingPresentation)oAss.getLeafElement()).setMoContent(eDistance.NEAR);
						//oManipulatableList.add(oContainer);
					}
				}
				
			}
		}
		
	}
	
	/**
	 * DOCUMENT (zeilinger) - retrieves memory matches for environmental input; objects are identified and exchanged by their 
	 * stored correspondance.
	 *
	 * @author zeilinger
	 * 14.03.2011, 14:27:30
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> retrieveImages(ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal;
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		ArrayList<clsThingPresentationMesh> poSearchPattern = new ArrayList<clsThingPresentationMesh>();
		
		// separate internal from external associations. for search just internal associations are relevant
				for(clsPrimaryDataStructureContainer oEnvTPM :oPerceivedImage_IN) {
									
						if (oEnvTPM.getMoDataStructure().getMoContentType() == eContentType.ENTITY) {
										
							clsThingPresentationMesh oUnknownTPM = (clsThingPresentationMesh) oEnvTPM.getMoDataStructure();				
													
							ArrayList<clsAssociation> oRemoveAss = new ArrayList<clsAssociation>();
							// 	separate internal attributes (which identify the entity) from external attributes (which are additional information)
							for (clsAssociation oIntAss: oUnknownTPM.getMoInternalAssociatedContent()) {
								if (isInternalAttribute(oIntAss.getMoAssociationElementB().getMoContentType().toString()) == false) {
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
				}
				
		//Assign TP to the identified object in PerceivedImage_IN
		search(eDataType.TP, poSearchPattern, oSearchResult); 
		//Take the best match for object
		oRetVal = createImage(oSearchResult);
		
		//Assign drive meshes to each found image		
		assignDriveMeshes(oRetVal); // associated emotions are also fetched, because they have the same nBinaryValue as DM
		//INFO AW: Why are the external TPs necessary? No. It was only thought for the self to load default values.
		//These values are now added in F14
		//assignExternalTPAssociations(oRetVal);
		
		//assignEmotions(oRetVal);
		
		return oRetVal;
	}	
	
	private ArrayList<clsThingPresentationMesh> retrieveImagesTPM(ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		for (clsPrimaryDataStructureContainer oContainer : oPerceivedImage_IN) {
			if (oContainer.getMoDataStructure() instanceof clsThingPresentationMesh) {
				clsThingPresentationMesh oTPM = (clsThingPresentationMesh) oContainer.getMoDataStructure();
				oTPM.setMoExternalAssociatedContent(oContainer.getMoAssociatedDataStructures());
				oRetVal.add(oTPM);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Add associations 
	 * wendt
	 *
	 * @since 18.08.2011 11:22:36
	 *
	 * @param poPerception
	 */
	private void assignDriveMeshes(ArrayList<clsPrimaryDataStructureContainer> poPerception) {
		
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
	
		//oSearchResult = search(eDataType.DM, poPerception);
		
		search(eDataType.DM, poPerception, oSearchResult);
		//for (ArrayList<clsPair<Double,clsDataStructureContainer>> oRes : oSearchResult) {
		addAssociations(oSearchResult, poPerception);
		//}
		//addAssociations(oSearchResult, poPerception);
	}
	
	/**
	 * This method adds the associated items from the search result to the
	 * associatedDataStructures of the (perception) container.
	 *
	 * @author Marcus Zottl (e0226304)
	 * 22.06.2011, 18:29:38
	 *
	 * @param poSearchResult	- the result of a MemorySearch in the KnowledgeBase 
	 * @param poPerception		- the perception to which the items in the search
	 * result should be added.
	 */
	private void addAssociations(
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
			ArrayList<clsPrimaryDataStructureContainer> poPerception) {

		//oEntry: Data structure with a double association weight and an object e. g. CAKE with its associated DM.
		if (poSearchResult.size()!=poPerception.size()) {
			try {
				throw new Exception("F14: addAssociations, Error, different Sizes");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (int i=0;i<poSearchResult.size();i++) {
			ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchPair = poSearchResult.get(i);
			clsPrimaryDataStructureContainer oPC = poPerception.get(i);
			if (oSearchPair.size()>0) {
				poPerception.get(i).getMoAssociatedDataStructures().addAll(oSearchPair.get(0).b.getMoAssociatedDataStructures());
			}
			
		}
		
		/*for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult) {
			if(oEntry.size() > 0){
				//get associated DM from a the object e. g. CAKE
				ArrayList<clsAssociation> oAssociationList = oEntry.get(0).b.getMoAssociatedDataStructures();
				//Add associated DM to the input list. Now the list moAssociatedDataStructures contains DM and ATTRIBUTES
				poPerception.getMoAssociatedDataStructures().addAll(oAssociationList);
			}
		}*/
	}	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.03.2011, 23:06:52
	 *
	 */
	private ArrayList<clsPrimaryDataStructureContainer> createImage(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
		
				int nEntryIndex = poSearchResult.indexOf(oEntry); 
				clsPrimaryDataStructureContainer oNewImage;
				clsPrimaryDataStructureContainer oPerceptionEntry =  moEnvironmentalTP.get(nEntryIndex); 
				
				if(oEntry.size() > 0){
					oNewImage = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
					oNewImage.setMoAssociatedDataStructures(oPerceptionEntry.getMoAssociatedDataStructures()); 
					mergePerceptionAndKnowledge(oNewImage, oPerceptionEntry);
					
					oRetVal.add(oNewImage);
				}
			}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 20:39:09
	 *
	 * @param oSearchResult
	 * @param poEnvironmentalInput
	 */
	private void mergePerceptionAndKnowledge(clsPrimaryDataStructureContainer poNewImage,
												   clsPrimaryDataStructureContainer poPerceptionEntry) {

		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oAssociatedElements = new ArrayList<clsDataStructurePA>(); 
		
		if(poNewImage.getMoDataStructure() instanceof clsThingPresentationMesh 
			&& poPerceptionEntry.getMoDataStructure() instanceof clsThingPresentationMesh){

			extractUnknownData(oAssociatedElements, poPerceptionEntry, poNewImage); 
			search(eDataType.UNDEFINED, oAssociatedElements, oSearchResult); 
		 	addAttributeAssociations(oSearchResult, poNewImage); 
		 }
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
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:27:04
	 *
	 * @param oUnknownData
	 * @param poPerceptionEntry
	 * @param poNewImage 
	 */
	private void extractUnknownData(ArrayList<clsDataStructurePA> poUnknownData,
			clsPrimaryDataStructureContainer poPerceptionEntry, 
			clsPrimaryDataStructureContainer poNewImage) {
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).getMoInternalAssociatedContent()){
	 		
	 		if( !((clsThingPresentationMesh)poNewImage.getMoDataStructure()).contain(oEntry.getMoAssociationElementB())){
	 			poUnknownData.add(oEntry.getMoAssociationElementB()); 
	 		}
	 	}
		
		for(clsAssociation oEntry : ((clsThingPresentationMesh)poPerceptionEntry.getMoDataStructure()).getExternalMoAssociatedContent()){
	 		
	 		if( !((clsThingPresentationMesh)poNewImage.getMoDataStructure()).contain(oEntry.getMoAssociationElementB())){
	 			poUnknownData.add(oEntry.getMoAssociationElementB()); 
	 		}
	 	}
	}

	/**
	 * Add corresponding attribute associations to a container
	 *
	 * @author zeilinger
	 * 15.03.2011, 10:25:24
	 *
	 * @param oSearchResult
	 * @param poNewImage
	 */
	private void addAttributeAssociations(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult,
						clsPrimaryDataStructureContainer poNewImage) {
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			if(oEntry.size() > 0){
				clsPrimaryDataStructureContainer oBestMatch = (clsPrimaryDataStructureContainer)extractBestMatch(oEntry); 
				clsAssociation oAssociation = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(
							-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
							(clsThingPresentationMesh)poNewImage.getMoDataStructure(), 
							(clsThingPresentation)oBestMatch.getMoDataStructure());
				poNewImage.getMoAssociatedDataStructures().add(oAssociation);
			}
		}
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
}
