/**
 * CHANGELOG
 *
 * 22.05.2012 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationEmotion;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * This class contains methods for data search and extraction in meshes
 * 
 * (wendt) 
 * 
 * @author wendt
 * 22.05.2012, 13:01:07
 * 
 */
public class clsMeshTools {
	
	//=== STATIC VARIBALES --- START ===//
	
	/** This is the max depth, which can be search for (wendt); @since 04.06.2012 15:23:49 */
	private static int mnMaxLevel = 10;
	
	private static final clsThingPresentationMesh moNullObjectTPM = clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.NULLOBJECT, new ArrayList<clsThingPresentation>(), eContentType.NULLOBJECT.toString()));
	private static final clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	
	
	//=== STATIC VARIBALES --- END ===//
	
	/**
	 * @since 05.07.2012 21:59:44
	 * 
	 * @return the moNullObjectTPM
	 */
	public static clsThingPresentationMesh getNullObjectTPM() {
		return clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.NULLOBJECT, new ArrayList<clsThingPresentation>(), eContentType.NULLOBJECT.toString()));//moNullObjectTPM;
	}
	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());//moNullObjectWPM;
	}
	
	
	
	//=== SEARCH DATA STRUCTURES IN TPM AND WPM GENERAL --- START ===//
	
	/**
	 * Find a PA datastructure in any arraylist and return the object from the arraylist
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:40:24
	 *
	 * @param poList
	 * @param poSearchDS
	 * @return
	 */
	public static <E extends clsDataStructurePA> E searchPADataStructureInArrayList(ArrayList<E> poList, E poSearchDS) {
		E oRetVal = null;
		
		//check invalid IDs
		if (poSearchDS.getMoDS_ID() == -1) {
			try {
				throw new Exception("clsDataStructureTools, findPADataStructureInArrayList: Invalid Input ID");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for (E oDS : poList) {
			if (oDS.getMoDS_ID() == -1) {
				try {
					throw new Exception("clsDataStructureTools, findPADataStructureInArrayList: Invalid DatastructurePA ID");
				} catch (Exception e) {
					// TODO (wendt) - Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (oDS.getMoDS_ID() == poSearchDS.getMoDS_ID()) {
				oRetVal = oDS;
			}
		}
		
		return  oRetVal;
	}
	
	//=== SEARCH DATA STRUCTURES IN TPM AND WPM GENERAL --- END ===//
	
	//=== SEARCH DATA STRUCTURES IN TPM GENERAL --- START ===//
	/**
	 * Get all TPM structures in a mesh up to a certain level. A list with each TPM structure is returned
	 * (wendt)
	 *
	 * @since 05.01.2012 10:42:02
	 *
	 * @param poMesh: The ThingPresentationMesh, normally the perception
	 * @param poDataType: Filter: Returndatatype: TPM, TP or DM
	 * @param poContentType: Filter ContentType. Set null for no filter
	 * @param poContent: Filter Content. Set null for no filter
	 * @param pbStopAtFirstMatch: Break at first match
	 * @param pnLevel: Level of search depth. If = 1, only the current image is processed. 
	 * If pnLevel = 2, all direct matches to the top image are processed
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> getDataStructureInTPM(clsThingPresentationMesh poMesh, eDataType poDataType, ArrayList<eContentType> poContentTypeList, boolean pbStopAtFirstMatch, int pnLevel) {
		ArrayList<clsThingPresentationMesh> oAddedElements = new ArrayList<clsThingPresentationMesh>();
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through each TPM (Node) and search for the defined structures. This is a recursive function
		searchDataStructureInTPM(poMesh, oAddedElements, oRetVal, poDataType, poContentTypeList, pbStopAtFirstMatch, pnLevel);
		
		return oRetVal;
	}
	
	/**
	 * Get the first found data structure in a TPM of a certain type
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 13:54:39
	 *
	 * @param poMesh
	 * @param poDataType
	 * @param poContentTypeAndContent
	 * @param pnLevel
	 * @return
	 */
	public static clsDataStructurePA getFirstDataStructureInTPM(clsThingPresentationMesh poMesh, eDataType poDataType, ArrayList<eContentType> poContentTypeList, int pnLevel) {
		clsDataStructurePA oResult = null;
		
		ArrayList<clsDataStructurePA> oListOfTP = clsMeshTools.getDataStructureInTPM(poMesh, poDataType, poContentTypeList, true, 0);
		
		if (oListOfTP.isEmpty()==false) {
			oResult = oListOfTP.get(0);
		}
		
		return oResult;
	}
		
	
	/**
	 * Recursively go through all elements in the mesh to get all TPMs, but here, this function is only used if there already exists a list
	 * 
	 * (wendt)
	 *
	 * @since 06.12.2011 11:37:00
	 *
	 * @param poMesh
	 * @return
	 */
	private static void searchDataStructureInTPM(clsThingPresentationMesh poMesh, ArrayList<clsThingPresentationMesh> poAddedElements, ArrayList<clsDataStructurePA> poRetVal, eDataType poDataType, ArrayList<eContentType> poContentTypeFilterList, boolean pbStopAtFirstMatch, int pnLevel) {
		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
		
		//Add the structure itself to the list of passed elements
		poAddedElements.add(poMesh);
		//Check this data structure for filter options and add the result to the result list if filter fits
		if (poDataType.equals(eDataType.TPM)==true) {
			if (poContentTypeFilterList.isEmpty()==true) {
				//If empty, then no matching at all
				poRetVal.add(poMesh);
			} else {
				//Check if this mesh matches the content and content type filter. If yes, then add the result
				for (eContentType oCTC : poContentTypeFilterList) {
					//Check if this mesh has this filter
					boolean bMatchFound = filterTPM(poMesh, oCTC);
					
					//As soon as positive, break loop
					if (bMatchFound==true) {
						poRetVal.add(poMesh);
						break;
					}
				}
			}
		} else if (poDataType.equals(eDataType.TP)==true) {
			ArrayList<clsAssociationAttribute> oFoundTPAssList = getTPAssociations(poMesh, poContentTypeFilterList, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundTPAssList);
		} else if (poDataType.equals(eDataType.DM)==true) {
			ArrayList<clsAssociationDriveMesh> oFoundDMAssList = getDMAssociations(poMesh, poContentTypeFilterList, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundDMAssList);
		} else if (poDataType.equals(eDataType.EMOTION)==true) {
			ArrayList<clsAssociationEmotion> oFoundDMAssList = getEMOTIONAssociations(poMesh, poContentTypeFilterList, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundDMAssList);
		} else {
			try {
				throw new Exception("clsDataStructureTools: searchDataStructureInMesh: Only TPM, TP, EMOTION or DM allowed as data types");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (pbStopAtFirstMatch==false || poRetVal.isEmpty()==true) {	//=NOT Stopatfirstmatch=true AND oRetVal is not empty
			
			//Add the substructures of the internal associations
			if ((pnLevel>0) || (pnLevel==-1)) {
				for (clsAssociation oAss : poMesh.getMoInternalAssociatedContent()) {
					if (poAddedElements.contains(oAss.getLeafElement())==false && oAss.getLeafElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeFilterList, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeFilterList, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
			
			//Add external associations to other external images
			if ((pnLevel>1) || (pnLevel==-1)) {
				for (clsAssociation oExtAss : poMesh.getExternalMoAssociatedContent()) {
					if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oExtAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeFilterList, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsThingPresentationMesh) {
						searchDataStructureInTPM((clsThingPresentationMesh) oExtAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeFilterList, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
		}
		
	}
	
	/**
	 * Filter a list of TPMs for certain content type. 
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:57:09
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static boolean filterTPM(clsThingPresentationMesh poMesh, eContentType poContentType) {
		//ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		boolean oRetVal = false;
		
		//if (poContentType.equals(eContentType.NULLOBJECT)==true) {
		//	oRetVal = true;
		//} else {
			if (poContentType.equals(poMesh.getMoContentType())==true) {
				oRetVal = true;
			}
		//}
		
//		if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==false) {
//				if ((poContentType.equals(poMesh.getMoContentType())==true) &&
//						(poContent.equals(poMesh.getMoContent())==true)) {
//					oRetVal = true;
//				}
//			} else if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==true) {
//				if (poContentType.equals(poMesh.getMoContentType())==true) {
//					oRetVal = true;
//				}
//			} else if (poContentType.equals(eContentType.NULLOBJECT)==true && poContent.equals("")==false) {
//				if (poContent.equals(poMesh.getMoContent())==true) {
//					oRetVal = true;
//				}
//			} else {
//				oRetVal = true;
//			}
		
		return oRetVal;
	}
	
	/**
	 * Get all associatied DM in a TPM by using some filter criteria
	 * 
	 * (wendt)
	 *
	 * @since 20.01.2012 11:36:30
	 *
	 * @param poTPM
	 * @param poContentTypeAndContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsAssociationDriveMesh> getDMAssociations(clsThingPresentationMesh poTPM, ArrayList<eContentType> poContentTypeFilterList, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();

		//Go through all external
		//If no filter set, add all associations
		if (poContentTypeFilterList.isEmpty()==true) {
			for (clsAssociation oAss : poTPM.getExternalMoAssociatedContent()) {
				if (oAss instanceof clsAssociationDriveMesh) {
					oRetVal.add((clsAssociationDriveMesh) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		} else {
			for (eContentType oCTC : poContentTypeFilterList) {
				oRetVal.addAll(FilterDMList(poTPM.getExternalMoAssociatedContent(), oCTC, pbStopAtFirstMatch));
				if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
					//Go through the external list
					//oRetVal.addAll(FilterDMList(poTPM.getExternalMoAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get a filtered list of all emotions of a certain TPM
	 * 
	 * (wendt)
	 *
	 * @since 01.09.2012 12:45:04
	 *
	 * @param poTPM
	 * @param poContentTypeFilterList
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsAssociationEmotion> getEMOTIONAssociations(clsThingPresentationMesh poTPM, ArrayList<eContentType> poContentTypeFilterList, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationEmotion> oResult = new ArrayList<clsAssociationEmotion>();
		
		//Go through all external
		//If no filter set, add all associations
		if (poContentTypeFilterList.isEmpty()==true) {
			for (clsAssociation oAss : poTPM.getExternalMoAssociatedContent()) {
				if (oAss instanceof clsAssociationEmotion) {
					oResult.add((clsAssociationEmotion) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		} else {
			for (eContentType oCTC : poContentTypeFilterList) {
				oResult.addAll(FilterEMOTIONList(poTPM.getExternalMoAssociatedContent(), oCTC, pbStopAtFirstMatch));
				if (pbStopAtFirstMatch==false || oResult.isEmpty()==true) {
					break;
				}
			}
		}
		
		return oResult;
	}
	
	/**
	 * Filter a List of TP for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationAttribute> FilterTPList(ArrayList<clsAssociation> poAssList, eContentType poContentType, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationAttribute) {
				if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
					oRetVal.add((clsAssociationAttribute) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
				
				
//				
//				
//				if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==false) {
//					if ((poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) &&
//							(poContent.equals(((clsThingPresentation)oAss.getLeafElement()).getMoContent().toString())==true)) {
//						oRetVal.add((clsAssociationAttribute) oAss);
//						if (pbStopAtFirstMatch==true) {
//							break;
//						}
//					}
//				} else if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==true) {
//					if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
//						oRetVal.add((clsAssociationAttribute) oAss);
//						if (pbStopAtFirstMatch==true) {
//							break;
//						}
//					}
//				} else if (poContentType.equals(eContentType.NULLOBJECT)==true && poContent.equals("")==false) {
//					if (poContent.equals(((clsThingPresentation)oAss.getLeafElement()).getMoContent().toString())==true) {
//						oRetVal.add((clsAssociationAttribute) oAss);
//						if (pbStopAtFirstMatch==true) {
//							break;
//						}
//					}
//				} else {
//					oRetVal.add((clsAssociationAttribute) oAss);
//					if (pbStopAtFirstMatch==true) {
//						break;
//					}
//				}
//			}
//		}
		
		return oRetVal;
	}
	
	/**
	 * Filter a List of DM for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationDriveMesh> FilterDMList(ArrayList<clsAssociation> poAssList, eContentType poContentType, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationDriveMesh) {
				if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
					oRetVal.add((clsAssociationDriveMesh) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
			
			
//			boolean bBreakAssLoop = false;
//			//Go through all pairs of contents and content types
//			//for (clsPair<String, String> oCTC : poContentTypeAndContent) {
//				//Check if dm has the corrent content and content type
//				if (oAss instanceof clsAssociationDriveMesh) {
//					if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==false) {
//						if ((poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) &&
//								(poContent.equals(((clsDriveMeshOLD)oAss.getLeafElement()).getMoContent().toString())==true)) {
//							oRetVal.add((clsAssociationDriveMesh) oAss);
//							if (pbStopAtFirstMatch==true) {
//								bBreakAssLoop=true;
//							}
//							break;
//						}
//					} else if (poContentType.equals(eContentType.NULLOBJECT)==false && poContent.equals("")==true) {
//						if (poContentType.equals(((clsAssociationDriveMesh)oAss).getLeafElement().getMoContentType())==true) {
//							oRetVal.add((clsAssociationDriveMesh) oAss);
//							if (pbStopAtFirstMatch==true) {
//								bBreakAssLoop=true;
//							}
//						}
//					} else if (poContentType.equals(eContentType.NULLOBJECT)==true && poContent.equals("")==false) {
//						if (poContent.equals(((clsDriveMeshOLD)oAss.getLeafElement()).getMoContent().toString())==true) {
//							oRetVal.add((clsAssociationDriveMesh) oAss);
//							if (pbStopAtFirstMatch==true) {
//								bBreakAssLoop=true;
//							}
//						}
//					} else {
//						oRetVal.add((clsAssociationDriveMesh) oAss);
//						if (pbStopAtFirstMatch==true) {
//							break;
//						}
//					}
//				//}
//			}
//			
//			if (bBreakAssLoop==true) {
//				break;
//			}
//		}
		
		return oRetVal;
	}
	
	/**
	 * Add emotions connected from a list of associations, which fulfill a certain filter criterium
	 * 
	 * (wendt)
	 *
	 * @since 01.09.2012 12:43:36
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsAssociationEmotion> FilterEMOTIONList(ArrayList<clsAssociation> poAssList, eContentType poContentType, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationEmotion> oRetVal = new ArrayList<clsAssociationEmotion>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationEmotion) {
				if (poContentType.equals(((clsAssociationAttribute)oAss).getLeafElement().getMoContentType())==true) {
					oRetVal.add((clsAssociationEmotion) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Check the mesh for existing structures, where the input is a TPM. The type ID is compared
	 * 
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> searchDataStructureTypesInMesh(clsThingPresentationMesh poTargetImageMesh, clsThingPresentationMesh poFindDataStructure, int pnLevel) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		//Compare IDs of the structures
		//TODO: Extend to instance_IDs
		
		ArrayList<clsThingPresentationMesh> oAllTPMInMesh = getAllTPMObjects(poTargetImageMesh, pnLevel);
		
		//Go through all objects on this level in each image
		for (clsThingPresentationMesh oObject : oAllTPMInMesh) {
			
			if (poFindDataStructure.getMoDS_ID() == oObject.getMoDS_ID()) {
				oRetVal.add(oObject);
			}
		}

		return oRetVal;
	}
	
	/**
	 * Find a certain instance of a TPM in a mesh. The instance ID is compared here
	 * 
	 * (wendt)
	 *
	 * @since 22.09.2011 14:31:10
	 *
	 * @param poTargetImage
	 * @param poFindDataStructure
	 * @return
	 */
	public static clsThingPresentationMesh searchDataStructureInstanceInMeshTPM(clsThingPresentationMesh poTargetImageMesh, clsThingPresentationMesh poFindDataStructure, int pnLevel) {
		clsThingPresentationMesh oRetVal = null;
		//Compare IDs of the structures
		//TODO: Extend to instance_IDs
		
		ArrayList<clsThingPresentationMesh> oAllTPMInMesh = getAllTPMImages(poTargetImageMesh, pnLevel);
		
		for (clsThingPresentationMesh oObject : oAllTPMInMesh) {
			if (poFindDataStructure.getMoDSInstance_ID() == oObject.getMoDSInstance_ID()) {
				oRetVal = oObject;
				break;
			}
		}

		return oRetVal;
	}
	

	/**
	 * Search any association list for a certain content type of a Data structure in the primary process
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:07:50
	 *
	 * @param poInputList
	 * @param poThisDataStructure
	 * @param poContentType: Content type of the searched structure
	 * @param pnMode: 0 = get the contenttype of the OTHER association as this element; 1 = get the contenttype of the ROOT element; 2 = get the contenttype of the LEAF element
	 * @param pbGetWholeAssociation: Get the whole association and not one of the end points
	 * @param pbStopAtFirstMatch: Stop at first match
	 * @return
	 */
	private static ArrayList<clsDataStructurePA> searchAssociationListPrimary(ArrayList<clsAssociation> poInputList, clsDataStructurePA poThisDataStructure, eContentType poDSContentType, int pnMode, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		for (clsAssociation oAss : poInputList) {
			if (pnMode==0 && oAss.getTheOtherElement(poThisDataStructure).getMoContentType().equals(poDSContentType)) {
				if (pbGetWholeAssociation==true) {
					oRetVal.add(oAss);
				} else {
					oRetVal.add(oAss.getTheOtherElement(poThisDataStructure));
				}
				
				if (pbStopAtFirstMatch==true) {
					break;
				}
			} else if (pnMode==1 && oAss.getRootElement().getMoContentType().equals(poDSContentType)) {
				if (pbGetWholeAssociation==true) {
					oRetVal.add(oAss);
				} else {
					oRetVal.add(oAss.getRootElement());
				}
				
				if (pbStopAtFirstMatch==true) {
					break;
				}
			} else if (pnMode==2 && oAss.getLeafElement().getMoContentType().equals(poDSContentType)) {
				if (pbGetWholeAssociation==true) {
					oRetVal.add(oAss);
				} else {
					oRetVal.add(oAss.getLeafElement());
				}
				
				if (pbStopAtFirstMatch==true) {
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Search all associtions of a certain contenttype in any TPM
	 * 
	 * This function is only used within a TPM, i. e. level 0
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:12:12
	 *
	 * @param poInput
	 * @param poAssociationContentType
	 * @param pnMode
	 * @param pbGetWholeAssociation
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> searchDataStructureOverAssociation(clsThingPresentationMesh poInput, eContentType poDataStructureContentType, int pnMode, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through outer associations
		oRetVal.addAll(searchAssociationListPrimary(poInput.getExternalMoAssociatedContent(), poInput, poDataStructureContentType, pnMode, pbGetWholeAssociation, pbStopAtFirstMatch));
		
		//Go through inner associations
		oRetVal.addAll(searchAssociationListPrimary(poInput.getMoInternalAssociatedContent(), poInput, poDataStructureContentType, pnMode, pbGetWholeAssociation, pbStopAtFirstMatch));
					
		return oRetVal;
	}
	
	/**
	 * Search the first data structure of a certain associational content type
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:17:09
	 *
	 * @param poInput
	 * @param poAssociationContentType
	 * @param pnMode
	 * @param pbGetWholeAssociation
	 * @return
	 */
	public static clsDataStructurePA searchFirstDataStructureOverAssociationTPM(clsThingPresentationMesh poInput, eContentType poDataStructureContentType, int pnMode, boolean pbGetWholeAssociation) { 
		//pnMode:
		//0 = the other element
		//1 = the root element
		//2 = the leaf element
		
		clsDataStructurePA oRetVal = null;
		
		ArrayList<clsDataStructurePA> oList = searchDataStructureOverAssociation(poInput, poDataStructureContentType, pnMode, pbGetWholeAssociation, true);
		if (oList.isEmpty()==false) {
			oRetVal = oList.get(0);
		}
		
		return oRetVal;
	}
	

	
	
	//=== SEARCH DATA STRUCTURES IN TPM GENERAL --- END ===//
	
	
	//=== SEARCH DATA STRUCTURES IN WPM GENERAL --- START ===//
	
	/**
	 * Search any association list for a certain secondary association and return the other 
	 * element of the association. Stop at first match is optional.
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:21:33
	 *
	 * @param poInputList
	 * @param poPredicate
	 * @param pnMode: 0 = get the other element, 1 = get the root element, 2 = get the leaf element
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsDataStructurePA> searchAssociationListSecondary(ArrayList<clsAssociation> poInputList, clsDataStructurePA poThisDataStructure, ePredicate poPredicate, int pnMode, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		for (clsAssociation oAss : poInputList) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate)) {
					if (pbGetWholeAssociation==true) {
						oRetVal.add(oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					} else {
						if (pnMode==0) {
							oRetVal.add(oAss.getTheOtherElement(poThisDataStructure));
							if (pbStopAtFirstMatch==true) {
								break;
							}
						} else if (pnMode==1) {
							oRetVal.add(oAss.getRootElement());
							if (pbStopAtFirstMatch==true) {
								break;
							}
						} else if (pnMode==2) {
							oRetVal.add(oAss.getLeafElement());
							if (pbStopAtFirstMatch==true) {
								break;
							}
						}
					}
					
				}		
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the first data structure for a certain association within a data structure
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:25:20
	 *
	 * @param poInput
	 * @param poPredicate
	 * @param pnMode
	 * 0 = the other element, 1 = the root element, 2 = the leaf element
	 * @return
	 */
	public static clsDataStructurePA searchFirstDataStructureOverAssociationWPM(clsWordPresentationMesh poInput, ePredicate poPredicate, int pnMode, boolean pbGetWholeAssociation) { 
		//pnMode:
		//0 = the other element
		//1 = the root element
		//2 = the leaf element
		
		clsDataStructurePA oRetVal = null;
		
		ArrayList<clsDataStructurePA> oList = searchDataStructureOverAssociation(poInput, poPredicate, pnMode, pbGetWholeAssociation, true);
		if (oList.isEmpty()==false) {
			oRetVal = oList.get(0);
		}
		
		return oRetVal;
	}
	
	
	
	/**
	 * Search a through a WPM for a certain type of associations based on the predicate. Optinal, only the first
	 * match is searched
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:20:22
	 *
	 * @param poInput
	 * @param poPredicate
	 * @param pnMode 
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> searchDataStructureOverAssociation(clsWordPresentationMesh poInput, ePredicate poPredicate, int pnMode, boolean pbGetWholeAssociation, boolean pbStopAtFirstMatch) {
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through outer associations
		oRetVal.addAll(searchAssociationListSecondary(poInput.getExternalAssociatedContent(), poInput, poPredicate, pnMode, pbGetWholeAssociation, pbStopAtFirstMatch));
		
		//Go through inner associations
		oRetVal.addAll(searchAssociationListSecondary(poInput.getMoInternalAssociatedContent(), poInput, poPredicate, pnMode, pbGetWholeAssociation, pbStopAtFirstMatch));
					
		return oRetVal;
	}
	
	/**
	 * Search for a secondary data structure within an image
	 * 
	 * (wendt)
	 *
	 * @since 04.06.2012 14:55:26
	 *
	 * @param poInput
	 * @param poPredicate
	 * @param poDataStructureContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsSecondaryDataStructure> searchSecondaryDataStructureInImage(clsWordPresentationMesh poInput, ePredicate poPredicate, String poDataStructureContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		ArrayList<clsDataStructurePA> oFoundDSList =  searchDataStructureOverAssociation(poInput, poPredicate, 0, false, false);
		
		for (clsDataStructurePA oDS : oFoundDSList) {
			if (poDataStructureContent != "" && ((clsSecondaryDataStructure)oDS).getMoContent().equals(poDataStructureContent)) {
				oRetVal.add((clsSecondaryDataStructure) oDS);
				
				if (pbStopAtFirstMatch == true) {
					break;
				}
				
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the primary component of a WPM
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 22:01:39
	 *
	 * @param poInput
	 * @return
	 */
	public static clsThingPresentationMesh getPrimaryDataStructureOfWPM(clsWordPresentationMesh poInput) {
		clsThingPresentationMesh oRetVal = clsMeshTools.moNullObjectTPM;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationWordPresentation && oAss.getRootElement() instanceof clsThingPresentationMesh) {
				//Add the TPM to the output
				oRetVal = (clsThingPresentationMesh) oAss.getRootElement();
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all WPM structures in a mesh up to a certain level. A list with each WPM structure is returned
	 * (wendt)
	 *
	 * @since 05.01.2012 10:42:02
	 *
	 * @param poMesh: The ThingPresentationMesh, normally the perception
	 * @param poDataType: Filter: Returndatatype: TPM, TP or DM
	 * @param poContentType: Filter ContentType. Set null for no filter
	 * @param poContent: Filter Content. Set null for no filter
	 * @param pbStopAtFirstMatch: Break at first match
	 * @param pnLevel: Level of search depth. If = 1, only the current image is processed. 
	 * If pnLevel = 2, all direct matches to the top image are processed
	 * @return
	 */
	public static ArrayList<clsDataStructurePA> getDataStructureInWPM(clsWordPresentationMesh poMesh, eDataType poDataType, ArrayList<clsPair<eContentType, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		ArrayList<clsWordPresentationMesh> oAddedElements = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsDataStructurePA> oRetVal = new ArrayList<clsDataStructurePA>();
		
		//Go through each TPM (Node) and search for the defined structures. This is a recursive function
		searchDataStructureInWPM(poMesh, oAddedElements, oRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel);
		
		return oRetVal;
	}
	
	/**
	 * Filter WPM for certain content and content type
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:26:13
	 *
	 * @param poMesh
	 * @param poContentType: eContentType.NOTHING is equal to "" in strings
	 * @param poContent
	 * @return
	 */
	private static boolean FilterWPM(clsWordPresentationMesh poMesh, eContentType poContentType, String poContent) {

		boolean oRetVal = false;
		
			if (poContentType.equals(eContentType.NOTHING)==false && poContent.equals("")==false) {
				if ((poContentType.equals(poMesh.getMoContentType())==true) &&
						(poContent.equals(poMesh.getMoContent())==true)) {
					oRetVal = true;
				}
			} else if (poContentType.equals(eContentType.NOTHING)==false && poContent.equals("")==true) {
				if (poContentType.equals(poMesh.getMoContentType())==true) {
					oRetVal = true;
				}
			} else if (poContentType.equals(eContentType.NOTHING)==true && poContent.equals("")==false) {
				if (poContent.equals(poMesh.getMoContent())==true) {
					oRetVal = true;
				}
			} else {
				oRetVal = true;
			}
		
		return oRetVal;
	}
	
	/**
	 * Get all properties TP in from a certain mesh perception and associated memories.
	 * 
	 * If poContent = null, then it is not a criteria (String 1)
	 * If poContentType = null, then it is not a criteria (String 2)
	 * 
	 * pnMode
	 * 0: External and internal associations of the TPM
	 * 1: Only external associations of the TPM
	 * 2: Only internal associations of the TPM
	 * 
	 * (wendt)
	 *
	 * @since 06.09.2011 16:36:10
	 *
	 * @param poEnvironmentalPerception
	 * @param poAssociatedMemories
	 * @return
	 */
	private static ArrayList<clsAssociationAttribute> getTPAssociations(clsThingPresentationMesh poTPM, ArrayList<eContentType> poContentTypeFilterList, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationAttribute> oRetVal = new ArrayList<clsAssociationAttribute>();

		//If no filter set, add all associations
		if (poContentTypeFilterList.isEmpty()==true) {
			for (clsAssociation oAss : poTPM.getExternalMoAssociatedContent()) {
				if (oAss instanceof clsAssociationAttribute) {
					oRetVal.add((clsAssociationAttribute) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		} else {
			//Go through all external TPs
			for (eContentType oCTC : poContentTypeFilterList) {
				oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), oCTC, pbStopAtFirstMatch));
				if (pbStopAtFirstMatch==true && oRetVal.isEmpty()==false) {
					//Go through the external list
					//oRetVal.addAll(FilterTPList(poTPM.getExternalMoAssociatedContent(), oCTC, pbStopAtFirstMatch));
					break;
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all associations where a certain Content and content type is used
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 22:03:56
	 *
	 * @param poWPM
	 * @param poContentTypeAndContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	private static ArrayList<clsAssociationSecondary> getWPAssociations(clsWordPresentationMesh poWPM, ArrayList<clsPair<eContentType, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationSecondary> oRetVal = new ArrayList<clsAssociationSecondary>();

		//Go through all external
		for (clsPair<eContentType, String> oCTC : poContentTypeAndContent) {
			oRetVal.addAll(FilterWPList(poWPM.getMoInternalAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
			if (pbStopAtFirstMatch==false || oRetVal.isEmpty()==true) {
				//Go through the external list
				oRetVal.addAll(FilterWPList(poWPM.getExternalAssociatedContent(), oCTC.a, oCTC.b, pbStopAtFirstMatch));
				break;
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Filter a List of WP for certain content and contenttype
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:29:31
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	private static ArrayList<clsAssociationSecondary> FilterWPList(ArrayList<clsAssociation> poAssList, eContentType poContentType, String poContent, boolean pbStopAtFirstMatch) {
		ArrayList<clsAssociationSecondary> oRetVal = new ArrayList<clsAssociationSecondary>();
		
		for (clsAssociation oAss: poAssList) {
			//Check if attribute
			if (oAss instanceof clsAssociationSecondary && oAss.getLeafElement() instanceof clsWordPresentation) {
				if (poContentType.equals(eContentType.NOTHING)==false && poContent.equals("")==false) {
					if ((poContentType.equals(((clsAssociationSecondary)oAss).getLeafElement().getMoContentType())==true) &&
							(poContent.equals(((clsWordPresentation)oAss.getLeafElement()).getMoContent().toString())==true)) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals(eContentType.NOTHING)==false && poContent.equals("")==true) {
					if (poContentType.equals(((clsAssociationSecondary)oAss).getLeafElement().getMoContentType())==true) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else if (poContentType.equals(eContentType.NOTHING)==true && poContent.equals("")==false) {
					if (poContent.equals(((clsWordPresentation)oAss.getLeafElement()).getMoContent().toString())==true) {
						oRetVal.add((clsAssociationSecondary) oAss);
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				} else {
					oRetVal.add((clsAssociationSecondary) oAss);
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Recursively go through all elements in the mesh to get all TPMs, but here, this function is only used if there already exists a list
	 * 
	 * (wendt)
	 *
	 * @since 06.12.2011 11:37:00
	 *
	 * @param poMesh
	 * @return
	 */
	private static void searchDataStructureInWPM(clsWordPresentationMesh poMesh, ArrayList<clsWordPresentationMesh> poAddedElements, ArrayList<clsDataStructurePA> poRetVal, eDataType poDataType, ArrayList<clsPair<eContentType, String>> poContentTypeAndContent, boolean pbStopAtFirstMatch, int pnLevel) {
		//ArrayList<clsThingPresentationMesh> oRetVal = poAddedElements;
		
		//Add this element, in order not to search it through 2 times
		poAddedElements.add(poMesh);
		//Add the structure itself to the list of passed elements
		//Check this data structure for filter options and add the result to the result list if filter fits
		if (poDataType.equals(eDataType.WPM)==true) {
			//Check if this mesh matches the content and content type filter. If yes, then add the result
			for (clsPair<eContentType, String> oCTC : poContentTypeAndContent) {
				//Check if this mesh has this filter
				boolean bMatchFound = FilterWPM(poMesh, oCTC.a, oCTC.b);
				
				//As soon as positive, break loop
				if (bMatchFound==true) {
					poRetVal.add(poMesh);
					break;
				}
			}
		} else if (poDataType.equals(eDataType.WP)==true) {
			ArrayList<clsAssociationSecondary> oFoundWPAssList = getWPAssociations(poMesh, poContentTypeAndContent, pbStopAtFirstMatch);
			poRetVal.addAll(oFoundWPAssList);
		} else {
			try {
				throw new Exception("clsDataStructureTools: searchDataStructureInMesh: Only WPM allowed as data types");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (pbStopAtFirstMatch==false || poRetVal.isEmpty()==true) {	//=NOT Stopatfirstmatch=true AND oRetVal is not empty
			
			//Add the substructures of the internal associations
			if ((pnLevel>0) || (pnLevel==-1)) {
				for (clsAssociation oAss : poMesh.getMoInternalAssociatedContent()) {
					if (poAddedElements.contains(oAss.getLeafElement())==false && oAss.getLeafElement() instanceof clsWordPresentationMesh && oAss.getLeafElement().equals(poMesh)==false) {
						searchDataStructureInWPM((clsWordPresentationMesh) oAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					} else if (poAddedElements.contains(oAss.getRootElement())==false && oAss.getRootElement() instanceof clsWordPresentationMesh && oAss.getRootElement().equals(poMesh)==false) {
						searchDataStructureInWPM((clsWordPresentationMesh) oAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
					}
				}
			}
			
			//Add external associations to other external images
			if ((pnLevel>1) || (pnLevel==-1)) {
				for (clsAssociation oExtAss : poMesh.getExternalAssociatedContent()) {
					//Association WP contains TPM, therefore, here is not searched
					if ((oExtAss instanceof clsAssociationWordPresentation)==false) {
						if (poAddedElements.contains(oExtAss.getLeafElement())==false && oExtAss.getLeafElement() instanceof clsWordPresentationMesh && oExtAss.getLeafElement().equals(poMesh)==false) {
							searchDataStructureInWPM((clsWordPresentationMesh) oExtAss.getLeafElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
						} else if (poAddedElements.contains(oExtAss.getRootElement())==false && oExtAss.getRootElement() instanceof clsWordPresentationMesh && oExtAss.getRootElement().equals(poMesh)==false) {
							searchDataStructureInWPM((clsWordPresentationMesh) oExtAss.getRootElement(), poAddedElements, poRetVal, poDataType, poContentTypeAndContent, pbStopAtFirstMatch, pnLevel-1);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * Search a complete mesh for an instance of the search object, e.g. search for a certain WPM in a mesh, which may be not an instance.
	 * Comparison: ID, ContentType and Content. They have to be equal.
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 12:13:37
	 *
	 * @param poFindInMesh
	 * @param poFindDataStructure
	 * @return
	 */
	private static clsWordPresentationMesh searchInstanceOfDataStructureInWPMImageMeshbyID(clsWordPresentationMesh poFindInMesh, clsWordPresentationMesh poFindDataStructure) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsWordPresentationMesh> oWPMList = clsMeshTools.getAllWPMImages(poFindInMesh, 10);
		
		for (clsWordPresentationMesh oMeshWPM : oWPMList) {
			if (poFindDataStructure.getMoDS_ID()==oMeshWPM.getMoDS_ID() && 
					poFindDataStructure.getMoContentType()==oMeshWPM.getMoContentType() && 
					poFindDataStructure.getMoContent()==oMeshWPM.getMoContent()) {
				oResult = oMeshWPM;
				break;
			}
		}
		
		return oResult;
	}
	
	
	/**
	 * Search a complete mesh for an instance of the search object. The hascodes are compared
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 12:13:37
	 *
	 * @param poFindInMesh
	 * @param poFindDataStructure
	 * @return
	 */
	private static clsWordPresentationMesh searchInstanceOfDataStructureInWPMImageMeshbyHashCode(clsWordPresentationMesh poFindInMesh, clsWordPresentationMesh poFindDataStructure) {
		clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
		
		ArrayList<clsWordPresentationMesh> oWPMList = clsMeshTools.getAllWPMImages(poFindInMesh, 10);
		
		for (clsWordPresentationMesh oMeshWPM : oWPMList) {
			if (poFindDataStructure.equals(oMeshWPM)) {
				oResult = oMeshWPM;
				break;
			}
		}
		
		return oResult;
	}
	
	//=== SEARCH DATA STRUCTURES IN WPM GENERAL --- END ===//
	
	
	//=== ADD DATA STRUCTURES TO TPM GENERAL --- START ===//
	
	/**
	 * Creates a new AssociationPri between 2 containers and adds this association to the associated data structures.
	 * In this way, the match weight between a perceived image and a memory is stored
	 * 
	 * ONLY TPM ARE ALLOWED TO HAVE AN ASSOCIATION PRIMARY IN ITS EXTERNAL ASSOCIATIONS
	 * 
	 * (wendt)
	 *
	 * @since 22.07.2011 10:01:54
	 *
	 * @param poContainerA
	 * @param poContainerB
	 * @param prWeight
	 */
	public static void createAssociationPrimary(clsThingPresentationMesh poStructureA, clsThingPresentationMesh poStructureB, double prWeight) {
		eContentType oContentType = eContentType.PIASSOCIATION;
		clsAssociationPrimary oAssPri = (clsAssociationPrimary)clsDataStructureGenerator.generateASSOCIATIONPRI(oContentType, poStructureA, poStructureB, prWeight);
		poStructureA.getExternalMoAssociatedContent().add(oAssPri);
		poStructureB.getExternalMoAssociatedContent().add(oAssPri);
	}
	
	/**
	 * Create an association between a TPM and a TP
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:46:25
	 *
	 * @param poStructureA
	 * @param poStructureB
	 * @param prWeight
	 * @param pnAddMode: 0 = internal associations, 2
	 * @throws Exception 
	 */
	public static void createAssociationAttribute(clsThingPresentationMesh poStructureA, clsThingPresentation poStructureB, double prWeight, int pnAddMode) throws Exception {
		eContentType oContentType = eContentType.ASSOCIATIONATTRIBUTE;
		clsAssociationAttribute oAssAttr = (clsAssociationAttribute)clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(oContentType, poStructureA, poStructureB, prWeight);
		if (pnAddMode==0) {
			poStructureA.getMoInternalAssociatedContent().add(oAssAttr);
		} else if (pnAddMode==1) {
			poStructureA.getExternalMoAssociatedContent().add(oAssAttr);
		} else {
			throw new Exception("Only 0=add to internal associations or 1=add to external associations are selectable");
		}
		
	}
	
	/**
	 * Creates a new AssociationTemp between 2 structures and adds this association to the associated data structures.
	 * 
	 * ONLY TPM ARE ALLOWED TO HAVE AN ASSOCIATION TEMP IN ITS EXTERNAL ASSOCIATIONS
	 * 
	 * (wendt)
	 *
	 * @since 22.07.2011 10:01:54
	 *
	 * @param poContainerA
	 * @param poContainerB
	 * @param prWeight
	 */
	public static void createAssociationTemporary(clsThingPresentationMesh poSuperStructure, clsThingPresentationMesh poSubStructure, double prWeight) {
		eContentType oContentType = eContentType.PARTOFASSOCIATION;
		//Create association
		clsAssociationTime oAssTemp = (clsAssociationTime)clsDataStructureGenerator.generateASSOCIATIONTIME(oContentType, poSuperStructure, poSubStructure, prWeight);
		//Add association to the superstructure
		poSuperStructure.getMoInternalAssociatedContent().add(oAssTemp);
		//Add association to the substructure
		poSubStructure.getExternalMoAssociatedContent().add(oAssTemp);
	}
	
	/**
	 * Set a unique TP to a TPM. These TP are usually used for commands
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:48:25
	 *
	 * @param poTPM
	 * @param poTPContentType
	 * @param poTPContent
	 * @throws Exception 
	 */
	public static void setUniqueTP(clsThingPresentationMesh poTPM, eContentType poTPContentType, String poTPContent) throws Exception {
		//Get association if exists
		clsAssociation oAss = clsMeshTools.getUniqueTPAssociation(poTPM, poTPContentType);
		if (oAss!=null & oAss instanceof clsAssociationAttribute) {
			//Replace
			((clsThingPresentation)oAss.getLeafElement()).setMoContent(poTPContent);
		} else {
			//Create TP
			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(poTPContentType, poTPContent));
			//Create Association attribute
			clsMeshTools.createAssociationAttribute(poTPM, oTP, 1.0, 1);
		}
		
	}
	
	/**
	 * Get a unique Thing Presentation in a TPM. This option is used to get settings, which are saved as TPs
	 * 
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 13:57:08
	 *
	 * @param poTPM
	 * @param poTPContentType
	 * @return
	 */
	public static clsAssociation getUniqueTPAssociation(clsThingPresentationMesh poTPM, eContentType poTPAssociationContentType) {
		clsAssociation oResult = null;
		
		clsPair<eContentType, String> oFilter = new clsPair<eContentType, String>(poTPAssociationContentType, "");
		ArrayList<clsPair<eContentType, String>> oFilterList = new ArrayList<clsPair<eContentType, String>>();
		oFilterList.add(oFilter);
		
		oResult = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationTPM(poTPM, poTPAssociationContentType, 0, true);
		
		return oResult;
	}
	
	//=== ADD DATA STRUCTURES TO TPM GENERAL --- END ===//

	
	//=== ADD DATA STRUCTURES TO WPM GENERAL --- START ===//

	/**
	 * Create a new association secondary between 2 existing objects and add the association to the objects association lists (depending on add state)
	 * 
	 * (wendt)
	 *
	 * @since 25.01.2012 16:09:04
	 *
	 * @param <E> WPM or WP
	 * @param poElementOrigin Always a WPM
	 * @param nOriginAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
	 * @param poElementTarget WPM or WP
	 * @param nTargetAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
	 * @param prWeight
	 * @param poContenType
	 * @param poPredicate
	 * @param pbSwapDirectionAB
	 */
	public static <E extends clsSecondaryDataStructure> void createAssociationSecondary(clsWordPresentationMesh poElementOrigin, int nOriginAddAssociationState, E poElementTarget, int nTargetAddAssociationState, double prWeight, eContentType poContentType, ePredicate poPredicate, boolean pbSwapDirectionAB) {
		//Create association
		clsAssociationSecondary oNewAss;
		if (pbSwapDirectionAB==false) {
			oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, poElementOrigin, poElementTarget, poPredicate, prWeight);
		} else {
			oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, poElementTarget, poElementOrigin, poPredicate, prWeight);
		}
		
		//Process the original Element 
		if (nOriginAddAssociationState==1) {
			poElementOrigin.getMoInternalAssociatedContent().add(oNewAss);
		} else if (nOriginAddAssociationState==2) {
			poElementOrigin.getExternalAssociatedContent().add(oNewAss);
		}
		//If Associationstate=0, then do nothing
		
		//Add association to the target structure if it is a WPM
		if ((poElementTarget instanceof clsWordPresentationMesh) && (nOriginAddAssociationState!=0)) {
			if (nTargetAddAssociationState==1) {
				((clsWordPresentationMesh)poElementTarget).getMoInternalAssociatedContent().add(oNewAss);
			} else if (nTargetAddAssociationState==2) {
				((clsWordPresentationMesh)poElementTarget).getExternalAssociatedContent().add(oNewAss);
			}
		}
	}
	
	/**
	 * Set any word presentation to a certain WPM
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 17:04:04
	 *
	 * @param poWPM
	 * @param poAssContentType
	 * @param poAssPredicate
	 * @param poWPContentType
	 * @param poWPContent
	 */
	public static void setUniquePredicateWP(clsWordPresentationMesh poWPM, eContentType poAssContentType, ePredicate poAssPredicate, eContentType poWPContentType, String poWPContent, boolean pbAddToInternalAssociations) {
		//Get association if exists
		clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poWPM, poAssPredicate, 0, true);
		
		if (oAss==null) {
			//Create new WP
			clsWordPresentation oNewPresentation = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(poWPContentType, poWPContent));
			
			//Create and add association
			if (pbAddToInternalAssociations==false) {
				clsMeshTools.createAssociationSecondary(poWPM, 2, oNewPresentation, 0, 1.0, poAssContentType, poAssPredicate, false);
			} else {
				clsMeshTools.createAssociationSecondary(poWPM, 1, oNewPresentation, 0, 1.0, poAssContentType, poAssPredicate, false);
			}
			
			
		} else {
			((clsSecondaryDataStructure)oAss.getTheOtherElement(poWPM)).setMoContent(poWPContent);
		}
		
	}
	
	/**
	 * Set any word presentation to a certain WPM
	 * 
	 * Non unique = one predicate can have several content
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 22:11:51
	 *
	 * @param poWPM
	 * @param poAssContentType
	 * @param poAssPredicate
	 * @param poWPContentType
	 * @param poWPContent
	 */
	public static void setNonUniquePredicateWP(clsWordPresentationMesh poWPM, ePredicate poAssPredicate, eContentType poWPContentType, String poWPContent, boolean pbAddToInternalAssociations) {
		//Get association if exists
		ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(poWPM, poAssPredicate, 0, true, false);
		
		boolean bWPFound = false;
		
		for (clsDataStructurePA oAss : oAssList) {
			//Get WP
			clsWordPresentation oWP = (clsWordPresentation) ((clsAssociation)oAss).getLeafElement();
			if (oWP.getMoContent().equals(poWPContent) && oWP.getMoContentType().equals(poWPContentType)) {
				bWPFound = true;	//Do nothing as it is already set
				break;
			}
		}
		
		if (bWPFound==false) {
			//Create new WP
			clsWordPresentation oNewPresentation = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(poWPContentType, poWPContent));
			
			//Create and add association
			if (pbAddToInternalAssociations==false) {
				clsMeshTools.createAssociationSecondary(poWPM, 2, oNewPresentation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
			} else {
				clsMeshTools.createAssociationSecondary(poWPM, 1, oNewPresentation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
			}
			
			
		}
	}
	
	/**
	 * Remove all secondary data structures, which are connected with poWPM via a certain ePredicate
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 22:23:09
	 *
	 * @param poWPM
	 * @param poAssPredicate
	 */
	public static void removeAllNonUniquePredicateWP(clsWordPresentationMesh poWPM, ePredicate poAssPredicate) {
		ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(poWPM, poAssPredicate, 0, true, false);
		
		for (clsDataStructurePA oAss : oAssList) {
			clsMeshTools.removeAssociationInObject(poWPM, (clsSecondaryDataStructure)((clsAssociation)oAss).getLeafElement());
		}
		
	
	}
		
	/**
	 * Get the first WP for a certain predicate in a certian mesh
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:26:47
	 *
	 * @param poWPM
	 * @param poAssPredicate
	 * @return
	 */
	public static clsWordPresentation getUniquePredicateWP(clsWordPresentationMesh poWPM, ePredicate poAssPredicate) {
		//clsWordPresentation oResult = null;
		
		//clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poWPM, poAssPredicate, 0, true);
		clsWordPresentation oResult = (clsWordPresentation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poWPM, poAssPredicate, 0, false);
//		if (oAss!=null) {
//			oResult = (clsWordPresentation)oAss.getTheOtherElement(poWPM);
//		}
		
		return oResult;
	}
	
	/**
	 * Get all WP of a certain predicate
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 20:56:24
	 *
	 * @param poWPM
	 * @param poAssPredicate
	 * @return
	 */
	public static ArrayList<clsWordPresentation> getNonUniquePredicateWP(clsWordPresentationMesh poWPM, ePredicate poAssPredicate) {
		ArrayList<clsWordPresentation> oResult = new ArrayList<clsWordPresentation>();
			
		ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(poWPM, poAssPredicate, 0, false, false);

		for (clsDataStructurePA oDS : oDSList) {
			oResult.add((clsWordPresentation) oDS);
		}
			
		return oResult;
	}
	
	
	/**
	 * Add associations to both elements, if they only exist in one of the elements.
	 * This is especially useful at the image associations
	 * 
	 * (wendt)
	 *
	 * @since 24.05.2012 11:43:23
	 *
	 * @param poInput
	 */
	public static void setInverseAssociations(clsWordPresentationMesh poInput) {
		//Go through the associations and search for the other elements in the external associations
		System.out.println(poInput);
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationSecondary) {
				clsSecondaryDataStructure oOtherElement = (clsSecondaryDataStructure) oAss.getTheOtherElement(poInput);
				//At the other element, add this association to its external associations if it does not already exist
				if ((oOtherElement instanceof clsWordPresentationMesh) && 
						((clsWordPresentationMesh) oOtherElement).getExternalAssociatedContent().contains(oAss)==false) {
					((clsWordPresentationMesh)oOtherElement).getExternalAssociatedContent().add(oAss);
				}
			}
		}
	}
	
	//=== ADD DATA STRUCTURES TO WPM GENERAL --- END ===//
	
	
	//=== REMOVE DATA STRUCTURES IN TPM GENERAL --- START ===//
	/**
	 * Remove all associations, which connect 2 images or 2 entities, which are not in the same image. 
	 * Only entities within the same image can use the associationtemporary. All other shall use association primary 
	 * 
	 * (wendt)
	 *
	 * @since 23.05.2012 17:24:03
	 *
	 * @param poMesh
	 */
	public static void removeAllExternalAssociationsTPM(clsThingPresentationMesh poMesh) {
		
		ListIterator<clsAssociation> liList = poMesh.getExternalMoAssociatedContent().listIterator();
		while (liList.hasNext()) {
			clsAssociation oAss = liList.next();
			if (oAss instanceof clsAssociationPrimary) {
				liList.remove();
			}
		}
	}
	
	/**
	 * Remove all association time in an entitiy
	 * 
	 * Example: A cake shall be divided from its PI
	 * 
	 * (wendt)
	 *
	 * @since 23.07.2012 23:34:54
	 *
	 * @param poMesh
	 */
	public static void removeAllTemporaryAssociationsTPM(clsThingPresentationMesh poMesh) {
		ListIterator<clsAssociation> liList = poMesh.getExternalMoAssociatedContent().listIterator();
		while (liList.hasNext()) {
			clsAssociation oAss = liList.next();
			if (oAss instanceof clsAssociationTime) {
				liList.remove();
			}
		}
	}
	
	//=== REMOVE DATA STRUCTURES IN TPM GENERAL --- END ===//
	
	/**
	 * Delete a certain association within an object. 
	 * 
	 * The purpose is to remove an association in the target object for each object, which is deleted.
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:22:47
	 *
	 * @param poSourceTPM: The object which has the association
	 * @param poDeleteObject: The associatited object, which association shall be deleted.
	 */
	public static void deleteAssociationInObject(clsThingPresentationMesh poSourceTPM, clsThingPresentationMesh poDeleteObject) {
		boolean bFound = false;
		clsAssociation oDeleteAss = null;
		
		//Check external associations
		for (clsAssociation oExternalAss : poSourceTPM.getExternalMoAssociatedContent()) {
			if (oExternalAss.getLeafElement().equals(poDeleteObject) || oExternalAss.getRootElement().equals(poDeleteObject)) {
				bFound = true;
				oDeleteAss = oExternalAss;
				break;
			}
		}
		
		//If found, then delete
		if (bFound==true) {
			poSourceTPM.getExternalMoAssociatedContent().remove(oDeleteAss);
		} else {
			//Check internal associations
			for (clsAssociation oExternalAss : poSourceTPM.getMoInternalAssociatedContent()) {
				if (oExternalAss.getLeafElement().equals(poDeleteObject) || oExternalAss.getRootElement().equals(poDeleteObject)) {
					bFound = true;
					oDeleteAss = oExternalAss;
					break;
				}
			}
			
			if (bFound==true) {
				poSourceTPM.getMoInternalAssociatedContent().remove(oDeleteAss);
			}
		}
	}
	
	//=== REMOVE DATA STRUCTURES IN WPM GENERAL --- START ===//
	
	/**
	 * Delete a certain association within an object. 
	 * 
	 * The purpose is to remove an association in the target object for each object, which is deleted.
	 * 
	 * (wendt)
	 *
	 * @since 03.02.2012 16:22:47
	 *
	 * @param poSourceTPM: The object which has the association
	 * @param poDeleteObject: The associatited object, which association shall be deleted.
	 */
	public static void removeAssociationInObject(clsWordPresentationMesh poSourceWPM, clsSecondaryDataStructure poRootOrLeafDeleteObject) {
		boolean bFound = false;
		clsAssociation oDeleteAss = null;
		
		//Check external associations of the source
		for (clsAssociation oExternalAss : poSourceWPM.getExternalAssociatedContent()) {
			if (oExternalAss.getLeafElement().equals(poRootOrLeafDeleteObject) || oExternalAss.getRootElement().equals(poRootOrLeafDeleteObject)) {
				bFound = true;
				oDeleteAss = oExternalAss;
				break;
			}
		}
		
		//If found, then delete
		if (bFound==true) {
			poSourceWPM.getExternalAssociatedContent().remove(oDeleteAss);
		} else {
			//Check internal associations
			for (clsAssociation oExternalAss : poSourceWPM.getMoInternalAssociatedContent()) {
				if (oExternalAss.getLeafElement().equals(poRootOrLeafDeleteObject) || oExternalAss.getRootElement().equals(poRootOrLeafDeleteObject)) {
					bFound = true;
					oDeleteAss = oExternalAss;
					break;
				}
			}
			
			if (bFound==true) {
				poSourceWPM.getMoInternalAssociatedContent().remove(oDeleteAss);
			}
		}
	}
	
	/**
	 * Remove all associations in a WPM with a certain predicate
	 * 
	 * (wendt)
	 * 
	 * @since 23.07.2012 17:19:37
	 *
	 * @param poSourceWPM
	 * @param oPredicateToDelete
	 */
	public static void removeAssociationInObject(clsWordPresentationMesh poSourceWPM, ePredicate oPredicateToDelete) {
		ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(poSourceWPM, oPredicateToDelete, 0, true, false);
		
		for (clsDataStructurePA oAss : oAssList) {
			boolean bFoundAndRemoved = poSourceWPM.getExternalAssociatedContent().remove(oAss);
			
			if (bFoundAndRemoved==false) {
				poSourceWPM.getMoInternalAssociatedContent().remove(oAss);
			}
		}
	}
	
	//=== REMOVE DATA STRUCTURES IN WPM GENERAL --- END ===//
	
	
	
	//=== PERCEIVED IMAGE TOOLS TPM --- START ===//
	/**
	 * Get the self object in the image TPM
	 * 
	 * (wendt)
	 *
	 * @since 01.06.2012 12:25:24
	 *
	 * @param poImage
	 * @return
	 */
	public static clsThingPresentationMesh getSELF(clsThingPresentationMesh poImage) {
		 clsThingPresentationMesh oTPMRetVal = null;
		
		for (clsAssociation oAss : ((clsThingPresentationMesh)poImage).getMoInternalAssociatedContent()) {
			if (oAss instanceof clsAssociationTime && oAss.getLeafElement() instanceof clsThingPresentationMesh) {
				if (((clsThingPresentationMesh)oAss.getLeafElement()).getMoContent().equals(eContent.SELF)) {
					oTPMRetVal = (clsThingPresentationMesh)oAss.getLeafElement();
					break;
				}
			}
		}
			
		return oTPMRetVal;
	}

	//=== PERCEIVED IMAGE AND REMEMBERED IMAGE TOOLS TPM --- START ===//
	
	/**
	 * Get all images in a mesh, i. e. contentType = RI or PI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMImages(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Get all TPM in the mesh
		//ArrayList<clsThingPresentationMesh> oAllTPM = getTPMInMesh(poPerceptionalMesh, pnLevel);
		
		//Add PI. There is only one
		ArrayList<eContentType> oContentTypePI = new ArrayList<eContentType>();
		oContentTypePI.add(eContentType.PI);
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypePI, true, pnLevel));
		
		//Add all RI. 
		ArrayList<eContentType> oContentTypeRI = new ArrayList<eContentType>();
		oContentTypeRI.add(eContentType.RI);
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeRI, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Merge 2 meshes. Only TPM are allowed. Move all associations from the new mesh to the source mesh
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:06:35
	 *
	 * @param poSourceTPM
	 * @param poNewMesh
	 */
	public static void mergeMesh(clsThingPresentationMesh poSourceMesh, clsThingPresentationMesh poNewMesh) {
		//Check if both presentations are TPM or WPM. Else nothing is done
		//if (poSourceMesh instanceof clsThingPresentationMesh && poNewMesh instanceof clsThingPresentationMesh) {
		//Get source mesh list
		ArrayList<clsThingPresentationMesh> oSourceTPMList = getAllTPMImages(poSourceMesh, mnMaxLevel);
			
		//Get the new mesh list
		ArrayList<clsThingPresentationMesh> oNewTPMList = getAllTPMImages(poNewMesh, mnMaxLevel);
		
		//Go through each mesh in the newMesh
		for (clsThingPresentationMesh oNewTPM : oNewTPMList) {
			//Go through each mesh in the source list
			for (clsThingPresentationMesh oSourceTPM : oSourceTPMList) {
				//If there are IDs with -1, it is not allowed and should be thrown as exception
				if (oSourceTPM.getMoDS_ID()==-1) {
					try {
						throw new Exception("Error: DataStructureTools, mergeMesh: A TPM with ID = -1 was found");
					} catch (Exception e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
				//If the images are equal, then transfer the associations
				if (oSourceTPM.getMoDS_ID() == oNewTPM.getMoDS_ID()) {
					moveAllAssociations(oSourceTPM, oNewTPM);
					break;
				}
			}
		}
	}
	
	/**
	 * Move an association from the origin TPM to the target TPM
	 * 
	 * (wendt)
	 *
	 * @since 03.01.2012 20:43:49
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 * @param poAssociation
	 */
	public static void moveAssociation(clsThingPresentationMesh poTargetTPM, clsThingPresentationMesh poOriginTPM, clsAssociation poAssociation) {
		//1. Check if Element A or B has the origin structure
		if ((poAssociation.getRootElement().equals(poOriginTPM)==true) || (poAssociation.getLeafElement().equals(poOriginTPM)==true)) {
			if (poAssociation.getRootElement().equals(poOriginTPM)==true) {
				poAssociation.setRootElement(poTargetTPM);
			} else {
				poAssociation.setLeafElement(poTargetTPM);
			}
			
			if (poOriginTPM.getExternalMoAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetTPM.getExternalMoAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getExternalMoAssociatedContent().remove(poAssociation);
			} else if (poOriginTPM.getMoInternalAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetTPM.getMoInternalAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				poOriginTPM.getMoInternalAssociatedContent().remove(poAssociation);
			}

		}
	}
	
	/**
	 * Move all associations, which do not already exist from the origin structure to the target structure. In that way it is
	 * possible to merge 2 meshes over the same structure.
	 * 
	 * This is the TPM function
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:09:18
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 */
	public static void moveAllAssociations(clsThingPresentationMesh poTargetTPM, clsThingPresentationMesh poOriginTPM) {
		//Move all internal associations from origin to target
		for (clsAssociation oOriAss : poOriginTPM.getMoInternalAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetTPM.getMoInternalAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				moveAssociation(poTargetTPM, poOriginTPM, oOriAss);
			}
		}
		
		//Move all external associations from origin to target
		for (clsAssociation oOriAss : poOriginTPM.getExternalMoAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetTPM.getExternalMoAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false && oOriAss.getMoDS_ID()!=-1) {
				//Move the association
				moveAssociation(poTargetTPM, poOriginTPM, oOriAss);
			}
		}
	}
	
	/**
	 * Delete an object in a mesh. Ony the deleteobject is the input, as the object is deleted as soon as the delete object is 
	 * not connected to any mesh. All meshes, where this object was present are modified.
	 * 
	 * (wendt)
	 *
	 * @since 30.01.2012 21:02:46
	 *
	 * @param poPerceptionalMesh
	 * @param poDeleteObject
	 */
	public static void deleteObjectInMesh(clsThingPresentationMesh poDeleteObject) {
		//In this function the whole tree shall be deleted, of which a structure is connected. Start with the focused element and
		//delete the "delete-Element" as well as the whole tree connected with it.
		
		//Go through all internal associations
		for (clsAssociation oInternalAss : poDeleteObject.getMoInternalAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Go through all external associations
		for (clsAssociation oInternalAss : poDeleteObject.getExternalMoAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsThingPresentationMesh) {
					clsThingPresentationMesh oRelatedElement = (clsThingPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					deleteAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Now the object is not connected with anything more and can be seen as deleted from the meshes, which it was connected. It does not matter in which mesh it belongs, everything is deleted
	}
	
	//=== PERCEIVED IMAGE AND REMEMBERED IMAGE TOOLS TPM --- END ===//
	
	//=== PERCEIVED IMAGE AND REMEMBERED IMAGE TOOLS WPM --- START ===//
	
	/**
	 * Get all images in a WPM mesh, i. e. contentType = RI or PI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getAllWPMImages(clsWordPresentationMesh poMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		//Add all RI. 
		ArrayList<clsPair<eContentType, String>> oContentTypeAndContentPairRI = new ArrayList<clsPair<eContentType, String>>();
		oContentTypeAndContentPairRI.add(new clsPair<eContentType, String>(eContentType.RI, ""));
		oFoundImages.addAll(getDataStructureInWPM(poMesh, eDataType.WPM, oContentTypeAndContentPairRI, false, pnLevel));
		
		for (clsDataStructurePA oWPM : oFoundImages) {
			oRetVal.add((clsWordPresentationMesh) oWPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Move an association from the origin WPM to the target WPM
	 * 
	 * (wendt)
	 *
	 * @since 03.01.2012 20:43:49
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 * @param poAssociation
	 */
	public static void moveAssociation(clsWordPresentationMesh poTargetWPM, clsWordPresentationMesh poOriginWPM, clsAssociation poAssociation, boolean removeAfterMove) {
		//1. Check if Element A or B has the origin structure
		if ((poAssociation.getRootElement().equals(poOriginWPM)==true) || (poAssociation.getLeafElement().equals(poOriginWPM)==true)) {
			if (poAssociation.getRootElement().equals(poOriginWPM)==true) {
				poAssociation.setRootElement(poTargetWPM);
			} else {
				poAssociation.setLeafElement(poTargetWPM);
			}
			
			if (poOriginWPM.getExternalAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetWPM.getExternalAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				if (removeAfterMove==true) {
					poOriginWPM.getExternalAssociatedContent().remove(poAssociation);
				}
				
			} else if (poOriginWPM.getMoInternalAssociatedContent().contains(poAssociation)) {
				//2. Add the changed association to the targetTPM
				poTargetWPM.getMoInternalAssociatedContent().add(poAssociation);
				//3. Remove the association from the originTPM
				if (removeAfterMove==true) {
					poOriginWPM.getMoInternalAssociatedContent().remove(poAssociation);
				}
				
			}

		}
	}
	
	
	/**
	 * Merge 2 meshes. Only WPM are allowed. Move all associations from the new mesh to the source mesh
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 20:06:35
	 *
	 * @param poSourceTPM
	 * @param poNewMesh
	 */
	public static void mergeMesh(clsWordPresentationMesh poSourceMesh, clsWordPresentationMesh poNewMesh) {
		//Check if both presentations are TPM or WPM. Else nothing is done
		//if (poSourceMesh instanceof clsThingPresentationMesh && poNewMesh instanceof clsThingPresentationMesh) {
		//Get source mesh list
		ArrayList<clsWordPresentationMesh> oSourceWPMList = getAllWPMImages(poSourceMesh, mnMaxLevel);
			
		//Get the new mesh list
		ArrayList<clsWordPresentationMesh> oNewWPMList = getAllWPMImages(poNewMesh, mnMaxLevel);
		
		//Create process pairs Source and New
		ArrayList<clsPair<clsWordPresentationMesh, clsWordPresentationMesh>> oInstancePairList = new ArrayList<clsPair<clsWordPresentationMesh, clsWordPresentationMesh>>();
		
		//Go through each mesh in the newMesh
		for (int i=0; i<oNewWPMList.size();i++) {
			clsWordPresentationMesh oNewWPM = oNewWPMList.get(i);
			clsWordPresentationMesh oFoundSourceMeshWPM = clsMeshTools.getNullObjectWPM();
			
			//Go through each mesh in the source list
			for (int j=0; j<oSourceWPMList.size();j++) {
				clsWordPresentationMesh oSourceWPM = oSourceWPMList.get(j);
				
				//If there are IDs with -1, it is not allowed and should be thrown as exception
				if (oSourceWPM.getMoDS_ID()==-1) {
					try {
						throw new Exception("Error: DataStructureTools, mergeMesh: A TPM with ID = -1 was found");
					} catch (Exception e) {
						// TODO (wendt) - Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				//If the images are equal but not the same instance, then transfer the associations
				if (oSourceWPM.getMoDS_ID() == oNewWPM.getMoDS_ID()) {
					oFoundSourceMeshWPM = oSourceWPM;
					break;
				}
			}
			
			oInstancePairList.add(new clsPair<clsWordPresentationMesh, clsWordPresentationMesh>(oNewWPM, oFoundSourceMeshWPM));		
		} 
		
		//Now all WPM-Matches have been listed in the instancePairlist
		for (clsPair<clsWordPresentationMesh, clsWordPresentationMesh> oInstancePair : oInstancePairList) {
			//Move all associations from the NEWWPM to the SOURCEWPM
			
			//Move associations from the new mesh to the source b->a
			//removeAllExternalAssociationsWithSameID(oInstancePair.a, oInstancePair.b);
			//Move all associations from the NEWWPM to the SOURCEWPM
			if (oInstancePair.b.isNullObject()==false) {
				clsMeshTools.moveAllAssociationsMergeMesh(oInstancePair.b, oInstancePair.a);
			} else {
				//This is a new object, then add it
				clsMeshTools.addWPMImageToWPMImageMesh(poSourceMesh, oInstancePair.a);	
			}
		}
	}
	
	/**
	 * Move all associations from one mesh to another. Only the direct associations are considered.
	 * Cases:
	 * 1. WPM Association already exists -> Do nothing
	 * 2. WP association already exists -> Replace the WP with the new one
	 * 3. WP association does not exist -> Move the association
	 * 4. WPM association does not already exist -> Find if the other element of the MoveFromMesh in the MoveToMesh. If it exists, move association, 
	 * if not, do nothing. All new elements will be handled separately
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 12:21:59
	 *
	 * @param poMoveToMesh
	 * @param poMoveFromMesh
	 */
	private static void moveAllAssociationsMergeMesh(clsWordPresentationMesh poMoveToMesh, clsWordPresentationMesh poMoveFromMesh) {
		
		//Check if the instances are equal
		if (poMoveToMesh.equals(poMoveFromMesh)) {
			return;
		}
		
		//Internal associations
		for (clsAssociation oIntMoveFromAss : poMoveFromMesh.getMoInternalAssociatedContent()) {
			//Find this Ass in the internal Ass of the moveto mesh
			boolean bAssExist=false;
			for (clsAssociation oIntMoveToAss : poMoveToMesh.getMoInternalAssociatedContent()) {
				bAssExist = clsMeshTools.checkAssociationExists(oIntMoveToAss, oIntMoveFromAss);
				//Association found
				if (bAssExist==true) {
					//Only if one of the associations is a WP, then it will be replaced		
					if (oIntMoveToAss.getTheOtherElement(poMoveToMesh) instanceof clsWordPresentation) {
						clsWordPresentation oNewWP = (clsWordPresentation) oIntMoveFromAss.getLeafElement();
						oIntMoveToAss.setLeafElement(oNewWP);
					}
					//Else nothing is done, as the association already exists
					break;
				}
			}
			
			//In case the association does not exist at all, but the data structure may exist
			if (bAssExist==false) {
				//Check if WP, if WP, then add the whole association
				if (oIntMoveFromAss.getLeafElement() instanceof clsWordPresentation) {
					//Set the root element
					oIntMoveFromAss.setRootElement(poMoveToMesh);
					poMoveToMesh.getMoInternalAssociatedContent().add(oIntMoveFromAss);
				} else {
					//Search the other element in the sourcemesh. If it exists, then change the association, if it does not exist, do nothing
					//as the association to the new element is added by the new element
					
					//Then it has to be a WPM
					clsDataStructurePA oOtherUnknownElement = oIntMoveFromAss.getTheOtherElement(poMoveFromMesh);
					if (oOtherUnknownElement instanceof clsWordPresentationMesh) {
						clsWordPresentationMesh oMeshElementWPM = clsMeshTools.searchInstanceOfDataStructureInWPMImageMeshbyID(poMoveToMesh, (clsWordPresentationMesh) oOtherUnknownElement);
						if (oMeshElementWPM.isNullObject()==false) {
							//Now, replace the other element of the MoveFromMesh with the found element in the MoveToMesh
							oIntMoveFromAss.setTheOtherElement(poMoveFromMesh, oMeshElementWPM);
							//Then replace the MoveFromMesh with the MoveToMesh
							oIntMoveFromAss.setTheOtherElement(oMeshElementWPM, poMoveToMesh);
							//Add the association to the MoveToMesh
							poMoveToMesh.getMoInternalAssociatedContent().add(oIntMoveFromAss);
						}
						
						//Else nothing is done, as this structure is new to the mesh and will be handled separately
						
					} else {
						try {
							throw new Exception("Only WPM can be addressed here.");
						} catch (Exception e) {
							// TODO (wendt) - Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}
			}
		}
		
		//TODO: Primary process associations are not moved. If it is needed, it should be implemented
		
		//External associations
		for (clsAssociation oExtMoveFromAss : poMoveFromMesh.getExternalAssociatedContent()) {
			
			if (oExtMoveFromAss instanceof clsAssociationSecondary) {
				//Find this Ass in the internal Ass of the moveto mesh
				boolean bAssExist=false;
				for (clsAssociation oExtMoveToAss : poMoveToMesh.getExternalAssociatedContent()) {
					bAssExist = clsMeshTools.checkAssociationExists(oExtMoveToAss, oExtMoveFromAss);
					//Association found
					if (bAssExist==true) {
						//Only if one of the associations is a WP, then it will be replaced		
						if (oExtMoveToAss.getTheOtherElement(poMoveToMesh) instanceof clsWordPresentation) {
							clsWordPresentation oNewWP = (clsWordPresentation) oExtMoveFromAss.getLeafElement();
							oExtMoveToAss.setLeafElement(oNewWP);
						}
						//Else nothing is done, as the association already exists
						break;
					}
				}
				
				//In case the association does not exist at all, but the data structure may exist
				if (bAssExist==false) {
					//Check if WP, if WP, then add the whole association
					if (oExtMoveFromAss.getLeafElement() instanceof clsWordPresentation) {
						//Set the root element
						oExtMoveFromAss.setRootElement(poMoveToMesh);
						poMoveToMesh.getMoInternalAssociatedContent().add(oExtMoveFromAss);
					} else {
						//Search the other element in the sourcemesh. If it exists, then change the association, if it does not exist, do nothing
						//as the association to the new element is added by the new element
						
						//Then it has to be a WPM
						clsDataStructurePA oOtherUnknownElement = oExtMoveFromAss.getTheOtherElement(poMoveFromMesh);
						if (oOtherUnknownElement instanceof clsWordPresentationMesh) {
							clsWordPresentationMesh oMeshElementWPM = clsMeshTools.searchInstanceOfDataStructureInWPMImageMeshbyID(poMoveToMesh, (clsWordPresentationMesh) oOtherUnknownElement);
							if (oMeshElementWPM.isNullObject()==false) {
								//Now, replace the other element of the MoveFromMesh with the found element in the MoveToMesh
								oExtMoveFromAss.setTheOtherElement(poMoveFromMesh, oMeshElementWPM);
								//Then replace the MoveFromMesh with the MoveToMesh
								oExtMoveFromAss.setTheOtherElement(oMeshElementWPM, poMoveToMesh);
								//Add the association to the MoveToMesh
								poMoveToMesh.getMoInternalAssociatedContent().add(oExtMoveFromAss);
							}
							
							//Else nothing is done, as this structure is new to the mesh and will be handled separately
							
						} else {
							try {
								throw new Exception("Only WPM can be addressed here.");
							} catch (Exception e) {
								// TODO (wendt) - Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
				}
			}
		}
		
	}
	
	/**
	 * Add a new WPM to a mesh and redirect all associations
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 12:32:18
	 *
	 * @param poMoveToMesh
	 * @param poNewStructure
	 */
	private static void addWPMImageToWPMImageMesh(clsWordPresentationMesh poMoveToMesh, clsWordPresentationMesh poNewStructure) {
		//Check if this instance already exists in the mesh
		clsWordPresentationMesh oExistingMesh = searchInstanceOfDataStructureInWPMImageMeshbyHashCode(poMoveToMesh, poNewStructure);
		if (oExistingMesh.isNullObject()==true) {
			//Nothing has to be done as the structure already exists
			return;
		}
		
		//Go through internal associations
		//TODO: Implement if necessary

		//Go through external associations
		for (clsAssociation oAss : poNewStructure.getExternalAssociatedContent()) {
			//Get the other element
			clsDataStructurePA oOtherElement  = oAss.getTheOtherElement(poNewStructure);
			
			//Only WPM are treated
			if (oOtherElement instanceof clsWordPresentationMesh) {
				//Find in mesh
				clsWordPresentationMesh oOtherElementInMesh = clsMeshTools.searchInstanceOfDataStructureInWPMImageMeshbyID(poMoveToMesh, (clsWordPresentationMesh) oOtherElement);
				//If found, then replace the current other element with the element from the mesh and add the association to the mesh element
				//if not found, do nothing, as the new other element has to be added separately
				if (oOtherElementInMesh.isNullObject()==false) {
					oAss.setTheOtherElement(poNewStructure, oOtherElementInMesh);
					oOtherElementInMesh.getExternalAssociatedContent().add(oAss);
				}
			}
		}
	}
	
	
	
	/**
	 * Removes all associations which are identical but not the same instance
	 * 
	 * (wendt)
	 *
	 * @since 25.07.2012 02:16:31
	 *
	 * @param poOldWPM
	 * @param poNewWPM
	 */
	private static void removeAllExternalAssociationsWithSameID(clsWordPresentationMesh poOldWPM, clsWordPresentationMesh poNewWPM) {
		ArrayList<clsAssociation> oResult = new ArrayList<clsAssociation>();
		
		for(clsAssociation oOldAss : poOldWPM.getExternalAssociatedContent()) {
			for(clsAssociation oNewAss : poNewWPM.getExternalAssociatedContent()) {
				if(clsMeshTools.checkAssociationExists(oOldAss, oNewAss)) {
					oResult.add(oOldAss);
				}
			}
		}
		
		for (clsAssociation oAss : oResult) {
			poOldWPM.getExternalAssociatedContent().remove(oAss);
		}
	}
	
	/**
	 * Move all associations, start of the recursive function.
	 * 
	 * (wendt)
	 *
	 * @since 25.07.2012 00:44:53
	 *
	 * @param poTargetWPM
	 * @param poOriginWPM
	 */
	public static void moveAllAssociations(clsWordPresentationMesh poTargetWPM, clsWordPresentationMesh poOriginWPM) {
		ArrayList<clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation>> oTaskList = new ArrayList<clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation>>();
		moveAllAssociations(poTargetWPM, poOriginWPM, new ArrayList<clsDataStructurePA>(), oTaskList);
		
		//Do all changes
		for (clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation> oTask : oTaskList) {
			//moveAssociation(oTask.a, oTask.b, oTask.c, true);
		}
	}
	
	
	/**
	 * Move all associations, which do not already exist from the origin structure to the taregt structure. In that way it is
	 * possible to merge 2 meshes over the same structure
	 * 
	 * This function is only valid for the WPM
	 * 
	 * This function only moves the associations within one image
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:09:18
	 *
	 * @param poTargetTPM
	 * @param poOriginTPM
	 */
	public static void moveAllAssociations(clsWordPresentationMesh poTargetWPM, clsWordPresentationMesh poOriginWPM, ArrayList<clsDataStructurePA> poMovedAssociationList, ArrayList<clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation>> poTaskList) {
		
		//For the recursive function: Add processed objects
		poMovedAssociationList.add(poOriginWPM);
		poMovedAssociationList.add(poTargetWPM);
		
		//Move all internal associations from origin to target
		
		if (poTargetWPM==null || poOriginWPM==null) {
			try {
				throw new Exception("Error in F21, moveAllAssociations: poTargetWPM = " + poTargetWPM + "or poOriginWPM = " + poOriginWPM +" is null");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		//Create a list of associations, which shall be moved
		ArrayList<clsAssociation> oAssList = new ArrayList<clsAssociation>();
		
		ArrayList<clsPair<clsAssociation, clsAssociation>> poTaskListInt = new ArrayList<clsPair<clsAssociation, clsAssociation>>();
		for (clsAssociation oOriAss : poOriginWPM.getMoInternalAssociatedContent()) {
			//Only if this association has not been processed yet, it can be moved
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetWPM.getMoInternalAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					//If it already exists, then the object associations shall be transferred recursively
					//1. Get the other objects
//					clsDataStructurePA OtherOriWPM = oOriAss.getTheOtherElement(poOriginWPM);
//					clsDataStructurePA OtherTarWPM = oTarAss.getTheOtherElement(poTargetWPM);
//					
//					if (poMovedAssociationList.contains(OtherOriWPM)==false && OtherOriWPM instanceof clsWordPresentationMesh && OtherTarWPM instanceof clsWordPresentationMesh) {
//						clsMeshTools.moveAllAssociations((clsWordPresentationMesh)OtherTarWPM, (clsWordPresentationMesh)OtherOriWPM, poMovedAssociationList, poTaskList);
//					}
					poTaskListInt.add(new clsPair<clsAssociation, clsAssociation>(oOriAss, oTarAss));
					
					//break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				oAssList.add(oOriAss);
				//poTaskList.add(new clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation>(poTargetWPM, poOriginWPM, oOriAss));
				//moveAssociation(poTargetWPM, poOriginWPM, oOriAss);
			}
		}
		
		for (clsPair<clsAssociation, clsAssociation> oP : poTaskListInt) {
			clsDataStructurePA OtherOriWPM = oP.a.getTheOtherElement(poOriginWPM);
			clsDataStructurePA OtherTarWPM = oP.b.getTheOtherElement(poTargetWPM);
			
			if (poMovedAssociationList.contains(OtherOriWPM)==false && OtherOriWPM instanceof clsWordPresentationMesh && OtherTarWPM instanceof clsWordPresentationMesh) {
				clsMeshTools.moveAllAssociations((clsWordPresentationMesh)OtherTarWPM, (clsWordPresentationMesh)OtherOriWPM, poMovedAssociationList, poTaskList);
			}
		}
		
		//Move all external associations from origin to target
		ArrayList<clsPair<clsAssociation, clsAssociation>> poTaskListExt = new ArrayList<clsPair<clsAssociation, clsAssociation>>();
		for (clsAssociation oOriAss : poOriginWPM.getExternalAssociatedContent()) {
			boolean bFound = false;
			for (clsAssociation oTarAss : poTargetWPM.getExternalAssociatedContent()) {
				bFound = checkAssociationExists(oOriAss, oTarAss);
				if (bFound==true) {
					//If it already exists, then the object associations shall be transferred recursively
					//1. Get the other objects
					poTaskListExt.add(new clsPair<clsAssociation, clsAssociation>(oOriAss, oTarAss));
					
//					clsDataStructurePA OtherOriWPM = oOriAss.getTheOtherElement(poOriginWPM);
//					clsDataStructurePA OtherTarWPM = oTarAss.getTheOtherElement(poTargetWPM);
//					
//					if (poMovedAssociationList.contains(OtherOriWPM)==false && OtherOriWPM instanceof clsWordPresentationMesh && OtherTarWPM instanceof clsWordPresentationMesh) {
//						clsMeshTools.moveAllAssociations((clsWordPresentationMesh)OtherTarWPM, (clsWordPresentationMesh)OtherOriWPM, poMovedAssociationList, poTaskList);
//					}
					
					//break;
				}
			}
			
			//If the association does not exist, then move the association
			if (bFound==false) {
				//Move the association
				oAssList.add(oOriAss);
				//poTaskList.add(new clsTriple<clsWordPresentationMesh, clsWordPresentationMesh, clsAssociation>(poTargetWPM, poOriginWPM, oOriAss));
						
				//moveAssociation(poTargetWPM, poOriginWPM, oOriAss);
			}
		}
		
		for (clsPair<clsAssociation, clsAssociation> oP : poTaskListExt) {
			clsDataStructurePA OtherOriWPM = oP.a.getTheOtherElement(poOriginWPM);
			clsDataStructurePA OtherTarWPM = oP.b.getTheOtherElement(poTargetWPM);
			
			if (poMovedAssociationList.contains(OtherOriWPM)==false && OtherOriWPM instanceof clsWordPresentationMesh && OtherTarWPM instanceof clsWordPresentationMesh) {
				clsMeshTools.moveAllAssociations((clsWordPresentationMesh)OtherTarWPM, (clsWordPresentationMesh)OtherOriWPM, poMovedAssociationList, poTaskList);
			}
		}
		
		
		
		for (clsAssociation oAss : oAssList) {
			//Move from the origin, i. e. the cloned value to the inout value
			moveAssociation(poTargetWPM, poOriginWPM, oAss, true);
			
		}
	}
	
	
	/**
	 * Check if 2 associations are the same. If yes, give true, else false
	 * 
	 * (wendt)
	 *
	 * @since 31.01.2012 21:00:36
	 *
	 * @param poAssA
	 * @param poAssB
	 * @return
	 */
	private static boolean checkAssociationExists(clsAssociation poAssA, clsAssociation poAssB) {
		boolean bRetVal = false;
		
		if (poAssA.equals(poAssB)) {
			bRetVal=true;
		} else if (poAssA.getMoDS_ID() == poAssB.getMoDS_ID() && 
				poAssA.getRootElement().getMoDS_ID() == poAssB.getRootElement().getMoDS_ID() &&
				poAssA.getLeafElement().getMoDS_ID() == poAssB.getLeafElement().getMoDS_ID() &&
				poAssA.getRootElement().getMoContentType().equals(poAssB.getRootElement().getMoContentType())==true &&
				poAssA.getLeafElement().getMoContentType().equals(poAssB.getLeafElement().getMoContentType())==true) {
					
			if (poAssA instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)poAssA).getMoPredicate().equals(((clsAssociationSecondary)poAssB).getMoPredicate())) {
					bRetVal = true;
				}
			} else {
				bRetVal = true;
			}
		}
		
		return bRetVal;
	}
	
	/**
	 * Get all internal image associations, which are not present in the input list from an image
	 * 
	 * (wendt)
	 *
	 * @since 19.06.2012 22:32:30
	 *
	 * @param poImage
	 * @param poEntityList
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getOtherInternalImageAssociations(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMesh> poEntityList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsAssociation oAss : poImage.getMoInternalAssociatedContent()) {
			clsWordPresentationMesh oImageEntity = (clsWordPresentationMesh) oAss.getLeafElement();
			if (poEntityList.contains(oImageEntity)==false) {
				oRetVal.add(oImageEntity);
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Delete an object in a mesh. Ony the deleteobject is the input, as the object is deleted as soon as the delete object is 
	 * not connected to any mesh. All meshes, where this object was present are modified.
	 * 
	 * (wendt)
	 *
	 * @since 30.01.2012 21:02:46
	 *
	 * @param poPerceptionalMesh
	 * @param poDeleteObject
	 */
	public static void deleteObjectInMesh(clsWordPresentationMesh poDeleteObject) {
		//In this function the whole tree shall be deleted, of which a structure is connected. Start with the focused element and
		//delete the "delete-Element" as well as the whole tree connected with it.
		
		//Go through all internal associations
		for (clsAssociation oInternalAss : poDeleteObject.getMoInternalAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsWordPresentationMesh) {
					clsWordPresentationMesh oRelatedElement = (clsWordPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					removeAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsWordPresentationMesh) {
					clsWordPresentationMesh oRelatedElement = (clsWordPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					removeAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Go through all external associations
		for (clsAssociation oInternalAss : poDeleteObject.getExternalAssociatedContent()) {
			//If the root element is this mesh, then...
			if (oInternalAss.getRootElement().equals(poDeleteObject)) {
				//go to the other element, if it is a TPM
				if (oInternalAss.getLeafElement() instanceof clsWordPresentationMesh) {
					clsWordPresentationMesh oRelatedElement = (clsWordPresentationMesh) oInternalAss.getLeafElement();
					//Find this element in the other element and delete it
					removeAssociationInObject(oRelatedElement, poDeleteObject);
				}				
			} else if (oInternalAss.getLeafElement().equals(poDeleteObject)) {
				if (oInternalAss.getRootElement() instanceof clsWordPresentationMesh) {
					clsWordPresentationMesh oRelatedElement = (clsWordPresentationMesh) oInternalAss.getRootElement();
					//Find this element in the other element and delete it
					removeAssociationInObject(oRelatedElement, poDeleteObject);
				}
			}
		}
		
		//Now the object is not connected with anything more and can be seen as deleted from the meshes, which it was connected. It does not matter in which mesh it belongs, everything is deleted
	}
	
	
	//=== PERCEIVED IMAGE AND REMEMBERED IMAGE TOOLS WPM --- END ===//
	
	
	//=== PERCEIVED IMAGE TOOLS TPM --- END ===//
	
	
	//=== PERCEIVED IMAGE TOOLS WPM --- START ===//


	//=== PERCEIVED IMAGE TOOLS WPM --- END ===//

	
	//=== REMEMBERED IMAGE TOOLS TPM --- START ===//
	
	/**
	 * Create a new TPM as Top-Object from a list of TPMs, i. e. an image
	 * 
	 * (wendt)
	 *
	 * @since 29.11.2011 15:04:29
	 *
	 * @param poInput
	 * @param poContentType
	 * @param poContent
	 * @return
	 */
	public static clsThingPresentationMesh createTPMImage(ArrayList<clsThingPresentationMesh> poInput, eContentType poContentType, String poContent) {
		clsThingPresentationMesh oRetVal = null;
		
		clsTriple<Integer, eDataType, eContentType> oTPMIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.TPM, poContentType);
		clsThingPresentationMesh oConstructedImage = new clsThingPresentationMesh(oTPMIdentifier, new ArrayList<clsAssociation>(), poContent);
		
		addTPMToTPMImage(oConstructedImage, poInput);
		
		oRetVal = oConstructedImage;
		
		return oRetVal;
	}
	
	/**
	 * Add a list of TPM to another TPM as parts of it to its intrinsic structures
	 * 
	 * (wendt)
	 *
	 * @since 05.12.2011 15:33:14
	 *
	 * @param poInput: List of structures, which shall be added
	 * @param poMesh: The image, which shall receive the structures
	 */
	public static void addTPMToTPMImage(clsThingPresentationMesh oImage, ArrayList<clsThingPresentationMesh> oAddList) {
		//Modify the image by adding additional compontents
		
		for (clsThingPresentationMesh oC : oAddList) {
			createAssociationTemporary(oImage, oC, 1.0);
		}
		
	}
	
	/**
	 * Get all memories (images, contentType = Remembered image RI) in a mesh, i. e. contentType = RI
	 * 
	 * (wendt)
	 *
	 * @since 28.12.2011 10:30:25
	 *
	 * @param poPerceptionalMesh
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMMemories(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Add all RI. 
		ArrayList<eContentType> oContentTypeRI = new ArrayList<eContentType>();
		oContentTypeRI.add(eContentType.RI);
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, oContentTypeRI, false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all TPM with a certain contenttype and content
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:56:02
	 *
	 * @param poPerceptionalMesh
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @param pnLevel
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> getAllTPMObjects(clsThingPresentationMesh poPerceptionalMesh, int pnLevel) {
		ArrayList<clsDataStructurePA> oFoundImages = new ArrayList<clsDataStructurePA>();
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		//Get all TPM for that level
		oFoundImages.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.TPM, new ArrayList<eContentType>(), false, pnLevel));
		
		for (clsDataStructurePA oTPM : oFoundImages) {
			oRetVal.add((clsThingPresentationMesh) oTPM);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get a list of all drivemeshes in a certain thing presentation mesh, pnLevel=1 (Only this image)
	 * (wendt)
	 *
	 * @since 15.01.2012 17:52:22
	 *
	 * @param poPerceptionalMesh
	 * @return
	 */
	public static ArrayList<clsAssociationDriveMesh> getAllDMInMesh(clsThingPresentationMesh poPerceptionalMesh) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		//This is an unconverted clsAssociationDriveMesh
		ArrayList<clsDataStructurePA> oFoundList = getDataStructureInTPM(poPerceptionalMesh, eDataType.DM, new ArrayList<eContentType>(), false, 1);
		
		for (clsDataStructurePA oAss : oFoundList) {
			oRetVal.add((clsAssociationDriveMesh) oAss);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get all drive meshes from a TPM image, which fulfill certain contenty type and content
	 * 
	 * (wendt)
	 *
	 * @since 30.01.2012 21:02:43
	 *
	 * @param poPerceptionalMesh
	 * @param poFilterContentTypeAndContent
	 * @return
	 */
	public static ArrayList<clsAssociationDriveMesh> getSelectedDMInImage(clsThingPresentationMesh poPerceptionalMesh, ArrayList<eContentType> poFilterContentType) {
		ArrayList<clsAssociationDriveMesh> oRetVal = new ArrayList<clsAssociationDriveMesh>();
		
		ArrayList<clsDataStructurePA> oFoundList = new ArrayList<clsDataStructurePA>();
		
		for (clsAssociation oAss : poPerceptionalMesh.getMoInternalAssociatedContent()) {
			if (oAss instanceof clsAssociationTime) {
				oFoundList.addAll(getDataStructureInTPM((clsThingPresentationMesh) oAss.getLeafElement(), eDataType.DM, poFilterContentType, false, 1));
			}
		}
						
		for (clsDataStructurePA oAss : oFoundList) {
			oRetVal.add((clsAssociationDriveMesh) oAss);
		}
		
		return oRetVal;
	}
	
	
	/**
	 * Get all emotions in an image or entity
	 * 
	 * (wendt)
	 *
	 * @since 31.08.2012 12:47:04
	 *
	 * @param poPerceptionalMesh
	 * @return
	 */
	public static ArrayList<clsAssociationEmotion> getAllEmotionsInImage(clsThingPresentationMesh poPerceptionalMesh) {
		ArrayList<clsAssociationEmotion> oRetVal = new ArrayList<clsAssociationEmotion>();
		
		ArrayList<clsDataStructurePA> oFoundList = new ArrayList<clsDataStructurePA>();
		
		//Add for this image
		oFoundList.addAll(getDataStructureInTPM(poPerceptionalMesh, eDataType.EMOTION, new ArrayList<eContentType>(), false, 1));
		
		//Add for all entities
		for (clsAssociation oAss : poPerceptionalMesh.getMoInternalAssociatedContent()) {
			if (oAss instanceof clsAssociationTime) {
				oFoundList.addAll(getDataStructureInTPM((clsThingPresentationMesh) oAss.getLeafElement(), eDataType.EMOTION, new ArrayList<eContentType>(), false, 1));
			}
		}
						
		for (clsDataStructurePA oAss : oFoundList) {
			oRetVal.add((clsAssociationEmotion) oAss);
		}
		
		return oRetVal;
	}
	 
	/**
	 * Filter a list of TPMs for certain content and content type. NULL counts as nothing and is not used as filter criterium
	 * 
	 * (wendt)
	 *
	 * @since 02.01.2012 21:57:09
	 *
	 * @param poAssList
	 * @param poContentType
	 * @param poContent
	 * @param pbStopAtFirstMatch
	 * @return
	 */
	public static ArrayList<clsThingPresentationMesh> filterTPMList(ArrayList<clsThingPresentationMesh> poMeshList, String poContentType, String poContent, boolean bStopAtFirstMatch) {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		for (clsThingPresentationMesh oTPM : poMeshList)
			if (poContentType!=null && poContent!=null) {
				if ((poContentType.equals(oTPM.getMoContentType())==true) &&
						(poContent.equals(oTPM.getMoContent())==true)) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else if (poContentType!=null && poContent==null) {
				if (poContentType.equals(oTPM.getMoContentType())==true) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else if (poContentType==null && poContent!=null) {
				if (poContent.equals(oTPM.getMoContent())==true) {
					oRetVal.add(oTPM);
					if (bStopAtFirstMatch==true) {
						break;
					}
				}
			} else {
				oRetVal.add(oTPM);
				if (bStopAtFirstMatch==true) {
					break;
				}
			}
		
		return oRetVal;
	}

	//=== REMEMBERED IMAGE TOOLS TPM --- END ===//
	
	
	//=== REMEMBERED IMAGE TOOLS WPM --- START ===//
	
	public static clsWordPresentationMesh createWPMImage(ArrayList<clsSecondaryDataStructure> poInput, eContentType poContentType, String poContent) {
		clsWordPresentationMesh oRetVal = null;
		
		clsTriple<Integer, eDataType, eContentType> oWPMIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, poContentType);
		clsWordPresentationMesh oConstructedImage = new clsWordPresentationMesh(oWPMIdentifier, new ArrayList<clsAssociation>(), poContent);
		
		clsMeshTools.addSecondaryDataStructuresToWPMImage(oConstructedImage, poInput);
		//addTPMToTPMImage(oConstructedImage, poInput);
		
		oRetVal = oConstructedImage;
		
		return oRetVal;
	}
	
	/**
	 * Add a list of WPM or WP to another WPM as parts of it to its intrinsic structures
	 * 
	 * (wendt)
	 *
	 * @since 05.12.2011 15:33:14
	 *
	 * @param poInput: List of structures, which shall be added
	 * @param poMesh: The image, which shall receive the structures
	 */
	public static void addSecondaryDataStructuresToWPMImage(clsWordPresentationMesh oImage, ArrayList<clsSecondaryDataStructure> oAddList) {
		//Modify the image by adding additional compontents
		
		for (clsSecondaryDataStructure oC : oAddList) {
			clsMeshTools.createAssociationSecondary(oImage, 1, oC, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPART, false);
		}
		
	}
	
	/**
	 * Get the super structure of a data structure. If the input is its own super structure, then
	 * return itself.
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 13:27:40
	 *
	 * @param poInput
	 * @return
	 */
	public static clsWordPresentationMesh getSuperStructure(clsWordPresentationMesh poInput) {
		clsWordPresentationMesh oRetVal = poInput;
		
		//If it is an image, this will work
		clsWordPresentationMesh oSuperStructure = (clsWordPresentationMesh) searchFirstDataStructureOverAssociationWPM(poInput, ePredicate.HASSUPER, 2, false);
				
		//If it is an entity, this will work
		if (oSuperStructure==null) {
			oSuperStructure = (clsWordPresentationMesh) searchFirstDataStructureOverAssociationWPM(poInput, ePredicate.HASPART, 1, false);		
		}
		
		if (oSuperStructure!=null) {
			oRetVal = oSuperStructure;
		}
		
		return oRetVal;
	}
	
	/**
	 * Get a list with all sub objects of an image, i. e. the objects of the image
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 17:14:48
	 *
	 * @param poImage
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getAllSubWPMInWPMImage(clsWordPresentationMesh poImage) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsAssociation oAss : poImage.getMoInternalAssociatedContent()) {
			oRetVal.add((clsWordPresentationMesh) oAss.getLeafElement());
		}
		
		return oRetVal;
	}
	
	/**
	 * Get the self object in the image WPM
	 * 
	 * (wendt)
	 *
	 * @since 01.06.2012 12:24:58
	 *
	 * @param poImage
	 * @return
	 */
	public static clsWordPresentationMesh getSELF(clsWordPresentationMesh poImage) {
		clsWordPresentationMesh oRetVal = null;
		
		ArrayList<clsPair<eContentType, String>> oFilterContent = new ArrayList<clsPair<eContentType, String>>();
		oFilterContent.add(new clsPair<eContentType, String>(eContentType.ENTITY, eContent.SELF.toString()));
		ArrayList<clsDataStructurePA> oFoundItems = clsMeshTools.getDataStructureInWPM(poImage, eDataType.WPM, oFilterContent, true, 1);
		
		if (oFoundItems.isEmpty()==false) {
			oRetVal = (clsWordPresentationMesh) oFoundItems.get(0);
		}
		
		return oRetVal;
	}

	public static clsWordPresentationMesh createImageFromEntity(clsWordPresentationMesh poEntity, eContentType poImageContentType) {
		clsWordPresentationMesh oResult = null;
		
		//Check if the WPM has a primary data structure
		clsThingPresentationMesh oEntityTPMPart = clsMeshTools.getPrimaryDataStructureOfWPM(poEntity);
		try {
			if (oEntityTPMPart.isNullObject()==true) {
				throw new Exception("No TPM-Part exists for this image. All entities must have PP parts.");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Create TPM Image
		ArrayList<clsThingPresentationMesh> oEntityListTPM = new ArrayList<clsThingPresentationMesh>();
		oEntityListTPM.add(oEntityTPMPart);
		clsThingPresentationMesh oTPMImage = clsMeshTools.createTPMImage(oEntityListTPM, poImageContentType, eContent.ENTITY2IMAGE.toString());
		
		//Create WPM image
		ArrayList<clsSecondaryDataStructure> oImageContent = new ArrayList<clsSecondaryDataStructure>();
		oImageContent.add(poEntity);
		
		oResult = clsMeshTools.createWPMImage(oImageContent, poImageContentType, eContent.ENTITY2IMAGE.toString());
		
		//Create WP association
		clsAssociationWordPresentation oAssWP = (clsAssociationWordPresentation) clsDataStructureGenerator.generateASSOCIATIONWP(eContentType.ASSOCIATIONWP, oResult, oTPMImage, 1.0);
		
		oResult.getExternalAssociatedContent().add(oAssWP);
		
		return oResult;
	}


		
	//=== REMEMBERED IMAGE TOOLS WPM --- END ===//
	
	//=== ENTITY TOOLS TPM --- START ===//
	
	//=== ENTITY TOOLS TPM --- END ===//
	
	//=== ENTITY TOOLS WPM --- START ===//
	
	//=== ENTITY TOOLS WPM --- END ===//
	
	
	
	
}
