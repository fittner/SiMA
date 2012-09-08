/**
 * CHANGELOG
 *
 * 19.05.2012 hinterleitner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa._v38.tools.planningHelpers.PlanningNode;

/**
 * DOCUMENT (hinterleitner) - insert description
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * 
 */
public class clsConcept {

	protected List<PlanningNode> myConcepts;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @since 25.08.2012 13:23:05
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((myConcepts == null) ? 0 : myConcepts.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @since 25.08.2012 13:23:05
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		clsConcept other = (clsConcept) obj;
		if (myConcepts == null) {
			if (other.myConcepts != null)
				return false;
		} else if (!myConcepts.equals(other.myConcepts))
			return false;
		return true;
	}

	public List<PlanningNode> returnContent() {
		return myConcepts;
	}

}
