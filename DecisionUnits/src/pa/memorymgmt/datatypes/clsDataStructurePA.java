/**
 * clsDatastructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

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

	protected int moDS_ID;
	protected eDataType moDataStructureType;
	protected String moContentType;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:50:02
	 *
	 */
	public clsDataStructurePA(clsTripple<Integer, eDataType, String> poDataStructureIdentifier) {
		moDS_ID = poDataStructureIdentifier.a; 
		moDataStructureType = poDataStructureIdentifier.b;
		moContentType = poDataStructureIdentifier.c; 
	}
		
//	/**
//	 * DOCUMENT (zeilinger) - insert description
//	 *
//	 * @author zeilinger
//	 * 15.07.2010, 08:56:36
//	 *
//	 * @param oContentListTemplate
//	 * @param oContentListUnknown
//	 * @return
//	 */
//	protected double getCompareScorePrimary(ArrayList<clsAssociation> poContentListTemplate,ArrayList<clsAssociation> poContentListUnknown) {
//		double oMatchScore	 = 0.0;
//		double rMatchScoreTemp = 0.0;
//		ArrayList<clsAssociation> oClonedTemplateList = this.cloneList(poContentListTemplate); 
//		
//		for(clsAssociation oAssociationUnknown : poContentListUnknown){
//			/*oMatch defines an object of clsPair that contains the match-score (Double value) between two objects (moAssociationElementB of 
//			 * oAssociationUnknown and oAssociationTemplate) and the entry number where the best matching element is found in 
//			 * oClonedTemplateList. After it is selected as best match it is removed from the list in order to admit that the 
//			 * association element of the next association in poContentListUnknown is compared again with the same element.*/
//			clsPair <Double, Integer> oMatch = new clsPair<Double, Integer>(0.0,-1);
//					
//			for(clsAssociation oAssociationTemplate : oClonedTemplateList){
//					rMatchScoreTemp = oAssociationTemplate.moAssociationElementB.compareTo(oAssociationUnknown.moAssociationElementB); 
//					
//					if(rMatchScoreTemp > oMatch.a){ 
//						oMatch.a = rMatchScoreTemp; 
//						oMatch.b = oClonedTemplateList.indexOf(oAssociationTemplate);
//					}
//			}
//			//Sums up the match score; Takes always the highest possible score 
//			oMatchScore += oMatch.a;
//			
//			if(oMatch.a > 0.0){
//				try{
//					oClonedTemplateList.remove((int)oMatch.b);
//				}catch(Exception e){System.out.println("oMatch.b was set to an incorrect value " + e.toString());}} 
//		}
//		return oMatchScore;
//	}
	
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @return the moDS_ID
	 */
	public int getMoDS_ID() {
		return moDS_ID;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @param moDS_ID the moDS_ID to set
	 */
	public void setMoDS_ID(int moDS_ID) {
		this.moDS_ID = moDS_ID;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @return the moDataStructureType
	 */
	public eDataType getMoDataStructureType() {
		return moDataStructureType;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @param moDataStructureType the moDataStructureType to set
	 */
	public void setMoDataStructureType(eDataType moDataStructureType) {
		this.moDataStructureType = moDataStructureType;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @return the moContentType
	 */
	public String getMoContentType() {
		return moContentType;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @param moContentType the moContentType to set
	 */
	public void setMoContentType(String moContentType) {
		this.moContentType = moContentType;
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
	protected <E extends clsDataStructurePA> double getMatchScore(ArrayList<E> poContentListTemplate,ArrayList<E> poContentListUnknown) {
		double oMatchScore	 = 0.0;
		double rMatchScoreTemp = 0.0;
		List<E> oClonedTemplateList = this.cloneList(poContentListTemplate); 
		
		for(E oUnknownDS : poContentListUnknown){
			/*oMatch defines an object of clsPair that contains the match-score (Double value) between two objects (moAssociationElementB of 
			 * oAssociationUnknown and oAssociationTemplate) and the entry number where the best matching element is found in 
			 * oClonedTemplateList. After it is selected as best match it is removed from the list in order to admit that the 
			 * association element of the next association in poContentListUnknown is compared again with the same element.*/
			clsPair <Double, Integer> oMatch = new clsPair<Double, Integer>(0.0,-1);
					
			for(E oClonedKnownDS : oClonedTemplateList){
				
				if( oClonedKnownDS instanceof clsAssociation ){
					rMatchScoreTemp = ((clsAssociation)oClonedKnownDS).moAssociationElementB.compareTo(((clsAssociation)oUnknownDS).moAssociationElementB) * ((clsAssociation)oClonedKnownDS).mrImperativeFactor; 
				}
				else if (oClonedKnownDS instanceof clsSecondaryDataStructure){
					rMatchScoreTemp = oClonedKnownDS.compareTo(oUnknownDS);
				}
				else {
					throw new UnknownError( "Data structure type for comparison not useable" ); 
				}
				
				if(rMatchScoreTemp > oMatch.a){ 
					oMatch.a = rMatchScoreTemp; 
					oMatch.b = oClonedTemplateList.indexOf(oClonedKnownDS);
				}
			}
			//Sums up the match score; Takes always the highest possible score 
			oMatchScore += oMatch.a;
			
			if(oMatch.a > 0.0){
				try{
					oClonedTemplateList.remove((int)oMatch.b);
				}catch(Exception e){System.out.println("oMatch.b was set to an incorrect value " + e.toString());}
			} 
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
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <E extends clsDataStructurePA> List<E> cloneList(List<E> poContentListTemplate) {
		
		List<E> oClone = new ArrayList<E>(); 
		for(E oAssociation : poContentListTemplate){
			try { 
				Object dupl = oAssociation.clone(); 
				oClone.add((E) dupl); // unchecked warning
			} catch (Exception e) {
				//.....
			}
		}
		return oClone;
	}
}