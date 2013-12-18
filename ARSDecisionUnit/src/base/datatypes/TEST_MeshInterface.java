/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package base.datatypes;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 08.10.2013, 09:26:24
 * 
 */
public interface TEST_MeshInterface<T extends clsDataStructurePA> extends TEST_DataStructurePAInterface {
    public T getStructure();
    public TEST_AssociationInterface<clsAssociationSecondary> getTest();
}
