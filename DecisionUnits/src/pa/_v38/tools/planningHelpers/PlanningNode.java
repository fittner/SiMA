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
abstract public class PlanningNode extends clsSecondaryDataStructureContainer {

	public String label;
	public boolean visited = false;
	public PlanningNode myParent;
	public boolean hasChild = false;

	// public ArrayList<PlanningNode> parent = new ArrayList<PlanningNode>();
	// public ArrayList<PlanningNode> child = new ArrayList<PlanningNode>();

	public PlanningNode(String l) {
		super("planfragment", l);
		label = l;
	}

	@Override
	public String toString() {
		return super.toString() + " hasChild:" + hasChild + ", myParent: "
				+ myParent;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {		
		PlanningNode myPlanningNode = (PlanningNode) super.clone();
		myPlanningNode.myParent = this.myParent;
		myPlanningNode.visited = this.visited;
		myPlanningNode.label = this.label;
		myPlanningNode.hasChild = this.hasChild;
		
		return myPlanningNode;
	}
} 
