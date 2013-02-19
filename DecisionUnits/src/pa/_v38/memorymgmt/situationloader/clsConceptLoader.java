package pa._v38.memorymgmt.situationloader;

import java.util.Set;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 15.02.2013, 20:08:52
 * 
 */
public class clsConceptLoader implements itfConceptLoader {

    private clsConcept moConcept;

    private Set<Integer> moVisitedDataStructures;

    private clsTriple<Integer, eDataType, eContentType> moActionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ACTION);
    private final clsTriple<Integer, eDataType, eContentType> moEntityTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ENTITY);
    private final clsTriple<Integer, eDataType, eContentType> moDistanceTriple = new clsTriple<Integer, eDataType, eContentType>(1,
            eDataType.CONCEPT, eContentType.DISTANCE);
    private final clsTriple<Integer, eDataType, eContentType> moEmotionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.EMOTION);

    @Override
    public final clsConcept generate(final clsDataStructurePA... poDataStructures) {
        for (clsDataStructurePA oDS : poDataStructures) {
            checkInputData(oDS);
        }
        return null; // To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public final clsConcept extend(final clsConcept poConcept, final clsDataStructurePA... poDataStructures) {
        moConcept = poConcept;
        for (clsDataStructurePA oDS : poDataStructures) {
            checkInputData(oDS);
        }
        return null; // To change body of implemented methods use File | Settings | File Templates.
    }

    private void checkInputData(final clsDataStructurePA poDataStructure) {
        if (poDataStructure instanceof clsWordPresentationMesh) {
            clsWordPresentationMesh oWPM = (clsWordPresentationMesh) poDataStructure;

        }
    }

}
