/**
 * clsSearchSpaceHandler.java: DecisionUnits - pa.informationrepresentation
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 */
package pa._v38.memorymgmt.informationrepresentation;

import java.util.ArrayList;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.informationrepresentation.enums.eDataSources;
import pa._v38.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceBase;
import pa._v38.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceCreator;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 18:21:01
 * 
 */
public class clsSearchSpaceHandler implements itfInspectorInternalState {
	private clsSearchSpaceBase moSearchSpace; 
	
	public clsSearchSpaceBase getSearchSpace() {
		return moSearchSpace;
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 16:33:39
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		return moSearchSpace.stateToTEXT();
	}	
	
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
	//This function returns all associations of the input of a certain type and a certain data structure
	public ArrayList <clsAssociation> readOutSearchSpace(int poReturnType, clsDataStructurePA poDataStructure){
		ArrayList <clsAssociation> oAssociatedDataStructureList = new ArrayList<clsAssociation>();
		ArrayList <clsAssociation> oList = moSearchSpace
		                                        .returnSearchSpaceTable()
		                                        	.get(poDataStructure.getMoDataStructureType())
		                                              .get(poDataStructure.getMoContentType())
		                                              	.get(poDataStructure.getMoDS_ID()).b;
		
		for(clsAssociation oAssociationElement : oList){
			clsDataStructurePA elementB; 
			
			if(oAssociationElement.getMoAssociationElementA().getMoDS_ID() == poDataStructure.getMoDS_ID()){ 
				elementB = oAssociationElement.getMoAssociationElementB(); 
			}
			else if(oAssociationElement.getMoAssociationElementB().getMoDS_ID()  == poDataStructure.getMoDS_ID()){
				elementB = oAssociationElement.getMoAssociationElementA();
			}
			else {throw new NoSuchFieldError("Association " + oAssociationElement.getMoDS_ID() + " does not contain data structure " + poDataStructure.getMoDS_ID());}
		
			if((poReturnType & elementB.getMoDataStructureType().nBinaryValue) != 0x0){
				oAssociatedDataStructureList.add(oAssociationElement); 
			}
		}
		return oAssociatedDataStructureList; 
	}

}