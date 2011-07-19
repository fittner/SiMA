/**
 * CHANGELOG
 *
 * 17.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 17.07.2011, 12:36:28
 * 
 */
abstract public class PlanningNode {

	public String label;
	public boolean visited = false;

	public PlanningNode(String l) {
		this.label = l;
	}
}
