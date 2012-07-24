/**
 * F57_MemoryTracesForDrives.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author hinterleitner
 * 05.08.2011, 10:20:03
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I4_1_receive;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * DOCUMENT (hinterleitner)  
 * 
 * @author hinterleitner
 * 05.08.2011, 10:23:13
 * 
 */
public class F57_MemoryTracesForDrives extends clsModuleBaseKB 
		implements I4_1_receive,  I5_1_send{

	public static final String P_MODULENUMBER = "57";
	//private clsThingPresentationMesh moPerceptionalMesh_IN;	//AW 20110521: New containerstructure. Use clsDataStructureConverter.TPMtoTI to convert to old structure
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;	//AW 20110621: Associated Memories
	private ArrayList<clsDriveMesh> moDriveCandidates;
	private  ArrayList<clsDriveMesh> moDrivesAndTraces_OUT;
	
	private double mrThresholdMatchFactor = 0.5;
	private double mrThresholdPleasure = 0.2;
	
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
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
			super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);

		applyProperties(poPrefix, poProp); 
		moDrivesAndTraces_OUT = new  ArrayList<clsDriveMesh>();	//If no drive candidate is there, then it is initialized
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
		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
		//text += toText.listToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);	
		//text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		
		
		return text;
	}
	
//	/* (non-Javadoc)
//	 *
//	 * @author zeilinger
//	 * 04.05.2011, 09:07:36
//	 * 
//	 * @see pa._v38.interfaces.modules.I5_7_receive#receive_I5_7(java.util.ArrayList)
//	 */
//	/* Comment TD from Mail: also deepCopy ist ganz ganz ganz ganz ganz Ö ganz boeses voodoo. 
//	 * In diesem fall ist das problem, dass du 2 cast in einem machst/machen muﬂt. 
//	 * Und der ist so nicht checkbar (afaik). In diesem fall einfach suppresswarning machen 
//	 * (ist bei deepcopy nicht schlimm ñ kommt innerhalb der funktion dauernd vor).
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
		moDriveCandidates = poDriveCandidates; 
	
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
		
		moDrivesAndTraces_OUT = attachDriveCandidates(moDriveCandidates);
	
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

	private ArrayList<clsDriveMesh> attachDriveCandidates(ArrayList<clsDriveMesh> poDriveCandidates) { 
		
		ArrayList<clsDriveMesh> oRetVal = new ArrayList<clsDriveMesh>();
		ArrayList<clsAssociation> oAssSimilarDMs = null;
		ArrayList<clsAssociation> oMemoryDMAssociations =new ArrayList<clsAssociation>();
				
		clsThingPresentationMesh oDriveObject = null;
		clsThingPresentationMesh  oDriveAim = null;
		
		double rCurrentMatchFactor = 0.0;
		double rMaxMatchfactor = 0.0;

		
		// for each simulator-DM (should be 16 for now)
		for (clsDriveMesh oSimulatorDM : poDriveCandidates) {
						
				oAssSimilarDMs = new ArrayList<clsAssociation>();
				
			
				ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
				
				ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
				poSearchPattern.add(oSimulatorDM);
				
				// search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
				search(eDataType.TPM, poSearchPattern, oSearchResult);
				
				rMaxMatchfactor = 0.0;
				rCurrentMatchFactor = 0.0;
					
				for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
					// for results of similar memory-DMs (should be various similar DMs)
					for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList) {
						// take matchfactor for associating simulator-dm with memory-dm. pleasureValue is implicitly included in matchfactor
						rCurrentMatchFactor = oSearchPair.a; 
						if( rCurrentMatchFactor > mrThresholdMatchFactor) {
							
							// get similar memory-dm
							clsDriveMesh oMemoryDM = (clsDriveMesh)oSearchPair.b.getMoDataStructure();

							// get associations of memory-dm (= drive object + drive aim). this is needed because search do not return the dm with associations
							oMemoryDMAssociations = oSearchPair.b.getMoAssociatedDataStructures();
							
							// add associations to memory-dm
							//oMemoryDM.addInternalAssociations(oMemoryDMAssociations);
							
							// add similar memory-DMs to simulator-DM (via primaryDM-Assoc) 
							// weighting of asscoiation-weight with QoA
							oAssSimilarDMs.add(clsDataStructureGenerator.generateASSOCIATIONPRIDM(eContentType.ASSOCIATIONPRIDM, oSimulatorDM, oMemoryDM, rCurrentMatchFactor*oMemoryDM.getQuotaOfAffect()));
							
							// take  drive object+drive aim of best match 
							if( rCurrentMatchFactor > rMaxMatchfactor) {
								rMaxMatchfactor = rCurrentMatchFactor; 
								oDriveObject = oMemoryDM.getActualDriveObject();
								oDriveAim = oMemoryDM.getActualDriveAim();
							}
						}
					}
				}
				
				// if no memory-dm is similar
				if (oAssSimilarDMs == null) {
					// no error if simulator-dm does not have similar memroy-dms
				}
				
				oSimulatorDM.addExternalAssociations(oAssSimilarDMs);
				
				try {
					oSimulatorDM.associateActualDriveObject(oDriveObject, 0.0);
					oSimulatorDM.associateActualDriveAim(oDriveAim, 0.0);
				}
				catch(Exception e){
					
				}
				
				
				oRetVal.add(oSimulatorDM);
				
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


}
