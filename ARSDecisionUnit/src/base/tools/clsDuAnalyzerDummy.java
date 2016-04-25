/**
 * CHANGELOG
 *
 * 17.12.2015 Kollmann - File created
 *
 */
package base.tools;

import java.util.ArrayList;
import java.util.List;

import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import control.interfaces.itfDuAnalysis;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 17.12.2015, 11:15:34
 * 
 */
public class clsDuAnalyzerDummy implements itfDuAnalysis {

    @Override
    public void putFactor(String oFactorId, String oFactorValue) {
        //do nothing
    }

    /* (non-Javadoc)
     *
     * @since 17.12.2015 11:15:35
     * 
     * @see control.interfaces.itfDuAnalysis#putAction(java.lang.String)
     */
    @Override
    public void putAction(String oActionValue) {
        //do nothing
    }

    /* (non-Javadoc)
     *
     * @since 22.12.2015 18:14:48
     * 
     * @see control.interfaces.itfDuAnalysis#put_F71_emotionValues(java.util.List)
     */
    @Override
    public void put_F71_emotionValues(List<clsEmotion> poEmotions) {
        // TODO (Kollmann) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 18:27:45
     * 
     * @see control.interfaces.itfDuAnalysis#putFinalGoals(java.util.ArrayList)
     */
    @Override
    public void putFinalGoals(ArrayList<clsWordPresentationMeshPossibleGoal> goals) {
        // TODO (Kollmann) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 06. Feb. 2016 16:38:17
     * 
     * @see control.interfaces.itfDuAnalysis#put_F63_emotionContributors(base.datatypes.clsEmotion, base.datatypes.clsEmotion, base.datatypes.clsEmotion, base.datatypes.clsEmotion, base.datatypes.clsEmotion)
     */
    @Override
    public void put_F63_emotionContributors(clsEmotion poFromDrives, clsEmotion poFromPerceptionDrive, clsEmotion poFromPerceptionExperiences,
            clsEmotion poFromPerceptionBodystates, clsEmotion poFromMemorizedValuations) {
        // TODO (Heinrich Kemmler) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 06. Feb. 2016 16:38:17
     * 
     * @see control.interfaces.itfDuAnalysis#put_F63_basicEmotion(base.datatypes.clsEmotion)
     */
    @Override
    public void put_F63_basicEmotion(clsEmotion poBasicEmotion) {
        // TODO (Heinrich Kemmler) - Auto-generated method stub
        
    }

}
