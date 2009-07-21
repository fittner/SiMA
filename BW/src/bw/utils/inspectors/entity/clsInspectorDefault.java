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
import bw.entities.clsEntity;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;


import sim.util.gui.PropertyField;

/**
 * Basic Inspector for the all entities, displays the minimum, default values
  * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
final class clsInspectorDefault extends Inspector {

	/**
	 * TODO (muchitsch) - insert description 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsEntity moEntity;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	
	

	/**
	 * TODO (muchitsch) - CTOR Default Inspectors, 4 all entities 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsEntity poEntity = is a Bubble
	 */
	public clsInspectorDefault(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsEntity poEntity) {
		
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moGuiState = guiState;
		moEntity = poEntity;
		
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		
		
		PropertyField oProp1 = new  PropertyField("ID", ""+moEntity.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp2 = new  PropertyField("Pose", ""+moEntity.getPose(), false, null, PropertyField.SHOW_VIEWBUTTON);
		PropertyField oProp3 = new  PropertyField("Position X", ""+moEntity.getPosition().x, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp4 = new  PropertyField("Position Y", ""+moEntity.getPosition().y, false, null, PropertyField.SHOW_TEXTFIELD);
		PropertyField oProp5 = new  PropertyField("Color", ""+moEntity.getShape().getPaint().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		
		oBox1.add(oProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp2, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp3, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp4, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(oProp5, BorderLayout.AFTER_LAST_LINE);
	
		
		
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
