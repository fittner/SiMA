/**
 * @author qadeer
 * 11.03.2009, 17:47:01
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation.simpleXMLLoader;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsBubble;
import bw.entities.clsCan;
import bw.entities.clsRemoteBot;
import bw.entities.clsStone;
import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.sim.creation.clsLoader;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class LoadMobileItems {

		
	public static void loadBubbles(int pnNumBubbles, NodeList nodelist){		
		 for (int i = 0; i < pnNumBubbles; i++)
         {
	         clsPose oStartPose = clsLoader.generateRandomPose();
		  	 clsBubble oBubble = new clsBubble(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
		  	 clsRegisterEntity.registerEntity(oBubble);
         }
	}
	
	public static void loadStones(int pnNumStones, NodeList nodelist){
		for (int i = 0; i < pnNumStones; i++)
        {
			clsPose oStartPose = clsLoader.generateRandomPose();
			
			//FIXME warum ist der Radius random? wenn ein Bild drüber ist, dann muss der Radius dem Bild entsprechen!
			double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
	        
		    clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius);
		    clsRegisterEntity.registerEntity(oStone);
        }
	}
	
		
	public static void loadCans(int pnNumCans, NodeList nodelist){
        for (int i = 0; i < pnNumCans; i++)
        {
        	clsPose oStartPose = clsLoader.generateRandomPose();
	        clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
	        
	        clsRegisterEntity.registerEntity(oCan);
        }
		
	}
	
	
	public static void loadRemoteBot(int pnNumBots, NodeList nodelist) {
		for (int i = 0; i < pnNumBots; i++) {
	        clsPose oStartPose = clsLoader.generateRandomPose();
 			clsRemoteBot oBot = new clsRemoteBot(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
			clsRegisterEntity.registerEntity(oBot);
        }
	}	
	
}
