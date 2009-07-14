/**
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import sim.portrayal.inspector.TabbedInspector;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 */
public class clsInspectorFrame extends JFrame {

	private TabbedInspector moInspectorContent; 
	
	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 14.07.2009, 14:46:31
	 */
	private static final long serialVersionUID = 1L;

	public static clsInspectorFrame getInspectorFrame(TabbedInspector poContent, String poName) {

		clsInspectorFrame oRetVal = new clsInspectorFrame();
		oRetVal.setInspectorContent( poContent );
		oRetVal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oRetVal.setName(poName);
		oRetVal.getContentPane().add(poContent, BorderLayout.CENTER);
		oRetVal.pack();
		oRetVal.setVisible(true);
		return oRetVal;
	}

	/**
	 * @author langr
	 * 14.07.2009, 18:29:01
	 * 
	 * @param moInspectorContent the moInspectorContent to set
	 */
	public void setInspectorContent(TabbedInspector moInspectorContent) {
		this.moInspectorContent = moInspectorContent;
	}

	/**
	 * @author langr
	 * 14.07.2009, 18:29:01
	 * 
	 * @return the moInspectorContent
	 */
	public TabbedInspector getInspectorContent() {
		return moInspectorContent;
	}
	
}
