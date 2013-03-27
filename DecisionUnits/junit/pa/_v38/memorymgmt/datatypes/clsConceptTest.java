/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.planningHelpers.eDistance;
import du.enums.eEntityType;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 03.08.2012, 15:59:02
 * 
 */
public class clsConceptTest {

	private clsConcept _concept;

	/**
	 * DOCUMENT (havlicek) - insert description
	 * 
	 * @since 03.08.2012 15:59:02
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_concept = new clsConcept();
	}

	/**
	 * Test method for
	 * {@link pa._v38.memorymgmt.datatypes.clsConcept#clsConcept()}.
	 */
	@Test
	public final void constructorTest() {
		assertEquals(clsConcept.class, _concept.getClass());

	}

	@Test
	public final void convertSingleWPMTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.ENTITY, "cake");
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);

		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addEntityWPMTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.ENTITY, "cake");
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);

		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addDistanceWPMTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.DISTANCE, eDistance.MEDIUM.name());
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);

		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addActionWPMTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.ACTION, eAction.EAT.name());
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);

		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addEmotionWPMTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.EMOTION, eEmotionType.JOY.name());
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);

		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addWPMwithSelfLoopTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.EMOTION, eEmotionType.JOY.name());
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		clsAssociation loop = new clsAssociationSecondary(
				new clsTriple<Integer, eDataType, eContentType>(1,
						eDataType.EMOTION, eContentType.EMOTION), wpm, wpm,
				ePredicate.HASDISTANCE);
		wpm.moInternalAssociatedContent.add(loop);
		_concept.addWPMs(wpm);
		assertEquals(1,
				_concept.returnContent().moInternalAssociatedContent.size());
	}
	
	@Test
	public final void addWPMwithAssociationsTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.GOAL, eGoalType.DRIVESOURCE.name());
		clsPair<eContentType, Object> entityPair = new clsPair<eContentType, Object>(eContentType.ENTITY, eEntityType.CAKE.name());
		clsPair<eContentType, Object> actionPair = new clsPair<eContentType, Object>(eContentType.ACTION, eAction.EAT.name());
		
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		clsWordPresentationMesh wpmEntity = clsDataStructureGenerator.generateWPM(
				entityPair, new ArrayList<clsAssociation>());
		clsWordPresentationMesh wpmAction = clsDataStructureGenerator.generateWPM(
				actionPair, new ArrayList<clsAssociation>());
		
		clsAssociation association = new clsAssociationSecondary(new clsTriple<Integer, eDataType, eContentType>(1,
						eDataType.ACT, eContentType.ACTIONTYPE), wpmEntity, wpmAction, ePredicate.HASACTION);
		wpm.moInternalAssociatedContent.add(association);
		
		_concept.addWPMs(wpm);
		
		assertEquals(2, _concept.returnContent().moInternalAssociatedContent.size());
	}

	@Test
	public final void addNotValidTest() {
		clsPair<eContentType, Object> basicPair = new clsPair<eContentType, Object>(
				eContentType.GOAL, eEmotionType.JOY.name());
		clsWordPresentationMesh wpm = clsDataStructureGenerator.generateWPM(
				basicPair, new ArrayList<clsAssociation>());
		_concept.addWPMs(wpm);
		assertEquals(0,
				_concept.returnContent().moInternalAssociatedContent.size());
		assertEquals(0,
				_concept.returnContent().moExternalAssociatedContent.size());
	}

}
