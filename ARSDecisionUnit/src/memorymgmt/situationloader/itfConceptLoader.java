package memorymgmt.situationloader;

import properties.clsProperties;
import base.datatypes.clsConcept;
import base.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (havlicek) - This interface specifies the methods for a Concept Loader. Such a loader will take an 
 * array of {@code clsDataStructurePA}s and will generate a the context entities from them. The result will be 
 * returned as {@code clsConcept} containing the result of the search over the given input.
 * 
 * @author havlicek 15.02.2013, 20:09:06
 * 
 */
public interface itfConceptLoader {

    /**
     * DOCUMENT (havlicek) - search for context entities in the given input and generate a new clsConcept from them.
     * 
     * @since 21.02.2013 21:18:25
     * 
     * @param poProperties
     * @param poDataStructures
     *            the meshes with their associations.
     * @return a filled clsConcept
     */
    clsConcept generate(clsProperties poProperties, clsDataStructurePA... poDataStructures);

    /**
     * DOCUMENT (havlicek) - merges additional information from a new search into an already filled clsConcept.
     * 
     * @since 21.02.2013 21:18:31
     * 
     * @param poConcept
     *            a clsConcept already containing some context entities.
     * @param poProperties
     * @param poDataStructures
     *            additional meshes with their associations that where not considered in the first run for example.
     * @return the merged clsConcept from the clsConcept given on input and the search results.
     */
    clsConcept extend(clsConcept poConcept, clsProperties poProperties, clsDataStructurePA... poDataStructures);
}
