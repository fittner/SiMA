/**
 * clsCompareOperator.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:32:59
 */
package pa.loader.templateimage;

import bfg.utils.enums.eTrippleState;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:32:59
 * 
 */
public class clsCompareOperator {
	private String moCompareOperator = new String("NOT_DEFINED"); //default is type NOT_DEFINED

	  //---------------------------------------------------------------------------
	  public clsCompareOperator() 
	  //---------------------------------------------------------------------------
	  {
	    moCompareOperator = "NOT_DEFINED";
	  }

	  //---------------------------------------------------------------------------
	  public clsCompareOperator(String compareOperator) 
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
	  @SuppressWarnings("unchecked")
	public boolean compare( Object a, Object b ) {
	  //---------------------------------------------------------------------------
		boolean oRetVal = false;

		if( a.getClass().getName().equals( b.getClass().getName()) ) { //same instances
			
			if(a instanceof Integer) {
				compare( (Integer)a, (Integer)b );
			}
			if(a instanceof Enum) {
				compare( ((Enum)a).ordinal(), ((Enum)b).ordinal());
			}
			else if(a instanceof Double) {
				compare( (Double)a, (Double)b );
			}
			else if(a instanceof String) {
				compare( (Double)a, (Double)b );
			}
			else if(a instanceof Boolean && b instanceof Boolean) {
				compare( (Boolean)a, (Boolean)b );
			}
			else if(a instanceof Boolean && b instanceof eTrippleState) {
				compare( (Boolean)a, (eTrippleState)b );
			}
			else if(a instanceof eTrippleState && b instanceof Boolean ) {
				compare( (Boolean)b, (eTrippleState)a );
			}
			else {
				System.out.println("Error in Template Compare Operator\n -- Cannot compare " 
						+ a.getClass().getName() + " with " + b.getClass().getName() + " - not Supported!");
			}
			
		}
		  
		return oRetVal;
	  }
	  
	  //---------------------------------------------------------------------------
	  public boolean compare( int a, int b )
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
	  public boolean compare( double a, double b )
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
	  
	  public boolean compare(boolean a, boolean b) {
			
		  boolean oRetVal = false;

			if( moCompareOperator.equals("==") )
		    {
				if( a == b ) {
					oRetVal = true;
				}
		    }
		    else if( moCompareOperator.equals("!=") )
		    {
				if( a != b ) {
					oRetVal = true;
				}    	
		    }

		  return oRetVal;
	  }
	  
	  public boolean compare(boolean pnBool, eTrippleState peTrip) {
		
		  boolean oRetVal = false;
		  
		  if(peTrip != eTrippleState.UNDEFINED){
			if( moCompareOperator.equals("==") )
		    {
				if( (peTrip == eTrippleState.FALSE && !pnBool) ||
					(peTrip == eTrippleState.TRUE  &&  pnBool) ) {
					oRetVal = true;
				}
		    }
		    else if( moCompareOperator.equals("!=") )
		    {
				if( (peTrip == eTrippleState.FALSE &&  pnBool) ||
					(peTrip == eTrippleState.TRUE  && !pnBool) ) {
					oRetVal = true;
				}		    	
		    }
		  }
		  return oRetVal;
	  }
	  
	  public boolean compare(String a, String b) {
		  
		  boolean oRetVal = false;
		  
		  if( moCompareOperator.equals("==") )
		    {
		      if( a == b )
		      {
		    	  oRetVal = true;
		      }
		    }
		    else if( moCompareOperator.equals("!=") )
		    {
		      if( a != b )
		      {
		    	  oRetVal = true;
		      }
		    }
		  
		  return oRetVal;
	  }
}