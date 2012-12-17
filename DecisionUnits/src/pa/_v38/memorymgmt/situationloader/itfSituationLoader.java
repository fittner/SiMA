/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import config.clsProperties;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - the SituationLoader takes as input pregenerated context
 * entities and tries to provide a fitting situation from the stored knowledge
 * base.
 * 
 * @author havlicek 09.12.2012, 08:29:06
 * 
 */
public interface itfSituationLoader {

	
	
	/**
	 * DOCUMENT (havlicek) - set the clsConcept containing the context entities.
	 * 
	 * @since 09.12.2012 08:32:05
	 * 
	 * @param oSituationConcept
	 *            the filled clsConcept
	 */
	public void setContextEntities(clsConcept oSituationConcept);

	/**
	 * 
	 * DOCUMENT (havlicek) - fetch the fully initialized situation.
	 * 
	 * @since 09.12.2012 08:34:06
	 * 
	 * @param poPrefix the prefix to be passed down to the knowledgehandler
	 * @param poProps the properties to be passed down to the knowledgehandler
	 * @return a clsSituation initialized with the data from the context
	 *         entities.
	 */
	public clsSituation generate(String poPrefix, clsProperties poProps);
}
