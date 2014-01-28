/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.conversion;

import java.util.ArrayList;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eGoalType;
import memorymgmt.enums.ePredicate;
import memorymgmt.interfaces.itfModuleMemoryAccess;

import org.slf4j.Logger;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationTime;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.helpstructures.clsTriple;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.datamanipulation.clsMeshTools;

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
        return convertCompleteTPMtoWPM(ltm, poTPM, new ArrayList<clsThingPresentationMesh>(), 1, poTPM.getContentType());
    }
    
    public static clsWordPresentationMesh convertCompleteTPMtoWPM(itfModuleMemoryAccess poLongTermMemory, clsThingPresentationMesh poTPM, ArrayList<clsThingPresentationMesh> poProcessedList, int pnLevel, eContentType poContentType) {
        return convertCompleteTPMtoWPM(poLongTermMemory, poTPM, new ArrayList<clsThingPresentationMesh>(), pnLevel, pnLevel, poTPM.getContentType());
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
    public static clsWordPresentationMesh convertCompleteTPMtoWPM(itfModuleMemoryAccess ltm, clsThingPresentationMesh poTPM, ArrayList<clsThingPresentationMesh> poProcessedList, int pnLevelInternal, int pnLevelExternal, eContentType contentType) {
        clsWordPresentationMesh oRetVal = null;

        // add the current TPM to the list
        poProcessedList.add(poTPM);

        // Get the WPM for the thing presentation itself
        //long start = System.currentTimeMillis();
        clsAssociationWordPresentation oWPforObject = ltm.getSecondaryDataStructure(poTPM, 1.0);
        //System.out.println((System.currentTimeMillis()- start));
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
                log.error("", e);
            }

        } else {
            // It may be the PI, then create a new image with for the PI or from
            // the repressed content
            oRetVal = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, poTPM.getContentType()), new ArrayList<clsAssociation>(), poTPM.getContent());
            clsAssociationWordPresentation oWPAss = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), oRetVal, poTPM);
            oRetVal.getExternalAssociatedContent().add(oWPAss);
        }

        // Go deeper, only if the pnLevel allows
        // If nothing was found, then the structure is null
        if (((pnLevelExternal >= 0) || (pnLevelExternal == -1)) && oRetVal != null) {
            // DOCUMENT AW: Internal sub structures are not considered here, as
            // only the word of the object is relevant
            // Search for the other external substructures of the WPM, i. e.
            // clsAttribute and clsDriveMesh
            // DOCUMENT Important note: clsAssociationPrimary is not considered
            // for the secondary process
            for (clsAssociation oTPMExternalAss : poTPM.getExternalAssociatedContent()) {

                // Case AssociationAttribute
                if (oTPMExternalAss instanceof clsAssociationAttribute) {
                    if(oTPMExternalAss.getAssociationElementB() instanceof clsThingPresentation){
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
    
                            if (oAttributeWP.getContentType() == eContentType.DISTANCE) {
                                clsMeshTools.createAssociationSecondary(oRetVal, 2,
                                        oAttributeWP, 0, 1.0,
                                        eContentType.POSITIONASSOCIATION,
                                        ePredicate.HASDISTANCE, false);
                            } else if (oAttributeWP.getContentType() == eContentType.POSITION) {
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
                    }
                    else if (oTPMExternalAss.getAssociationElementB() instanceof clsThingPresentationMesh){
                        //do Nothing
                    }
                    
                    
                    // Case drivemesh
                } 
                else if (oTPMExternalAss instanceof clsAssociationDriveMesh) {
                    // Get the affect templates
                    // Get the DriveMesh
                    clsDriveMesh oDM = (clsDriveMesh) oTPMExternalAss.getLeafElement();
                    //Get goal type
                    eGoalType goalType = eGoalType.MEMORYDRIVE;  
                    if (contentType.equals(eContentType.PI)) {
                        goalType = eGoalType.PERCEPTIONALDRIVE;
                    }
                    clsWordPresentationMeshPossibleGoal oDMWP = clsGoalManipulationTools.convertDriveMeshPerceptionToGoal(oDM, (clsWordPresentationMesh) oRetVal, goalType); //clsGoalTools.convertDriveMeshToWP(oDM);

                    // Create an association between the both structures and add
                    // the association to the external associationlist of the
                    // RetVal-Structure (WPM)
                    clsMeshTools.createAssociationSecondary(oRetVal, 2, oDMWP, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECT, false);
                } else if (oTPMExternalAss instanceof clsAssociationEmotion) {
                    clsEmotion oEmotion = (clsEmotion) oTPMExternalAss.getLeafElement();
                    clsWordPresentationMeshFeeling oFeeling = clsGoalManipulationTools.convertEmotionToFeeling(oEmotion);
                    
                    clsMeshTools.createAssociationSecondary(oRetVal, 2, oFeeling, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASFEELING, false);
                } else if(oTPMExternalAss instanceof clsAssociationPrimary) {
                	clsDataStructurePA oSubDataStructure = ((clsAssociationPrimary) oTPMExternalAss).getLeafElement();
                	
                	if(oSubDataStructure instanceof clsThingPresentationMesh) {
                	    clsThingPresentationMesh oSubTPM = (clsThingPresentationMesh)oSubDataStructure;
                	    
                        //Kollmann: currently the TPMs seem to store primary associations in both the root and the leaf of the association
                	    //          therefore we only consider associations where the current TPM is NOT the leaf
                	    if(!(oSubTPM.getDS_ID() == poTPM.getDS_ID())) {
                        	// Convert the complete structure to a WPM
                            clsWordPresentationMesh oSubWPM = convertCompleteTPMtoWPM(ltm, (clsThingPresentationMesh)oSubDataStructure, poProcessedList, pnLevelInternal, pnLevelExternal - 1, contentType);
                            
                            if(oSubDataStructure.getContentType() == eContentType.ACTION) {
                        	    clsMeshTools.createAssociationSecondary(oRetVal, 2, oSubWPM, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTION, false);
                        	    
                        	    //for now we associate the action to the self AND to the image containing the self, therefore we climb up the graph 'till we hit a remembered image
                        	    
                        	} else if(oSubDataStructure.getContentType() == eContentType.ENTITY) {
                        	    clsMeshTools.createAssociationSecondary(oRetVal, 2, oSubWPM, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASACTIONOBJECT, false);
                        	}
                	    }
                	}
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

        if (((pnLevelInternal > 0) || (pnLevelInternal == -1)) && oRetVal != null) {

            // Check the inner associations, if they are associationtime, as it
            // means that is an image
            for (clsAssociation oTPMInternalAss : poTPM
                    .getInternalAssociatedContent()) {
                // Internal TP-Associations are NOT checked, as they must not be
                // converted to WP
                // Only one internal level is converted, i. e. no images in
                // images are checked
                if (oTPMInternalAss instanceof clsAssociationTime && poProcessedList.contains(((clsAssociationTime) oTPMInternalAss).getLeafElement()) == false) {

                    clsThingPresentationMesh oSubTPM = ((clsAssociationTime) oTPMInternalAss).getLeafElement();
                    // Convert the complete structure to a WPM
                    clsWordPresentationMesh oSubWPM = convertCompleteTPMtoWPM(ltm, oSubTPM, poProcessedList, pnLevelInternal - 1, pnLevelExternal, contentType);

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
