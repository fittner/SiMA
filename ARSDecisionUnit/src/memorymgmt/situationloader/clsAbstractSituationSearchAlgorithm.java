/**
 * CHANGELOG
 *
 * 10.02.2013 ende - File created
 *
 */
package memorymgmt.situationloader;

import properties.clsProperties;
import base.datatypes.clsConcept;

/**
 * DOCUMENT (havlicek) - abstract implementation of the search algorithms interface for the situation search.
 * 
 * @author havlicek 10.02.2013, 15:06:56
 * 
 */
public abstract class clsAbstractSituationSearchAlgorithm implements itfSituationSearchAlgorithm {

    /** The basic concept */
    protected clsConcept moConcept;
    /** The properties used to manipulate the search */
    protected clsProperties moProperties;

    /*
     * (non-Javadoc)
     * 
     * @since 10.02.2013 15:06:56
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationSearchAlgorithm#init()
     */
    @Override
    public final void init(final clsConcept poConcept, final clsProperties poProperties) {
        moConcept = poConcept;
        moProperties = poProperties;
    }
    
}
