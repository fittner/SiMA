/**
 * CHANGELOG
 *
 * 19.05.2012 hinterleitner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsQuadruppel;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (hinterleitner) - The clsConcept class represents the collected
 * ConceptEntities for one agent in one step.
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * 
 */
public class clsConcept {

	/**
	 * The Concept stored as a {@link clsWordPresentationMesh} to enable
	 * integration into the memory.
	 */
	protected clsWordPresentationMesh moConceptMesh;

	private clsTriple<Integer, eDataType, eContentType> moActionTriple;
	private clsTriple<Integer, eDataType, eContentType> moEntityTriple;
	private clsTriple<Integer, eDataType, eContentType> moDistanceTriple;
	private clsTriple<Integer, eDataType, eContentType> moEmotionTriple;

	/** Internal Helper for the generation of the clsConcept. */
	private Set<Integer> moVisitedWPMs;

	/** The String representations of the ConceptEntities */
	private List<clsQuadruppel<Entity, Action, Emotion, Distance>> moConceptEntities;

	/**
	 * DOCUMENT (hinterleitner) - Basic Constructor for a new Concept
	 * 
	 * @since 19.05.2012 16:28:39
	 * 
	 */
	public clsConcept() {
		moConceptMesh = new clsWordPresentationMesh(
				new clsTriple<Integer, eDataType, eContentType>(1,
						eDataType.CONCEPT, eContentType.UNDEFINED),
				new ArrayList<clsAssociation>(), "");

		moActionTriple = new clsTriple<Integer, eDataType, eContentType>(1,
				eDataType.CONCEPT, eContentType.ACTION);
		moEntityTriple = new clsTriple<Integer, eDataType, eContentType>(1,
				eDataType.CONCEPT, eContentType.ENTITY);
		moDistanceTriple = new clsTriple<Integer, eDataType, eContentType>(1,
				eDataType.CONCEPT, eContentType.DISTANCE);
		moEmotionTriple = new clsTriple<Integer, eDataType, eContentType>(1,
				eDataType.CONCEPT, eContentType.EMOTION);
		
		moVisitedWPMs = new HashSet<Integer>();

		moConceptEntities = new ArrayList<clsQuadruppel<Entity, Action, Emotion, Distance>>();
	}

	/**
	 * DOCUMENT (havlicek) - insert description
	 * 
	 * @since 13.10.2012 16:59:05
	 * 
	 * @param oWPMs
	 */
	public void addWPMs(List<clsWordPresentationMesh> oWPMs) {
		for (clsWordPresentationMesh wpm : oWPMs) {
			checkDataStructure(wpm);
		}
	}

	/**
	 * 
	 * DOCUMENT (havlicek) - insert description
	 * 
	 * @since 13.10.2012 16:59:12
	 * 
	 * @param oWPMs
	 */
	public void addWPMs(clsWordPresentationMesh... oWPMs) {
		for (clsWordPresentationMesh wpm : oWPMs) {
			checkDataStructure(wpm);
		}
	}

