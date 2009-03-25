/**
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.mind;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.entities.clsBubble;
import bw.mind.clsMind;
import bw.mind.ai.clsDumbMindA;
import bw.utils.sound.AePlayWave;
import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * Inspector for testing purpose to switch on/off intelligence-levels in 'AI brain'  
 * 
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 */
public class clsDumbBrainInspector extends Inspector implements ItemListener{

	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 25.03.2009, 10:36:38
	 */
	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsBubble moBubble;
	private Controller moConsole;

	//content controls
	private JLabel moCaption;
	private JCheckBox moCheckBoxCD; //collision detection
	private JCheckBox moCheckBoxCA; //collision avoidance
	    
    public clsDumbBrainInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState)
        {
    		moOriginalInspector = originalInspector;
    		moBubble= (clsBubble)((clsMobileObject2D)wrapper.getObject()).getEntity();
            final SimState state=guiState.state;
            moConsole=guiState.controller;
            
            moCaption = new JLabel("Layers of Brooks Subsumption Architecture");

            // creating the checkbox to sitch on/off the AI intelligence-levels.
            Box oBox1 = new Box(BoxLayout.X_AXIS);
            moCaption = new JLabel("Layers of Brooks Subsumption Architecture");
            oBox1.add(moCaption);
            oBox1.add(Box.createGlue());

            
            // creating the checkbox to sitch on/off the AI intelligence-levels.
            Box oBox2 = new Box(BoxLayout.X_AXIS);
            moCheckBoxCD = new JCheckBox("Roomba brain (collision detection)");
            moCheckBoxCD.setSelected(true);
            oBox2.add(moCheckBoxCD);
            oBox2.add(Box.createGlue());
            
            //Register a listener for the check boxes.
            moCheckBoxCD.addItemListener(this);
            
            
         // set up our inspector: keep the properties inspector around too
            setLayout(new BorderLayout());
            add(oBox1, BorderLayout.NORTH);
            add(oBox2, BorderLayout.AFTER_LAST_LINE);
            
            add(originalInspector, BorderLayout.CENTER);
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

		if( source == moCheckBoxCD)
		{
			
			clsMind oMind = moBubble.moAgentBody.getBrain().getMind();
			if( oMind instanceof clsDumbMindA ) {
				((clsDumbMindA)oMind).setRoombaIntelligence(moCheckBoxCD.isSelected());
			}
		}
		
		moConsole.refresh();
	}
}
