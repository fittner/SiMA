/**
 * clsAssociationContext.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 11.08.2009, 15:04:24
 */
package pa.datatypes;


/**
 *                  moAssociationContext
 *                          |
 *                          |
 *               moWeight   |
 *    moElementA  <---------------------> moElementB
 * 
 * @author langr
 * 11.08.2009, 15:04:24
 * 
 */
public class clsAssociationContext<TYPE> extends clsAssociationWeighted<TYPE>{

	public TYPE moAssociationContext;
	
}
