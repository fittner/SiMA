package EnergyInspector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class TreeTableEasyExample {
	public static void main(String[] args)
	  {
	    JFrame frame = new JFrame();

	    DefaultMutableTreeNode root = getExampleFamily();

	    TreeTableModel model = new MyTreeTableModel(root);
	    JTreeTable treeTable = new JTreeTable(model);

	    frame.getContentPane().add(new JScrollPane(treeTable));
	    frame.setSize(300, 400);
	    frame.setVisible(true);
	  }

	  private static class MyTreeTableModel extends AbstractTreeTableModel
	  {

	    public MyTreeTableModel(Object root)
	    {
	      super(root);
	    }

	    /**
	     * Error in AbstractTreeTableModel !!!
	     * Without overriding this method you can't expand the tree!
	     */
	    public Class getColumnClass(int column)
	    {
	      switch (column)
	      {
	      case 0:
	        return TreeTableModel.class;
	      default:
	        return Object.class;
	      }
	    }

	    public Object getChild(Object parent, int index)
	    {
	      assert parent instanceof MutableTreeNode;
	      MutableTreeNode treenode = (MutableTreeNode) parent;
	      return treenode.getChildAt(index);
	    }

	    public int getChildCount(Object parent)
	    {
	      assert parent instanceof MutableTreeNode;
	      MutableTreeNode treenode = (MutableTreeNode) parent;
	      return treenode.getChildCount();
	    }

	    public int getColumnCount()
	    {
	      return 2;
	    }

	    public String getColumnName(int column)
	    {
	      switch (column)
	      {
	      case 0:
	        return "Surname";
	      case 1:
	        return "Firstname";

	      default:
	        return null;
	      }

	    }

	    public Object getValueAt(Object node, int column)
	    {
	      assert node instanceof DefaultMutableTreeNode;
	      DefaultMutableTreeNode treenode = (DefaultMutableTreeNode) node;
	      Person person = (Person) treenode.getUserObject();
	      switch (column)
	      {
	      case 1:
	        return person.firstname;

	      default:
	        return null;
	      }

	    }
	  }

	  private static class Person
	  {
	    private String surname;
	    private String firstname;

	    public Person(String surname, String firstname)
	    {
	      this.surname = surname;
	      this.firstname = firstname;
	    }

	    /**
	     * @see DefaultMutableTreeNode#toString()
	     */
	    public String toString()
	    {
	      return surname;
	    }

	  }

	  private static DefaultMutableTreeNode getExampleFamily()
	  {
	    Person grandmother = new Person("Schmitz", "Sabine");
	    Person mother = new Person("Schmitz", "Christiane");
	    Person father = new Person("Schmitz", "Paul");
	    Person daughter = new Person("Schmitz", "Laura");
	    Person son = new Person("Schmitz", "Flo");
	    DefaultMutableTreeNode root = new DefaultMutableTreeNode(grandmother);
	    DefaultMutableTreeNode child1 = new DefaultMutableTreeNode(father);
	    DefaultMutableTreeNode child2 = new DefaultMutableTreeNode(mother);
	    root.add(child1);
	    root.add(child2);
	    DefaultMutableTreeNode grandchild1a = new DefaultMutableTreeNode(daughter);
	    DefaultMutableTreeNode grandchild2a = new DefaultMutableTreeNode(son);
	    DefaultMutableTreeNode grandchild1b = new DefaultMutableTreeNode(daughter);
	    DefaultMutableTreeNode grandchild2b = new DefaultMutableTreeNode(son);
	    child1.add(grandchild1a);
	    child1.add(grandchild2a);
	    child2.add(grandchild1b);
	    child2.add(grandchild2b);
	    return root;
	  }
}
