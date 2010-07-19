/**
 * clsWordPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:40
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:40
 * 
 */
public class clsWordPresentation extends clsSecondaryDataStructure{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 20:00:15
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsWordPresentation(String poDataStructureID, eDataType poDataStructureType) {
		super(poDataStructureID, poDataStructureType);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:03:13
	 * 
	 * @see pa.memorymgmt.datatypes.clsSecondaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
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
		//clsWordPresentation oDataStructure = (clsWordPresentation)poDataStructure;
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
//			if(this.moContentName.equals(oDataStructure.moContentName)){
//				if(this.moContent.equals(oDataStructure.moContent)){return 1;}
//			}

		return 0.0;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null) oResult += this.oDataStructureID + ":";
		//oResult += + moContentName +" " + moContent.toString();
		return oResult; 
	}
}
