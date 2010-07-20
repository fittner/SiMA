/**
 * clsThingPresentation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 */
package pa.memorymgmt.datatypes;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:48:16
 * 
 */
public class clsThingPresentation extends clsPhysicalRepresentation{
	
	public String moContentName = "";
	public Object moContent = null;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:43:50
	 *
	 * @param poWordPresentationAssociation
	 */
	public clsThingPresentation(String poDataStructureID,
								eDataType poDataStructureType, 
								String poContentName,
								Object poContent) {
		
		super(poDataStructureID, poDataStructureType);
		
		moContentName = poContentName; 
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
		clsThingPresentation oDataStructure = (clsThingPresentation)poDataStructure;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(oDataStructure.oDataStructureID!=null){
			if(this.oDataStructureID.equals(oDataStructure.oDataStructureID)){return 1.0;}
			else{return 0.0;}
		}
			
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
			if(this.moContentName.equals(oDataStructure.moContentName)){
				if(this.moContent.equals(oDataStructure.moContent)){return 1;}
			}
		return 0;
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
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null) oResult += this.oDataStructureID + ":";
		oResult += moContentName +":" + moContent.toString();
		return oResult; 
	}
}
