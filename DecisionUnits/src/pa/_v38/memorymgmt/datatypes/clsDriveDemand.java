/**
 * clsDriveDemand.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.enums.eDataType;

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
		oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
		
		return oResult; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 15.08.2010, 12:15:44
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsHomeostaticRepresentation#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}