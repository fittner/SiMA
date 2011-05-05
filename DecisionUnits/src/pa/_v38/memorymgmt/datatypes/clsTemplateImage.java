/**
 * clsTemplateImage.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:49:45
 * 
 */
public class clsTemplateImage extends clsPhysicalStructureComposition{
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
	public clsTemplateImage(clsTripple<Integer, eDataType, String> poDataStructureIdentifier,
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
	private void setAssociations(
			ArrayList<clsAssociation> poAssociatedTemporalStructures) {
		moAssociatedContent = poAssociatedTemporalStructures;
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
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		clsTemplateImage oDataStructure = (clsTemplateImage)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moAssociatedContent;
				
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TIs can be associated to data structures that can consist of associated
				 * data structures too (TIs can consist out of TIs).  
				 */
				oRetVal = oDataStructure.getNumbAssociations();
		}
		else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//TI content is represented by a list of temporal associations	
	
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
				oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
		}
		else if (this.moContentType.intern() == poDataStructure.moContentType.intern()){
				oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
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
	private double getNumbAssociations() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement1 : moAssociatedContent){
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
		
		for(clsAssociation oAssociation : this.moAssociatedContent){
			if(oAssociation.moAssociationElementB.compareTo(poDataStructure)> 0.0){
				return true;
			}
		}
		
		return false;
	}

}
