/**
 * clsDriveDemand.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 * 
 */
public class clsDriveDemand extends clsHomeostaticRepresentation{
	private double moContent = 0.0; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:24:07
	 *
	 */
	public clsDriveDemand(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, double prDemandTension) {
		super(poDataStructureIdentifier); 
		moContent = prDemandTension; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:32
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public double getTension(){
		return moContent; 
	}
	
	public void setTension(double prDemandTension){
		moContent = prDemandTension; 
	}
	
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		throw new NoSuchMethodError("Method compareTo should not be invoked for objects of datatype clsDriveDemand");
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
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":";
		//oResult += moContentName +" " + moContent.toString();
		return oResult; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 15.08.2010, 12:15:44
	 * 
	 * @see pa.memorymgmt.datatypes.clsHomeostaticRepresentation#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
