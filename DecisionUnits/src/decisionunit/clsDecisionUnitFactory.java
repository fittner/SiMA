package decisionunit;
import config.clsBWProperties;
import du.enums.eDecisionType;
import du.itf.itfDecisionUnit;
import du.itf.itfDecisionUnitFactory;
import decisionunit.clsBaseDecisionUnit;

/**
 * DecisionUnitFactory.java: DecisionUnits - 
 * 
 * @author deutsch
 * 06.05.2010, 16:53:19
 */

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 06.05.2010, 16:53:19
 * 
 */
public class clsDecisionUnitFactory implements itfDecisionUnitFactory {
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 10.05.2010, 16:46:19
	 * 
	 * @see decisionunit.itfDecisionUnitFactory#createDecisionUnit(du.utils.enums.eDecisionType, java.lang.String, config.clsBWProperties)
	 */
	@Override
	public itfDecisionUnit createDecisionUnit(eDecisionType nDecisionType,
			String poPrefix, clsBWProperties poProp, String uid) throws java.lang.IllegalArgumentException {
		return createDecisionUnit_static(nDecisionType, poPrefix, poProp, uid);
	}

	public static itfDecisionUnit createDecisionUnit_static(eDecisionType nDecisionType,
				String poPrefix, clsBWProperties poProp, String uid) throws java.lang.IllegalArgumentException {		
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
			case PA:
				oDecisionUnit = new pa.clsPsychoAnalysis(poPrefix, poProp, uid);
				break;
			case ActionlessTestPA:
				oDecisionUnit = new testbrains.clsActionlessTestPA(poPrefix, poProp, uid);
				break;
			default:
				throw new java.lang.IllegalArgumentException("eDecisionType."+nDecisionType.name());
		}
		
		return (itfDecisionUnit) oDecisionUnit;
	}

	public static clsBWProperties getDefaultProperties(eDecisionType nDecisionType, String poPrefix) throws java.lang.IllegalArgumentException {
		clsBWProperties oProps = null;
		
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
			case PA:
				oProps = pa.clsPsychoAnalysis.getDefaultProperties(poPrefix);
				break;
			default:
				throw new java.lang.IllegalArgumentException("eDecisionType."+nDecisionType.name());
		}
		
		return oProps;
	}
}
