/**
 * CHANGELOG
 *
 * 02.10.2013 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.10.2013, 21:28:39
 * 
 */
public class PrintTools {
    public static <E extends Object> String printArrayListWithLineBreaks(ArrayList<E> images) {
        String result ="";
        
        for (E i: images) {
            result += "\n   ";
            result += i.toString();
            
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
            oResult += oTPM.getMoContent() + ", ";
        }
        
        return oResult;
    }
}
