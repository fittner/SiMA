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

import bw.factories.clsSingletonMasonGetter;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.portrayal.Inspector;
import sim.portrayal.inspector.TabbedInspector;

/**
 * The InspectorFrame is responsible for the registration (in the CTOR) in the MASON-schedule to be updated
 * AND also provides the de-registration method by stopping the inspectors. The containing inspectors have to implement
 * both: the steppable AND the stoppable methode! Otherwise a class-cast-exception is thrown!
 * 
 * @author langr
 * 14.07.2009, 14:46:22
 * 
 */
public class clsInspectorFrame extends JFrame implements Steppable {

	private TabbedInspector moInspectorContent; 
	private Stoppable moStoppableInspectors = null;
	
	/**
	 * TODO (langr) - insert description 
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
		
		for( Object oInsp : moInspectorContent.inspectors) {
			if(oInsp instanceof Inspector) {
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
}
