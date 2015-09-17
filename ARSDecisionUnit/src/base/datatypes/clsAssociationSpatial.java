/**
 * CHANGELOG
 *
 * Sep 1, 2015 zhukova - File created
 *
 */
package base.datatypes;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (zhukova) - insert description 
 * 
 * @author zhukova
 * Sep 1, 2015, 5:35:30 PM
 * 
 */
public class clsAssociationSpatial extends clsAssociation {

    /**
     * DOCUMENT - insert description
     *
     * @author zhukova
     * @since Sep 1, 2015 5:36:38 PM
     *
     * @param poDataStructureIdentifier
     * @param poAssociationElementA
     * @param poAssociationElementB
     */
    public clsAssociationSpatial(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, clsDataStructurePA poAssociationElementA,
            clsDataStructurePA poAssociationElementB) {
        super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
        // TODO (zhukova) - Auto-generated constructor stub
    }

    /* (non-Javadoc)
     *
     * @since Sep 1, 2015 5:36:45 PM
     * 
     * @see base.datatypes.itfComparable#compareTo(base.datatypes.clsDataStructurePA)
     */
    @Override
    public double compareTo(clsDataStructurePA poDataStructure) {
        // TODO (zhukova) - Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     *
     * @since Sep 1, 2015 5:36:45 PM
     * 
     * @see base.datatypes.clsAssociation#getLeafElement()
     */
    @Override
    public clsDataStructurePA getLeafElement() {
        // TODO (zhukova) - Auto-generated method stub
        return (clsThingPresentationMesh) moAssociationElementB;
    }

    /* (non-Javadoc)
     *
     * @since Sep 1, 2015 5:36:45 PM
     * 
     * @see base.datatypes.clsAssociation#getRootElement()
     */
    @Override
    public clsDataStructurePA getRootElement() {
        // TODO (zhukova) - Auto-generated method stub
        return (clsThingPresentationMesh) moAssociationElementA;
    }

    /* (non-Javadoc)
     *
     * @since Sep 1, 2015 5:36:45 PM
     * 
     * @see base.datatypes.clsAssociation#setLeafElement(base.datatypes.clsDataStructurePA)
     */
    @Override
    public void setLeafElement(clsDataStructurePA poDS) {
        // TODO (zhukova) - Auto-generated method stub
        moAssociationElementB = poDS;

    }

    /* (non-Javadoc)
     *
     * @since Sep 1, 2015 5:36:45 PM
     * 
     * @see base.datatypes.clsAssociation#setRootElement(base.datatypes.clsDataStructurePA)
     */
    @Override
    public void setRootElement(clsDataStructurePA poDS) {
        // TODO (zhukova) - Auto-generated method stub
        moAssociationElementA = poDS;

    }

}
