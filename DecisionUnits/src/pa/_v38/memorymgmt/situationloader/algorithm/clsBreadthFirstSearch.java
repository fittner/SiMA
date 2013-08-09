/**
 * CHANGELOG
 *
 * 03.08.2013 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader.algorithm;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.situationloader.clsAbstractContextEntitySearchAlgorithm;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 03.06.2013, 15:28:50
 * 
 */
public class clsBreadthFirstSearch extends clsAbstractContextEntitySearchAlgorithm {

    /* (non-Javadoc)
     *
     * @since 03.06.2013 15:28:50
     * 
     * @see pa._v38.memorymgmt.situationloader.itfContextEntitySearchAlgorithm#process()
     */
    @Override
    public clsConcept process() {        
        return moConcept;
    }

}
