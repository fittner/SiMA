/**
 * CHANGELOG
 *
 * 23.02.2013 havlicek - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import config.clsProperties;

/**
 * DOCUMENT (havlicek) - unit tests for the itfConceptLoader
 * 
 * @author havlicek 23.02.2013, 11:23:32
 * 
 */
public class clsConceptLoaderTest {

    private itfConceptLoader moConceptLoader;
    
    @Mock
    private clsProperties moProperties;
    @Mock
    private clsDataStructurePA moDataStructure;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        moConceptLoader = new clsConceptLoader();
    }
    
    @Test
    public final void testGenerateWithEmptyDatastructure() {
        when(moProperties.getProperty(anyString(), anyString())).thenReturn(itfContextEntitySearchAlgorithm.ALGORITHMS.DEPTH_FIRST.name());
        clsConcept result = moConceptLoader.generate(moProperties, moDataStructure);        
        
        assertNotNull("returned result is valid?", result);
        assertEquals("expect a new clsConcept", new clsConcept(), result);        
    }

    @Test
    public final void testExtendWithEmptyDatastructure() {
        when(moProperties.getProperty(anyString(), anyString())).thenReturn(itfContextEntitySearchAlgorithm.ALGORITHMS.DEPTH_FIRST.name());
        clsConcept result = moConceptLoader.extend(new clsConcept(), moProperties, moDataStructure);        
        
        assertNotNull("returned result is valid?", result);
        assertEquals("expect a new clsConcept", new clsConcept(), result);
    }

}
