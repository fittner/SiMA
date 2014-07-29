/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 *  taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeCompareOperator.java
// May 10, 2006
//

// Belongs to package
package physical2d.physicalObject.datatypes;

import physical2d.physicalObject.datatypes.enumClass;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 * @deprecated
 */
public class clsTypeCompareOperator extends enumClass {

  private String moCompareOperator = new String("NOT_DEFINED"); //default is type NOT_DEFINED

  //---------------------------------------------------------------------------
  public clsTypeCompareOperator() 
  //---------------------------------------------------------------------------
  {
    moCompareOperator = "NOT_DEFINED";
  }

  //---------------------------------------------------------------------------
  public clsTypeCompareOperator(String compareOperator) 
  //---------------------------------------------------------------------------
  {
    if( compareOperator.equals("==") ||
        compareOperator.equals("!=") ||
        compareOperator.equals(">=") ||
        compareOperator.equals("<=") ||
        compareOperator.equals(">")  ||
        compareOperator.equals("<") )
     {
        moCompareOperator = compareOperator;
     }
     else if ( compareOperator.equals("&lt;=") )
     {
        moCompareOperator = "<=";
     }
     else if ( compareOperator.equals("&lt;") )
     {
        moCompareOperator = ">=";
     }
     else
     {
        //throw exception();?
        moCompareOperator = "NOT_DEFINED";
     }
  }

  //---------------------------------------------------------------------------
  public String getCompareOperator()
  //---------------------------------------------------------------------------
  {
    return moCompareOperator;
  }

  //---------------------------------------------------------------------------
  public boolean compareInteger( int a, int b )
  //---------------------------------------------------------------------------
  {
    boolean retVal = false;
    if( moCompareOperator.equals("==") )
    {
      if( a == b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("!=") )
    {
      if( a != b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals(">=") )
    {
      if( a >= b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("<=") )
    {
      if( a <= b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals(">") )
    {
      if( a > b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("<") )
    {
      if( a < b )
      {
        retVal = true;
      }
    }
    return retVal;
  }

  //---------------------------------------------------------------------------
  public boolean compareDouble( double a, double b )
  //---------------------------------------------------------------------------
  {
    boolean retVal = false;
    if( moCompareOperator.equals("==") )
    {
      if( a == b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("!=") )
    {
      if( a != b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals(">=") )
    {
      if( a >= b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("<=") )
    {
      if( a <= b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals(">") )
    {
      if( a > b )
      {
        retVal = true;
      }
    }
    else if( moCompareOperator.equals("<") )
    {
      if( a < b )
      {
        retVal = true;
      }
    }
    return retVal;
  }
  
  //---------------------------------------------------------------------------
  public boolean compare( float a, int b, String poType )
  //---------------------------------------------------------------------------
  {
    boolean retVal = false;

//    if( poType.toLowerCase().equals("complexemotion") )
//    {
//      int poA = (int)(a*100);
//      int poB = (int)( (100f/enumTypeLevelComplexEmotion.TLEVELCEMOTION_VERYHIGH)*b );
//      retVal = compareInteger( poA, poB );
//    }

    return retVal;
  }
  
  public boolean compare(boolean pnBool, eTrippleState peTrip) {
	
	  boolean oRetVal = false;
	  
	  if(peTrip != eTrippleState.UNDEFINED){
		if( (peTrip == eTrippleState.FALSE && !pnBool) ||
			(peTrip == eTrippleState.TRUE  &&  pnBool) ) {
			oRetVal = true;
		}
	  }
	  return oRetVal;
  }
};
