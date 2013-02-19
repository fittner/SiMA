package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 15.02.2013, 20:09:06
 * 
 */
public interface itfConceptLoader {

    
    clsConcept generate(clsDataStructurePA... poDataStructures);

    clsConcept extend(clsConcept poConcept, clsDataStructurePA... poDataStructures);
}
