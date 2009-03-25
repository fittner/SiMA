/**
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.mind;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import bw.entities.clsBubble;
import bw.mind.clsMind;
import bw.mind.ai.clsDumbMindA;
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
	private JCheckBox moCheckBox;
	private clsBubble moBubble;
    
    public clsDumbBrainInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState)
        {
    		moOriginalInspector = originalInspector;
    		
    		moBubble=(clsBubble) wrapper.getObject();
            final SimState state=guiState.state;
            final Controller console=guiState.controller;
            
            // now let's add a Button
            Box oBox = new Box(BoxLayout.X_AXIS);
            moCheckBox = new JCheckBox("Roomba brain");
            moCheckBox.setSelected(true);
            oBox.add(moCheckBox);
            oBox.add(Box.createGlue());
            
            //Register a listener for the check boxes.
            moCheckBox.addItemListener(this);
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

		if( source == moCheckBox)
		{
			
			clsMind oMind = moBubble.moAgentBody.getBrain().getMind();
			if( oMind instanceof clsDumbMindA ) {
				((clsDumbMindA)oMind).setRoombaIntelligence(moCheckBox.isSelected());
			}
		}
	}
}
