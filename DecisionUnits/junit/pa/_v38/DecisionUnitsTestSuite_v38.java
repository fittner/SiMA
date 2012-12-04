/**
 * CHANGELOG
 *
 * 15.08.2012 ende - File created
 *
 */
package pa._v38;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import pa._v38.memorymgmt.datatypes.DatatypesTestSuite;
import pa._v38.modules.ModulesTestSuite;
import pa._v38.tools.ToolsTestSuite;

/**
 * DOCUMENT (ende) - insert description
 * 
 * @author ende 15.08.2012, 15:38:01
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ ToolsTestSuite.class, ModulesTestSuite.class,
		DatatypesTestSuite.class })
public class DecisionUnitsTestSuite_v38 {

}
