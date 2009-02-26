package EnergyInspector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
public class TreeExample {
	public static void main(String args[]) {
		  JFrame frame = new JFrame("Tree");
		  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  JTree tree = new JTree();
		  JScrollPane pane = new JScrollPane(tree);
		  frame.getContentPane().add(pane);
		  frame.setSize(250, 250);
		  frame.setVisible(true);
		 }

}
