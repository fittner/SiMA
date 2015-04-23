// File clsAction.java
// July 10, 2006
//

// Belongs to package
package memory.tempframework;

// Imports

// Imports
import java.util.Comparator;

//import bw.memory.tempframework.clsXMLAbstractImageReader;

/**
 *
 * This is the class description ...
 *
 * $Revision: 1085 $:  Revision of last commit
 * $Author: riediger $: Author of last commit
 * $Date: 2008-06-12 12:23:37 +0200 (Do, 12 Jun 2008) $: Date of last commit
 *
 */
public class clsAction 
{
  public int mnId;
  public String moName;
  public String moDescription;

  public String moParameters;
  public int meWaitUntilAccomplished = enumTypeTrippleState.TTRIPPLE_UNDEFINED;
  public int meAccomplished = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  public float mrPriority = 0;
  public int   meSource = enumTypeActionSource.TACTIONSOURCE_UNDEFINED;
  public float mrEffectivePriority = 0; //mnPriority * (TemplateImageMatch or DesireIntesity)

  //---------------------------------------------------------------------------
  public clsAction()
  //---------------------------------------------------------------------------
  {
  }

//  //---------------------------------------------------------------------------
//  public static clsAction create(Node poActionNode)
//  //---------------------------------------------------------------------------
//  {
//    clsAction oResult = new clsAction();
//
//    NamedNodeMap oAttributes = poActionNode.getAttributes();
//    oResult.mnId   = Integer.parseInt( oAttributes.getNamedItem("ID").getNodeValue() );
//    oResult.mrPriority   = Float.parseFloat( oAttributes.getNamedItem("Priority").getNodeValue() );
//    oResult.meSource   = enumTypeActionSource.getInteger( oAttributes.getNamedItem("Source").getNodeValue() );
//
//    oResult.mrEffectivePriority = oResult.mrPriority;
//
//    oResult.moName = clsXMLAbstractImageReader.getTagStringValue(poActionNode, "Name");
//    oResult.moDescription = clsXMLAbstractImageReader.getTagStringValue(poActionNode, "Description");
//    oResult.moParameters = clsXMLAbstractImageReader.getTagStringValue(poActionNode, "Params");
//
//    oResult.meWaitUntilAccomplished = enumTypeTrippleState.getInteger( clsXMLAbstractImageReader.getTagStringValue(poActionNode, "WaitUntilAccomplished") );
//
//    //TODO: map actionId with action
//
//    return oResult;
//  }

  //---------------------------------------------------------------------------
  public static clsAction conflictAction()
  //---------------------------------------------------------------------------
  {
    clsAction oResult = new clsAction();
    oResult.mnId = enumTypeBrainAction.TBRAINACTION_INNERCONFLICT;
    oResult.moName = "Conflict generated by SUPER-EGO";
    oResult.meWaitUntilAccomplished = enumTypeTrippleState.TTRIPPLE_TRUE;
    return oResult;
  }

  //---------------------------------------------------------------------------
  public clsAction clone2()
  //---------------------------------------------------------------------------
  {
    clsAction oResult = new clsAction();
    oResult.mnId = mnId;
    oResult.moName = moName;
    oResult.moDescription = moDescription;
    oResult.moParameters = moParameters;
    oResult.meWaitUntilAccomplished = meWaitUntilAccomplished;
    oResult.mrEffectivePriority = mrEffectivePriority;
    return oResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString()
  //---------------------------------------------------------------------------
  {
    String oResult = "ID="+mnId+" Name="+moName+" Description="+moDescription+" Parameters="+moParameters+" Priority="+mrPriority+" Accomplished="+meAccomplished;
    return oResult;
  }

  public static class PriorityComparer implements Comparator 
  {
    //---------------------------------------------------------------------------
    public int compare(Object obj1, Object obj2)
    //---------------------------------------------------------------------------
    {
            float i1 = ((clsAction)obj1).mrEffectivePriority;
            float i2 = ((clsAction)obj2).mrEffectivePriority;

            return (int)(i2 - i1);
    }
  }
};
