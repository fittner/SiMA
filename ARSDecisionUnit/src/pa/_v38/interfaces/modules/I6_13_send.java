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
 * 
 * 
 * @author deutsch
 * 07.05.2012, 14:42:23
 * 
 */
public interface I6_13_send {
  
    /**
     * DOCUMENT - insert description
     *
     * @author hinterleitner
     * @since 25.12.2013 16:03:00
     *
     * @param moWordingToContext_OUT2
     * @param poPerception
     * @param poAssociatedMemoriesSecondary
     */
   public void send_I6_13(clsWordPresentationMesh moWordingToContext_OUT2, clsWordPresentationMesh poPerception,
            ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary);
   
}
