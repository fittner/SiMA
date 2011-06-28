/**
 * clsAct.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 */
package pa._v38.memorymgmt.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.enums.eActState;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:43:47
 * 
 */
public class clsAct extends clsSecondaryDataStructure {
	
	private ArrayList<clsWordPresentation> m_alWordpresentations;
	
	private String moContent = "UNDEFINED"; 
	private ArrayList<clsSecondaryDataStructure> moAssociatedContent; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 20:01:13
	 *
	 * @param poDataStructureName
	 * @param poDataStructureType
	 */
	public clsAct(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, 
														ArrayList<clsSecondaryDataStructure> poAssociatedWordPresentations,
														String poContent) {
		super(poDataStructureIdentifier);
		setAssociatedWP(poAssociatedWordPresentations); 
		setContent(poContent); 
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:47:55
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:47:55
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:47:56
	 * 
	 * @return the moAssociatedContent
	 */
	public ArrayList<clsSecondaryDataStructure> getMoAssociatedContent() {
		return moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:47:56
	 * 
	 * @param moAssociatedContent the moAssociatedContent to set
	 */
	public void setMoAssociatedContent(
			ArrayList<clsSecondaryDataStructure> moAssociatedContent) {
		this.moAssociatedContent = moAssociatedContent;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:16:54
	 *
	 * @param poContent
	 */
	private void setContent(String poContent) {
		if(poContent!= null){
			moContent = poContent; 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:16:52
	 *
	 * @param poAssociatedWordPresentations
	 */
	private void setAssociatedWP(ArrayList<clsSecondaryDataStructure> poAssociatedWordPresentations) {
		moAssociatedContent = poAssociatedWordPresentations;
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
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		clsAct oDataStructure = (clsAct)poDataStructure;
		
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as ACTs can be associated to different types of data structures that can consist of associated
				 * data structures too (ACTs can consist out of ACTs).  
				 */
				oRetVal = oDataStructure.getNumbAssociatedDS();
			}
		else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//ACT content is represented by associated WPs (May be deleted in a future version)and a String
		//variable that defines PRECONDITIONS, ACTION, and CONSEQUENCE - both Strings are compared to each other
		
		//if(this.moContent.intern() == oDataStructure.moContent.intern()){
		//oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
		//}
		oRetVal = getMatchScore(this.moContent, oDataStructure.moContent); 
		return oRetVal; 
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 29.08.2010, 13:16:17
	 *
	 * @param moContent2
	 * @param moContent3
	 * @return
	 */
	private double getMatchScore(String poContentKnown, String poContentUnknown) {
		double nMatchScore = 0.0; 
		String oPreconditionKnown = poContentKnown.substring(poContentKnown.indexOf("|", poContentKnown.indexOf(eActState.PRECONDITION.name())), poContentKnown.indexOf(eActState.ACTION.name())); 
		String oActionKnown = poContentKnown.substring(poContentKnown.indexOf("|", poContentKnown.indexOf(eActState.ACTION.name())), poContentKnown.indexOf(eActState.CONSEQUENCE.name())); 
		String oConsequenceKnown = poContentKnown.substring(poContentKnown.indexOf("|", poContentKnown.indexOf(eActState.CONSEQUENCE.name()))); 
		
		String [] oPreconditionUnknown = poContentUnknown.substring(poContentUnknown.indexOf("|", poContentUnknown.indexOf(eActState.PRECONDITION.name())) + 1, poContentUnknown.indexOf(eActState.ACTION.name())).split("[|]"); 
		String [] oActionUnknown = poContentUnknown.substring(poContentUnknown.indexOf("|", poContentUnknown.indexOf(eActState.ACTION.name())) + 1, poContentUnknown.indexOf(eActState.CONSEQUENCE.name())).split("[|]"); 
		String [] oConsequenceUnknown = poContentUnknown.substring(poContentUnknown.indexOf("|", poContentUnknown.indexOf(eActState.CONSEQUENCE.name())) + 1).split("[|]");
		
		for(String oSubString : oPreconditionUnknown){
			if(oPreconditionKnown.contains(oSubString)){
				nMatchScore ++; 
			}
		}
			
		for(String oSubString : oActionUnknown){
			if(oActionKnown.contains(oSubString)){
				nMatchScore ++; 
			}
		}
			
		for(String oSubString : oConsequenceUnknown){
			if(oConsequenceKnown.contains(oSubString)){
				nMatchScore ++; 
			}
		}
		
		return nMatchScore;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:44:40
	 *
	 * @return
	 */
	private double getNumbAssociatedDS() {
		double oResult = 0.0;
		for(clsDataStructurePA oElement : moAssociatedContent){
			if(oElement instanceof clsAct){
				oResult +=((clsAct)oElement).getNumbAssociatedDS(); 
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
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsSecondaryDataStructure>(); 
        		
        		for(Object oSecondaryDS : moAssociatedContent){
        			try {
        				Class<?> clzz = oSecondaryDS.getClass();
        	    		Method   meth = clzz.getMethod("clone", new Class[0]);
        				Object   dupl = meth.invoke(oSecondaryDS, new Object[0]);
        				oClone.moAssociatedContent.add((clsSecondaryDataStructure)dupl); // unchecked warning
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
		oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
			
//		
//		if (oResult.length() > 4) {
//			oResult = oResult.substring(0, oResult.length()-3);
//		}
		return oResult; 
	}
	
}

