/**
 * CHANGELOG
 *
 * 27.07.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;

/**
 * A mesh of >=1 word presentations. If a word presentation is a word, then the word presentation is a sentence 
 * 
 * @author wendt
 * 27.07.2011, 13:41:04
 * 
 */
public class clsWordPresentationMesh extends clsLogicalStructureComposition {

	//private String moContent = "UNDEFINED";
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.07.2011 20:59:03
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsWordPresentationMesh(
			clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, 
			ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
		super(poDataStructureIdentifier);
		
		moContent = (String)poContent;
		setAssociations(poAssociatedStructures);
		
		// TODO (wendt) - Auto-generated constructor stub
	}

//	public String getMoContent() {
//		return moContent;
//	}
//
//	/**
//	 * @author zeilinger
//	 * 17.03.2011, 00:52:49
//	 * 
//	 * @param moContent the moContent to set
//	 */
//	public void setMoContent(String moContent) {
//		this.moContent = moContent;
//	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:12:06
	 *
	 * @param poAssociatedTemporalStructures
	 */
	private void setAssociations(
			ArrayList<clsAssociation> poAssociatedStructures) {
		moInternalAssociatedContent = poAssociatedStructures;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#contain(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public boolean contain(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return false;
	}
	
	/**
	 * Find all data structures, which are connected with a certain predicate
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:06:20
	 *
	 * @param poPredicate
	 * @return
	 */
	public ArrayList<clsSecondaryDataStructure> findDataStructure(ePredicate poPredicate, boolean pbStopAtFirstMatch) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		
		
		for (clsAssociation oAss : this.moInternalAssociatedContent) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate)==true) {
					oRetVal.add((clsSecondaryDataStructure) oAss.getTheOtherElement(this));
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
 		}
		
		if (oRetVal.size()==0 || pbStopAtFirstMatch==false) {
			for (clsAssociation oAss : this.moExternalAssociatedContent) {
				if (oAss instanceof clsAssociationSecondary) {
					if (((clsAssociationSecondary)oAss).getMoPredicate().equals(poPredicate)==true) {
						oRetVal.add((clsSecondaryDataStructure) oAss.getTheOtherElement(this));
						
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				}
	 		}
		}
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	@Override
//	public Object clone() throws CloneNotSupportedException {
//        try {
//        	clsWordPresentationMesh oClone = (clsWordPresentationMesh)super.clone();
//        	if (moInternalAssociatedContent != null) {
//        		oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moInternalAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//        	if (moExternalAssociatedContent != null) {
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
		
		clsWordPresentationMesh oClone = null;
		
		try {
			//Clone the data structure without associated content. They only exists as empty lists
			oClone = (clsWordPresentationMesh)super.clone();
			oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>();
			oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>();
			//Add this structure and the new clone to the list of cloned structures
			poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
			
			//Go through all associations
			if (moInternalAssociatedContent != null) {
				//Add internal associations to oClone 
        		for(clsAssociation oAssociation : moInternalAssociatedContent){
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
        		for(clsAssociation oAssociation : moExternalAssociatedContent){
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
			oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
			
			//Add by AW
			if (this.moContentType.equals(eContentType.RI) || this.moContentType.equals(eContentType.PI)) {
				oResult += "\nINTERNAL ASSOCIATED CONTENT\n";
				for (clsAssociation oEntry : this.moInternalAssociatedContent) {
					oResult += oEntry.getLeafElement().toString() + ","; 
				}
				
				oResult += "\nEXTERNAL ASSOCIATED CONTENT\n";
				for (clsAssociation oEntry : moExternalAssociatedContent) {
					oResult += oEntry.toString() + ","; 
				}
			}
			
			return oResult; 
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 18.06.2012, 16:38:20
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		return moInternalAssociatedContent.size();
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 18.06.2012, 16:38:20
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		return moExternalAssociatedContent.size();
	}
	
	/**
	 * Check if the WPM is a null object
	 * 
	 * (wendt)
	 *
	 * @since 21.07.2012 20:49:30
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

}
