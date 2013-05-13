package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 15.02.2013, 20:08:39
 *
 */
public abstract class clsAbstractContextEntitySearchAlgorithm implements itfContextEntitySearchAlgorithm {

    protected clsConcept moConcept;
    
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moActionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ACTION);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moEntityTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ENTITY);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moDistanceTriple = new clsTriple<Integer, eDataType, eContentType>(1,
            eDataType.CONCEPT, eContentType.DISTANCE);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moEmotionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.EMOTION);
    
    public clsAbstractContextEntitySearchAlgorithm() {
        moConcept = new clsConcept();
    }
    
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
    protected void checkDataStructure(clsDataStructurePA poDataStructurePA) {
        
    }

    
}
