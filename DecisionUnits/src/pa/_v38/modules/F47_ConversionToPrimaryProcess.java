/**
 * E47_ConversionToPrimaryProcess.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:22:59
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.tools.clsPair;
import pa._v38.interfaces.itfMinimalModelMode;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.I5_19_receive;
import pa._v38.interfaces.modules.I5_19_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eActState;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsGlobalFunctions;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;

import config.clsBWProperties;

/**
 * Contents of various action plans can be used to reduce libido tension in E45. 
 * Before they can be processed by primary process functions, they have to be converted back again. 
 * The preconscious parts of the contents - the word presentations - are removed by this module. 
 * 
 * @author wendt
 * 03.03.2011, 15:22:59
 * 
 */
public class F47_ConversionToPrimaryProcess extends clsModuleBase implements itfMinimalModelMode, I6_9_receive, I5_19_send {
	public static final String P_MODULENUMBER = "47";
	//FIXME AW: Extends ModulebaseKB is a hack until results from the planning can be used. Then it should be changed 
	//to clsModuleBase

	
	/** Minimal model */
	private boolean mnMinimalModel;
	/** One primary data structure container, which is input for F46 */
	private clsPrimaryDataStructureContainer moReturnedTPMemory_OUT;
	/** The list of generated actions and their associated memories */
	private ArrayList<clsDataStructureContainer> moReturnedWPMemories_IN;
	
	/**
	 * Constructor of F47. Apply properties
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:56:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F47_ConversionToPrimaryProcess(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		String text ="";
		
		//text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.valueToTEXT("moReturnedTPMemory_OUT", moReturnedTPMemory_OUT);
		text += toText.valueToTEXT("moReturnedWPMemories_IN", moReturnedWPMemories_IN);
		
		return text;
	}		
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		mnMinimalModel = false;
		//nothing to do
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
			
		moReturnedTPMemory_OUT = getMemoryFromWP(moReturnedWPMemories_IN);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		
	}
	
	private clsAct getTestDataForAct() {
		
		String poPreCondition = "XX";
			
		//Acts are retrieved by the consequence they have on the agent - hence the content String of the Act is constructed
		//here - only the consequence part is filled
		//The string looks like: "PRECONDITION||ACTION||CONSEQUENCE|NOURISH" or "PRECONDITION||ACTION||CONSEQUENCE|LOCATION:xy|"
		String oContent = eActState.PRECONDITION.name() + "||" 
				   + eActState.ACTION.name() + "||" 
				   + eActState.CONSEQUENCE.name() + "|" + poPreCondition;
		String oActualGoal = oContent;
		
		clsAct oAct = (clsAct)clsDataStructureGenerator.generateACT(new clsTripple <String, ArrayList<clsSecondaryDataStructure>, Object>(
				eDataType.ACT.name(), new ArrayList<clsSecondaryDataStructure>(), oActualGoal));
		
		//clsAct oAct = generateAct(oActualGoal); 
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();
		
		//search(eDataType.DM, new ArrayList<clsDataStructurePA>(Arrays.asList(oAct)), oSearchResult);
		return oAct;
		
	}
	
	/**
	 * This function picks one memory trace in the form of a template image from a secondary process container and
	 * sends to the primary process, where it is used as input of the association of new memories
	 *
	 * @since 20110625
	 *
	 * ${tags}
	 * 
	 */
	private clsPrimaryDataStructureContainer getMemoryFromWP(ArrayList<clsDataStructureContainer> poInput) {
		clsPrimaryDataStructureContainer oRetVal = null;
		clsSecondaryDataStructureContainer oBestPlan = null;
		
		//Get the most important plan in the list, probably the first item in the list, which is a secondarydatastructurecontainer
		for (int i=0;i<poInput.size();i++) {
			if (poInput.get(i) instanceof clsSecondaryDataStructureContainer) {
				oBestPlan = (clsSecondaryDataStructureContainer)poInput.get(i);	//Most important plan found
				break;
			}
		}
		
		clsPrimaryDataStructureContainer oHighestQoAImage = null;
		
		//If a best plan was found, search through all associated structures
		if (oBestPlan != null) {
			double rMaxQuotaOfAffect = 0.0;
			double rThisQuotaOfAffect = 0.0;
			//Search for associated structures, over secondary associations
			for (clsAssociation oAss : oBestPlan.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationSecondary) {
					//Get the whole container from this structure
					clsSecondaryDataStructureContainer oAssWPMemoryContainer = (clsSecondaryDataStructureContainer) getActivatedContainerFromDS(oAss.getLeafElement(),poInput);
					//Get the primary data structure from the secondary container
					clsPrimaryDataStructure oImage = getAssociatedPrimaryStructure(oAssWPMemoryContainer);
					//Get the whole primary container from the received data structure
					clsPrimaryDataStructureContainer oAssTPMemoryContainer = (clsPrimaryDataStructureContainer) getActivatedContainerFromDS(oImage, poInput);
					//Calculate the total quota of affect for that container
					rThisQuotaOfAffect = clsGlobalFunctions.calculateAbsoluteAffect(oAssTPMemoryContainer);
					//If the total quota of affect is higher than the max value, this quota of affect will be the max value
					if (rThisQuotaOfAffect > rMaxQuotaOfAffect) {
						rMaxQuotaOfAffect = rThisQuotaOfAffect;
						oHighestQoAImage = oAssTPMemoryContainer;
					}
				}
			}
		}
		//The highest match is returned
		oRetVal = oHighestQoAImage;
		//oRetVal = new clsPrimaryDataStructureContainer(clsDataStructureGenerator.generateTI(new clsTripple<String, ArrayList<clsPhysicalRepresentation>, Object>("DummyMemory", new ArrayList<clsPhysicalRepresentation>(), "DummyMemory")), new ArrayList<clsAssociation>());
		
