/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - Class clsTemplateImage is filled with associations from the type clsTemporalAssociation. It merges TPMs and TPs 
 * 						which are occur at the same time (Associated through time).
 * 
 * moContent (String) 
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class
 * poAssociatedTemporalStructures (ArrayList):	List of temporal associations which combine objects of type TP, TPM, or TI to a template Image
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * @deprecated
 */
public class clsTemplateImage extends clsPhysicalStructureComposition {
	private String moContent = "UNDEFINED";	
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:51:46
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:51:46
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 12:41:23
	 *
	 */
	public clsTemplateImage(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			ArrayList<clsAssociation> poAssociatedTemporalStructures,
			String poContent) {
		super(poDataStructureIdentifier); 
		
		setAssociations(poAssociatedTemporalStructures); 
		setContent(poContent); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:12:08
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
	 * 16.08.2010, 22:12:06
	 *
	 * @param poAssociatedTemporalStructures
	 */
	
	public void setAssociations(
			ArrayList<clsAssociation> poAssociatedTemporalStructures) {
		moInternalAssociatedContent = poAssociatedTemporalStructures;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 16:19:32
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
	 * 13.07.2010, 20:58:48
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		/* Comparisons
		 * 1. Unequal DataStructureType return 0
		 * 2. Equal ID, return number of associations. If the data structure is equal the return value >= 1
		 * ** DISABLED ** 3. If ID exists and is unequal -> return 0
		 * 4. Equal content return match score of the associations
		 * 5. Equal content type return match score of the associations
		 */
		double oRetVal = 0.0; // equal to no match at all
		//1.
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		//2. 
		clsTemplateImage oDataStructure = (clsTemplateImage)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moInternalAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moInternalAssociatedContent;
				
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/* In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TIs can be associated to data structures that can consist of associated
				 * data structures too (TIs can consist out of TIs).  
				 */
				oRetVal = oDataStructure.getNumbInternalAssociations();
		}
		//3.
		else if (oDataStructure.moDS_ID > -1) {
			/*Each saved CAKE or other individual shall have an own ID. Here, the ID is treated as a type ID, which makes it
			 *impossible to compare individuals */
			return oRetVal;
		}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//TI content is represented by a list of temporal associations	
	
		//4.
		else if(this.moContent.intern() == oDataStructure.moContent.intern()){
				oRetVal = getMatchScore(this, oDataStructure);
		}
		//5.
		else if (this.moContentType == poDataStructure.moContentType){
				oRetVal = getMatchScore(this, oDataStructure);
		}
		
		return oRetVal;  
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:36:58
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement1 : moInternalAssociatedContent){
			if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TI){
				oResult +=((clsTemplateImage)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
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
	 * 18.07.2010, 16:36:58
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement1 : moExternalAssociatedContent){
			if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TI){
				oResult +=((clsTemplateImage)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
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
        	if (moInternalAssociatedContent != null) {
        		oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moInternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>()); 
    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
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
		oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent + "|";
			
//		for (clsAssociation oEntry : moAssociatedContent) {
//			oResult += oEntry.toString() + ":"; 
//		}
		return oResult; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:59:51
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsPhysicalStructureComposition#contain(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public boolean contain(clsDataStructurePA poDataStructure) {
		
		for(clsAssociation oAssociation : this.moInternalAssociatedContent){
			if(oAssociation.moAssociationElementB.compareTo(poDataStructure)> 0.0){
				return true;
			}
		}
		
		return false;
	}

}
