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
		String oExt = ".default.property";
		
		clsBWProperties.writeProperties(clsAnimal.getDefaultProperties(""), oBaseDir, "animal"+oExt, "");
		clsBWProperties.writeProperties(clsAnimate.getDefaultProperties(""), oBaseDir, "animate"+oExt, "");
		clsBWProperties.writeProperties(clsBase.getDefaultProperties(""), oBaseDir, "base"+oExt, "");
		clsBWProperties.writeProperties(clsBubble.getDefaultProperties(""), oBaseDir, "bubble"+oExt, "");
		clsBWProperties.writeProperties(clsCake.getDefaultProperties(""), oBaseDir, "cake"+oExt, "");
		clsBWProperties.writeProperties(clsCan.getDefaultProperties(""), oBaseDir, "can"+oExt, "");
		clsBWProperties.writeProperties(clsCarrot.getDefaultProperties(""), oBaseDir, "carrot"+oExt, "");
		clsBWProperties.writeProperties(clsEntity.getDefaultProperties(""), oBaseDir, "entity"+oExt, "");
		clsBWProperties.writeProperties(clsFungus.getDefaultProperties(""), oBaseDir, "fungus"+oExt, "");
		clsBWProperties.writeProperties(clsHare.getDefaultProperties(""), oBaseDir, "hare"+oExt, "");
		clsBWProperties.writeProperties(clsInanimate.getDefaultProperties(""), oBaseDir, "inanimate"+oExt, "");
		clsBWProperties.writeProperties(clsLynx.getDefaultProperties(""), oBaseDir, "lynx"+oExt, "");
		clsBWProperties.writeProperties(clsMobile.getDefaultProperties(""), oBaseDir, "mobile"+oExt, "");
		clsBWProperties.writeProperties(clsPlant.getDefaultProperties(""), oBaseDir, "plant"+oExt, "");
		clsBWProperties.writeProperties(clsRemoteBot.getDefaultProperties(""), oBaseDir, "remotebot"+oExt, "");
		clsBWProperties.writeProperties(clsStationary.getDefaultProperties(""), oBaseDir, "stationary"+oExt, "");
		clsBWProperties.writeProperties(clsStone.getDefaultProperties(""), oBaseDir, "stone"+oExt, "");
		clsBWProperties.writeProperties(clsUraniumOre.getDefaultProperties(""), oBaseDir, "uraniumore"+oExt, "");
		clsBWProperties.writeProperties(clsWallAxisAlign.getDefaultProperties(""), oBaseDir, "wall_axis"+oExt, "");
		clsBWProperties.writeProperties(clsWallHorizontal.getDefaultProperties(""), oBaseDir, "wall_hor"+oExt, "");
		clsBWProperties.writeProperties(clsWallVertical.getDefaultProperties(""), oBaseDir, "wall_ver"+oExt, "");

		clsBWProperties.writeProperties(clsSimplePropertyLoader.getDefaultProperties(""), clsGetARSPath.getConfigPath(), "simplePropertyLoader"+oExt, "");
	}

}
