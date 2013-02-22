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

import org.junit.Before;
import org.junit.Test;

import pa._v38.memorymgmt.datatypes.clsConcept.clsAction;
import pa._v38.memorymgmt.datatypes.clsConcept.clsDistance;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEmotion;
import pa._v38.memorymgmt.datatypes.clsConcept.clsEntity;
import pa._v38.tools.clsQuadruppel;

/**
 * DOCUMENT (havlicek) - Test cases for {@link clsConcept}
 * 
 * @author havlicek 03.08.2012, 15:59:02
 * 
 */
public class clsConceptTest {

    private clsConcept moConcept;

    @Before
    public void setUp() throws Exception {

    }

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
    @Test
    public final void isEmptyOnlyInitTest() {
        List<clsQuadruppel<clsEntity, clsAction, clsEmotion, clsDistance>> quadListMock = mock(List.class);
        when(quadListMock.isEmpty()).thenReturn(true);

        moConcept = new clsConcept();

        moConcept.setConceptEntities(quadListMock);

        assertEquals("Empty List and nothing else should be empty.", true, moConcept.isEmpty());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @Test
    public final void isEmptyWithConceptEntitiesTest() {
        List<clsQuadruppel<clsEntity, clsAction, clsEmotion, clsDistance>> quadListMock = mock(List.class);
        when(quadListMock.isEmpty()).thenReturn(false);

        moConcept = new clsConcept();

        moConcept.setConceptEntities(quadListMock);

        assertEquals("Set of results present so not empty.", false, moConcept.isEmpty());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @Test
    public final void isEmptyWithEmptyMeshTest() {
        clsWordPresentationMesh wpmMock = mock(clsWordPresentationMesh.class);
        List<clsAssociation> associationListMock = mock(ArrayList.class);
        when(wpmMock.getExternalMoAssociatedContent()).thenReturn((ArrayList<clsAssociation>) associationListMock);
        when(associationListMock.isEmpty()).thenReturn(true);

        moConcept = new clsConcept();

        moConcept.moConceptMesh = wpmMock;

        assertEquals("Empty List and nothing else should be empty.", true, moConcept.isEmpty());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.datatypes.clsConcept#isEmpty()}.
     */
    @Test
    public final void isEmptyWithInitMeshTest() {
        clsWordPresentationMesh wpmMock = mock(clsWordPresentationMesh.class);
        List<clsAssociation> associationListMock = mock(ArrayList.class);
        when(wpmMock.getExternalMoAssociatedContent()).thenReturn((ArrayList<clsAssociation>) associationListMock);
        when(associationListMock.isEmpty()).thenReturn(false);

        moConcept = new clsConcept();

        moConcept.moConceptMesh = wpmMock;

        assertEquals("Set of results present so not empty.", false, moConcept.isEmpty());
    }
}
