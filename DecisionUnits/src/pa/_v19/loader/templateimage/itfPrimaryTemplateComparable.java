/**
 * itfTemplateComparable.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:13:41
 */
package pa._v19.loader.templateimage;

import java.util.ArrayList;

import pa._v19.datatypes.clsPrimaryInformation;

/**
 * 
 * @author langr
 * 23.10.2009, 12:13:41
 * 
 */
@Deprecated
public interface itfPrimaryTemplateComparable {

	public void compareTemplateWith(clsPrimaryInformation poCurrentPrim, ArrayList<Boolean> poMatchList);
	public boolean checkType(clsPrimaryInformation poCurrentPrim);
}
