package memorymgmt.situationloader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsConcept;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsConcept.clsAction;
import base.datatypes.clsConcept.clsDistance;
import base.datatypes.clsConcept.clsDrive;
import base.datatypes.clsConcept.clsEmotion;
import base.datatypes.clsConcept.clsEntity;
import base.datatypes.helpstructures.clsPentagon;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (havlicek) - insert description 
 * 
 * @author havlicek
 * 15.02.2013, 20:08:39
 *
 */
public abstract class clsAbstractContextEntitySearchAlgorithm implements itfContextEntitySearchAlgorithm {

    protected clsConcept moConcept;
    
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moActionTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ACTION);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moEntityTriple = new clsTriple<Integer, eDataType, eContentType>(1, eDataType.CONCEPT,
            eContentType.ENTITY);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moDistanceTriple = new clsTriple<Integer, eDataType, eContentType>(1,
            eDataType.CONCEPT, eContentType.DISTANCE);
    /** */
    protected final clsTriple<Integer, eDataType, eContentType> moEmotionTriple = new clsTriple<Integer, eDataType, eContentType>(1, 
            eDataType.CONCEPT, eContentType.EMOTION);
    
    /** Internal Helper for the generation. */
    private Set<Integer> moVisitedDataStructures = new HashSet<Integer>();

    
    public clsAbstractContextEntitySearchAlgorithm() {
        moConcept = new clsConcept();
    }
    
    @Override
    public final void setConcept(clsConcept poConcept) {
        moConcept = poConcept;
    }
         
    /**
     * DOCUMENT (havlicek) - verify input is of type {@link clsWordPresentationMesh} and if so add it for the computation.
     * 
     * @since 21.02.2013 21:10:20
     * 
     * @param poDataStructure
     */
    public void checkInputData(final clsDataStructurePA poDataStructure) {
        if (poDataStructure instanceof clsWordPresentationMesh) {
            clsWordPresentationMesh oWPM = (clsWordPresentationMesh) poDataStructure;
            addWPMs(oWPM);
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
    @Override
    public void addWPMs(List<clsWordPresentationMesh> poWPMs) {
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
    @Override
    public void addWPMs(clsWordPresentationMesh... poWPMs) {
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
            } else if (eContentType.EMOTION.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moEmotionTriple);
            } else if (eContentType.BASICEMOTION.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moEmotionTriple);
            } else if (eContentType.ENTITY.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moEntityTriple);
            } else if (eContentType.ACTION.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moActionTriple);
            } else if (eContentType.DISTANCE.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moDistanceTriple);
            } else if (eContentType.DM.equals(poDataStructurePA.getContentType())) {
                integrateDataStructure(poDataStructurePA, moDistanceTriple);
            }

            if (poDataStructurePA instanceof clsWordPresentationMesh) {
                clsWordPresentationMesh oMesh = (clsWordPresentationMesh) poDataStructurePA;
                for (clsAssociation externalAssociation : oMesh.getExternalAssociatedContent()) {
                    checkAssociation(externalAssociation);
                }
                for (clsAssociation internalAssociation : oMesh.getInternalAssociatedContent()) {
                    checkAssociation(internalAssociation);
                }
            }
            if (poDataStructurePA instanceof clsThingPresentationMesh) {
                clsThingPresentationMesh oMesh = (clsThingPresentationMesh) poDataStructurePA;
                for (clsAssociation externalAssociation : oMesh.getExternalAssociatedContent()) {
                    checkAssociation(externalAssociation);
                }
                for (clsAssociation internalAssociation : oMesh.getInternalAssociatedContent()) {
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

        clsWordPresentationMesh newMesh = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(poDataStructurePA.getDS_ID(),
                poDataStructurePA.getMoDataStructureType(), poDataStructurePA.getContentType()), new ArrayList<clsAssociation>(), " ");
        newMesh.setMoDS_ID(0 + poDataStructurePA.getDS_ID());
        // TODO select fitting ePredicate
        clsAssociation oAssociation = new clsAssociationSecondary(poIdentifier, moConcept.returnContent(), newMesh, ePredicate.NONE);

        if (!moConcept.returnContent().getInternalAssociatedContent().contains(oAssociation)) {
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
                    new clsPentagon<clsConcept.clsEntity, clsConcept.clsAction, clsConcept.clsEmotion, clsConcept.clsDistance, clsConcept.clsDrive>(
                            entity, moConcept.new clsAction(), moConcept.new clsEmotion(), moConcept.new clsDistance(), moConcept.new clsDrive()));
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
     * DOCUMENT (havlicek) - Method to integrate a Drive into the String representation of a ConceptEntity
     *
     * @since 04.07.2013 12:32:29
     *
     * @param pnDS_ID
     *          the ID of the Entity
     * @param poEntityContent
     *          the Name of the Entity
     * @param poDriveContent
     *          the String representation of the Drive of the specified Entity
     */
    private void integrateDrive(Integer pnDS_ID, String poEntityContent, String poDriveContent) {
        integrateEntity(pnDS_ID, poEntityContent);
        
        for (clsPentagon<clsEntity, clsAction, clsEmotion, clsDistance, clsDrive> conceptEntity : moConcept.getConceptEntities()) {
            if (conceptEntity.a.getMoDS_ID() == pnDS_ID) {
                conceptEntity.e.setDrive((String) poDriveContent);
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
    protected String extractContentString(clsDataStructurePA poDataStructure) {
        if (poDataStructure instanceof clsWordPresentation) {
            return ((clsWordPresentation) poDataStructure).getContent();
        } else if (poDataStructure instanceof clsWordPresentationMesh) {
            return ((clsWordPresentationMesh) poDataStructure).getContent();
        } else if (poDataStructure instanceof clsThingPresentation) {
            return ((clsThingPresentation) poDataStructure).getContent().toString();
        } else if (poDataStructure instanceof clsThingPresentationMesh) {
            return ((clsThingPresentationMesh) poDataStructure).getContent().toString();
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
    protected void checkAssociation(clsAssociation poAssociation) {

        checkDataStructure(poAssociation.getAssociationElementA());
        checkDataStructure(poAssociation.getAssociationElementB());

        if (eContentType.ASSOCIATIONATTRIBUTE.equals(poAssociation.getContentType())) {
            if (eContentType.POSITION.equals(poAssociation.getAssociationElementB().getContentType())) {
                integratePosition(poAssociation.getAssociationElementA().getDS_ID(),
                        extractContentString(poAssociation.getAssociationElementA()),
                        extractContentString(poAssociation.getAssociationElementB()));
            } else if (eContentType.DISTANCE.equals(poAssociation.getAssociationElementB().getContentType())) {
                integrateDistance(poAssociation.getAssociationElementA().getDS_ID(),
                        extractContentString(poAssociation.getAssociationElementA()),
                        extractContentString(poAssociation.getAssociationElementB()));
            } else if (eContentType.DRIVEDEMAND.equals(poAssociation.getAssociationElementB().getContentType())) {
                //TODO check needed drive value
                integrateDrive(poAssociation.getAssociationElementA().getDS_ID(), 
                        extractContentString(poAssociation.getAssociationElementA()),
                        extractContentString(poAssociation.getAssociationElementB()));
            }
        }
    }
}
