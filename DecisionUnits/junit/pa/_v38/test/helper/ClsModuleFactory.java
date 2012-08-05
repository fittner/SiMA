/**
 * CHANGELOG
 *
 * 05.08.2012 ende - File created
 *
 */
package pa._v38.test.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.modules.clsModuleBase;
import pa._v38.storage.clsEnvironmentalImageMemory;
import pa._v38.storage.clsShortTermMemory;

/**
 * DOCUMENT (ende) - insert description 
 * 
 * @author ende
 * 05.08.2012, 09:34:56
 * 
 */
public class ClsModuleFactory {

	private clsModuleBase _baseModule;
	
	private String poProb;
	private HashMap<Integer, clsModuleBase> poModuleList;
	private SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData;
	private clsKnowledgeBaseHandler poKnowledgeBaseHandler;
	private clsShortTermMemory poShortTermMemory;
	private clsEnvironmentalImageMemory poTempLocalizationStorage;
	
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poProb the poProb to set
	 */
	public void setPoProb(String poProb) {
		this.poProb = poProb;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poModuleList the poModuleList to set
	 */
	public void setPoModuleList(HashMap<Integer, clsModuleBase> poModuleList) {
		this.poModuleList = poModuleList;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poInterfaceData the poInterfaceData to set
	 */
	public void setPoInterfaceData(
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		this.poInterfaceData = poInterfaceData;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poKnowledgeBaseHandler the poKnowledgeBaseHandler to set
	 */
	public void setPoKnowledgeBaseHandler(
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		this.poKnowledgeBaseHandler = poKnowledgeBaseHandler;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poShortTermMemory the poShortTermMemory to set
	 */
	public void setPoShortTermMemory(clsShortTermMemory poShortTermMemory) {
		this.poShortTermMemory = poShortTermMemory;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poTempLocalizationStorage the poTempLocalizationStorage to set
	 */
	public void setPoTempLocalizationStorage(
			clsEnvironmentalImageMemory poTempLocalizationStorage) {
		this.poTempLocalizationStorage = poTempLocalizationStorage;
	}

	
	
	/**
	 * @since 05.08.2012 09:39:43
	 * 
	 * @return the _baseModule
	 */
	public clsModuleBase get_baseModule() {
		//TODO
		return _baseModule;
	}	
}
