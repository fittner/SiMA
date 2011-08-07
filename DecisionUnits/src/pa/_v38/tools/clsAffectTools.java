/**
 * CHANGELOG
 *
 * 25.06.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.06.2011, 09:29:24
 * 
 */
public class clsAffectTools {
	
	//Added by AW, in order to be able to add drive goals from perception and memories
	private static ArrayList<String> oPossibleDriveGoals = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 20.07.2011 13:58:37
	 *
	 * @param poImage
	 * @return
	 */
	public static double calculateAbsoluteAffect(clsPrimaryDataStructureContainer poImage) {
		double rAbsoluteAffect;
		
		rAbsoluteAffect = 0;
		
		for (clsAssociation oAss: poImage.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationDriveMesh) {
				rAbsoluteAffect += java.lang.Math.abs(((clsDriveMesh)oAss.getLeafElement()).getPleasure());
			}
		}
		return rAbsoluteAffect;
	}
	
	/**
	 * Get all possible goals from the intention of the perception act.
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 29.07.2011 10:03:27
	 *
	 * @param poPredictionInput
	 * @return
	 */
	public static ArrayList<clsSecondaryDataStructureContainer> getDriveGoalsFromPrediction(clsPrediction poPredictionInput) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		//Format WP: BITE||ENTITY:CARROT|LOCATION:FARLEFT|NOURISH:HIGH|BITE:HIGH|
		//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|NOURISH:HIGH|BITE:HIGH|
		
		//Get intention
		clsDataStructureContainerPair oIntention = poPredictionInput.getIntention();
		if (oIntention.getSecondaryComponent()==null) {
			return null;
		}
		clsSecondaryDataStructureContainer oIntentionSecondary = oIntention.getSecondaryComponent();
		clsSecondaryDataStructure oIntentionBasicDS = (clsSecondaryDataStructure) oIntentionSecondary.getMoDataStructure();
		
		//Get either from the word presentation or from the word presentation mesh
		if (oIntentionBasicDS instanceof clsSecondaryDataStructure) {
			//If no Mesh
			if (oIntentionBasicDS instanceof clsWordPresentation) {
				try {
					oRetVal = getDriveGoals((clsWordPresentation)oIntentionBasicDS, oIntentionSecondary);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (oIntentionBasicDS instanceof clsWordPresentationMesh) {
				try {
					oRetVal = getWPMDriveGoals(oIntentionSecondary);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract possible drive goals from a word presentation mesh
	 * (wendt)
	 *
	 * @since 29.07.2011 14:16:29
	 *
	 * @param poWPInput
	 * @param poContainer
	 * @return
	 * @throws Exception 
	 */
	public static ArrayList<clsSecondaryDataStructureContainer> getWPMDriveGoals(clsSecondaryDataStructureContainer poContainer) throws Exception {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		//FIXME AW Hack - Get Content from Base Image and drives from the sub images
		//Go through the base element
		if (poContainer.getMoDataStructure() instanceof clsWordPresentationMesh) {
			//Get possible drive goals from the base structure
			try {
				oRetVal.addAll(getDriveGoals((clsSecondaryDataStructure) poContainer.getMoDataStructure(), poContainer));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//Go through the sub elements
			for (clsAssociation oAss : ((clsWordPresentationMesh)poContainer.getMoDataStructure()).getMoAssociatedContent()) {
				clsSecondaryDataStructure oSubDS = (clsSecondaryDataStructure) oAss.getRootElement();	//Get root from the association secondary
				
				try {
					ArrayList<clsSecondaryDataStructureContainer> oReceivedGoals = getDriveGoals(oSubDS, poContainer);
					//Check if this goal already exists and if it does not, then add the new goal
					for (clsSecondaryDataStructureContainer oContainer : oReceivedGoals) {
						boolean blFoundGoal = false;
						for (clsSecondaryDataStructureContainer oRetValContainer : oRetVal) {
							if (((clsWordPresentation)oRetValContainer.getMoDataStructure()).getMoContent().equals(((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent())) {
								blFoundGoal = true;
								break;
							}
						}
						if (blFoundGoal == false) {
							oRetVal.add(oContainer);
						}	
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} else {
			throw new Exception("clsAffectTools, getWPMDriveGoals: This datatype is an unallowed input. Only WPM allowed"); 
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 29.07.2011 14:16:25
	 *
	 * @param poWPInput
	 * @param poContainer
	 * @return
	 * @throws Exception 
	 */
	private static ArrayList<clsSecondaryDataStructureContainer> getDriveGoals(clsSecondaryDataStructure poInput, clsSecondaryDataStructureContainer poContainer) throws Exception {
		
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();

		//Go through all drive goals in the list
		for (String oDriveGoal : oPossibleDriveGoals) {
			//Set the input 
			String oExternalContent = "";
			if (poInput instanceof clsWordPresentation) {
				oExternalContent = ((clsWordPresentation)poInput).getMoContent();
			} else if (poInput instanceof clsWordPresentationMesh) {
				oExternalContent = ((clsWordPresentationMesh)poInput).getMoContent();
			}
			else {
				throw new Exception("clsAffectTools: getDriveGoals: This datatype is an unallowed input");
			}
			//Check if the word presentation contains any of the possible drive goals
			if(oExternalContent.contains(oDriveGoal)) {
			//Get the base expression
				String oBaseContent = "";
				if (poContainer.getMoDataStructure() instanceof clsWordPresentation) {
					oBaseContent = ((clsWordPresentation)poContainer.getMoDataStructure()).getMoContent();
				} else if (poContainer.getMoDataStructure() instanceof clsWordPresentationMesh) {
					oBaseContent = ((clsWordPresentationMesh)poContainer.getMoDataStructure()).getMoContent();
				}
				else {
					throw new Exception("clsAffectTools, getDriveGoals: This datatype is an unallowed input");
				}
			
				String oBaseContentType = poContainer.getMoDataStructure().getMoContentType();
			
				//String oGoalContent = oDriveGoal + _Delimiter02 + oExternalContent;
				
				//Format WP: BITE||ENTITY:CARROT|LOCATION:FARLEFT|NOURISH:HIGH|BITE:HIGH|
				//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|NOURISH:HIGH|BITE:HIGH|
				
				String oGoalContent = createSubcontents(oBaseContent, oBaseContentType, oExternalContent, oDriveGoal);
				
				//Define data structure for the goal
				clsWordPresentation oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent)); 
				//Get all associated structures with this goal
				ArrayList<clsAssociation> oAssociatedDS = poContainer.getMoAssociatedDataStructures(poInput);
				//Create the container
				oRetVal.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
			}
		}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 30.07.2011 14:45:39
	 *
	 * @param poBaseContent
	 * @param poBaseContentType
	 * @param poSubContent
	 * @param poDriveGoal
	 * @return
	 */
	private static String createSubcontents(String poBaseContent, String poBaseContentType, String poSubContent, String poDriveGoal) {
		String oRetVal = "";
		
		//String oEntityString = "ENTITY";
		
		
		//If the content itself is the base content, then nothing has to be done, else the base content must be adapted to an image
		String oNewBaseContent = "";
		if (poBaseContent!=poSubContent) {	//i.e. it is a WP
			oNewBaseContent = poBaseContentType + _Delimiter01  + poBaseContent;
		}
		
		//Create new subcontent
		//String oNewSubcontent = "";

		//Split all parts of the sub base content
		//String[] oSubEntries = poSubContent.split("\\|");
		//First add the entity if found
		/*for (String oE : oSubEntries) {
			if (oE.contains(oEntityString)) {
				oNewSubcontent += oE + _Delimiter03;
				break;
			}
		}
		//Then, add drive goal information
		for (String oE : oSubEntries) {
			if (oE.contains(poDriveGoal)) {
				oNewSubcontent += oE + _Delimiter03;
			}
		}*/

		//Format these drive goals: BITE||IMAGE:A1TOP|ENTITY:CARROT|NOURISH:HIGH|BITE:HIGH|
		
		//oRetVal = poDriveGoal + _Delimiter02 + oNewBaseContent + _Delimiter03 + oNewSubcontent;
		oRetVal = poDriveGoal + _Delimiter02 + oNewBaseContent + _Delimiter03 + poSubContent;
		
		return oRetVal;
		
	}
	
	/**
	 * Sort the input list of the drive demands according to max pleasure
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 05.08.2011 22:16:51
	 *
	 * @param poDriveDemandsList
	 */
	public static ArrayList<clsSecondaryDataStructureContainer> sortDriveDemands(ArrayList<clsSecondaryDataStructureContainer> poDriveDemandsList) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		//If the list is empty return
		if (poDriveDemandsList.size()<=1) {
			return oRetVal; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//Set list of drives in the order of drive priority, FIXME KD: Which drives have priority and how is that changed if they have the same affect
		//FIXME CM: What drives do exist????
		ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		//TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>> oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		
		ArrayList<clsTriple<Integer, Integer, clsSecondaryDataStructureContainer>> oNewList = new ArrayList<clsTriple<Integer, Integer, clsSecondaryDataStructureContainer>>();
		
		//Go through the original list
		for (int i=0; i<poDriveDemandsList.size();i++) {	//Go through each element in the list
			//The the content of each drive
			clsSecondaryDataStructureContainer oContainer = poDriveDemandsList.get(i);
			//Get the content of the datatype in the container
			String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent();
			//Sort first for affect
			int nAffect = getDriveIntensity(oContent);
			//Sort then for drive
			String oDriveType = getDriveType(oContent);
			int nDriveIndex = oKeyWords.size()-oKeyWords.indexOf(oDriveType)-1;	//The higher the better
			
			int nIndex = 0;
			//Increase index if the list is not empty
			while((oNewList.isEmpty()==false) && 
					(nIndex<oNewList.size()) &&
					(oNewList.get(nIndex).a >= nAffect) &&
					(oNewList.get(nIndex).b > nDriveIndex)) {
				nIndex++;
			}
			
			oNewList.add(nIndex, new clsTriple<Integer, Integer, clsSecondaryDataStructureContainer>(nAffect, nDriveIndex, oContainer));
		}
		
		//Add results to the new list
		for (int i=0; i<oNewList.size();i++) {
			oRetVal.add(i, oNewList.get(i).c);
		}
		
		return oRetVal;
	}
	
	/**
	 * Get drive intensity or affect of a drive
	 * (wendt)
	 *
	 * @since 05.08.2011 22:30:45
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static int getDriveIntensity(String poDriveContent) {
		int nIntensity =  0;
		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
		String oDriveIntensity = oDrive.split(_Delimiter01)[1];
		nIntensity = eAffectLevel.valueOf(oDriveIntensity).ordinal();
		
		return nIntensity;
	}
	
	/**
	 * Extract the type of drive like NOURISH, BITE etc... from an input string of drive content
	 * (wendt) - insert description
	 *
	 * @since 05.08.2011 22:33:54
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static String getDriveType(String poDriveContent) {
		String oDrive = poDriveContent.split("\\" + _Delimiter03)[0];
		String oDriveType = oDrive.split(_Delimiter01)[0];

		return oDriveType;
	}
	
	/**
	 * Extract the Drive object from an input string of a drive content
	 * (wendt) - insert description
	 *
	 * @since 05.08.2011 22:33:52
	 *
	 * @param poDriveContent
	 * @return
	 */
	public static String getDriveObjectType(String poDriveContent) {
		String oDriveObject = poDriveContent.split("\\" + _Delimiter03)[1];
		//String oDriveContentType = oDrive.split(_Delimiter01)[1];

		return oDriveObject;
	}

	
}


