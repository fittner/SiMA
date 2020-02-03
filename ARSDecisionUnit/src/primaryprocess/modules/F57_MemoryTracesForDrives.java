/**
 * F57_MemoryTracesForDrives.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author hinterleitner
 * 05.08.2011, 10:20:03
 */
package primaryprocess.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfGraphCompareInterfaces;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.I4_1_receive;
import modules.interfaces.I5_1_receive;
import modules.interfaces.I5_1_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructureContainer;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * DOCUMENT (hinterleitner)  
 * 
 * @author hinterleitner
 * 05.08.2011, 10:23:13
 *  
 */
public class F57_MemoryTracesForDrives extends clsModuleBaseKB 
		implements I4_1_receive,  I5_1_send, itfInspectorGenericDynamicTimeChart, itfGraphCompareInterfaces{

	public static final String P_MODULENUMBER = "57";
	
	public static final String P_THRESHOLD_MATCH_FACTOR = "THRESHOLD_MATCH_FACTOR";
	public static final String P_THRESHOLD_PLEASURE_FACTOR = "THRESHOLD_PLEASURE_FACTOR";

    private static itfModuleMemoryAccess poLongTermMemory2 = null;
	
	//private clsThingPresentationMesh moPerceptionalMesh_IN;	//AW 20110521: New containerstructure. Use clsDataStructureConverter.TPMtoTI to convert to old structure
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;	//AW 20110621: Associated Memories
	private ArrayList<clsDriveMesh> moDriveCandidates_IN;

	private  ArrayList<clsDriveMesh> moDrivesAndTraces_OUT;
	
	private double mrThresholdMatchFactor;
	private double mrThresholdPleasure;
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moTimeChartData;
	
	private clsShortTermMemoryMF moSTM_Learning;
	private clsPair<Double,clsDriveMesh> oBigDrive=new clsPair(0.0,null);
	double satisfaction=0;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * DOCUMENT (zeilinger) 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:52:25
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F57_MemoryTracesForDrives(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poLongTermMemory, clsShortTermMemoryMF poSTM_Learning, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
			super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);

		applyProperties(poPrefix, poProp); 
		poLongTermMemory2 = poLongTermMemory;
		
		mrThresholdMatchFactor= poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_THRESHOLD_MATCH_FACTOR).getParameterDouble();
		mrThresholdPleasure=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_THRESHOLD_PLEASURE_FACTOR).getParameterDouble();

		
		moDrivesAndTraces_OUT = new  ArrayList<clsDriveMesh>();	//If no drive candidate is there, then it is initialized
		moTimeChartData =  new HashMap<String, Double>(); //initialize charts
		moSTM_Learning = poSTM_Learning;
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
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.listToTEXT("moDrivesAndTraces_OUT", moDrivesAndTraces_OUT);
		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates_IN);
		//text += toText.listToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);	
		//text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += "-NEW-SATISFACTION-MEMORY-------------------------------------------------\n";
		text += "OBJECT: \t";
		if(this.moSTM_Learning.getActualStep()>229)
		{
		    text += "f";
		}
		if(this.moSTM_Learning.moShortTermMemoryMF.get(0)!=null)
		{
		    if(this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects().size()>0)
	        {
		        text += this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects().get(0).b;
	        }
		    else
		    {
		        text += "#+#";
		    }
		}
        else
        {
            text += "#-#";
        }
		text += "--[ASS:";
        if(this.moSTM_Learning.moShortTermMemoryMF.get(0)!=null)
        {
            if(this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects().size()>0)
            {
                text += this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects().get(0).a+"]";
            }
        }
		text += "\nDRIVE: \t"+oBigDrive.b;
		text += "\nSATISFACTION:"+satisfaction;
		return text;
	}
	
