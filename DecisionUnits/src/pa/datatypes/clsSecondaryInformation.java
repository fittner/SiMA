/**
 * clsSecondaryInformation.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 11.08.2009, 11:48:35
 */
package pa.datatypes;

import pa.interfaces.itfSecondaryProcessComparable;
import pa.interfaces.itfSecondaryProcessComparableWP;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 11:48:35
 * 
 */
public class clsSecondaryInformation extends clsPrimaryInformation implements itfSecondaryProcessComparable, itfSecondaryProcessComparableWP, Cloneable {

	public clsWordPresentation moWP;
	
	public clsSecondaryInformation(clsWordPresentation poWP, clsThingPresentationSingle poTP, clsAffect poAffect) {
		super(poTP, poAffect);
		moWP = poWP;
	}
	
	//transforms a primary information into a secondary information - stupid copy of content from Thin->WordPresentation
	public clsSecondaryInformation(clsPrimaryInformation poPrim) {
		this(new clsWordPresentation(poPrim.moTP), poPrim.moTP, poPrim.moAffect);
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:27:47
	 * 
	 * @see pa.interfaces.itfSecondaryProcessComparable#compareTo(pa.datatypes.clsSecondaryInformation)
	 */
	@Override
	public int compareTo(clsSecondaryInformation poSecondaryInformation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 12:44:36
	 * 
	 * @see pa.interfaces.itfSecondaryProcessComparableWP#compareTo(pa.datatypes.clsWordPresentation)
	 */
	@Override
	public int compareTo(clsWordPresentation poWordPresentation) {
		// TODO (langr) - Auto-generated method stub
		return 0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException{
        try {
        	clsSecondaryInformation oClone = (clsSecondaryInformation)super.clone();
        	oClone.moWP = (clsWordPresentation)moWP.clone();   	
        	return oClone;
        } catch (CloneNotSupportedException e) {
            return e;
        }
		
	}	

}
