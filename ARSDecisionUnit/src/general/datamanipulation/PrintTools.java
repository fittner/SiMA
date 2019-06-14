/**
 * CHANGELOG
 *
 * 02.10.2013 wendt - File created
 *
 */
package general.datamanipulation;

import primaryprocess.datamanipulation.*;

import java.util.ArrayList;

//import memorymgmt.enums.eContent;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
//import base.datatypes.helpstructures.clsTriple;
import inspector.interfaces.Singleton;
import memorymgmt.enums.eContent;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 02.10.2013, 21:28:39
 * 
 */
//delacruz 11.3.2019: extend clsPrimarySpatialTools for printing PI Match support
public class PrintTools extends clsPrimarySpatialTools{
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
    
    /**
     * Print content of an arraylist
     *
     * @author wendt
     * @since 28.11.2013 11:22:59
     *
     * @param images
     * @return
     */
    public static <E extends clsThingPresentationMesh> String printArrayListTPMContentWithLineBreaks(ArrayList<E> images) {
        String result ="";
        
        for (E i: images) {
            result += "\n   ";
            result += i.getContent();
            
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
    
    
    public static String printActivatedMeshWithPIMatchOriginal(clsThingPresentationMesh poImage) {
        String oResult = "";
        ArrayList<clsThingPresentationMesh> oList = clsMeshTools.getAllTPMImages(poImage, 5);
        for (clsThingPresentationMesh tpm : oList) {
            oResult += tpm.getContent();
            double pimatch = clsActTools.getPrimaryMatchValueToPI(tpm);
            if (pimatch>-1) {
                oResult += ", PI match: " + pimatch;
            } else if (tpm.getContent().equals(eContent.PI.toString())==true) {
                oResult += ", source";
            } else {
                oResult += ", no PI association";
            }
            oResult += "\n";
        }
        
        return oResult;
        
    }
    
    //delacruz: this function returns a string array with pi Match important factors
    //and final pi match result. 
    
   
   
    
    
    
    /*This function takes a Thing Presentation Mesh Image List and retrieves the PI Match
     * value from a TPM Array List of 5 Images. It retrieves the PI Match from function
     * getPrimaryMatchValueToPI from clsActTools class
     * Input: clsThingPresentationMesh
     * Output: string pi match  */
    public static String printActivatedMeshWithPIMatch(clsThingPresentationMesh poImage) {
        
        String oResult = "\n";
        //Singleton PIMatchInstance = Singleton.getInstance();
        //ArrayList<clsThingPresentationMesh> oList = clsMeshTools.getAllTPMImages(poImage, 5);
        //for (clsThingPresentationMesh tpm : oList) {
           // oResult += tpm.getContent();
            //double pimatch = clsActTools.getPrimaryMatchValueToPI(tpm);
            
           
            oResult += "Perceived Image: " + Singleton.getInstance().PIList.get(Singleton.getInstance().stepGlobalPIMatch-1) + "\n";
            oResult+= "Perceived Image Emotions: " + Singleton.getInstance().PIEmotionList.get(Singleton.getInstance().stepGlobalPIMatch-1) + "\n\n";
            
            /*clsEmotion PIEmotion = null;      
            for(int i=0;i<Singleton.PIList.size();i++) {
                
                
                for(clsAssociation AssEmotion: Singleton.PIList.get(i).getExternalAssociatedContent())
                {                
                    if(AssEmotion instanceof clsAssociationEmotion) {
                        PIEmotion = (clsEmotion) AssEmotion.getTheOtherElement(Singleton.PIList.get(Singleton.stepGlobalPIMatch-1));
                        
                    }                    
                       
                }
                
            }
            */
            
            //oResult+= "Perceived Image Emotions: " + PIEmotion + "\n";
            oResult += "Number of Images which will be compared: " + Singleton.getInstance().stepPIMatch + "\n\n";
           //for(Map<String, Map<String, Double>> entry : Singleton.getInstance().getList()) {
            
            for(int i=0;i<Singleton.getInstance().PIMatchList.size();i++) {
                
                oResult += "Received Image: " + Singleton.getInstance().RIList.get(i) + "\n";
            
                //for(int j=0;i<Singleton.PIMatchList.size();j++) {
                oResult += "\n";              
                oResult += "Image: " + (i+1) + "\n";
                //oResult += "Position array for the RI with the values of the PI, Object from RI, positionX from PI, positionY from PI, distance between them: \n";
                //oResult +=  Singleton.RIPIMatchList + "\n";
                //oResult +=  Singleton.PIMatchList.get(i);
                oResult +=  Singleton.getInstance().getPISpatialMatch(i) + "\n";
                oResult +=  Singleton.getInstance().getPIEmotionMatch(i) + "\n\n";
                
                //}
            }
        //}
        
        return oResult;
        
    }
}
