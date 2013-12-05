/**
 * CHANGELOG
 *
 * 03.08.2012 ende - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * DOCUMENT (havlicek) - Test cases for {@link clsConcept}
 * 
 * @author havlicek 03.08.2012, 15:59:02
 * 
 */
public class clsConceptTest {

    private clsConcept moConcept;

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#clsConcept()}.
     */
    @Test
    public final void constructorTest() {
        moConcept = new clsConcept();
        assertEquals(clsConcept.class, moConcept.getClass());
        assertNotNull(moConcept);
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @Test
    public final void isEmptyNullTest() {
        moConcept = new clsConcept();
        assertEquals("New Concept should be empty.", true, moConcept.isEmpty());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void isEmptyWithEmptyMeshTest() {
        clsWordPresentationMesh oWpmMock = mock(clsWordPresentationMesh.class);
        List<clsAssociation> associationListMock = mock(ArrayList.class);
        when(oWpmMock.getExternalAssociatedContent()).thenReturn((ArrayList<clsAssociation>) associationListMock);
        when(associationListMock.isEmpty()).thenReturn(true);

        moConcept = new clsConcept();

        moConcept.moConceptMesh = oWpmMock;

        assertEquals("Empty List and nothing else should be empty.", true, moConcept.isEmpty());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void isEmptyWithInitMeshTest() {
        clsWordPresentationMesh oWpmMock = mock(clsWordPresentationMesh.class);
        List<clsAssociation> associationListMock = mock(ArrayList.class);
        when(oWpmMock.getExternalAssociatedContent()).thenReturn((ArrayList<clsAssociation>) associationListMock);
        when(associationListMock.isEmpty()).thenReturn(false);

        moConcept = new clsConcept();

        moConcept.moConceptMesh = oWpmMock;

        assertEquals("Set of results present so not empty.", false, moConcept.isEmpty());
    }

    @Test
    public final void equalsHashReflexivityWithEmptyTest() {
        moConcept = new clsConcept();

        assertEquals("The same object must be equal. <Reflexivity>", true, moConcept.equals(moConcept));
        assertEquals("The same object must have the same hash. <Reflexivity>", moConcept.hashCode(), moConcept.hashCode());
    }

    @Test
    public final void equalsHashSymmetryNewEmptyTest() {
        moConcept = new clsConcept();
        final clsConcept oConcept = new clsConcept();

        assertEquals("Two new concepts are equal.", true, moConcept.equals(oConcept));
        assertEquals("Two new concepts are equal. <Symmetry>", true, moConcept.equals(oConcept));
        assertEquals("Two new empty concepts have the same hash. <Symmetry>", oConcept.hashCode(), moConcept.hashCode());
    }

    // @Test
    // public final void equlasHashEmptyFilledNotSameTest() {
    // moConcept = new clsConcept();
    // final clsConcept oConcept =
    // clsConceptTestFactory.create().withConceptEntity(new clsEntity(), new clsAction(), new clsEmotion(), new clsDistance()).getObject();
    // }
    //
    //
    //
}
