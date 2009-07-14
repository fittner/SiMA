/**
 * @author langr
 * 25.03.2009, 09:52:20
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

import sim.display.Controller;
import sim.display.GUIState;
//import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import simple.dumbmind.clsDumbMindA;

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
	private clsDumbMindA moDU;
	private Controller moConsole;

	//content controls
	private JLabel moCaption;
	private JCheckBox moCheckBoxCD; //collision detection
	private JCheckBox moCheckBoxCA; //collision avoidance
	    
    public clsDumbBrainInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState,
                                clsDumbMindA poDU)
        {
    		moOriginalInspector = originalInspector;
    		moDU= poDU;
//            final SimState state=guiState.state;
            moConsole=guiState.controller;
            
            moCaption = new JLabel("Layers of Brooks Subsumption Architecture");

            // creating the checkbox to sitch on/off the AI intelligence-levels.
            Box oBox1 = new Box(BoxLayout.Y_AXIS);
            moCaption = new JLabel("Layers of Brooks Subsumption Architecture");
            moCheckBoxCD = new JCheckBox("Roomba brain (collision detection)");
            moCheckBoxCD.setSelected(poDU.isRoombaIntelligence());
            
            moCheckBoxCA = new JCheckBox("Obstacle Master Brain (collision avoidance)");
            moCheckBoxCA.setSelected(poDU.isCollisionAvoidance());
            
            oBox1.add(moCaption, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moCheckBoxCD, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moCheckBoxCA, BorderLayout.AFTER_LAST_LINE);
            oBox1.setBorder(BorderFactory.createTitledBorder("AI Brain Inspector"));
            oBox1.add(Box.createGlue());
            
            //Register a listener for the check boxes.
            moCheckBoxCD.addItemListener(this);
            moCheckBoxCA.addItemListener(this);
            
            
         // set up our inspector: keep the properties inspector around too
            setLayout(new BorderLayout());
            add(oBox1, BorderLayout.NORTH);
            //add(originalInspector, BorderLayout.CENTER);
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
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getItemSelectable();
		
			if( source == moCheckBoxCD)
			{
				moDU.setRoombaIntelligence(moCheckBoxCD.isSelected());
			}
			else if(source == moCheckBoxCA)
			{
				moDU.setCollisionAvoidance(moCheckBoxCA.isSelected());
			}
			
		moConsole.refresh();
	}
}
