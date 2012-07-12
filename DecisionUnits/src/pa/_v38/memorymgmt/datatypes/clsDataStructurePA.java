/**
 * clsDatastructure.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.List;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) 
 * 
 * Every data structure inherits from clsDataStructurePA which represents the super class of all defined data structures. Class 
 * clsDataStructurePA introduces three member variables which hence, are set for every psychoanalytical data structure.
 * 
 * moDS_ID (int): Represents the identifier for every data structure which is set during the generation (loading from the database or 
 * 		within the ARS decision unit itself). Its value ranges in between -1 (data structure is created within the decision unit but 
 * 		has not been compared with knowledge yet) and N. This value is introduced in order to allow a more efficient search – in case 
 * 		a data structure has already been identified a number unequal to -1 is assigned and must not be compared with stored templates 
 * 		anymore.
 * 
 * moDataStructureType (eDataType): Defines the type of data structure (TP, TPM, DM, WP, Affect, Act)
 * 
 * moContentType (String): Specifies the content of the data structure but not the content itself. The content is set within the data 
 * 		structure class itself. For an atomic data structure like a thing presentation, the content type holds the String “Color”, 
 * 		“Bump” etc. For data structure meshes, the content type defines the type of entity (e.g. “CAKE”)
 * 
 * @author zeilinger
 * 23.05.2010, 21:40:06
 * 
 */
/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 06.07.2011, 10:59:56
 * 
 */
public abstract class clsDataStructurePA implements Cloneable, itfComparable{

	protected int moDS_ID;
	protected eDataType moDataStructureType;
	protected eContentType moContentType;
	//AW 20110706: New Identifier for instances
	/*As there are no possibility to identify a root element with a unique key, an instance ID have to be used
	 * in order to be able to assign the root elements in the associated data structures in the containers with
	 * the elements within a data structure within a container. A normal object comparison with "equal" does not
	 * work as deepcopy does not keep instance relations in the associations
	 * 
	 * A more efficient alternative would be to dispose the containers and to use real PA structures instead...
	 */
	protected int moDSInstance_ID;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.06.2010, 15:50:02
	 *
	 */
	public clsDataStructurePA(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier) {
		moDS_ID = poDataStructureIdentifier.a; 
		moDataStructureType = poDataStructureIdentifier.b;
		moContentType = poDataStructureIdentifier.c; 
		
		//All new structures are initialized with 0
		moDSInstance_ID = 0;
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
	public eContentType getMoContentType() {
		return moContentType;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @param moContentType the moContentType to set
	 */
	public void setMoContentType(eContentType moContentType) {
		this.moContentType = moContentType;
	}
	
	public int getMoDSInstance_ID() {
		return moDSInstance_ID;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:55:43
	 * 
	 * @param moDS_ID the moDS_ID to set
	 */
	public void setMoDSInstance_ID(int moDSInstance_ID) {
		this.moDSInstance_ID = moDSInstance_ID;
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
	protected <E extends clsDataStructurePA> double getMatchScore(ArrayList<E> poContentListTemplate, ArrayList<E> poContentListUnknown) {
		double oMatchScore = 0.0;
		double oMatchScoreNorm = 0.0;
		double rMatchScoreTemp = 0.0;
		List<E> oClonedTemplateList = this.cloneList(poContentListTemplate); 
		int nAssociationCount = poContentListUnknown.size(); 
		
		//The unknown data structures are searched in the known data structures.
		//If an unknown data structure is not found in the known data structures, the match is 0, i. e. if the 
		//more specialized data structure is not found in the generalized data structure, the return value is 0.
		//Example: CAKE is compared with ENTITY: CAKE is an ENTITY -> Match 1.0.
		//Function CAKE.getMatchScore(ENTITY) = 1.0
		//Function ENTITY.getMatchScore(CAKE) = 0.0, i.e. the attributes of the CAKE shall be found in ENTITY
		
		//An attribute is considered as found, if they share the same CONTENTTYPE
		//oProp.setProperty(pre+P_SOURCE_NAME, "/DecisionUnits/config/_v38/bw/pa.memory/AGENT_BASIC/BASIC_AW.pprj"); in clsInformationRepresentationManager.java
		
			for(E oUnknownDS : poContentListUnknown){
				/*oMatch defines an object of clsPair that contains the match-score (Double value) between two objects (moAssociationElementB of 
				 * oAssociationUnknown and oAssociationTemplate) and the entry number where the best matching element is found in 
				 * oClonedTemplateList. After it is selected as best match it is removed from the list in order to admit that the 
				 * association element of the next association in poContentListUnknown is compared again with the same element.*/
				clsPair <Double, Integer> oMatch = new clsPair<Double, Integer>(0.0,-1);
					
				for(E oClonedKnownDS : oClonedTemplateList){				
					//Check data types
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
			
			//Norm the output
			if (nAssociationCount>0) {
				oMatchScoreNorm = oMatchScore/nAssociationCount;
			} else {
				oMatchScoreNorm = 0;
			}
		}
				
		
		//return oMatchScore;
		return oMatchScoreNorm;
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