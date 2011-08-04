package PropertiesInspector;

import java.awt.HeadlessException;
import javax.swing.JFrame;
import config.clsProperties;


public class clsTestFramePropertiesInspector extends JFrame {

	/** DOCUMENT (jakubec) - insert description; @since 03.08.2011 20:06:04 */
	private static final long serialVersionUID = 1L;

	private static String propertiesFilePath = "S:\\ARSIN_V01\\Sim\\config";
	private static String propertiesFileName = "TestPropertiesFileForPropertyInspector3";

	private static clsProperties BWProperties;

	public clsTestFramePropertiesInspector() throws HeadlessException {
		initApplication(); // Initiates the application and constructs the layout of the programs pane (window).
	}
	
    private  void initApplication() {
    	
		BWProperties = clsProperties.readProperties(propertiesFilePath, propertiesFileName);
		System.out.println("ConfigInspector Test!");
		new clsPropertiesInspector(BWProperties);
		System.out.println(BWProperties.getProperty("title"));
    }

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new clsTestFramePropertiesInspector().setVisible(true);
	}
}
