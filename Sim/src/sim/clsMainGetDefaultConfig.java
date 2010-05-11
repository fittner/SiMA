/**
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim;

import config.clsBWProperties;
import sim.creation.simplePropertyLoader.clsSimplePropertyLoader;
import statictools.clsGetARSPath;
import bw.entities.clsAnimal;
import bw.entities.clsAnimate;
import bw.entities.clsBase;
import bw.entities.clsBubble;
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

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 */
public class clsMainGetDefaultConfig {

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @author deutsch
	 * 23.07.2009, 15:51:57
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String oBaseDir = clsGetARSPath.getConfigPath()+System.getProperty("file.separator")+"default";
		String oEntityDir = oBaseDir+System.getProperty("file.separator")+"entity";
		String oDecisionUnitDir = oBaseDir+System.getProperty("file.separator")+"du";
		
		String oSubExt = ".default.properties";
		String oMainExt = ".main.properties";
		String oSystemFile = "system.default.properties";
		
		clsBWProperties.writeProperties(clsAnimal.getDefaultProperties(""), oEntityDir, "animal"+oSubExt, "");
		clsBWProperties.writeProperties(clsAnimate.getDefaultProperties(""), oEntityDir, "animate"+oSubExt, "");
		clsBWProperties.writeProperties(clsBase.getDefaultProperties(""), oEntityDir, "base"+oSubExt, "");
		clsBWProperties.writeProperties(clsBubble.getDefaultProperties(""), oEntityDir, "bubble"+oSubExt, "");
		clsBWProperties.writeProperties(clsFungusEater.getDefaultProperties(""), oEntityDir, "funguseater"+oSubExt, "");
		clsBWProperties.writeProperties(clsCake.getDefaultProperties(""), oEntityDir, "cake"+oSubExt, "");
		clsBWProperties.writeProperties(clsCan.getDefaultProperties(""), oEntityDir, "can"+oSubExt, "");
		clsBWProperties.writeProperties(clsCarrot.getDefaultProperties(""), oEntityDir, "carrot"+oSubExt, "");
		clsBWProperties.writeProperties(clsEntity.getDefaultProperties(""), oEntityDir, "entity"+oSubExt, "");
		clsBWProperties.writeProperties(clsFungus.getDefaultProperties(""), oEntityDir, "fungus"+oSubExt, "");
		clsBWProperties.writeProperties(clsHare.getDefaultProperties(""), oEntityDir, "hare"+oSubExt, "");
		clsBWProperties.writeProperties(clsInanimate.getDefaultProperties(""), oEntityDir, "inanimate"+oSubExt, "");
		clsBWProperties.writeProperties(clsLynx.getDefaultProperties(""), oEntityDir, "lynx"+oSubExt, "");
		clsBWProperties.writeProperties(clsMobile.getDefaultProperties(""), oEntityDir, "mobile"+oSubExt, "");
		clsBWProperties.writeProperties(clsPlant.getDefaultProperties(""), oEntityDir, "plant"+oSubExt, "");
		clsBWProperties.writeProperties(clsRemoteBot.getDefaultProperties(""), oEntityDir, "remotebot"+oSubExt, "");
		clsBWProperties.writeProperties(clsStationary.getDefaultProperties(""), oEntityDir, "stationary"+oSubExt, "");
		clsBWProperties.writeProperties(clsStone.getDefaultProperties(""), oEntityDir, "stone"+oSubExt, "");
		clsBWProperties.writeProperties(clsUraniumOre.getDefaultProperties(""), oEntityDir, "uraniumore"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallAxisAlign.getDefaultProperties(""), oEntityDir, "wall_axis"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallHorizontal.getDefaultProperties(""), oEntityDir, "wall_hor"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallVertical.getDefaultProperties(""), oEntityDir, "wall_ver"+oSubExt, "");

		clsBWProperties.writeProperties(simple.dumbmind.clsDumbMindA.getDefaultProperties(""), oDecisionUnitDir, "dumbminda"+oSubExt, "");
		clsBWProperties.writeProperties(simple.reactive.clsReactive.getDefaultProperties(""), oDecisionUnitDir, "reactive"+oSubExt, "");
		clsBWProperties.writeProperties(simple.remotecontrol.clsRemoteControl.getDefaultProperties(""), oDecisionUnitDir, "remotecontrol"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.JADEX.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "jadex_hare"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.JAM.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "jam_hare"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.IfThenElse.clsHareMind.getDefaultProperties(""), oDecisionUnitDir, "ifthenelse_hare"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.JADEX.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "jadex_lynx"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.JAM.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "jam_lynx"+oSubExt, "");
		clsBWProperties.writeProperties(students.lifeCycle.IfThenElse.clsLynxMind.getDefaultProperties(""), oDecisionUnitDir, "itthenelse_lynx"+oSubExt, "");
		clsBWProperties.writeProperties(pa.clsPsychoAnalysis.getDefaultProperties(""), oDecisionUnitDir, "psychoanalysis"+oSubExt, "");
		
		clsBWProperties.writeProperties(clsSimplePropertyLoader.getDefaultProperties("", true, true), clsGetARSPath.getConfigPath(), "simplePropertyLoader"+oMainExt, "");
		
		clsBWProperties.writeProperties(clsBWMainWithUI.getDefaultProperties(""),  clsGetARSPath.getConfigPath(), oSystemFile, "");
		
		System.out.println("done ...");
	}

}
