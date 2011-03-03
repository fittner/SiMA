package statictools;

public class clsGetARSPath {
	public static String getArsPath()
	{
		if (System.getProperty("file.separator").equals("/"))
			return System.getProperty("user.home") + "/SVN/ARSIN_V01"; // Unix
		else if (System.getProperty("file.separator").equals("\\"))
			return "S:\\ARSIN_V01"; // Windows
		else throw new NullPointerException("Spooky OS detected, can't find ARS-Root-Dir.");
	}
	
	public static String getSeperator() {
		return System.getProperty("file.separator");
	}
	
	public static String getConfigPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"Sim"+System.getProperty("file.separator")+"config";
	}
	
	public static String getIconPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"BW"+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator")+"EntitySelectorIcon"+System.getProperty("file.separator");
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
	
	public static String getLogPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"BW"+System.getProperty("file.separator")+"log";
	}
	
	public static String getXMLPathMemory()
	{
		return getXMLPathEntity()+"pa.memory"+System.getProperty("file.separator");
	}
	
}
