/**
 * @author langr
 * 24.06.2009, 09:52:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package inspectors.mind;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.engine.Steppable;
//import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import simple.remotecontrol.clsRemoteControl;

public class clsRemoteControlInspector extends Inspector implements ItemListener, ChangeListener, Steppable {

	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 25.03.2009, 10:36:38
	 */
	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsRemoteControl moDU;
	private Controller moConsole;

	//content controls
	private JLabel moCaption;
	private JCheckBox moLog; //collision detection
	// private JCheckBox moCheckBoxCA; //collision avoidance	// EH - make warnings free
	private JSpinner moStepsToSkip; //for tracing purpose - how many steps shall be skipped
	    
    public clsRemoteControlInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState,
                                clsRemoteControl poDU)
        {
    		moOriginalInspector = originalInspector;
    		moDU= poDU;
//            final SimState state=guiState.state;
            moConsole=guiState.controller;
            
            moCaption = new JLabel("Log-file options");

            // creating the checkbox to sitch on/off the AI intelligence-levels.
            Box oBox1 = new Box(BoxLayout.Y_AXIS);
            moCaption = new JLabel("Log-file options");
            moLog = new JCheckBox("Log into XML-logfile");
            moLog.setSelected(poDU.isLogXML());
            
            moStepsToSkip = new JSpinner();
            
            oBox1.add(moCaption, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moLog, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moStepsToSkip, BorderLayout.AFTER_LAST_LINE);
            oBox1.setBorder(BorderFactory.createTitledBorder("AI Brain Inspector"));
            oBox1.add(Box.createGlue());
            
            //Register a listener for the check boxes.
            moLog.addItemListener(this);
            moStepsToSkip.addChangeListener(this);
            
            
         // set up our inspector: keep the properties inspector around too
            setLayout(new BorderLayout());
            add(oBox1, BorderLayout.NORTH);
            add(originalInspector, BorderLayout.WEST);
        }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 09:52:36
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		moOriginalInspector.updateInspector();
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 10:10:30
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getItemSelectable();
		
		if( source == moLog)
		{
			moDU.setLogXML(moLog.isSelected());
		}
			
		moConsole.refresh();
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {

		Object source = arg0.getSource();
		
		if( source == moStepsToSkip ) {
			moDU.setStepsToSkip(Integer.parseInt(moStepsToSkip.getValue().toString()));
		}
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 16.07.2009, 14:47:13
	 * 
	 * @see sim.engine.Steppable#step(sim.engine.SimState)
	 */
	@Override
	public void step(SimState state) {
		updateInspector();
		repaint();
	}
	
}
