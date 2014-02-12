/**
 * clsSecondaryDataStructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:42:44
 */
package base.datatypes;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;

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
	public String getContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:49
	 * 
	 * @param moContent the moContent to set
	 */
	public void setContent(String moContent) {
		this.moContent = moContent;
	}
	
	public clsSecondaryDataStructure(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
	}
	
	/**
	 * Check if two instances, which are not the same instance are the same
	 *
	 * @author wendt
	 * @since 08.10.2013 10:14:28
	 *
	 * @param ds
	 * @return
	 */
	public <E extends clsSecondaryDataStructure> boolean isEquivalentDataStructure(E ds) {
	    boolean isEqual = false;
	    
	    if (ds.getClass().getName().equals(this.getClass().getName()) &&
	        ds.getDS_ID()==this.moDS_ID &&
	        ds.getDSInstance_ID() == this.moDSInstance_ID &&
	        ds.getContent()==this.getContent() &&
	        ds.getContentType()==this.getContentType()) {
	        isEqual=true;
	    }
	    
	    return isEqual;
	}
}
