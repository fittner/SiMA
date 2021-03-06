/**
 * CHANGELOG
 *
 * 10.02.2013 ende - File created
 *
 */
package memorymgmt.situationloader;

import java.util.List;

import properties.clsProperties;

import base.datatypes.clsConcept;
import base.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - this interface defines the functions of the search algorithms that will be used by 
 * the situation loader.
 * 
 * @author havlicek 10.02.2013, 14:18:45
 */
public interface itfSituationSearchAlgorithm {

    /**
     * DOCUMENT (havlicek) - a list of the known algorithms. 
     * 
     * @author havlicek
     * 03.06.2013, 12:45:58
     *
     */
    enum ALGORITHMS {
        GREEDY,
        CONSTRAIT_DRIVEN
    }
    
    /**
     * DOCUMENT (havlicek) - this will be used to set up the search algorithm before the search is started. 
     * NOTE this method may be used to init search trees and other setup options.
     * 
     * @since 10.02.2013 16:28:35
     * 
     * @param poConcept
     *            the concept holding the entities for the search.
     * @param poProperties
     *            properties passed on to the search.
     */
    void init(clsConcept poConcept, clsProperties poProperties);

    /**
     * DOCUMENT (havlicek) - will start the search and return the results if any.
     * 
     * @since 10.02.2013 16:28:38
     * 
     * @return the result of the search algorithm.
     */
    List<clsSituation> process();
}
