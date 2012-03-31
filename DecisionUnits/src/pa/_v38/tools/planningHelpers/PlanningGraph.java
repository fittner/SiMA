/**
 * CHANGELOG
 *
 * 17.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import pa._v38.memorymgmt.datatypes.clsPlan;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 17.07.2011, 12:50:39
 * 
 */
public class PlanningGraph {

	// member to say where to start from
	public PlanningNode m_rootNode;

	public HashMap<Integer, ArrayList<PlanningNode>> m_planningResults;

	// available plans
	public ArrayList<PlanningNode> m_nodes = new ArrayList<PlanningNode>();

	// connections between plans
	public int[][] m_adjMatrix;
	int m_iSize;

	public void setStartPlanningNode(PlanningNode n) {
		m_rootNode = n;
	}

	public PlanningNode getStartPlanningNode() {
		return m_rootNode;
	}

	public void addPlanningNode(PlanningNode n) {
		m_nodes.add(n);
	}

	/**
	 * 
	 * DOCUMENT (perner) - tells the search engine which plans can be executed after each other
	 * 
	 * @since 17.07.2011 13:00:47
	 * 
	 * @param start
	 * @param end
	 */
	public void connectNode(PlanningNode start, PlanningNode end) {
		if (m_adjMatrix == null) {
			m_iSize = m_nodes.size();
			m_adjMatrix = new int[m_iSize][m_iSize];
		}

		int startIndexPlanningNode = m_nodes.indexOf(start);
		int endIndexPlanningNode = m_nodes.indexOf(end);

		m_adjMatrix[startIndexPlanningNode][endIndexPlanningNode] = 1;
		m_adjMatrix[endIndexPlanningNode][startIndexPlanningNode] = 1;
	}

	/**
	 * 
	 * DOCUMENT (perner) - returns an unvisited child
	 * 
	 * @since 17.07.2011 13:01:57
	 * 
	 * @param n
	 * @return
	 */
	private PlanningNode getUnvisitedChildNode(PlanningNode n) {

		int index = m_nodes.indexOf(n);
		int j = 0;
		while (j < m_iSize) {
			if (m_adjMatrix[index][j] == 1 && ((PlanningNode) m_nodes.get(j)).visited == false)
				return (PlanningNode) m_nodes.get(j);
			j++;
		}
		return null;
	}

	/**
	 * 
	 * DOCUMENT (perner) - breath first search on the before initialized graph-engine
	 * 
	 * @since 17.07.2011 13:02:15
	 * 
	 */
	public void breathFirstSearch() {

		System.out.println(getClass()+"========================= generating plans ... ===================");
		m_planningResults = new HashMap<Integer, ArrayList<PlanningNode>>();
		Queue<PlanningNode> q = new LinkedList<PlanningNode>();
		int iLevel = 0;

		q.add(m_rootNode);
		printPlanningNodeToSysOut(null, m_rootNode, 0);

		m_rootNode.visited = true;
		while (!q.isEmpty()) {
			PlanningNode currentNode = (PlanningNode) q.remove();
			PlanningNode childNode = null;
			iLevel++;
			while ((childNode = getUnvisitedChildNode(currentNode)) != null) {
				childNode.visited = true;

				// childNode.parent.add(parentNode);
				// parentNode.child.add(childNode);

				savePlanningNode(childNode, currentNode, iLevel);
				printPlanningNodeToSysOut(childNode, currentNode, iLevel);
				q.add(childNode);
			}
		}
	}

	/**
	 * 
	 * DOCUMENT (perner) - depth first search to iterate over graph
	 * 
	 * @since 17.07.2011 12:59:28
	 * 
	 */
	public void depthFirstSearch() {
		// stack to store plans
		Stack<PlanningNode> s = new Stack<PlanningNode>();
		s.push(m_rootNode);
		m_rootNode.visited = true;

		int iLevel = 0;
		// savePlanningNode(rootNode);
		printPlanningNodeToSysOut(null, m_rootNode, iLevel);
		while (!s.isEmpty()) {

			PlanningNode n = (PlanningNode) s.peek();
			PlanningNode child = getUnvisitedChildNode(n);
			iLevel++;
			if (child != null) {
				child.visited = true;
				// savePlanningNode(child);
				printPlanningNodeToSysOut(child, n, iLevel);
				s.push(child);
			} else {
				s.pop();
			}
		}
	}

	/**
	 * 
	 * DOCUMENT (perner) - clear available nodes
	 * 
	 * @since 17.07.2011 13:00:01
	 * 
	 */
	private void clearPlanningNodes() {
		int i = 0;
		while (i < m_iSize) {
			PlanningNode n = (PlanningNode) m_nodes.get(i);
			n.visited = false;
			n.myParent = null;
			n.hasChild = false;
			i++;
		}
	}

	/**
	 * 
	 * DOCUMENT (perner) - print nodes to sys-out for debugging
	 * 
	 * @since 17.07.2011 13:00:17
	 * 
	 * @param n
	 */
	private void printPlanningNodeToSysOut(PlanningNode predecessorNode, PlanningNode currentNode, int iLevel) {
		System.out.println(getClass() + ":: l: " + iLevel + ", predecesser plan-fragment: " + predecessorNode + ", current-plan fragment: " + currentNode);
	}

	private void savePlanningNode(PlanningNode child, PlanningNode parent, int iLevel) {

		/** prepare my parent */
		parent.hasChild = true;
		child.myParent = parent;

		// check if for this level already something exists
		if (m_planningResults.containsKey(iLevel)) {
			try {
				PlanningNode plNode = (PlanningNode) child.clone();
				m_planningResults.get(iLevel).add(plNode);

			} catch (Exception e) {
				System.out.println("ERROR, couldn't clone planning node");
			}
		} else {
			// all plans at level iLevel and new array-list if nothing was yet set at this level
			ArrayList<PlanningNode> plansAtCertainLevel = new ArrayList<PlanningNode>();
			plansAtCertainLevel.add(child);
			m_planningResults.put(iLevel, plansAtCertainLevel);
		}

	}

	/**
	 * 
	 * DOCUMENT (perner) - returns all plans which were generated in this cycle
	 * 
	 * @since 30.03.2012 16:34:29
	 * 
	 * @return
	 */
	public ArrayList<clsPlan> getPlans() {
		ArrayList<clsPlan> availablePlans = new ArrayList<clsPlan>();

		Set<Integer> myKeys = m_planningResults.keySet();

		/** iterate over planning levels */
		for (Integer key : myKeys) {
			ArrayList<PlanningNode> myPlanningNodes = m_planningResults.get(key);

			/** iterate over plans of a certain level */
			for (PlanningNode myPlanningNode : myPlanningNodes) {

				/**
				 * check if this node has a child, if not, go backward to first planning-node and save all planning nodes on the path
				 */

				if (!myPlanningNode.hasChild) {
					clsPlan myPlan = new clsPlan();

					myPlan.pushPlanFragment(myPlanningNode);
					while (myPlanningNode.myParent != null) {
						myPlanningNode = myPlanningNode.myParent;
						myPlan.pushPlanFragment(myPlanningNode);
					}

				}
			}

		}

		// clear nodes
		clearPlanningNodes();

		return availablePlans;
	}
}
