/**
 * clsCompareOperator.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:32:59
 */
package pa.loader.templateimage;

import java.awt.Color;

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
	     else if ( compareOperator.equals("&gt;=") )
	     {
	        moCompareOperator = ">=";
	     }
	     else if ( compareOperator.equals("&lt;") )
	     {
	        moCompareOperator = "<";
	     }
	     else if ( compareOperator.equals("&gt;") )
	     {
	        moCompareOperator = ">";
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
				oRetVal = compare( (Integer)a, (Integer)b );
			}
			if(a instanceof Enum) {
				oRetVal = compare( ((Enum)a).ordinal(), ((Enum)b).ordinal());
			}
			else if(a instanceof Double) {
				oRetVal = compare( (Double)a, (Double)b );
			}
			else if(a instanceof String) {
				oRetVal = compare( (String)a, (String)b );
			}
			else if(a instanceof Boolean && b instanceof Boolean) {
				oRetVal = compare( (Boolean)a, (Boolean)b );
			}
			else {
				System.out.println("Error in Template Compare Operator\n -- Cannot compare " 
						+ a.getClass().getName() + " with " + b.getClass().getName() + " - not Supported!");
			}
			
		}
		else {
			if(a instanceof Boolean && b instanceof eTrippleState) {
				oRetVal = compare( (Boolean)a, (eTrippleState)b );
			}
			else if(a instanceof eTrippleState && b instanceof Boolean ) {
				oRetVal = compare( (Boolean)b, (eTrippleState)a );
			}
			else if(a instanceof String && b instanceof Color ) {
				oRetVal = compare( (String)a, "#"+Integer.toString(((Color)b).getRGB() & 0xffffff, 16).toUpperCase() );
			}
			else if(a instanceof String && b instanceof Boolean ) {
				oRetVal = compare( Boolean.parseBoolean(a.toString()), b );
			}
			else if(a instanceof String && b instanceof Enum) {
				oRetVal = compare( a, ((Enum)b).name());
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
	  
	  public boolean compare(Boolean a, Boolean b) {
		  return compare(a.booleanValue(), b.booleanValue());
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
		      if( a.equals(b) )
		      {
		    	  oRetVal = true;
		      }
		    }
		    else if( moCompareOperator.equals("!=") )
		    {
		      if( !a.equals(b) )
		      {
		    	  oRetVal = true;
		      }
		    }
		  
		  return oRetVal;
	  }
}