/**
 * CHANGELOG
 *
 * 05.08.2012 ende - File created
 *
 */
package testutils.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.old.clsKnowledgeBaseHandler;
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
public class clsModuleTestFactory<T extends clsModuleBase> {
	private final clsModuleTestFactory<?> _factory;
	
	private T _baseModule;
	
	private String poProb;
	private HashMap<Integer, clsModuleBase> poModuleList;
	private SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData;
	private clsKnowledgeBaseHandler poKnowledgeBaseHandler;
	private clsShortTermMemory poShortTermMemory;
	private clsEnvironmentalImageMemory poTempLocalizationStorage;
	
	private clsModuleTestFactory() {
		_factory = this;
	}
	
	public final clsModuleTestFactory<T> create() {
		
		return this;
	}
	
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poProb the poProb to set
	 */
	public clsModuleTestFactory<T> setPoProb(String poProb) {
		this.poProb = poProb;
		return this;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poModuleList the poModuleList to set
	 */
	public clsModuleTestFactory<T> setPoModuleList(HashMap<Integer, clsModuleBase> poModuleList) {
		this.poModuleList = poModuleList;
		return this;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poInterfaceData the poInterfaceData to set
	 */
	public clsModuleTestFactory<T> setPoInterfaceData(
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) {
		this.poInterfaceData = poInterfaceData;
		return this;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poKnowledgeBaseHandler the poKnowledgeBaseHandler to set
	 */
	public clsModuleTestFactory<T> setPoKnowledgeBaseHandler(
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) {
		this.poKnowledgeBaseHandler = poKnowledgeBaseHandler;
		return this;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poShortTermMemory the poShortTermMemory to set
	 */
	public clsModuleTestFactory<T> setPoShortTermMemory(clsShortTermMemory poShortTermMemory) {
		this.poShortTermMemory = poShortTermMemory;
		return this;
	}
	
	/**
	 * @since 05.08.2012 09:38:37
	 * 
	 * @param poTempLocalizationStorage the poTempLocalizationStorage to set
	 */
	public clsModuleTestFactory<T> setPoTempLocalizationStorage(
			clsEnvironmentalImageMemory poTempLocalizationStorage) {
		this.poTempLocalizationStorage = poTempLocalizationStorage;
		return this;
	}

	
	
	/**
	 * @since 05.08.2012 09:39:43
	 * 
	 * @return the _baseModule
	 */
	public clsModuleBase getBaseModule() {
		//TODO havlicek
		return _baseModule;
	}	
}
