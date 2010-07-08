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
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import sim.display.Controller;
import sim.display.GUIState;
//import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import simple.reactive.clsReactive;

/**
 * Inspector for testing purpose to switch on/off intelligence-levels in 'AI brain'  
 * 
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 */
public class clsReactiveInspector extends Inspector implements ItemListener{

	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 25.03.2009, 10:36:38
	 */
	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsReactive moDU;
	private Controller moConsole;

	//content controls
	private JLabel moMode;
	private JLabel moLayer;
	private JLabel moAction;  
	private JLabel moSubArchEating;
	private JLabel moSubArchHarvesting;
	private JLabel moSubArchHoming;
	private JLabel moSubArchExploring;
	
    public clsReactiveInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState,
                                clsReactive poDU)
        {
    		moOriginalInspector = originalInspector;
    		moDU= poDU;
//            final SimState state=guiState.state;
            moConsole=guiState.controller;
            
            // creating the checkbox to sitch on/off the AI intelligence-levels.
            Box oBox1 = new Box(BoxLayout.Y_AXIS);
            //moCaption = new JLabel("Layers of Brooks Subsumption Architecture");
            moMode = new JLabel();
            moLayer = new JLabel();
            moAction = new JLabel();
            
            moSubArchEating = new JLabel();
            moSubArchHarvesting = new JLabel();
            moSubArchHoming = new JLabel();
            moSubArchExploring = new JLabel();
            
            oBox1.add(moMode, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moLayer, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moAction, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moSubArchEating, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moSubArchHarvesting, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moSubArchHoming, BorderLayout.AFTER_LAST_LINE);
            oBox1.add(moSubArchExploring, BorderLayout.AFTER_LAST_LINE);
            oBox1.setBorder(BorderFactory.createTitledBorder("AI Brain Inspector"));
            oBox1.add(Box.createGlue());
                        
            
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
		moMode.setText("Mode:  " + moDU.getMode());
		moLayer.setText("Layer:  " + moDU.getLayer());
		moAction.setText("Action: " + moDU.geActionInProgress());
		moSubArchEating.setIcon(new ImageIcon("../DecisionUnitMasonInspectors/src/resources/images/SA_Eating.PNG"));
		moSubArchHarvesting.setIcon(new ImageIcon("../DecisionUnitMasonInspectors/src/resources/images/SA_Harvesting.PNG"));
		moSubArchHoming.setIcon(new ImageIcon("../DecisionUnitMasonInspectors/src/resources/images/SA_Homing_MO.PNG"));
		moSubArchExploring.setIcon(new ImageIcon("../DecisionUnitMasonInspectors/src/resources/images/SA_Exploring.PNG"));
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
			
		moConsole.refresh();
	}
}
