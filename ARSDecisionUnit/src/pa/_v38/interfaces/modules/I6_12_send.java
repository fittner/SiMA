/**
 * I2_12.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:42:23
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * 
 * 
 * @author deutsch
 * 07.05.2012, 14:42:23
 * 
 */
public interface I6_12_send {
   
    /**
     * DOCUMENT - insert description
     *
     * @author hinterleitner
     * @since 26.12.2013 12:08:02
     *
     * @param poPerception
     * @param poAssociatedMemoriesSecondary
     * @param moWordingToContext2
     */
   public void send_I6_12(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary, clsWordPresentationMesh moWordingToContext2);
}
