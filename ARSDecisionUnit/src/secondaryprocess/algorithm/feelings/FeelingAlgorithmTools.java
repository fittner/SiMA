/**
 * CHANGELOG
 *
 * 30.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.feelings;

import java.util.ArrayList;
import org.slf4j.Logger;

import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eGoalType;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.10.2013, 12:23:03
 * 
 */
public class FeelingAlgorithmTools {
    private static final Logger moLogger = clsLogger.getLog("Feelings");
    
    private static double temporaryAffectComputation(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList){
        double rResult = 0;
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
        
        for(clsWordPresentationMeshFeeling feltFeeling: poFeltFeelingList){
            //Check for error-case: Content not a feeling
            if(feltFeeling.getContentType().equals(eContentType.FEELING)){
                //Special case: Anxiety. Does not apply to feeling goals
                if(!poGoal.getGoalTypeName().equals(eGoalType.MEMORYFEELING) && feltFeeling.getContent().equals("ANXIETY")){
                    for(clsWordPresentationMeshFeeling goalFeeling: oFeelingList){
                        rResult += getFeelingValue(goalFeeling);
                    }
                //Standard case
                }else{
                    double feltValue;
                    double goalValue;
                    for(clsWordPresentationMeshFeeling goalFeeling: oFeelingList){
                        if(feltFeeling.getContent().equals(goalFeeling.getContent())){
                            feltValue = getFeelingValue(feltFeeling);
                            goalValue = getFeelingValue(goalFeeling);
                            if(feltValue*goalValue >0){                              
                                if(Math.abs(feltValue)>=Math.abs(goalValue)){
                                    rResult+=goalValue;
                                }
                                else{
                                    rResult+=feltValue;
                                }
                            }
                        }
                    }
                }
            }else{
                //TODO: replace with logger or Exception / not actually an error, but expected?
               // System.err.println("Non-Feeling WPMF caught in clsImportanceTools." +
               //       "getConsequencesOfFeelingsOnGoalsAsImportance");
            }
        }
        return rResult;
    }
    
    /**
     * Returns the difference between pleasure and unpleasure. Returns
     * 0 if the content type is not eContentType.FEELING
     * 
     *
     * @since 29.06.2013 15:42:59
     *
     * @param feel
     * @return
     */
    private static double getFeelingValue(clsWordPresentationMeshFeeling feel){
        double retVal = 0;
        
        if(feel.getContentType().equals(eContentType.FEELING)){
            retVal = feel.getPleasure() - feel.getUnpleasure();
        
        }
        return retVal;
    }
    
    private static double getFeelingMatch(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        double rResult = 0;
        double rMatchingFactor = 0;
        int nCount = 0;
        
        //Get Feeling affect
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
            
        for (clsWordPresentationMeshFeeling oGoalFeeling : oFeelingList) {
            
            for (clsWordPresentationMeshFeeling oCurrentFeeling: poFeltFeelingList) {
                if(oCurrentFeeling.getContent().contentEquals(oGoalFeeling.getContent())) {
                    rMatchingFactor += 1 - oCurrentFeeling.getDiff(oGoalFeeling);
                    nCount++;
                }
            }
        }

//        if(poGoal.getSupportiveDataStructure().getContent().startsWith("A14")) 
//            moLogger.debug("Feelings cause evaluation change for {}({}) by {}", poGoal.getContent(), poGoal.getSupportiveDataStructure().getContent(), rMatchingFactor);
        
        rResult = rMatchingFactor / nCount;
        
        return rResult;
    }
    
    private static double getFeelingMatch(clsWordPresentationMesh poImage, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        double rMatchingFactor = 0;
        int nCount = 0;
        double rDiffValue = 0;
        double rDiff = 0;
        
        if(poImage != null && !poImage.isNullObject()) {
            //Get Feelings fromt he image
            ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poImage.getFeelings();
            
            if(oFeelingList.isEmpty()) {
                //Try to get a SELF from the image and take the feelings from there
                clsWordPresentationMesh oSelf = clsMeshTools.getSELF(poImage);
                
                if(oSelf != null && !oSelf.isNullObject()) {
                    oFeelingList = oSelf.getFeelings();
                }
            }
            
            //Compare the feelings
            for (clsWordPresentationMeshFeeling oGoalFeeling : oFeelingList) {
                for (clsWordPresentationMeshFeeling oCurrentFeeling: poFeltFeelingList) {
                    if(oCurrentFeeling.getContent().contentEquals(oGoalFeeling.getContent())) {
                        rDiff = oCurrentFeeling.getDiff(oGoalFeeling);
                        rDiffValue = rDiffValue + (1 - rDiffValue) * rDiff;
                        //rMatchingFactor += 1 - oCurrentFeeling.getDiff(oGoalFeeling);
                        
                    }
                }
            }
        }
        
        return 1 - rDiffValue;
    }
    
