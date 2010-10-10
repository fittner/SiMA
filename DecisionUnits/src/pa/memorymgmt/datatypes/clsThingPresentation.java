/**
 * clsThingPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 */
package pa.memorymgmt.datatypes;

import java.awt.Color;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 * 
 */
public class clsThingPresentation extends clsPhysicalRepresentation{
	
	public Object moContent = null;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:43:50
	 *
	 * @param poWordPresentationAssociation
	 */
	public clsThingPresentation(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, Object poContent) {
		
		super(poDataStructureIdentifier);
		moContent = poContent;
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:55
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0;
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}
		
		clsThingPresentation oDataStructure = (clsThingPresentation)poDataStructure;
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
			
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		
		//HZ 16.08.2010 Be careful - as their are upper-case and lower-case issues within the simulator 
		//				and in between the simulator and the ontology regarding the current naming 
		//				of SymbolTypes, the content types are compared without case sensitivity. 
		if(moContentType.intern() == oDataStructure.moContentType.intern()){
							
				if(this.moContent instanceof Boolean && oDataStructure.moContent instanceof Boolean) {
					oRetVal = compare((Boolean)this.moContent, (Boolean)oDataStructure.moContent );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Color ) {
					oRetVal = compare((String)this.moContent, (String)"#"+Integer.toString(((Color)oDataStructure.moContent).getRGB() & 0xffffff, 16).toUpperCase() );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Boolean ) {
					oRetVal = compare((Boolean) Boolean.parseBoolean(this.moContent.toString()), (Boolean)oDataStructure.moContent );
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof Enum) {
					oRetVal = compare((String)this.moContent, ((Enum<?>)oDataStructure.moContent).name());
				}
				else if(this.moContent instanceof String && oDataStructure.moContent instanceof String) {
					oRetVal = compare((String)this.moContent, (String)oDataStructure.moContent);
				}
		}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 17.08.2010, 10:57:28
	 *
	 * @param moContent2
	 * @param string
	 * @return
	 */
	private double compare(String a, String b) {
		double oRetVal = 0; 
		
		if( a.intern() == b.intern() ){
			oRetVal = 1.0;
		}
		
		return oRetVal; 
	}
	
	private double compare (Boolean a, Boolean b){
		double oRetVal = 0; 
		
		if( a.booleanValue() == b.booleanValue() ){
			oRetVal = 1;
		}
		
		return oRetVal; 
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentation oClone = (clsThingPresentation)super.clone();
        	
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
