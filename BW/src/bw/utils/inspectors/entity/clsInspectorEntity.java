/**
 * @author langr
 * 13.07.2009, 15:01:27
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;
import inspectors.clsInspectorMappingDecision;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import bw.body.clsBaseBody;
import bw.body.itfGetBrain;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.utils.inspectors.clsInspectorMappingEntity;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import sim.util.gui.PropertyField;
/**
 * The InspectorEntity is the main entry point for all further entity inspectors.
 * It provides common entity information (position, id, ...) and 
 * three buttons for further popup-inspector-tab-windows:
 * 
 * 1.: EntityInspectors
 * 2.: BodyInspectors
 * 3.: BrainInspectors
 * 
 * All three are of the type clsInspectorFrame. This frame contains/handles the inspectors and 
 * registers itself as a Steppable in the MASON's schedule to get updated. It then calls the 
 * containing inspects.
 * 
 * Define the inspector-content for each entity in the responsible InspectorMapping-classes:
 * clsInspectorMappingEntity (for Entity and Body)
 * clsInspectorMappingDecision (for brain related things)
 * 
 * NOTE: each customized inspector has to implement the updateInspector-function!
 * 
 * @author langr
 * 13.07.2009, 15:01:27
 * 
 */
public class clsInspectorEntity extends Inspector implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsEntity moEntity;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	ArrayList<clsInspectorFrame> moEntityWindows = new ArrayList<clsInspectorFrame>();
	
	private JButton moBtnEntityInspectors;
	private JButton moBtnBodyInspectors;
	private JButton moBtnBrainInspectors;
	
	private PropertyField moProp1;
	private PropertyField moProp2;
	private PropertyField moProp3;
	private PropertyField moProp4;
	
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 13.07.2009, 15:01:27
	 *
	 */
    public clsInspectorEntity(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsEntity poEntity)
	{
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moEntity = poEntity;
		//final SimState state=guiState.state;
		moGuiState = guiState;

		
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		moProp1 = new  PropertyField("Type", poEntity.getEntityType().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp2 = new  PropertyField("ID", ""+poEntity.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		moProp3 = new  PropertyField("Position X", ""+moEntity.getPosition().x, false, null, PropertyField.SHOW_TEXTFIELD);
		moProp4 = new  PropertyField("Position Y", ""+moEntity.getPosition().y, false, null, PropertyField.SHOW_TEXTFIELD);
		
		moBtnEntityInspectors = new JButton("Entity Details...");
		moBtnBodyInspectors = new JButton("Body Details...");
		moBtnBrainInspectors = new JButton("Brain Information...");
		
		oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp2, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp3, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp4, BorderLayout.AFTER_LAST_LINE);
		
		oBox1.add(moBtnEntityInspectors, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnBodyInspectors, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnBrainInspectors, BorderLayout.AFTER_LAST_LINE);
		
		//JToggleButton oToggle = PropertyInspector.makeInspector(moEntity.getClass(), oProp, 1, this, guiState);
	
		add(oBox1, BorderLayout.AFTER_LAST_LINE);
		
		moBtnEntityInspectors.addActionListener(this);
		moBtnBodyInspectors.addActionListener(this);
		moBtnBrainInspectors.addActionListener(this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.07.2009, 15:01:27
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		Formatter oDoubleFormatter = new Formatter();
		moProp3.setValue( oDoubleFormatter.format("%.2f", moEntity.getPosition().x).toString() );
		oDoubleFormatter = new Formatter();
		moProp4.setValue( oDoubleFormatter.format("%.2f", moEntity.getPosition().y).toString() );
		
		for (Iterator<clsInspectorFrame> it = moEntityWindows.iterator(); it.hasNext(); ) {
			clsInspectorFrame oFrame = it.next();
			if( oFrame != null) {
				//oFrame.updateContent();
				if(!oFrame.isVisible()) { 
					oFrame.stopInspector(); //remove the inspector-steppables from the schedule 
					it.remove();//remove the window that should be updated from the list when it is invisible (=closed)
				}
			}
			else { //remove the window that should be updated from the list when it null (should not happen)
				it.remove();
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.07.2009, 17:44:10
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		
		if( source == moBtnEntityInspectors) {
			//define the inspector-content for each entity in the responsible InspectorMapping-class
			TabbedInspector oMasonInspector = clsInspectorMappingEntity.getInspectorEntity(moOriginalInspector, moWrapper, moGuiState, moEntity);
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, moEntity.getId()  + " - " +  moEntity.getEntityType().toString() + " - Entity Inspector") );
		}
		else if( source == moBtnBodyInspectors ) {
			clsBaseBody iBody = ((itfGetBody)moEntity).getBody();
			//define the inspector-content for each entity in the responsible InspectorMapping-class
			TabbedInspector oMasonInspector = clsInspectorMappingEntity.getInspectorBody(moOriginalInspector, moWrapper, moGuiState, iBody);
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, moEntity.getId()  + " - " +  moEntity.getEntityType().toString() + " - Body Inspector") );
		}
		else if( source == moBtnBrainInspectors) {
			TabbedInspector oMasonInspector = new TabbedInspector();
			//define the inspector-content for each entity in the responsible InspectorMapping-class
			oMasonInspector.addInspector( clsInspectorMappingDecision.getInspector(moOriginalInspector, moWrapper, moGuiState, ((itfGetBrain)((itfGetBody)moEntity).getBody()).getBrain().getDecisionUnit()), "Brain Insp.");
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, moEntity.getId() + " - " + moEntity.getEntityType().toString() + " - Brain Inspector") );
		}
	}
}
