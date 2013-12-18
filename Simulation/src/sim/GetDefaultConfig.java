/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - added some javadoc
 */
package sim;

import java.util.Iterator;

import statictools.clsGetARSPath;
import bw.ARSIN.factory.clsARSINFactory;
import config.clsProperties;
import creation.simplePropertyLoader.clsSimplePropertyLoader;
import entities.factory.clsEntityFactory;

/**
 * extracts the default properties for all different kind of classes and writes them to files. All entities of project BW and the different decision units from project DecisionUnits are processed.
 * files are created in the default config folder in the subfolders "default/entity" and "default/du".  
 * 
 * @author deutsch
 * @since 23.07.2009, 15:51:57
 * 
 */
public class GetDefaultConfig {

	/**
	 * Executes the purpose of this class. See class description.
	 *
	 * @author deutsch
	 * 23.07.2009, 15:51:57
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String oBaseDir = clsGetARSPath.getConfigPath()+System.getProperty("file.separator")+"default_not_in_use";
		String oEntityDir = oBaseDir+System.getProperty("file.separator")+"entity";
		String oDecisionUnitDir = oBaseDir+System.getProperty("file.separator")+"du";

		
		String oSubExt = ".default.properties";
		String oMainExt = ".main.properties";
		String oSystemFile = "system.default.properties";
		
		Iterator<Class> it = clsEntityFactory.getEntities().values().iterator();
		while(it.hasNext()){
			Class x = it.next();
			clsProperties.writeProperties(clsEntityFactory.getEntityDefaultProperties(x, ""), oEntityDir, x.getName()+oSubExt, "");
		}
		it = clsARSINFactory.getEntities().values().iterator();
		while(it.hasNext()){
			Class x = it.next();
			clsProperties.writeProperties(clsARSINFactory.getEntityDefaultProperties(x, ""), oEntityDir, x.getName()+oSubExt, "");
		}
		clsProperties.writeProperties(control.clsPsychoAnalysis.getDefaultProperties(""), oDecisionUnitDir, "psychoanalysis"+oSubExt, "");
		clsProperties.writeProperties(_MOVEOUTOFPROJECTtestbrains.clsActionlessTestPA.getDefaultProperties(""), oDecisionUnitDir, "pa_actionlesstest"+oSubExt, "");
		
		clsProperties.writeProperties(clsSimplePropertyLoader.getDefaultProperties("", true, true), oBaseDir, "simplePropertyLoader"+oMainExt, "");
		
		clsProperties.writeProperties(SimulatorMain.getDefaultProperties(""),  oBaseDir, oSystemFile, "");
		
		System.out.println("done ...");
		System.out.println("Default porperties written to Sim/config/default_not_in_use.");
	}

}
