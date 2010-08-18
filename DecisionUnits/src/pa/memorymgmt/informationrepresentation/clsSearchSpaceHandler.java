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
	private clsSearchSpaceBase moSearchSpace; 
	
	public clsSearchSpaceHandler(String poDatabaseSource, String poSourceName){
		createSearchSpace(poDatabaseSource, poSourceName);
	}
	
	private void createSearchSpace(String poDatabaseSource, String poSourceName){
		if(poDatabaseSource.equals(eDataSources.MAINMEMORY.name())){moSearchSpace = clsSearchSpaceCreator.createSearchSpace(poSourceName);}
		else if(poDatabaseSource.equals(eDataSources.DATABASE.name())){/*TODO define database creator access */ ;}
		else {throw new NullPointerException("database source not found " + poDatabaseSource);}
	}
	
	public clsSearchSpaceBase returnSearchSpace(){
		return moSearchSpace;
	}
	
	public void setSearchSpace(clsSearchSpaceBase poSearchSpaceBase){
		moSearchSpace = poSearchSpaceBase;
	}
	
	public ArrayList <clsAssociation> readOutSearchSpace(int poReturnType, clsDataStructurePA poDataStructure){
		ArrayList <clsAssociation> oAssociatedDataStructureList = new ArrayList<clsAssociation>();
		ArrayList <clsAssociation> oList = moSearchSpace
		                                        .returnSearchSpaceTable(poDataStructure.moDataStructureType)
		                                              .get(poDataStructure.moContentType)
		                                                   .get(poDataStructure);
		
		for(clsAssociation oAssociationElement : oList){
			clsDataStructurePA elementB; 
			
			if(oAssociationElement.moAssociationElementA.moDataStructureID.equals(poDataStructure.moDataStructureID)){ 
				elementB = oAssociationElement.moAssociationElementB; 
			}
			else if(oAssociationElement.moAssociationElementB.moDataStructureID.equals(poDataStructure.moDataStructureID)){
				elementB = oAssociationElement.moAssociationElementA;
			}
			else {throw new NoSuchFieldError("Association " + oAssociationElement.moDataStructureID + " does not contain data structure " + poDataStructure.moDataStructureID);}
		
			if((poReturnType & elementB.moDataStructureType.nBinaryValue) != 0x0){
				oAssociatedDataStructureList.add(oAssociationElement); 
			}
		}
		return oAssociatedDataStructureList; 
	}
}
