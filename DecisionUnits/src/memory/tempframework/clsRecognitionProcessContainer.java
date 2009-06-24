// File clsRecognitionProcessContainer.java
// July 24, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
import java.util.TreeMap;
import bw.memory.tempframework.clsActionContainer;
import bw.memory.tempframework.clsContainerBaseVector;

/**
 *
 * This is the class description ...
 *
 * $Revision: 692 $:  Revision of last commit
 * $Author: langr $: Author of last commit
 * $Date: 2007-07-14 11:10:42 +0200 (Sa, 14 Jul 2007) $: Date of last commit
 *
 */
public class clsRecognitionProcessContainer extends clsContainerBaseVector
{
  //---------------------------------------------------------------------------
  public clsRecognitionProcess get(int pnPos) 
  //---------------------------------------------------------------------------
  {
    return (clsRecognitionProcess)getObject(pnPos);
  }

  //---------------------------------------------------------------------------
//  public void getTransitionIdList(TreeMap oTreeMap)
//  //---------------------------------------------------------------------------
//  {
//    for( int i=0; i<size();++i )
//    {
//      get(i).getHead().moTransitionList.getTransitionIdList( oTreeMap );
//    }
//  }

  //---------------------------------------------------------------------------  
//  public void getCurrentActionList(clsActionContainer poOuterActionList, clsActionContainer poInnerActionList)
//  //---------------------------------------------------------------------------
//  {
//    for( int i=0; i<size();++i )
//    {
//      get(i).getCurrentActionList(poOuterActionList, poInnerActionList);
//    }
//    return;
//  }

  //---------------------------------------------------------------------------  
  public void addRecognitionList(clsRecognitionProcessContainer oContainer)
  //---------------------------------------------------------------------------
  {
    for( int i=0; i<oContainer.size(); ++i )
    {
      add( oContainer.get(i) );
    }
  }

  //---------------------------------------------------------------------------
  protected String gettoString(Object poObject)
  //---------------------------------------------------------------------------
  {
    return ((clsRecognitionProcess)poObject).toString();
  }

  //---------------------------------------------------------------------------
  public void clear() 
  //---------------------------------------------------------------------------
  {
    clear();
  }

//  //---------------------------------------------------------------------------
//  public boolean containsInitializedProcess()
//  //---------------------------------------------------------------------------
//  {
//    boolean nRetVal = false;
//    for( int i=0; i<size();++i )
//    {
//      if( get(i).moRecognizedStateList.size() <= 2 )
//      {
//        nRetVal = true;
//      }
//    }
//    return nRetVal;
//  }
};
