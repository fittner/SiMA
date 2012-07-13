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
public class clsDomain{

	protected ArrayList<PlanningNode> myDomain;

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 19.05.2012 16:28:39
	 * 
	 */
	public clsDomain() {
		myDomain = new ArrayList<PlanningNode>();
	}

	public void pushPlanFragment(PlanningNode conceptFragment) {
		myDomain.add(conceptFragment);
	}

	public PlanningNode getPlanAtPos(int i) {
		return myDomain.get(i);
	}

	public int getSize() {
		return myDomain.size();
	}

	@Override
	public String toString() {
		String content = new String();
		for (PlanningNode singlePlan : myDomain) {
			content = content + " " + singlePlan + " <-";
		}
		return content;
	}
	
	public ArrayList<PlanningNode> returnContent() {
		return myDomain;
	}

}
