/**
 * tssOntologyLoader.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 11.06.2010, 08:08:46
 */
package tstpa.memorymgmt.informationrepresentation.searchspace;

import static org.junit.Assert.assertTrue;

import java.util.Hashtable;
import java.util.List;

import org.junit.Test;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.enums.eDataType;
import pa.memorymgmt.informationrepresentation.searchspace.clsOntologyLoader;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 11.06.2010, 08:08:46
 * 
 */
public class tssOntologyLoader {
//	@Test
//	public void testOWLAPIOntology(){
//		OWLOntology OntologyTEST = clsOntologyLoader.loadOWLAPIOntology();  
//		assertTrue(OntologyTEST != null); 
//	}	
	
//	@Test
//	public void testJenaOntology(){
//		Model OntologyTEST = clsOntologyLoader.loadJenaOntology();  
//		assertTrue(OntologyTEST != null);
//	}
	
	@Test
	public void testProtegeDB(){
		clsOntologyLoader.loadOntology(new Hashtable<eDataType, List<clsDataStructurePA>>(), "./config/bw/pa.memory/AGENT_BASIC/BASIC.pprj");   
		assertTrue(true);
	}
}
