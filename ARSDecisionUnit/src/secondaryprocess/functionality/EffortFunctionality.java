/**
 * CHANGELOG
 *
 * 27.09.2013 wendt - File created
 *
 */
package secondaryprocess.functionality;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import base.datatypes.clsWordPresentationMeshPossibleGoal;
import secondaryprocess.algorithm.goals.GoalAlgorithmTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2013, 21:18:37
 * 
 */
public class EffortFunctionality {
    
    private static Logger log = clsLogger.getLog("SecondaryProcessFunctionality");
    /**
   * DOCUMENT (wendt) - insert description
   *
   * @since 12.02.2013 11:41:40
   *
   * @param poGoalList
   */
  public static void applyEffortOfGoal(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList) {
      for (clsWordPresentationMeshPossibleGoal oGoal : poGoalList) {
          
          //Get the penalty for the effort of distance, act confidence and conditions for all goal types
          //TODO: Replace this method with productions instead
          double oImportanceValue = GoalAlgorithmTools.calculateEffortPenalty(oGoal);
          
          
          
          
        //Add Context importantce
        //nResult = clsImportanceTools.getEffortValueOfSpeechActConfidence(clsActDataStructureTools.getIntention(poGoal.getSupportiveDataStructure()));
          
          
          
          if (oImportanceValue<0)
          {
              oImportanceValue = 0;
          }
          oGoal.addEffortImpactImportance(oImportanceValue);
      }
      
      log.debug("Applied effort to goals:" + poGoalList.toString());
      
  }
  
}
