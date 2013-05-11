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
import bw.entities.clsFungus;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import bw.utils.inspectors.clsInspectorUtils;


import sim.util.gui.PropertyField;

/**
 * Main Inspector for the Fungus class, add values you want to see on the tab here
 * don't forget to add values in the update function if they need to be updated too
 * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
public class clsInspectorFungus extends Inspector {

	/**
	 * Main Inspector for the Fungus, displays all values we want on this
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsFungus moFungus;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;
	private PropertyField moProp2;

	

	/**
	 * CTOR RemoteBot Inspectors, only give the Entity when it is a RemoteBot! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsFungus poFungus
	 */
	public clsInspectorFungus(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsFungus poFungus) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moFungus = poFungus;
		
		//get the default things
		clsInspectorBasic moDefaultInspector = new clsInspectorBasic(poOriginalInspector, poWrapper, poGuiState, (clsEntity)poFungus);
		add(moDefaultInspector,  BorderLayout.AFTER_LAST_LINE);
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		moProp1 = new  PropertyField("ID", moFungus.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp2 = new  PropertyField("FleshWeight", clsInspectorUtils.FormatDouble(moFungus.getFlesh().getWeight()), false, null, PropertyField.SHOW_TEXTFIELD);
		
		oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp2, BorderLayout.AFTER_LAST_LINE);
	

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
		
//		moProp1.setValue(moFungus.getId()); // TD - this value does not change during runtime
		moProp2.setValue(clsInspectorUtils.FormatDouble(moFungus.getFlesh().getWeight()));
	}

}
