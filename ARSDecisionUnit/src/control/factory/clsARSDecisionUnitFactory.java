/**
 * CHANGELOG
 * 
 * 2011/07/06 TD - added javadoc comments. code sanitation.
 */
package control.factory;
import base.tools.clsSingletonAnalysisAccessor;
import control.interfaces.clsBaseDecisionUnit;
import control.interfaces.itfDecisionUnit;
import properties.clsProperties;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import du.enums.eDecisionType;


/**
 * Creates an instance of the selected decision unit type. Can be called either via static call or via regular public method.
 * 
 * @author deutsch
 * 06.05.2010, 16:53:19
 * 
 */
public class clsARSDecisionUnitFactory {
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
			String poPrefix, clsProperties poProp, int uid,
            itfModuleMemoryAccess poMemory) throws java.lang.IllegalArgumentException {
		return createDecisionUnit_static(nDecisionType, poPrefix, poProp, uid, poMemory);
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
				String poPrefix, clsProperties poProp, int uid,
	            itfModuleMemoryAccess poMemory) throws java.lang.IllegalArgumentException {		
		clsBaseDecisionUnit oDecisionUnit = null;
		
		//create the defined decision unit...
		switch(nDecisionType) {
			case PA:
				oDecisionUnit = new control.clsPsychoAnalysis(poPrefix, poProp, uid, poMemory);
				clsSingletonAnalysisAccessor.getAnalyzer().registerDu(uid);
				break;
			case ActionlessTestPA:
				oDecisionUnit = new _MOVEOUTOFPROJECTtestbrains.clsActionlessTestPA(poPrefix, poProp, uid, poMemory);
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
			case PA:
				oProps = control.clsPsychoAnalysis.getDefaultProperties(poPrefix);
				break;
			default:
				throw new java.lang.IllegalArgumentException("eDecisionType."+nDecisionType.name());
		}
		
		return oProps;
	}
}
