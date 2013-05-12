// File clsImageAbstractAction.java
// September 12, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
//import pkgXMLTools.clsXMLConfiguration;
//import pkgXMLTools.XMLException;
//import pkgXMLTools.clsXMLAbstractImageReader;
//import org.w3c.dom.Node;
//import org.w3c.dom.NamedNodeMap;
//import java.util.Vector;
//import java.util.TreeMap;
//import pkgBrainAction.clsActionPlan;

/**
 *
 * This is the class description ...
 *
 * $Revision: 682 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-11 16:48:22 +0200 (Mi, 11 Jul 2007) $: Date of last commit
 *
 */
public class clsImageAbstractAction {

//  public static TreeMap moAIActionLink = new TreeMap();
//
//  //---------------------------------------------------------------------------
//  public String toString() 
//  //---------------------------------------------------------------------------
//  {
//    String oResult = "";
//
//    oResult += "moAIActionLink: "+moAIActionLink;
//
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public static void loadActionLinkList(Vector poTeamDirectory, int pnTeamId)
//  //---------------------------------------------------------------------------
//  {
//    clsContainerAbstractImages oResult   = new clsContainerAbstractImages();
//    try
//    {
//      Vector actionFileList = clsXMLConfiguration.getConfig().getReactiveActionsList( poTeamDirectory, pnTeamId );
//      for( int fileCount=0; fileCount<actionFileList.size();++fileCount)
//      {
//        String oFilePath = (String)actionFileList.get(fileCount);
//        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(oFilePath);
//    
//        Vector oNodeList = new Vector();
//        clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), "AbstractImageAction", 1, oNodeList);
//        for(int i=0;i<oNodeList.size();i++)                       
//        {
//          Node oActionLinkNode = (Node)oNodeList.get(i);
//      
//          NamedNodeMap oAattribs = oActionLinkNode.getAttributes();
//          int oImageId  = Integer.parseInt( oAattribs.getNamedItem("AbstractImageID").getNodeValue() );
//  
//          Vector oPlanList = new Vector();
//          clsXMLAbstractImageReader.getNodeElementByName( oActionLinkNode, "ActionPlan", 1, oPlanList);
//          //Engine.log.println( "OPLANLIST size="+oPlanList.size() );
//          if( oPlanList.size() > 0 )
//          {
//            //Engine.log.println( "ACTIONPLAN: createing actionPlan");
//            clsActionPlan oPlan = clsActionPlan.create( (Node)oPlanList.get(0) );
//            //Engine.log.println( "ACTIONPLAN: " + oPlan.toString() );
//            moAIActionLink.put( new Integer( oImageId ), oPlan );
//          }
//        }
//      }
//      //Engine.log.println( "actionLinkList size="+moAIActionLink);
//    }
//    catch(XMLException ex)
//    {
//      //Engine.log.println( "Error loading ActionLinkList: "+ex.getMessage() );
//    }
//  }
//
//  //---------------------------------------------------------------------------
//  public static clsActionPlan getAction( int mnAbstractImageId )
//  //---------------------------------------------------------------------------
//  {
//    return (clsActionPlan)moAIActionLink.get( new Integer(mnAbstractImageId) );
//  }
};
