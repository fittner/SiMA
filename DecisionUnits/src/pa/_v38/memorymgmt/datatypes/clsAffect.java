/**
 * clsAffect.java: DecisionUnits - pa._v38.memorymgmt.datatypes
 * 
 * @author zeilinger
 * 14.08.2010, 16:11:11
 */
package pa._v38.memorymgmt.datatypes;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - In ARSi10, the affect defines the temporal change of the drive demand. 
 * 
 * moContent (double):	moContent defines the temporal change of a specific demand (pleasure)
 * mrMinVal
 * mrMaxVal
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as 
 * 							the content type. It is passed on to the super class
 * 
 * 
 * @author zeilinger
 * 14.08.2010, 16:11:11
 * 
 */
public class clsAffect extends clsHomeostaticRepresentation{
	private double moContent = 0.0; 
	private double mrMinVal = 0.0; 
	private double mrMaxVal = 0.0; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 14.08.2010, 16:11:37
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsAffect(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, double prPleasure) {
		super(poDataStructureIdentifier);
		moContent = prPleasure; 
	}
	
	public double getPleasure(){
		return moContent; 
	}
	
	public void setMinVal(double prMinVal){
		mrMinVal = prMinVal; 		
	}
	
	public void setMaxVal(double prMaxVal){
		mrMaxVal = prMaxVal; 		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.08.2010, 16:11:34
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0; 
		clsAffect oDataStructure = (clsAffect)poDataStructure;
				
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result.   
				 */
				oRetVal = 1.0;
		}
		else if (oDataStructure.moDS_ID > -1) {return oRetVal;} 
		
		if(this.mrMinVal <= oDataStructure.moContent && oDataStructure.moContent < this.mrMaxVal){
			oRetVal = 1.0; 
		}
		
		if(this.mrMaxVal == 1.0 && oDataStructure.moContent == 1.0){
			oRetVal = 1.0; 
		}
		
		return oRetVal; 
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsAffect oClone = (clsAffect)super.clone();
        	
         	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.08.2010, 16:11:34
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsHomeostaticRepresentation#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
