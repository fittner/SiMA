// File clsEmotion.java
// May 02, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import memory.tempframework.clsAction;

import memory.tempframework.clsCloneable;

/*
import pkgXMLTools.clsXMLConfiguration;
import memory.tempframework.clsXMLAbstractImageReader;
import pkgXMLTools.XMLException;

import memory.tempframework.enumTypeLevelEmotion;
import memory.tempframework.enumTypeEmotion;
import memory.tempframework.enumTypeBrainInnerAction;
import memory.tempframework.enumTypeActionSource;
*/
//import java.util.Vector;

/**
 *
 * This is the class description ...
 *
 * $Revision: 1097 $:  Revision of last commit
 * $Author: riediger $: Author of last commit
 * $Date: 2008-06-16 17:33:44 +0200 (Mo, 16 Jun 2008) $: Date of last commit
 *
 */
public class clsEmotion extends clsCloneable {
  int meTypeId;
  int meLevel = enumTypeLevelEmotion.TLEVELEMOTION_UNDEFINED;
  float moValue;

  public int meHormoneAction = enumTypeBrainInnerAction.TBRAININNERACTION_UNDEFINED;
  public int meAntimoneAction = enumTypeBrainInnerAction.TBRAININNERACTION_UNDEFINED;

  String moName;

  //---------------------------------------------------------------------------
  public static clsEmotion create(Node poEmotionNode)
  //---------------------------------------------------------------------------
  {
    clsEmotion oResult = null;
    NamedNodeMap oAttributes = poEmotionNode.getAttributes();
    if (oAttributes.getLength() != 0) {
//      int nTypeId   = Integer.parseInt( oAttributes.getNamedItem("ID").getNodeValue());
      int eLevel = enumTypeLevelEmotion.getInteger( oAttributes.getNamedItem("Level").getNodeValue() );
      String oName   = oAttributes.getNamedItem("Name").getNodeValue();
      int nTypeId = enumTypeEmotion.getInteger(oName);

      int eHorm = enumTypeBrainInnerAction.getInteger( oAttributes.getNamedItem("HormonAction").getNodeValue() );
      int eAnti = enumTypeBrainInnerAction.getInteger( oAttributes.getNamedItem("AntimonAction").getNodeValue() );
  
      float rValue = enumTypeLevelEmotion.getFloatLevel( eLevel );
  
      oResult= new clsEmotion(nTypeId, rValue);
      oResult.meLevel = eLevel;
      oResult.moName = oName;
      oResult.meHormoneAction = eHorm;
      oResult.meAntimoneAction = eAnti;
    }

    return oResult;
  }

//  //---------------------------------------------------------------------------
//  public static clsContainerEmotion createEmotionList(Vector XMLFilePath, int poCreationId)
//  //---------------------------------------------------------------------------
//  {
//    try
//    {
//      clsContainerEmotion oResult = new clsContainerEmotion();
//
//      Vector emotionFileList = clsXMLConfiguration.getConfig().getEmotionFileList( XMLFilePath, poCreationId );
//
//      for( int fileCount=0; fileCount<emotionFileList.size();++fileCount)
//      {
//        String xmlFileName = (String)emotionFileList.get(fileCount);
//        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
//
//        Vector oEmotionNodes  = new Vector();
//
//        clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), "Emotion", 1, oEmotionNodes);
//        for(int i=0;i<oEmotionNodes.size();i++)                       
//        {
//          Node oEmotionNode = (Node)oEmotionNodes.get(i);
//          clsEmotion oEmotion = clsEmotion.create( oEmotionNode );
//
//          if (oEmotion != null) {
//            oResult.add( oEmotion, oEmotion.meTypeId );
//          }
//        }
//      }
//      return oResult;
//    }
//    catch(XMLException ex)
//    {
//      //Engine.log.println( "Error loading complexEmotion: "+ex.getMessage() );
//      //Engine.log.println( "  -> Bubble: "+poCreationId+" Team: "+XMLFilePath );
//    }
//    return null;
//  }

  public clsEmotion(int pnTypeId) {
    meTypeId = pnTypeId;

    moValue = 0;
    setLevel();
  }

  public clsEmotion(int pnTypeId, float prValue) {
    meTypeId = pnTypeId;

    moValue = prValue;
    setLevel();
  }
  
  public void setValue(float prValue) {
    moValue=prValue;
    setLevel();
  }

  public float getValue() {
    return moValue;
  }

  public int getType() {
    return meTypeId;
  }
  public int getLevel() {
    return meLevel;
  }
  public float getInternalValue() {
    return moValue;
  }

  public void setLevel(int level) {
    meLevel = level;
  }

  private void setLevel() {
    meLevel = enumTypeLevelEmotion.getEmotionLevel(moValue);
  }

/*  public void processDelta( float pnDelta )
  {
    //Engine.log.println( "CHANGE BASIC EMOTION - "+enumTypeEmotion.getString(meTypeId)+" - Val="+moValue.getValue()+" Delta="+pnDelta );
    moValue.addValue( pnDelta );
    //Engine.log.println( "NEW BASIC EMOTION    - "+enumTypeEmotion.getString(meTypeId)+" - Val="+moValue.getValue() );
  }*/

  public clsAction getInnerAction( float prDelta )
  {
    clsAction oResult = new clsAction();
    oResult.meSource = enumTypeActionSource.TACTIONSOURCE_INNER;

    if( prDelta > 0 )       //Hormone Action
    {
     oResult.mnId = meHormoneAction;
     oResult.moName = enumTypeBrainInnerAction.getString( meHormoneAction );
    }
    else if( prDelta > 0 )  //Antimone Action
    {
      oResult.mnId = meAntimoneAction;
      oResult.moName = enumTypeBrainInnerAction.getString( meAntimoneAction );
    }
    oResult.mrPriority = prDelta*moValue*100;

    return oResult;
  }

  public static clsEmotion createEmotion( Node poEmotionNode )
  {
    return create(poEmotionNode);
  }

  @Override
  public String toString() {
    return toString(false);
  }

  public String toString(boolean pnDetails) {
    String oResult = "";

    oResult = "emotion:"+enumTypeEmotion.getString(meTypeId)+" value:"+enumTypeLevelEmotion.getString(meLevel);

    if (pnDetails) {
     oResult += " internals:"+moValue;
    }

    return oResult;
  }
};
