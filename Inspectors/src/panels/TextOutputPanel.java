/**
 * TextOutputPanel.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.autocreated
 * 
 * @author deutsch
 * 23.04.2011, 21:56:41
 */
package panels;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 23.04.2011, 21:56:41
 * 
 */

public class TextOutputPanel extends JPanel
{
  /**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 23.04.2011, 22:07:48
	 */
	private static final long serialVersionUID = 1875416853247035582L;
// A Swing textarea for display of string info
  JTextArea textArea = null;
  
  public TextOutputPanel(String poText) {
	  create(poText);
	  
	  
  }
  public TextOutputPanel () {
	  create("");
  }
  
  private void create(String poText) {
    setLayout (new BorderLayout ());

    // Create an instance of JTextArea
    textArea = new JTextArea (poText);
    textArea.setEditable (false);

    // Add to a scroll pane so that a long list of
    // computations can be seen.
    JScrollPane areaScrollPane = new JScrollPane (textArea);
    add (areaScrollPane,"Center");
  }
  
  public void setText(String poText) {
	  textArea.setText(poText);
  }
 }
