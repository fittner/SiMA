package PropertiesInspector;
import config.clsBWProperties;


public class clsTestFramePropertiesInspector {

	private static String propertiesFilePath = "S:\\ARSIN_V01\\Sim\\config";
	private static String propertiesFileName = "clemens.own.dont.touch.this.world.main.properties";

	private static clsBWProperties BWProperies;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BWProperies = clsBWProperties.readProperties(propertiesFilePath, propertiesFileName);
		System.out.println("ConfigInspector Test!");
		new clsPropertiesInspector(BWProperies);
		System.out.println(BWProperies.getProperty("title"));
	}
}
