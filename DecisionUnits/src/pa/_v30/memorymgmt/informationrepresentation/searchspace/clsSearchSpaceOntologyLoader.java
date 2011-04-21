/**
 * clsSearchSpaceOntologyLoader.java: DecisionUnits - pa._v30.memorymgmt.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 25.06.2010, 13:22:41
 */
package pa._v30.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa._v30.tools.clsPair;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.enums.eDataType;

public class clsSearchSpaceOntologyLoader extends clsSearchSpaceBase{

	private HashMap<eDataType,HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>> moSearchSpaceContent;
	HashMap<eDataType, ArrayList<clsDataStructurePA>> moSimplifiedSearchSpace; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 25.06.2010, 13:23:09
	 *
	 * @param poDataStructureList
	 */
	public clsSearchSpaceOntologyLoader(HashMap<String, clsDataStructurePA> poDataStructureTable) {
		super(poDataStructureTable);
		loadSearchSpace();
		initSimplifiedSearchSpace(); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 25.06.2010, 13:22:57
	 * 
	 * @see pa._v30.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase#loadSearchSpace()
	 */
	
	private void loadSearchSpace() {
		convertArrayListToHashTable(); 
		bindObjectsAndAssociations(); 
		
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 14:24:08
	 *
	 */
	private void convertArrayListToHashTable() {
		moSearchSpaceContent = new HashMap<eDataType, HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>>();
		
		for(Map.Entry<String, clsDataStructurePA> oEntry : moDataStructureTable.entrySet()){
			clsDataStructurePA oDS = oEntry.getValue();
			
			if(!moSearchSpaceContent.containsKey(oDS.getMoDataStructureType())){
				moSearchSpaceContent.put(oDS.getMoDataStructureType(), new HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>()); 
			}
			if(!moSearchSpaceContent.get(oDS.getMoDataStructureType()).containsKey(oDS.getMoContentType())){
				moSearchSpaceContent.get(oDS.getMoDataStructureType())
									.put(oDS.getMoContentType(), new HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>()); 
			}
			if(!moSearchSpaceContent.get(oDS.getMoDataStructureType()).get(oDS.getMoContentType()).containsKey(oDS.getMoDS_ID())){
				moSearchSpaceContent.get(oDS.getMoDataStructureType()).get(oDS.getMoContentType())
									.put(oDS.getMoDS_ID(), new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(oDS, new ArrayList<clsAssociation>()));
			}
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 14:25:12
	 *
	 */
	private void bindObjectsAndAssociations() {
		for(Map.Entry<String, clsDataStructurePA> oEntry : moDataStructureTable.entrySet()){
			if(oEntry.getValue() instanceof clsAssociation){
					defineElements(oEntry.getValue());
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 20:28:40
	 *
	 * @param association
	 */
	private void defineElements(clsDataStructurePA poAssociation) {
		clsDataStructurePA oElementA = ((clsAssociation)poAssociation).getMoAssociationElementA();
		clsDataStructurePA oElementB = ((clsAssociation)poAssociation).getMoAssociationElementB();
		
		moSearchSpaceContent.get(oElementA.getMoDataStructureType()).get(oElementA.getMoContentType()).get(oElementA.getMoDS_ID()).b.add((clsAssociation)poAssociation);
		moSearchSpaceContent.get(oElementB.getMoDataStructureType()).get(oElementB.getMoContentType()).get(oElementB.getMoDS_ID()).b.add((clsAssociation)poAssociation);	
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 21.04.2011, 15:16:18
	 * 
	 */
	private void initSimplifiedSearchSpace (){
		moSimplifiedSearchSpace = new HashMap<eDataType, ArrayList<clsDataStructurePA>>(); 
		
		for(eDataType oDataType : moSearchSpaceContent.keySet()){
			ArrayList<clsDataStructurePA> oDataStructureList = new ArrayList<clsDataStructurePA>(); 

			for(String oDataStructureContentType : moSearchSpaceContent.get(oDataType).keySet()){
				for(Integer oDataStructureID : moSearchSpaceContent.get(oDataType).get(oDataStructureContentType).keySet()){
					oDataStructureList.add(moSearchSpaceContent.get(oDataType).get(oDataStructureContentType).get(oDataStructureID).a);
				}
			}
			
			moSimplifiedSearchSpace.put(oDataType, oDataStructureList); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 21.04.2011, 15:16:18
	 * 
	 */
	
	public HashMap<eDataType, ArrayList<clsDataStructurePA>> getSimplifiedSearchSpaceContent(){
				
		return moSimplifiedSearchSpace; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 25.06.2010, 13:22:57
	 * 
	 * @see pa._v30.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
	 */
	@Override
	public HashMap<eDataType,HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>> returnSearchSpaceTable() {
		return moSearchSpaceContent;
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 22:01:13
	 *
	 */
	@Override
	public String toString() {
		String oRetVal = ":Search space: \n"; 
		for(eDataType oDataElement : moSearchSpaceContent.keySet()){
			oRetVal += "DataStructure " + oDataElement + "\n";
			for(String oDataStructureContentType : moSearchSpaceContent.get(oDataElement).keySet()){
				oRetVal +="	Content Type:	" + oDataStructureContentType + "\n";
					for(Integer oDataStructureID : moSearchSpaceContent.get(oDataStructureContentType).get(oDataElement).keySet()){
						clsDataStructurePA oDataStructure = moSearchSpaceContent.get(oDataStructureContentType).get(oDataElement).get(oDataStructureID).a; 
						oRetVal +="	Object:	" + oDataStructure.getMoDS_ID() + "\n";
						for(clsAssociation oAssociation : moSearchSpaceContent.get(oDataStructureContentType).get(oDataElement).get(oDataStructure.getMoDS_ID()).b){
							oRetVal +="		Association: " + oAssociation.getMoDS_ID() +"\n"; 
						}
					}
			}
		}
		return oRetVal; 
	}

}
