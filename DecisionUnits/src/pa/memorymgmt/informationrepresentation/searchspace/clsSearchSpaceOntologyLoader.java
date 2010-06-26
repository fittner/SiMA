/**
 * clsSearchSpaceOntologyLoader.java: DecisionUnits - pa.memorymgmt.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 25.06.2010, 13:22:41
 */
package pa.memorymgmt.informationrepresentation.searchspace;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 25.06.2010, 13:22:41
 * 
 */
public class clsSearchSpaceOntologyLoader extends clsSearchSpaceBase{

	Hashtable<eDataType, Hashtable<clsDataStructurePA, ArrayList<clsAssociation>>> moSearchSpaceContent;  
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 25.06.2010, 13:23:09
	 *
	 * @param poDataStructureList
	 */
	public clsSearchSpaceOntologyLoader(Hashtable<eDataType, List<clsDataStructurePA>> poDataStructureTable) {
		super(poDataStructureTable);
		moSearchSpaceContent =new Hashtable <eDataType, Hashtable<clsDataStructurePA, ArrayList<clsAssociation>>>();
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
		printSearchSpace(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 22:01:13
	 *
	 */
	private void printSearchSpace() {
		for(eDataType oDataElement : moSearchSpaceContent.keySet()){
			System.out.println("DataStructure " + oDataElement);
			for(clsDataStructurePA oDataStructure : moSearchSpaceContent.get(oDataElement).keySet()){
				System.out.println("	Object:	" + oDataStructure.oDataStructureID);
				for(clsAssociation oAssociation : moSearchSpaceContent.get(oDataElement).get(oDataStructure)){
					System.out.println("		Association: " + oAssociation.oDataStructureID); 
				}
			}
		}
		
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.06.2010, 14:24:08
	 *
	 */
	private void convertArrayListToHashTable() {
		for(eDataType oDataType : eDataType.returnInitValues()){
			moSearchSpaceContent.put(eDataType.valueOf(oDataType.toString()), new Hashtable<clsDataStructurePA, ArrayList<clsAssociation>>()); 
			for(clsDataStructurePA oDataStructure : moDataStructureTable.get(oDataType)){
				moSearchSpaceContent.get(oDataType).put(oDataStructure, new ArrayList<clsAssociation>()); 
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
		for(clsDataStructurePA oAssociation : moDataStructureTable.get(eDataType.ASSCOCIATIONATTRIBUTE)){
			defineElements(oAssociation); 
		}	
		for(clsDataStructurePA oAssociation : moDataStructureTable.get(eDataType.ASSOCIATIONDM)){
			defineElements(oAssociation); 
		}
		for(clsDataStructurePA oAssociation : moDataStructureTable.get(eDataType.ASSOCIATIONTEMP)){
			defineElements(oAssociation); 
		}
		for(clsDataStructurePA oAssociation : moDataStructureTable.get(eDataType.ASSOCIATIONWP)){
			defineElements(oAssociation); 
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
		
		moSearchSpaceContent.get(oElementA.oDataStructureType).get(oElementA).add((clsAssociation)poAssociation);
		moSearchSpaceContent.get(oElementB.oDataStructureType).get(oElementB).add((clsAssociation)poAssociation);	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 25.06.2010, 13:22:57
	 * 
	 * @see pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase#returnSearchSpace(java.lang.String)
	 */
	@Override
	public Hashtable<clsDataStructurePA, ArrayList<clsAssociation>> returnSearchSpace(eDataType poDataStructureType) {
		return moSearchSpaceContent.get(poDataStructureType);
	}

}
