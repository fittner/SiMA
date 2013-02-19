/**
 * CHANGELOG
 *
 * 10.02.2013 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 10.02.2013, 15:06:56
 * 
 */
public abstract class clsAbstractSituationSearchAlgorithm implements itfSituationSearchAlgorithm {

    /**
     * The basic concept
     */
    protected clsConcept moConcept;

    /*
     * (non-Javadoc)
     * 
     * @since 10.02.2013 15:06:56
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationSearchAlgorithm#init()
     */
    @Override
    public final void init(final clsConcept poConcept) {
        moConcept = poConcept;
    }

}
