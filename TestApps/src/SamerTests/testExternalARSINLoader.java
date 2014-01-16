/**
 * externalEntityLoader.java: TestApps - SamerTests
 * 
 * @author schaat
 * Mar 27, 2012, 3:06:23 PM
 */
package SamerTests;

//import statictools.clsGetARSPath;
import properties.clsProperties;
import alternativeLoader.clsExternalARSINLoader;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import singeltons.clsSimState;
import singeltons.clsSingletonMasonGetter;
import utils.clsGetARSPath;
import utils.clsUniqueIdGenerator;

//import du.enums.eDecisionType;
import entities.clsARSIN;


/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Mar 27, 2012, 3:06:23 PM
 * 
 */
public class testExternalARSINLoader {

	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * Mar 27, 2012, 3:06:23 PM
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO (schaat)
	
		// Create empty property-object
		clsProperties oProp = new clsProperties();
		
		// Create Singleton (minimum-settings needed for Simulation)
		clsSingletonMasonGetter.setFieldEnvironment(new Continuous2D(25, 0, 0));
		clsSimState.setSimState(new SimState(clsUniqueIdGenerator.getUniqueId()));
		
		// Get properties from property-files. First get path to property files (expected to be in S:\ARSIN01\Sim\config\default\entity and ...\du)
		String oPath = clsGetARSPath.getEntityConfigPath();
		
		// ARSIN property file. CHANGE NAME OF PROPERTY FILE HERE
		oProp = clsProperties.readProperties(oPath, "arsin.default.properties");
		
		oPath = clsGetARSPath.getDuConfigPath();

		// decision unit property file. CHANGE NAME OF PROPERTY FILE HERE
		clsProperties oPropDU = clsProperties.readProperties(oPath, "du.default.properties");
		//merge settings - overwrites existing entries
		oProp.putAll(oPropDU);
		
		// get the ARSIN
		clsExternalARSINLoader oLoader = new clsExternalARSINLoader(oProp);
		clsARSIN arsin = (clsARSIN)oLoader.getARSINI();
										
		System.out.println(arsin.getId());
		arsin.processing();
	}

}
