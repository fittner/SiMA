/**
 * CHANGELOG
 *
 * 06.07.2012 wendt - File created
 *
 */
package secondaryprocess.datamanipulation;

import java.util.ArrayList;

import org.slf4j.Logger;

import logger.clsLogger;
import memorymgmt.enums.PsychicSpreadingActivationMode;
import memorymgmt.enums.eAction;
import memorymgmt.enums.eActionType;
import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContent;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.ePredicate;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 06.07.2012, 13:47:41
 * 
 */
public class clsActionTools {
	private final static clsWordPresentationMesh moNullObjectWPM = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT.toString()), new ArrayList<clsAssociation>());
	   private static final Logger log = clsLogger.getLog("action");

	
	/**
	 * @since 05.07.2012 22:04:13
	 * 
	 * @return the moNullObjectWPM
	 */
	public static clsWordPresentationMesh getNullObjectWPM() {
		return moNullObjectWPM;
	}
	
	public static clsWordPresentationMesh createAction(eAction poAction) {
		clsWordPresentationMesh oResult = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ACTION, poAction.toString()), new ArrayList<clsAssociation>());
		
		clsActionTools.setActionType(oResult);
		
		return oResult;
	}
	
	public static clsWordPresentationMesh createAction(eAction poAction, clsWordPresentationMesh poActionObject) {
        clsWordPresentationMesh oAction = createAction(poAction);
        
        setActionObject(oAction, poActionObject);
        
        return oAction;
    }
    
    private static boolean setActionObject(clsWordPresentationMesh oAction, clsWordPresentationMesh poActionObject) {
        boolean bOutcome = false;
        
        if(oAction != null && poActionObject != null) {
            clsAssociationSecondary oObjectAssociation = clsMeshTools.createAssociationSecondary(oAction, 1, poActionObject, 1, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTIONOBJECT, false);
            bOutcome = true;
        }
        
        return bOutcome;
    }
	
    public static clsWordPresentationMesh getActionObject(clsWordPresentationMesh poAction) {
        clsWordPresentationMesh oActionObject = null;
        
        if(poAction != null && !poAction.isNullObject()) {
            oActionObject = clsMeshTools.getUniquePredicateWPM(poAction, ePredicate.HASACTIONOBJECT);
        }
        
        return oActionObject;
    }
    
	public static void setActionType(clsWordPresentationMesh oResult) {
		
		if (clsActionTools.getAction(oResult).equals(eAction.FOCUS_ON.toString()) || 
				clsActionTools.getAction(oResult).equals(eAction.SEND_TO_PHANTASY.toString()) || 
				clsActionTools.getAction(oResult).equals(eAction.FOCUS_MOVE_FORWARD.toString()) || 
				clsActionTools.getAction(oResult).equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString()) ||
				clsActionTools.getAction(oResult).equals(eAction.NONE.toString())) {
			
			clsActionTools.setActionType(oResult, eActionType.SINGLE_INTERNAL);
			
		} else if (clsActionTools.getAction(oResult).equals(eAction.SEARCH1) ||
				clsActionTools.getAction(oResult).equals(eAction.FLEE)) {
			
			clsActionTools.setActionType(oResult, eActionType.COMPOSED_EXTERNAL);
		} else {
			clsActionTools.setActionType(oResult, eActionType.SINGLE_EXTERNAL);
		}
		
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 25.07.2012 12:22:55
	 *
	 * @param poAction
	 * @return
	 */
	public static String getAction(clsWordPresentationMesh poAction) {
		return poAction.getContent();
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 25.07.2012 12:22:57
	 *
	 * @param poAction
	 * @param poActionName
	 */
	public static void setAction(clsWordPresentationMesh poAction, eAction poActionName) {
		poAction.setContent(poActionName.toString());
	}
	
	/**
	 * Set the supportive data structure of an action
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 15:56:12
	 *
	 * @param poMentalSituation
	 * @param poDataStructure
	 */
	public static void setSupportiveDataStructureForAction(clsWordPresentationMesh poAction, clsWordPresentationMesh poDataStructure) {
		clsMeshTools.setNonUniquePredicateWPM(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, poDataStructure, false);		
	}
	
//	/**
//	 * Get the supportive data structure of an action
//	 * 
//	 * (wendt)
//	 *
//	 * @since 09.07.2012 15:55:48
//	 *
//	 * @param poAction
//	 * @return
//	 */
//	public static ArrayList<clsWordPresentationMesh> getSupportiveDataStructureForAction(clsWordPresentationMesh poAction) {
//	    ArrayList<clsWordPresentationMesh> retVal = new ArrayList<clsWordPresentationMesh>();
//		
//		ArrayList<clsSecondaryDataStructure> oFoundStructures = poAction.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, false);
//		
//		for (clsSecondaryDataStructure ds : oFoundStructures) {
//		    retVal.add((clsWordPresentationMesh) ds);
//		}
//		
//		return retVal;
//	}
	
	/**
	 * Get the supportive data structure of an action
	 * 
	 * (wendt)
	 *
	 * @since 09.07.2012 15:55:48
	 *
	 * @param poAction
	 * @return
	 */
	public static ArrayList<clsWordPresentationMesh> getSupportiveDataStructureForAction(clsWordPresentationMesh poAction) {
	    ArrayList<clsWordPresentationMesh> result = clsMeshTools.getNonUniquePredicateWPM(poAction, ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION);
	    
		return result;
	}
	
	/**
	 * Set phantasyflag
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:30:09
	 *
	 * @param poAction
	 */
	public static void setPhantasyFlag(clsWordPresentationMesh poAction) {
		clsMeshTools.setUniquePredicateWP(poAction, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPHANTASYFLAG, eContentType.PHANTASYFLAG, eContent.TRUE.toString(), false);
	}
	
	/**
	 * Get the phantasyflag if any is set
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:29:51
	 *
	 * @param poAction
	 * @return
	 */
	public static PsychicSpreadingActivationMode getPhantasyFlag(clsWordPresentationMesh poAction) {
	    PsychicSpreadingActivationMode oResult = PsychicSpreadingActivationMode.NONE;
		
	    clsWordPresentation value = clsMeshTools.getUniquePredicateWP(poAction, ePredicate.HASPHANTASYFLAG);
		
		if (value!=null) {
			oResult = PsychicSpreadingActivationMode.valueOf(value.getContent());
		}
		
		return oResult;
	}
	
	/**
	 * Set action type
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:30:09
	 *
	 * @param poAction
	 */
	public static void setActionType(clsWordPresentationMesh poAction, eActionType poActionType) {
		clsMeshTools.setUniquePredicateWP(poAction, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTIONTYPE, eContentType.ACTIONTYPE, poActionType.toString(), false);
	}
	
	/**
	 * Get the get action type
	 * 
	 * (wendt)
	 *
	 * @since 12.07.2012 17:29:51
	 *
	 * @param poAction
	 * @return
	 */
	public static eActionType getActionType(clsWordPresentationMesh poAction) {
		eActionType oResult = eActionType.NULLOBJECT;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poAction, ePredicate.HASACTIONTYPE);
		
		if (oWP!=null) {
			oResult = eActionType.valueOf(oWP.getContent());
		}
		
		return oResult;
	}
	
	/**
	 * Get Preconditions
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 20:59:12
	 *
	 * @param poAction
	 * @return
	 */
	public static ArrayList<eCondition> getPreconditions(clsWordPresentationMesh poAction) {
		ArrayList<eCondition> oResult = new ArrayList<eCondition>();
		
		ArrayList<clsWordPresentation> oWPList = clsMeshTools.getNonUniquePredicateWP(poAction, ePredicate.HASPRECONDITION);
		
		for (clsWordPresentation oWP : oWPList) {
			oResult.add(eCondition.valueOf(oWP.getContent()));
		}
		
		return oResult;
	}
	
	/**
	 * Check if a certain action has a certain precondition
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 21:02:37
	 *
	 * @param poAction
	 * @param poDecisionTask
	 * @return
	 */
	public static boolean checkIfPreconditionsMatch(clsWordPresentationMesh poAction, ArrayList<eCondition> poInputTaskStatusList) {
		boolean bResult = true;
		
		if (poInputTaskStatusList.isEmpty()==true) {
			bResult=false;
		} else {
			ArrayList<eCondition> oActionPreconditionList = clsActionTools.getPreconditions(poAction);
		
			//Check if ALL preconditions can be matched
			for (eCondition oTaskStatus : oActionPreconditionList) {
				if (poInputTaskStatusList.contains(oTaskStatus)==false) {
					bResult = false;
					break;
				}
			}
		}
		
		
		return bResult;
	}
	
}
