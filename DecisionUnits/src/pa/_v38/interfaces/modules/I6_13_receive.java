/**
 * CHANGELOG
 *
 * 11.05.2013 hinterleitner - File created
 *
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWording;

/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * @author hinterleitner
 * 11.05.2013, 15:00:03
 * 
 */

public interface I6_13_receive {
  
    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 11.05.2013 15:01:37
     *
     * @param moWording
     */
    public void receive_I6_13(clsWording moWording);

    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 11.05.2013 15:59:32
     *
     * @param poPerception
     * @param poAssociatedMemoriesSecondary
     */
    public void receive_I6_13(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
}
