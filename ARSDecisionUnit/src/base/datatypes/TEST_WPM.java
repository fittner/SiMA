/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package base.datatypes;

import memorymgmt.enums.eContentType;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 08.10.2013, 09:29:42
 * 
 */
public class TEST_WPM implements TEST_MeshInterface<clsPrimaryDataStructure> {

    private TEST_AssociationInterface<clsAssociationSecondary> test;
    /* (non-Javadoc)
     *
     * @since 08.10.2013 09:30:37
     * 
     * @see pa._v38.memorymgmt.datatypes.TEST_DataStructurePAInterface#getID()
     */
    @Override
    public int getID() {
        // TODO (wendt) - Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     *
     * @since 08.10.2013 09:30:37
     * 
     * @see pa._v38.memorymgmt.datatypes.TEST_DataStructurePAInterface#getContentType()
     */
    @Override
    public eContentType getContentType() {
        // TODO (wendt) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 08.10.2013 09:30:37
     * 
     * @see pa._v38.memorymgmt.datatypes.TEST_DataStructurePAInterface#getContent()
     */
    @Override
    public String getContent() {
        // TODO (wendt) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 08.10.2013 09:30:37
     * 
     * @see pa._v38.memorymgmt.datatypes.TEST_MeshInterface#getStructure()
     */
    @Override
    public clsPrimaryDataStructure getStructure() {
        // TODO (wendt) - Auto-generated method stub
        return null;
    }

    /**
     * @since 08.10.2013 09:36:56
     * 
     * @return the test
     */
    @Override
    public TEST_AssociationInterface<clsAssociationSecondary> getTest() {
        return test;
    }

    /**
     * @since 08.10.2013 09:36:56
     * 
     * @param test the test to set
     */
    public void setTest(TEST_AssociationInterface<clsAssociationSecondary> test) {
        this.test = test;
    }

}
