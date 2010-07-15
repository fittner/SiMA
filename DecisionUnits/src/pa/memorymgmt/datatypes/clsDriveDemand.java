/**
 * clsDriveDemand.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 * 
 */
public class clsDriveDemand extends clsDataStructurePA{
	public double mnDriveDemandIntensity; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:24:07
	 *
	 */
	public clsDriveDemand(double pnDriveDemandIntensity, String poAssociationID, eDataType peAssociationType) {
		super(poAssociationID, peAssociationType); 
		mnDriveDemandIntensity = pnDriveDemandIntensity; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:32
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsDriveDemand oDataStructure = (clsDriveDemand)poDataStructure;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs. 
		if(oDataStructure.oDataStructureID != null){
			if(compareDataStructureID(oDataStructure))return 9999; 
			else return 0; 
		}
		else{
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
//			if(this.moContentName.equals(oDataStructure.moContentName)){
//				if(this.moContent.equals(oDataStructure.moContent)){return 1;}
//			}
		}
		return 0;
	}
}
