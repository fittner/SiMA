/**
 * CHANGELOG
 *
 * 03.07.2011 perner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.planningHelpers.PlanningNode;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 03.07.2011, 15:38:18
 * 
 */
public class clsPlanFragment extends PlanningNode {

	/** set of wordpresentations that holds the information about the action */
	public clsAct m_act;

	/** image that stores the information about how the world looks like before this action is carried out */
	public clsImage m_preconditionImage;

	/** image that stores the information about how the world looks like after the actions was carried out */
	public clsImage m_effectImage;
	
	public String planLabel;

	/**
	 * DOCUMENT (perner) - insert description
	 * 
	 * @since 03.07.2011 15:43:40
	 * 
	 */
	public clsPlanFragment() {
		super("empty");
	}

	/**
	 * 
	 * DOCUMENT (perner) - constructs a new plan fragment
	 * 
	 * @since 17.07.2011 12:38:57
	 * 
	 * @param act
	 * @param precdImage
	 * @param effectImage
	 */
	public clsPlanFragment(clsAct act, clsImage precdImage, clsImage effectImage) {

		super(act.toString());
		m_act = act;
		m_preconditionImage = precdImage;
		m_effectImage = effectImage;
	}
	
	public clsPlanFragment(clsAct act, clsImage precdImage, clsImage effectImage, String planLabel) {
		this(act, precdImage, effectImage);
		this.planLabel = planLabel; 
	}

	@Override
	public String toString() {
		if (this.planLabel != null && !this.planLabel.isEmpty())
			return this.planLabel;
		return super.label + ", hasChild: " + super.hasChild + ", myParent: " + super.myParent + ", \nprecondition: " + this.m_preconditionImage + " postcondition:" + this.m_effectImage;

	}
}
