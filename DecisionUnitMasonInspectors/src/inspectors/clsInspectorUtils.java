/**
 * clsInspectorUtils.java: DecisionUnitMasonInspectors - inspectors
 * 
 * @author muchitsch
 * 05.04.2011, 15:53:49
 */
package inspectors;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 05.04.2011, 15:53:49
 * 
 */
public class clsInspectorUtils {
	
	 public static String getCustomStackTrace(Throwable aThrowable) {
	        //add the class name and any message passed to constructor
	        final StringBuilder result = new StringBuilder( "ARS-EX: " );
	        result.append(aThrowable.toString());
	        final String NEW_LINE = System.getProperty("line.separator");
	        result.append(NEW_LINE);

	        //add each element of the stack trace
	        for (StackTraceElement element : aThrowable.getStackTrace() ){
	          result.append( element );
	          result.append( NEW_LINE );
	        }
	        return result.toString();
	      }

}
