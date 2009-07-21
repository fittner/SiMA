/**
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import bw.entities.clsBubble;
//import bw.entities.clsEntity;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;


import sim.util.gui.PropertyField;

/**
 * Basic Inspector for the Bubble class, add values you want to see on the bubble tab here
 * don't forget to add values in the update function if they need to be setted too
 * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
public class clsInspectorBubble extends Inspector {

	/**
	 * TODO (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsBubble moBubble;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	
	

	/**
	 * TODO (muchitsch) - CTOR Bubble Inspectors, only give the Entity when it is a Bubble! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsEntity poEntity = is a Bubble
	 */
	public clsInspectorBubble(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBubble poBubble) {
		
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moGuiState = guiState;
		moBubble = poBubble;
		
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		
		
		PropertyField oProp1 = new  PropertyField("ID", ""+moBubble.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp2 = new  PropertyField("Type", ""+moBubble.getInternalEnergyConsumption(), false, null, PropertyField.SHOW_VIEWBUTTON);
		PropertyField oProp3 = new  PropertyField("Position X", ""+moBubble.getPosition().x, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp4 = new  PropertyField("Position Y", ""+moBubble.getPosition().y, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp5 = new  PropertyField("Color", ""+moBubble.getShape().getPaint().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		PropertyField oProp6 = new  PropertyField("IntEnergyConsuption", ""+moBubble.getInternalEnergyConsuptionSUM(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		oBox1.add(oProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp2, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp3, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp4, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp5, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp6, BorderLayout.AFTER_LAST_LINE);
		
		//clsInspectorDefault defaultInspector = new clsInspectorDefault(moWrapper, moGuiState, (clsEntity)moBubble);
		add(oBox1, BorderLayout.AFTER_LAST_LINE);
		
	}

	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * Jul 15, 2009, 1:20:23 PM
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		
		//TODO update me values
		
		// TODO Auto-generated method stub
		
	}

}
