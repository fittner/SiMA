// File enumTypeCompareOperator.java
// May 10, 2006
//

// Belongs to package
package bw.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
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
