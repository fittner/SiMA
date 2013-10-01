/**
 * CHANGELOG
 *
 * 27.09.2013 wendt - File created
 *
 */
package system.functionality.effort;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import system.algorithm.goals.clsDecisionPreparationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 27.09.2013, 21:18:37
 * 
 */
public class EffortTools {
    
    private static Logger log = clsLogger.getLog("DecisionPreparation");
    /**
   * DOCUMENT (wendt) - insert description
   *
   * @since 12.02.2013 11:41:40
   *
   * @param poGoalList
   */
  public static void applyEffortOfGoal(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
      for (clsWordPresentationMeshGoal oGoal : poGoalList) {
          //Get the penalty for the effort
          double oImportanceValue = clsDecisionPreparationTools.calculateEffortPenalty(oGoal);
          
          oGoal.setEffortImpact(oImportanceValue);
      }
      
      log.debug("Applied effort to goals:" + poGoalList.toString());
      
  }
}
