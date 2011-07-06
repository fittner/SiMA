/**
 * CHANGELOG
 * 
 * 2011/07/06 TD - added javadoc comments. code sanitation.
 */
package du.itf;

import config.clsBWProperties;
import du.enums.eDecisionType;

/**
 * Interface to a decision unit factory.
 * 
 * @author deutsch
 * 06.05.2010, 17:21:27
 * 
 */
public interface itfDecisionUnitFactory {
	/**
	 * Create a decision unit according to the provided params. 
	 *
	 * @since 06.07.2011 13:11:04
	 *
	 * @param nDecisionType the type of the decision unit. @see eDecisionType
	 * @param poPrefix prefix of the entries in the property object.
	 * @param poProp the property object.
	 * @param uid the unique identifier for the agent. is provided to the body and the decision unit to ease debugging and logging.
	 * @return a fresh instance of the selected decision unit.
	 * @throws java.lang.IllegalArgumentException
	 */
	public itfDecisionUnit createDecisionUnit(eDecisionType nDecisionType, String poPrefix, clsBWProperties poProp, int uid) throws java.lang.IllegalArgumentException;
}
