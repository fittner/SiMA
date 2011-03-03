/**
 * clsModuleContainer.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:15:58
 */
package pa.modules._v19;

import pa._v19.clsInterfaceHandler;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
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
	protected clsMemory moMemory;
	protected clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	protected clsInterfaceHandler moInterfaceHandler;	
	
	public clsModuleContainer(String poPrefix, clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		moEnclosingContainer = poEnclosingContainer;
		moMemory = poMemory;
		moKnowledgeBaseHandler = poKnowledgeBaseHandler; 
		moInterfaceHandler = poInterfaceHandler;
		
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
}
