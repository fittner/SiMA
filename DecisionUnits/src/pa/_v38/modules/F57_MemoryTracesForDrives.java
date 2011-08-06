/**
 * F57_MemoryTracesForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:23
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I4_1_receive;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_1_send;
import pa._v38.interfaces.modules.I5_7_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:23
 * 
 */
public class F57_MemoryTracesForDrives extends clsModuleBaseKB 
		implements I4_1_receive, I5_7_receive, I5_1_send{

	public static final String P_MODULENUMBER = "57";
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;	//AW 20110521: New containerstructure. Use clsDataStructureConverter.TPMtoTI to convert to old structure
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;	//AW 20110621: Associated Memories
	private ArrayList<clsDriveMesh> moDriveCandidates;
	private  ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrivesAndTraces_OUT;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
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
	public F57_MemoryTracesForDrives(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
			super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);

		applyProperties(poPrefix, poProp); 
		moDrivesAndTraces_OUT = new  ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>();	//If no drive candidate is there, then it is initialized
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
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
		text += toText.valueToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);	
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moDriveCandidates", moDriveCandidates);
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:07:36
	 * 
	 * @see pa._v38.interfaces.modules.I5_7_receive#receive_I5_7(java.util.ArrayList)
	 */
	/* Comment TD from Mail: also deepCopy ist ganz ganz ganz ganz ganz … ganz böses voodoo. 
	 * In diesem fall ist das problem, dass du 2 cast in einem machst/machen mußt. 
	 * Und der ist so nicht checkbar (afaik). In diesem fall einfach suppresswarning machen 
	 * (ist bei deepcopy nicht schlimm – kommt innerhalb der funktion dauernd vor).
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)poEnvironmentalTP.clone();
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
	}

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
		
		//ArrayList<clsPair<clsPrimaryDataStructureContainer, ArrayList<clsDriveMesh>>>;
		
		moDrivesAndTraces_OUT = attachDriveCandidatesToEnvironPerception(moDriveCandidates, moEnvironmentalPerception_IN);
		
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * @param <clsPhysicalDataStructure>
	 *
	 * @since 01.07.2011 10:24:34
	 *
	 */
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> attachDriveCandidatesToEnvironPerception(ArrayList<clsDriveMesh> poDriveCandidates, clsPrimaryDataStructureContainer poEnvironmentalPerception) { 
		//initializing of the list, because it cannnot be null
		ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> oRetVal = new ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>();
		
		//im Speicher suchen nachen nach TPMs die mit den verschiedenen Triebkandidaten assoziiert sind = Triebobjekte	
		//1. Compare drive meshes with drive meshes in the perception
		for (clsDriveMesh oDM : poDriveCandidates) {
			clsDataStructurePA oDS=null;
			double rMaxMatchfactor = 0.0; 
			for (clsAssociation oAss : poEnvironmentalPerception.getMoAssociatedDataStructures()) {
				//FIXME ISABELLA: Which type of comparison will be used, content, contenttype or drive qualities?
				if (oAss.getLeafElement()instanceof clsDriveMesh) {
					if (((clsDriveMesh) oAss.getLeafElement()).matchCathegories(oDM) > 0.1) {
						if (((clsDriveMesh) oAss.getLeafElement()).matchCathegories(oDM) > rMaxMatchfactor) {
							rMaxMatchfactor = ((clsDriveMesh)oAss.getLeafElement()).getPleasure();
							oDS = oAss.getRootElement();
						}
					}
				}
			}
			if (oDS!=null) {
				oRetVal.add(new clsPair<clsPhysicalRepresentation, clsDriveMesh>((clsPhysicalRepresentation)oDS, oDM));
			}
		}
		
		
		//ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer, clsDataStructurePA>>();
		//createSearchPattern(eDataType.TPM, poDriveCandidates, oSearchPattern);
		
		//ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		//search(eDataType.TPM, poDriveCandidates, oSearchResult); //Suche nach TPMs, die mit Trieben assoziiert sind
		    	
		//2. Find the drive structures in the perception
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oReducedList = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		//for 
		//if (poEnvironmentalPerception.containsInstanceType(poInput))

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
		
		putInterfaceData(I5_1_send.class, poData);
	}
}
