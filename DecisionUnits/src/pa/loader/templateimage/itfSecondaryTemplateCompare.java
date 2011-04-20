/**
 * itfSecondaryTemplateCompare.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 24.10.2009, 23:47:04
 */
package pa.loader.templateimage;

import pa.datatypes.clsSecondaryInformation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 24.10.2009, 23:47:04
 * 
 */
@Deprecated
public interface itfSecondaryTemplateCompare {
	public void compareTemplateWith(clsSecondaryInformation poCurrentSec, Integer[] pnMatches);
	public boolean checkType(clsSecondaryInformation poCurrentSec);
	public int getNodeCount();
	public void getNodeCountRecursive(Integer[] poNodeCount);
}
