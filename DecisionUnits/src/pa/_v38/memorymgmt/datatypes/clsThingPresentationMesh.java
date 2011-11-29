/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - The term Thing Presentation Mesh (TPM) describes a mesh of TPs which are connected via attribute associations. 
 * 	The term TPM is introduced to the technical model only and does not occur in psychoanalytic theory. 
 * 
 * moContent (String) 
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class
 * 				poAssociatedPhysicalRepresentations (ArrayList)	@@@Bug – ArrayList must be parameterized with objects of type clsAssociationAttributes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	private String moContent = "UNDEFINED";
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:29
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:29
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilingere
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh(clsTriple<Integer, eDataType, String> poDataStructureIdentifier,
									ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
									String poContent) {
		
		super(poDataStructureIdentifier);
		setAssociations(poAssociatedPhysicalRepresentations); 
		setContent(poContent); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:10:28
	 *
	 * @param poContent
	 */
	private void setContent(String poContent) {
		if(poContent != null){
			moContent = poContent;
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:10:26
	 *
	 * @param poAssociatedPhysicalRepresentations
	 */
	private void setAssociations(ArrayList<clsAssociation> poAssociatedPhysicalRepresentations) {
		moAssociatedContent = poAssociatedPhysicalRepresentations; 
	}

	/**
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:46:07
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		addAssociations(oDataStructureList);
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
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}
		
		clsThingPresentationMesh oDataStructure = (clsThingPresentationMesh)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moAssociatedContent;
				
    	//This if statement proofs if the compared data structure does already have an ID =>
		//the ID specifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TPMs can be associated to different types of data structures that can consist of associated
				 * data structures too (TPMs can consist out of TPMs).  
				 */
				oRetVal = oDataStructure.getNumbAssociations();
		}
		/*Each saved CAKE or other individual shall have an own ID. Here, the ID is treated as a type ID, which makes it
		 *impossible to compare individuals */
		else if (oDataStructure.moDS_ID > -1){	return oRetVal;	}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined.
		//TPM content is represented by a list of attribute associations
		//TODO HZ Define why there is no if statement regarding the content!
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
			//added by AW: If there are no associations in the TPM, then only content type and content makes a 1.0 equality
			//FIXME AW: Also the EMPTYSPACE shall have som properties
			if ((oContentListTemplate.isEmpty()==true) && (oContentListUnknown.isEmpty()==true)) {
				oRetVal=1.0;
			} else {
				oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
			}
			
		}
		else if (this.moContentType.intern() == poDataStructure.moContentType.intern()){
			oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
		}
		
		//Special case, if the TPM is empty	
		
			
		return oRetVal; 
	}
	
	public double compareToMethod2(clsDataStructurePA poDataStructure) {
		double rRetVal = 0;
		//1. Types has to be equal
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return rRetVal;}
		//2. If the TYPE-ID is the same, then there is a full match 1.0. If the ID matches, the intrinsic properties are automatically equal
		if(this.moDS_ID == poDataStructure.moDS_ID){rRetVal = 1.0;}
		//3. Compare similarity
		
		return rRetVal;
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
			for(clsDataStructurePA oElement1 : moAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	
	@Override
	public boolean contain(clsDataStructurePA poDataStructure){
		
		for(clsAssociation oAssociation : this.moAssociatedContent){
			if((String)oAssociation.moAssociationElementB.moContentType.intern() == poDataStructure.moContentType.intern()){
				return true;
			}
		}
		
		return false; 
	}
	
		
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
        	if (moAssociatedContent != null) {
        		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moExternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
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
		//oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
		oResult += this.moContentType + ":" + this.moContent;
		
//		for (clsAssociation oEntry : moAssociatedContent) {
//			oResult += oEntry.toString() + ":"; 
//		}
		
		return oResult; 
	}
}
