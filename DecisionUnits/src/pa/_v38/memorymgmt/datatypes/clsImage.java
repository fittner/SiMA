/**
 * CHANGELOG
 *
 * 03.07.2011 perner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.planningHelpers.eDirection;
import pa._v38.tools.planningHelpers.eDistance;
import pa._v38.tools.planningHelpers.eObjectCategorization;


/**
 * DOCUMENT (perner) - image of a situation
 * used right now as a wrapper class to test the planning environment
 * 
 * @author perner
 * 03.07.2011, 15:39:58
 * 
 */
public class clsImage {

	/**
	 * possible entries
	 * not all entries must be set - at least one entry holds a value
	 */
	eDistance 				m_eDist = null;
	eDirection 				m_eDir  = null;
	eObjectCategorization 	m_eObj = null;
	
	/**
	 * 
	 * DOCUMENT (perner) - constructor to set all values 
	 *
	 * @since 20.07.2011 18:50:03
	 *
	 * @param dist
	 * @param dir
	 * @param obj
	 */
	public clsImage(eDistance dist, eDirection dir, eObjectCategorization obj) {
		m_eDist = dist;
		m_eDir = dir;
		m_eObj = obj;
	}

	/**
	 * 
	 * DOCUMENT (perner) - constructor to only set object and direction 
	 *
	 * @since 20.07.2011 18:50:18
	 *
	 * @param dir
	 * @param obj
	 */
	public clsImage(eDirection dir, eObjectCategorization obj) {
		m_eDir = dir;
		m_eObj = obj;
	}

	/**
	 * 
	 * DOCUMENT (perner) - only set the object
	 *
	 * @since 20.07.2011 18:52:11
	 *
	 * @param obj
	 */
	public clsImage(eObjectCategorization obj) {
		m_eObj = obj;
	}

	
}
