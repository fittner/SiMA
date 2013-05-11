/**
 * CHANGELOG
 *
 * 25.03.2013 hinterleitner - File created
 *
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * @author hinterleitner
 * 25.03.2013, 20:02:21
 * 
 */

public interface I6_13_send {
	

    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 11.05.2013 15:59:00
     *
     * @param poPerception
     * @param poAssociatedMemoriesSecondary
     */
    void send_I6_13(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
}
