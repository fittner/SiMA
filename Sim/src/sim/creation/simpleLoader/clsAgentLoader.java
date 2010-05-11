/**
 * @author langr
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation.simpleLoader;

import java.awt.Color;
import pa.clsPsychoAnalysis;
import config.clsBWProperties;
import sim.creation.clsLoader;
import simple.reactive.clsReactive;
import simple.remotecontrol.clsRemoteControl;
import ARSsim.physics2D.util.clsPose;
import bw.entities.clsBubble;
import bw.entities.clsFungusEater;
import bw.entities.clsEntity;
import bw.entities.clsRemoteBot;
import bw.entities.tools.clsShapeCreator;
import bw.factories.clsRegisterEntity;

/**
 * Helper class to load every agent and register to mason-physics2D 
 * @author langr
 * 
 */
public class clsAgentLoader {

	public static void loadAgents(int pnNumRemoteBots, int pnNumBubbles, int pnNumFungusEaters){
		loadRemoteBots(pnNumRemoteBots);
		loadBubbles(pnNumBubbles);
		loadFungusEaters(pnNumFungusEaters);
	}
	
	public static void loadBubbles(int pnNumAgents){	
		Color[] oColors = {Color.green, Color.ORANGE, Color.yellow};
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 Color oColor = Color.GREEN;
			 if(i < oColors.length)
				 oColor = oColors[i];
			 	         
	         clsBWProperties oProp = clsBubble.getDefaultProperties("Bubble.");
			 oProp.putAll( clsLoader.generateRandomPose("Bubble.", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );	         
	         oProp.setProperty("Bubble."+clsEntity.P_ID, i);
	         
	         oProp.setProperty("Bubble."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_COLOR, oColor);
	         
	         clsPsychoAnalysis oBrain = new clsPsychoAnalysis("", clsPsychoAnalysis.getDefaultProperties("") );
	         
	         clsBubble oBubble = new clsBubble(oBrain, "Bubble.", oProp );

		  	 clsRegisterEntity.registerEntity(oBubble);
         }
	}
	
	public static void loadFungusEaters(int pnNumAgents){	
		Color[] oColors = {Color.green, Color.ORANGE, Color.yellow};
		
		 for (int i = 0; i < pnNumAgents; i++)
         {
			 Color oColor = Color.GREEN;
			 if(i < oColors.length)
				 oColor = oColors[i];
			 	         
	         clsBWProperties oProp = clsFungusEater.getDefaultProperties("Fungus Eater.");
			 oProp.putAll( clsLoader.generateRandomPose("Fungus Eater.", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );	         
	         oProp.setProperty("Fungus Eater."+clsEntity.P_ID, i);
	         
	         oProp.setProperty("Fungus Eater."+clsEntity.P_SHAPE+"."+clsShapeCreator.P_COLOR, oColor);
	         
	         clsReactive oBrain = new clsReactive("", clsReactive.getDefaultProperties("") );
	         
	         clsFungusEater oFungusEater = new clsFungusEater(oBrain, "Fungus Eater.", oProp );

		  	 clsRegisterEntity.registerEntity(oFungusEater);
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
			 oProp.putAll( clsLoader.generateRandomPose("RemoteBot.", clsPose.P_POS_X, clsPose.P_POS_Y, clsPose.P_POS_ANGLE) );	         
	         
	         oProp.setProperty("RemoteBot."+clsEntity.P_ID, i);
	         
	         clsRemoteControl oBrain = new clsRemoteControl("", clsRemoteControl.getDefaultProperties("") );	         

 			clsRemoteBot oBot = new clsRemoteBot(oBrain, "RemoteBot.", oProp);

 			clsRegisterEntity.registerEntity(oBot);
        }
	}	
}
