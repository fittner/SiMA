/**
 * CHANGELOG
 *
 * 30.07.2013 herret - File created
 *
 */
package pa._v38.memorymgmt.datahandlertools;

import java.util.ArrayList;

import datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import system.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 30.07.2013, 09:20:18
 * 
 */
public class clsDataStructureCompareTools {
    /**
     * DOCUMENT Compare 2 images regarding datatype and contenttype. If the perceived objects of the "FromImage" are found in the "ToImage", then the 
     * associated data structures (datatype and contenttype) are assigned the found structures. Only a complete type match is allowed for object matching,
     * i. e. the MoDS_ID is compared. The purpose of the function is to find corresponding objects in the "ToImage", where e. g. drive meshes can be attached.
     * This is used in F45. 
     * 
     * TODO AW: At the moment, this function is only implemented for drive meshes.
     *
     * @since 16.07.2011 10:28:21
     *
     * @param poFromImage
     * @param poToImage
     * @param poDataType
     * @param poContentType
     * @return
     */
    public static ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>> getSpecificAssociatedContent(clsThingPresentationMesh poFromImage, clsThingPresentationMesh poToImage, eDataType poDataType, eContentType poContentType) {
        ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>> oRetVal = new ArrayList<clsPair<clsThingPresentationMesh, clsAssociation>>();
        clsPair<clsThingPresentationMesh, clsAssociation> oMatch = null;
        //Get the data structure, which could also have DMs
        //Only DM and TP can be copied
        
        if ((poFromImage != null) && (poToImage != null)) { //Catch the problem if the data structure would be null
            //Get Target structures
            
            //if ((poToImage.getMoDataStructure() instanceof clsTemplateImage)==false) {
            //  try {
            //      throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: Only data structures consisting of clsTemplateImage canbbe used.");
            //  } catch (Exception e) {
            //      e.printStackTrace();
            //  }
            //}
            //clsTemplateImage oToImageObject = (clsTemplateImage)poToImage.getMoDataStructure();
            //TODO AW: Only Template Images, which contain TPMs are concerned, expand to other data types and nested template images
            if (poDataType == eDataType.DM) {
                //Get all compare drive meshes
                ArrayList<clsAssociationDriveMesh> oFromImageDriveMeshes = clsMeshTools.getAllDMInMesh(poFromImage);
            
                //For each DM or TP in the associated structures in the SourceContainer
                for (clsAssociationDriveMesh oFromImageDM : oFromImageDriveMeshes) {    //The association in the source file. The root element shall be found in that target file
                
                    //if (oAssInFromImage instanceof clsAssociationDriveMesh) {
                    if (poContentType != null) {    //Add only that content type of that structure type
                        if (oFromImageDM.getLeafElement().getMoContentType() == poContentType) {
                            oMatch = getMatchInDataStructure(oFromImageDM, poToImage);
                        }
                    } else {    //Add all
                        oMatch = getMatchInDataStructure(oFromImageDM, poToImage);
                    }
                }
            
                /*} else if (poDataType == eDataType.TP) {
                    if (oAssInFromImage instanceof clsAssociationAttribute) {
                        if (poContentType != null) {    //Add only that content type of that structure type
                            if (oAssInFromImage.getLeafElement().getMoContentType().toString() == "poContentType") {
                                oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
                            }
                        } else {    //Add all
                            oMatch = getMatchInDataStructure(oAssInFromImage, oToImageObject);
                        }
                    }*/
                //} 
            } else {
                    try {
                        throw new Exception("Error in copySpecificAssociatedContent in F45_LibidoDischarge: A not allowed datatype was used");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                if (oMatch != null) {
                    oRetVal.add(oMatch);
                }
            }
        
        return oRetVal;
    }

    /**
     * The root element of the association is searched in all intrinsic (de: innewohnenede, definierende) data structures in the
     * target image and the match is returned as a pair of the data structure of the target image and the association itself.
     * 
     * This function is a part of "getSpecificAssociatedContent"
     *
     * @since 16.07.2011 10:31:28
     *
     * @param poSourceAssociation
     * @param poTargetDataStructure
     * @return
     */
    private static clsPair<clsThingPresentationMesh, clsAssociation> getMatchInDataStructure (clsAssociation poSourceAssociation, clsThingPresentationMesh poTargetDataStructure) {
        clsPair<clsThingPresentationMesh, clsAssociation> oRetVal = null;
        
        //Get root element
        clsThingPresentationMesh oCompareRootElement = (clsThingPresentationMesh) poSourceAssociation.getRootElement();
        //Find the root element in the target image. Only an exact match is count
        //1. Check if the root element is the same as the data structure in the target container
        if ((oCompareRootElement.getMoDS_ID() == poTargetDataStructure.getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
            oRetVal = new clsPair<clsThingPresentationMesh, clsAssociation>(poTargetDataStructure, poSourceAssociation);
        } else {
            //2. Check if the root element can be found in the associated data structures
            for (clsAssociation oAssToImage : poTargetDataStructure.getMoInternalAssociatedContent()) {
                if ((oCompareRootElement.getMoDS_ID() == oAssToImage.getLeafElement().getMoDS_ID() && (oCompareRootElement.getMoDS_ID() > 0))) {
                    oRetVal = new clsPair<clsThingPresentationMesh, clsAssociation>((clsThingPresentationMesh) oAssToImage.getLeafElement(), poSourceAssociation);
                    break;
                }
            }
        }
        
        return oRetVal;
    }
}
