/**
 * clsDriveMesh.java: DecisionUnits - pa.memorymgmt.datatypes
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation{
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.06.2010, 20:37:07
	 *
	 * @param poHomeostaticSource
	 * @param poAssociationID
	 * @param peAssociationType
	 */
	public clsDriveMesh(String poAssociationID, eDataType peAssociationType, ArrayList<clsAssociation> poAssociatedDriveSource) {
		super(poAssociationID, peAssociationType); 
		moContent = poAssociatedDriveSource;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:00:22
	 * 
	 * @see pa.memorymgmt.datatypes.clsPrimaryDataStructure#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
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
	 * 13.07.2010, 20:58:39
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		clsDriveMesh oDataStructure = (clsDriveMesh)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moContent;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(oDataStructure.oDataStructureID!=null){
			if(this.oDataStructureID.equals(oDataStructure.oDataStructureID)){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result.   
				 */
				return oDataStructure.getNumbAssociations();
			}
			else{return 0.0;}
		}
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//Drive Mesh content is represented by a list of attribute associations	
		return getCompareScore(oContentListTemplate, oContentListUnknown);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:38:20
	 *
	 * @return
	 */
	private double getNumbAssociations() {
		return moContent.size();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveMesh oClone = (clsDriveMesh)super.clone();
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
