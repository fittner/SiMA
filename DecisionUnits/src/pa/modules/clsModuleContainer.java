/**
 * clsModuleContainer.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:15:58
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:15:58
 * 
 */
public abstract class clsModuleContainer {
	protected clsModuleContainer moEnclosingContainer;
	protected eProcessType mnProcessType;
	protected ePsychicInstances mnPsychicInstances;
	
	public clsModuleContainer(String poPrefix, clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		moEnclosingContainer = poEnclosingContainer;
		
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