//	/* (non-Javadoc)
//	 *
//	 * @author zeilinger
//	 * 04.05.2011, 09:07:36
//	 * 
//	 * @see pa._v38.interfaces.modules.I5_7_receive#receive_I5_7(java.util.ArrayList)
//	 */
//	/* Comment TD from Mail: also deepCopy ist ganz ganz ganz ganz ganz … ganz boeses voodoo. 
//	 * In diesem fall ist das problem, dass du 2 cast in einem machst/machen mußt. 
//	 * Und der ist so nicht checkbar (afaik). In diesem fall einfach suppresswarning machen 
//	 * (ist bei deepcopy nicht schlimm – kommt innerhalb der funktion dauernd vor).
//	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void receive_I5_7(clsThingPresentationMesh poPerceptionalMesh) {
//		try {
//			//moPerceptionalMesh_IN = (clsThingPresentationMesh)poPerceptionalMesh.cloneGraph();
//			moPerceptionalMesh_IN = (clsThingPresentationMesh)poPerceptionalMesh.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}
//		//moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
//	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:07:36
	 * 
	 * @see pa._v38.interfaces.modules.I4_1_receive#receive_I4_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I4_1(ArrayList<clsDriveMesh> poDriveCandidates) {
		moDriveCandidates_IN = poDriveCandidates;
		moDrivesAndTraces_OUT =  (ArrayList<clsDriveMesh>) deepCopy(poDriveCandidates); 
	
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.05.2012, 13:46:36
	 * 
	 * @see pa._v38.interfaces.modules.I5_6_receive#receive_I5_6(java.util.ArrayList)
	 */
	/*@Override
	//v38g has no interface between F46 and F57
	public void receive_I5_6(clsThingPresentationMesh poPerceptionalMesh) {
		moPerceptionalMesh_IN = poPerceptionalMesh; 
	
	}
*/
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		moDrivesAndTraces_OUT = hallucinatoryWishfulfillment(moDrivesAndTraces_OUT);
		clsShortTermMemoryMF test = new clsShortTermMemoryMF(poLongTermMemory2);
		
		// create time Chart Data
		for( clsDriveMesh oDriveMeshEntry:moDrivesAndTraces_OUT){
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moTimeChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moTimeChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
	
	}

	/**
	 * DOCUMENT (schaat) - Search for TPMs that are associated with the different drive candidates. 
	 * @param <clsPhysicalDataStructure>
	 *
	 * @since 01.07.2011 10:24:34
	 *
	 * the ARSIN hallucinates possible drive objects and drive aims (which he remembers from memory).
	 * Therefore all similar DMs from memory are associated to the simulator-dm. from these appropriate dms the one with the
	 * highest QoA is chosen and its drive object+drive aim is taken. in the end, the simulator-dm gets the best (according to the pleasure-principle)
	 * drive-aim+drive-object. Since this choice can be revised (by defense mechansims and SP) all similar memory-dms keep associated as external
	 * associations 
	 */

	private ArrayList<clsDriveMesh>  hallucinatoryWishfulfillment(ArrayList<clsDriveMesh> poDriveCandidates) { 
		
		ArrayList<clsDriveMesh> oRetVal = new ArrayList<clsDriveMesh>();
		ArrayList<clsAssociation> oAssSimilarDMs = null;
		ArrayList<clsAssociation> oMemoryDMAssociations =new ArrayList<clsAssociation>();
				
		clsThingPresentationMesh oDriveObject = null;
		clsThingPresentationMesh oDriveObjectActivated = null;
		
		clsThingPresentationMesh  oDriveAim = null;
		
		double rCurrentMatchFactor = 0.0;
		
		double rCurrentDecisionFactor= 0.0;
		
		double rMaxDecisionfactor = 0.0;

		double rSatisfactionOfActualDM = 0;
		
		double rTotSatisfactionOfActualDMs = 0;
		
		double rSumSimilarDMsQoA = 0;
		double rQoA = 0;
		
        boolean found = false;
        for (clsDriveMesh oSimulatorDM : poDriveCandidates)
        {
            this.moSTM_Learning.moShortTermMemoryMF.get(0).setLearningPartDMs(oSimulatorDM);
        }
        ArrayList<clsPair<Double,clsDriveMesh>> DMPairs = new ArrayList<clsPair<Double,clsDriveMesh>>();
		for(clsDriveMesh DMold : this.moSTM_Learning.moShortTermMemoryMF.get(1).getLearningPartDMs())
        {
            for(clsDriveMesh DMnew : this.moSTM_Learning.moShortTermMemoryMF.get(0).getLearningPartDMs()){
                if(  DMnew.compareTo(DMold) > 0.0)
                {
                    DMnew.setActiveTime(DMold.getActiveTime()+1);
                    if(DMnew.getQuotaOfAffect() != DMold.getQuotaOfAffect())
                    {
                        rQoA = DMnew.getQuotaOfAffect() - DMold.getQuotaOfAffect();
                        DMPairs.add(new clsPair(rQoA,DMnew));
                        DMnew.setQoAchange(rQoA);
                        if (  (rQoA >  0.015)
                           || (rQoA < -0.015))
                        {
                            found = true;
                        }
                    }
                }
            }
        }
		
		clsPair<Double,clsDriveMesh> DMPairB4 = null;
		clsPair<Double,clsDriveMesh> DMPairBig=null;
		for(clsPair<Double,clsDriveMesh> DMPair : DMPairs)
		{
		    if (DMPairB4!=null)
            {   if(DMPairB4.a < DMPair.a )
    		    {
                    DMPairBig = DMPair;
    		    }
                else
                {
                    DMPairBig = DMPairB4;
                }
            }
		    DMPairB4 = DMPair;
		}
        if(DMPairBig!=null)
        {   
            if(oBigDrive.b==null || oBigDrive.b.compareTo(DMPairBig.b)<1.0)
            {
                oBigDrive.b = DMPairBig.b;
                oBigDrive.a += DMPairBig.b.getQuotaOfAffect();
            }
            satisfaction = oBigDrive.a - DMPairBig.b.getQuotaOfAffect();
            if(satisfaction < 0)
            {
                satisfaction = 0;
            }
        }
		//oBigDrive=null;
		// Sortieren der Liste
		
        // for each simulator-DM (should be 16 for now)
		for (clsDriveMesh oSimulatorDM : poDriveCandidates) {
				
			 	oDriveObject = null;
				oDriveAim = null;
				
				rSatisfactionOfActualDM = 0;
				
				oAssSimilarDMs = new ArrayList<clsAssociation>();
				
			
				ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
				
				ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
				poSearchPattern.add(oSimulatorDM);
				
				// search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
				oSearchResult = this.getLongTermMemory().searchEntity(eDataType.TPM, poSearchPattern);
				

				
				rMaxDecisionfactor = 0.0;
				rCurrentMatchFactor = 0.0;
				rCurrentDecisionFactor= 0.0;
				rSumSimilarDMsQoA = 0.0;
				
				clsDriveMesh oMemoryDM = null;
					
				// get rSumSimilarDMsQoA to calculate cathexis (see below)
				for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
					// for results of similar memory-DMs (should be various similar DMs)
					for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList) {
					    oMemoryDM = ((clsDriveMesh)oSearchPair.b.getMoDataStructure());
					    if (oMemoryDM.getContentType().equals(eContentType.MEMORIZEDDRIVEREPRESENTATION)){
				            // Neues QoA Auflösen und auf alten QoA speichern
//					        oMemoryDM.setNewQoA();
					        
					        rSumSimilarDMsQoA += oMemoryDM.getQuotaOfAffect();
					    }
					}
				}
				
				for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
					// for results of similar memory-DMs (should be various similar DMs)
					for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList) {
						// take matchfactor for associating simulator-dm with memory-dm. pleasureValue is implicitly included in matchfactor
						
						// get similar memory-dm
						oMemoryDM = (clsDriveMesh)oSearchPair.b.getMoDataStructure();
						
						//Add by AW: In order to use drive meshes for images, only the ones, which are in the "semantic" memory can be used. The drive meshes in the "episodic" part
						//of the memory cannot be used for this purpose
						//FIXME: SOMEBODY! In Protege, drive meshes are put on PRIINSTANCES. They should be replaced by emotions, as drive meshes only exist for entity types and for images,
						//only emotions should be used. This is a hack by AW, in order to finish the phd implementation within 2012. This is a good student task! Contact AW for more info.
						if (oMemoryDM.getContentType().equals(eContentType.MEMORIZEDDRIVEREPRESENTATION)) {
							// weight with QoA, otherwise all DMs are handled the same if they all have a higher QoA than the simulatorDM (often the case) 
							rCurrentMatchFactor = oSearchPair.a; 
							rCurrentDecisionFactor = oSearchPair.a * oMemoryDM.getQuotaOfAffect(); 
							
							// take the best match
							
							if( rCurrentDecisionFactor > mrThresholdMatchFactor) {
								
								// get associations of memory-dm (= drive object + drive aim). this is needed because search do not return the dm with associations
								// oMemoryDMAssociations = oSearchPair.b.getMoAssociatedDataStructures();
								
								// add associations to memory-dm
								//oMemoryDM.addInternalAssociations(oMemoryDMAssociations);
								
								// add similar memory-DMs to simulator-DM (via primaryDM-Assoc) 
								// weighting of asscoiation-weight with QoA
								
								oAssSimilarDMs.add(clsDataStructureGenerator.generateASSOCIATIONPRIDM(eContentType.ASSOCIATIONPRIDM, oSimulatorDM, oMemoryDM, rCurrentMatchFactor));
																
								// embodiment activation: source activation function: memory- drive object gets activation (how good would this drive object satisfy actual DM?)
								if(oMemoryDM.getActualDriveObject() != null) {
									oDriveObjectActivated = oMemoryDM.getActualDriveObject();
									
									//TODO: CATHEXIS STATT embodimentActivation?? test mit perception
									oDriveObjectActivated.applyEmbodimentActivation(poDriveCandidates);
									//oDriveObjectActivated.applySourceActivation(eActivationType.EMBODIMENT_ACTIVATION, rCurrentMatchFactor, oSimulatorDM.getQuotaOfAffect());
									
									// cathexis of potential drive object												
									oMemoryDM.cathexis(oSimulatorDM.getQuotaOfAffect() * (oMemoryDM.getQuotaOfAffect()/rSumSimilarDMsQoA));
								}
															
								// take  drive object+drive aim of best match 
								if( rCurrentDecisionFactor > rMaxDecisionfactor) {
									rMaxDecisionfactor = rCurrentDecisionFactor; 								
									oDriveAim = oMemoryDM.getActualDriveAim();
									oDriveObject = oMemoryDM.getActualDriveObject();
									// Fittner: Not needed?
									// rSatisfactionOfActualDM = rCurrentDecisionFactor;
								}
								 
								
							}
						}
					}
				}
				
				// if no memory-dm is similar
				if (oAssSimilarDMs == null) {
					// no error if simulator-dm does not have similar memory-dms
				}
				
				oSimulatorDM.addExternalAssociations(oAssSimilarDMs);
				
				try {
					// just consider drives with driveobject and -aim
					if(oDriveObject != null && oDriveAim != null){
						oSimulatorDM.setActualDriveObject(oDriveObject, 1.0);		
						oSimulatorDM.setActualDriveAim(oDriveAim, 1.0);	
						oSimulatorDM.setMoContentType(eContentType.DRIVEREPRESENTATION);
						oRetVal.add(oSimulatorDM);												
					}
					else {
						
					}
				}
				catch(Exception e){
					
				}
				
				
				
		}
		return oRetVal;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_1(moDrivesAndTraces_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs)";
		// TODO (muchitsch) - give a en description
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:07:36
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_1(ArrayList<clsDriveMesh> poData) {
		
		((I5_1_receive)moModuleList.get(49)).receive_I5_1(poData); 
		((I5_1_receive)moModuleList.get(14)).receive_I5_1(poData); 
		
		putInterfaceData(I5_1_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {

		return 1.0;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {

		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {

		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {

		return "";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moTimeChartData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moTimeChartData.keySet());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:00:39 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged=false;
		
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 18, 2012 2:42:46 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesRecv() {
		
		return getInterfacesRecv();
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 18, 2012 2:42:46 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesSend() {
		
		return getInterfacesSend();
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

}
