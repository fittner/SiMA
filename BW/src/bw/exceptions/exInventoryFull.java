/**
 * @author Benny Dönz
 * 03.07.2009, 20:07:51
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.exceptions;

/**
 * TODO (Benny Dönz) - insert description 
 * 
 * @author Benny Dönz
 * 03.07.2009, 20:07:51
 * 
 */
public class exInventoryFull extends exException {

	public String toString() {
		return ("Inventory full - cannot add another item");
	}

	private static final long serialVersionUID = -1735591814368561268L;

}
