/**
 * itfSecondaryTemplateCompare.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 24.10.2009, 23:47:04
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 24.10.2009, 23:47:04
 * 
 */
public interface itfSecondaryTemplateCompare {
	public void compareTemplateWith(clsSecondaryInformation poCurrentSec, ArrayList<Boolean> poMatchList);
	public boolean checkType(clsSecondaryInformation poCurrentSec);
}
