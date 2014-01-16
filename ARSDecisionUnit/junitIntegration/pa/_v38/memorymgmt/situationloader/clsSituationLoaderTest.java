/**
 * CHANGELOG
 *
 * 22.02.2013 havlicek - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import memorymgmt.situationloader.clsSituationLoader;

import org.junit.Test;

import properties.clsProperties;

import base.datatypes.clsConcept;
import base.datatypes.clsSituation;

/**
 * DOCUMENT (havlicek) - test cases for the implementation of the itfSituationLoader
 * 
 * @author havlicek 22.02.2013, 17:57:24
 * 
 */
public class clsSituationLoaderTest {

    /**
     * Test method for {@link memorymgmt.situationloader.clsSituationLoader}
     * .
     */
    @Test
    public final void constructorTest() {
        clsConcept oConceptMock = mock(clsConcept.class);
        clsProperties oPropertiesMock = mock(clsProperties.class);
        // itfKnowledgeBaseHandler oKBHMock = mock(itfKnowledgeBaseHandler.class);
        final String oPrefix = "nothing";

        when(oConceptMock.isEmpty()).thenReturn(true);

        clsSituationLoader loader = new clsSituationLoader();

        assertNotNull(loader);
        assertNotNull(loader.hashCode());
        assertEquals(loader.hashCode(), loader.hashCode());
    }

    /**
     * Test method for {@link memorymgmt.situationloader.clsSituationLoader#generate()}.
     */
    @Test
    public final void generateEmptyTest() {
        clsConcept oConceptMock = mock(clsConcept.class);
        clsProperties oPropertiesMock = mock(clsProperties.class);
        // itfKnowledgeBaseHandler oKBHMock = mock(itfKnowledgeBaseHandler.class);
        final String oPrefix = "nothing";

        when(oConceptMock.isEmpty()).thenReturn(true);

        clsSituationLoader loader = new clsSituationLoader();

        final clsSituation result = loader.generate(oPrefix, oConceptMock, oPropertiesMock);

        assertNotNull(result);
    }

}
