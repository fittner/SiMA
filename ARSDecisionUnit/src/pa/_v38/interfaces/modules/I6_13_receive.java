/**
 * CHANGELOG
 *
 * 11.05.2013 hinterleitner - File created
 *
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (hinterleitner) - insert description 
 * 
 * @author hinterleitner
 * 11.05.2013, 15:00:03
 * 
 */

public interface I6_13_receive {
  
   public void receive_I6_13(clsWordPresentationMesh moWordingToContext_OUT2, clsWordPresentationMesh poPerception,
            ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);

/**
 * DOCUMENT - insert description
 *
 * @author hinterleitner
 * @since 27.12.2013 19:10:12
 *
 * @param poWording
 * @param poPerception
 * @param poAssociatedMemoriesSecondary
 */
void receive_I6_13(ArrayList<clsWordPresentationMesh> poWording, clsWordPresentationMesh poPerception,
        ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);


   
}
