/**
 * CHANGELOG
 *
 * 19.05.2012 hinterleitner - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;
import java.util.Collection;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;
import secondaryprocess.algorithm.planning.helpers.PlanningNode;

/**
 * DOCUMENT (hinterleitner) - insert description
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * 
 */
public class clsDomain {

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

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 22.12.2012 15:46:05
	 * 
	 * @param clsTriple
	 * @param arrayList
	 * @param oElVal
	 */
	public clsDomain(clsTriple<Integer, eDataType, eContentType> clsTriple,
			ArrayList<clsSecondaryDataStructure> arrayList, String oElVal) {
		// TODO (hinterleitner) - Auto-generated constructor stub
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

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 22.12.2012 15:48:13
	 * 
	 * @return
	 */
	public String getMoContent() {
		// TODO (hinterleitner) - Auto-generated method stub
		return null;
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 22.12.2012 15:48:19
	 * 
	 * @return
	 */
	public Collection<?> getMoAssociatedContent() {
		// TODO (hinterleitner) - Auto-generated method stub
		return null;
	}

	/**
	 * DOCUMENT (hinterleitner) - insert description
	 * 
	 * @since 22.12.2012 15:48:26
	 * 
	 * @param string
	 */
	public void setMoContent(String string) {
		// TODO (hinterleitner) - Auto-generated method stub

	}

}
