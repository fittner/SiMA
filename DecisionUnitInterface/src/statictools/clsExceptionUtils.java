/**
 * clsExeptionUtils.java: BW - bw.exceptions
 * 
 * @author muchitsch
 * 05.04.2011, 16:19:01
 */
package statictools;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 05.04.2011, 16:19:01
 * 
 */
public class clsExceptionUtils {
	
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
