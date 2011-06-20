/**
 * itfSecondaryTemplateCompare.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 24.10.2009, 23:47:04
 */
package pa._v19.loader.templateimage;

import pa._v19.datatypes.clsSecondaryInformation;

/**
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
