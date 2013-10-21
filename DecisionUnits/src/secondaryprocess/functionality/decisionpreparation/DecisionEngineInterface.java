/**
 * CHANGELOG
 *
 * 16.10.2013 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.tools.ElementNotFoundException;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.10.2013, 12:46:50
 * 
 */
public interface DecisionEngineInterface {
    public ArrayList<clsWordPresentationMeshSelectableGoal> initialzeGoals(ArrayList<clsWordPresentationMeshSelectableGoal> incomingGoals);
    public void declareGoalAsContinued(clsWordPresentationMeshGoal poGoal) throws ElementNotFoundException;
    public ArrayList<clsWordPresentationMeshSelectableGoal> getContinuedGoals(ArrayList<clsWordPresentationMeshSelectableGoal> goalList);
    public clsWordPresentationMeshSelectableGoal getPlanGoal(ArrayList<clsWordPresentationMeshSelectableGoal> goalList);
    public void removeGoalAsContinued(ArrayList<clsWordPresentationMeshGoal> goalList) throws ElementNotFoundException;
    public void declareGoalAsContinuedGoal(ArrayList<clsWordPresentationMeshSelectableGoal> poGoal) throws Exception;
    public void declareGoalAsPlanGoal(clsWordPresentationMeshSelectableGoal poGoal) throws Exception;
    public void removeGoalAsPlanGoal(ArrayList<clsWordPresentationMeshSelectableGoal> goalList) throws ElementNotFoundException;
    public void setInitialSettings(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList);
    public void applyConsequencesOfActionOnContinuedGoal(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList);
    public void generateDecision(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList);
    public void generatePlanFromDecision(ArrayList<clsWordPresentationMeshSelectableGoal> poGoalList);
    
    
}
