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
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.I5_19_receive;
import pa._v38.interfaces.modules.I5_19_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

import config.clsProperties;

/**
 * From an incoming list of action plan together with a list of their associated data structures, the primary data structures
 * (in containers) are extracted and ranked according to the total quota of affect (the highest first). This list of primary data structure
 * images (clsTemplateImage) are returned and sent to F46.
 * 
 * Note: This module does not have any memory access. Therefore, it can only extract data from the available
 * lists. All necessary data is therefore loaded in F52
 *   
 * 
 * @author wendt
 * 03.03.2011, 15:22:59
 * 
 */
public class F47_ConversionToPrimaryProcess extends clsModuleBase implements I6_9_receive, I5_19_send {
	public static final String P_MODULENUMBER = "47";
	//FIXME AW: Extends ModulebaseKB is a hack until results from the planning can be used. Then it should be changed 
	//to clsModuleBase

	
	/** A list of primarty data structure containers, which form the input for phantsies in F46 */
	private ArrayList<clsPrimaryDataStructureContainer> moReturnedTPMemory_OUT;
	/** The list of generated actions */
	private ArrayList<clsSecondaryDataStructureContainer> moActionCommands_IN;
	/** The list of associated memories of the generated actions */
	private ArrayList<clsDataStructureContainerPair> moAssociatedMemories_IN;
	
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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
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
		
		text += toText.listToTEXT("moReturnedTPMemory_OUT", moReturnedTPMemory_OUT);
		text += toText.listToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.listToTEXT("moActionCommands_IN", moActionCommands_IN);
		
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
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
			
		moReturnedTPMemory_OUT = getMemoryFromWP(moActionCommands_IN, moAssociatedMemories_IN);
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
	
	/**
	 * This function extracts all primary data structure containers from the input list, calcultates their
	 * total quota of affect and returns a sorted list of primary data structure containers, which are sorted
	 * according to total quota of affect (the highest first)
	 * (wendt)
	 * @since 20110625
	 *
	 * ${tags}
	 * 
	 */
	private ArrayList<clsPrimaryDataStructureContainer> getMemoryFromWP(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainerPair> poAssociatedMemories) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		//TODO AP: Remove strange data type
		boolean bPlanFragement = false;
		for (clsSecondaryDataStructureContainer oC : poActionCommands) {
			if (oC instanceof clsPlanFragment) {
				bPlanFragement = true;
				break;
			}
		}
		
		if (bPlanFragement==false) {
			//Until the planning is finished, this solution will be fine
			ArrayList<clsPair<Double, clsPrimaryDataStructureContainer>> oAffectEvaluatedContainers = new ArrayList<clsPair<Double, clsPrimaryDataStructureContainer>>();
			
			for (clsSecondaryDataStructureContainer oContainer : poActionCommands) {
				
				boolean bActivate = getConsciousPhantasyActivation(oContainer);
				
				if (bActivate==true) {
					for (clsAssociation oAss : oContainer.getMoAssociatedDataStructures()) {
						//The leaf element contains the wpm of the associated memory
						if (oAss.getLeafElement() instanceof clsSecondaryDataStructure) {
							//Get the primary container of this structure
							clsDataStructureContainerPair oIntentionContainer = clsDataStructureTools.getContainerFromList(poAssociatedMemories, oAss.getLeafElement());
							
							
							if (oIntentionContainer.getSecondaryComponent()!=null) {
								clsPrimaryDataStructureContainer oPIntentionContainer = oIntentionContainer.getPrimaryComponent(); //clsDataStructureTools.extractPrimaryContainer(oSIntentionContainer, poAssociatedMemories);
								if (oPIntentionContainer!=null) {
									//Calculate Total Affect value
									//clsPair<Double, clsPrimaryDataStructureContainer> oContainerAdd =  new clsPair<Double, clsPrimaryDataStructureContainer>(clsAffectTools.calculateAbsoluteAffect((clsPrimaryDataStructureContainer)oPIntentionContainer),(clsPrimaryDataStructureContainer) oPIntentionContainer);
									//Add with sort
									//int i = 0;
									//while ((i + 1 < oAffectEvaluatedContainers.size()) && oContainerAdd.a < oAffectEvaluatedContainers.get(i).a) {
									//	i++;
									//}
									//oAffectEvaluatedContainers.add(i, oContainerAdd);
								}
							}
						}
					}
				}
				
			}
			//Remove the match values
			for (clsPair<Double, clsPrimaryDataStructureContainer> oPair : oAffectEvaluatedContainers) {
				oRetVal.add(oPair.b);
			}
		}
		
			
		return oRetVal;
		
