/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw;

/**
 * Test suite for all bw.sim classes
 * 
 * @author deutsch
 * 
 */
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses( {
	
	tstBw.sim.tstBWMain.class,
})

public class tssSim {

}


