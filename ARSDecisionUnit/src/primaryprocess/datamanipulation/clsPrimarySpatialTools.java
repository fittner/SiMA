/**
 * CHANGELOG
 *
 * 01.10.2011 wendt - File created
 *
 */
package primaryprocess.datamanipulation;

import java.util.ArrayList;
//import java.util.HashMap;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationSpatial;
import base.datatypes.clsAssociationTime;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import inspector.interfaces.Singleton;
//import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;
import testfunctions.clsTester;


/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2011, 09:16:51
 * 
 */
public class clsPrimarySpatialTools {
		
    //private static final Logger log = clsLogger.getLog("Tools");
    
	/**
	 * This function calculates the match between 2 images and returns the matching value. In this process also the RI (Remembered Image) is modified as 
	 * associations of the distance of the PI positions are added. 
	 * 
	 * 1 type of associations is added: Distance association where the association weight is extracted from the distance and the element a and b  
	 * (wendt)
	 *
	 * @since 07.11.2011 11:54:02
	 *
	 * @param poPI
	 * @param poRI
	 * @return
	 */
	
	

    public static double getImageMatch(clsThingPresentationMesh poPI, clsThingPresentationMesh poRI) {
        //Matching: All Objects in RI are searched for in PI, which means that RI is the more generalized image 
        double rRetVal = 0;
        double rEmotionImpactFactor = 0.5; // this factor defines how much influence the emotion match between PI and RI has on the total image match
        double rEmotionMatch = 0;
        double rTotalMatch = 0;
        
        //delacruz: create Singleton instance in order to set PI Match globally
        Singleton rRetValInstance = Singleton.getInstance();
        
        //delacruz: GetImageMatch logging  (see log4j_delacruz.properties)
        Logger datalogger = LogManager.getLogger("GetImageMatch");
       
        //datalogger.debug("Perceived Image: " + poPI + "Received Image : " + poRI);
        //Singleton.PI = poPI;        
     
        
        //Create position array for the PI. These positions can also be null, if the PI is a RI, which is somehow generalized, e. g. if memories are searched for in the LIBIDO discharge
        //Only references in the array
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oPIPositionArray = getImageEntityPositions(poPI);
        
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oPISortedPositionArray  = sortPositionArray(oPIPositionArray);
        
        //Create position array for the RI
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oRIPositionArray = getImageEntityPositions(poRI);
        
        //Sort the RI array for generalization, the least generalized first
        ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oRISortedPositionArray  = sortPositionArray(oRIPositionArray);
        
        //Create new modified position array for the RI with the values of the PI, Object from RI, positionX from PI, positionY from PI, distance between them
        //Compare the RI-Array with the PA-Array and search for the closest matches between them
        //In RI and in PI position elements with null are allowed to occur
            
        ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> oRIPIMatchList = findMatchingEntities(oPISortedPositionArray, oRISortedPositionArray);
        
        //delacruz: GetImageMatch logging (see log4j_delacruz.properties)
        datalogger.debug("Position array for the RI with the values of the PI, Object from RI, positionX from PI, positionY from PI, distance between them");
        datalogger.debug("Compare the RI-Array with the PA-Array and search for the closest matches between them: ");
        datalogger.debug(oRIPIMatchList);
       
        
        //delacruz: assign PIRIMatchList to singleton variable oRIPIMatch
        
        if(oRIPIMatchList.size() > 0)
        {
            try {
                   rRetValInstance.setRIPIMatch(oRIPIMatchList);
            }
            catch(Exception e){
                   datalogger.debug("Error setting RIPIMatch to RIPIMatchList" + e + "\n");
            }                
            try {                
                rRetValInstance.addToRIPIMatchList();
               
            }
            catch(Exception e) {
                datalogger.debug("Error while adding RIPIMatch into RIPIMatchList" + e + "\n");
            }
           
           // Singleton.oRIPIMatchList.clone();
            
        }
        
        //add Received Image to Singleton global variable RIList
        try {
               
               datalogger.debug("RI Image : " + Singleton.getInstance().RIList.get(Singleton.getInstance().stepPIMatch));
        }
        catch(Exception e){
              datalogger.error("Error while adding RI in RIList: " + e);
        }
        
 

        //Add matching associations to the objects in the RI
        //Add distanceassociations
        
        //addRIAssociations(oRIPIMatchList);
        
        // Action and intention images which you get from another agent 
        
        if(poRI.getContentType() == eContentType.RPI || poRI.getContentType() == eContentType.RPA)  { 
            ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oPIActionsArray = getImageEntityActions(poPI);
            ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oRIActionsArray = getImageEntityActions(poRI);
               
            //ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oPISpatialArray = getImageEntitySpatial(poPI);
            ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oRISpatialArray = getImageEntitySpatial(poRI);        
            //ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oRISpatialArray = getImageEntitySpatial(poRI);
            if(!oRISpatialArray.isEmpty() || !oRIActionsArray.isEmpty()) {
                double rRetValSp = calculateImageMatchSpatial(poPI, poRI);
                datalogger.debug("Spatial Match " + rRetValSp);
                ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> oRIPIMatchActionList = findMatchingActionOrSpatial(oRIActionsArray, oPIActionsArray);  
                double rRetValAc =  calculateImageMatchAction(oRIPIMatchActionList);
                if(!oRIPIMatchActionList.isEmpty() || !oRISpatialArray.isEmpty()) { 
                    rRetVal = (rRetValSp + rRetValAc)/(oRIPIMatchActionList.size() + oRISpatialArray.size()/2);
                    //delacruz: add Spatial Match to singleton 
                    //rRetValInstance.addToPIMatchList();
                    rRetValInstance.setSpatialMatch(rRetVal);
                    datalogger.debug("Spatial Match " + rRetVal);
                   
                }
                else { rRetVal = 0; }
            }
            else { 
                rRetVal = 0;
            } 
            // ArrayList<clsPair<clsPair<clsThingPresentationMesh,clsThingPresentationMesh>, Double>> oRIPIMatchSpatialContentList = findMatchingActionOrSpatial(oRISpatialArray, oPISpatialArray);  
        }
        else {
            if(oRIPositionArray.isEmpty()) {
                rRetVal = 1;
            } else {
                //Calculate the spatial image match              
                rRetVal = calculateImageMatch(oRIPIMatchList, oRISortedPositionArray);
              //delacruz: add Spatial Match to singleton
                //if(Singleton.stepPIMatch == 0 )
                //{
                   // rRetValInstance.addToPIMatchList();
                //}
                  try {
                      //rRetValInstance.addToPIMatchList();
                      rRetValInstance.setSpatialMatch(rRetVal);
                      
                  }                
                 catch(Exception e){
                     datalogger.debug("Error while setting Spatial Match " + e + "\n");
                 }
                //delacruz logger
               // datalogger.info("Spatial Match: " + rRetValInstance.PIMatchList);
                
                
            }
        }
        //=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            try {
                clsTester.getTester().exeTestAssociationAssignment(poRI);
            } catch (Exception e) {
                //log.error("Systemtester has an error in addRIAssociations(oRIPIMatchList), poRI", e);
            }
        }
        
        //if the RI contains no entities at all, set the match value to 1, otherwise calculate the match

        clsEmotion oEmotionPI = null,  oEmotionRI = null; 
        //calculate emotion match of images
        //get emotions from first image
        if(poRI.getContentType() != eContentType.RPI) { 
            oEmotionPI = clsEmotion.fromTPM(poPI);
        
        //get emotions from second image
            oEmotionRI = clsEmotion.fromTPM(poRI);
            
        }
        else {
            oEmotionPI =  getEmotionFromAnotherEntity(poPI);
            oEmotionRI = getRecognizedEmotionFromMemorizedImage(poRI);
        }
        //get match value for the two emotions
        if(oEmotionPI != null && oEmotionRI != null) {
            /* Note: delacruz: in order to map the PI EmotionMatch with Aggresivity, Libido, Pleasure and Unpleasure,
             * addition of variables to singleton is done within the inner method called by compareTo: "matchingFunction" 
             */
            rEmotionMatch = oEmotionPI.compareTo(oEmotionRI);
            //delacruz logger
            datalogger.debug("Emotion Match: " + rEmotionMatch);
        }
        //delacruz logger
        datalogger.debug("Emotion Impact Factor: " + rEmotionImpactFactor);
        rTotalMatch = (rRetVal * (1 - rEmotionImpactFactor)) + (rEmotionMatch * rEmotionImpactFactor);
        datalogger.debug("Total Match: " + rTotalMatch);
        
        //delacruz logger
        datalogger.debug("Total Match List after " + Singleton.getInstance().stepGlobalPIMatch + " steps: " + Singleton.getInstance().PIMatchList);
        return rTotalMatch;
       
    }
    
    

    protected static clsEmotion getRecognizedEmotionFromMemorizedImage(clsThingPresentationMesh poImage) {
        for(clsAssociation oAss : poImage.getExternalAssociatedContent()) { 
            if(oAss instanceof clsAssociationEmotion) {
                if(((clsEmotion)oAss.getAssociationElementA()).getContentType().equals(eContentType.BASICEMOTION))
                    return (clsEmotion)oAss.getAssociationElementA();
            }
        }
        return null;
    }
    
    //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
	protected static clsEmotion getEmotionFromAnotherEntity(clsThingPresentationMesh poImage) { 
	    clsThingPresentationMesh oEntity = getAliveEntity(poImage);
	    if(oEntity != null) {
    	    for(clsAssociation oAss : oEntity.getExternalAssociatedContent()) { 
    	        if(oAss instanceof clsAssociationEmotion) { 
    	            return (clsEmotion)oAss.getAssociationElementA();
    	        }
    	    }
	    }
	    
	    return null;
	}


	private static clsThingPresentationMesh getAliveEntity(clsThingPresentationMesh poImage) {
	    
	    for(clsAssociation oAss : poImage.getInternalAssociatedContent()) { 
	        clsThingPresentationMesh oElement = (clsThingPresentationMesh)oAss.getAssociationElementB();
	        if(oElement.getContent().equals("SELF") || oElement.getContent().equals("EMPTYSPACE")) {
	            continue;
	        }
	        
	        if(!isAlive(oElement)) {
	            continue;
	        } else {
	           return oElement;
	        }
	    }
	    return null;  
	}

	private static boolean isAlive(clsThingPresentationMesh poTPM)  {
	    for(clsAssociation oIntAss : poTPM.getInternalAssociatedContent()) { 
	        if(oIntAss.getAssociationElementB().getContentType().toString().equals("Alive")) {
	                clsThingPresentation associatedProperty = (clsThingPresentation)oIntAss.getAssociationElementB();
	                return Boolean.parseBoolean(associatedProperty.getContent().toString());          
	          }
	        }
	        return false;
	    }
	
    /**
     * DOCUMENT - insert description
     *
     * @author zhukova
     * @since Sep 28, 2015 5:06:06 PM
     *
     * @param oRIActionsArray
     * @param oPIActionsArray
     * @return
     */
	//delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
    protected static ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> findMatchingActionOrSpatial(
            ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oRIActionsArray,
            ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> oPIActionsArray) {
        
        ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> oMatchArray = new ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>>();
        
        String RIAction = null, RIObject = null, PIAction = null, PIObject  = "";
        boolean bMatch = false;
        for(clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oTPMPairRI : oRIActionsArray) {
            //if(oTPMPairRI.a.getContent().equals("SELF") || oTPMPairRI.b.getContent().equals("SELF")) { 
            //    return oMatchArray; 
            //}
        }
        for(clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oTPMPairRI : oRIActionsArray) {
                if(oTPMPairRI.a.getContent().equals("SELF") || oTPMPairRI.b.getContent().equals("SELF")) { 
                    continue; 
                }
                if(oTPMPairRI.a.getContentType().equals(eContentType.ENTITY)) { 
                    RIObject  = oTPMPairRI.a.getContent();
                    RIAction = oTPMPairRI.b.getContent();
                }
                else {
                    RIObject  = oTPMPairRI.b.getContent();
                    RIAction = oTPMPairRI.a.getContent();
                }
                
                for(clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oTPMPairPI : oPIActionsArray) {
                    if(oTPMPairPI.a.getContent().equals("SELF") || oTPMPairPI.b.getContent().equals("SELF")) { continue; } 
                    if(oTPMPairPI.a.getContentType().equals(eContentType.ENTITY)) { 
                        PIObject  = oTPMPairPI.a.getContent();
                        PIAction = oTPMPairPI.b.getContent();
                    }
                    else {
                        PIObject  = oTPMPairPI.b.getContent();
                        PIAction = oTPMPairPI.a.getContent();
                    }
                    
                    if(RIObject.equals(PIObject) && PIAction.equals(RIAction)) {
                        bMatch = true;
                    }
                        
                }
                if(bMatch) {
                    oMatchArray.add(new clsPair(oTPMPairRI, 1.0));
                }
                else {
                    oMatchArray.add(new clsPair(oTPMPairRI, 0.0));
                }
        }
        
        // TODO (zhukova) - Auto-generated method stub
        return oMatchArray;
    }


    /**
     * DOCUMENT - insert description
     *
     * @author zhukova
     * @since Sep 28, 2015 2:21:56 PM
     *
     * @param poPI
     * @return
     */
    
  //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
    protected static ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> getImageEntitySpatial(clsThingPresentationMesh moImage) {
        ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> associatedSpatialContent = new ArrayList<>();        
        if(moImage.getContent().equals("PI")){
               ArrayList<clsAssociation> internalAssociatedContent = new ArrayList<clsAssociation>();
               for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                   clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                   if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                   ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                   for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                      if(oAssExtTMP instanceof clsAssociationSpatial) { 
                          clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oSpatialContent = new clsPair(oAssExtTMP.getAssociationElementA(), oAssExtTMP.getAssociationElementB());
                          associatedSpatialContent.add(oSpatialContent );
                       }
                   }
               }
           }
           else  {
               ArrayList<clsAssociation> externalAssociatedContent = new ArrayList<clsAssociation>();
               for(clsAssociation oAssExt : moImage.getExternalAssociatedContent()) {
                   if(oAssExt instanceof clsAssociationSpatial) { 
                       clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oSpatialContent = new clsPair(oAssExt.getAssociationElementA(), oAssExt.getAssociationElementB());
                       associatedSpatialContent.add(oSpatialContent);
                   }
               }
               
           }
        return associatedSpatialContent;
    }


    /**
     * DOCUMENT - insert description
     *
     * @author zhukova
     * @since Sep 24, 2015 4:10:12 PM
     *
     * @param poPI
     * @return
     */
  //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
    protected static ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> getImageEntityActions(clsThingPresentationMesh moImage) {
        ArrayList<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>> associatedActions = new ArrayList<>();
        
        if(moImage.getContent().equals("PI")){
            for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                   if(oAssExtTMP instanceof clsAssociationPrimary) { 
                       clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oAction = new clsPair(oAssExtTMP.getAssociationElementA(), oAssExtTMP.getAssociationElementB());
                       associatedActions.add(oAction);
                    }
                }
            }
        }
        else  {
            for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                if(oAssExt.getAssociationElementB() instanceof clsThingPresentationMesh) { 
                    for(clsAssociation oAssExt2 : ((clsThingPresentationMesh)(oAssExt.getAssociationElementB())).getExternalAssociatedContent()) { 
                        if(oAssExt2 instanceof clsAssociationPrimary && !((clsThingPresentationMesh)(oAssExt2.getAssociationElementA())).getContent().equals("SELF")) { 
                          // !((clsThingPresentationMesh)(oAssExt2.getAssociationElementA())).getContent().equals("GOTO")) { 
                            clsPair<clsThingPresentationMesh, clsThingPresentationMesh> oAction = new clsPair(oAssExt2.getAssociationElementA(), oAssExt2.getAssociationElementB());
                            associatedActions.add(oAction);
                        }
                    }
                }
            }    
        }
        return associatedActions;
    }



	
	
    private static ArrayList<clsAssociation> getSpatialAssociations(clsThingPresentationMesh moImage) {
        ArrayList<clsAssociation> oSpatialAssociations = new ArrayList<clsAssociation>();
         if(moImage.getContent().equals("PI")){
                ArrayList<clsAssociation> internalAssociatedContent = new ArrayList<clsAssociation>();
                for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                    clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                    if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                    ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                    for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                       if(oAssExtTMP instanceof clsAssociationSpatial) { 
                           oSpatialAssociations.add(oAssExtTMP);
                        }
                    }
                }
            }
            else  {
                ArrayList<clsAssociation> externalAssociatedContent = new ArrayList<clsAssociation>();
                for(clsAssociation oAssExt : moImage.getExternalAssociatedContent()) {
                    if(oAssExt instanceof clsAssociationSpatial) { 
                        oSpatialAssociations.add(oAssExt);
                    }
                }
                
            }
         return oSpatialAssociations;
     }
	
    
    
    private static ArrayList<clsAssociation> getPrimaryAssociations(clsThingPresentationMesh moImage) {
        ArrayList<clsAssociation> oPrimaryAssociations = new ArrayList<clsAssociation>();
         if(moImage.getContent().equals("PI")){
                for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                    clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                    if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                    ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                    for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                       if(oAssExtTMP instanceof clsAssociationPrimary) { 
                           oPrimaryAssociations.add(oAssExtTMP);
                        }
                    }
                }
            }
            else  {
                for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                    if(oAssExt.getAssociationElementB() instanceof clsThingPresentationMesh) { 
                        for(clsAssociation oAssExt2 : ((clsThingPresentationMesh)(oAssExt.getAssociationElementB())).getExternalAssociatedContent()) { 
                            if(oAssExt2 instanceof clsAssociationPrimary && !((clsThingPresentationMesh)(oAssExt2.getAssociationElementA())).getContent().equals("SELF")) { 
                                oPrimaryAssociations.add(oAssExt2);
                            }
                        }
                    }
                }
                
            }
         return oPrimaryAssociations;
     }
  //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
    private static double calculateImageMatchSpatial(clsThingPresentationMesh moPI, clsThingPresentationMesh moRI) { 
        ArrayList<clsAssociation> oSpatialAssociationsmoPI = getSpatialAssociations(moPI);
        ArrayList<clsAssociation> oSpatialAssociationsmoRI = getSpatialAssociations(moRI);
        int numberOfMatchingAssociation = 0;
        
        for(clsAssociation oAssRI : oSpatialAssociationsmoRI) {
            String oAssociatedElementRIA = ((clsThingPresentationMesh)oAssRI.getAssociationElementB()).getContent();
            String oAssociatedElementRIB = ((clsThingPresentationMesh)oAssRI.getAssociationElementA()).getContent();

            for(clsAssociation oAssPI :oSpatialAssociationsmoPI) {
                String oAssociatedElementPIA = ((clsThingPresentationMesh)oAssPI.getAssociationElementA()).getContent();
                String oAssociatedElementPIB = ((clsThingPresentationMesh)oAssPI.getAssociationElementB()).getContent();
                if(oAssociatedElementRIA.equals(oAssociatedElementPIA) || oAssociatedElementRIA.equals(oAssociatedElementPIB) ||
                   oAssociatedElementRIB.equals(oAssociatedElementPIA) || oAssociatedElementRIB.equals(oAssociatedElementPIB)) { 
                        numberOfMatchingAssociation += 1;
                        break;
                }
            }
        }
        if(oSpatialAssociationsmoRI.size()!=0) { 
            return numberOfMatchingAssociation/2;
        }       
        else {  return 0; } 
    }
  //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
   private static double calculateImageMatchAction(ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> poActionMatch) {
       double mrMatch = 0;
       for(clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double> pair :   poActionMatch) {
           mrMatch += pair.b;
       }
       
       return mrMatch ;
   }
    
    
    private static double calculateImageMatchAction(clsThingPresentationMesh moPI, clsThingPresentationMesh moRI) {
        ArrayList<clsAssociation> oActionAssociationsmoPI = getPrimaryAssociations(moPI);
        ArrayList<clsAssociation> oActionAssociationsmoRI = getPrimaryAssociations(moRI);
        String moObject = "",  moAction = "";
        for(clsAssociation oAss : oActionAssociationsmoRI) {
            if(oAss.getAssociationElementA().getContentType().equals(eContentType.ENTITY)){
                moObject = ((clsThingPresentationMesh)(oAss.getAssociationElementA())).getContent();
            }
            if(oAss.getAssociationElementB().getContentType().equals((eContentType.ACTION))) { 
                moAction = ((clsThingPresentationMesh)(oAss.getAssociationElementB())).getContent();
            }
        }
        
        for(clsAssociation oAss : oActionAssociationsmoPI) { 
            clsThingPresentationMesh oTPM1 =  (clsThingPresentationMesh)oAss.getAssociationElementA();
            clsThingPresentationMesh oTPM2 =  (clsThingPresentationMesh)oAss.getAssociationElementB();
            if(oTPM1.getContentType().equals(eContentType.ENTITY) && oTPM1.getContent().contains(moObject) &&
                    oTPM2.getContentType().equals(eContentType.ACTION) && oTPM2.getContent().contains(moAction))
                return 1;
        }

        
        
        return 0;
        
    }
