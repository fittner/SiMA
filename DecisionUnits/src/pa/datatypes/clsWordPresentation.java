/**
 * clsWordPresentation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:16:20
 */
package pa.datatypes;

import pa.interfaces.itfSecondaryProcessComparableWP;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:16:20
 * 
 */
@Deprecated
public class clsWordPresentation extends clsPsychicRepresentative implements itfSecondaryProcessComparableWP, Cloneable {

	public String moContentName;
	public String moContentType = "";
	public Object moContent = null;
	

	public clsWordPresentation() {

	}
	
	/**
	 * just takes the values from the thin presentation --> 
	 * remove this when you know what the difference is (TP/WP) except of the associations!
	 * 
	 * @author langr
	 * 24.10.2009, 18:31:01
	 *
	 * @param poTP
	 */
	public clsWordPresentation(clsThingPresentationSingle poTP) {
		
		moContent = poTP.moContent;
		moContentType = poTP.meContentType;
		moContentName = poTP.meContentName;
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:44:02
	 * 
	 * @see pa.interfaces.itfSecondaryProcessComparableWP#compareTo(pa.datatypes.clsWordPresentation)
	 */
	@Override
	public int compareTo(clsWordPresentation poWordPresentation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsWordPresentation oClone = (clsWordPresentation)super.clone();
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
