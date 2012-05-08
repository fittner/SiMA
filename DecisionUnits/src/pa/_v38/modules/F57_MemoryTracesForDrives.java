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
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsDataStructureTools;
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
		implements I4_1_receive, I5_1_send{

	public static final String P_MODULENUMBER = "57";
	private clsThingPresentationMesh moPerceptionalMesh_IN;	//AW 20110521: New containerstructure. Use clsDataStructureConverter.TPMtoTI to convert to old structure
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;	//AW 20110621: Associated Memories
	private ArrayList<clsDriveMesh> moDriveCandidates;
	private  ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrivesAndTraces_OUT;
	
	private double mrThreshold = 0.5;
	
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
		moDrivesAndTraces_OUT = new  ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>();	//If no drive candidate is there, then it is initialized
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
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		
		
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
		moDriveCandidates = poDriveCandidates; 
	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//FIXME hier muss es wieder funktionieren, perceptual Mesh gibt es beiv38g ja nciht mehr
		//moDrivesAndTraces_OUT = attachDriveCandidates(moDriveCandidates, moPerceptionalMesh_IN);
		
		
	}

	/**
	 * DOCUMENT (hinterleitner) - Search for TPMs that are associated with the different drive candidates. 
	 * @param <clsPhysicalDataStructure>
	 *
	 * @since 01.07.2011 10:24:34
	 *
	 */
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> attachDriveCandidates(ArrayList<clsDriveMesh> poDriveCandidates, clsThingPresentationMesh poPerceptionalMesh) { 
		//initializing of the list, because it cannnot be null
		ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> oRetVal = new ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>();
		
		//Get all DMs from Perception
		ArrayList<clsAssociationDriveMesh> oAssDMList = clsDataStructureTools.getAllDMInMesh(poPerceptionalMesh);
		
		//1. Compare drive meshes with drive meshes in the perception	
		for (clsDriveMesh oDM : poDriveCandidates) {
			clsDataStructurePA oDS=null;
			double rCurrentMatchFactor = 0.0;
			double rMaxMatchfactor = 0.0;
			double rMaxPleasurefactor = 0.0;

			//1. Compare candidate drive meshes with drive meshes from the perception
			//For each DM in the memory
			for (clsAssociationDriveMesh oAss : oAssDMList) {
				if (oAss.getLeafElement()instanceof clsDriveMesh) {
					//Get match categoriesfactor
					
					
					
					//FIXME CM: AW: This method does not work very well as the categories are not properly set. This has to be fixed. Until then, the comparison is made with the content instead
					//rCurrentMatchFactor = ((clsDriveMesh)oAss.getLeafElement()).matchCathegories(oDM);
					
					if (((clsDriveMesh)oAss.getLeafElement()).getMoContent().equals(oDM.getMoContent())) {
						rCurrentMatchFactor = 1.0;
					} else {
						rCurrentMatchFactor = 0.0;
					}
					
					//It shall be more than the htreshold and more than the max factor
					if ((rCurrentMatchFactor > mrThreshold) && (rCurrentMatchFactor > rMaxMatchfactor)) {
						double rCurrentPleasureValue = ((clsDriveMesh)oAss.getLeafElement()).getMrPleasure();
						//Get the one with the highest lust
						if (rCurrentPleasureValue > rMaxPleasurefactor) {
							rMaxPleasurefactor = rCurrentPleasureValue;
							rMaxMatchfactor = rCurrentMatchFactor;
							oDS = oAss.getRootElement();
						}
					}
				}
			}
			if (oDS!=null) {
				oRetVal.add(new clsPair<clsPhysicalRepresentation, clsDriveMesh>((clsPhysicalRepresentation)oDS, oDM));
			} else {
				// generate empty memory traces, if nothing is perceived
				
				/* SSch 2012-04-10 If the ARSIN does not get any drive objects from perceptions or the drive objects do not conform with the threshold
				 * , the ARSIN halluzinates possible drive objects (whicht he remembers from memory)
				 * Todo: Derzeit wird nur ein clsPair<DM, TPM> weitergereicht. Es fehlt die Moeglichkeit zu einem DM mehrere TPMs anzuhängen (eventuell ueber container? -->
				 *  konsitente linie ob datencontainer für assoziationen oder membervariablen nötig!). Darauf aufbauend berücksichtigen und gewichten von halluz. und wahrg. Objekte 
				 */
				
				rMaxPleasurefactor = 0.0;
				rMaxMatchfactor = 0.0;
				
				ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
						new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
				
				ArrayList<clsDriveMesh> poSearchPattern = new ArrayList<clsDriveMesh>();
				poSearchPattern.add(oDM);
				
				// search for similar DMs in memory (similar to drive candidate) and return the associated TPMs
				search(eDataType.TPM, poSearchPattern, oSearchResult);
				
				for (ArrayList<clsPair<Double, clsDataStructureContainer>> oSearchList : oSearchResult){
					// for each found DM
					for (clsPair<Double, clsDataStructureContainer> oSearchPair: oSearchList) {
						rCurrentMatchFactor = oSearchPair.a;
						if( rCurrentMatchFactor > mrThreshold && (rCurrentMatchFactor >= rMaxMatchfactor)) {
							double rCurrentPleasureValue = ((clsDriveMesh)oSearchPair.b.getMoDataStructure()).getMrPleasure();
							if (rCurrentPleasureValue > rMaxPleasurefactor) {
								rMaxPleasurefactor = rCurrentPleasureValue;
								rMaxMatchfactor = rCurrentMatchFactor;
								ArrayList<clsAssociation> oAssDM = oSearchPair.b.getMoAssociatedDataStructures();
								// take first object that is connected with the found DM
								oDS = oAssDM.get(0).getRootElement();
							}
						}
					}
				}
				
				if (oDS == null) {
					oDS = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL"));
				}
				
				oRetVal.add(new clsPair<clsPhysicalRepresentation, clsDriveMesh>((clsPhysicalRepresentation)oDS, oDM));
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
	public void send_I5_1(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		((I5_1_receive)moModuleList.get(49)).receive_I5_1(poData); 
		((I5_1_receive)moModuleList.get(46)).receive_I5_1(poData); 
		
		putInterfaceData(I5_1_send.class, poData);
	}


}
