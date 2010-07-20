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
	public clsDriveDemand(String poAssociationID, eDataType peAssociationType, double pnDriveDemandIntensity) {
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
		//clsDriveDemand oDataStructure = (clsDriveDemand)poDataStructure;
	
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
//			if(this.moContentName.equals(oDataStructure.moContentName)){
//				if(this.moContent.equals(oDataStructure.moContent)){return 1;}
//			}

		return 0.0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
           	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
	@Override
	public String toString(){
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null) oResult += this.oDataStructureID + ":";
		//oResult += moContentName +" " + moContent.toString();
		return oResult; 
	}
}
