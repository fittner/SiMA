/**
 * CHANGELOG
 *
 * 03.07.2011 perner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author perner
 * 03.07.2011, 15:38:18
 * 
 */
public class clsPlanFragment {
	
	/** set of wordpresentations that holds the information about the action */ 
	private clsAct m_act; 
	
	/** image that stores the information about how the world looks like before this action is carried out */
	private clsImage m_preconditionImage;
	
	/** image that stores the information about how the world looks like after the actions was carried out */ 
	private clsImage m_effectImage;

	/**
	 * DOCUMENT (perner) - insert description 
	 *
	 * @since 03.07.2011 15:43:40
	 *
	 */
	public clsPlanFragment() {
		// TODO (perner) - Auto-generated constructor stub
	}
	
	public clsPlanFragment (clsAct act, clsImage precdImage, clsImage effectImage ) {
		m_act = act;
		m_preconditionImage = precdImage;
		m_effectImage = effectImage;
	}
}
