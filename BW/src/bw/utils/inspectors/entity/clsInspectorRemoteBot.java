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
import bw.entities.clsRemoteBot;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.PropertyField;

/**
 * Main Inspector for the RemoteBot class, add values you want to see on the ARSIN tab here
 * don't forget to add values in the update function if they need to be updated too
 * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
public class clsInspectorRemoteBot extends Inspector {

	/**
	 * Main Inspector for the RemoteBot, displays all values we want on this
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsRemoteBot moRemoteBot;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;

	

	/**
	 * CTOR RemoteBot Inspectors, only give the Entity when it is a RemoteBot! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsRemoteBot poRemoteBot
	 */
	public clsInspectorRemoteBot(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsRemoteBot poRemoteBot) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moRemoteBot = poRemoteBot;
		
		//get the default things
		clsInspectorBasic moDefaultInspector = new clsInspectorBasic(poOriginalInspector, poWrapper, poGuiState, (clsEntity)poRemoteBot);
		add(moDefaultInspector,  BorderLayout.AFTER_LAST_LINE);
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		moProp1 = new  PropertyField("ID", moRemoteBot.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
	

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
	}

}
