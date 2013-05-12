/**
 * clsSecondaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 * 
 */
public abstract class clsSecondaryDataStructure extends clsDataStructurePA{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 19:59:46
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	
	protected String moContent = "UNDEFINED"; 
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:49
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:49
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}
	
	public clsSecondaryDataStructure(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
	}
}
