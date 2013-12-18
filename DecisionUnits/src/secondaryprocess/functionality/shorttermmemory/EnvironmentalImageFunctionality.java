/**
 * CHANGELOG
 *
 * 01.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.shorttermmemory;

import base.datatypes.clsWordPresentationMesh;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2013, 21:11:31
 * 
 */
public class EnvironmentalImageFunctionality {
    /**
     * Update timesteps environmental memory
     *
     * @author wendt
     * @since 01.10.2013 12:06:42
     *
     * @param environmentalImageMemory
     */
    private static void updateTimeStepsEnvironmentalImageMemory(clsEnvironmentalImageMemory environmentalImageMemory) {
        environmentalImageMemory.updateTimeSteps();
    }
    
    /**
     * Add current perception to environmental storage. At the same time, the image is updated
     *
     * @author wendt
     * @since 01.10.2013 21:12:30
     *
     * @param environmentalImageMemory
     * @param perceivedImage
     */
    public static void addNewImageToEnvironmentalImage(clsEnvironmentalImageMemory environmentalImageMemory, clsWordPresentationMesh perceivedImage) {
        updateTimeStepsEnvironmentalImageMemory(environmentalImageMemory);
        
        environmentalImageMemory.addNewImage(perceivedImage);
    }
}
