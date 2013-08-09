package pa._v38.memorymgmt.situationloader;

import org.apache.log4j.Logger;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.situationloader.itfContextEntitySearchAlgorithm.ALGORITHMS;
import pa._v38.memorymgmt.situationloader.algorithm.clsBreadthFirstSearch;
import pa._v38.memorymgmt.situationloader.algorithm.clsDepthFirstSearch;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - the concept loader class holds the functionality for the implemented {@link itfConceptLoader}
 * 
 * @author havlicek 15.02.2013, 20:08:52
 * 
 */
public class clsConceptLoader implements itfConceptLoader {

    /** Specialized Logger for this class */
    private Logger moLog = Logger.getLogger(this.getClass());
    
    @Override
    public final clsConcept generate(final clsProperties poProperties, final clsDataStructurePA... poDataStructures) {
        clsAbstractContextEntitySearchAlgorithm oAlgorithm = (clsAbstractContextEntitySearchAlgorithm) fetchAlgorithm(poProperties);

        for (clsDataStructurePA oDS : poDataStructures) {
            oAlgorithm.checkInputData(oDS);
        }

        return oAlgorithm.process();
    }

    @Override
    public final clsConcept extend(final clsConcept poConcept, final clsProperties poProperties, 
            final clsDataStructurePA... poDataStructures) {
        clsAbstractContextEntitySearchAlgorithm oAlgorithm = (clsAbstractContextEntitySearchAlgorithm) fetchAlgorithm(poProperties);
        oAlgorithm.setConcept(poConcept);
        for (clsDataStructurePA oDS : poDataStructures) {
            oAlgorithm.checkInputData(oDS);
        }
        return oAlgorithm.process();
    }

    private itfContextEntitySearchAlgorithm fetchAlgorithm(clsProperties poProperties) {
        // NOTE: since default values for properties are broken, catch runtime / null pointer exception!
        String oPropValue = ALGORITHMS.DEPTH_FIRST.name(); 
        try {
            oPropValue = poProperties.getProperty("" + "_ContextSearchAlgoirthm", ALGORITHMS.DEPTH_FIRST.name());
        } catch (NullPointerException e) {
            moLog.debug("Messi impl of getProperty with default value fired NullPointerException", e);
        }
        
        ALGORITHMS oSelection = ALGORITHMS.valueOf(oPropValue);

        switch (oSelection) {
        case DEPTH_FIRST:
            return new clsDepthFirstSearch();
        case BREADTH_FIRST:
            return new clsBreadthFirstSearch();
        default:
            return new clsDepthFirstSearch();
        }
    }

}
