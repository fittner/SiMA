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
import javax.swing.JPanel;

/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 */
public class clsInspectorFrame extends JFrame {

	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 14.07.2009, 14:46:31
	 */
	private static final long serialVersionUID = 1L;

	public static clsInspectorFrame getInspectorFrame(JPanel poContent, String poName) {
		
		clsInspectorFrame oRetVal = new clsInspectorFrame();
		oRetVal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oRetVal.setName(poName);
		oRetVal.getContentPane().add(poContent, BorderLayout.CENTER);
		oRetVal.pack();
		oRetVal.setVisible(true);
		return oRetVal;
		
	}
	
}
