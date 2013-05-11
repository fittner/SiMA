/**
 * CHANGELOG
 *
 * 16.07.2012 wendt - File created
 *
 */
package pa._v38.tools;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.07.2012, 15:11:50
 * 
 */
public class clsPhantasyTools {
	
	
	
	/**
	 * Sets a phantasy flag TRUE in both the WPM and TPM
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 15:13:20
	 *
	 * @param poWPM
	 * @throws Exception 
	 */
	public static void setPhantasyFlagTrue(clsWordPresentationMesh poWPM) throws Exception {
		//Set WP
		clsMeshTools.setUniquePredicateWP(poWPM, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPHANTASYFLAG, eContentType.PHANTASYFLAG, eContent.TRUE.toString(), false);
		
		//Get TPM
		clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(poWPM);
		
		if (oTPM == null) {
			throw new NullPointerException("No TPM connected to this phantasy. The phantasy cannot be sent to the primary process");
		}
		
		//Set TP
		clsMeshTools.setUniqueTP(oTPM, eContentType.PHANTASYFLAG, "TRUE");
	}
	
	/**
	 * Sets a phantasy flag FALSE in both the WPM and TPM
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 15:13:20
	 *
	 * @param poWPM
	 * @throws Exception 
	 */
	public static void setPhantasyFlagFalse(clsWordPresentationMesh poWPM) throws Exception {
		//Set WP
		clsMeshTools.setUniquePredicateWP(poWPM, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPHANTASYFLAG, eContentType.PHANTASYFLAG, eContent.FALSE.toString(), false);
		
		//Get TPM
		clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(poWPM);
		
		if (oTPM == null) {
			throw new NullPointerException("No TPM connected to this phantasy. The phantasy cannot be sent to the primary process");
		}
		
		//Set TP
		clsMeshTools.setUniqueTP(oTPM, eContentType.PHANTASYFLAG, "TRUE");
	}
		
	/**
	 * Get the status of the phantasyflag in the secondary process
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 15:21:30
	 *
	 * @param poWPM
	 * @return
	 */
	public static boolean checkPhantasyActivate(clsWordPresentationMesh poWPM) {
		boolean bResult = false;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poWPM, ePredicate.HASPHANTASYFLAG);
		
		if (oWP!=null) {
			if(oWP.getMoContent().equals(eContent.TRUE.toString())==true) {
				bResult = true;
			}
		}
		
		
		return bResult;
	}
	
	/**
	 * Get the status of the phantasyflag in the primary process
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 15:31:10
	 *
	 * @param poTPM
	 * @return
	 */
	public static boolean checkPhantasyActivate(clsThingPresentationMesh poTPM) {
		boolean bResult = false;
		
		clsAssociation oAss = clsMeshTools.getUniqueTPAssociation(poTPM, eContentType.PHANTASYFLAG);
		if (oAss!=null & oAss instanceof clsAssociationAttribute) {
			if (((clsThingPresentation)oAss.getLeafElement()).getMoContent().equals(eContent.TRUE.toString())==true) {
				bResult = true;
			}
		}
		
		return bResult;
	}
	
	/**
	 * Check if a phantasyflag have been set in the secondary process
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 12:25:18
	 *
	 * @param poWPM
	 * @return true if it has been set for both the WPM and TPM, else false
	 */
	public static boolean checkIfPhantasyFlagExists(clsWordPresentationMesh poWPM) {
		boolean bResult = false;
		
		clsWordPresentation oWP = clsMeshTools.getUniquePredicateWP(poWPM, ePredicate.HASPHANTASYFLAG);
		clsAssociation oAss = clsMeshTools.getUniqueTPAssociation(clsMeshTools.getPrimaryDataStructureOfWPM(poWPM), eContentType.PHANTASYFLAG);
		
		if (oWP!=null && oAss !=null) {
			bResult=true;
		}
		
		return bResult;
	}
}
