/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - the SituationLoader takes as input pregenerated context entities and tries to provide a fitting situation from the stored
 * knowledge base.
 * 
 * @author havlicek 09.12.2012, 08:29:06
 * 
 */
public interface itfSituationLoader {

    /**
     * DOCUMENT (havlicek) - set the clsConcept containing the context entities. Must be called before {@link generate()}.
     * 
     * @since 09.12.2012 08:32:05
     * 
     * @param poSituationConcept
     *            the filled clsConcept
     */
    void setContextEntities(clsConcept poSituationConcept);

    /**
     * DOCUMENT (havlicek) -
     * 
     * @since 10.02.2013 15:30:30
     * 
     * @param poProps
     *            Properties to be used by the situation loader and the associated algorithms.
     */
    void setProperties(clsProperties poProps);

    /**
     * DOCUMENT (havlicek) - insert description
     * 
     * @since 10.02.2013 15:30:44
     * 
     * @param poPrefix
     */
    void setPrefix(String poPrefix);

    /**
     * 
     * DOCUMENT (havlicek) - fetch the fully initialized situation.
     * 
     * @since 09.12.2012 08:34:06
     * 
     * @param poPrefix
     *            the prefix to be passed down to the knowledgehandler
     * @param poProps
     *            the properties to be passed down to the knowledgehandler
     * @return a clsSituation initialized with the data from the context entities.
     */
    clsSituation generate();
}
