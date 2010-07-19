/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa.memorymgmt.informationrepresentation;

import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.informationrepresentation.enums.eDataSources;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceCreator;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 * 
 */
public class clsSearchSpaceHandler {
	clsSearchSpaceBase moSearchSpace; 
	
	public clsSearchSpaceHandler(String poDatabaseSource){
		createSearchSpace(poDatabaseSource);
	}
	
	private void createSearchSpace(String poDatabaseSource){
		if(poDatabaseSource.equals(eDataSources.MAINMEMORY)){moSearchSpace = clsSearchSpaceCreator.createSearchSpace();}
		else if(poDatabaseSource.equals(eDataSources.DATABASE)){/*TODO define database creator access */ ;}
		else {throw new NullPointerException("database source not found " + poDatabaseSource);}
	}
	
	public clsSearchSpaceBase returnSearchSpace(){
		return moSearchSpace;
	}
	
	public ArrayList <clsAssociation> readOutSearchSpace(int poReturnType, clsDataStructurePA poDataStructure){
		ArrayList <clsAssociation> oAssociatedDataStructureList = new ArrayList<clsAssociation>();
		ArrayList <clsAssociation> oList = moSearchSpace.returnSearchSpaceTable(poDataStructure.oDataStructureType).get(poDataStructure);
		
		for(clsAssociation oAssociationElement : oList){
			clsDataStructurePA elementB; 
			
			if(oAssociationElement.moAssociationElementA.oDataStructureID.equals(poDataStructure.oDataStructureID)){ 
				elementB = oAssociationElement.moAssociationElementB; 
			}
			else if(oAssociationElement.moAssociationElementB.oDataStructureID.equals(poDataStructure.oDataStructureID)){
				elementB = oAssociationElement.moAssociationElementA;
			}
			else {throw new NoSuchFieldError("Association " + oAssociationElement.oDataStructureID + " does not contain data structure " + poDataStructure.oDataStructureID);}
		
			if((poReturnType & elementB.oDataStructureType.nBinaryValue) != 0x0){
				oAssociatedDataStructureList.add(oAssociationElement); 
			}
		}
		return oAssociatedDataStructureList; 
	}
}
