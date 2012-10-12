/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:38:29
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_1_send;
import pa._v38.interfaces.modules.I6_4_receive;
import pa._v38.interfaces.modules.I6_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.storage.clsEnvironmentalImageMemory;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.clsQuadruppel;

import pa._v38.tools.toText;
import config.clsProperties;

/**
 * This module does the same as {E8} but with perceptions instead of drive
 * representations. The thing presentations and quota of affects generated by
 * incoming perceived neurosymbols are associated with the most fitting word
 * presentations found in memory.
 * 
 * @author kohlhauser 07.05.2012, 14:38:29
 * 
 */
public class F21_ConversionToSecondaryProcessForPerception extends clsModuleBaseKB implements I5_15_receive, I6_1_send, I6_4_send {
	
	public static final String P_MODULENUMBER = "21";

	/** Perception IN */
	private clsThingPresentationMesh moPerceptionalMesh_IN;

	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;

	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;

	private clsShortTermMemory moShortTermMemory;
	
	/** Memory of the generated concepts; @since 06.10.2012 */
	private clsShortTermMemory moConceptMemory;

	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	private ArrayList< clsQuadruppel<String, String, String, String >> moSituationalConcept;
	
	
	private ArrayList<clsEmotion> moEmotions_Input; 
	
	/** DOCUMENT (hinterleitner) - insert description; */
	private ArrayList< clsTriple<String, String, String >> moSituationalMemory;
	

	private clsConcept moConcept;

	/** TEMP A perceived image */
	// private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	/** TEMP Associated memories */
	// private ArrayList<clsPrimaryDataStructureContainer>
	// moAssociatedMemories_IN;

	/**
	 * Associated memories out, enriched with word presentations of the
	 * associated thing presentations
	 */
	// private ArrayList<clsDataStructureContainerPair>
	// moAssociatedMemoriesSecondary_OUT;
	// private ArrayList<clsPrimaryDataStructureContainer>
	// moGrantedPerception_Input;
	// FIXME HZ: This would require a change in the interfaces!!! => different
	// to the actual definition
	// private ArrayList<clsPair<clsSecondaryDataStructureContainer,
	// clsPair<clsWordPresentation, clsWordPresentation>>> moPerception_Output;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:51:43 */
	// private ArrayList<clsSecondaryDataStructureContainer>
	// moPerception_Output;
	// private clsDataStructureContainerPair moEnvironmentalPerception_OUT;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:51:45 */
	// private ArrayList<clsTriple<clsDataStructurePA,
	// ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh,
	// clsAffect>>>> moOrderedResult;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:52:26 */
	// private HashMap<Integer, clsDriveMesh> moTemporaryDM;

	/**
	 * Load up to 98 indirectly associated structures; @since 30.01.2012
	 * 19:57:31
	 */
	private int mnMaxLevel = 100;

