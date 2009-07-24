/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import java.awt.Color;
import sim.creation.clsLoader;
import bw.entities.clsBubble;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsRemoteBot;
import bw.entities.tools.clsShapeCreator;
import bw.factories.clsRegisterEntity;
import bw.utils.config.clsBWProperties;

/**
 * Helper class to load every agent and register to mason-physics2D 
 * @author langr
 * 
 */
public class clsAgentLoader {

	public static void loadAgents(int pnNumRemoteBots, int pnNumBubbles){
		loadRemoteBots(pnNumRemoteBots);
		loadBubbles(pnNumBubbles);
	}
	
	public static void loadBubbles(int pnNumAgents){	
		
		Color[] oColors = {Color.green, Color.ORANGE, Color.yellow};
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 Color oColor = Color.GREEN;
			 if(i < oColors.length)
				 oColor = oColors[i];
			 	         
	         clsBWProperties oProp = clsBubble.getDefaultProperties("Bubble.");
			 oProp.putAll( clsLoader.generateRandomPose("Bubble.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );	         
	         oProp.setProperty("Bubble."+clsEntity.P_ID, i);
	         
	         oProp.setProperty("Bubble."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_COLOR, oColor);
	         clsBubble oBubble = new clsBubble( "Bubble.", oProp );

		  	 clsRegisterEntity.registerEntity(oBubble);
         }
	}
	
	/**
	 * remote control is only implemented for one remoteBot
	 *
	 * @author muchitsch
	 * 26.02.2009, 14:34:24
	 *
	 * @param pnNumBots
	 */
	public static void loadRemoteBots(int pnNumBots) {
		for (int i = 0; i < pnNumBots; i++) {
	         clsBWProperties oProp = clsRemoteBot.getDefaultProperties("RemoteBot.");
			 oProp.putAll( clsLoader.generateRandomPose("RemoteBot.", clsMobile.P_POS_X, clsMobile.P_POS_Y, clsMobile.P_POS_ANGLE) );	         
	         
	         oProp.setProperty("RemoteBot."+clsEntity.P_ID, i);

 			clsRemoteBot oBot = new clsRemoteBot("RemoteBot.", oProp);

 			clsRegisterEntity.registerEntity(oBot);
        }
	}	
}
