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

import pa._v38.logger.clsLogger;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsQuadruppel;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (hinterleitner) - insert description
 * 
 * @author hinterleitner 19.05.2012, 16:28:04
 * 
 */
public class clsConcept {

	protected clsWordPresentationMesh moConceptMesh;

	private clsTriple<Integer, eDataType, eContentType> moActionTriple;
	private clsTriple<Integer, eDataType, eContentType> moEntityTriple;
	private clsTriple<Integer, eDataType, eContentType> moDistanceTriple;
	private clsTriple<Integer, eDataType, eContentType> moEmotionTriple;

	private Set<Integer> moVisitedWPMs;

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

	public void addWPMs(List<clsWordPresentationMesh> in) {
		for (clsWordPresentationMesh wpm : in) {
			checkDataStructure(wpm);
		}
	}

	public void addWPMs(clsWordPresentationMesh... in) {
		for (clsWordPresentationMesh wpm : in) {
			checkDataStructure(wpm);
		}
	}

	/**
	 * 
	 * DOCUMENT (ende) - Method to integrate clsWordPresentationMesh into the
	 * concept. This method splits up the wpm and forwards interesting parts to
	 * be integrated.
	 * 
	 * @since 16.09.2012 14:05:29
	 * 
	 * @param in
	 *            the wpms to be checked for interesting wpms
	 */
	public void checkDataStructure(clsDataStructurePA moDataStructurePA) {

		// TODO IH: use logger to print or disable test prints before pushing
		// System.out.println("Checking WPM: " + wpm.toString());
		if (null == moDataStructurePA
				|| moVisitedWPMs.contains(moDataStructurePA.hashCode())) {

		} else {
			clsLogger.jlog.info("checking " + moDataStructurePA);
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
	 * DOCUMENT (ende) - integrate a given wpm into the concept mesh. It is
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
			// System.out.println("Integrating WPM: " + mesh.toString());
			// FIXME IH: Use logger

			ArrayList<clsAssociation> associations = new ArrayList<clsAssociation>();
			associations.add(association);
			moConceptMesh.addInternalAssociations(associations);
		}
	}

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
			moConceptEntities
					.add(new clsQuadruppel<clsConcept.Entity, clsConcept.Action, clsConcept.Emotion, clsConcept.Distance>(
							entity, new Action(), new Emotion(), new Distance()));
		}
	}

	private void integrateDistance(Integer oDS_ID, String oEntityContent,
			String oDistanceContent) {
		integrateEntity(oDS_ID, oEntityContent);
		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (conceptEntity.a.moDS_ID == oDS_ID) {
				conceptEntity.d.setDistance((String) oDistanceContent);
				return;
			}
		}
	}

	private void integratePosition(Integer oDS_ID, String oEntityContent,
			String oPositionContent) {
		integrateEntity(oDS_ID, oEntityContent);

		for (clsQuadruppel<Entity, Action, Emotion, Distance> conceptEntity : moConceptEntities) {
			if (conceptEntity.a.moDS_ID == oDS_ID) {
				conceptEntity.d.setPosition((String) oPositionContent);
				return;
			}
		}
	}

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
	 * DOCUMENT (ende) - helper method to split up the check concerning
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
				clsLogger.jlog.info("found Position " + association);
				integratePosition(association.moAssociationElementA.moDS_ID,
						extractContentString(association.moAssociationElementA),
						extractContentString(association.moAssociationElementB));
			} else if (eContentType.DISTANCE
					.equals(association.moAssociationElementB.moContentType)) {
				clsLogger.jlog.info("found Distance " + association);
				integrateDistance(association.moAssociationElementA.moDS_ID,
						extractContentString(association.moAssociationElementA),
						extractContentString(association.moAssociationElementB));
			}
		}

		// if (association.moAssociationElementA instanceof
		// clsWordPresentationMesh) {
		// addWPMs((clsWordPresentationMesh) association.moAssociationElementA);
		// }
		// if (association.moAssociationElementB instanceof
		// clsWordPresentationMesh) {
		// addWPMs((clsWordPresentationMesh) association.moAssociationElementB);
		// }
	}

	/**
	 * DOCUMENT (ende) - Get the current content of the Concept together with
	 * its WPM.
	 * 
	 * @since 30.09.2012 17:09:03
	 * 
	 * @return The clsWorldPresentationMesh of the current concept.
	 */
	public clsWordPresentationMesh returnContent() {
		return moConceptMesh;
	}

	/**
	 * DOCUMENT (ende) - Get the current content of the Concept as String
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
		text += moConceptMesh.toString();
		if (null != moConceptMesh.moInternalAssociatedContent) {
			text += "[internal: "
					+ moConceptMesh.moInternalAssociatedContent.toString()
					+ "]";
		}
		if (null != moConceptMesh.moExternalAssociatedContent) {
			text += "[external: "
					+ moConceptMesh.moInternalAssociatedContent.toString()
					+ "]";
		}
		text += "\n";
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

	public class Entity {

		private int moDS_ID = -1;
		private String moEntity = "";

		public void setEntity(int oDS_ID, String oContent) {
			moDS_ID = oDS_ID;
			moEntity = oContent;
		}

		@Override
		public String toString() {
			return "(" + moEntity + ":" + moDS_ID + ")";
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Entity) {
				Entity o = (Entity) other;
				if (moDS_ID == o.moDS_ID) {
					if (moEntity.equals(o.moEntity)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	public class Action {
		
		@Override
		public String toString() {
			String text = "";

			return text;
		}
		
	}

	public class Emotion {
		
		@Override
		public String toString() {
			String text = "";

			return text;
		}
	}

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
