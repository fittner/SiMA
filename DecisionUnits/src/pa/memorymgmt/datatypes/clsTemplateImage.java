/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
public class clsTemplateImage extends clsPhysicalStructureComposition{
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:41:23
	 *
	 */
	public clsTemplateImage(clsTripple<String, eDataType, String> poDataStructureIdentifier,
			ArrayList<clsAssociation> poAssociatedTemporalStructures) {
		super(poDataStructureIdentifier); 
		 
		moContent = poAssociatedTemporalStructures; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 16:19:32
	 * 
	 * @see pa.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		applyAssociations(oDataStructureList);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:48
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsTemplateImage oDataStructure = (clsTemplateImage)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(oDataStructure.moDataStructureID!=null){
			if(this.moDataStructureID.equals(oDataStructure.moDataStructureID)){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TIs can be associated to data structures that can consist of associated
				 * data structures too (TIs can consist out of TIs).  
				 */
				return oDataStructure.getNumbAssociations();
			}
			else{return 0.0;}
		}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//TI content is represented by a list of temporal associations	
		return getCompareScore(oContentListTemplate, oContentListUnknown); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:36:58
	 *
	 * @return
	 */
	private double getNumbAssociations() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement1 : moContent){
			if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TI){
				oResult +=((clsTemplateImage)((clsAssociation)oElement1).moAssociationElementB).getNumbAssociations(); 
			}
			else {
				oResult += 1.0; 
			}
		}
	return oResult;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsTemplateImage oClone = (clsTemplateImage)super.clone();
        	if (moContent != null) {
        		oClone.moContent = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
          	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	

	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		if(this.moDataStructureID != null){oResult += this.moDataStructureID + ":";}
			
		for (clsAssociation oEntry : moContent) {
			oResult += oEntry.toString() + ":"; 
		}
		return oResult; 
	}
}