	public void addMentalSituation(clsWordPresentationMesh oMentalSituation) {
		String situation = oMentalSituation.toString();
		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (situation.contains(conceptEntity.a.moEntity)) {
				String[] situationArray = situation.split(":");
				situationArray[situationArray.length-1] = situationArray[situationArray.length-1].replace(";", "");
				conceptEntity.b.setAction(situationArray[situationArray.length-1]);
			}
		}
	}
	
	/**
	 * 
	 * DOCUMENT (havlicek) - Method to integrate clsWordPresentationMesh into
	 * the concept. This method splits up the wpm and forwards interesting parts
	 * to be integrated.
	 * 
	 * @since 16.09.2012 14:05:29
	 * 
	 * @param in
	 *            the wpms to be checked for interesting wpms
	 */
	public void checkDataStructure(clsDataStructurePA moDataStructurePA) {

		if (null == moDataStructurePA
				|| moVisitedWPMs.contains(moDataStructurePA.hashCode())) {

		} else {
			// clsLogger.jlog.debug("checking " + moDataStructurePA);
			moVisitedWPMs.add(moDataStructurePA.hashCode());
			if (eDataType.EMOTION.equals(moDataStructurePA.moDataStructureType)) {
				integrateDataStructure(moDataStructurePA, moEmotionTriple);
			} else if (eContentType.EMOTION
					.equals(moDataStructurePA.moContentType)) {
				integrateDataStructure(moDataStructurePA, moEmotionTriple);
			} else if (eContentType.BASICEMOTION
					.equals(moDataStructurePA.moContentType)) {
				integrateDataStructure(moDataStructurePA, moEmotionTriple);
			} else if (eContentType.ENTITY
					.equals(moDataStructurePA.moContentType)) {
				integrateDataStructure(moDataStructurePA, moEntityTriple);
			} else if (eContentType.ACTION
					.equals(moDataStructurePA.moContentType)) {
				integrateDataStructure(moDataStructurePA, moActionTriple);
			} else if (eContentType.DISTANCE
					.equals(moDataStructurePA.moContentType)) {
				integrateDataStructure(moDataStructurePA, moDistanceTriple);
			}
			if (moDataStructurePA instanceof clsWordPresentationMesh) {
				clsWordPresentationMesh mesh = (clsWordPresentationMesh) moDataStructurePA;
				for (clsAssociation externalAssociation : mesh.moExternalAssociatedContent) {
					checkAssociation(externalAssociation);
				}
				for (clsAssociation internalAssociation : mesh.moInternalAssociatedContent) {
					checkAssociation(internalAssociation);
				}
			}
			if (moDataStructurePA instanceof clsThingPresentationMesh) {
				clsThingPresentationMesh mesh = (clsThingPresentationMesh) moDataStructurePA;
				for (clsAssociation externalAssociation : mesh.moExternalAssociatedContent) {
					checkAssociation(externalAssociation);
				}
				for (clsAssociation internalAssociation : mesh.moInternalAssociatedContent) {
					checkAssociation(internalAssociation);
				}
			}

		}

	}

	/**
	 * DOCUMENT (havlicek) - integrate a given wpm into the concept mesh. It is
	 * needed to seperate the interesting part from any associations in order to
	 * save space.
	 * 
	 * @since 16.09.2012 17:28:17
	 * 
	 * @param mesh
	 *            the WPM to be integrated.
	 * @param identifier
	 *            the clsTriple to be used for identifying the WPM
	 */
	private void integrateDataStructure(clsDataStructurePA moDataStructurePA,
			clsTriple<Integer, eDataType, eContentType> identifier) {

		clsWordPresentationMesh newMesh = new clsWordPresentationMesh(
				new clsTriple<Integer, eDataType, eContentType>(
						moDataStructurePA.moDS_ID,
						moDataStructurePA.moDataStructureType,
						moDataStructurePA.moContentType),
				new ArrayList<clsAssociation>(), " ");
		newMesh.moDS_ID = 0 + moDataStructurePA.moDS_ID;
		// TODO select fitting ePredicate
		clsAssociation association = new clsAssociationSecondary(identifier,
				moConceptMesh, newMesh, ePredicate.NONE);

		if (moConceptMesh.moInternalAssociatedContent.contains(association)) {

		} else {
			ArrayList<clsAssociation> associations = new ArrayList<clsAssociation>();
			associations.add(association);
			moConceptMesh.addInternalAssociations(associations);
		}
	}

	/**
	 * DOCUMENT (havlicek) - Method to integrate an Entity into the String
	 * representation of a ConceptEntity.
	 * 
	 * @since 13.10.2012 17:13:17
	 * 
	 * @param oDS_ID
	 *            the ID of the Entity
	 * @param oContent
	 *            the Name of the Entity
	 */
	private void integrateEntity(Integer oDS_ID, String oContent) {
		Entity entity = new Entity();
		entity.setEntity(oDS_ID, oContent);
		boolean entityKnown = false;
		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (conceptEntity.a.equals(entity)) {
				entityKnown = true;
			}
		}
		if (!entityKnown) {
			// clsLogger.jlog.debug("Concept: found Entity " +
			// entity.toString());
			moConceptEntities
					.add(new clsQuadruppel<clsConcept.Entity, clsConcept.Action, clsConcept.Emotion, clsConcept.Distance>(
							entity, new Action(), new Emotion(), new Distance()));
		}
	}

	/**
	 * 
	 * DOCUMENT (havlicek) - Method to integrate a Distance into the String
	 * representation of a ConceptEntity
	 * 
	 * @since 13.10.2012 17:14:23
	 * 
	 * @param oDS_ID
	 *            the ID of the Entity
	 * @param oEntityContent
	 *            the Name of the Entity
	 * @param oDistanceContent
	 *            the String representation of the Distance of the specified
	 *            Entity
	 */
	private void integrateDistance(Integer oDS_ID, String oEntityContent,
			String oDistanceContent) {
		integrateEntity(oDS_ID, oEntityContent);
		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (conceptEntity.a.moDS_ID == oDS_ID) {
				// clsLogger.jlog.info("Concept: found Distance " +
				// oDistanceContent);
				conceptEntity.d.setDistance((String) oDistanceContent);
				return;
			}
		}
	}

	/**
	 * DOCUMENT (havlicek) - Method to integrate a Position into the String
	 * representation of a ConceptEntity
	 * 
	 * @since 13.10.2012 17:24:28
	 * 
	 * @param oDS_ID
	 *            the ID of the Entity
	 * @param oEntityContent
	 *            the Name of the Entity
	 * @param oPositionContent
	 *            the String representation of the Position of the specified
	 *            Entity
	 */
	private void integratePosition(Integer oDS_ID, String oEntityContent,
			String oPositionContent) {
		integrateEntity(oDS_ID, oEntityContent);

		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (conceptEntity.a.moDS_ID == oDS_ID) {
				// clsLogger.jlog.info("Concept: found Position " +
				// oPositionContent);
				conceptEntity.d.setPosition((String) oPositionContent);
				return;
			}
		}
	}

	/**
	 * DOCUMENT (ende) - internal helper method to extract the String
	 * representing the Content from different extensions of the
	 * clsDataStructurePA.
	 * 
	 * @since 13.10.2012 17:30:16
	 * 
	 * @param oDataStructure
	 *            A class instance of a any extended class of clsDataStructurePA
	 *            from which the Content should be extracted.
	 * @return the String representation of the Content
	 */
	private String extractContentString(clsDataStructurePA oDataStructure) {
		if (oDataStructure instanceof clsWordPresentation) {
			return ((clsWordPresentation) oDataStructure).moContent;
		} else if (oDataStructure instanceof clsWordPresentationMesh) {
			return ((clsWordPresentationMesh) oDataStructure).moContent;
		} else if (oDataStructure instanceof clsThingPresentation) {
			return ((clsThingPresentation) oDataStructure).getMoContent()
					.toString();
		} else if (oDataStructure instanceof clsThingPresentationMesh) {
			return ((clsThingPresentationMesh) oDataStructure).getMoContent()
					.toString();
		}

		return "";
	}

	/**
	 * 
	 * DOCUMENT (havlicek) - helper method to split up the check concerning
	 * Associations.
	 * 
	 * @since 16.09.2012 17:49:44
	 * 
	 * @param association
	 */
	private void checkAssociation(clsAssociation association) {

		checkDataStructure(association.moAssociationElementA);
		checkDataStructure(association.moAssociationElementB);

		if (eContentType.ASSOCIATIONATTRIBUTE.equals(association.moContentType)) {
			if (eContentType.POSITION
					.equals(association.moAssociationElementB.moContentType)) {
				integratePosition(
						association.moAssociationElementA.moDS_ID,
						extractContentString(association.moAssociationElementA),
						extractContentString(association.moAssociationElementB));
			} else if (eContentType.DISTANCE
					.equals(association.moAssociationElementB.moContentType)) {
				integrateDistance(
						association.moAssociationElementA.moDS_ID,
						extractContentString(association.moAssociationElementA),
						extractContentString(association.moAssociationElementB));
			}
		}
	}

	/**
	 * DOCUMENT (havlicek) - Get the current content of the Concept together
	 * with its WPM.
	 * 
	 * @since 30.09.2012 17:09:03
	 * 
	 * @return The clsWorldPresentationMesh of the current concept.
	 */
	public clsWordPresentationMesh returnContent() {
		return moConceptMesh;
	}

	/**
	 * DOCUMENT (havlicek) - Get the current content of the Concept as String
	 * 
	 * @since 30.09.2012 17:10:14
	 * 
	 * @return A String representing the current concept.
	 */
	public String returnContentString() {
		return moConceptMesh.moContent;
	}

	@Override
	public String toString() {
		String text = "";
//		text += moConceptMesh.toString();
//		if (null != moConceptMesh.moInternalAssociatedContent) {
//			text += "[internal: "
//					+ moConceptMesh.moInternalAssociatedContent.toString()
//					+ "]";
//		}
//		if (null != moConceptMesh.moExternalAssociatedContent) {
//			text += "[external: "
//					+ moConceptMesh.moInternalAssociatedContent.toString()
//					+ "]";
//		}
//		text += "\n";
		text += moConceptEntities.toString();
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @since 25.08.2012 13:23:05
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((moConceptMesh == null) ? 0 : moConceptMesh.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @since 25.08.2012 13:23:05
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		clsConcept other = (clsConcept) obj;
		if (moConceptMesh == null) {
			if (other.moConceptMesh != null)
				return false;
		} else if (!moConceptMesh.equals(other.moConceptMesh))
			return false;
		return true;
	}

	/**
	 * DOCUMENT (havlicek) - internal representation of an entity.
	 * 
	 * @author havlicek 13.10.2012, 15:25:03
	 * 
	 */
	public class Entity {

		private int moDS_ID = -1;
		private String moEntity = "";

		public void setEntity(int oDS_ID, String oContent) {
			moDS_ID = oDS_ID;
			moEntity = oContent;
		}

		@Override
		public String toString() {
			String text = "(";
			text += moEntity;
			text += ":";
			text += moDS_ID;
			text += ")";
			return text;
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Entity) {
				Entity o = (Entity) other;
				boolean isEqual = true;
				if (moDS_ID != o.moDS_ID) {
					isEqual = false;
				}
				if (!moEntity.equals(o.moEntity)) {
					isEqual = false;

				}
				return isEqual;
			}
			return false;
		}
	}

	/**
	 * DOCUMENT (havlicek) - internal representation of an action.
	 * 
	 * @author havlicek 13.10.2012, 15:24:48
	 * 
	 */
	public class Action {

		private String moAction = "";

		/**
		 * @since 13.10.2012 15:27:57
		 * 
		 * @return the moAction
		 */
		public String getAction() {
			return moAction;
		}

		/**
		 * @since 13.10.2012 15:27:57
		 * 
		 * @param moAction
		 *            the moAction to set
		 */
		public void setAction(String moAction) {
			this.moAction = moAction;
		}

		@Override
		public String toString() {
			String text = "";
			text += moAction;
			return text;
		}

	}

	/**
	 * DOCUMENT (havlicek) - internal representation of an emotion.
	 * 
	 * @author havlicek 13.10.2012, 15:24:33
	 * 
	 */
	public class Emotion {

		private String moEmotion = "";

		@Override
		public String toString() {
			String text = "";
			text += moEmotion;
			return text;
		}

		/**
		 * @since 13.10.2012 15:27:11
		 * 
		 * @return the moEmotion
		 */
		public String getMoEmotion() {
			return moEmotion;
		}

		/**
		 * @since 13.10.2012 15:27:11
		 * 
		 * @param moEmotion
		 *            the moEmotion to set
		 */
		public void setMoEmotion(String moEmotion) {
			this.moEmotion = moEmotion;
		}
	}

	/**
	 * DOCUMENT (havlicek) - internal representation of a distance.
	 * 
	 * @author havlicek 13.10.2012, 15:23:53
	 * 
	 */
	public class Distance {

		private String moDistance = "";
		private String moPosition = "";

		/**
		 * @since 13.10.2012 12:01:28
		 * 
		 * @return the moPosition
		 */
		public String getPosition() {
			return moPosition;
		}

		/**
		 * @since 13.10.2012 12:01:28
		 * 
		 * @param moPosition
		 *            the moPosition to set
		 */
		public void setPosition(String moPosition) {
			this.moPosition = moPosition;
		}

		/**
		 * @since 13.10.2012 12:01:42
		 * 
		 * @return the moDistance
		 */
		public String getDistance() {
			return moDistance;
		}

		/**
		 * @since 13.10.2012 12:01:42
		 * 
		 * @param moDistance
		 *            the moDistance to set
		 */
		public void setDistance(String moDistance) {
			this.moDistance = moDistance;
		}

		@Override
		public String toString() {
			String text = "(";
			text += moDistance;
			text += ":";
			text += moPosition;
			text += ")";
			return text;
		}

	}
}
