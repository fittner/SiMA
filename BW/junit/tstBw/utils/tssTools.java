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
	
	tstBw.utils.tools.tstContentColumn.class,
	tstBw.utils.tools.tstNutritionLevel.class,	
	tstBw.utils.tools.tstFillLevel.class,
	tstBw.utils.tools.tstFood.class,
	tstBw.utils.tools.tstDecayColumn.class,
})
public class tssTools {

}