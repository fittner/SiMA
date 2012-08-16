/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
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
	public clsThingPresentationMesh(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
									ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
									String poContent) {
		
		super(poDataStructureIdentifier);
		moInternalAssociatedContent = poAssociatedPhysicalRepresentations; 
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
	
	/*public void setAssociations(ArrayList<clsAssociation> poAssociatedPhysicalRepresentations) {
		moAssociatedContent = poAssociatedPhysicalRepresentations; 
	}*/

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
		
		addInternalAssociations(oDataStructureList);
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
		ArrayList <clsAssociation> oContentListTemplate = this.moInternalAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moInternalAssociatedContent;
		
		// a TPM-entity may have attributes of different importance. e.g. color and shape should be more important than position and distance (which should not be internal attributes, since they do not identify the TPM)
				
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
				oRetVal = 1.0; //oDataStructure.getNumbInternalAssociations();
				return oRetVal;
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
		else if (this.moContentType  == poDataStructure.moContentType ){
			oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
		}
		else if(this.moContentType  == eContentType.RI & poDataStructure.moContentType == eContentType.PI) {
			oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
		}
		//Special case, if the TPM is empty	
		
			
		return oRetVal; 
	}
	
//	public double compareToMethod2(clsDataStructurePA poDataStructure) {
//		double rRetVal = 0;
//		//1. Types has to be equal
//		if(this.moDataStructureType != poDataStructure.moDataStructureType){return rRetVal;}
//		//2. If the TYPE-ID is the same, then there is a full match 1.0. If the ID matches, the intrinsic properties are automatically equal
//		if(this.moDS_ID == poDataStructure.moDS_ID){rRetVal = 1.0;}
//		//3. Compare similarity
//		
//		return rRetVal;
//	}
	
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 8.08.2012, 16:12:00
	 *
	 * @return
	 */
//	public ArrayList<clsThingPresentation> getAssociatedThingPresentations() {
//		ArrayList<clsThingPresentation> oResult = new ArrayList<clsThingPresentation>();
//			for(clsAssociation oIntAss: this.moInternalAssociatedContent) {
//				try {
//					oResult.add((clsThingPresentation)oIntAss.getMoAssociationElementB());
//				}
//				catch (Exception e) {
//					
//				}
//			}
//		return oResult;
//	}
	
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:12:00
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moInternalAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:12:00
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moExternalAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	@Override
	public boolean contain(clsDataStructurePA poDataStructure){
		
		for(clsAssociation oAssociation : this.moInternalAssociatedContent){
			if(oAssociation.moAssociationElementB.moContentType == poDataStructure.moContentType ){
				return true;
			}
		}
		
		return false; 
	}
	
	/**
	 * Check if this object is a null object
	 * 
	 * (wendt)
	 *
	 * @since 21.07.2012 20:48:57
	 *
	 * @return
	 */
	public boolean isNullObject() {
		boolean bResult = false;
		
		if (this.getMoContentType()==eContentType.NULLOBJECT) {
			bResult=true;
		}
		
		return bResult;
	}
	
		
	@Override
//	public Object clone() throws CloneNotSupportedException {
//        try {
//        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
//        	if (moAssociatedContent != null) {
//        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//        	if (moAssociatedContent != null) {
//        		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moExternalAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//          	return oClone;
//        } catch (CloneNotSupportedException e) {
//           return e;
//        }
//	}
	
	/**
	 * Alternative clone for cloning directed graphs
	 * 
	 * (wendt)
	 *
	 * @since 01.12.2011 16:29:38
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
	//public Object cloneGraph() throws CloneNotSupportedException {
		return clone(new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>());
	}
	
	/**
	 * Alternative clone for cloning directed graphs. This function adds cloned objects to a list and considers
	 * that loops may occur
	 * 
	 * (wendt)
	 *
	 * @since 01.12.2011 16:29:58
	 *
	 * @param poNodeList
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
		
		clsThingPresentationMesh oClone = null;
		
		try {
			//Clone the data structure without associated content. They only exists as empty lists
			oClone = (clsThingPresentationMesh)super.clone();
			oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>();
			oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>();
			//Add this structure and the new clone to the list of cloned structures
			poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
			
			//Go through all associations
			if (moInternalAssociatedContent != null) {
				//Add internal associations to oClone 
        		for(clsAssociation oAssociation : this.moInternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
						
			//Go through all associations
			if (moExternalAssociatedContent != null) {
				//Add internal associations to oClone 
        		for(clsAssociation oAssociation : this.moExternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
			
		} catch (CloneNotSupportedException e) {
           return e;
        }
		
		return oClone;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moContentType + ":" + this.moContent;
		
		//Add by AW
		if (this.moContentType.equals(eContentType.RI.toString()) || this.moContentType.equals(eContentType.PI.toString())) {
			oResult += "\nINTERNAL ASSOCIATED CONTENT\n";
			for (clsAssociation oEntry : moInternalAssociatedContent) {
				oResult += oEntry.getLeafElement().toString() + ","; 
			}
			
			oResult += "\nEXTERNAL ASSOCIATED CONTENT\n";
			for (clsAssociation oEntry : moExternalAssociatedContent) {
				oResult += oEntry.toString() + ","; 
			}
		}
		
		return oResult; 
	}
}
