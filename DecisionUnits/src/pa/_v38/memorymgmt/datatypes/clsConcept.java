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
	}

	public void addWPMs(List<clsWordPresentationMesh> in) {
		for (clsWordPresentationMesh wpm : in) {
			addWPMs(wpm);
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
	public void addWPMs(clsWordPresentationMesh... in) {

		for (clsWordPresentationMesh wpm : in) {
			System.out.println("Checking WPM: " + wpm.toString());
			if (moVisitedWPMs.contains(wpm.hashCode())) {

			} else {
				moVisitedWPMs.add(wpm.hashCode());
				if (eDataType.EMOTION.equals(wpm.moDataStructureType)) {
					integrateWPM(wpm, moEmotionTriple);
				} else if (eContentType.EMOTION.equals(wpm.moContentType)) {
					integrateWPM(wpm, moEmotionTriple);
				} else if (eContentType.BASICEMOTION.equals(wpm.moContentType)) {
					integrateWPM(wpm, moEmotionTriple);
				} else if (eContentType.ENTITY.equals(wpm.moContentType)) {
					integrateWPM(wpm, moEntityTriple);
				} else if (eContentType.ACTION.equals(wpm.moContentType)) {
					integrateWPM(wpm, moActionTriple);
				} else if (eContentType.DISTANCE.equals(wpm.moContentType)) {
					integrateWPM(wpm, moDistanceTriple);
				}

				for (clsAssociation externalAssociation : wpm.moExternalAssociatedContent) {
					checkAssociation(externalAssociation);
				}
				for (clsAssociation internalAssociation : wpm.moInternalAssociatedContent) {
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
	private void integrateWPM(clsWordPresentationMesh mesh,
			clsTriple<Integer, eDataType, eContentType> identifier) {

		clsWordPresentationMesh newMesh = new clsWordPresentationMesh(
				new clsTriple<Integer, eDataType, eContentType>(mesh.moDS_ID,
						mesh.moDataStructureType, mesh.moContentType),
				new ArrayList<clsAssociation>(), mesh.moContent);

		// TODO select fitting ePredicate
		clsAssociation association = new clsAssociationSecondary(identifier,
				moConceptMesh, newMesh, ePredicate.NONE);

		if (moConceptMesh.moInternalAssociatedContent.contains(newMesh)) {

		} else {
			System.out.println("Integrating WPM: " + mesh.toString());

			ArrayList<clsAssociation> associations = new ArrayList<clsAssociation>();
			associations.add(association);
			moConceptMesh.addInternalAssociations(associations);
		}
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
		if (association.moAssociationElementA instanceof clsWordPresentationMesh) {
			addWPMs((clsWordPresentationMesh) association.moAssociationElementA);
		}
		if (association.moAssociationElementB instanceof clsWordPresentationMesh) {
			addWPMs((clsWordPresentationMesh) association.moAssociationElementB);
		}
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

}
