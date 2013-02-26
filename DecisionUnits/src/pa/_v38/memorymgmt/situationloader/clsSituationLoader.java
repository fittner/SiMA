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
import pa._v38.memorymgmt.enums.eInformationRepresentationManagementType;
import pa._v38.memorymgmt.old.clsKnowledgeBaseHandlerFactory;
import pa._v38.memorymgmt.old.itfKnowledgeBaseHandler;

/**
 * DOCUMENT (havlicek) - loader class to init a situation with data from the
 * ontology
 * 
 * @author havlicek 09.12.2012, 08:28:34
 * 
 */
public class clsSituationLoader implements itfSituationLoader {

	private clsConcept moConcept;

	private itfKnowledgeBaseHandler moKnowledgeBase;

	/*
	 * (non-Javadoc)
	 * 
	 * @since 09.12.2012 08:36:27
	 * 
	 * @see
	 * pa._v38.memorymgmt.situationloader.itfSituationLoader#setContextEntities
	 * (pa._v38.memorymgmt.datatypes.clsConcept)
	 */
	@Override
	public void setContextEntities(clsConcept oSituationConcept) {
		moConcept = oSituationConcept;
		// TODO havlicek - check the data for validity
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @since 17.12.2012 17:41:02
	 * 
	 * @see
	 * pa._v38.memorymgmt.situationloader.itfSituationLoader#generate(java.lang
	 * .String, config.clsProperties)
	 */
	@Override
	public clsSituation generate(String poPrefix, clsProperties poProperties) {
		clsSituation foundSituation = new clsSituation();
		moKnowledgeBase = clsKnowledgeBaseHandlerFactory
				.createInformationRepresentationManagement(
						eInformationRepresentationManagementType.ARSI10_MGMT
								.name(), poPrefix, poProperties);
		
		if (moConcept.isEmpty()) {
			return foundSituation;
		}
		
		
		
		return foundSituation;
	}
}
