/**
 * tssSearchSpaceCreator.java: DecisionUnits - tstpa.memorymgmt.informationrepresentation.searchspace
 * 
 * @author zeilinger
 * 25.06.2010, 20:40:02
 */
package tstpa.memorymgmt.informationrepresentation.searchspace;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceCreator;
import pa.memorymgmt.informationrepresentation.searchspace.clsSearchSpaceOntologyLoader;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 25.06.2010, 20:40:02
 * 
 */
public class tssSearchSpaceCreator {
	@Test
	public void testCreateSearchSpace(){
		System.out.println(((clsSearchSpaceOntologyLoader)clsSearchSpaceCreator.createSearchSpace("/DecisionUnits/config/bw/pa.memory/AGENT_BASIC/BASIC.pprj")).toString()); 
		assertTrue(true);
	}
}
