/**
 * clsAct.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 * 
 */
public class clsAct extends clsSecondaryDataStructure {
	public ArrayList<clsAssociation> moContent; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 20:01:13
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsAct(String poAssociationID,eDataType peAssociationType, ArrayList<clsAssociation> poAssociatedWordPresentations) {
		super(poAssociationID, peAssociationType);
		moContent = poAssociatedWordPresentations;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:03:23
	 * 
	 * @see pa.memorymgmt.datatypes.clsSecondaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation dataStructureAssociation) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
		
	protected void applyAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moContent.addAll(poAssociatedDataStructures); 
	}


	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:57:48
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsAct oDataStructure = (clsAct)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(oDataStructure.oDataStructureID!=null){
			if(this.oDataStructureID.equals(oDataStructure.oDataStructureID)){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as ACTs can be associated to different types of data structures that can consist of associated
				 * data structures too (ACTs can consist out of ACTs).  
				 */
				return oDataStructure.getNumbAssociations();
			}
			else{return 0.0;}
		}
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//ACT content is represented by a list of attribute associations	
		return getCompareScore(oContentListTemplate, oContentListUnknown);
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:44:40
	 *
	 * @return
	 */
	private double getNumbAssociations() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement1 : moContent){
			if(((clsAssociation)oElement1).moAssociationElementB.oDataStructureType == eDataType.ACT){
				oResult +=((clsAct)((clsAssociation)oElement1).moAssociationElementB).getNumbAssociations(); 
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
        	clsAct oClone = (clsAct)super.clone();
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

