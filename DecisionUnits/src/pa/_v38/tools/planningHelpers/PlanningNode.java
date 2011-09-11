/**
 * CHANGELOG
 *
 * 17.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 17.07.2011, 12:36:28
 * 
 */
abstract public class PlanningNode extends clsSecondaryDataStructureContainer{

	public String label;
	public boolean visited = false;

	public PlanningNode(String l) {
		super("planfragment", l);
		label = l;
	}
}
