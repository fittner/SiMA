/**
 * CHANGELOG
 * 
 * 2011/07/06 TD - added javadoc comments. code sanitation.
 */
package alternative.factory;
import config.clsProperties;
import du.enums.eDecisionType;
import du.itf.itfDecisionUnit;
//import du.itf.itfDecisionUnitFactory;
import decisionunit.clsBaseDecisionUnit;

/**
 * Creates an instance of the selected decision unit type. Can be called either via static call or via regular public method.
 * 
 * @author deutsch
 * 06.05.2010, 16:53:19
 * 
 */
public class clsAlternativeDecisionUnitFactory {
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

	public itfDecisionUnit createDecisionUnit(eDecisionType nDecisionType,
			String poPrefix, clsProperties poProp, int uid) throws java.lang.IllegalArgumentException {
		return createDecisionUnit_static(nDecisionType, poPrefix, poProp, uid);
	}

	/**
	 * Does the work for createDecisionUnit. 
	 *
	 * @since 06.07.2011 13:15:01
	 *
	 * @param nDecisionType the type of the decision unit. @see eDecisionType
	 * @param poPrefix prefix of the entries in the property object.
	 * @param poProp the property object.
	 * @param uid the unique identifier for the agent. is provided to the body and the decision unit to ease debugging and logging.
	 * @return a fresh instance of the selected decision unit.
	 * @throws java.lang.IllegalArgumentException
	 */
	public static itfDecisionUnit createDecisionUnit_static(eDecisionType nDecisionType,
				String poPrefix, clsProperties poProp, int uid) throws java.lang.IllegalArgumentException {		
		clsBaseDecisionUnit oDecisionUnit = null;
		
		//create the defined decision unit...
		switch(nDecisionType) {
			case DUMB_MIND_A:
				oDecisionUnit = new simple.dumbmind.clsDumbMindA(poPrefix, poProp, uid);
				break;
			case FUNGUS_EATER:
				oDecisionUnit = new simple.reactive.clsReactive(poPrefix, poProp, uid);
				break;
			case REMOTE:
				oDecisionUnit = new simple.remotecontrol.clsRemoteControl(poPrefix, poProp, uid);
				break;
			case HARE_JADEX:
				oDecisionUnit = new students.lifeCycle.JADEX.clsHareMind(poPrefix, poProp, uid);
				break;			
			case HARE_JAM:
				oDecisionUnit = new students.lifeCycle.JAM.clsHareMind(poPrefix, poProp, uid);
				break;		
			case HARE_IFTHENELSE:
				oDecisionUnit = new students.lifeCycle.IfThenElse.clsHareMind(poPrefix, poProp, uid);
				break;	
			case LYNX_JADEX:
				oDecisionUnit = new students.lifeCycle.JADEX.clsLynxMind(poPrefix, poProp, uid);
				break;			
			case LYNX_JAM:
				oDecisionUnit = new students.lifeCycle.JAM.clsLynxMind(poPrefix, poProp, uid);
				break;	
			case LYNX_IFTHENELSE:
				oDecisionUnit = new students.lifeCycle.IfThenElse.clsLynxMind(poPrefix, poProp, uid);
				break;	
			default:
				throw new java.lang.IllegalArgumentException("eDecisionType."+nDecisionType.name());
		}
		
		return (itfDecisionUnit) oDecisionUnit;
	}

	/**
	 * Provides the default entries for the selected decision unit type. See config.clsProperties in project DecisionUnitInterface.
	 *
	 * @since 06.07.2011 13:15:54
	 *
	 * @param nDecisionType
	 * @param poPrefix
	 * @return
	 * @throws java.lang.IllegalArgumentException
	 */
	public static clsProperties getDefaultProperties(eDecisionType nDecisionType, String poPrefix) throws java.lang.IllegalArgumentException {
		clsProperties oProps = null;
		
		//create the defined decision unit...
		switch(nDecisionType) {
			case DUMB_MIND_A:
				oProps = simple.dumbmind.clsDumbMindA.getDefaultProperties(poPrefix);
				break;
			case FUNGUS_EATER:
				oProps = simple.reactive.clsReactive.getDefaultProperties(poPrefix);
				break;
			case REMOTE:
				oProps = simple.remotecontrol.clsRemoteControl.getDefaultProperties(poPrefix);
				break;
			case HARE_JADEX:
				oProps = students.lifeCycle.JADEX.clsHareMind.getDefaultProperties(poPrefix);
				break;			
			case HARE_JAM:
				oProps = students.lifeCycle.JAM.clsHareMind.getDefaultProperties(poPrefix);
				break;		
			case HARE_IFTHENELSE:
				oProps = students.lifeCycle.IfThenElse.clsHareMind.getDefaultProperties(poPrefix);
				break;	
			case LYNX_JADEX:
				oProps = students.lifeCycle.JADEX.clsLynxMind.getDefaultProperties(poPrefix);
				break;			
			case LYNX_JAM:
				oProps = students.lifeCycle.JAM.clsLynxMind.getDefaultProperties(poPrefix);
				break;	
			case LYNX_IFTHENELSE:
				oProps = students.lifeCycle.IfThenElse.clsLynxMind.getDefaultProperties(poPrefix);
				break;	
			default:
				throw new java.lang.IllegalArgumentException("eDecisionType."+nDecisionType.name());
		}
		
		return oProps;
	}
}
