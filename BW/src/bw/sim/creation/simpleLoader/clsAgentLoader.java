/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import java.awt.Color;
import java.awt.Paint;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsBubble;
import bw.entities.clsRemoteBot;
import bw.factories.clsRegisterEntity;
import bw.sim.creation.clsLoader;
import bw.utils.container.clsConfigMap;

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
		
		Paint[] oColors = {Color.green, Color.ORANGE, Color.yellow};
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 Paint oColor = Color.GREEN;
			 if(i < oColors.length)
				 oColor = oColors[i];
			 
			 
	         clsPose oStartPose = clsLoader.generateRandomPose();
		  	 clsBubble oBubble = new clsBubble(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), oColor, new clsConfigMap());
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
	        clsPose oStartPose = clsLoader.generateRandomPose();
 			clsRemoteBot oBot = new clsRemoteBot(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
			clsRegisterEntity.registerEntity(oBot);
        }
	}	
}
