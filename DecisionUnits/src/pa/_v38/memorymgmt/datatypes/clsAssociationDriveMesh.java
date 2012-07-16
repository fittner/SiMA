/**
 * clsAssociationDriveMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;


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
	public clsAssociationDriveMesh(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, 
			clsDriveMeshOLD poAssociationElementA, 
			clsThingPresentationMesh poAssociationElementB){
		
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
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		//FIXME HZ: The DM has to be marked as leaf element! Bad term 
		return moAssociationElementA; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementB; 
	}
	
	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	public clsDriveMeshOLD getDM(){
		//Element A is always the Drive Mesh 
		return (clsDriveMeshOLD)moAssociationElementA; 
	}
}