	/**
	 * DOCUMENT (KOHLHAUSER) - insert description
	 * 
	 * @author kohlhauser 03.03.2011, 16:44:38
	 * 
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F21_ConversionToSecondaryProcessForPerception(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler,
			clsShortTermMemory poShortTermMemory,
			clsShortTermMemory poConceptMemory,
			clsEnvironmentalImageMemory poTempLocalizationStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData,
				poKnowledgeBaseHandler);

		applyProperties(poPrefix, poProp);

		this.moShortTermMemory = poShortTermMemory;
		this.moEnvironmentalImageStorage = poTempLocalizationStorage;
		this.moConceptMemory = new clsShortTermMemory(1,1); //FIXME ISABELLA: Dont use the environmental image storage to init your memory. It does not work properly then//poConceptMemory;

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";

		
		//text += toText.valueToTEXT("AdamPerspective:", moConcept);
		text += toText.valueToTEXT("BodosPerspective:", moConcept);
		text += toText.valueToTEXT("moSituationalMemory", moSituationalMemory);
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		text += toText.listToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		//text += toText.listToTEXT("moOrderedResult", moOrderedResult);
		//text += toText.mapToTEXT("moTemporaryDM", moTemporaryDM);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		text += toText.valueToTEXT("Concept Memory", moConceptMemory.toString());
		return text;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE,
				eImplementationStage.BASIC.toString());

		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		// String pre = clsProperties.addDot(poPrefix);

		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	
	private void fillSituationalMemory() {
		
		moSituationalMemory = new ArrayList<clsTriple<String,String,String>>();

		moSituationalMemory.add( new clsTriple<String,String, String>("ADAM", "NOURISH", "WAIT" ));
		moSituationalMemory.add( new clsTriple<String,String, String>("ADAM", "REPRESS", "THINK" ));
		moSituationalMemory.add( new clsTriple<String,String, String>("BELLA", "REPRESS", "THINK" ));

		moSituationalMemory.add( new clsTriple<String,String, String>("BODO", "NOURISH", "EAT" ));
		moSituationalMemory.add( new clsTriple<String,String, String>("BODO", "GRIEF", "THINK" ));
		moSituationalMemory.add( new clsTriple<String,String, String>("BELLA", "GRIEF", "THINK" ));		
	}
	

private void fillConcept() {
		
		moSituationalConcept = new ArrayList< clsQuadruppel<String, String, String, String >> ();
		moSituationalConcept.add( new clsQuadruppel<String,String, String, String>("ADAM", "NOURISH", "WAIT", ""));
		moSituationalConcept.add( new clsQuadruppel<String,String, String, String>("ADAM", "REPRESS", "WAIT", ""));
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:39:18
=======
	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 14:39:18
>>>>>>> e61ceb1d2a51a0954a64232c38a3647a95f9a245
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I5_15(clsThingPresentationMesh poPerceptionalMesh,
			ArrayList<clsEmotion> poEmotions) {
		moPerceptionalMesh_IN = poPerceptionalMesh;
		// try {
		// //moPerceptionalMesh_IN =
		// (clsThingPresentationMesh)poPerceptionalMesh.cloneGraph();
		// moPerceptionalMesh_IN =
		// (clsThingPresentationMesh)poPerceptionalMesh.clone();
		// } catch (CloneNotSupportedException e) {
		// // TODO (wendt) - Auto-generated catch block
		// e.printStackTrace();
		// }
		// moAssociatedMemories_IN =
		// (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
		// moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
		moEmotions_Input = poEmotions;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		clsLogger.jlog.debug("\n\n\n===START OF SECONDARY PROCESS===");

		// --- Update short term memory ---//
		this.moShortTermMemory.updateTimeSteps();
		
		//--- Update the environmental image ---//
		this.moEnvironmentalImageStorage.updateTimeSteps();
		
		fillSituationalMemory();	
		fillConcept();
		
		//Search for all images from the primary process in the memory
		//Input: TPM
		//1. Get all Images of the Mesh
		//2. Search for WPM for the image
		//3. Search for WPM for all internal objects in the WPM
		//4. For each WPM, search for more SP-WPM-Parts and connect them
		//5. Within the WPM-Structure, the allocation of images to acts is already done. Each image except the PI
		clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> oWPMConstruct = getWordPresentationsForImages(moPerceptionalMesh_IN);

		clsLogger.jlog.debug("Perceived Image: " + oWPMConstruct.a);

		// Take the created WPMs and build a clsConcept out of them.
		moConcept = new clsConcept();
		moConcept.addWPMs(oWPMConstruct.a);
		moConcept.addWPMs(oWPMConstruct.b);
		moConceptMemory.saveToShortTimeMemory(moConcept.returnContent());

		// Assign the output to the meshes
		moPerceptionalMesh_OUT = oWPMConstruct.a;
		moAssociatedMemories_OUT = oWPMConstruct.b;

		// debug
		// if (moAssociatedMemories_OUT.isEmpty()==false) {
		// ArrayList<clsWordPresentationMesh> poAct =
		// clsMeshTools.getAllWPMImages(clsActDataStructureTools.getIntention(moAssociatedMemories_OUT.get(0)),
		// 6);
		// System.out.print("poAct size" + poAct.size());
		// }

	}

	/**
<<<<<<< HEAD
	 * For the TPM as input, assign all of them with WPM images
	 * Return a pair of 1) Perception, 2) A list of memories. This function extracts all acts and other 
	 * memories from the primary process data structures. The list of memories is categorized in acts from the images
=======
	 * For the TPM as input, assign all of them with WPM images Return a pair of
	 * 1) Peception, 2) A list of memories. This function extracts all acts and
	 * other memories from the primary process data structures. The list of
	 * memories is categorized in acts from the images
>>>>>>> e61ceb1d2a51a0954a64232c38a3647a95f9a245
	 * 
	 * (wendt)
	 * 
	 * @since 25.01.2012 13:55:04
	 * 
	 * @param poPerceivedImage
	 * @return
	 */
	private clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> getWordPresentationsForImages(
			clsThingPresentationMesh poPerceivedImage) {
		clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>> oRetVal = null;
		// The input image is the perceived image (defined from the position).
		// Therefore, this image is taken to get the secondary process image
		clsWordPresentationMesh oPIWPM = convertCompleteTPMtoWPMRoot(poPerceivedImage);
		// Search for all images from the primary process in the memory

		// Input: TPM
		// 1. Get all Images of the Mesh
		ArrayList<clsThingPresentationMesh> oRITPMList = clsMeshTools
				.getAllTPMMemories(poPerceivedImage, 4);
		// 2. Search for WPM for the image and add the found image to a list.
		// The WPM is connected with the TPM by an associationWP
		ArrayList<clsWordPresentationMesh> oRIWPMList = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsWordPresentationMesh> oEnhancedRIWPMList = new ArrayList<clsWordPresentationMesh>();
		for (clsThingPresentationMesh oRITPM : oRITPMList) {
			// Convert the complete image to WPM
			clsWordPresentationMesh oRIWPM = convertCompleteTPMtoWPMRoot(oRITPM);
			// 3. Search for WPM for all internal objects in the WPM if they are
			// available
			oRIWPMList.add(oRIWPM);
		}

		// 4. For each WPM, search for more SP-WPM-Parts and connect them
		// ArrayList<clsWordPresentationMesh> oCompleteLoadedWPMObjectList = new
		// ArrayList<clsWordPresentationMesh>();
		// Add all already loaded objects to the list of activated WPM
		// oCompleteLoadedWPMObjectList.addAll(oRIWPMList);
		// This is a list of single independent WPM
		for (clsWordPresentationMesh oRIWPM : oRIWPMList) {
			// Get the complete WPM-Object including all WPM associations until
			// level 2. Input is a WPM, therefore, only WPM is returned
			// FIXME AW: As the intention is loaded, all other connected
			// containers are loaded here. This is too specialized
			clsWordPresentationMesh oEnhancedWPM = (clsWordPresentationMesh) searchCompleteMesh(
					oRIWPM, 2);
			// Add the enhanced WPM to a new list, as the enhanced WPM are
			// complete and the former RI are not.
			oEnhancedRIWPMList.add(oEnhancedWPM);

			// Check if all the loaded structures can be added by getting all
			// WPM as a list
			// ArrayList<clsWordPresentationMesh> oEnhancedList =
			// clsMeshTools.getAllWPMImages(oEnhancedWPM, 6);
			// Go through all new found entities
			// for (clsWordPresentationMesh oWPM : oEnhancedList) {
			// if (oEnhancedWPM!=oWPM) {
			// clsMeshTools.mergeMesh(oEnhancedWPM,
			// (clsWordPresentationMesh)oWPM);
			// }
			// }
			// System.out.println("oEnhancedList Size: " +
			// oEnhancedList.size());
		}

		// Create a List of all loaded acts and other memories
		ArrayList<clsWordPresentationMesh> oCategorizedRIWPMList = clsActTools
				.processMemories(oEnhancedRIWPMList);

		// Output: ArrayList<WPM> for each TPM-Image. The WPM are already
		// assigned their acts here
		oRetVal = new clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentationMesh>>(
				oPIWPM, oCategorizedRIWPMList);

		return oRetVal;
	}

