/**
 * CHANGELOG
 *
 * Jun 27, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/** 
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 27, 2012, 10:47:02 AM
 * 
 */
public class clsAssociationEmotion  extends clsAssociation{

	/**
	 * DOCUMENT (schaat) - insert description 
	 *
	 * @since Jun 27, 2012 10:48:35 AM
	 *
	 * @param poDataStructureIdentifier
	 * @param poAssociationElementA
	 * @param poAssociationElementB
	 */
	public clsAssociationEmotion(
			clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			clsEmotion poAssociationElementA,
			clsThingPresentationMesh poAssociationElementB) {
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
		// TODO (schaat) - Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 27.06.2012, 10:58:09
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 27.06.2012, 21:11:25
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		return moAssociationElementA; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementB; 
	}
	
	/* (non-Javadoc)
	 *
	 * @since 27.06.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;

		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.06.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		
	}
	
	public clsEmotion getDM(){
		//Element A is always the Drive Mesh 
		return (clsEmotion)moAssociationElementA; 
	}
}



