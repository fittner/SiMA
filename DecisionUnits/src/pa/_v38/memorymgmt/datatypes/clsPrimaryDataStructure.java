/**
 * clsPrimaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 31.05.2010, 16:32:42
 * 
 */



public abstract class clsPrimaryDataStructure extends clsDataStructurePA{
	
	/** this value is used in F49 to mark (Mascherl) drive candidates for primal repression, true = repress; @since 03.05.2012 12:03:35 */
	private boolean mnCandidateForRepression = false;
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:36:00
	 *
	 */
	public clsPrimaryDataStructure(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);  
	}

	/**
	 * @since 03.05.2012 12:03:26
	 * This value is used in F49 to mark (Mascherl) drive candidates for primal repression, true = repress
	 * @param mnCandidateForRepression the mnCandidateForRepression to set
	 */
	public void setCandidateForRepression(boolean mnCandidateForRepression) {
		this.mnCandidateForRepression = mnCandidateForRepression;
	}

	/**
	 * @since 03.05.2012 12:03:26
	 * This value is used in F49 to mark (Mascherl) drive candidates for primal repression, true = repress
	 * @return the mnCandidateForRepression
	 */
	public boolean isCandidateForRepression() {
		return mnCandidateForRepression;
	}
}
