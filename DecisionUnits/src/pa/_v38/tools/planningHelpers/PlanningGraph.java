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
import java.util.Stack;


/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author perner
 * 17.07.2011, 12:50:39
 * 
 */
public class PlanningGraph {

		// member to say where to start from
	 	public PlanningNode m_rootNode;
	 	
	 	public HashMap<PlanningNode, HashMap<Integer, PlanningNode>> m_planningResults;
	 	
	 	// available plans
	      public ArrayList <PlanningNode> m_nodes=new ArrayList<PlanningNode>();
	      
	      // connections between plans
	      public int[][] m_adjMatrix;
	      int m_iSize;
	      
	      
	      public void setStartPlanningNode(PlanningNode n) {
	          m_rootNode=n;
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
	      public void connectNode(PlanningNode start,PlanningNode end) {
	          if(m_adjMatrix==null) {
	              m_iSize=m_nodes.size();
	              m_adjMatrix=new int[m_iSize][m_iSize];
	          }
	  
	          int startIndexPlanningNode = m_nodes.indexOf(start);
	          int endIndexPlanningNode   = m_nodes.indexOf(end);
	          
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
	          
	          int index=m_nodes.indexOf(n);
	          int j=0;
	          while(j<m_iSize) {
	              if(m_adjMatrix[index][j]==1 && ((PlanningNode)m_nodes.get(j)).visited==false)
	                  return (PlanningNode)m_nodes.get(j);
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
	    	  
	    	  m_planningResults  = new HashMap<PlanningNode, HashMap<Integer, PlanningNode>>(); 
	          Queue<PlanningNode> q = new LinkedList<PlanningNode>();
	          int iLevel = 0;
	          
	          q.add(m_rootNode);
	          printPlanningNodeToSysOut(m_rootNode);
	          
	          m_rootNode.visited=true;
	          while(!q.isEmpty()){
	        	  PlanningNode n=(PlanningNode)q.remove();
	        	  PlanningNode child=null;
	        	  iLevel++;
	              while((child=getUnvisitedChildNode(n))!=null) {
	                  child.visited=true;
	                  
	                  savePlanningNode(child, iLevel);
	                  System.out.println("planning searchlevel: " + iLevel);
	                  printPlanningNodeToSysOut(child);
	                  q.add(child);
	              }
	          }
	          //Clear visited property of nodes
	          clearPlanningNodes();
	      }
	      
	      /**
	       * 
	       * DOCUMENT (perner) - depth first search to iterate over graph
	       *
	       * @since 17.07.2011 12:59:28
	       *
	       */
	      public void depthFirstSearch() {
	          //stack to store plans
	          Stack<PlanningNode> s = new Stack<PlanningNode>();
	          s.push(m_rootNode);
	          m_rootNode.visited=true;
	          
	          //savePlanningNode(rootNode);
	          printPlanningNodeToSysOut(m_rootNode);
	          while(!s.isEmpty())
	          {
	        	  PlanningNode n=(PlanningNode)s.peek();
	        	  PlanningNode child=getUnvisitedChildNode(n);
	              if(child!=null)
	              {
	                  child.visited=true;
	                  //savePlanningNode(child);
	                  printPlanningNodeToSysOut(child);
	                  s.push(child);
	              }
	              else {
	                  s.pop();
	              }
	          }
	          // clear nodes
	          clearPlanningNodes();
	      }
	      
	      /**
	       * 
	       * DOCUMENT (perner) - clear available nodes
	       *
	       * @since 17.07.2011 13:00:01
	       *
	       */
	      private void clearPlanningNodes() {
	          int i=0;
	          while(i<m_iSize)
	          {
	        	  PlanningNode n=(PlanningNode)m_nodes.get(i);
	              n.visited=false;
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
	      private void printPlanningNodeToSysOut(PlanningNode n) {
	          System.out.println(n.label+" ");
	      }	
	      
	      private void savePlanningNode(PlanningNode n, int iLevel) {
	    	
	    	//  m_planningResults.put(arg0, arg1)
	      }
}
