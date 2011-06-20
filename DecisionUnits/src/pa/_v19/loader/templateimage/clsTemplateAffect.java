/**
 * clsTemplateAffect.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 13:14:42
 */
package pa._v19.loader.templateimage;

import pa._v19.datatypes.clsAffect;

/**
 * 
 * @author langr
 * 23.10.2009, 13:14:42
 * 
 */
@Deprecated
public class clsTemplateAffect extends clsAffect {

	public double moValue;
	public clsCompareOperator moCompareOperator;
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:14:42
	 * 
	 * @see pa.datatypes.clsAffect#getValue()
	 */
	@Override
	public double getValue() {
		return moValue;
	}

}
