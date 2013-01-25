/**
 * CHANGELOG
 *
 * 30.11.2012 LuHe - File created
 *
 */
package pa._v38.personality.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import statictools.clsGetARSPath;




import config.clsProperties;

/**
 * Container to hold all personality parameter
 * 
 * @author LuHe
 * 30.11.2012, 08:34:07
 * 
 */
public class clsPersonalityParameterContainer {

	public static final String P_PERSONALITY_PARAMETER ="personality.parameter.file";
	public static final String P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME="default.properties";
	private ArrayList<clsPersonalityParameterModule> moModules;
	private String moFilename;
	
	public clsPersonalityParameterContainer(String poPrefix, clsProperties poProp){
		moModules = new ArrayList<clsPersonalityParameterModule>();
		applyProperties(poPrefix,poProp);
		
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp){
		//String pre = clsProperties.addDot(poPrefix);
		moFilename= poProp.getProperty(poPrefix);
		clsProperties iProp = clsProperties.readProperties(clsGetARSPath.getPeronalityParameterConfigPath(), P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME);
		
		if(!moFilename.equals(P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME)){
			iProp.putAll(clsProperties.readProperties(clsGetARSPath.getPeronalityParameterConfigPath(), moFilename));
		}
		
		ArrayList<String> modules = new ArrayList<String>();
		
		//generate an arraylist of all modules
		Set<Object> keys =iProp.keySet();
		for(Object o : keys){
			String key = o.toString();
			String module = key.substring(0, key.indexOf('.'));
			if(!modules.contains(module)) modules.add(module);
		}
		
		//create the modules and the personality parameters in it
		
		for(String oKey:modules){
			clsProperties oProp = iProp.getSubset(oKey);

			clsPersonalityParameterModule oModule = new clsPersonalityParameterModule(oKey, oProp);
			moModules.add(oModule);	
		}
		Collections.sort(moModules);	
				
	}

	/**
	 * @since 17.12.2012 20:00:25
	 * 
	 * @return the moFilename
	 */
	public String getMoFilename() {
		return moFilename;
	}

	/**
	 * 
	 * Returns the name of the deafult personality parameter file as a property
	 *
	 * @since 25.01.2013 08:30:45
	 *
	 * @param poPrefix
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {

		clsProperties oProp = new clsProperties();
		//oProp.putAll(clsProperties.readProperties(clsGetARSPath.getPeronalityParameterConfigPath(), "default.personality.properties"));
		//oProp.addPrefix(poPrefix);
		oProp.setProperty(poPrefix, P_DEFAULT_PERSONALITY_PARAMETER_FILE_NAME);
		return oProp;
	}
	
	/**
	 * 
	 * Returns the personality Parameter for the specified module (poModule) and the name (poName)
	 *
	 * @since 25.01.2013 08:32:20
	 *
	 * @param poModule
	 * @param poName
	 * @return
	 */
	public clsPersonalityParameter getPersonalityParameter(String poModule, String poName){
		for(clsPersonalityParameterModule iModule: moModules){
			if(iModule.getMoModuleNumber().equals(poModule)){
				return iModule.getParameter(poName);
			}
/*			if(iModule.getParameter(poType)!=null){
				return iModule.getParameter(poType);
			}*/
		}
		return null;
	}
	
	public ArrayList<clsPersonalityParameterModule> getAllModule(){
		return moModules;
	}
	
	@Override
	public String toString(){
		String oResult ="[";
		for(clsPersonalityParameterModule oModule :moModules){
			oResult+= oModule.toString();
			oResult += ",";
		}
		oResult += "]";
		return oResult;
	}
}
