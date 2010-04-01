/**
 * itfTemplateComparable.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:13:41
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:13:41
 * 
 */
public interface itfPrimaryTemplateComparable {

	public void compareTemplateWith(clsPrimaryInformation poCurrentPrim, ArrayList<Boolean> poMatchList);
	public boolean checkType(clsPrimaryInformation poCurrentPrim);
}