		//clsSecondaryDataStructureContainer oBestPlan = null;
		
		//Get the most important plan in the list, probably the first item in the list, which is a secondarydatastructurecontainer
		/*for (int i=0;i<poActionCommands.size();i++) {
			if (poActionCommands.get(i) instanceof clsSecondaryDataStructureContainer) {
				oBestPlan = (clsSecondaryDataStructureContainer)poActionCommands.get(i);	//Most important plan found
				break;
			}
		}*/
		
		//clsPrimaryDataStructureContainer oHighestQoAImage = null;
		
		//If a best plan was found, search through all associated structures
		/*if (oBestPlan != null) {
			double rMaxQuotaOfAffect = 0.0;
			double rThisQuotaOfAffect = 0.0;
			//Search for associated structures, over secondary associations
			for (clsAssociation oAss : oBestPlan.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationSecondary) {
					//Get the whole container from this structure
					clsSecondaryDataStructureContainer oAssWPMemoryContainer = (clsSecondaryDataStructureContainer) getActivatedContainerFromDS(oAss.getLeafElement(),poActionCommands);
					//Get the primary data structure from the secondary container
					clsPrimaryDataStructure oImage = getAssociatedPrimaryStructure(oAssWPMemoryContainer);
					//Get the whole primary container from the received data structure
					clsPrimaryDataStructureContainer oAssTPMemoryContainer = (clsPrimaryDataStructureContainer) getActivatedContainerFromDS(oImage, poActionCommands);
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
		*/
		//return oRetVal;
	}
	
	private boolean getConsciousPhantasyActivation(clsSecondaryDataStructureContainer poContainer) {
		boolean oRetVal = false;
		
		ArrayList<clsSecondaryDataStructure> oWPList = clsDataStructureTools.getAttributeOfSecondaryPresentation(poContainer, ePredicate.ACTIVATESPHANTASY.toString());
		
		if (oWPList.isEmpty()==false) {
			oRetVal = true;
		}
			
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
	//TODO AW: Remove if not used
	/*private clsPrimaryDataStructure getAssociatedPrimaryStructure (clsSecondaryDataStructureContainer oSecondaryContainer) {
		clsPrimaryDataStructure oRetVal = null;
		
		for (clsAssociation oAss : oSecondaryContainer.getMoAssociatedDataStructures()) {
			if ((oAss instanceof clsAssociationWordPresentation) && (oAss.getRootElement().getMoDSInstance_ID() == oSecondaryContainer.getMoDataStructure().getMoDSInstance_ID())) {
				oRetVal = (clsPrimaryDataStructure) oAss.getLeafElement();
				break;
			}
		}
		
		return oRetVal;
		
	}*/
	
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
	//TODO AW: Remove if not used
	/*private clsDataStructureContainer getActivatedContainerFromDS(clsDataStructurePA poInput, ArrayList<clsDataStructureContainer> poContainerList) {
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
	}*/
	
	//TODO AW: Remove if this function will not be used.
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
		send_I5_19(moReturnedTPMemory_OUT);
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
			ArrayList<clsPrimaryDataStructureContainer> poReturnedMemory) {
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
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainerPair> poAssociatedMemories, clsDataStructureContainerPair poEnvironmentalPerception) {
		//TODO AW: Replace secondarydatastructurecontainer with only datastructurecontainer
		moActionCommands_IN = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poActionCommands);
		moAssociatedMemories_IN = (ArrayList<clsDataStructureContainerPair>)deepCopy(poAssociatedMemories);

		
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
		
}
