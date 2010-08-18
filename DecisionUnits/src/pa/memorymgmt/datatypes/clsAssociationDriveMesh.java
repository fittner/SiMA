/**
 * clsAssociationDriveMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 */
package pa.memorymgmt.datatypes;

import java.util.NoSuchElementException;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 * 
 */
public class clsAssociationDriveMesh extends clsAssociation{
	//never usedprivate Object moContent = null; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:36:37
	 *
	 * @param clsDataStructurePA
	 * @param poDriveMesh
	 */
	public clsAssociationDriveMesh(clsTripple<String, eDataType, String> poDataStructureIdentifier, 
			clsDriveMesh poAssociationElementA, 
			clsPrimaryDataStructure poAssociationElementB){
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);		
		//HZ moContent defines the association weight => also defines the affect!
		//It has to be defined if an affect is required and how the affect's minus values
		//should be represented in case the affect is the same as the clsAssociation's weight 
		//moContent = 1.0; 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:09
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:11:25
	 * 
	 * @see pa.memorymgmt.datatypes.clsAssociation#getLeafElement(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement(clsDataStructurePA poRootElement) {
		// TODO (zeilinger) - Auto-generated method stub
		return null;
	}
	
	public clsDriveMesh getDM(){
		if(moAssociationElementA instanceof clsDriveMesh){return (clsDriveMesh)moAssociationElementA;}
		else if(moAssociationElementB instanceof clsDriveMesh) {return (clsDriveMesh)moAssociationElementB;}
		else{ throw new NoSuchElementException("Element not found");}
	}
}
