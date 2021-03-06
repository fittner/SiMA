/**
 * CHANGELOG
 *
 * 09.12.2012 ende - File created
 *
 */
package memorymgmt.situationloader;

import java.util.List;
import java.util.Random;

import properties.clsProperties;

import memorymgmt.situationloader.algorithm.clsConstraintDrivenSearch;
import memorymgmt.situationloader.algorithm.clsGreedySearch;
import memorymgmt.situationloader.itfSituationSearchAlgorithm.ALGORITHMS;
import base.datatypes.clsConcept;
import base.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - loader class to init a situation with data from the ontology
 * 
 * @author havlicek 09.12.2012, 08:28:34
 * 
 */
public class clsSituationLoader implements itfSituationLoader {

    /** The handler to be used for accessing the knowledge base. */
    // private itfKnowledgeBaseHandler moKnowledgeBase;

    /**
     * DOCUMENT (havlicek) - the default constructor for the clsSituationLaoder
     * 
     * @since 17.12.2012 17:35:21 *
     */
    public clsSituationLoader() {

        // clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement(
        // eInformationRepresentationManagementType.ARSI10_MGMT.name(), moPrefix, moProperties);
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

        if (poConcept.isEmpty()) {
            return oFoundSituation;
        }
        itfSituationSearchAlgorithm oAlgorithm = null;
        if (poProps.containsKey(poPrefix)) {
            String oAlgorithmSelection = poProps.getProperty(poPrefix + "_ALGORITHM", ALGORITHMS.GREEDY.name());
            ALGORITHMS selection = ALGORITHMS.valueOf(oAlgorithmSelection);
            switch (selection) {
            case GREEDY:
                oAlgorithm = new clsGreedySearch();
                break;
            case CONSTRAIT_DRIVEN:
                oAlgorithm = new clsConstraintDrivenSearch();
                break;
            default:
                oAlgorithm = getDefaultAlgorithm();
                break;
            }
        } else {
            oAlgorithm = getDefaultAlgorithm();
        }

        oAlgorithm.init(poConcept, poProps);

        List<clsSituation> oResultList = oAlgorithm.process();
        if (!oResultList.isEmpty()) {
            return oResultList.get(new Random().nextInt(oResultList.size()));
        }
        return oFoundSituation;
    }

    private itfSituationSearchAlgorithm getDefaultAlgorithm() {
        return new clsGreedySearch();
    }

}
