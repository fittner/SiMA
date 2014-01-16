/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
 *
 */
package mind.functionalmodel;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JFrame;


/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 11, 2012, 9:46:50 AM
 * 
 */
public class clsModuleInspectorWindow extends JFrame implements WindowListener{
	
	/** DOCUMENT (herret) - insert description; @since Sep 11, 2012 9:48:25 AM */
	private static final long serialVersionUID = 1848271242256148265L;
	
	private clsPAInspectorFunctional moCaller;

	clsModuleInspectorWindow(clsPAInspectorFunctional poCaller){
		setSize(750,550);
		moCaller = poCaller;
		addWindowListener (this);
	}
	@Override
	public void windowClosing(WindowEvent ev) {
        moCaller.childWindowClosed(this);
    }


	@Override
	public void windowActivated(WindowEvent arg0) {
		// do nothing
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// do nothing
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// do nothing
	}


	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// do nothing
	}


	@Override
	public void windowIconified(WindowEvent arg0) {
		// do nothing
	}


	@Override
	public void windowOpened(WindowEvent arg0) {
		// do nothing
	}

}
