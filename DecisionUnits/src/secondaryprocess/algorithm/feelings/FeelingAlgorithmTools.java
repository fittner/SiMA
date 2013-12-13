/**
 * CHANGELOG
 *
 * 30.10.2013 wendt - File created
 *
 */
package secondaryprocess.algorithm.feelings;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.10.2013, 12:23:03
 * 
 */
public class FeelingAlgorithmTools {
    
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
     * DOCUMENT (wendt) - insert description
     *
     * @since 23.05.2013 13:30:24
     *
     * @param poGoal 
     */
    public static double getConsequencesOfFeelingsOnGoalAsImportance(clsWordPresentationMeshPossibleGoal poGoal, ArrayList<clsWordPresentationMeshFeeling> poFeltFeelingList) {
        return temporaryAffectComputation(poGoal,  poFeltFeelingList);
        /*
        double rResult = 0;
        //Get Feeling affect
        ArrayList<clsWordPresentationMeshFeeling> oFeelingList = poGoal.getFeelings();
            
        for (clsWordPresentationMeshFeeling oF : oFeelingList) {
            double nAffectFromFeeling = oF.getIntensity();
            rResult += nAffectFromFeeling;
        }
        
        return rResult;
        */
    }
}
