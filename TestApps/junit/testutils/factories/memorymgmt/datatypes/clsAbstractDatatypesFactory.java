/**
 * CHANGELOG
 *
 * 23.02.2013 ende - File created
 *
 */
package testutils.factories.memorymgmt.datatypes;

/**
 * DOCUMENT (havlicek) - abstract implementation of the factories interface.
 * 
 * @param <T>
 *            the class of the datatype to be created.
 * 
 * @author havlicek 23.02.2013, 11:43:55
 */
public abstract class clsAbstractDatatypesFactory<T> implements itfDatatypesFactory<T> {

    /** the object in creation, stored inside the factory */
    protected T moObject;

    @Override
    public T getObject() {
        return moObject;
    }

}
