/**
 * CHANGELOG
 *
 * 02.10.2013 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.ArrayList;

import memorymgmt.enums.eContent;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.10.2013, 21:28:39
 * 
 */
public class PrintTools {
    /**
     * Print content of an arraylist
     *
     * @author wendt
     * @since 28.11.2013 11:22:59
     *
     * @param images
     * @return
     */
    public static <E extends Object> String printArrayListWithLineBreaks(ArrayList<E> images) {
        String result ="";
        
        for (E i: images) {
            result += "\n   ";
            result += i.toString();
            
        }
        
        return result;
    }
    
    public static String printArrayListImageNamesWithLineBreaks(ArrayList<clsPair<clsThingPresentationMesh, Double>> images) {
        String result ="";
        
        for (clsPair<clsThingPresentationMesh, Double> i: images) {
            result += "\n   ";
            result += i.b + ", " + i.a.getContent();
            
        }
        
        return result;
    }
    
    /**
     * Create a string with the images in a mesh
     * 
     * (wendt)
     *
     * @since 04.03.2013 11:18:08
     *
     * @param poImage
     * @return
     */
    public static String printImagesInMesh(clsThingPresentationMesh poImage) {
        String oResult = "";
        ArrayList<clsThingPresentationMesh> oList = clsMeshTools.getAllTPMImages(poImage, 5);
        for (clsThingPresentationMesh oTPM : oList) {
            oResult += oTPM.getContent() + ", ";
        }
        
        return oResult;
    }
    
    public static String printActivatedMeshWithPIMatch(clsThingPresentationMesh poImage) {
        String oResult = "";
        ArrayList<clsThingPresentationMesh> oList = clsMeshTools.getAllTPMImages(poImage, 5);
        for (clsThingPresentationMesh tpm : oList) {
            oResult += tpm.getContent();
            double pimatch = clsActTools.getPrimaryMatchValueToPI(tpm);
            if (pimatch>0) {
                oResult += ", direct activation PI match: " + pimatch;
            } else if (tpm.getContent().equals(eContent.PI.toString())==true) {
                oResult += ", source";
            } else {
                oResult += ", -= indirect activation =-";
            }
            oResult += "\n";
        }
        
        return oResult;
        
    }
}