    /**
     * 
     * DOCUMENT (wendt, schaat) - Values goals which feelings matches with the agent's current feelings ("Impulshandeln")
     *
     * @since 23.05.2013 13:30:24
     *
     * @param poGoal 
     */
    public static double evaluateGoalByTriggeredFeelings(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        double rFeelingMatch = 0;
        clsWordPresentationMesh oWPMImage = null;
        
        //get act for that goal (what should happen if the goal has no act [yet], like perception or drive goals?)
        clsWordPresentationMesh oSupp = poGoal.getSupportiveDataStructure();
        
        //how to find out if this is an act
        if(oSupp.getContentType().equals(eContentType.ACT)) {
            //get the intention
            oWPMImage = clsActDataStructureTools.getIntention(oSupp);
            
            //compare the intention(image) to the current feelings (match value is [0,1]
            rFeelingMatch = getFeelingMatch(oWPMImage, poFeltFeelingList);
            
            
        }
        
        //adjust match value by match importance
        return rFeelingMatch; 
    }
    
    protected static clsEmotion extractBasicEmotion(clsWordPresentationMesh poImage) {
        clsEmotion oBasicEmotion = null;
        
        if(poImage != null && !poImage.isNullObject()) {
            ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poImage.getFeelings();
            //kollmann: difficult to decide with emotion to use if several are available ...
            if(!oFeelingList.isEmpty()) {
                oBasicEmotion = oFeelingList.get(0).getEmotion();
            }
        }
        
        return oBasicEmotion;
    }
    
    protected static double calculateEmotionAttractiveness(clsEmotion poEmotion) {
        return poEmotion.getSourcePleasure() - poEmotion.getSourceUnpleasure();
    }
    
    /**
     * 
     * DOCUMENT (martinez) - this method just resembles the evaluateGoalByTriggeredFeelings() method. Right now it just return 0, so the goal handling functionality class can work.
     * SSch: if enough neutralized intensity is available, the agent reflects about the goal's appropriateness. That is, the consideration if the act reduces unpleasure and increases pleasure. 
     *  
     * @since 27.05.2014 13:30:24
     *
     * @param poGoal 
     */
    public static double evaluateGoalByExpectedFeelings(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList){
        double rImportanceChange = 0;
        clsEmotion oExpectedChange = null;
        clsWordPresentationMesh oIntention = null;
        clsWordPresentationMesh oFirstImage = null;
        clsWordPresentationMesh oLastImage = null;
        clsEmotion oFirstEmotion = null;
        clsEmotion oLastEmotion = null;
        
        //get act for that goal (what should happen if the goal has no act [yet], like perception or drive goals?)
        clsWordPresentationMesh oSupp = poGoal.getSupportiveDataStructure();
        
        //how to find out if this is an act
        if(oSupp.getContentType().equals(eContentType.ACT)) {
            //get the intention
            oIntention = clsActDataStructureTools.getIntention(oSupp);
            
            //just to be safe that the image actually has an intention
            if(oIntention != null && !oIntention.isNullObject()) {
                oFirstImage = clsActTools.getFirstImageFromIntention(oIntention);
                oLastImage = clsActTools.getLastImage(oFirstImage);

                oFirstEmotion = extractBasicEmotion(oFirstImage);
                oLastEmotion = extractBasicEmotion(oLastImage);
                
                //kollmann: see if the image has emotions at all (only continue of we have a FIRST and LAST image emotion
                //          TODO: not just extract the emotion from first and last imgae, but find the first and last emotion;
                if(oFirstEmotion != null && oLastEmotion != null) {
                    oExpectedChange = oLastEmotion.diff(oFirstEmotion);
                
                    rImportanceChange = calculateEmotionAttractiveness(oExpectedChange);
                }
            } else {
                throw new RuntimeException("Act " + oSupp.getContent() + " has no intention associated");
            }
        }
        
        //adjust match value by match importance
        return rImportanceChange; 
    }
    
    
        /**
     * 
     * DOCUMENT (martinez) this method just resembles the evaluateGoalByTriggeredFeelings() method. Right now it just return 0, so the goal handling functionality class can work.
     *
     * @since 27.05.2014 13:30:24
     *
     * @param poGoal 
     */
    public static double evaluateGoalByReservedFeelings(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList){
        double rFeelingMatchImportance = 0.1;
        
        double rFeelingMatch = getFeelingMatch(poGoal, poFeltFeelingList);
        
        return rFeelingMatchImportance * rFeelingMatch;
    }
}
