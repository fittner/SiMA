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
 * 
 * @author perner
 * 03.07.2011, 15:39:58
 * 
 */
public class clsImage {

	//TODO add implementation
	
	eDistance 				m_eDist;
	eDirection 				m_eDir;
	eObjectCategorization 	m_eObj;
	
	public clsImage(eDistance dist, eDirection dir, eObjectCategorization obj) {
		m_eDist = dist;
		m_eDir = dir;
		m_eObj = obj;
	}
	
}
