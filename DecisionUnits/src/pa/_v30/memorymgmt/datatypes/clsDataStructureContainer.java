/**
 * clsDataStructureContainer.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 */
package pa._v30.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 *
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 * 
 */
public abstract class clsDataStructureContainer implements Cloneable{
	protected clsDataStructurePA moDataStructure; 
	protected ArrayList<clsAssociation> moAssociatedDataStructures; 
	
	public clsDataStructureContainer(clsDataStructurePA poDataStructure, ArrayList<clsAssociation>poAssociationList){
		moDataStructure = poDataStructure; 
		moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
		
		if(poAssociationList != null) {moAssociatedDataStructures = poAssociationList;}
	}
	
	
	
	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @return the moDataStructure
	 */
	public clsDataStructurePA getMoDataStructure() {
		return moDataStructure;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @param moDataStructure the moDataStructure to set
	 */
	public void setMoDataStructure(clsDataStructurePA poDataStructure) {
		this.moDataStructure = poDataStructure;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @return the moAssociatedDataStructures
	 */
	public ArrayList<clsAssociation> getMoAssociatedDataStructures() {
		return moAssociatedDataStructures;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @param moAssociatedDataStructures the moAssociatedDataStructures to set
	 */
	public void setMoAssociatedDataStructures(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		this.moAssociatedDataStructures = poAssociatedDataStructures;
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
