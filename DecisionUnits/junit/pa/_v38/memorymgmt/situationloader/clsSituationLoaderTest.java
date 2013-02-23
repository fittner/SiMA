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

import org.junit.Before;
import org.junit.Test;

import pa._v38.memorymgmt.itfKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - insert description
 * 
 * @author havlicek 22.02.2013, 17:57:24
 * 
 */
public class clsSituationLoaderTest {

    /**
     * DOCUMENT (havlicek) - insert description
     * 
     * @since 22.02.2013 17:57:24
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    /**
     * Test method for
     * {@link pa._v38.memorymgmt.situationloader.clsSituationLoader# clsSituationLoader(pa._v38.memorymgmt.datatypes.clsConcept, java.lang.String, config.clsProperties, pa._v38.memorymgmt.itfKnowledgeBaseHandler)}
     * .
     */
    @Test
    public final void constructorTest() {
        clsConcept oConceptMock = mock(clsConcept.class);
        clsProperties oPropertiesMock = mock(clsProperties.class);
        itfKnowledgeBaseHandler oKBHMock = mock(itfKnowledgeBaseHandler.class);
        final String oPrefix = "nothing";

        when(oConceptMock.isEmpty()).thenReturn(true);

        clsSituationLoader loader = new clsSituationLoader(oConceptMock, oPrefix, oPropertiesMock, oKBHMock);

        assertNotNull(loader);
        assertNotNull(loader.hashCode());
        assertEquals(loader.hashCode(), loader.hashCode());
    }

    /**
     * Test method for {@link pa._v38.memorymgmt.situationloader.clsSituationLoader#generate()}.
     */
    @Test
    public final void generateEmptyTest() {
        clsConcept oConceptMock = mock(clsConcept.class);
        clsProperties oPropertiesMock = mock(clsProperties.class);
        itfKnowledgeBaseHandler oKBHMock = mock(itfKnowledgeBaseHandler.class);
        final String oPrefix = "nothing";

        when(oConceptMock.isEmpty()).thenReturn(true);

        clsSituationLoader loader = new clsSituationLoader(oConceptMock, oPrefix, oPropertiesMock, oKBHMock);

        final clsSituation result = loader.generate(oPrefix, oConceptMock, oPropertiesMock);

        assertNotNull(result);
    }

}
