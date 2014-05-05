/**
 * CHANGELOG
 *
 * 30.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.feelings;

import java.util.ArrayList;

import org.slf4j.Logger;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eGoalType;
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
    
    
        /**
     * 
     * DOCUMENT (wendt, schaat) - insert description
     *
     * @since 23.05.2013 13:30:24
     *
     * @param poGoal 
     */
    public static double getConsequencesOfFeelingsOnGoalAsImportance(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        //return temporaryAffectComputation(poGoal,  poFeltFeelingList);
        
        double rResult = 0;
        double rMatchingFactor = 0;
        
        //Get Feeling affect
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
            
        for (clsWordPresentationMeshFeeling oGoalFeeling : oFeelingList) {
            
            for (clsWordPresentationMeshFeeling oCurrentFeeling: poFeltFeelingList) {
                if(oCurrentFeeling.getContent().contentEquals(oGoalFeeling.getContent())) {
                    
                    rMatchingFactor =+ 1- (Math.abs(oCurrentFeeling.getIntensity()-oGoalFeeling.getIntensity()));
                }
            }
        }

//        if(poGoal.getSupportiveDataStructure().getContent().startsWith("A14")) 
            moLogger.debug("Feelings cause evaluation change for {}({}) by {}", poGoal.getContent(), poGoal.getSupportiveDataStructure().getContent(), rMatchingFactor);
        
        rResult += rMatchingFactor;
        
        return rResult;
        
    }
}
