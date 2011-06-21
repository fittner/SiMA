/**
 * EmptyListException.java: DecisionUnits - pa._v19.memorymgmt.exceptions
 * 
 * @author zeilinger
 * 14.08.2010, 12:39:26
 */
package pa._v19.memorymgmt.exceptions;

/**
 *
 * 
 * @author zeilinger
 * 14.08.2010, 12:39:26
 * 
 */
public class EmptyListException extends Exception{

	/**
	 *
	 * 
	 * @author zeilinger
	 * 14.08.2010, 12:39:55
	 */
	private static final long serialVersionUID = -2557748357217057220L;
	
	public EmptyListException(String oMessage){
		super(oMessage); 
	}
}
