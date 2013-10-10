/**
 * CHANGELOG
 *
 * 08.10.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 08.10.2013, 09:21:47
 * 
 */
public interface TEST_AssociationInterface<T extends clsAssociation> extends TEST_DataStructurePAInterface {
    public T getRootElement();
    public T getLeafElement();
     
}