//	/**
//	 * Get the position coordinates in X, Y intergers from thing presentations of a data structure in a container
//	 * (wendt)
//	 *
//	 * @since 01.10.2011 09:50:49
//	 *
//	 * @param poDS
//	 * @param poImageContainer
//	 * @return
//	 */
//	public static clsTriple<clsDataStructurePA, eXPosition, eYPosition> getPosition(clsDataStructurePA poDS, clsPrimaryDataStructureContainer poImageContainer) {
//		clsTriple<clsDataStructurePA, eXPosition, eYPosition> oRetVal = null;
//		
//		//Search for xy compontents
//		eXPosition X = null;	//default error value
//		eYPosition Y = null;
//		ArrayList<clsAssociation> oDSAssList = poImageContainer.getMoAssociatedDataStructures(poDS);
//		for (clsAssociation oAss : oDSAssList) {
//			if (oAss instanceof clsAssociationAttribute && oAss.getLeafElement().getMoContentType().equals("LOCATION")) {
//				//Get content of the association
//				String oContent = (String) ((clsThingPresentation)oAss.getLeafElement()).getMoContent();
//				//Get the X-Part
//				if (X==null) {
//					X = eXPosition.elementAt(oContent);
//				}
//				
//				if (Y==null) {
//					Y = eYPosition.elementAt(oContent);
//				}
//			}
//		}
//		
//		oRetVal = new clsTriple<clsDataStructurePA, eXPosition, eYPosition>(poDS, X, Y);
//				
//		return oRetVal;
//	}
	
	/**
	 * Get the position coordinates in X, Y integers from thing presentations of a data structure
	 * (wendt)
	 *
	 * @since 01.10.2011 09:50:49
	 *
	 * @param poDS
	 * @param poImageContainer
	 * @return
	 */
	public static clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> getPosition(clsThingPresentationMesh poDS) {
		clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oRetVal = null;
		
		//Search for xy compontents
		ePhiPosition X = null;	//default error value
		eRadius Y = null;
		
		
		//ArrayList<clsAssociation> oDSAssList = poImageContainer.getMoAssociatedDataStructures(poDS);
		for (clsAssociation oAss : poDS.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationAttribute) {
				if (oAss.getLeafElement().getContentType().equals(eContentType.DISTANCE)) {
					//Get content of the association
					String oContent = (String) ((clsThingPresentation)oAss.getLeafElement()).getContent();
					if (Y==null) {
						Y = eRadius.elementAt(oContent);
					}
					//Special case if EATABLE is used
					//FIXME AW: EATABLE is center
					if (((clsThingPresentation)oAss.getLeafElement()).getContent().equals("EATABLE")==true) {
						if (X==null) {
							X = ePhiPosition.CENTER;
						}
					}
					
				} else if (oAss.getLeafElement().getContentType().equals(eContentType.POSITION)) {
					String oContent = (String) ((clsThingPresentation)oAss.getLeafElement()).getContent();
					//Get the X-Part
					if (X==null) {
						X = ePhiPosition.elementAt(oContent);
					}
				}
			}
		
		}
		
		oRetVal = new clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>(poDS, X, Y);
				
		return oRetVal;
	}
	
	/**
	 * For an image, get an arraylist with all positions of the objects in the image
	 * (wendt)
	 *
	 * @since 03.11.2011 22:42:31
	 *
	 * @param poImageContainer
	 * @return
	 */
	public static ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> getImageEntityPositions(clsThingPresentationMesh poImage) {
		ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oRetVal = new ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>>();
		
		//Get positions for all objects in the image		
		for (clsAssociation oAss : poImage.getInternalAssociatedContent()) {
			//Get the leaf elements
			clsDataStructurePA oImageObject = oAss.getLeafElement();
			//Get the position
			if (oImageObject instanceof clsThingPresentationMesh) {
				clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oObjectPosition = getPosition((clsThingPresentationMesh) oImageObject);
				//Throw exception if some position is NULL
				//if (oObjectPosition.b==null || oObjectPosition.c==null) {
				//	try {
				//		throw new Exception("clsSpatialTools, getImagePositions: Object position = null. Original object: " + oImageObject.toString() + ". Calculated object: " + oObjectPosition.toString());
				//	} catch (Exception e) {
				//		// TODO (wendt) - Auto-generated catch block
				//		e.printStackTrace();
				//	}
				//}
				oRetVal.add(oObjectPosition);
			}
		}
		
		return oRetVal;
	}
	// distinguish between spatial and temporable image 
	public static boolean isSpatialImage(clsThingPresentationMesh moImage) {
	    //return getSpatialAssociations(moImage) != null;
	    
	    if(moImage.getContent().equals("PI")){
            for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                   if(oAssExtTMP instanceof clsAssociationSpatial) { 
                        return true;
                    }
                }
            }
            return false;
        }
	    else  {
	        ArrayList<clsAssociation> externalAssociatedContent = new ArrayList<clsAssociation>();
	        for(clsAssociation oAssExt : moImage.getExternalAssociatedContent()) {
	            if(oAssExt instanceof clsAssociationSpatial) { 
	                return true;
	            }
	        }
	        return false;
	    }
	}
	
