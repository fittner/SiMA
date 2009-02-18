/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package tstBw.body;


/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses( {
	
	tstBw.body.internalSystems.tstInternalEnergyConsumption.class,
	tstBw.body.internalSystems.tstStomachSystem.class,
	tstBw.body.internalSystems.tstSlowMessengerSystem.class,
	//bw.body.???.class,
})

public class tssInternalSystems {

}
