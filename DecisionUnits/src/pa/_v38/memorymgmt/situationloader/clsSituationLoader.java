/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import pa._v38.memorymgmt.itfKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - loader class to init a situation with data from the ontology
 * 
 * @author havlicek 09.12.2012, 08:28:34
 * 
 */
public class clsSituationLoader implements itfSituationLoader {

    /** Stored instance of a {@link clsConcept} passed on during creation. */
    private clsConcept moConcept;
    /** The prefix to be used for the generated situations underlying data structure. */
    private String moPrefix;
    /**
     * The properties used to specify the search and other parameters: NOTE: add list of used properties here!
     * 
     * */
    private clsProperties moProperties;

    /** The handler to be used for accessing the knowledge base. */
    private itfKnowledgeBaseHandler moKnowledgeBase;

    /**
     * DOCUMENT (havlicek) - insert description
     * 
     * @since 19.02.2013 20:38:19
     * 
     * @param poConcept
     * @param poPrefix
     * @param poProperties
     * @param poKnowledgeBase
     */
    public clsSituationLoader(clsConcept poConcept, String poPrefix, clsProperties poProperties, itfKnowledgeBaseHandler poKnowledgeBase) {
        super();
        moConcept = poConcept;
        moPrefix = poPrefix;
        moProperties = poProperties;

        // clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement(
        // eInformationRepresentationManagementType.ARSI10_MGMT.name(), moPrefix, moProperties);
        moKnowledgeBase = poKnowledgeBase;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 17.12.2012 17:41:02
     * 
     * @see pa._v38.memorymgmt.situationloader.itfSituationLoader#generate(java.lang.String, pa._v38.memorymgmt.datatypes.clsConcept,
     * config.clsProperties)
     */
    @Override
    public clsSituation generate(String poPrefix, clsConcept poConcept, clsProperties poProps) {
        clsSituation oFoundSituation = new clsSituation();

        if (moConcept.isEmpty()) {
            return oFoundSituation;
        }

        return oFoundSituation;
    }

}
