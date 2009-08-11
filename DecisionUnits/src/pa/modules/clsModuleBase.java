/**
 * clsModuleAbstract.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 */
package pa.modules;

import pa.interfaces.itfStep;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 * 
 */
public abstract class clsModuleBase implements itfStep {
	protected clsModuleContainer moEnclosingContainer;
	protected eProcessType mnProcessType;
	protected ePsychicInstances mnPsychicInstances;
	
	public clsModuleBase(String poPrefix, clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		moEnclosingContainer = poEnclosingContainer;
		
		setProcessType();
		setPsychicInstances();
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}
	
	protected abstract void setProcessType();
	protected abstract void setPsychicInstances();	
}