//	/**
//	 * For an image, get an arraylist with all positions of the objects in the image
//	 * (wendt)
//	 *
//	 * @since 03.11.2011 22:42:31
//	 *
//	 * @param poImageContainer
//	 * @return
//	 */
//	public static ArrayList<clsTriple<clsThingPresentationMesh, eXPosition, eYPosition>> getImageObjectPositions(clsPrimaryDataStructureContainer poImageContainer) {
//		ArrayList<clsTriple<clsThingPresentationMesh, eXPosition, eYPosition>> oRetVal = new ArrayList<clsTriple<clsThingPresentationMesh, eXPosition, eYPosition>>();
//		
//		//Get positions for all objects in the image
//		if (poImageContainer.getMoDataStructure() instanceof clsTemplateImage) {
//			//Get Image
//			clsTemplateImage oImageStripped = (clsTemplateImage) poImageContainer.getMoDataStructure();
//			for (clsAssociation oAss : oImageStripped.getMoAssociatedContent()) {
//				//Get the leaf elements
//				clsDataStructurePA oImageObject = oAss.getLeafElement();
//				//Get the position
//				clsTriple<clsDataStructurePA, eXPosition, eYPosition> oObjectPosition = getPosition(oImageObject, poImageContainer);
//
//				//Add position the results
//				//if (oObjectPosition.a.getMoContentType()!="BUMP" || oObjectPosition.b!=null || oObjectPosition.c!=null) {
//				//	if ((oObjectPosition.b==null||oObjectPosition.c==null) && (((clsTemplateImage)poImageContainer.getMoDataStructure()).getMoContent()=="PERCEPTION")) {
//				//		try {
//				//			throw new Exception("Error: oObjectPosition=null: " + oObjectPosition.toString());
//				//		} catch (Exception e) {
//				//			// TODO (wendt) - Auto-generated catch block
//				//			e.printStackTrace();
//				//		}
//				//	}
//				oRetVal.add(oObjectPosition);
//				//}
//
//			}
//		}
//		
//		return oRetVal;
//	}
	/*
	private static double calculateImageMatch(ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> poRIPIMatchList, ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> poRIPositionList,
	                                           ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> poRIPIActionArray,
	                                           ArrayList<clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double>> oRIPIMatchSpatialContentList) 
	{
		double rRetVal = 0;
		
		//Get the number of elements in the RI position list
		double rNormalizefactor = 1;
		if (poRIPositionList.size() > 0) {
			rNormalizefactor = poRIPositionList.size();
		}
	    if(!poRIPIActionArray.isEmpty()) {
	        rNormalizefactor += poRIPIActionArray.size();
		}
	      if(!oRIPIMatchSpatialContentList.isEmpty()) {
	            rNormalizefactor += oRIPIMatchSpatialContentList.size();
	        }
		//Get the sum of distance associations in the RIPI Match list
		double rWeightSum = 0;
        for (clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>> oRIPIMatch : poRIPIMatchList) {
            rWeightSum += calculateAssociationWeightFromDistance(oRIPIMatch.b.b);
        }
		
        for (clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double> oRIPIMatch : poRIPIActionArray) {
            rWeightSum += oRIPIMatch.b;
        }
        
        for (clsPair<clsPair<clsThingPresentationMesh, clsThingPresentationMesh>, Double> oRIPIMatch : oRIPIMatchSpatialContentList) {
            rWeightSum += oRIPIMatch.b;
        }
		rRetVal = rWeightSum/rNormalizefactor;
		
		return rRetVal;
	} */
	   //delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
	   protected static double calculateImageMatch(ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> poRIPIMatchList, ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> poRIPositionList) {
	        double rRetVal = 0;
	        
	        //Get the number of elements in the RI position list
	        double rNormalizefactor = 1;
	        if (poRIPositionList.size()>0) {
	            rNormalizefactor = poRIPositionList.size();
	        }
	        
	        //Get the sum of distance associations in the RIPI Match list
	        double rWeightSum = 0;
	        for (clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>> oRIPIMatch : poRIPIMatchList) {
	            rWeightSum = rWeightSum + calculateAssociationWeightFromDistance(oRIPIMatch.b.b);
	        }
	        
	        rRetVal = rWeightSum/rNormalizefactor;
	        
	        return rRetVal;
	    }
	
	/**
	 * With a data structure of the match between PI and RI, convert this data structure to an association, which is added to the RI container. With this function, each match is added as an
	 * associationTime to an object. 
	 * (wendt)
	 *
	 * @since 07.11.2011 12:25:54
	 *
	 * @param popoRIPIMatchList=The first element is the PI Entity with positions and the second element is the element of the RI with the match
	 * @param poRI
	 */
	private static void addRIAssociations(ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> poRIPIMatchList) {
		//For each found object, create an association
		for (clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>> oRIPIMatch : poRIPIMatchList) {
			//Create an association, The root object is the RI and the leafobject is the PI
			clsAssociationTime oMatchAssociation = createDistanceAssociation(oRIPIMatch.b.a, oRIPIMatch.a.a, oRIPIMatch.b.b);
			//Add the association to the container
			oRIPIMatch.b.a.getExternalAssociatedContent().add(oMatchAssociation);
			
			//Find that object in the RI
			//for (clsAssociation oAss : poRI.getMoAssociatedContent()) {
			//	if (oAss.getLeafElement().getMoDSInstance_ID()==oRIPIMatch.b.a.getMoDSInstance_ID()) {
			//		poRI.getExternalMoAssociatedContent().add(oMatchAssociation);
			//	}
			//}
			
		}
	}
	
