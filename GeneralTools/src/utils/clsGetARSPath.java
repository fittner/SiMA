package utils;

import java.io.IOException;

public class clsGetARSPath {
	public static String getArsPath()
	{
		if (System.getProperty("file.separator").equals("/")){
			try {
				String path = new java.io.File(".").getCanonicalPath().toString();
				path = path.substring(0, path.lastIndexOf('/'));
				return path;
			} catch (IOException e) {
				return System.getProperty("user.home") + "/ARSIN_V01"; // Unix
			}
		}
			
		else if (System.getProperty("file.separator").equals("\\")){
			try {
				String path = new java.io.File(".").getCanonicalPath().toString();
				path = path.substring(0, path.lastIndexOf('\\'));
				//path = "S:\\ARSIN_V01"; // Windows
				return path;
			} catch (IOException e) {
				return "S:\\ARSIN_V01"; // Windows
			}
			
			
		}
		else throw new NullPointerException("Spooky OS detected, can't find ARS-Root-Dir.");
	}
	
	public static String getSeperator() {
		return System.getProperty("file.separator");
	}
	

	
	public static String getConfigPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"Simulation"+System.getProperty("file.separator")+"config";
	}
	
	public static String getExpressionPath(){
		return getImagePath() + "expressions"+System.getProperty("file.separator");
	}
	public static String getScenarioPath()
	{
		return getConfigPath()+System.getProperty("file.separator")+"scenarios";
	}
	
	public static String getEntityConfigPath(){
		return getConfigPath()+System.getProperty("file.separator")+"default"+System.getProperty("file.separator")+"entity";
	}
	public static String getDecisionUnitPeronalityParameterConfigPath(){
		return getConfigPath()+System.getProperty("file.separator")+"personality_parameters"+System.getProperty("file.separator")+"decision_unit";
	}
	public static String getAnalysisRunConfigPath() {
		return getArsPath() + getSeperator() + "Analysis" + getSeperator() + "config";
	}
	public static String getBodyPeronalityParameterConfigPath(){
		return getConfigPath()+System.getProperty("file.separator")+"personality_parameters"+System.getProperty("file.separator")+"body";
	}
	public static String getDuConfigPath(){
		return getConfigPath()+System.getProperty("file.separator")+"default"+System.getProperty("file.separator")+"du";
	}	
	
	public static String getConfigImagePath()
	{
		return getArsPath()+System.getProperty("file.separator")+"Simulation"+System.getProperty("file.separator")+"config"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator");
	}
	
	public static String getIconPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"Entity"+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator")+"EntitySelectorIcon"+System.getProperty("file.separator");
	}
	
	public static String getImagePath()
	{
		return getArsPath()+System.getProperty("file.separator")+"Entity"+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator");
	}
	
	public static String getRelativImagePath(){
		return System.getProperty("file.separator")+"Entity"+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator");
	}
	
	public static String getXMLPath()
	{
		return getArsPath()+System.getProperty("file.separator")+
							"DecisionUnits"+System.getProperty("file.separator")+
							"config"+System.getProperty("file.separator")+
							"bfg"+System.getProperty("file.separator")+
							"xml"+System.getProperty("file.separator");
	}
	
	public static String getXMLPathEntity()
	{
		return getXMLPath()+"entity_config"+System.getProperty("file.separator");
	}
	
	public static String getLogPath() {
		//TODO (Deutsch): create a new subdirectory for each simulation run. even for small settings, a lot of log files are created ...  
		return getArsPath()+System.getProperty("file.separator")+"log";
	}
	
	public static String getMemoryPath() {
		return getArsPath() + getSeperator() + "ARSMemory" + getSeperator() + "config" + getSeperator() + "_v38" + getSeperator() + "bw" + getSeperator() + "pa.memory";
	}
	
	public static String getXMLPathMemory()
	{
		return getXMLPathEntity()+"pa.memory"+System.getProperty("file.separator");
	}
	
}
