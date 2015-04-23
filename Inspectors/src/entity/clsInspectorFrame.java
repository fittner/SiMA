/**
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entity;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import mind.clsInspectorTab_Modules;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.portrayal.Inspector;
import sim.portrayal.inspector.TabbedInspector;
import singeltons.clsSingletonMasonGetter;

/**
 * The InspectorFrame is responsible for the registration (in the CTOR) in the MASON-schedule to be updated
 * AND also provides the de-registration method by stopping the inspectors. The containing inspectors have to implement
 * both: the steppable AND the stoppable methode! Otherwise a class-cast-exception is thrown!
 * 
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 */
public class clsInspectorFrame extends JFrame implements Steppable, WindowListener{

	private TabbedInspector moInspectorContent; 
	private Stoppable moStoppableInspectors = null;
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 14.07.2009, 14:46:31
	 */
	private static final long serialVersionUID = 1L;

	public static clsInspectorFrame getInspectorFrame(TabbedInspector poContent, String poName) {

		//create a visible frame
		clsInspectorFrame oRetVal = new clsInspectorFrame();
		oRetVal.setInspectorContent( poContent );
		oRetVal.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		oRetVal.setName(poName);
		oRetVal.getContentPane().add(poContent, BorderLayout.CENTER);
		oRetVal.pack();
		oRetVal.setVisible(true);
		oRetVal.setTitle(poName);
		oRetVal.setSize(new Dimension(1024,768)); //set's the size of all TabbedInspector frames to this starting value
		oRetVal.addWindowListener(oRetVal);
		
		//registers this frame as steppable and stores the stoppable for deregistration when the window is closed
        synchronized(clsSingletonMasonGetter.getSimState().schedule)  // //avoid deadlocks with MASON scheduler
        {
        	oRetVal.moStoppableInspectors = clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating((Steppable)oRetVal, 10, 1);
        }
        
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

	/* updates the contained tabs by invoking the updateInspector method from each tab.
	 *
	 * @author langr
	 * 16.07.2009, 14:47:13
	 * 
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		
		Inspector oSelected = (Inspector)moInspectorContent.tabs.getSelectedComponent();
		
		for( Object oInsp : moInspectorContent.inspectors) {
			if(oInsp instanceof Inspector && oInsp == oSelected) {
				((Inspector) oInsp).updateInspector();
			}
		}
	}
	
	
	/**
	 * deregisters the steppable element from the mason scheduler
	 * this method has to be called from outside, when the window is closed
	 *
	 * @author langr
	 * 21.07.2009, 10:47:08
	 *
	 */
	public void stopInspector() {
		moStoppableInspectors.stop();
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// do nothing
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		//if inspector is closed
		for( Object oInsp : moInspectorContent.inspectors) {
			//call clsInspectorTab_Modules.close() to close the child Inspector Windows
			if(oInsp instanceof clsInspectorTab_Modules) {
				((clsInspectorTab_Modules) oInsp).close();
			}
		}
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// do nothing
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// do nothing
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// do nothing
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// do nothing
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// do nothing
		
	}
}
