package pa._v38.memorymgmt.situationloader;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsConcept.clsAction;
import pa._v38.memorymgmt.datatypes.clsConcept.clsDistance;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEntity;
import pa._v38.memorymgmt.datatypes.clsConcept.clsDrive;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.situationloader.algorithm.clsDepthFirstSearch;
import pa._v38.tools.clsPentagon;
import pa._v38.memorymgmt.situationloader.itfContextEntitySearchAlgorithm.ALGORITHMS;
import pa._v38.tools.clsTriple;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - the concept loader class holds the functionality for the implemented {@link itfConceptLoader}
 * 
 * @author havlicek 15.02.2013, 20:08:52
 * 
 */
public class clsConceptLoader implements itfConceptLoader {

    /** The modified instance of a {@link clsConcept}. */
    private clsConcept moConcept;

    /** Internal Helper for the generation. */
    private Set<Integer> moVisitedDataStructures;

    /** */
    private clsTriple<Integer, eDataType, eContentType> moActionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ACTION);
    /** */
    private final clsTriple<Integer, eDataType, eContentType> moEntityTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ENTITY);
    /** */
    private final clsTriple<Integer, eDataType, eContentType> moDistanceTriple = new clsTriple<Integer, eDataType, eContentType>(1,
            eDataType.CONCEPT, eContentType.DISTANCE);
    /** */
    private final clsTriple<Integer, eDataType, eContentType> moEmotionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.EMOTION);
    
    /** */
    private final clsTriple<Integer, eDataType, eContentType> moDriveTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.DM);

    @Override
    public final clsConcept generate(final clsProperties poProperties, final clsDataStructurePA... poDataStructures) {
        moConcept = new clsConcept();
        for (clsDataStructurePA oDS : poDataStructures) {
            checkInputData(oDS);
        }
        return null;
    }

    @Override
    public final clsConcept extend(final clsConcept poConcept, final clsProperties poProperties, final clsDataStructurePA... poDataStructures) {
        moConcept = poConcept;
        for (clsDataStructurePA oDS : poDataStructures) {
            checkInputData(oDS);
        }
        return null;
    }
    
    
    private itfContextEntitySearchAlgorithm fetchAlgorithm(clsProperties poProperties) {
        String oPropValue = poProperties.getProperty(""+"_ContextSearchAlgoirthm", ALGORITHMS.DEPTH_FIRST.name());
        ALGORITHMS oSelection = ALGORITHMS.valueOf(oPropValue);
        
        switch (oSelection) {
        case DEPTH_FIRST:
            return new clsDepthFirstSearch();
        default:
            return new clsDepthFirstSearch();            
        }
        
    }
    

    /**
     * DOCUMENT (havlicek) - insert description
     * 
     * @since 21.02.2013 21:10:20
     * 
     * @param poDataStructure
     */
    private void checkInputData(final clsDataStructurePA poDataStructure) {
        if (poDataStructure instanceof clsWordPresentationMesh) {
            clsWordPresentationMesh oWPM = (clsWordPresentationMesh) poDataStructure;

        }
    }

    /**
     * DOCUMENT (havlicek) - searches the passed on WPMs for information that is of interest for creating the context entities.
     * 
     * @since 13.10.2012 16:59:05
     * 
     * @param poWPMs
     *            a list of clsWorldPresentationMesh that shall be recursively searched for associations of interest.
     */
    private void addWPMs(List<clsWordPresentationMesh> poWPMs) {
        for (clsWordPresentationMesh oWPM : poWPMs) {
            checkDataStructure(oWPM);
        }
    }

    /**
     * 
     * DOCUMENT (havlicek) - searches the passed on WPMs for information that is of interest for creating the context entities.
     * 
     * @since 13.10.2012 16:59:12
     * 
     * @param poWPMs
     *            one or more clsWorldPresentationMesh that shall be recursively searched for associations of interest.
     */
    private void addWPMs(clsWordPresentationMesh... poWPMs) {
        for (clsWordPresentationMesh oWPM : poWPMs) {
            checkDataStructure(oWPM);
        }
    }

    private void addMentalSituation(clsWordPresentationMesh poSituation) {
        String oSituation = poSituation.toString();
        for (clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> oConceptEntity : moConcept.getConceptEntities()) {
            if (oSituation.contains(oConceptEntity.a.getMoEntity())) {
                String[] oSituationArray = oSituation.split(":");
                oSituationArray[oSituationArray.length - 1] = oSituationArray[oSituationArray.length - 1].replace(";", "");
                oConceptEntity.b.setAction(oSituationArray[oSituationArray.length - 1]);
            }
        }
    }

    /**
     * 
     * DOCUMENT (havlicek) - Method to integrate clsWordPresentationMesh into the concept. This method splits up the wpm and forwards interesting
     * parts to be integrated.
     * 
     * @since 16.09.2012 14:05:29
     * 
     * @param in
     *            the wpms to be checked for interesting wpms
     */
    private void checkDataStructure(clsDataStructurePA poDataStructurePA) {

        if (null != poDataStructurePA && !moVisitedDataStructures.contains(poDataStructurePA.hashCode())) {
            // clsLogger.jlog.debug("checking " + moDataStructurePA);
            moVisitedDataStructures.add(poDataStructurePA.hashCode());
            if (eDataType.EMOTION.equals(poDataStructurePA.getMoDataStructureType())) {
                integrateDataStructure(poDataStructurePA, moEmotionTriple);
            } else if (eContentType.EMOTION.equals(poDataStructurePA.getMoContentType())) {
                integrateDataStructure(poDataStructurePA, moEmotionTriple);
            } else if (eContentType.BASICEMOTION.equals(poDataStructurePA.getMoContentType())) {
                integrateDataStructure(poDataStructurePA, moEmotionTriple);
            } else if (eContentType.ENTITY.equals(poDataStructurePA.getMoContentType())) {
                integrateDataStructure(poDataStructurePA, moEntityTriple);
            } else if (eContentType.ACTION.equals(poDataStructurePA.getMoContentType())) {
                integrateDataStructure(poDataStructurePA, moActionTriple);
            } else if (eContentType.DISTANCE.equals(poDataStructurePA.getMoContentType())) {
                integrateDataStructure(poDataStructurePA, moDistanceTriple);
            }
              else if (eContentType.DM.equals(poDataStructurePA.getMoContentType())) {
            integrateDataStructure(poDataStructurePA, moDistanceTriple);
            }
            
            if (poDataStructurePA instanceof clsWordPresentationMesh) {
                clsWordPresentationMesh oMesh = (clsWordPresentationMesh) poDataStructurePA;
                for (clsAssociation externalAssociation : oMesh.getExternalAssociatedContent()) {
                    checkAssociation(externalAssociation);
                }
                for (clsAssociation internalAssociation : oMesh.getMoInternalAssociatedContent()) {
                    checkAssociation(internalAssociation);
                }
            }
            if (poDataStructurePA instanceof clsThingPresentationMesh) {
                clsThingPresentationMesh oMesh = (clsThingPresentationMesh) poDataStructurePA;
                for (clsAssociation externalAssociation : oMesh.getExternalMoAssociatedContent()) {
                    checkAssociation(externalAssociation);
                }
                for (clsAssociation internalAssociation : oMesh.getMoInternalAssociatedContent()) {
                    checkAssociation(internalAssociation);
                }
            }

        }

    }

    /**
     * DOCUMENT (havlicek) - integrate a given wpm into the concept mesh. It is needed to seperate the interesting part from any associations in order
     * to save space.
     * 
     * @since 16.09.2012 17:28:17
     * 
     * @param mesh
     *            the WPM to be integrated.
     * @param poIdentifier
     *            the clsTriple to be used for identifying the WPM
     */
    private void integrateDataStructure(clsDataStructurePA poDataStructurePA, clsTriple<Integer, eDataType, eContentType> poIdentifier) {

        clsWordPresentationMesh newMesh = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(poDataStructurePA.getMoDS_ID(),
                poDataStructurePA.getMoDataStructureType(), poDataStructurePA.getMoContentType()), new ArrayList<clsAssociation>(), " ");
        newMesh.setMoDS_ID(0 + poDataStructurePA.getMoDS_ID());
        // TODO select fitting ePredicate
        clsAssociation oAssociation = new clsAssociationSecondary(poIdentifier, moConcept.returnContent(), newMesh, ePredicate.NONE);

        if (!moConcept.returnContent().getMoInternalAssociatedContent().contains(oAssociation)) {
            ArrayList<clsAssociation> oAssociations = new ArrayList<clsAssociation>();
            oAssociations.add(oAssociation);
            moConcept.returnContent().addInternalAssociations(oAssociations);
        }
    }

    /**
     * DOCUMENT (havlicek) - Method to integrate an Entity into the String representation of a ConceptEntity.
     * 
     * @since 13.10.2012 17:13:17
     * 
     * @param pnDS_ID
     *            the ID of the Entity
     * @param poContent
     *            the Name of the Entity
     */
    private void integrateEntity(Integer pnDS_ID, String poContent) {
        clsEntity entity = moConcept.new clsEntity();
        entity.setEntity(pnDS_ID, poContent);
        boolean entityKnown = false;
        for (clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> conceptEntity : moConcept.getConceptEntities()) {
            if (conceptEntity.a.equals(entity)) {
                entityKnown = true;
            }
        }
        if (!entityKnown) {
            // clsLogger.jlog.debug("Concept: found Entity " +
            // entity.toString());
            moConcept.getConceptEntities().add(
                    new clsPentagon<clsConcept.clsEntity, clsConcept.clsAction, clsConcept.clsEmotion, clsConcept.clsDistance, clsConcept.clsDrive>(entity,
                            moConcept.new clsAction(), moConcept.new clsEmotion(), moConcept.new clsDistance(), moConcept.new clsDrive()));
        }
    }

    /**
     * 
     * DOCUMENT (havlicek) - Method to integrate a Distance into the String representation of a ConceptEntity
     * 
     * @since 13.10.2012 17:14:23
     * 
     * @param pnDS_ID
     *            the ID of the Entity
     * @param poEntityContent
     *            the Name of the Entity
     * @param poDistanceContent
     *            the String representation of the Distance of the specified Entity
     */
    private void integrateDistance(Integer pnDS_ID, String poEntityContent, String poDistanceContent) {
        integrateEntity(pnDS_ID, poEntityContent);
        for (clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> conceptEntity : moConcept.getConceptEntities()) {
            if (conceptEntity.a.getMoDS_ID() == pnDS_ID) {
                // clsLogger.jlog.info("Concept: found Distance " +
                // oDistanceContent);
                conceptEntity.d.setDistance((String) poDistanceContent);
                return;
            }
        }
    }

    /**
     * DOCUMENT (havlicek) - Method to integrate a Position into the String representation of a ConceptEntity
     * 
     * @since 13.10.2012 17:24:28
     * 
     * @param pnDS_ID
     *            the ID of the Entity
     * @param poEntityContent
     *            the Name of the Entity
     * @param poPositionContent
     *            the String representation of the Position of the specified Entity
     */
    private void integratePosition(Integer pnDS_ID, String poEntityContent, String poPositionContent) {
        integrateEntity(pnDS_ID, poEntityContent);

        for (clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> conceptEntity : moConcept.getConceptEntities()) {
            if (conceptEntity.a.getMoDS_ID() == pnDS_ID) {
                // clsLogger.jlog.info("Concept: found Position " +
                // oPositionContent);
                conceptEntity.d.setPosition((String) poPositionContent);
                return;
            }
        }
    }

    /**
     * DOCUMENT (havlicek) - internal helper method to extract the String representing the Content from different extensions of the
     * clsDataStructurePA.
     * 
     * @since 13.10.2012 17:30:16
     * 
     * @param poDataStructure
     *            A class instance of a any extended class of clsDataStructurePA from which the Content should be extracted.
     * @return the String representation of the Content
     */
    private String extractContentString(clsDataStructurePA poDataStructure) {
        if (poDataStructure instanceof clsWordPresentation) {
            return ((clsWordPresentation) poDataStructure).getMoContent();
        } else if (poDataStructure instanceof clsWordPresentationMesh) {
            return ((clsWordPresentationMesh) poDataStructure).getMoContent();
        } else if (poDataStructure instanceof clsThingPresentation) {
            return ((clsThingPresentation) poDataStructure).getMoContent().toString();
        } else if (poDataStructure instanceof clsThingPresentationMesh) {
            return ((clsThingPresentationMesh) poDataStructure).getMoContent().toString();
        }

        return "";
    }

    /**
     * 
     * DOCUMENT (havlicek) - helper method to split up the check concerning Associations.
     * 
     * @since 16.09.2012 17:49:44
     * 
     * @param poAssociation
     */
    private void checkAssociation(clsAssociation poAssociation) {

        checkDataStructure(poAssociation.getMoAssociationElementA());
        checkDataStructure(poAssociation.getMoAssociationElementB());

        if (eContentType.ASSOCIATIONATTRIBUTE.equals(poAssociation.getMoContentType())) {
            if (eContentType.POSITION.equals(poAssociation.getMoAssociationElementB().getMoContentType())) {
                integratePosition(poAssociation.getMoAssociationElementA().getMoDS_ID(),
                        extractContentString(poAssociation.getMoAssociationElementA()),
                        extractContentString(poAssociation.getMoAssociationElementB()));
            } else if (eContentType.DISTANCE.equals(poAssociation.getMoAssociationElementB().getMoContentType())) {
                integrateDistance(poAssociation.getMoAssociationElementA().getMoDS_ID(),
                        extractContentString(poAssociation.getMoAssociationElementA()),
                        extractContentString(poAssociation.getMoAssociationElementB()));
            }
        }
    }

}
