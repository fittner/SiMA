/**
 * CHANGELOG
 *
 * 16.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;
import pa._v38.tools.ElementNotFoundException;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.10.2013, 12:46:50
 * 
 */
public interface DecisionEngineInterface {
    public ArrayList<clsWordPresentationMeshPossibleGoal> initialzeGoals(ArrayList<clsWordPresentationMeshPossibleGoal> incomingGoals);
    public void declareGoalAsContinued(clsWordPresentationMeshGoal poGoal) throws ElementNotFoundException;
    public ArrayList<clsWordPresentationMeshPossibleGoal> getContinuedGoals(ArrayList<clsWordPresentationMeshPossibleGoal> goalList);
    public clsWordPresentationMeshPossibleGoal getPlanGoal(ArrayList<clsWordPresentationMeshPossibleGoal> goalList);
    public void removeGoalAsContinued(ArrayList<clsWordPresentationMeshGoal> goalList) throws ElementNotFoundException;
    public void declareGoalAsContinuedGoal(ArrayList<clsWordPresentationMeshPossibleGoal> poGoal) throws Exception;
    public void declareGoalAsPlanGoal(clsWordPresentationMeshPossibleGoal poGoal) throws Exception;
    public void removeGoalAsPlanGoal(ArrayList<clsWordPresentationMeshPossibleGoal> goalList) throws ElementNotFoundException;
    public void setInitialSettings(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList);
    public void applyConsequencesOfActionOnContinuedGoal(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList);
    public void generateDecision(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList);
    public void generatePlanFromDecision(ArrayList<clsWordPresentationMeshPossibleGoal> poGoalList);
    
    
}
