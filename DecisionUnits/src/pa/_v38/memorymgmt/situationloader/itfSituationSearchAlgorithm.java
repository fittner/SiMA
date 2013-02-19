/**
 * CHANGELOG
 *
 * 10.02.2013 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import java.util.List;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 10.02.2013, 14:18:45
 */
public interface itfSituationSearchAlgorithm {

    /**
     * DOCUMENT (ende) - insert description
     * 
     * @since 10.02.2013 16:28:35
     * 
     * @param poConcept
     */
    void init(clsConcept poConcept);

    /**
     * DOCUMENT (ende) - insert description
     * 
     * @since 10.02.2013 16:28:38
     * 
     * @return
     */
    List<clsSituation> process();
}
