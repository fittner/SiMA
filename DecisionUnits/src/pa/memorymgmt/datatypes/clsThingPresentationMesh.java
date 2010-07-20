/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilingere
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh(String poDataStructureID,
			 						eDataType poDataStructureType,
									ArrayList<clsAssociation> poAssociatedPhysicalRepresentations) {
		
		super(poDataStructureID,poDataStructureType);
		moContent = poAssociatedPhysicalRepresentations;  
	}
	
									 /**
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:46:07
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
	* 13.07.2010, 20:59:01
	* 
	* @see java.lang.Comparable#compareTo(java.lang.Object)
	*/
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsThingPresentationMesh oDataStructure = (clsThingPresentationMesh)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent;
		
		//This if statement proofs if the compared data structure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(oDataStructure.oDataStructureID!=null){
			if(this.oDataStructureID.equals(oDataStructure.oDataStructureID)){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TPMs can be associated to different types of data structures that can consist of associated
				 * data structures too (TPMs can consist out of TPMs).  
				 */
				return oDataStructure.getNumbAssociations();
			}
			else{return 0.0;}
		}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined.
		//TPM content is represented by a list of attribute associations	
		return getCompareScore(oContentListTemplate, oContentListUnknown);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:12:00
	 *
	 * @return
	 */
	public double getNumbAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moContent){
				if(((clsAssociation)oElement1).moAssociationElementB.oDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbAssociations(); 
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
        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
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
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null) oResult += this.oDataStructureID + ":";
			
		for (clsAssociation oEntry : moContent) {
			oResult += oEntry.toString() + " / "; 
		}
		
		if (oResult.length() > 4) {
			oResult = oResult.substring(0, oResult.length()-3);
		}
		return oResult; 
	}
}
