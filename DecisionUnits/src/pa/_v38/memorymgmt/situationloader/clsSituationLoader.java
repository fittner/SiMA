/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.clsKnowledgeBaseHandlerFactory;
import pa._v38.memorymgmt.itfKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import pa._v38.memorymgmt.enums.eInformationRepresentationManagementType;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - loader class to init a situation with data from the ontology
 * 
 * @author havlicek 09.12.2012, 08:28:34
 * 
 */
public class clsSituationLoader implements itfSituationLoader {

    private clsConcept moConcept;
    private String moPrefix;
    private clsProperties moProperties;

    private itfKnowledgeBaseHandler moKnowledgeBase;

    /*
     * (non-Javadoc)
     * 
     * @since 09.12.2012 08:36:27
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#setContextEntities (pa._v38.memorymgmt.datatypes.clsConcept)
     */
    @Override
    public final void setContextEntities(final clsConcept poSituationConcept) {
        moConcept = poSituationConcept;
        // TODO havlicek - check the data for validity
    }

    /*
     * (non-Javadoc)
     * 
     * @since 17.12.2012 17:41:02
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#generate(java.lang .String, config.clsProperties)
     */
    @Override
    public final clsSituation generate() {
        // TODO havlicek - not yet null save here!
        clsSituation oFoundSituation = new clsSituation();
        moKnowledgeBase = clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement(
                eInformationRepresentationManagementType.ARSI10_MGMT.name(), moPrefix, moProperties);

        if (moConcept.isEmpty()) {
            return oFoundSituation;
        }

        return oFoundSituation;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 10.02.2013 16:46:15
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#setProperties(config .clsProperties)
     */
    @Override
    public final void setProperties(final clsProperties poProps) {
        moProperties = poProps;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 10.02.2013 16:46:15
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#setPrefix(java. lang.String)
     */
    @Override
    public final void setPrefix(final String poPrefix) {
        moPrefix = poPrefix;
    }

}
