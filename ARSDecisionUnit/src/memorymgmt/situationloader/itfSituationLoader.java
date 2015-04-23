/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package memorymgmt.situationloader;

import properties.clsProperties;
import base.datatypes.clsConcept;
import base.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - the SituationLoader takes as input pregenerated context entities and 
 * tries to provide a fitting situation from the stored knowledge base.
 * 
 * @author havlicek 09.12.2012, 08:29:06
 * 
 */
public interface itfSituationLoader {
    
    /**
     * DOCUMENT (havlicek) - fetch the fully initialized situation.
     * 
     * @since 09.12.2012 08:34:06
     * 
     * @param poPrefix
     *            the prefix to be used for the generated clsSituations data structures.
     * @param poConcept
     *            the clsCocnept holding the context entities the search will be based on.
     * @param poProps
     *            the properties passed on to the knowledge base handler and the search algorithms.
     * @return a clsSituation initialized with the data from the context entities.
     */
    clsSituation generate(String poPrefix, clsConcept poConcept, clsProperties poProps);
}
