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
	public clsDataStructurePA(String poDataStructureID, eDataType poDataStructureType) {
		oDataStructureID = poDataStructureID; 
		oDataStructureType = poDataStructureType;
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
		double oMatchScore	 = 0.0; 
		
		for(clsAssociation oAssociationUnknown : poContentListUnknown){
			double oMatchScoreSingle = 0.0;
			for(clsAssociation oAssociationTemplate : poContentListTemplate){
				double oMatchScoreTemp = 0.0; 
				if(oAssociationTemplate.moAssociationElementB.getClass().equals(oAssociationUnknown.moAssociationElementB.getClass())){
					oMatchScoreTemp = oAssociationTemplate.moAssociationElementB.compareTo(oAssociationUnknown.moAssociationElementB); 
					if(oMatchScoreTemp > oMatchScoreSingle) {oMatchScoreSingle = oMatchScoreTemp;}
				}
			}
			//Sums up the match score; Takes always the highest possible score 
			oMatchScore += oMatchScoreSingle;
			oMatchScoreSingle = 0.0; 
		}
		return oMatchScore;
	}
}