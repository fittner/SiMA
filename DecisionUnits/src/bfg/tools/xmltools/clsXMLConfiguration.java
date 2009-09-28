// File clsXMLConfiguration.java
// June 21, 2006
//

// Belongs to package
package bfg.tools.xmltools;

// Imports
//import bfg.tools.shapes.clsPoint;
//import bfg.utils.enums.enumTypeLandscape;

//import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import statictools.clsGetARSPath;

import java.util.TreeMap;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;


/**
 *
 * This is the class description ...
 *
 * $Revision: 939 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-02-28 18:57:23 +0100 (Do, 28 Feb 2008) $: Date of last commit
 *
 */
public class clsXMLConfiguration 
{
  //singleton pattern --> get object ONLY over getConfig()!!!
  private static clsXMLConfiguration moConfig;
  private clsXMLConfiguration() { initConfigValues(); };
  public synchronized static clsXMLConfiguration getConfig()
  {
    if( moConfig == null ) //then we are the first time called -> create the one and olnly instance
    {
      moConfig = new clsXMLConfiguration();
    }
    return moConfig;
  }

  //members of configuration

  public static String moConfigurationPath;
  public static char mcXMLPathSeparator;

 /* these variables are private and never used in this class ... td
  private static String moAbstractImagePath;  //S:\ARS-PA\BubbleFamilyGame\xml\AbstractImages
  private static String moAbstractImageLinkPath;
  private static String moRoutinePath;
  private static String moScenarioPath;
  private static String moDesirePath;
  private static String moComplexEmotionPath;
*/

  private static String moEntityPath;

/* these variables are private and never used in this class ... td
  private static String moStylesheetPath;
*/ 

  public static int mnTeamCount;

  public static TreeMap moEnergySourcesList;
  public static TreeMap moNormalTeamList;
  public static TreeMap moPsychoTeamList;

  //basic XML-Nodes
//  private static clsXMLAbstractImageReader moXml;

  public String getEntityPath()
  {
    return moEntityPath;
  }

