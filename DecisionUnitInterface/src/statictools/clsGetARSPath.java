package statictools;

public class clsGetARSPath {
	public static String getArsPath()
	{
		if (System.getProperty("file.separator").equals("/"))
			return System.getProperty("user.home") + "/SVN/ARS/PA/BWv1"; // Unix
		else if (System.getProperty("file.separator").equals("\\"))
			return "S:\\ARS\\PA\\BWv1"; // Windows
		else throw new NullPointerException("Spooky OS detected, can't find ARS-Root-Dir.");
	}
	
	public static String getConfigPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"BW"+System.getProperty("file.separator")+"config";
	}
	
	public static String getIconPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"BW"+System.getProperty("file.separator")+"src"+System.getProperty("file.separator")+"resources"+System.getProperty("file.separator")+"images"+System.getProperty("file.separator")+"EntitySelectorIcon"+System.getProperty("file.separator");
	}
	
	public static String getLogPath()
	{
		return getArsPath()+System.getProperty("file.separator")+"BW"+System.getProperty("file.separator")+"log";
	}	
}
