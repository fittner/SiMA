/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 09.12.2012, 08:28:34
 * 
 */
public class clsSituationLoader implements itfSituationLoader {

	private clsConcept moConcept;
	
	/* (non-Javadoc)
	 *
	 * @since 09.12.2012 08:36:27
	 * 
	 * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#setContextEntities(pa._v38.memorymgmt.datatypes.clsConcept)
	 */
	@Override
	public void setContextEntities(clsConcept oSituationConcept) {
		moConcept = oSituationConcept;
		//TODO havlicek - check the data for validity
	}

	/* (non-Javadoc)
	 *
	 * @since 09.12.2012 08:36:27
	 * 
	 * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#generate()
	 */
	@Override
	public clsSituation generate() {
		clsSituation foundSituation = new clsSituation();
		
		
		
		// TODO (ende) - Auto-generated method stub
		return foundSituation;
	}

}
