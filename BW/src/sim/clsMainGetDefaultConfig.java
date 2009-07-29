/**
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim;

import sim.creation.simplePropertyLoader.clsSimplePropertyLoader;
import statictools.clsGetARSPath;
import bw.entities.clsAnimal;
import bw.entities.clsAnimate;
import bw.entities.clsBase;
import bw.entities.clsBubble;
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
import bw.utils.config.clsBWProperties;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.07.2009, 15:51:57
 * 
 */
public class clsMainGetDefaultConfig {

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 23.07.2009, 15:51:57
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String oBaseDir = clsGetARSPath.getConfigPath()+System.getProperty("file.separator")+"default";
		String oSubExt = ".default.property";
		String oMainExt = ".main.property";
		
		clsBWProperties.writeProperties(clsAnimal.getDefaultProperties(""), oBaseDir, "animal"+oSubExt, "");
		clsBWProperties.writeProperties(clsAnimate.getDefaultProperties(""), oBaseDir, "animate"+oSubExt, "");
		clsBWProperties.writeProperties(clsBase.getDefaultProperties(""), oBaseDir, "base"+oSubExt, "");
		clsBWProperties.writeProperties(clsBubble.getDefaultProperties(""), oBaseDir, "bubble"+oSubExt, "");
		clsBWProperties.writeProperties(clsCake.getDefaultProperties(""), oBaseDir, "cake"+oSubExt, "");
		clsBWProperties.writeProperties(clsCan.getDefaultProperties(""), oBaseDir, "can"+oSubExt, "");
		clsBWProperties.writeProperties(clsCarrot.getDefaultProperties(""), oBaseDir, "carrot"+oSubExt, "");
		clsBWProperties.writeProperties(clsEntity.getDefaultProperties(""), oBaseDir, "entity"+oSubExt, "");
		clsBWProperties.writeProperties(clsFungus.getDefaultProperties(""), oBaseDir, "fungus"+oSubExt, "");
		clsBWProperties.writeProperties(clsHare.getDefaultProperties(""), oBaseDir, "hare"+oSubExt, "");
		clsBWProperties.writeProperties(clsInanimate.getDefaultProperties(""), oBaseDir, "inanimate"+oSubExt, "");
		clsBWProperties.writeProperties(clsLynx.getDefaultProperties(""), oBaseDir, "lynx"+oSubExt, "");
		clsBWProperties.writeProperties(clsMobile.getDefaultProperties(""), oBaseDir, "mobile"+oSubExt, "");
		clsBWProperties.writeProperties(clsPlant.getDefaultProperties(""), oBaseDir, "plant"+oSubExt, "");
		clsBWProperties.writeProperties(clsRemoteBot.getDefaultProperties(""), oBaseDir, "remotebot"+oSubExt, "");
		clsBWProperties.writeProperties(clsStationary.getDefaultProperties(""), oBaseDir, "stationary"+oSubExt, "");
		clsBWProperties.writeProperties(clsStone.getDefaultProperties(""), oBaseDir, "stone"+oSubExt, "");
		clsBWProperties.writeProperties(clsUraniumOre.getDefaultProperties(""), oBaseDir, "uraniumore"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallAxisAlign.getDefaultProperties(""), oBaseDir, "wall_axis"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallHorizontal.getDefaultProperties(""), oBaseDir, "wall_hor"+oSubExt, "");
		clsBWProperties.writeProperties(clsWallVertical.getDefaultProperties(""), oBaseDir, "wall_ver"+oSubExt, "");

		clsBWProperties.writeProperties(clsSimplePropertyLoader.getDefaultProperties("", true), clsGetARSPath.getConfigPath(), "simplePropertyLoader"+oMainExt, "");
		
		System.out.println("done ...");
	}

}