  //functions for configuration
  //---------------------------------------------------------------------------
  public static void initConfigValues()
  //---------------------------------------------------------------------------
  {
      moEnergySourcesList = new TreeMap();
      moNormalTeamList    = new TreeMap();
      moPsychoTeamList    = new TreeMap();

      //Engine.log.println( "\n-------------------------------" );
      //Engine.log.println( "reading global configuration..." );
      //Engine.log.println( "-------------------------------" );

      //Engine.log.println( "Configuration Path: " + moConfigurationPath );
  }

//  //---------------------------------------------------------------------------
//  public static void getClientConfig(String poXMLBasePath, pkgEntityContainer.clsClient poClient)
//  //---------------------------------------------------------------------------
//  {
//    try
//    {
//      String oConfigPath = poXMLBasePath+File.separator+"Configuration"+File.separator+"ClientConfig.xml";
//      
//      //Engine.log.println( "\n-------------------------------" );
//      //Engine.log.println( "reading client configuration ["+oConfigPath+"] ..." );
//      //Engine.log.println( "-------------------------------" );
//      clsXMLAbstractImageReader poXml = new clsXMLAbstractImageReader(oConfigPath);
//  
//      Node oEntityConfig = (Node)poXml.getDocument().getDocumentElement();
//      //Engine.log.println( "node: " + oEntityConfig.getNodeName() );
//      
//      Node oServer = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfig, "Server" );
//      poClient.moServerIP = clsXMLAbstractImageReader.getAttributeByName( oServer, "IP" );
//      poClient.mnServerPort = Integer.parseInt( clsXMLAbstractImageReader.getAttributeByName( oServer, "Port" ) );
//  
//      Node oDebug = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfig, "Debug" );
//      poClient.moDebugIP = clsXMLAbstractImageReader.getAttributeByName( oDebug, "IP" );
//      poClient.mnDebugPort = Integer.parseInt( clsXMLAbstractImageReader.getAttributeByName( oDebug, "Port" ) );
//      //Engine.log.println( "ClientConfig read: Server IP="+ poClient.moServerIP + " Port="+poClient.mnServerPort+" Debug IP="+poClient.moDebugIP+" Port="+poClient.mnDebugPort);
//    }
//    catch( Exception ex )
//    {
//      //Engine.log.println( "Error reading client configuration: "+ex.getMessage() );
//    }
//  }

//  //---------------------------------------------------------------------------
//  //returns a vector including teamId's that should be handled by this client
//  public static void getFactoryConfig(String poConfigSet, String poXMLBasePath, pkgEntityContainer.clsClient poClient)
//  //---------------------------------------------------------------------------
//  {
//    try
//    {
//      String oConfigPath = poXMLBasePath+File.separator+"setup_config"+File.separator+poConfigSet+"\\setup_config.xml";
//      
////      Engine.log.println( "\n-------------------------------" );
////      Engine.log.println( "reading factory configuration ["+oConfigPath+"] - set "+poConfigSet+" ..." );
////      Engine.log.println( "-------------------------------" );
//      clsXMLAbstractImageReader poXml = new clsXMLAbstractImageReader(oConfigPath);
//  
//      Node oFactoryConfig = (Node)poXml.getDocument().getDocumentElement();
////      Engine.log.println( "node: " + oFactoryConfig.getNodeName() );
//
//       //getTeamInformation
//      Vector oTeamList = new Vector();
//
//      Node oEntitiesConfig = clsXMLAbstractImageReader.getNextNodeElementByName( oFactoryConfig, "Entities");
//      clsXMLAbstractImageReader.getNodeElementByName( oEntitiesConfig, "Family", 1, oTeamList);
//
//      for( int i=0; i<oTeamList.size(); ++i)
//      {
//        Node oTeamNode = (Node)oTeamList.get(i);
//        NamedNodeMap oAttributes = oTeamNode.getAttributes();
//        int nId = clsXMLReaderTools.getNamedItemInteger( oAttributes, "ID");
//        String oDirName = clsXMLReaderTools.getNamedItemString( oAttributes, "DirName");
//        String oTeamType = clsXMLReaderTools.getNamedItemString( oAttributes, "Type");
//        int oCount = clsXMLReaderTools.getNamedItemInteger( oAttributes, "Count");
//        poClient.addTeam( nId, oDirName, oTeamType, oTeamNode, oCount );
//      }         
//
////      Engine.log.println( "FactoryConfig read - Teams: " + oTeamList.size() );
//    }
//    catch( Exception ex )
//    {
//      Engine.log.println( "Error reading factory configuration: "+ex.getMessage() );
//    }
//  }

//  //---------------------------------------------------------------------------
//  //returns a vector including teamId's that should be handled by this client
//  public static void getObstacleConfig(String poConfigSet, String poXMLBasePath, /*out*/ Vector poObstacleConfigList)
//  //---------------------------------------------------------------------------
//  {
//    if( poObstacleConfigList == null)
//    {
//      poObstacleConfigList = new Vector();    
//    }
//
//    try
//    {
//      String oConfigPath = poXMLBasePath+File.separator+"setup_config"+File.separator+poConfigSet+"\\setup_config.xml";
//      
///*      Engine.log.println( "\n-------------------------------" );
//      Engine.log.println( "reading obstacles out of factory configuration ["+oConfigPath+"] - set "+poConfigSet+" ..." );
//      Engine.log.println( "-------------------------------" );
//*/      clsXMLAbstractImageReader poXml = new clsXMLAbstractImageReader(oConfigPath);
//  
//      Node oFactoryConfig = (Node)poXml.getDocument().getDocumentElement();
////      Engine.log.println( "node: " + oFactoryConfig.getNodeName() );
//
//      //getTeamInformation
//      Vector oObstacleList = new Vector();
//
//      Node oEntitiesConfig = clsXMLAbstractImageReader.getNextNodeElementByName( oFactoryConfig, "Entities");
//      clsXMLAbstractImageReader.getNodeElementByName( oEntitiesConfig, "Obstacle", 1, oObstacleList);
//
//      for( int i=0; i<oObstacleList.size(); ++i)
//      {
//        Node oObstacleNode = (Node)oObstacleList.get(i);
//        NamedNodeMap oAttributes = oObstacleNode.getAttributes();
//        int nId = clsXMLReaderTools.getNamedItemInteger( oAttributes, "ID");
//        String oDirName = clsXMLReaderTools.getNamedItemString( oAttributes, "DirName");
//        int oCount = clsXMLReaderTools.getNamedItemInteger( oAttributes, "Count");
//        clsTeamConfig oTeamConfig = new clsTeamConfig( nId, "obstacle", oDirName, oObstacleNode, oCount );
//        poObstacleConfigList.add( oTeamConfig );
//      }         
//
////      Engine.log.println( "FactoryConfig read - obstacle types: " + poObstacleConfigList.size() );
//    }
//    catch( Exception ex )
//    {
//      Engine.log.println( "Error reading factory configuration: "+ex.getMessage() );
//    }
//  }


//  //---------------------------------------------------------------------------
//  private static Node getWorldConfigNode(String poConfigSet, String poXMLBasePath) throws XMLException
//  //---------------------------------------------------------------------------
//  {
//      String oConfigPath = poXMLBasePath+File.separator+"setup_config"+File.separator+poConfigSet+"\\setup_config.xml";
//      clsXMLAbstractImageReader poXml = new clsXMLAbstractImageReader(oConfigPath);
//  
//      Node oFactoryConfig = (Node)poXml.getDocument().getDocumentElement();
//
//      //search for used world in scenario
//      Node oWorldConfig = clsXMLAbstractImageReader.getNextNodeElementByName( oFactoryConfig, "World");
//      String oWorldPath = clsXMLAbstractImageReader.getAttributeByName( oWorldConfig, "DirName" );
//
//      //load landscapes from world_config file
//      oConfigPath = poXMLBasePath+File.separator+"world_config"+File.separator+oWorldPath+"\\world_config.xml";
//      poXml = new clsXMLAbstractImageReader(oConfigPath);
//      oWorldConfig = (Node)poXml.getDocument().getDocumentElement();
//
//      return oWorldConfig;
//  }

//  //---------------------------------------------------------------------------
//  public static clsBoundingBox getWorldsizeConfig(String poConfigSet, String poXMLBasePath)
//  //---------------------------------------------------------------------------
//  {
//    clsBoundingBox oBox = new clsBoundingBox();
//
//    try
//    {
////      Vector oShapeList = new Vector();
//
//      Node oWorldConfig = getWorldConfigNode(poConfigSet, poXMLBasePath);
//      Node oWorldLimits = clsXMLAbstractImageReader.getNextNodeElementByName( oWorldConfig, "Worldsize" );
//      NamedNodeMap oAttributes = oWorldLimits.getAttributes();
//
//      float rWidth = Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttributes, "width") );     
//      float rHeight = Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttributes, "height") );
//
//      oBox.set(rWidth, rHeight);
//
//      Engine.log.println( "FactoryConfig read - limits: " + oBox );
//    }
//    catch( Exception ex )
//    {
//      Engine.log.println( "Error reading factory configuration: "+ex.getMessage() );
//    }
//
//    return oBox;
//  }

//  //---------------------------------------------------------------------------
//  public static void getLandscapeConfig(String poConfigSet, String poXMLBasePath, /*out*/ Vector poLandscapeConfigList)
//  //---------------------------------------------------------------------------
//  {
//    if( poLandscapeConfigList == null)
//    {
//      poLandscapeConfigList = new Vector();    
//    }
//
//    try
//    {
//      Vector oShapeList = new Vector();
//
//      Node oWorldConfig = getWorldConfigNode(poConfigSet, poXMLBasePath);
//      Node oLandscapes = clsXMLAbstractImageReader.getNextNodeElementByName( oWorldConfig, "Landscapes" );
//            
//      clsXMLAbstractImageReader.getNodeElementByName( oLandscapes, "Shape", 1, oShapeList);
//
//      for( int i=0; i<oShapeList.size(); ++i)
//      {
//        Node oShapeNode = (Node)oShapeList.get(i);
//        NamedNodeMap oAttributes = oShapeNode.getAttributes();
//        
//
//        int nID = enumTypeLandscape.getInteger( clsXMLReaderTools.getNamedItemString( oAttributes, "ID") );
//        String oName = clsXMLReaderTools.getNamedItemString( oAttributes, "Name");
//        int nType = enumTypeLandscape.getInteger( clsXMLReaderTools.getNamedItemString( oAttributes, "Type") );
//
//        clsPoint oOffset = new clsPoint(
//          Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttributes, "offsetX") ),
//          Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttributes, "offsetY") )
//        );
//
//        float rScale = Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttributes, "scale") );
//
//        clsLandscape oLandscape = new clsLandscape( nID, oName, nType );
//
//        Vector oPointList = new Vector();
//        clsXMLAbstractImageReader.getNodeElementByName( oShapeNode, "Point", 1, oPointList);
//
//        for( int u=0; u<oPointList.size();++u)
//        {
//          Node oPointNode = (Node)oPointList.get(u);
//          NamedNodeMap oAttrib = oPointNode.getAttributes();
//
//          float rPosX = Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttrib, "posX") );
//          float rPosY = Float.parseFloat( clsXMLReaderTools.getNamedItemString( oAttrib, "posY") );
//          clsPoint oPoint = new clsPoint( rPosX, rPosY);
//          oPoint.add(oOffset);
//
//          oLandscape.moPolygon.addPoint(oPoint);
//        }
//
//        Engine.log.println("LANDSCAPE --------------------------------------\n"+oLandscape);
//        oLandscape.moPolygon.scale(rScale);
//        Engine.log.println("LANDSCAPE --------------------------------------\n"+oLandscape);
//        Engine.log.println("------------------------------------------------");
//
//        poLandscapeConfigList.add( oLandscape );
//      }         
//
//      Engine.log.println( "FactoryConfig read - landscapes: " + poLandscapeConfigList.size() );
//    }
//    catch( Exception ex )
//    {
//      Engine.log.println( "Error reading factory configuration: "+ex.getMessage() );
//    }
//  }

  
  //---------------------------------------------------------------------------
  public static void ReadTeamTypeInformation(int pnTeamID) throws XMLException
  //---------------------------------------------------------------------------
  {
    clsXMLAbstractImageReader oXml = new clsXMLAbstractImageReader(moEntityPath+pnTeamID+File.separator+"EntityConfig.xml");
    Node oEntityConfigTeam = (Node)oXml.getDocument().getDocumentElement();
    
    Node teamMember = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfigTeam, "TeamInfo" );
    String oTeamType = clsXMLAbstractImageReader.getAttributeByName( teamMember, "type" );

