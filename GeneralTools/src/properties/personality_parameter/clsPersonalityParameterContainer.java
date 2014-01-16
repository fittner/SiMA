/**
 * CHANGELOG
 *
 * 30.11.2012 LuHe - File created
 *
 */
package properties.personality_parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import properties.clsProperties;





/**
 * Container to hold all personality parameter
 * 
 * @author LuHe
 * 30.11.2012, 08:34:07
 * 
 */
public class clsPersonalityParameterContainer {


	private ArrayList<clsPersonalityParameterModule> moModules;
	private String moFilename;
	
	public clsPersonalityParameterContainer(String poFilePath, String poFilename, String poDefaultFileName){
		moModules = new ArrayList<clsPersonalityParameterModule>();
		loadProperties(poFilePath, poFilename, poDefaultFileName);
		
	}
	
	private void loadProperties(String poFilePath, String poFilename, String poDefaultFileName){
		//String pre = clsProperties.addDot(poPrefix);
		//moFilename= poProp.getProperty(poPrefix);
		clsProperties iProp = clsProperties.readProperties(poFilePath, poDefaultFileName);
		moFilename = poDefaultFileName;
		if(!poFilename.equals(poDefaultFileName)){
			moFilename = poFilename;
			iProp.putAll(clsProperties.readProperties(poFilePath, moFilename));
			
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
