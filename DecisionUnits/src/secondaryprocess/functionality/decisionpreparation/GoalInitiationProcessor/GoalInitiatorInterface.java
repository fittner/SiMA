/**
 * CHANGELOG
 *
 * 15.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.GoalInitiationProcessor;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 15.10.2013, 20:59:58
 * 
 */
public interface GoalInitiatorInterface {
    public ArrayList<clsWordPresentationMeshSelectableGoal> initiateIncomingGoals(ArrayList<clsWordPresentationMeshSelectableGoal> newGoals, clsWordPresentationMeshMentalSituation previousGoal);
}
