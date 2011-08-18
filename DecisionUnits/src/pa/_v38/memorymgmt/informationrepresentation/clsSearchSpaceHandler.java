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
import pa._v38.memorymgmt.datatypes.clsAssociationTime;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.enums.eDataType;
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
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.07.2011 16:50:35
	 *
	 * @param poDataStructure
	 * @return
	 */
	public ArrayList <clsAssociation> readOutSearchSpace(clsDataStructurePA poDataStructure) {
		ArrayList <clsAssociation> oRetVal = new ArrayList <clsAssociation>();
		
		//Set all returntypes
		ArrayList<Integer> iReturnTypes = new ArrayList<Integer>();
		if (poDataStructure instanceof clsPrimaryDataStructure) {
			iReturnTypes.add(eDataType.DM.nBinaryValue);
			iReturnTypes.add(eDataType.TP.nBinaryValue);
			iReturnTypes.add(eDataType.TPM.nBinaryValue);
			iReturnTypes.add(eDataType.TI.nBinaryValue);
		} else if (poDataStructure instanceof clsSecondaryDataStructure) {
			iReturnTypes.add(eDataType.ACT.nBinaryValue);	//Add action plans, TODO AW: Add Andis plan structure here
			iReturnTypes.add(eDataType.WP.nBinaryValue);	//WP-WP/WPM-WPM associations
			//iReturnTypes.add(eDataType.WPM.nBinaryValue);	//FIXME AW: It is inefficient that WP and WPM have the same binary values
			iReturnTypes.add(eDataType.TI.nBinaryValue);	//Image associations for the secondary process
			
		}
		
		for (Integer iType :iReturnTypes) {
			ArrayList<clsAssociation> oAssList = readOutSearchSpace(iType, poDataStructure, true);
			for (clsAssociation oAss : oAssList) {
				//Add all associations but association time, as it is already there
				if ((oAss instanceof clsAssociationTime)==false) {
					oRetVal.add(oAss);
				}
			}
			
			
			//oRetVal.addAll(readOutSearchSpace(iType, poDataStructure, true));
		}
		
		return oRetVal; 
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.07.2011 16:50:40
	 *
	 * @param poReturnType
	 * @param poDataStructure
	 * @return
	 */
	public ArrayList <clsAssociation> readOutSearchSpace(int poReturnType, clsDataStructurePA poDataStructure) {
		return readOutSearchSpace(poReturnType, poDataStructure, false);
	}
	
	//This function returns all associations of the input of a certain type and a certain data structure
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 19.07.2011 16:50:44
	 *
	 * @param poReturnType
	 * @param poDataStructure
	 * @param blCompareInstance
	 * @return
	 */
	public ArrayList <clsAssociation> readOutSearchSpace(int poReturnType, clsDataStructurePA poDataStructure, boolean blCompareInstance){
		ArrayList <clsAssociation> oAssociatedDataStructureList = new ArrayList<clsAssociation>();
		ArrayList <clsAssociation> oList = moSearchSpace
		                                        .returnSearchSpaceTable()
		                                        	.get(poDataStructure.getMoDataStructureType())
		                                              .get(poDataStructure.getMoContentType())
		                                              	.get(poDataStructure.getMoDS_ID()).b;
		
		for(clsAssociation oAssociationElement : oList){
			clsDataStructurePA elementB = null; 
			
			//If compare instance = false, then only the moID is used
			if (blCompareInstance == false) {
				//Make a special case for drive meshes. //FIXME: Something has to be done with the instance IDs, else the stone or other structures will be erroneously addressed
				//If in the association, the Type is a DM, it will have an instanceID already and therefore, only the InstanceID=0 shall be used
				//if ((oAssociationElement.getMoAssociationElementA().getMoDataStructureType() == eDataType.DM)) {
					if ((oAssociationElement.getMoAssociationElementB().getMoDS_ID() == poDataStructure.getMoDS_ID()) && (oAssociationElement.getMoAssociationElementB().getMoDSInstance_ID() == 0)) {
						elementB = oAssociationElement.getMoAssociationElementA();
					}  else if ((oAssociationElement.getMoAssociationElementA().getMoDS_ID() == poDataStructure.getMoDS_ID()) && (oAssociationElement.getMoAssociationElementA().getMoDSInstance_ID() == 0)) {
							elementB = oAssociationElement.getMoAssociationElementB();
					}
				//Else, it is no DM and will not have an instanceID
				/*} else {
					if (oAssociationElement.getMoAssociationElementA().getMoDS_ID() == poDataStructure.getMoDS_ID()) {
						elementB = oAssociationElement.getMoAssociationElementB(); 
					}
					else if (oAssociationElement.getMoAssociationElementB().getMoDS_ID()  == poDataStructure.getMoDS_ID()) {
						elementB = oAssociationElement.getMoAssociationElementA();
					} else {
						throw new NoSuchFieldError("Association " + oAssociationElement.getMoDS_ID() + " does not contain data structure " + poDataStructure.getMoDS_ID());}
					} */
			//If compareInstance = true, i.e. only memory is checked
			} else {
				if(oAssociationElement.getRootElement().getMoDSInstance_ID() == poDataStructure.getMoDSInstance_ID()){ 
					elementB = oAssociationElement.getLeafElement(); 
				} else if (oAssociationElement.getLeafElement().getMoDSInstance_ID() == poDataStructure.getMoDSInstance_ID()) {
					elementB = oAssociationElement.getRootElement();
				}
			}
			
			if (elementB != null) {
				if((poReturnType & elementB.getMoDataStructureType().nBinaryValue) != 0x0){
					oAssociatedDataStructureList.add(oAssociationElement); 
				}
			}
		}
		return oAssociatedDataStructureList; 
	}

}
