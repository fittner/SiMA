/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - added some javadoc
 */
package sim;

import java.util.Iterator;

import config.clsProperties;
import creation.simplePropertyLoader.clsSimplePropertyLoader;

import statictools.clsGetARSPath;
/*
import creation.simplePropertyLoader.clsSimplePropertyLoader;
import bw.entities.clsAnimal;
import bw.entities.clsAnimate;
import bw.entities.clsBase;
import bw.entities.clsARSIN;
import bw.entities.clsFungusEater;
import bw.entities.clsCake;
import bw.entities.clsCan;
import bw.entities.clsCarrot;
import bw.entities.clsEntity;
import bw.entities.clsFungus;
import bw.entities.clsHare;
import bw.entities.clsInanimate;
import bw.entities.clsLynx;
import bw.entities.clsMobile;
import bw.entities.clsPlant;
import bw.entities.clsRemoteBot;
import bw.entities.clsStationary;
import bw.entities.clsStone;
import bw.entities.clsUraniumOre;
import bw.entities.clsWallAxisAlign;
import bw.entities.clsWallHorizontal;
import bw.entities.clsWallVertical;
*/
import bw.ARSIN.factory.clsARSINFactory;
import bw.entities.factory.clsEntityFactory;

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
		/*clsProperties.writeProperties(clsAnimal.getDefaultProperties(""), oEntityDir, "animal"+oSubExt, "");
		clsProperties.writeProperties(clsAnimate.getDefaultProperties(""), oEntityDir, "animate"+oSubExt, "");
		clsProperties.writeProperties(clsBase.getDefaultProperties(""), oEntityDir, "base"+oSubExt, "");
		clsProperties.writeProperties(clsARSIN.getDefaultProperties(""), oEntityDir, "arsin"+oSubExt, "");
		clsProperties.writeProperties(clsFungusEater.getDefaultProperties(""), oEntityDir, "funguseater"+oSubExt, "");
		clsProperties.writeProperties(clsCake.getDefaultProperties(""), oEntityDir, "cake"+oSubExt, "");
		clsProperties.writeProperties(clsCan.getDefaultProperties(""), oEntityDir, "can"+oSubExt, "");
		clsProperties.writeProperties(clsCarrot.getDefaultProperties(""), oEntityDir, "carrot"+oSubExt, "");
		clsProperties.writeProperties(clsEntity.getDefaultProperties(""), oEntityDir, "entity"+oSubExt, "");
		clsProperties.writeProperties(clsFungus.getDefaultProperties(""), oEntityDir, "fungus"+oSubExt, "");
		clsProperties.writeProperties(clsHare.getDefaultProperties(""), oEntityDir, "hare"+oSubExt, "");
		clsProperties.writeProperties(clsInanimate.getDefaultProperties(""), oEntityDir, "inanimate"+oSubExt, "");
		clsProperties.writeProperties(clsLynx.getDefaultProperties(""), oEntityDir, "lynx"+oSubExt, "");
		clsProperties.writeProperties(clsMobile.getDefaultProperties(""), oEntityDir, "mobile"+oSubExt, "");
		clsProperties.writeProperties(clsPlant.getDefaultProperties(""), oEntityDir, "plant"+oSubExt, "");
		clsProperties.writeProperties(clsRemoteBot.getDefaultProperties(""), oEntityDir, "remotebot"+oSubExt, "");
		clsProperties.writeProperties(clsStationary.getDefaultProperties(""), oEntityDir, "stationary"+oSubExt, "");
		clsProperties.writeProperties(clsStone.getDefaultProperties(""), oEntityDir, "stone"+oSubExt, "");
		clsProperties.writeProperties(clsUraniumOre.getDefaultProperties(""), oEntityDir, "uraniumore"+oSubExt, "");
		clsProperties.writeProperties(clsWallAxisAlign.getDefaultProperties(""), oEntityDir, "wall_axis"+oSubExt, "");
		clsProperties.writeProperties(clsWallHorizontal.getDefaultProperties(""), oEntityDir, "wall_hor"+oSubExt, "");
		clsProperties.writeProperties(clsWallVertical.getDefaultProperties(""), oEntityDir, "wall_ver"+oSubExt, "");
		*/
		clsProperties.writeProperties(simple.dumbmind.clsDumbMindA.getDefaultProperties(""), oDecisionUnitDir, "dumbminda"+oSubExt, "");
		clsProperties.writeProperties(simple.reactive.clsReactive.getDefaultProperties(""), oDecisionUnitDir, "reactive"+oSubExt, "");
		clsProperties.writeProperties(simple.remotecontrol.clsRemoteControl.getDefaultProperties(""), oDecisionUnitDir, "remotecontrol"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.JADEX.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "jadex_hare"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.JAM.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "jam_hare"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.IfThenElse.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "ifthenelse_hare"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.JADEX.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "jadex_lynx"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.JAM.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "jam_lynx"+oSubExt, "");
		clsProperties.writeProperties(students.lifeCycle.IfThenElse.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "itthenelse_lynx"+oSubExt, "");
		clsProperties.writeProperties(pa.clsPsychoAnalysis.getDefaultProperties(""), oDecisionUnitDir, "psychoanalysis"+oSubExt, "");
		clsProperties.writeProperties(testbrains.clsActionlessTestPA.getDefaultProperties(""), oDecisionUnitDir, "pa_actionlesstest"+oSubExt, "");
		
		clsProperties.writeProperties(clsSimplePropertyLoader.getDefaultProperties("", true, true), oBaseDir, "simplePropertyLoader"+oMainExt, "");
		
		clsProperties.writeProperties(SimulatorMain.getDefaultProperties(""),  oBaseDir, oSystemFile, "");
		
		System.out.println("done ...");
		System.out.println("Default porperties written to Sim/config/default_not_in_use.");
	}

}
