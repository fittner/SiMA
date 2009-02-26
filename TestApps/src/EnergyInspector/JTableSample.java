package EnergyInspector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.io.*;

public class JTableSample {
	JTable table;
	JTree tree;
	//final ArrayList toBeExpanded=new ArrayList();
	public JTableSample()
	{
	final JTree tree=JTreeSample.getTree();
	this.tree=tree;
	DefaultMutableTreeNode root=(DefaultMutableTreeNode)tree.getModel().getRoot();
	Enumeration enum1=root.children();
	 
	Vector tableVector=new Vector();
	int cc=0;
	while(enum1.hasMoreElements())
	{
		DefaultMutableTreeNode node=null;
		if(cc==0) node=(DefaultMutableTreeNode)tree.getModel().getRoot();
		else node=(DefaultMutableTreeNode)enum1.nextElement();
		cc++;
		Vector temp=new Vector();
		//temp.add(node);
		temp.add(new Integer(node.getDepth()));  //getMaxCap()
		temp.add(new Integer(node.getDepth()));  //getCurCap()
		tableVector.add(temp);
	}
	Vector columnNames=new Vector();
	//columnNames.add("Description");
	columnNames.add("ID");
	columnNames.add("Value");
	DefaultTableModel model=new DefaultTableModel(tableVector,columnNames)
	{
	public Class getColumnClass(int col)
	{
		Object retval= getValueAt(0, col);
		if (retval==null) return Object.class;
		return retval.getClass();
		
	}
	};
	 
	final JTable table=new JTable(model);
	table.setShowGrid(true);
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	ListSelectionModel rowSM = table.getSelectionModel();
	rowSM.addListSelectionListener(new ListSelectionListener() {
	    public void valueChanged(ListSelectionEvent e) {
	        //Ignore extra messages.
	        if (e.getValueIsAdjusting()) return;
	 
	        ListSelectionModel lsm =
	            (ListSelectionModel)e.getSource();
	        if (lsm.isSelectionEmpty()) {
	            
	        } else {
	            int selectedRow = lsm.getMinSelectionIndex();
	            tree.setSelectionRow(selectedRow);
	        }
	    }
	});
	table.setRowHeight(18);
	tree.setRowHeight(table.getRowHeight());
	tree.addTreeSelectionListener(new TreeSelectionListener() {
	    public void valueChanged(TreeSelectionEvent e) {
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)
	                           tree.getLastSelectedPathComponent();
	 
	        if (node == null) return;
	        int row=tree.getRowForPath(new TreePath(node.getPath()));
	       table.setRowSelectionInterval(row,row);
	    }
	});
	tree.addTreeWillExpandListener(new 
			TreeWillExpandListener()
			{
		public void treeWillExpand(TreeExpansionEvent e)
		{
			DefaultMutableTreeNode src=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
		//System.out.println(e.getPath());
		//System.out.println("Expanding "+src);
		int row=tree.getRowForPath(e.getPath());
		Enumeration enum1=src.children();
			
		while(enum1.hasMoreElements())
		{
			DefaultMutableTreeNode node=null;
			node=(DefaultMutableTreeNode)enum1.nextElement();
			Vector temp=new Vector();
			//temp.add(node);
			temp.add(new Integer(node.getDepth()));
			temp.add(new Integer(node.getDepth()));
			DefaultTableModel model=(DefaultTableModel)table.getModel();
			row++;
			model.insertRow(row,temp);
	 
		}
	/*	System.out.println(toBeExpanded);
		for(int i=0;i<toBeExpanded.size();i++)
		{
			FileNode nodeExp=(FileNode)toBeExpanded.get(i);
			if(nodeExp==src)return;
			tree.expandPath(new TreePath(nodeExp.getPath()));
		}
		toBeExpanded.clear();*/
		}
		public void treeWillCollapse(TreeExpansionEvent e)
		{
			
			DefaultMutableTreeNode src=(DefaultMutableTreeNode)e.getPath().getLastPathComponent();
			
			removeChilds(src);
			
		}
			}
	);
	 
	this.table=table;
	JFrame fr=new JFrame();
	JScrollPane sp=new JScrollPane(table);
	//sp.setRowHeaderView(tree);
	JPanel pan=new JPanel();
	//JLabel lab=new JLabel("Description");
	 
	
	JLabel lab = new JLabel("GroupName", SwingConstants.CENTER);
	 lab.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	 sp.setRowHeaderView(tree);

	
	
	
	lab.setAlignmentX(Component.CENTER_ALIGNMENT);
	pan.add(lab);
	sp.setCorner(JScrollPane.UPPER_LEFT_CORNER,
	        pan);
	fr.getContentPane().setBackground(Color.white);
	fr.getContentPane().add(sp);
	fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fr.pack();
	fr.setVisible(true);
	}
	 
	public void removeChilds(DefaultMutableTreeNode node)
	{
	Enumeration enum1=node.children();
	DefaultTableModel tableModel=null;
	while(enum1.hasMoreElements())
	{
		DefaultMutableTreeNode temp=(DefaultMutableTreeNode)enum1.nextElement();
	tableModel=(DefaultTableModel)table.getModel();
	//System.out.println("Processing node "+temp);
	if(tree.isExpanded(new TreePath(temp.getPath())))
	{
	tree.collapseRow(tree.getRowForPath(new TreePath(temp.getPath())));
	//toBeExpanded.add(temp);
	//System.out.println(tree.getRowCount());
	 
	}
	}
	int ccount=node.getChildCount();
	int row=tree.getRowForPath(new TreePath(node.getPath()));
	for(int i=0;i<ccount;i++)
	{
	tableModel.removeRow(row+1);	
	//System.out.println("Removing node "+(row+1));
	}
	 
	}
	public static void main(String args[])
	{
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e){e.printStackTrace();}
		new JTableSample();
	}

}
