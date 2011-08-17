/**
 * clsModuleContainer.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:15:58
 */
package pa._v19.modules;

import pa._v19.clsInterfaceHandler;
import pa._v19.memory.clsMemory;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import config.clsProperties;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 11:15:58
 * 
 */
@Deprecated
public abstract class clsModuleContainer {
	protected clsModuleContainer moEnclosingContainer;
	protected clsMemory moMemory;
	protected clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	protected clsInterfaceHandler moInterfaceHandler;	
	
	public clsModuleContainer(String poPrefix, clsProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		moEnclosingContainer = poEnclosingContainer;
		moMemory = poMemory;
		moKnowledgeBaseHandler = poKnowledgeBaseHandler; 
		moInterfaceHandler = poInterfaceHandler;
		
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		// String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
		//nothing to do
	}	
}