    if( oTeamType.equals("energySource") )
    {
      //Engine.log.println( "Team "+pnTeamID+" - EnergySource");
      //moEnergySourcesList.put(new Integer(pnTeamID), oEntityConfigTeam);
      moEnergySourcesList.put(new Integer(pnTeamID), oXml);  //cant be the node - 'cause garbage collector deletes base-object
    }                    
    else if( oTeamType.equals("non_psycho") )
    {
      //Engine.log.println( "Team "+pnTeamID+" - NON_PSYCHO");
      moNormalTeamList.put(new Integer(pnTeamID), oXml);
    }
    else if( oTeamType.equals("psycho") )
    {
      //Engine.log.println( "Team "+pnTeamID+" - PSYCHO");
      moPsychoTeamList.put(new Integer(pnTeamID), oXml);
    }
   }

  //---------------------------------------------------------------------------
  public static int ReadTeamCount(Node oEntityConfigTeam)
  //---------------------------------------------------------------------------
  {
    Node teamMember = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfigTeam, "TeamInfo" );
    String oTeamCountTeam = clsXMLAbstractImageReader.getAttributeByName( teamMember, "count" );
    return Integer.parseInt(oTeamCountTeam);
  }
/*
  //---------------------------------------------------------------------------
  public static void ReadTeamInformation(int pnTeamID) throws XMLException
  //---------------------------------------------------------------------------
  {
    //Engine.log.println( "\nreading entity configuration for team " + pnTeamID + " ... in " +moEntityPath+pnTeamID+File.separator+"EntityConfig.xml" );
    moXml = new clsXMLAbstractImageReader(moEntityPath+pnTeamID+File.separator+"EntityConfig.xml");
    Node oEntityConfigTeam = (Node)moXml.getDocument().getDocumentElement();
    //Engine.log.println( "node: " + oEntityConfigTeam.getNodeName() );
    
    Node teamMember = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfigTeam, "TeamInfo" );
    String oTeamCountTeam = clsXMLAbstractImageReader.getAttributeByName( teamMember, "count" );
    String oTeamType = clsXMLAbstractImageReader.getAttributeByName( teamMember, "type" );
    int nTeamCountTeam = Integer.parseInt(oTeamCountTeam);
    //Engine.log.println( "TeamCount: " +  nTeamCountTeam );
  
    Node initVal = clsXMLAbstractImageReader.getNextNodeElementByName( oEntityConfigTeam, "InitialValues" );
  
    if( oTeamType.equals("energySource") )
    {
      int nEnergyLevel = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "energyLevel" ));
      int nSize = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "size" ));
      //Engine.log.println( "InitialValues of energySource: energyLevel="+nEnergyLevel+";size="+nSize);
  
      for( int u=0;u<nTeamCountTeam;++u)
      {
        if( !ReadESInfo(u, oEntityConfigTeam) )
        {
          //Engine.log.println( "EnergySource"+u+"-DEFValue: energy="+nEnergyLevel+";size="+nSize);
          //create Bubble with default values
          // createBubble( pnTeamId, pnBubbleNumber, nEnergyLevel, nHungerLevel, nLustLevel, nAngerLevel, "random");
        }
      }
    }
    else
    {
      int nEnergyLevel = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "energyLevel" ));
      int nHungerLevel = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "hunger" ));
      int nLustLevel = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "lust" ));
      int nAngerLevel = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( initVal, "anger" ));
      //Engine.log.println( "DefaultValues: energy="+nEnergyLevel+";hunger="+nHungerLevel+";lust="+nLustLevel+";anger="+nAngerLevel);
  
      //for each bubble
      for( int u=0;u<nTeamCountTeam;++u)
      {
        if( !ReadBubbleInfo(u, oEntityConfigTeam) )
        {
          //Engine.log.println( "Bubble"+u+"-DEFValue: energy="+nEnergyLevel+";hunger="+nHungerLevel+";lust="+nLustLevel+";anger="+nAngerLevel);
          //create Bubble with default values
          // createBubble( pnTeamId, pnBubbleNumber, nEnergyLevel, nHungerLevel, nLustLevel, nAngerLevel, "random");
        }
      }
    }
  }
*/  
/*
  //---------------------------------------------------------------------------
  public static boolean ReadBubbleInfo(int pnBubbleNumber, Node poEntityConfigTeam)
  //---------------------------------------------------------------------------
  {
    String bubbleNodeName = "Bubble"+pnBubbleNumber;
    Node bubbleNode = clsXMLAbstractImageReader.getNextNodeElementByName( poEntityConfigTeam, bubbleNodeName );

    if( bubbleNode != null )
    {
      int nEnergyLevelBubble = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "energyLevel" ));
      int nHungerLevelBubble = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "hunger" ));
      int nLustLevelBubble = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "lust" ));
      int nAngerLevelBubble = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "anger" ));
  
      String position = clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "position" );
  
      int positionX = 0;
      int positionY = 0;
  
      if( position.equals("given") )
      {
        positionX = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "positionx" ));
        positionY = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( bubbleNode, "positiony" ));              
      }
      else
      {
        //getRandomPositionValues!!!!
      }                    
  
      //Engine.log.println( bubbleNodeName+"-Value: energy="+nEnergyLevelBubble+";hunger="+nHungerLevelBubble+";lust="+nLustLevelBubble+";anger="+nAngerLevelBubble+";position="+position+";posX="+positionX+";posY="+positionY);
  
      //meaning:
      // createBubble( pnTeamId, pnBubbleNumber, nEnergyLevelBubble, nHungerLevelBubble, nLustLevelBubble, nAngerLevelBubble, position, positionX, positionY);
      return true;
    }
    else
    {
      return false;
    }
  }
*/
/*
  //---------------------------------------------------------------------------
  public static boolean ReadESInfo(int pnESNumber, Node poEntityConfigTeam)
  //---------------------------------------------------------------------------
  {
    String esNodeName = "EnergySource"+pnESNumber;
    Node esNode = clsXMLAbstractImageReader.getNextNodeElementByName( poEntityConfigTeam, esNodeName );

    if( esNode != null )
    {
      int nEnergyLevelBubble = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( esNode, "energyLevel" ));
      int nSize = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( esNode, "size" ));
  
      String oPosition = clsXMLAbstractImageReader.getAttributeByName( esNode, "position" );
  
      int nPositionX = 0;
      int nPositionY = 0;
  
      if( oPosition.equals("given") )
      {
        nPositionX = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( esNode, "positionx" ));
        nPositionY = Integer.parseInt(clsXMLAbstractImageReader.getAttributeByName( esNode, "positiony" ));              
      }
      else
      {
        //getRandomPositionValues!!!!
      }                    
  
      //Engine.log.println( esNodeName+"-Value: energy="+nEnergyLevelBubble+";size="+nSize+";position="+oPosition+";posX="+nPositionX+";posY="+nPositionY);
  
      //meaning:
      // createBubble( pnTeamId, pnBubbleNumber, nEnergyLevelBubble, nHungerLevelBubble, nLustLevelBubble, nAngerLevelBubble, position, positionX, positionY);
      return true;
    }
    else
    {
      return false;
    }
  }
*/

  //---------------------------------------------------------------------------
  public static void getRelativeConfigs(String poPathToXMLConfigFile, Vector poRelativeConfigs)
  //---------------------------------------------------------------------------
  {
    try
    {
      String oRelativeConfigs = moConfigurationPath + "\\entity_config\\" + poPathToXMLConfigFile + "\\includes.xml";
//      Engine.log.print( "\nmoConfigPath: " + moConfigurationPath);
//      Engine.log.print( "\npoPathToXMLConfigFile: " + poPathToXMLConfigFile);
  
      //read config file
      clsXMLAbstractImageReader oXml = new clsXMLAbstractImageReader(oRelativeConfigs);
      Node oRelativeConfig = (Node)oXml.getDocument().getDocumentElement();
  
      Node oRelativeCount = clsXMLAbstractImageReader.getNextNodeElementByName( oRelativeConfig, "IncludeCount" );
      int nRelativeCount = Integer.parseInt( clsXMLAbstractImageReader.getAttributeByName( oRelativeCount, "count" ));
  
      for( int i=0; i<nRelativeCount; ++i )
      {
        Node oRelativeDir = clsXMLAbstractImageReader.getNextNodeElementByName( oRelativeConfig, "IncludeDir_"+i );      
        poRelativeConfigs.add( clsXMLAbstractImageReader.getAttributeByName( oRelativeDir, "path" ) );
      }
  
    }
    catch( XMLException ex)
    {
    }
    poRelativeConfigs.add( poPathToXMLConfigFile );
  }

  //---------------------------------------------------------------------------
  public static Vector getReactiveActionsList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();
    for(int i=0; i<poTeamPath.size();++i)
    {
      String oReactiveActionsPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\ReactiveActions\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oReactiveActionsPath, oResult );
    }
    
    return oResult;
  }

  //---------------------------------------------------------------------------
  public static Vector getEmotionFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();

    for(int i=0; i<poTeamPath.size();++i)
    {
      String oEmotionPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\Emotion\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oEmotionPath, oResult );
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
  public static Vector getComplexEmotionFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();

    for(int i=0; i<poTeamPath.size();++i)
    {
      String oComplexEmotionPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\ComplexEmotion\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oComplexEmotionPath, oResult );
    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  public static Vector getAbstractImageFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();

    for(int i=0; i<poTeamPath.size();++i)
    {
      String oAbstractImagesPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\AbstractImages\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oAbstractImagesPath, oResult );
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
  public static Vector getRoutineFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();
    for(int i=0; i<poTeamPath.size();++i)
    {
      String oRoutinePath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\Routine\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oRoutinePath, oResult );
    }
    return oResult;  
  }

  //---------------------------------------------------------------------------
  public static Vector getScenarioFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();
    for(int i=0; i<poTeamPath.size();++i)
    {
      String oScenarioPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\Scenario\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oScenarioPath, oResult );
    }
    return oResult;     
  }
  //---------------------------------------------------------------------------
  public static Vector getDesireFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();
    for(int i=0; i<poTeamPath.size();++i)
    {
      String oDesirePath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\Desire\\";
      getFileList( pnTeamId, (String)poTeamPath.get(i), oDesirePath, oResult );
    }
    return oResult;  
  }
  
  //---------------------------------------------------------------------------
  public static Vector getAbilityFileList( Vector poTeamPath, int pnTeamId )
  //---------------------------------------------------------------------------
  {
    Vector oResult = new Vector();
    for(int i=0; i<poTeamPath.size();++i)
    {
      String oAbilityPath = moConfigurationPath + "\\entity_config\\"+(String)poTeamPath.get(i)+"\\Ability\\";
      getFileList(pnTeamId, (String)poTeamPath.get(i), oAbilityPath, oResult);
    }
    return oResult;  
  }