	private clsWordPresentationMesh convertCompleteTPMtoWPMRoot(
			clsThingPresentationMesh poTPM) {
		return convertCompleteTPMtoWPM(poTPM,
				new ArrayList<clsThingPresentationMesh>(), 1);
	}

	/**
	 * For each single image, get the complete image with their objects as WPM.
	 * Each image has internal associations to the objects within. These objects
	 * have external associations to their positions and affects. For a TPM
	 * return a fully converted WPM
	 * 
	 * This is a recusive function
	 * 
	 * (wendt)
	 * 
	 * @since 25.01.2012 13:57:49
	 * 
	 * @param poSubTPM
	 * @return
	 */
	private clsWordPresentationMesh convertCompleteTPMtoWPM(
			clsThingPresentationMesh poTPM,
			ArrayList<clsThingPresentationMesh> poProcessedList, int pnLevel) {
		clsWordPresentationMesh oRetVal = null;

		// add the current TPM to the list
		poProcessedList.add(poTPM);

		// Get the WPM for the thing presentation itself
		clsAssociationWordPresentation oWPforObject = getWPMesh(poTPM, 1.0);
		// Copy object
		if (oWPforObject != null) {
			try {
				if (oWPforObject.getLeafElement() instanceof clsWordPresentationMesh) {
					oRetVal = (clsWordPresentationMesh) oWPforObject
							.getLeafElement();

					// Add the external association as it is correctly assigned.
					oRetVal.getExternalAssociatedContent().add(oWPforObject);
				} else {
					throw new Exception(
							"No clsWordPresentation is allowed to be associated here. The following type was recieved: "
									+ oWPforObject.getLeafElement().toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// It may be the PI, then create a new image with for the PI or from
			// the repressed content
			oRetVal = new clsWordPresentationMesh(
					new clsTriple<Integer, eDataType, eContentType>(-1,
							eDataType.WPM, poTPM.getMoContentType()),
					new ArrayList<clsAssociation>(), poTPM.getMoContent());
			clsAssociationWordPresentation oWPAss = new clsAssociationWordPresentation(
					new clsTriple<Integer, eDataType, eContentType>(-1,
							eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP),
					oRetVal, poTPM);
			oRetVal.getExternalAssociatedContent().add(oWPAss);
		}

		// Go deeper, only if the pnLevel allows
		// If nothing was found, then the structure is null
		if (((pnLevel >= 0) || (pnLevel == -1)) && oRetVal != null) {
			// DOCUMENT AW: Internal sub structures are not considered here, as
			// only the word of the object is relevant
			// Search for the other external substructures of the WPM, i. e.
			// clsAttribute and clsDriveMesh
			// DOCUMENT Important note: clsAssociationPrimary is not considered
			// for the secondary process
			for (clsAssociation oTPMExternalAss : poTPM
					.getExternalMoAssociatedContent()) {

				// Case AssociationAttribute
				if (oTPMExternalAss instanceof clsAssociationAttribute) {
					// Get the location templates
					clsAssociationWordPresentation oWPforTPAttribute = getWPMesh(
							(clsPrimaryDataStructure) oTPMExternalAss
									.getLeafElement(),
							1.0);
					if (oWPforTPAttribute != null) {
						clsWordPresentation oAttributeWP = null;
						try {
							oAttributeWP = (clsWordPresentation) oWPforTPAttribute
									.getLeafElement();
						} catch (Exception e) {
							System.out.println(oWPforTPAttribute
									.getLeafElement().toString());
							System.out.println(oWPforTPAttribute
									.getRootElement().toString());
							e.printStackTrace();
						}

						if (oAttributeWP.getMoContentType() == eContentType.DISTANCE) {
							clsMeshTools.createAssociationSecondary(oRetVal, 2,
									oAttributeWP, 0, 1.0,
									eContentType.POSITIONASSOCIATION,
									ePredicate.HASDISTANCE, false);
						} else if (oAttributeWP.getMoContentType() == eContentType.POSITION) {
							clsMeshTools.createAssociationSecondary(oRetVal, 2,
									oAttributeWP, 0, 1.0,
									eContentType.DISTANCEASSOCIATION,
									ePredicate.HASPOSITION, false);
						} else {
							try {
								throw new Exception(
										"Error in F21: getWPCompleteObjekt: A TP was found, which is neither Distance or Position");
							} catch (Exception e) {
								// TODO (wendt) - Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					// Case drivemesh
				} else if (oTPMExternalAss instanceof clsAssociationDriveMesh) {
					// Get the affect templates
					// Get the DriveMesh
					clsDriveMesh oDM = (clsDriveMesh) oTPMExternalAss
							.getLeafElement();
					clsWordPresentation oDMWP = convertDriveMeshToWP(oDM);

					// Create an association between the both structures and add
					// the association to the external associationlist of the
					// RetVal-Structure (WPM)
					clsMeshTools.createAssociationSecondary(oRetVal, 2, oDMWP,
							0, 1.0, eContentType.ASSOCIATIONSECONDARY,
							ePredicate.HASAFFECT, false);
				}
				// //Case association primary
				// } else if (oTPMExternalAss instanceof clsAssociationPrimary)
				// {
				// //In case of an association primary, only the strength of the
				// PI-Match is interesting
				// //Extract the PI match and add it as a WP to the image
				// if
				// (oTPMExternalAss.getMoContentType().equals(eContentType.PIASSOCIATION))
				// {
				// clsMeshTools.setUniquePredicateWP(oRetVal,
				// eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPIMATCH,
				// eContentType.PIMATCH,
				// String.valueOf(oTPMExternalAss.getMrWeight()), false);
				// }
				// }

			}
		}

		if (((pnLevel > 0) || (pnLevel == -1)) && oRetVal != null) {

			// Check the inner associations, if they are associationtime, as it
			// means that is an image
			for (clsAssociation oTPMInternalAss : poTPM
					.getMoInternalAssociatedContent()) {
				// Internal TP-Associations are NOT checked, as they must not be
				// converted to WP
				// Only one internal level is converted, i. e. no images in
				// images are checked
				if (oTPMInternalAss instanceof clsAssociationTime
						&& poProcessedList
								.contains(((clsAssociationTime) oTPMInternalAss)
										.getLeafElement()) == false) {

					clsThingPresentationMesh oSubTPM = ((clsAssociationTime) oTPMInternalAss)
							.getLeafElement();
					// Convert the complete structure to a WPM
					clsWordPresentationMesh oSubWPM = convertCompleteTPMtoWPM(
							oSubTPM, poProcessedList, pnLevel - 1);

					// Add the subWPM to the WPM structure
					clsMeshTools.createAssociationSecondary(oRetVal, 1,
							oSubWPM, 2, 1.0, eContentType.ASSOCIATIONSECONDARY,
							ePredicate.HASPART, false);
				}
			}
		}

		return oRetVal;
	}

//	/**
//	 * 
//	 * DOCUMENT (havlicek) - extract and build a clsConcept out of a given set
//	 * of {@link clsWordPresentationMesh}.
//	 * 
//	 * IN WORK
//	 * 
//	 * @since 03.08.2012 17:05:53
//	 * 
//	 * @param poListWPM
//	 *            give the list of <code>clsWordPresentationMesh</code>es of the
//	 *            current context.
//	 * @return the constructed clsConcept based on the given WPMs
//	 */
//	protected clsConcept generateConcept(clsWordPresentationMesh... poListWPM) {
//		// TODO (havlicek) this mapping is in work
//		clsConcept _concept = new clsConcept();
//		for (clsWordPresentationMesh _wpm : poListWPM) {
//			if (!_wpm.isNullObject()) {
//				_concept.addWPMs(_wpm);
//			}
//		}
//		return _concept;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// HZ: null is a placeholder for the bjects of the type
		// pa._v38.memorymgmt.datatypes
		send_I6_1(moPerceptionalMesh_OUT, moAssociatedMemories_OUT);
		send_I6_4(moPerceptionalMesh_OUT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I6_1(clsWordPresentationMesh poPerception,
			ArrayList<clsWordPresentationMesh> poAssociatedMemories) {
		// AW 20110602: Attention, the associated memeories contain images and
		// not objects like in the perception
		// ((I6_1_receive)moModuleList.get(23)).receive_I6_1(poPerception,
		// poAssociatedMemories);
		((I6_1_receive) moModuleList.get(61)).receive_I6_1(poPerception,
				poAssociatedMemories);

		putInterfaceData(I6_1_send.class, poPerception, poAssociatedMemories);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I6_4(clsWordPresentationMesh poPerception) {
		((I6_4_receive) moModuleList.get(20)).receive_I6_4(poPerception);
		putInterfaceData(I6_4_send.class, poPerception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 03.03.2011, 16:44:44
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author kohlhauser 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This module does the same as {E8} but with perceptions instead of drive representations. The thing presentations and quota of affects generated by incoming perceived neurosymbols are associated with the most fitting word presentations found in memory. ";

	}
}
