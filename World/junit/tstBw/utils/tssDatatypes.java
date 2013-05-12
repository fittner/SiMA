/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */

package tstBw.utils;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@SuiteClasses( {
	
	tstBw.utils.datatypes.tstMutableBoolean.class,
	tstBw.utils.datatypes.tstMutableDouble.class,
	tstBw.utils.datatypes.tstMutableFloat.class,
	tstBw.utils.datatypes.tstMutableInteger.class,
})
public class tssDatatypes {

}