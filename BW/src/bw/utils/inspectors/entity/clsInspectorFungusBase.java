/**
 * @author horvath
 * Jul 22, 2009, 2:08:00 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import bw.entities.clsBase;
//import bw.entities.clsEntity;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;


import sim.util.gui.PropertyField;

/**
 * Basic Inspector for the Base class, add values you want to see on the base tab here
 * don't forget to add values in the update function if they need to be setted too
 * 
 * @author horvath
 * Jul 22, 2009, 2:08:00 PM
 * 
 */
public class clsInspectorFungusBase extends Inspector {

	/**
	 * DOCUMENT (horvath) - insert description 
	 * 
	 * @author horvath
	 * Jul 22, 2009, 2:08:00 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsBase moBase;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp2;
	

	/**
	 * TODO (horvath) - CTOR Bubble Inspectors, only give the Entity when it is a Base! 
	 * 
	 * @author horvath
	 * Jul 22, 2009, 2:08:00 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsEntity poEntity = is a Bubble
	 */
	public clsInspectorFungusBase(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsBase poBase) {
		
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moGuiState = guiState;
		moBase = poBase;
		
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		
		
		PropertyField oProp1 = new  PropertyField("ID", ""+moBase.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp2 = new  PropertyField("Stored ore", ""+moBase.getMnStoredOre(), false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp3 = new  PropertyField("Position X", ""+moBase.getPosition().x, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp4 = new  PropertyField("Position Y", ""+moBase.getPosition().y, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp5 = new  PropertyField("Color", ""+moBase.getShape().getPaint().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
			
		oBox1.add(oProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp2, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp3, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp4, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp5, BorderLayout.AFTER_LAST_LINE);
		
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
		
		// TODO (muchitsch) - Auto-generated method stub
		
		moProp2.setValue(""+moBase.getMnStoredOre());
	}

}
