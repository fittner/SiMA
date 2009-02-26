/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleLoader;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsBubble;
import bw.entities.clsRemoteBot;
import bw.factories.clsRegisterEntity;
import bw.sim.creation.clsLoader;

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
		 for (int i = 0; i < pnNumAgents; i++)
         {
	         clsPose oStartPose = clsLoader.generateRandomPose();
		  	 clsBubble oBubble = new clsBubble(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
		  	 clsRegisterEntity.registerEntity(oBubble);
         }
	}
	
	public static void loadRemoteBots(int pnNumBots) {
		for (int i = 0; i < 1; i++) {
	        clsPose oStartPose = clsLoader.generateRandomPose();
 			clsRemoteBot oBot = new clsRemoteBot(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
			clsRegisterEntity.registerEntity(oBot);
        }
	}	
}
