package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 15.02.2013, 20:09:13
 * 
 */
public interface itfContextEntitySearchAlgorithm {

    /**
     * DOCUMENT (havlicek) - insert description
     *
     * @since 21.02.2013 21:18:43
     *
     * @param poWPMs
     */
    void addWPMs(clsWordPresentationMesh... poWPMs);
    
    /**
     * DOCUMENT (havlicek) - insert description
     *
     * @since 02.04.2013 20:58:47
     *
     * @return
     */
    clsConcept process();
}
