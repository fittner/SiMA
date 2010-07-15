/**
 * clsDatastructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 * 
 */
public abstract class clsDataStructurePA implements Cloneable, itfComparable{
	public String oDataStructureID;
	public eDataType oDataStructureType;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:50:02
	 *
	 */
	public clsDataStructurePA(String poDataStructureName, eDataType poDataStructureType) {
		oDataStructureID = poDataStructureName; 
		oDataStructureType = poDataStructureType;
	}
	
	protected boolean compareDataStructureID(clsDataStructurePA poDataStructure) {
		boolean oRetVal = false; 
		if(this.oDataStructureID.equals(poDataStructure.oDataStructureID)){
			oRetVal = true; 
		}
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.07.2010, 08:56:36
	 *
	 * @param oContentListTemplate
	 * @param oContentListUnknown
	 * @return
	 */
	protected double getCompareScore(ArrayList<clsAssociation> poContentListTemplate,ArrayList<clsAssociation> poContentListUnknown) {
		double oMatchScore = 0; 
		double oMatchScoreSingle = 0; 
		for(clsAssociation oAssociationTemplate : poContentListTemplate){
			for(clsAssociation oAssociationUnknown : poContentListUnknown){
				if(oAssociationTemplate.moAssociationElementB.getClass().equals(oAssociationUnknown.moAssociationElementB.getClass())){
					oMatchScoreSingle = oAssociationTemplate.moAssociationElementB.compareTo(oAssociationUnknown.moAssociationElementB); 
					oMatchScore =+ oAssociationTemplate.mrImperativeFactor*oAssociationTemplate.mrWeight*oMatchScoreSingle;
				}
			}
	}
		return oMatchScore;
	}
}