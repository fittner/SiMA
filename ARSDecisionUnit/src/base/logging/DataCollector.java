/**
 * CHANGELOG
 *
 * 25. Apr. 2016 Kollmann - File created
 *
 */
package base.logging;

import java.util.HashMap;
import java.util.Map;

import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 25. Apr. 2016, 13:29:00
 * 
 */
public class DataCollector {
    private static Map<String, GoalData> dtData = new HashMap<>();
    
    public static GoalData goal(clsWordPresentationMeshPossibleGoal oGoal) {
        String oIdentifier = oGoal.getSupportiveDataStructure().getContent() + ":" + oGoal.getGoalContentIdentifier();
        
        return goal(oIdentifier);
    }
    
    public static GoalData goal(String name) {
        if(!dtData.containsKey(name)) {
            dtData.put(name, new GoalData());
        }
        return dtData.get(name);
    }
}
