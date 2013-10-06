/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.conversion;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsAssociationEmotion;
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 11:07:04
 * 
 */
public class DataStructureConversionTools {
    
    private static Logger log = clsLogger.getLog("SecondaryProcessAlgorithm");
    
    /**
     * Convert a TPM to a WPM, but only with one level
     *
     * @author wendt
     * @since 01.10.2013 11:08:34
     *
     * @param poTPM
     * @return
     */
    public static clsWordPresentationMesh convertCompleteTPMtoWPMRoot(itfModuleMemoryAccess ltm, clsThingPresentationMesh poTPM) {
        return convertCompleteTPMtoWPM(ltm, poTPM, new ArrayList<clsThingPresentationMesh>(), 1, poTPM.getMoContentType());
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
    public static clsWordPresentationMesh convertCompleteTPMtoWPM(itfModuleMemoryAccess ltm, clsThingPresentationMesh poTPM, ArrayList<clsThingPresentationMesh> poProcessedList, int pnLevel, eContentType contentType) {
        clsWordPresentationMesh oRetVal = null;

        // add the current TPM to the list
        poProcessedList.add(poTPM);

        // Get the WPM for the thing presentation itself
        clsAssociationWordPresentation oWPforObject = ltm.getSecondaryDataStructure(poTPM, 1.0);
        // Copy object
        if (oWPforObject != null) {
            try {
                if (oWPforObject.getLeafElement() instanceof clsWordPresentationMesh) {
                    oRetVal = (clsWordPresentationMesh) oWPforObject.getLeafElement();

                    // Add the external association as it is correctly assigned.
                    oRetVal.getExternalAssociatedContent().add(oWPforObject);
                } else {
                    throw new Exception("No clsWordPresentation is allowed to be associated here. The following type was recieved: " + oWPforObject.getLeafElement().toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // It may be the PI, then create a new image with for the PI or from
            // the repressed content
            oRetVal = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, poTPM.getMoContentType()), new ArrayList<clsAssociation>(), poTPM.getMoContent());
            clsAssociationWordPresentation oWPAss = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), oRetVal, poTPM);
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
            for (clsAssociation oTPMExternalAss : poTPM.getExternalMoAssociatedContent()) {

                // Case AssociationAttribute
                if (oTPMExternalAss instanceof clsAssociationAttribute) {
                    // Get the location templates
                    clsAssociationWordPresentation oWPforTPAttribute = ltm.getSecondaryDataStructure((clsPrimaryDataStructure) oTPMExternalAss.getLeafElement(), 1.0);
                    if (oWPforTPAttribute != null) {
                        clsWordPresentation oAttributeWP = null;
                        try {
                            oAttributeWP = (clsWordPresentation) oWPforTPAttribute
                                    .getLeafElement();
                        } catch (Exception e) {
                            log.error(oWPforTPAttribute.getLeafElement().toString(), e);
                            log.error(oWPforTPAttribute.getRootElement().toString(), e);
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
                                throw new Exception("Error in F21: getWPCompleteObjekt: A TP was found, which is neither Distance or Position");
                            } catch (Exception e) {
                                log.error("Position error", e);
                            }
                        }
                    }

                    // Case drivemesh
                } else if (oTPMExternalAss instanceof clsAssociationDriveMesh) {
                    // Get the affect templates
                    // Get the DriveMesh
                    clsDriveMesh oDM = (clsDriveMesh) oTPMExternalAss.getLeafElement();
                    //Get goal type
                    eGoalType goalType = eGoalType.MEMORYDRIVE;  
                    if (contentType.equals(eContentType.PI)) {
                        goalType = eGoalType.PERCEPTIONALDRIVE;
                    }
                    clsWordPresentationMeshSelectableGoal oDMWP = clsGoalManipulationTools.convertDriveMeshPerceptionToGoal(oDM, (clsWordPresentationMesh) oRetVal, goalType); //clsGoalTools.convertDriveMeshToWP(oDM);

                    // Create an association between the both structures and add
                    // the association to the external associationlist of the
                    // RetVal-Structure (WPM)
                    clsMeshTools.createAssociationSecondary(oRetVal, 2, oDMWP, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECT, false);
                } else if (oTPMExternalAss instanceof clsAssociationEmotion) {
                    clsEmotion oEmotion = (clsEmotion) oTPMExternalAss.getLeafElement();
                    clsWordPresentationMeshFeeling oFeeling = clsGoalManipulationTools.convertEmotionToFeeling(oEmotion);
                    
                    clsMeshTools.createAssociationSecondary(oRetVal, 2, oFeeling, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASFEELING, false);
                }
                
                
                // //Case association primary
                // } else if (oTPMExternalAss instanceof clsAssociationPrimary)
                // {
                // //In case of an association primary, only the strength of the
                // PI-Match is interesting
                //THIS IS DONE IN  PROCESSING MEMORIES
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
                if (oTPMInternalAss instanceof clsAssociationTime && poProcessedList.contains(((clsAssociationTime) oTPMInternalAss).getLeafElement()) == false) {

                    clsThingPresentationMesh oSubTPM = ((clsAssociationTime) oTPMInternalAss).getLeafElement();
                    // Convert the complete structure to a WPM
                    clsWordPresentationMesh oSubWPM = convertCompleteTPMtoWPM(ltm, oSubTPM, poProcessedList, pnLevel - 1, contentType);

                    // Add the subWPM to the WPM structure
                    clsMeshTools.createAssociationSecondary(oRetVal, 1, oSubWPM, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASPART, false);
                }
            }
        }
        
        //Convert the PIMatch to the secondary process WPM
        //double rPIMatchPri = clsActTools.getPrimaryMatchValueToPI(poTPM);
        //clsActTools.setPIMatchToWPM(oRetVal);

        return oRetVal;
    }
}