/*  
  //---------------------------------------------------------------------------
  private static String rel2absPath( String fileName )
  //---------------------------------------------------------------------------
  // converts relative to absolute Paths, thereby fixing Windows vs. Unix path separator problem
  {
	fileName = fileName.replace(mcXMLPathSeparator, File.separatorChar);
	if (! new File(fileName).isAbsolute()) {
	  File oConfDir = new File(moConfigurationPath).getParentFile();
	  return new File(oConfDir, fileName).getAbsolutePath();
	} else return fileName;
  }
*/
/*  //---------------------------------------------------------------------------
  public static void getFileList( int pnBubbleId, int pnTeamId, String poDirectoryPath, Vector poOutputVector )
  //---------------------------------------------------------------------------
  {
    File aiDir = new File(poDirectoryPath); 
    try
    {
      //MUST EXIST!
      List generalAbstractImages = getFilesInDirectory( aiDir );
      Iterator filesIter = generalAbstractImages.iterator();
      while( filesIter.hasNext() ){
        poOutputVector.add( ((File)filesIter.next()).toString() );
      }

      try
      {
        List teamAbstractImages = getFilesInDirectory( new File( aiDir+File.separator+"Team_"+pnTeamId ) );
        filesIter = teamAbstractImages.iterator();
        while( filesIter.hasNext() ){
         poOutputVector.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }
      try
      {
        List bubbleAbstractImages = getFileListingRecursive( new File( aiDir+File.separator+"Team_"+pnTeamId+File.separator+pnBubbleId ) );
        filesIter = bubbleAbstractImages.iterator();
        while( filesIter.hasNext() ){
          poOutputVector.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }

//      for( int i=0; i< oResult.size(); ++i )
//      {
//        //Engine.log.println( oResult.get(i) );
//      }
    }
    catch( Exception e)
    {
      //Engine.log.println(e.getMessage());
    }
  }
*/
  
  //---------------------------------------------------------------------------
  public static ArrayList<String> getFileList( String poAgentId, String poGroupName, String poQueryType )
  //---------------------------------------------------------------------------
  {
	  ArrayList<String> oRetVal = new ArrayList<String>();
	
	  String oPath = clsGetARSPath.getXMLPathEntity()+poQueryType;
	  
		try
		{
			File aiDir = new File(oPath);
			List generalQueryFiles = getFilesInDirectory( aiDir );
			Iterator filesIter = generalQueryFiles.iterator();
			while( filesIter.hasNext() ){
				oRetVal.add( ((File)filesIter.next()).toString() );
			}
		}
	  catch( FileNotFoundException ex)
	  {
	    ////Engine.log.println( ex.getMessage() );
	  }

	  oPath += clsGetARSPath.getSeperator() + poGroupName;
      try
      {
    	List teamQueryFiles = getFilesInDirectory( new File( oPath ) );
        Iterator filesIter = teamQueryFiles.iterator();
        while( filesIter.hasNext() ){
        	oRetVal.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }
	  oPath += clsGetARSPath.getSeperator() + poAgentId;
      try
      {
        List agentQueryFiles = getFileListingRecursive( new File( oPath ) );
        Iterator filesIter = agentQueryFiles.iterator();
        while( filesIter.hasNext() ){
        	oRetVal.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }
      return oRetVal;
  }
  
    //---------------------------------------------------------------------------
  public static void getFileList( int pnBubbleId, String poTeamPath, String poDirectoryPath, Vector poOutputVector )
  //---------------------------------------------------------------------------
  {
    File aiDir = new File(poDirectoryPath);
    try
    {
      //MUST EXIST!
      List generalAbstractImages = getFilesInDirectory( aiDir );
      Iterator filesIter = generalAbstractImages.iterator();
      while( filesIter.hasNext() ){
        poOutputVector.add( ((File)filesIter.next()).toString() );
      }

      try
      {
        List teamAbstractImages = getFilesInDirectory( new File( aiDir + File.separator + poTeamPath ) );
        filesIter = teamAbstractImages.iterator();
        while( filesIter.hasNext() ){
         poOutputVector.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }
      try
      {
        List bubbleAbstractImages = getFileListingRecursive( new File( aiDir+File.separator+poTeamPath+File.separator+pnBubbleId ) );
        filesIter = bubbleAbstractImages.iterator();
        while( filesIter.hasNext() ){
          poOutputVector.add( ((File)filesIter.next()).toString() );
        }
      }
      catch( FileNotFoundException ex)
      {
        ////Engine.log.println( ex.getMessage() );
      }

//      for( int i=0; i< oResult.size(); ++i )
//      {
//        //Engine.log.println( oResult.get(i) );
//      }
    }
    catch( Exception e)
    {
      //Engine.log.println(e.getMessage());
    }
  }

  public static List getFileListingRecursive( File aStartingDir ) throws FileNotFoundException
  {
    validateDirectory(aStartingDir);
    List result = new ArrayList();
  
    File[] filesAndDirs = aStartingDir.listFiles();
    List filesDirs = Arrays.asList(filesAndDirs);
    Iterator filesIter = filesDirs.iterator();
    File file = null;
    while ( filesIter.hasNext() ) {
      file = (File)filesIter.next();
      if (!file.isFile()) {
        //must be a directory
        //recursive call!
        List deeperList = getFileListingRecursive(file);
        result.addAll(deeperList);
      }
      else  //in case of real file only search xml-files
      {
        if( getExtension(file).equals("xml") )
        {
          result.add(file); //always add, even if directory
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  public static List getFilesInDirectory( File aStartingDir ) throws FileNotFoundException
  {
    validateDirectory(aStartingDir);
    List result = new ArrayList();
  
    File[] filesAndDirs = aStartingDir.listFiles();
    List filesDirs = Arrays.asList(filesAndDirs);
    Iterator filesIter = filesDirs.iterator();
    File file = null;
    while ( filesIter.hasNext() ) {
      file = (File)filesIter.next();
      if ( file.isFile() )
      {
        if( getExtension(file).equals("xml") )
        {
          result.add(file); //always add, even if directory
        }
      }
    }
    Collections.sort(result);
    return result;
  }

  private static void validateDirectory (File aDirectory) throws FileNotFoundException 
  {
    if (aDirectory == null) {
      throw new IllegalArgumentException("Directory should not be null.");
    }
    if (!aDirectory.exists()) {
      throw new FileNotFoundException("Directory does not exist: " + aDirectory);
    }
    if (!aDirectory.isDirectory()) {
      throw new IllegalArgumentException("Is not a directory: " + aDirectory);
    }
    if (!aDirectory.canRead()) {
      throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
    }
  }
  
  private static String getExtension(File f)
  {
    String s = f.getName();
    int i = s.lastIndexOf('.');
    if (i > 0 &&  i < s.length() - 1)
      return s.substring(i+1).toLowerCase();
    return "";
  }

};
