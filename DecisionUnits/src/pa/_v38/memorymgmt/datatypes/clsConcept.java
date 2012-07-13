/**
 * CHANGELOG
 *
 * 19.05.2012 hinterleitner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.planningHelpers.PlanningNode;

/**
 * DOCUMENT (hinterleitner) - insert description
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * 
 */
public class clsConcept{

	protected ArrayList<PlanningNode> myConcepts;

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 19.05.2012 16:28:39
	 * 
	 */
	public clsConcept() {
		myConcepts = new ArrayList<PlanningNode>();
	}

	public void pushPlanFragment(PlanningNode conceptFragment) {
		myConcepts.add(conceptFragment);
	}

	public PlanningNode getPlanAtPos(int i) {
		return myConcepts.get(i);
	}

	public int getSize() {
		return myConcepts.size();
	}

	@Override
	public String toString() {
		String content = new String();
		for (PlanningNode singelPlan : myConcepts) {
			content = content + " " + singelPlan + " <-";
		}
		return content;
	}
	
	public ArrayList<PlanningNode> returnContent() {
		return myConcepts;
	}

}
