/**
 * EmptyListException.java: DecisionUnits - pa._v38.memorymgmt.exceptions
 * 
 * @author zeilinger
 * 14.08.2010, 12:39:26
 */
package pa._v38.memorymgmt.old;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 14.08.2010, 12:39:26
 * 
 * @deprecated
 */
public class EmptyListException extends Exception{

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 14.08.2010, 12:39:55
	 */
	private static final long serialVersionUID = -2557748357217057220L;
	
	public EmptyListException(String oMessage){
		super(oMessage); 
	}
}
