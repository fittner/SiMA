package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 15.02.2013, 20:08:39
 *
 */
public abstract class clsAbstractContextEntitySearchAlgorithm implements itfContextEntitySearchAlgorithm {

    @Override
    public final void addWPMs(final clsWordPresentationMesh... poWPMs) {
        for (clsWordPresentationMesh oWPM : poWPMs) {
            checkDataStructure(oWPM);
        }
    }

    /**
     * DOCUMENT (havlicek) - insert description
     *
     * @since 21.02.2013 21:18:59
     *
     * @param poDataStructurePA
     */
    protected abstract void checkDataStructure(clsDataStructurePA poDataStructurePA);
}
