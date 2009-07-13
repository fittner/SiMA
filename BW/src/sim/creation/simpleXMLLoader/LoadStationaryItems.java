/*
Summary of file:
    Loading Stationary Objects from DOM to BubbleWorld
   
Author:
   Nauman Qadeer
   
Version1 date:  
   11th March 2009
*/

package sim.creation.simpleXMLLoader;
import ARSsim.physics2D.util.clsPose;
import bw.entities.clsWall;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class LoadStationaryItems {
	

 public static void loadWorldBoundaries(NodeList nodelist){
		
  if (nodelist.getLength() != 0)
	    		
	 try {		
	     		  
	    System.out.println();
	    System.out.println("Diplaying values for All Walls");
	    System.out.println("--------------------------------");
	     		
	    for (int i = 0; i < nodelist.getLength(); i++)
	      {
	     	 /* Already Existing code (As in "bw.sim.creation.simpleLoader" package)
	     	         
	     	    clsPose oPose;
				clsWall oWall;
			
	        	// HORIZ
				oPose = new clsPose(100, 0, 0);
	        	oWall = new clsWall(1, oPose, 193, 6);
	        	clsRegisterEntity.registerEntity(oWall);
	       		clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	 
	         	
	        	oPose = new clsPose(100, 200, 0);
	        	oWall = new clsWall(2, oPose, 193, 6);
	        	clsRegisterEntity.registerEntity(oWall);
	        	clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall1.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	                
	        	// VERT
	        	oPose = new clsPose(0, 100, 0);
	        	oWall = new clsWall(3, oPose, 6, 200);
	        	clsRegisterEntity.registerEntity(oWall);
	        	clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	        
	        	oPose = new clsPose(200, 100, 0);
	        	oWall = new clsWall(4, oPose, 6, 200);
	        	clsRegisterEntity.registerEntity(oWall);
	        	clsImagePortrayal.PlaceImage(bw.sim.clsBWMain.msArsPath + "/src/resources/images/wall2.jpg", 8, new sim.util.Double2D(oPose.getPosition().x, oPose.getPosition().y), clsSingletonMasonGetter.getFieldEnvironment());
	     		  	 
	     	 */ 
	     	 System.out.println();
	     	 System.out.println("Values for Wall "+ (i+1)+ " are: ");	 
	     			
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
	     			NodeList attributelist3 = nodeelement3.getElementsByTagName("Length");
	     			Element attributeelement3 = (Element) attributelist3.item(0);
	     			NodeList attributechildlist3 = attributeelement3.getChildNodes();
	     			double value3 = new Double(attributechildlist3.item(0).getNodeValue());
	     			System.out.println("Length: " + value3);
	     				  
	     			Element nodeelement5 = (Element) currentNode;
	     			NodeList attributelist5 = nodeelement5.getElementsByTagName("Width");
	     			Element attributeelement5 = (Element) attributelist5.item(0);
	     			NodeList attributechildlist5 = attributeelement5.getChildNodes();
	     			double value5 = new Double(attributechildlist5.item(0).getNodeValue());
	     			System.out.println("Width: " + value5);	     				  

	     			Element nodeelement4 = (Element) currentNode;
	     			NodeList attributelist4 = nodeelement4.getElementsByTagName("Direction");
	     			Element attributeelement4 = (Element) attributelist4.item(0);
	     			NodeList attributechildlist4 = attributeelement4.getChildNodes();
	     			double value4 = new Double(attributechildlist4.item(0).getNodeValue());
	     			System.out.println("Direction: " + value4);		
	     			
	     			
	     			
	     			clsPose oPose = new clsPose(value1, value2, value4);
		        	clsWall oWall = new clsWall(1, oPose, value5, value3, new clsConfigMap());
		        	clsRegisterEntity.registerEntity(oWall);
	     					  
	     		 }   // End of if
	     			
	           }   // End of For loop
	     		
	     	}      // End try
	      catch (Exception e) 
	     	{
	     		System.err.println("Error Loading Walls ");
	     		System.exit(1);
	     	 } 	     
	        
    }       // End of loadWorldBoundaries method        
     
		
} // End of LoadStationaryItems class
