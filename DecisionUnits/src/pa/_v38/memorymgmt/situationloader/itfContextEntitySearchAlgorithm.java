package pa._v38.memorymgmt.situationloader;

import java.util.List;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (havlicek) - the interface to implement the context entity search algorithms against.
 * 
 * @author havlicek 15.02.2013, 20:09:13
 * 
 */
public interface itfContextEntitySearchAlgorithm {

    /**
     * DOCUMENT (havlicek) - list of known algorithms 
     * 
     * @author havlicek
     * 03.06.2013, 12:43:16
     *
     */
    enum ALGORITHMS {
        DEPTH_FIRST,
        BREADTH_FIRST
    }    
    
    /**
     * DOCUMENT (havlicek) - method to set a concept which is then extended.
     *
     * @since 03.06.2013 16:18:25
     *
     * @param poConcept the concept that shall be extended.
     */
    void setConcept(clsConcept poConcept);
    
    /**
     * DOCUMENT (havlicek) - extend the searchspace for the current instance of the algorithm.
     *
     * @since 21.02.2013 21:18:43
     *
     * @param poWPMs the {@link clsWordPresentationMesh}es to be added to the searchspace.
     */
    void addWPMs(clsWordPresentationMesh... poWPMs);
    
    /**
     * DOCUMENT (havlicek) - extend the searchspace for the current instance of the algorithm.
     *
     * @param poWPMs the {@link clsWordPresentationMesh}es to be added to the searchspace.
     */
    void addWPMs(List<clsWordPresentationMesh> poWPMs);
    
    /**
     * DOCUMENT (havlicek) - generate the clsConcept based on the current searchspace. Long running process.
     *
     * @since 02.04.2013 20:58:47
     *
     * @return the found context entities combined into one {@link clsConcept}
     */
    clsConcept process();
}
