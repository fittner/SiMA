/**
 * itfDecisionUnitFactory.java: DecisionUnitInterface - decisionunit
 * 
 * @author deutsch
 * 06.05.2010, 17:21:27
 */
package du.itf;

import config.clsBWProperties;
import du.enums.eDecisionType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 06.05.2010, 17:21:27
 * 
 */
public interface itfDecisionUnitFactory {
	public itfDecisionUnit createDecisionUnit(eDecisionType nDecisionType, String poPrefix, clsBWProperties poProp) throws java.lang.IllegalArgumentException;
}
