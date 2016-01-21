/**
 * CHANGELOG
 *
 * 16.12.2015 Kollmann - File created
 *
 */
package control.interfaces;

import java.util.ArrayList;
import java.util.List;

import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 16.12.2015, 12:14:57
 * 
 */
public interface itfDuAnalysis {
    public void putFactor(String oFactorId, String oFactorValue);
    public void putAction(String oActionValue);
    public void put_F71_emotionValues(List<clsEmotion> poEmotions);
    public void putFinalGoals(ArrayList<clsWordPresentationMeshPossibleGoal> goals);
}