//	/**
//	 * With a data structure of the match between PI and RI, convert this data structure to an association, which is added to the RI container. With this function, each match is added as an
//	 * associationTime to an object
//	 * (wendt)
//	 *
//	 * @since 07.11.2011 12:25:54
//	 *
//	 * @param popoRIPIMatchList
//	 * @param poRI
//	 */
//	private static void addRIAssociations(ArrayList<clsPair<clsTriple<clsPrimaryDataStructureContainer, eXPosition, eYPosition>, clsPair<clsPrimaryDataStructureContainer, Double>>> poRIPIMatchList, clsPrimaryDataStructureContainer poRI) {
//		//For each found object, create an association
//		for (clsPair<clsTriple<clsPrimaryDataStructureContainer, eXPosition, eYPosition>, clsPair<clsPrimaryDataStructureContainer, Double>> oRIPIMatch : poRIPIMatchList) {
//			//Create an association, The root object is the RI and the leafobject is the PI
//			clsAssociationTime oMatchAssociation = createDistanceAssociation((clsPrimaryDataStructureContainer)oRIPIMatch.b.a, (clsPrimaryDataStructureContainer)oRIPIMatch.a.a, oRIPIMatch.b.b);
//			//Add the association to the container
//			poRI.getExternalMoAssociatedContent().add(oMatchAssociation);
//		}
//	}
	
	/**
	 * Sort the positions in the array concerning generalization. Sort order: More accurate first and generalized at last
	 * (wendt)
	 *
	 * @since 07.11.2011 08:27:05
	 *
	 * @param oPositionArray
	 */
	//delacruz 11.3.2018: change visibility of method to protected for usability in PrintTools.java
	protected static ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> sortPositionArray(ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oPositionArray) {
		ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oNewArray = new ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>>();
		
		for (clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPos : oPositionArray) {
			//Set score: 3: null, null; 2: null, X; 1: X,X
			
			//Calculate score for the current position
			int nCompareScore = getPositionArrayScore(oPos);
			
			//Sort list for position
            int i=0;     
            while (oNewArray.isEmpty()==false && i<oNewArray.size()) {                 
            	//Get the score for 
            	
            	//Calculate score for the next image
            	int nCurrentScore = getPositionArrayScore(oNewArray.get(i));
    			if (nCompareScore<=nCurrentScore) {
    				break;
    			} else {
                	i++;
    			}
            }
            oNewArray.add(i, oPos);
		}
		
		return oNewArray;
	}
	
	/**
	 * Evaluate the generalization score of the positions. If a position is (x, null), then it is more generalized as a positon with (x,y)
	 * (wendt)
	 *
	 * @since 07.11.2011 08:23:59
	 *
	 * @param oPos
	 * @return
	 */
	private static int getPositionArrayScore(clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPos) {
		int nRetVal = 1;
		
		if (oPos.b==null) {
			nRetVal++;
		}
		
		if (oPos.c==null) {
			nRetVal++;
		}
		
		return nRetVal;
	}
	//delacruz 11.3.2018: change visibility function to protected for usability in PrintTools.java
	protected static ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> findMatchingEntities(ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> poPIPositionList, ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> poRIPositionList) {
		
		// similar entities to PI-entities
		ArrayList<clsThingPresentationMesh> oSimilarEntities= new ArrayList<clsThingPresentationMesh>() ;
		//threshold for similarity
		double rthreshold = 0.1;
		
		//Search the closest distance to objects in the perception
		
		//clsPair(PI-Part, RI-Part). PI-Part = PI, X, Y. RI-Part = RI, Distance
		ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> oRetVal = new ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>();
		
		ArrayList<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>> oPIListCopy = poPIPositionList;
		
		//go through all objects in the RI List, where a distance shall be calculated
		//In the quadrupel, the RI object is given, where the position is already known. The corresponding position is searched in the PI and added here. At the end, the RI-Object will have
		//Both its own and the PI positions and the distance between them. This is the foundation of the matching calculation
		for (int j=0; j<poRIPositionList.size();j++) {
			clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oRIPosition = poRIPositionList.get(j);
			//Get Object type ID (Instance ID is not important here)
			int nRIObjectID = oRIPosition.a.getDS_ID();
			
			//Position of the best match object, which shall be deleted from the list
			clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oBestPIPosition = null;
			int nObjectPositionInPIArray=-1;
			double rBestDistance = -1;
			//Boolean if there is any match
			//boolean bMatchFound = false;
			
			
			for (int i=0;i<oPIListCopy.size();i++) {
				clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPIPositionCopy = oPIListCopy.get(i);
				
				//Ssch 15.05.2012 Consider Entitycomparison when calculation Imagematch
				
				double rmatchingFactor = oRIPosition.a.compareTo(oPIPositionCopy.a);
//				double rmatchingFactor = oPIPositionCopy.a.compareTo(oRIPosition.a);
				if (rmatchingFactor >= rthreshold) {
							
				
					//No match is set, get the first match. Define the oMatched Position. The first value will be taken
					if (nObjectPositionInPIArray==-1) {
						//The first value is the best position
						rBestDistance = getDistance(oPIPositionCopy, oRIPosition);
						oBestPIPosition = oPIPositionCopy;
						//Get object position in the array
						nObjectPositionInPIArray = i;
					//else there is already a found value. The found value is compared with the newly found value, in order to find an even smaller distance
					} else {
						//Get the distance and specialized positions
						double rCurrentDistance = getDistance(oPIPositionCopy, oRIPosition);
						//If the new distance is smaller than the best distance, then this value should be taken instead
						if (rCurrentDistance<rBestDistance) {
							rBestDistance = rCurrentDistance;
							oBestPIPosition = oPIPositionCopy;
							nObjectPositionInPIArray = i;
						}
					}
					
					if (rBestDistance==0.0) {
						break;
					}
				}	
			}
			
			//If a best match was found, delete the found object from the PI-List, as it shall only be used in comparison once
			if (nObjectPositionInPIArray!=-1) {
				//Add the new element to the output list
				oRetVal.add(new clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>(oBestPIPosition, new clsPair<clsThingPresentationMesh, Double>(oRIPosition.a, rBestDistance)));
				//Delete the element
				oPIListCopy.remove(nObjectPositionInPIArray);
			}
		}

		return oRetVal;
	}
	
	/**
	 * This function specialize a position in an RI according to the position in the PI. This is used, in order to calculate the distance between objects in RI and PI. If the PI is not spezialized, 
	 * (wendt)
	 *
	 * @since 07.11.2011 11:27:06
	 *
	 * @param poRIElement
	 * @param poPIElement
	 * @return
	 */
	private static <E extends clsDataStructurePA> clsPair<clsTriple<E, ePhiPosition, eRadius>, clsTriple<E, ePhiPosition, eRadius>> specializePositions(clsTriple<E, ePhiPosition, eRadius> poPIElement, clsTriple<E, ePhiPosition, eRadius> poRIElement) {
		//Element a: PI, b: RI
		clsPair<clsTriple<E, ePhiPosition, eRadius>, clsTriple<E, ePhiPosition, eRadius>> oRetVal = new clsPair<clsTriple<E, ePhiPosition, eRadius>, clsTriple<E, ePhiPosition, eRadius>>(poPIElement, poRIElement);
		//Set default positions, if they are both not specialized
		ePhiPosition oDefaultXPosition = ePhiPosition.CENTER;
		eRadius oDefaultYPosition = eRadius.NEAR;
		
		
		//If the x-posisiton in RI is null, then it shall be taken from the x position in PI
		if (oRetVal.b.b==null && oRetVal.a.b!=null) {
			oRetVal.b.b=oRetVal.a.b;
		//If the x-posisiton in PI is null, then it shall be taken from the x position in RI
		} else if (oRetVal.b.b!=null && oRetVal.a.b==null) {
			oRetVal.a.b=oRetVal.b.b;
		//If both of them are null, then set	
		} else if (oRetVal.b.b==null && oRetVal.a.b==null) {
			oRetVal.a.b=oDefaultXPosition;
			oRetVal.b.b= oDefaultXPosition;
		}
		
		//If the x-posisiton in RI is null, then it shall be taken from the x position in PI
		if (oRetVal.b.c==null && oRetVal.a.c!=null) {
			oRetVal.b.c=oRetVal.a.c;
		//If the x-posisiton in PI is null, then it shall be taken from the x position in RI
		} else if (oRetVal.b.c!=null && oRetVal.a.c==null) {
			oRetVal.a.c=oRetVal.b.c;
		//If both of them are null, then set	
		} else if (oRetVal.b.c==null && oRetVal.a.c==null) {
			oRetVal.a.c=oDefaultYPosition;
			oRetVal.b.c= oDefaultYPosition;
		}
		
		return oRetVal;
	}
	
	/**
	 * Get distance between 2 objects, where all generalized positions are specialized first
	 * (wendt)
	 *
	 * @since 08.11.2011 11:17:23
	 *
	 * @param poPIElement
	 * @param poRIElement
	 * @return
	 */
	public static <E extends clsDataStructurePA> double getDistance(clsTriple<E, ePhiPosition, eRadius> poPIElement, clsTriple<E, ePhiPosition, eRadius> poRIElement) {
		
		//Specialize RI positions for PI
		clsPair<clsTriple<E, ePhiPosition, eRadius>, clsTriple<E, ePhiPosition, eRadius>> oSpecializedPositions = specializePositions(poPIElement, poRIElement);
		
		if (oSpecializedPositions.a.b==null||oSpecializedPositions.a.c==null) {
			try {
				throw new Exception("Some values in the Specialized PIPosition are null. PI: " + oSpecializedPositions.a.toString());
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//The outcome of specialize positions is always != null
		return getDistance((double)oSpecializedPositions.b.b.mnPos, (double)oSpecializedPositions.b.c.mnPos, (double)oSpecializedPositions.a.b.mnPos, (double)oSpecializedPositions.a.c.mnPos);
	}
	
	/**
	 * Return the distance between tow coordinates (x1, y1) - (x2, y2)
	 * (wendt)
	 *
	 * @since 01.10.2011 09:57:36
	 *
	 * @param prX
	 * @param prY
	 * @return
	 */
	public static double getDistance(double prX1, double prY1, double prX2, double prY2) {
		clsPair<Double, Double> oRelationVector = getRelationVector(prX1, prY1, prX2, prY2);
		
		return getDistance(oRelationVector);
	}
	
	/**
	 * Return the length of a relation vector (x, y)
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 16:07:31
	 *
	 * @param poRelationVector
	 * @return
	 */
	public static double getDistance(clsPair<Double, Double> poRelationVector) {
		return Math.sqrt(Math.pow(poRelationVector.a, 2) + Math.pow(poRelationVector.b, 2));
	}
	
	
	/**
	 * Get the relation vector between 2 objects
	 * 
	 * (wendt)
	 *
	 * @since 19.02.2012 16:02:10
	 *
	 * @param prX1
	 * @param prY1
	 * @param prX2
	 * @param prY2
	 * @return
	 */
	public static clsPair<Double, Double> getRelationVector(double prX1, double prY1, double prX2, double prY2) {
		double rX = prX1-prX2;
		double rY = prY1-prY2;
		
		return new clsPair<Double, Double>(rX, rY);
	}
	
	/**
	 * Get the relation of a 1D vector, i. e. only one coordinate
	 * 
	 * (wendt)
	 *
	 * @since 22.05.2012 09:12:56
	 *
	 * @param prX1orY1
	 * @param prX2orY2
	 * @return
	 */
	public static double get1DRelationVector(double prX1orY1, double prX2orY2) {
		return prX1orY1 - prX2orY2;
	}
	
	/**
	 * Calculate a normalized association weight from the distance. The higher the distance, the lower the weight
	 *
	 * WENDT
	 * @since 01.10.2011 10:00:49
	 *
	 * @param prDistance
	 * @return
	 */
	private static double calculateAssociationWeightFromDistance(double prDistance) {
		//return 1/(1+prDistance);
		return Math.pow(Math.E, -prDistance/5);
	}
	
	/**
	 * Extract the caluclated distance from the association weight
	 * (wendt)
	 *
	 * @since 07.11.2011 12:09:21
	 *
	 * @param prAssociationWeight
	 * @return
	 */
	private static double calculateDistanceFromAssociationWeight(double prAssociationWeight) {
		return (1-prAssociationWeight)/prAssociationWeight;
	}
	
	/**
	 * Create a temporal association between the objects in the image and in the perception. The weight of this association is the inverted distance. This association is
	 * added to the external associations of the objects. As it is no defining association, it is interpreted as a match association only
	 * 
	 * (wendt)
	 *
	 * @since 01.10.2011 10:07:38
	 *
	 * @param poElementA
	 * @param poElementB
	 * @param prDistance
	 * @return
	 */
	private static clsAssociationTime createDistanceAssociation(clsThingPresentationMesh poElementA, clsThingPresentationMesh poElementB, double prDistance) {
		double prAssWeight = calculateAssociationWeightFromDistance(prDistance);
		clsAssociationTime oRetVal = new clsAssociationTime(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONTEMP, eContentType.MATCHASSOCIATION), poElementA, poElementB);
		oRetVal.setMrWeight(prAssWeight);
		return oRetVal;
	}
	
//	/**
//	 * Add associations between all objects in the image to the image. The distance between the objects is used as association
//	 * weights. In this way, it is possible to identify patterns and to recognize images, with similar patterns
//	 * (wendt)
//	 *
//	 * @since 01.10.2011 22:41:58
//	 *
//	 * @param poInputContainer
//	 * @param pbTemplateSetting
//	 */
//	public static void addRelationAssociations(clsPrimaryDataStructureContainer poInputContainer, boolean pbTemplateSetting) {
//		//Compare all elements in the container with each other and add distance associations
//		if ((poInputContainer.getMoDataStructure() instanceof clsTemplateImage)==false) {
//			try {
//				throw new Exception("Error in clsSpatialTools, addRelationAssociations: Input data type not allowed. Only TI allowed");
//			} catch (Exception e) {
//				// TODO (wendt) - Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		ArrayList<clsAssociation> oTimeAssList = ((clsTemplateImage)poInputContainer.getMoDataStructure()).getMoAssociatedContent();
//		//Go through all elements in the image and calculate their positions
//		ArrayList<clsTriple<clsDataStructurePA, eXPosition, eYPosition>> oPositionList = new ArrayList<clsTriple<clsDataStructurePA, eXPosition, eYPosition>>();
//		for (clsAssociation oAss : oTimeAssList) {
//			//Get no Empty space objects
//			clsPrimaryDataStructure  oElement = (clsPrimaryDataStructure) oAss.getLeafElement();
//			clsTriple<clsDataStructurePA, eXPosition, eYPosition> oElementPosition = clsSpatialTools.getPosition(oElement, poInputContainer);
//			if (oElementPosition!=null) {
//				oPositionList.add(oElementPosition);
//			}
//		}
//		
//		//Get all distances between those positions
//		for (int i=0;i<oPositionList.size();i++) {
//			clsPrimaryDataStructure oElementA = (clsPrimaryDataStructure) oPositionList.get(i).a;
//			for(int j=i+1;j<oPositionList.size();j++) {
//				clsPrimaryDataStructure oElementB = (clsPrimaryDataStructure) oPositionList.get(j).a;
//				//get distance
//				double rDistance = getDistance((double)oPositionList.get(i).b.mnPos, (double)oPositionList.get(i).c.mnPos, (double)oPositionList.get(j).b.mnPos, (double)oPositionList.get(j).c.mnPos);
//				//create association
//				clsAssociationTime oNewDistanceAss = createDistanceAssociation(oElementA, oElementB, rDistance);
//				//Add this association to the container
//				poInputContainer.addMoAssociatedDataStructure(oNewDistanceAss);
//			}
//		}
//	}
	
//	/**
//	 * Get the position of an object as a pair of Strings, where the position-TP are in some container
//	 * (wendt)
//	 *
//	 * @since 02.09.2011 14:27:26
//	 *
//	 * @param <E>
//	 * @param poObject
//	 * @param poPositionContainer
//	 * @return
//	 */
//	public static clsTriple<clsThingPresentationMesh, String, String> getObjectPosition(clsThingPresentationMesh poObject) {
//		clsTriple<clsThingPresentationMesh, String, String> oRetVal = null;
//		//Get object position only if both x and y are given
//		
//		ArrayList<String> oDistance = new ArrayList<String>();
//		oDistance.addAll(Arrays.asList("FAR","MEDIUM","NEAR","MANIPULATEABLE","EATABLE"));
//		ArrayList<String> oPosition = new ArrayList<String>();
//		oPosition.addAll(Arrays.asList("RIGHT","MIDDLE_RIGHT","CENTER","MIDDLE_LEFT","LEFT"));
//		
//		ArrayList<clsAssociation> oAllAss = poObject.getExternalMoAssociatedContent();
//		
//		boolean bDistanceFound = false;
//		boolean bPositionFound = false;
//		
//		clsTriple<clsThingPresentationMesh, String, String> oPositionPair = new clsTriple<clsThingPresentationMesh, String, String>(poObject, "", "");
//		
//		for (clsAssociation oSingleAss : oAllAss) {
//			if (oSingleAss instanceof clsAssociationAttribute) {
//				if (oSingleAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE.toString())) {
//					String oContent = (String) ((clsThingPresentation)oSingleAss.getLeafElement()).getMoContent();
//					if (bDistanceFound==false) {
//						for (String oDist : oDistance) {
//							if (oDist.equals(oContent)) {
//								oPositionPair.c = oDist;
//								bDistanceFound = true;
//								break;
//							}
//						}
//					}
//				}
//							
//				if (oSingleAss.getLeafElement().getMoContentType().equals(eContentType.POSITION.toString())) {
//					String oContent = (String) ((clsThingPresentation)oSingleAss.getLeafElement()).getMoContent();
//					if (bPositionFound==false) {
//						for (String oPos : oPosition) {
//							if (oPos.equals(oContent)) {
//								oPositionPair.b = oPos;
//								bPositionFound = true;
//								break;
//							}
//						}
//					}
//				}
//			}
//		}
//		
//		if ((bPositionFound==true) && (bDistanceFound==true)) {
//			oRetVal = oPositionPair;
//		}
//		
//		return oRetVal;
//	}
	/*
    public static boolean isActionImage(clsThingPresentationMesh moImage) { 
        if(moImage.getContentType().equals(eContentType.RI)  && moImage.getContent().contains("ACTION_IMAGE"))
            return true;
        else if(moImage.getContent().equals("PI")) { 
            ArrayList<clsAssociation> internalAssociatedContent = new ArrayList<clsAssociation>();
            for(clsAssociation oAssExt : moImage.getInternalAssociatedContent()) {
                clsThingPresentationMesh  oElementB = (clsThingPresentationMesh)oAssExt.getAssociationElementB();
                if(oElementB.getContent().equals("EMPTYSPACE")) continue;
                ArrayList<clsAssociation> externalAssociatedContentTMP = oElementB.getExternalAssociatedContent();
                for(clsAssociation oAssExtTMP : externalAssociatedContentTMP)  {
                   if(oAssExtTMP instanceof clsAssociationPrimary) { 
                       return true;
                    }
                }
            }
            return false;
        }
        else 
            return false;
            
    } */

}
