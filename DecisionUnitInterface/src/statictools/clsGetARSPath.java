package statictools;

public class clsGetARSPath {
	public static String getArsPath()
	{
		if (System.getProperty("file.separator").equals("/"))
			return System.getProperty("user.home") + "/ARS/PA/BWv1"; // Unix
		else if (System.getProperty("file.separator").equals("\\"))
			return "S:/ARS/PA/BWv1"; // Windows
		else throw new NullPointerException("Spooky OS detected, can't find ARS-Root-Dir.");
	}
}
