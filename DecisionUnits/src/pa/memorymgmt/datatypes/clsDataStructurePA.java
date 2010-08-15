/**
 * clsDatastructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 * 
 */
public abstract class clsDataStructurePA implements Cloneable, itfComparable{
	public String moDataStructureID;
	public eDataType moDataStructureType;
	public String moContentType;
		
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:50:02
	 *
	 */
	public clsDataStructurePA(clsTripple<String, eDataType, String> poDataStructureIdentifier) {
		moDataStructureID = poDataStructureIdentifier.a; 
		moDataStructureType = poDataStructureIdentifier.b;
		moContentType = poDataStructureIdentifier.c; 
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
		ArrayList<clsAssociation> oClonedTemplateList = this.cloneList(poContentListTemplate); 
		
		for(clsAssociation oAssociationUnknown : poContentListUnknown){
			/*oMatch defines an object of clsPair that contains the match-score (Double value) between two objects (moAssociationElementB of 
			 * oAssociationUnknown and oAssociationTemplate) and the entry number where the best matching element is found in 
			 * oClonedTemplateList. After it is selected as best match it is removed from the list in order to admit that the 
			 * association element of the next association in poContentListUnknown is compared again with the same element.*/
			clsPair <Double, Integer> oMatch = new clsPair<Double, Integer>(0.0,-1);
			for(clsAssociation oAssociationTemplate : oClonedTemplateList){
				
				double rMatchScoreTemp = 0.0; 
				if(oAssociationTemplate.moAssociationElementB.getClass().equals(oAssociationUnknown.moAssociationElementB.getClass())){
					rMatchScoreTemp = oAssociationTemplate.moAssociationElementB.compareTo(oAssociationUnknown.moAssociationElementB); 
					
					if(rMatchScoreTemp > oMatch.a){ 
						oMatch.a = rMatchScoreTemp; 
						oMatch.b = oClonedTemplateList.indexOf(oAssociationTemplate);
					}
				}
			}
			//Sums up the match score; Takes always the highest possible score 
			oMatchScore += oMatch.a;
			
			if(oMatch.a > 0.0){
				try{
					oClonedTemplateList.remove((int)oMatch.b);
				}catch(Exception e){System.out.println("oMatch.b was set to an incorrect value " + e.toString());}} 
		}
		return oMatchScore;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.07.2010, 16:02:43
	 *
	 * @param poContentListTemplate
	 * @return
	 */
	private ArrayList<clsAssociation> cloneList(
			ArrayList<clsAssociation> poContentListTemplate) {
		
		ArrayList<clsAssociation> oClone = new ArrayList<clsAssociation>(); 
		for(clsAssociation oAssociation : poContentListTemplate){
			try { 
				Object dupl = oAssociation.clone(); 
				oClone.add((clsAssociation)dupl); // unchecked warning
			} catch (Exception e) {
				//.....
			}
		}
		return oClone;
	}
}