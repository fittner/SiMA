// File clsActionContainer.java
// July 10, 2006
//

// Belongs to package
package students.borer.episodicMemory.tempframework;

// Imports
import students.borer.episodicMemory.tempframework.clsContainerBaseVector;
import students.borer.episodicMemory.tempframework.enumTypeTrippleState;


/**
 *
 * This is the class description ...
 *
 * $Revision: 922 $:  Revision of last commit
 * $Author: gartner $: Author of last commit
 * $Date: 2008-02-26 14:27:22 +0100 (Di, 26 Feb 2008) $: Date of last commit
 *
 */
public class clsActionContainer extends clsContainerBaseVector
{
  /**
	 * @author deutsch
	 * 10.08.2010, 17:44:51
	 */
	private static final long serialVersionUID = 446418109514984548L;

public clsAction get(int pnPos) {
    return (clsAction)getObject(pnPos);
  }

//  public static clsActionContainer create(Vector poActionList)
//  {
//    clsActionContainer oResult = new clsActionContainer();
//
//    for(int i=0;i<poActionList.size();++i)
//    {                     
//      Node oActionNode = (Node)(poActionList.get(i));
//      oResult.add( clsAction.create(oActionNode) );
//    }
//    return oResult;
//  }

  public clsActionContainer clone2()
  {
    clsActionContainer oResult = new clsActionContainer();

    for(int i=0;i<size();++i)
    {
      oResult.add( get(i).clone2() );
    }
    return oResult;
  }

  public int checkActionAccomplished()
  {
    int oResult = enumTypeTrippleState.TTRIPPLE_TRUE;
    for(int i=0;i<size();++i)
    {
      int eIsAccomplished = get(i).meAccomplished;
      if( eIsAccomplished == enumTypeTrippleState.TTRIPPLE_UNDEFINED )
        return enumTypeTrippleState.TTRIPPLE_UNDEFINED;

      if( eIsAccomplished == enumTypeTrippleState.TTRIPPLE_FALSE)
      {
        oResult = enumTypeTrippleState.TTRIPPLE_FALSE;
      }
    }
    return oResult;
  }

  @Override
  protected String gettoString(Object poObject)
  {
   return ((clsAction)poObject).toString();
  }
};
