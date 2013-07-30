/**
 * CHANGELOG
 *
 * 23.02.2013 ende - File created
 *
 */
package testutils.factories.memorymgmt.datatypes;

/**
 * DOCUMENT (havlicek) - basic interface discriptions for datatype factories.
 * 
 * @param <T>
 *            the generated datatypes class
 * 
 * @author havlicek 23.02.2013, 11:41:48
 * 
 */
public interface itfDatatypesFactory<T> {

    /**
     * DOCUMENT (havlicek) - fetch the object created by the factories chained method calls. NOTE Must be null save, null object tests should not rely
     * on the factory.
     * 
     * @since 23.02.2013 11:43:15
     * 
     * @return the current object in creation.
     */
    T getObject();

    /**
     * DOCUMENT (havlicek) - fetch a new empty object from the factory.
     * 
     * @since 23.02.2013 11:43:22
     * 
     * @return a new object from the factory.
     */
    T getEmptyObject();

}
