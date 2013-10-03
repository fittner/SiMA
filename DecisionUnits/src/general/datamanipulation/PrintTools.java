/**
 * CHANGELOG
 *
 * 02.10.2013 wendt - File created
 *
 */
package general.datamanipulation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.10.2013, 21:28:39
 * 
 */
public class PrintTools {
    public static String printReducedImageList(ArrayList<clsWordPresentationMesh> images) {
        String result ="";
        
        for (clsWordPresentationMesh i: images) {
            result += i.getMoContent();
            result += "; ";
        }
        
        return result;
    }
}
