/**
 * clsDataStructureContainer.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 * 
 */
public abstract class clsDataStructureContainer implements Cloneable{
	public clsDataStructurePA moDataStructure; 
	public ArrayList<clsAssociation> moAssociatedDataStructures; 
	
	public clsDataStructureContainer(clsDataStructurePA poDataStructure, ArrayList<clsAssociation>poAssociationList){
		moDataStructure = poDataStructure; 
		moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
		
		if(poAssociationList != null) {moAssociatedDataStructures = poAssociationList;}
	}
	
	@Override
	public String toString(){
		String oRetVal = ""; 
		
		oRetVal += "DataStructureContainer:moDataStructure";
		oRetVal += moDataStructure.toString();
		for(clsAssociation oEntry : moAssociatedDataStructures){
			oRetVal += "\n	:AssociatedDataStructures:" + oEntry.toString(); 
		}
	
		return oRetVal;
	}
}
