/**
 * CHANGELOG
 *
 * 03.07.2011 perner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import secondaryprocess.algorithm.planningHelpers.eDirection;
import secondaryprocess.algorithm.planningHelpers.eDistance;
import secondaryprocess.algorithm.planningHelpers.eEntity;



/**
 * DOCUMENT (perner) - image of a situation
 * used right now as a wrapper class to test the planning environment
 * 
 * @author perner
 * 03.07.2011, 15:39:58
 * @deprecated
 */
public class clsImage {

	/**
	 * possible entries
	 * not all entries must be set - at least one entry holds a value
	 */
	public eDistance 				m_eDist = null;
	public eDirection 				m_eDir  = null;
	public eEntity 				m_eObj = null;
	
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
	public clsImage (eDistance dist, eDirection dir, eEntity obj) {
		m_eDist = dist;
		m_eDir = dir;
		m_eObj = obj;
	}
	
	/**
	 * 
	 * DOCUMENT (perner) - set only the direction 
	 *
	 * @since 09.09.2011 15:38:25
	 *
	 * @param dir
	 */
	public clsImage (eDirection dir) {
		m_eDir = dir;
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
	public clsImage(eDirection dir, eDistance dist, eEntity obj) {
		m_eDir = dir;
		m_eDist = dist;
		m_eObj = obj;
	}
	
	public clsImage(eDirection dir, eEntity obj) {
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
	public clsImage(eEntity obj) {
		m_eObj = obj;
	}

	/**
	 * 
	 * DOCUMENT (perner) - workaround constructor for current mix of distance and direction
	 *
	 * @since 09.09.2011 08:45:11
	 *
	 * @param obj
	 * @param dist
	 */
	public clsImage(eEntity obj, eDistance dist) {
		m_eObj = obj;
		m_eDist = dist;
	}
	
	/**
	 * 
	 * DOCUMENT (perner) - empty constructor to create flee-action 
	 *
	 * @since 17.09.2011 15:38:28
	 *
	 */
	public clsImage () {
		
	}
	
	/**
	 * 
	 * DOCUMENT (perner) - compares this instance of an image to a given image
	 * if alle values are equal in both images true is returned
	 * if a null-is found and this value is set at the other image, false is returned
	 *
	 * @since 20.07.2011 19:19:33
	 *
	 * @param srcCompare
	 * @return
	 */
	public boolean isEqualStrictTo(clsImage srcCompare) {
		
		if (m_eDist != null && srcCompare.m_eDist != null) {
			if (m_eDist.compareTo(srcCompare.m_eDist) != 0)
				return false;
		} else if (srcCompare.m_eDist != null)
			return false;

		if (m_eDir != null && srcCompare.m_eDir != null) {
			if (m_eDir.compareTo(srcCompare.m_eDir) != 0)
				return false;
		} else if (srcCompare.m_eDir != null)
			return false;

		if (m_eObj != null && srcCompare.m_eObj != null) {
			if (m_eObj.compareTo(srcCompare.m_eObj) != 0)
				return false;
		} else if (srcCompare.m_eObj != null)
			return false;

		return true;
	}

	/**
	 * 
	 * DOCUMENT (perner) - compares this instance of an image to a given image
	 * if only the set values are equal in both images true is returned
	 * null-values are ignored
	 *
	 * @since 20.07.2011 19:18:28
	 *
	 * @param srcCompare
	 * @return true if the images are loose equal
	 */
	public boolean isEqualLooseTo(clsImage srcCompare) {
		
		if (m_eDist != null && srcCompare.m_eDist != null) {
			if (m_eDist.compareTo(srcCompare.m_eDist) != 0)
				return false;
		}

		if (m_eDir != null && srcCompare.m_eDir != null) {
			if (m_eDir.compareTo(srcCompare.m_eDir) != 0)
				return false;
		}

		if (m_eObj != null && srcCompare.m_eObj != null) {
			if (m_eObj.compareTo(srcCompare.m_eObj) != 0)
				return false;
		}

		return true;
	}

	/* (non-Javadoc)
	 *
	 * @since 20.09.2011 14:22:46
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String retval = "";

		retval = "obj: " + m_eObj + " dist: " + m_eDist + " dir: " + m_eDir;
	
		return retval;
	}
	
	

}
