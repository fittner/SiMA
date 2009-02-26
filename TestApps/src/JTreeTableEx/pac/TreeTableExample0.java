package JTreeTableEx.pac;

import com.sun.java.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class TreeTableExample0 {
	public static void main(String[] args) {
		new TreeTableExample0();
	    }

	    public TreeTableExample0() {
		JFrame frame = new JFrame("TreeTable");
		JTreeTable treeTable = new JTreeTable(new FileSystemModel());

		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent we) {
			System.exit(0);
		    }
		});

		frame.getContentPane().add(new JScrollPane(treeTable));
		frame.pack();
		frame.show();
	    }
}
