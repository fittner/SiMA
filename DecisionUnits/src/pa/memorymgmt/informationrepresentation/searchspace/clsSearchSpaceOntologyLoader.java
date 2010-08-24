/**
 * clsSearchSpaceOntologyLoader.java: DecisionUnits - pa.memorymgmt.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 25.06.2010, 13:22:41
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 25.06.2010, 13:22:41
 * 
 */
public class clsSearchSpaceOntologyLoader extends clsSearchSpaceBase{

	HashMap<eDataType,HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>> moSearchSpaceContent;  
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
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 25.06.2010, 13:22:57
	 * 
	 * @see pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase#loadSearchSpace()
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
			
			if(!moSearchSpaceContent.containsKey(oDS.moDataStructureType)){
				moSearchSpaceContent.put(oDS.moDataStructureType, new HashMap<String, HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>>()); 
			}
			if(!moSearchSpaceContent.get(oDS.moDataStructureType).containsKey(oDS.moContentType)){
				moSearchSpaceContent.get(oDS.moDataStructureType)
									.put(oDS.moContentType, new HashMap<Integer, clsPair<clsDataStructurePA, ArrayList<clsAssociation>>>()); 
			}
			if(!moSearchSpaceContent.get(oDS.moDataStructureType).get(oDS.moContentType).containsKey(oDS.moDS_ID)){
				moSearchSpaceContent.get(oDS.moDataStructureType).get(oDS.moContentType)
									.put(oDS.moDS_ID, new clsPair<clsDataStructurePA, ArrayList<clsAssociation>>(oDS, new ArrayList<clsAssociation>()));
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
		clsDataStructurePA oElementA = ((clsAssociation)poAssociation).moAssociationElementA;
		clsDataStructurePA oElementB = ((clsAssociation)poAssociation).moAssociationElementB;
		
		moSearchSpaceContent.get(oElementA.moDataStructureType).get(oElementA.moContentType).get(oElementA.moDS_ID).b.add((clsAssociation)poAssociation);
		moSearchSpaceContent.get(oElementB.moDataStructureType).get(oElementB.moContentType).get(oElementB.moDS_ID).b.add((clsAssociation)poAssociation);	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 25.06.2010, 13:22:57
	 * 
	 * @see pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
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
						oRetVal +="	Object:	" + oDataStructure.moDS_ID + "\n";
						for(clsAssociation oAssociation : moSearchSpaceContent.get(oDataStructureContentType).get(oDataElement).get(oDataStructure.moDS_ID).b){
							oRetVal +="		Association: " + oAssociation.moDS_ID +"\n"; 
						}
					}
			}
		}
		return oRetVal; 
	}

}
