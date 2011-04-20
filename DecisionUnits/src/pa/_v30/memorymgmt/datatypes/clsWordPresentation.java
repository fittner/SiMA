/**
 * clsWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:40
 */
package pa._v30.memorymgmt.datatypes;

import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:40
 * 
 */
public class clsWordPresentation extends clsSecondaryDataStructure{
	
	private String moContent = "UNDEFINED"; 
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

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 20:00:15
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsWordPresentation(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, Object poContent) {
		super(poDataStructureIdentifier);
		moContent = (String)poContent; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:59:07
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		clsWordPresentation oDataStructure = (clsWordPresentation)poDataStructure;
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		
		if(this.moDS_ID == oDataStructure.moDS_ID){oRetVal = 1.0;}
		else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
		
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
			oRetVal = 1.0; 
		}
		return oRetVal;
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
	
	@Override
	public String toString(){
			String oResult = "::"+this.moDataStructureType+"::";  
			oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
			
			return oResult; 
	}
}
