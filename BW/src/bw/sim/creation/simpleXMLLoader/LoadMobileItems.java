/*
  Summary of file:
      Loading Mobile Objects from DOM to BubbleWorld
     
  Author:
     Nauman Qadeer
     
  Version1 date:  
     11th March 2009
*/
package bw.sim.creation.simpleXMLLoader;

import java.awt.Color;

import ARSsim.physics2D.util.clsPose;
import bw.entities.clsBubble;
import bw.entities.clsCan;
import bw.entities.clsRemoteBot;
import bw.entities.clsStone;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigMap;


import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class LoadMobileItems {

		
 public static void loadBubbles(int pnNumBubbles, NodeList nodelist){		
		 
   if (pnNumBubbles != 0)
		    		
	   try {		
		  
		System.out.println();
		System.out.println("Diplaying values for All Bubbles");
		System.out.println("--------------------------------");
		
		for (int i = 0; i < pnNumBubbles; i++)
         {
	        /* Already Existing code (As in "bw.sim.creation.simpleLoader" package)
	         
			 clsPose oStartPose = clsLoader.generateRandomPose();
		  	 clsBubble oBubble = new clsBubble(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
		  	 clsRegisterEntity.registerEntity(oBubble);
		  	 
		  	*/ 
			System.out.println();
			System.out.println("Values for Bubble "+ (i+1)+ " are: ");	 
			
		  	Node currentNode = nodelist.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
			   { 
				  Element nodeelement1 = (Element) currentNode;
				  NodeList attributelist1 = nodeelement1.getElementsByTagName("PosX");
				  Element attributeelement1 = (Element) attributelist1.item(0);
				  NodeList attributechildlist1 = attributeelement1.getChildNodes();
				  int value1 = new Integer(attributechildlist1.item(0).getNodeValue());
				  System.out.println("PosX: " + value1);
					 
				  Element nodeelement2 = (Element) currentNode;
				  NodeList attributelist2 = nodeelement2.getElementsByTagName("PosY");
				  Element attributeelement2 = (Element) attributelist2.item(0);
				  NodeList attributechildlist2 = attributeelement2.getChildNodes();
				  int value2 = new Integer(attributechildlist2.item(0).getNodeValue());
				  System.out.println("PosY: " + value2);
				  
				  Element nodeelement6 = (Element) currentNode;
				  NodeList attributelist6 = nodeelement6.getElementsByTagName("Direction");
				  Element attributeelement6 = (Element) attributelist6.item(0);
				  NodeList attributechildlist6 = attributeelement6.getChildNodes();
				  float value6 = new Float(attributechildlist6.item(0).getNodeValue());
				  System.out.println("Direction: " + value6);						  
				  
				  clsPose oStartPose = new clsPose(value1, value2, value6);
 			  	  clsBubble oBubble = new clsBubble(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), Color.green, new clsConfigMap());
				  clsRegisterEntity.registerEntity(oBubble);
			  
			    }   // End of if
			
             }   // End of For loop
		
	       }      // End try
	     catch (Exception e) 
			   {
				  System.err.println("Error Loading Bubbles ");
				  System.exit(1);
			   } 	     
   
   }       // End of loadBubbles method
	
	
  public static void loadStones(int pnNumStones, NodeList nodelist){

   if (pnNumStones != 0)
			
	 try {	

		System.out.println();
		System.out.println("Diplaying values for All Stones");
		System.out.println("--------------------------------");
		
		for (int i = 0; i < pnNumStones; i++)
         {
	        /* Already Existing code (As in "bw.sim.creation.simpleLoader" package)
	        
			   clsPose oStartPose = clsLoader.generateRandomPose();
			   double rRadius = clsSingletonMasonGetter.getSimState().random.nextDouble() * 30.0 + 10.0;
	           clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius);
		       clsRegisterEntity.registerEntity(oStone);
		  	 
		  	*/ 
			
			System.out.println();
			System.out.println("Values for Stone "+ (i+1)+ " are: ");	 
			
		  	Node currentNode = nodelist.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
			   { 
				  Element nodeelement1 = (Element) currentNode;
				  NodeList attributelist1 = nodeelement1.getElementsByTagName("PosX");
				  Element attributeelement1 = (Element) attributelist1.item(0);
				  NodeList attributechildlist1 = attributeelement1.getChildNodes();
				  int value1 = new Integer(attributechildlist1.item(0).getNodeValue());
				  System.out.println("PosX: " + value1);
					 
				  Element nodeelement2 = (Element) currentNode;
				  NodeList attributelist2 = nodeelement2.getElementsByTagName("PosY");
				  Element attributeelement2 = (Element) attributelist2.item(0);
				  NodeList attributechildlist2 = attributeelement2.getChildNodes();
				  int value2 = new Integer(attributechildlist2.item(0).getNodeValue());
				  System.out.println("PosY: " + value2);
				  
				  Element nodeelement3 = (Element) currentNode;
				  NodeList attributelist3 = nodeelement3.getElementsByTagName("Size");
				  Element attributeelement3 = (Element) attributelist3.item(0);
				  NodeList attributechildlist3 = attributeelement3.getChildNodes();
				  float value3 = new Float(attributechildlist3.item(0).getNodeValue());
				  System.out.println("Size: " + value3);
				  
			        
				   clsPose oStartPose = new clsPose(value1, value2, 0);
				   double rRadius = value3;
		           clsStone oStone = new clsStone(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), rRadius, new clsConfigMap());
			       clsRegisterEntity.registerEntity(oStone);
/*			       
				  Element nodeelement4 = (Element) currentNode;
				  NodeList attributelist4 = nodeelement4.getElementsByTagName("Height");
				  Element attributeelement4 = (Element) attributelist4.item(0);
				  NodeList attributechildlist4 = attributeelement4.getChildNodes();
				  float value4 = new Float(attributechildlist4.item(0).getNodeValue());
				  System.out.println("Height: " + value4);
				  
				  Element nodeelement5 = (Element) currentNode;
				  NodeList attributelist5 = nodeelement5.getElementsByTagName("Weight");
				  Element attributeelement5 = (Element) attributelist5.item(0);
				  NodeList attributechildlist5 = attributeelement5.getChildNodes();
				  float value5 = new Float(attributechildlist5.item(0).getNodeValue());
				  System.out.println("Weight: " + value5);
				  
				  Element nodeelement6 = (Element) currentNode;
				  NodeList attributelist6 = nodeelement6.getElementsByTagName("Color");
				  Element attributeelement6 = (Element) attributelist6.item(0);
				  NodeList attributechildlist6 = attributeelement6.getChildNodes();
				  String value6 = attributechildlist6.item(0).getNodeValue();
				  System.out.println("Color: " + value6);					     
*/					  
			    }   // End of if
			
             }   // End of For loop
			  
	       }      // End try
	     catch (Exception e) 
			   {
				  System.err.println("Error Loading Stones ");
				  System.exit(1);
			   } 
	    
    }       // End of loadStones method
	
		
  public static void loadCans(int pnNumCans, NodeList nodelist){
      	
	if (pnNumCans != 0)
		
	  try {
	
		System.out.println();
		System.out.println("Diplaying values for All Cans");
		System.out.println("--------------------------------");
		
		for (int i = 0; i < pnNumCans; i++)
         {
	        /* Already Existing code (As in "bw.sim.creation.simpleLoader" package)
	        
	        	clsPose oStartPose = clsLoader.generateRandomPose();
	        	clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
	        	clsRegisterEntity.registerEntity(oCan);
				  	 
		  	*/ 
			
			System.out.println();
			System.out.println("Values for Can "+ (i+1)+ " are: ");	 
			
		  	Node currentNode = nodelist.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
			   { 
				  Element nodeelement1 = (Element) currentNode;
				  NodeList attributelist1 = nodeelement1.getElementsByTagName("PosX");
				  Element attributeelement1 = (Element) attributelist1.item(0);
				  NodeList attributechildlist1 = attributeelement1.getChildNodes();
				  int value1 = new Integer(attributechildlist1.item(0).getNodeValue());
				  System.out.println("PosX: " + value1);
					 
				  Element nodeelement2 = (Element) currentNode;
				  NodeList attributelist2 = nodeelement2.getElementsByTagName("PosY");
				  Element attributeelement2 = (Element) attributelist2.item(0);
				  NodeList attributechildlist2 = attributeelement2.getChildNodes();
				  int value2 = new Integer(attributechildlist2.item(0).getNodeValue());
				  System.out.println("PosY: " + value2);
				  
		        	clsPose oStartPose = new clsPose(value1, value2, 0);
		        	clsCan oCan = new clsCan(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
		        	clsRegisterEntity.registerEntity(oCan);				  
			    }   // End of if
			
               }   // End of For loop
	         
	         }      // End try
	     catch (Exception e) 
			   {
				  System.err.println("Error Loading Cans ");
				  System.exit(1);
			   } 
	     
     }       // End of loadCans method
	
	
	
  public static void loadRemoteBot(int pnNumBots, NodeList nodelist) {
		
    if (pnNumBots != 0)
				
	  try {
				
		System.out.println();
		System.out.println("Diplaying values for All RemoteBots");
		System.out.println("--------------------------------");
					
		for (int i = 0; i < pnNumBots; i++)
		{
		     /* Already Existing code (As in "bw.sim.creation.simpleLoader" package)
				         
				clsPose oStartPose = clsLoader.generateRandomPose();
 			    clsRemoteBot oBot = new clsRemoteBot(i, oStartPose, new sim.physics2D.util.Double2D(0, 0));
				clsRegisterEntity.registerEntity(oBot);
				     						  	 
			  */ 
						
			  System.out.println();
			  System.out.println("Values for RemoteBot "+ (i+1)+ " are: ");	 
						
			  Node currentNode = nodelist.item(i);
			  if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
				 { 
					Element nodeelement1 = (Element) currentNode;
					NodeList attributelist1 = nodeelement1.getElementsByTagName("PosX");
					Element attributeelement1 = (Element) attributelist1.item(0);
					NodeList attributechildlist1 = attributeelement1.getChildNodes();
					int value1 = new Integer(attributechildlist1.item(0).getNodeValue());
					System.out.println("PosX: " + value1);
								 
					Element nodeelement2 = (Element) currentNode;
					NodeList attributelist2 = nodeelement2.getElementsByTagName("PosY");
					Element attributeelement2 = (Element) attributelist2.item(0);
					NodeList attributechildlist2 = attributeelement2.getChildNodes();
				    int value2 = new Integer(attributechildlist2.item(0).getNodeValue());
					System.out.println("PosY: " + value2);
					
					
					clsPose oStartPose = new clsPose(value1, value2, 0);
	 			    clsRemoteBot oBot = new clsRemoteBot(i, oStartPose, new sim.physics2D.util.Double2D(0, 0), new clsConfigMap());
					clsRegisterEntity.registerEntity(oBot);
							  
				  }   // End of if
						
			    }   // End of For loop
				         
			  }      // End try
		   catch (Exception e) 
			 {
				System.err.println("Error Loading RemoteBots ");
				System.exit(1);
			 } 
				     
	 }       // End of loadRemoteBot method			
			
}    // End of LoadMobileItems class