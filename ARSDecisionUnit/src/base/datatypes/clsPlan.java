/**
 * CHANGELOG
 *
 * 30.03.2012 perner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

import secondaryprocess.algorithm.planning.helpers.PlanningNode;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 30.03.2012, 16:28:04
 * @deprecated
 */
public class clsPlan {

	protected ArrayList<PlanningNode> myPlans;

	/**
	 * DOCUMENT (perner) - insert description
	 * 
	 * @since 30.03.2012 16:28:39
	 * 
	 */
	public clsPlan() {
		myPlans = new ArrayList<PlanningNode>();
	}

	public void pushPlanFragment(PlanningNode planFragment) {
		myPlans.add(planFragment);
	}

	public String getStringRepresentation() {
		return "not implemented yet";
	}

	public PlanningNode getPlanAtPos(int i) {
		return myPlans.get(i);
	}

	public int getSize() {
		return myPlans.size();
	}

	@Override
	public String toString() {
		String content = new String();
		for (PlanningNode singelPlan : myPlans) {
			content = content + " " + singelPlan + " <-";
		}
		return content;
	}
	
	public ArrayList<PlanningNode> returnContent() {
		return myPlans;
	}

}
