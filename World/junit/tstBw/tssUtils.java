/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw;

/**
 * 
 * 
 * @author deutsch
 * 
 */

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses( {
	
	tstBw.utils.tssTools.class,
	tstBw.utils.tssConfig.class,
	tstBw.utils.tssDatatypes.class,
})

public class tssUtils {

}
