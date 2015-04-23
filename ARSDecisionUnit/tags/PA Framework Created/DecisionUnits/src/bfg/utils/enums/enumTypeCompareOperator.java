/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME (deutsch) - keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeCompareOperator.java
// May 10, 2006
//

// Belongs to package
package bfg.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumTypeCompareOperator extends enumClass {

  private String moCompareOperator = new String("NOT_DEFINED"); //default is type NOT_DEFINED

  //---------------------------------------------------------------------------
  public enumTypeCompareOperator() 
  //---------------------------------------------------------------------------
  {
    moCompareOperator = "NOT_DEFINED";
  }

  //---------------------------------------------------------------------------
  public enumTypeCompareOperator(String compareOperator) 
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
  public boolean compare( float a, int b, String poType )
  //---------------------------------------------------------------------------
  {
    boolean retVal = false;

    if( poType.toLowerCase().equals("complexemotion") )
    {
      int poA = (int)(a*100);
      int poB = (int)( (100f/enumTypeLevelComplexEmotion.TLEVELCEMOTION_VERYHIGH)*b );
      retVal = compareInteger( poA, poB );
    }

    return retVal;
  }
};