		return oRetVal;
	}
	
	/**
	 * Extracts the corresponding primary data structure from a secondary datastructure. It will always find a structure, if such an association  
	 * structure is present, else null is returned
	 *
	 * @since 17.07.2011 09:17:43
	 *
	 * @param oSecondaryContainer
	 * @return
	 */
	private clsPrimaryDataStructure getAssociatedPrimaryStructure (clsSecondaryDataStructureContainer oSecondaryContainer) {
		clsPrimaryDataStructure oRetVal = null;
		
		for (clsAssociation oAss : oSecondaryContainer.getMoAssociatedDataStructures()) {
			if ((oAss instanceof clsAssociationWordPresentation) && (oAss.getRootElement().getMoDSInstance_ID() == oSecondaryContainer.getMoDataStructure().getMoDSInstance_ID())) {
				oRetVal = (clsPrimaryDataStructure) oAss.getLeafElement();
				break;
			}
		}
		
		return oRetVal;
		
	}
	
	/**
	 * If a found data element is activated, its container shall be found in a list. By instanceID, the container of a data structure 
	 * is searched for in the list and the first match is returned.
	 *
	 * @since 17.07.2011 09:28:36
	 *
	 * @param poInput
	 * @param poContainerList
	 * @return
	 */
	private clsDataStructureContainer getActivatedContainerFromDS(clsDataStructurePA poInput, ArrayList<clsDataStructureContainer> poContainerList) {
		clsDataStructureContainer oRetVal = null;
		
		if (poInput!=null) {
			for (clsDataStructureContainer oContainer : poContainerList) {
				//There can be only one container of the same instance in the list, therefore only the first match is taken
				if ((oContainer instanceof clsPrimaryDataStructureContainer) && (oContainer.getMoDataStructure().getMoDSInstance_ID() == poInput.getMoDSInstance_ID())) {
					oRetVal = (clsPrimaryDataStructureContainer)oContainer;
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	//FIXME AW: Remove if this function will not be used.
	/*private ArrayList<clsSecondaryDataStructureContainer> getAssociatedWPMemories(clsSecondaryDataStructureContainer poInput, ArrayList<clsDataStructureContainer> poContainerList) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		if (poInput != null) {
			//Search for associated structures, over secondary associations
			for (clsAssociation oAss : poInput.getMoAssociatedDataStructures()) {
				//FIXME AW: Which type of clsAssociationSecondary? What shall be the Attribute?
				if ((oAss instanceof clsAssociationSecondary) && (poInput.getMoDataStructure().getMoDSInstance_ID() == oAss.getRootElement().getMoDSInstance_ID()) && 
						(oAss.getLeafElement() instanceof clsSecondaryDataStructure)) {	
					//For each found association, search the attached list for the word presentation
					//Precondition: All associated WP-Containers are already loaded into the arraylist
					oRetVal.add((clsSecondaryDataStructureContainer) getActivatedContainerFromDS(oAss, poContainerList));
				}
			}
		}
		
		return oRetVal;
	}*/
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {
			send_I5_19(moReturnedTPMemory_OUT);
		} else {
			send_I5_19(moReturnedTPMemory_OUT);
		}

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:56:32
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 18:03:53
	 * 
	 * @see pa.interfaces.send._v38.I7_7_send#send_I7_7(java.util.ArrayList)
	 */
	@Override
	public void send_I5_19(
			clsPrimaryDataStructureContainer poReturnedMemory) {
		((I5_19_receive)moModuleList.get(46)).receive_I5_19(poReturnedMemory);
		putInterfaceData(I5_19_send.class, poReturnedMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 18:03:53
	 * 
	 * @see pa.interfaces.receive._v38.I7_3_receive#receive_I7_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands) {
		//TODO AW: Replace secondarydatastructurecontainer with only datastructurecontainer
		moReturnedWPMemories_IN = (ArrayList<clsDataStructureContainer>)deepCopy(poActionCommands);

		
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
		moDescription = "Contents of various action plans can be used to reduce libido tension in E45. Before they can be processed by primary process functions, they have to be converted back again. The preconscious parts of the contents - the word presentations - are removed by this module.";
	}	
	
	@Override
	public void setMinimalModelMode(boolean pnMinial) {
		mnMinimalModel = pnMinial;
	}

	@Override
	public boolean getMinimalModelMode() {
		return mnMinimalModel;
	}	
	
}
