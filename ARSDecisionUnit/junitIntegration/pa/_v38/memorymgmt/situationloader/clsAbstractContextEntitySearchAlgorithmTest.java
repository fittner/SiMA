/**
 * CHANGELOG
 *
 * 22.02.2013 ende - File created
 *
 */
package pa._v38.memorymgmt.situationloader;

import static org.mockito.Mockito.mock;

import java.util.List;

import memorymgmt.situationloader.clsAbstractContextEntitySearchAlgorithm;

import org.junit.Test;

import base.datatypes.clsConcept;
import base.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (havlicek) - abstract test case for implementations of {@link clsAbstractContextEntitySearchAlgorithm}
 * 
 * @author havlicek 22.02.2013, 19:36:32
 * 
 */
public class clsAbstractContextEntitySearchAlgorithmTest {
    
    @Test(expected = NullPointerException.class)
    public void addWPMsArrayNullArgumentTest() {
        clsAbstractContextEntitySearchAlgorithm oAlgorithm = new clsTestAbstractContextEntitySearchAlgorithm(); 
        oAlgorithm.addWPMs((clsWordPresentationMesh[]) null);
    }
    
    @Test(expected = NullPointerException.class) 
    public void addWPMsListNullArgumentTest() {
        clsAbstractContextEntitySearchAlgorithm oAlgorithm = new clsTestAbstractContextEntitySearchAlgorithm(); 
        oAlgorithm.addWPMs((List<clsWordPresentationMesh>) null);
    }
    
    @Test
    public void addWPMsTest() {
        clsWordPresentationMesh oWpmMock = mock(clsWordPresentationMesh.class);
        
        clsAbstractContextEntitySearchAlgorithm oAlgorithm = new clsTestAbstractContextEntitySearchAlgorithm();
        
        
        oAlgorithm.addWPMs(oWpmMock);
        
    }

    /**
     * test helper class
     */
    private class clsTestAbstractContextEntitySearchAlgorithm extends clsAbstractContextEntitySearchAlgorithm {

        /* (non-Javadoc)
         *
         * @since 03.08.2013 15:14:06
         * 
         * @see pa._v38.memorymgmt.situationloader.itfContextEntitySearchAlgorithm#process()
         */
        @Override
        public clsConcept process() {
            return null;
        }
        
    }
}
