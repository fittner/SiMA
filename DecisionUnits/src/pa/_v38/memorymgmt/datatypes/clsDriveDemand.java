/**
 * clsDriveDemand.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 */
package pa._v38.memorymgmt.datatypes;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - The drive demand is received from the homeostasis and represents its unbalance. The temporal change of the drive demand results in the affect (see clsAffect). 
 * 
 * moContent (double): Holds the homeostatic tension
 * poDataStructureIdentifier (clsTripple): Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:24
 * 
 */

@Deprecated
public class clsDriveDemand extends clsHomeostaticRepresentation{
	private double moContent = 0.0; 
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:24:07
	 *@deprecated
	 */
	public clsDriveDemand(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, double prDemandTension) {
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
