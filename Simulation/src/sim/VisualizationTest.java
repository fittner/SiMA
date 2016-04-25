/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package sim;

import java.util.Random;

import base.datatypes.clsDecisionTreeData;
import base.datatypes.clsTreemapData;
import base.datatypes.interfaces.itfMapTreeNode;
import base.tools.SimaJSONHandler;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 13:59:03
 * 
 */
public class VisualizationTest {

	protected static void setup(clsTreemapData tree) {
		itfMapTreeNode root = tree.createNode(null, "root", 1.0, 0.0, "#000000");
		itfMapTreeNode n1 = root.addChild(tree.createNode(root, "1.1", 1.0, 1.1, "#000001"));
		itfMapTreeNode n2 = root.addChild(tree.createNode(root, "1.2", 1.0, 1.2, "#000001"));
		itfMapTreeNode n3 = root.addChild(tree.createNode(root, "1.3", 1.0, 1.3, "#000001"));
		
		itfMapTreeNode n1_1 = n1.addChild(tree.createNode(n1, "1.1.1", 1.0, 1.1, "#000001"));
		itfMapTreeNode n1_2 = n1.addChild(tree.createNode(n1, "1.1.2", 1.0, 1.2, "#000001"));
		itfMapTreeNode n1_3 = n1.addChild(tree.createNode(n1, "1.1.3", 1.0, 1.3, "#000001"));
		
		itfMapTreeNode n2_1 = n2.addChild(tree.createNode(n2, "1.2.1", 1.0, 2.1, "#000001"));
		itfMapTreeNode n2_2 = n2.addChild(tree.createNode(n2, "1.2.2", 1.0, 2.2, "#000001"));
		
		itfMapTreeNode n2_1_1 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.1", 1.0, 1.1, "#000001"));
		itfMapTreeNode n2_1_2 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.2", 1.0, 1.2, "#000001"));
		itfMapTreeNode n2_1_3 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.3", 1.0, 1.3, "#000001"));
		
		itfMapTreeNode n2_2_1 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.1", 1.0, 2.1, "#000001"));
		itfMapTreeNode n2_2_2 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.2", 1.0, 2.2, "#000001"));
		itfMapTreeNode n2_2_3 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.3", 1.0, 2.3, "#000001"));
		
		tree.setRoot(root);
	}

	protected static void printNode(itfMapTreeNode node) {
		System.out.print("Node: " + node.getData("id") + " has children: ");
		for(itfMapTreeNode child : node.getChildren()) {
			System.out.print(child.getData("id"));
			System.out.print(", ");
		}
		System.out.println(" |");
	}
	
	protected static void printTree(itfMapTreeNode node) {
		printNode(node);
		for(itfMapTreeNode child : node.getChildren()) {
			printTree(child);
		}
	}
	
	protected static void setup(clsDecisionTreeData tree) {
		Random rand = new Random();
		int level = 0;
		
		itfMapTreeNode root = tree.createNode(null, "root", rand.nextInt(20), level);
		level++;
		itfMapTreeNode n1 = root.addChild(tree.createNode(root, "1.1", rand.nextInt(20), level));
		itfMapTreeNode n2 = root.addChild(tree.createNode(root, "1.2", rand.nextInt(20), level));
		itfMapTreeNode n3 = root.addChild(tree.createNode(root, "1.3", rand.nextInt(20), level));
		
		level++;
		itfMapTreeNode n1_1 = n1.addChild(tree.createNode(n1, "1.1.1", rand.nextInt(20), level));
		itfMapTreeNode n1_2 = n1.addChild(tree.createNode(n1, "1.1.2", rand.nextInt(20), level));
		itfMapTreeNode n1_3 = n1.addChild(tree.createNode(n1, "1.1.3", rand.nextInt(20), level));
		
		itfMapTreeNode n2_1 = n2.addChild(tree.createNode(n2, "1.2.1", rand.nextInt(20), level));
		itfMapTreeNode n2_2 = n2.addChild(tree.createNode(n2, "1.2.2", rand.nextInt(20), level));
		
		level++;
		itfMapTreeNode n2_1_1 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.1", rand.nextInt(20), level));
		itfMapTreeNode n2_1_2 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.2", rand.nextInt(20), level));
		itfMapTreeNode n2_1_3 = n2_1.addChild(tree.createNode(n2_1, "1.2.1.3", rand.nextInt(20), level));
		
		itfMapTreeNode n2_2_1 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.1", rand.nextInt(20), level));
		itfMapTreeNode n2_2_2 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.2", rand.nextInt(20), level));
		itfMapTreeNode n2_2_3 = n2_2.addChild(tree.createNode(n2_2, "1.2.2.3", rand.nextInt(20), level));
		
		tree.setRoot(root);
	}
	
	/**
	 * DOCUMENT (Kollmann) - insert description
	 *
	 * @since 20.01.2016 13:59:03
	 *
	 * @param args
	 */
	public static void main(String[] args) {
//		clsTreemapData tree = new clsTreemapData();
		
//		setup(tree);
		
		clsDecisionTreeData tree = new clsDecisionTreeData();
		
		setup(tree);
		
		SimaJSONHandler handler = new SimaJSONHandler();
		
		handler.writeData(tree.getRoot());
		
		//go through the tree
		printTree(tree.getRoot());
		
		System.out.println("done");
	}

}
