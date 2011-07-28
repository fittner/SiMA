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
import bw.entities.clsFungusEater;
import bw.entities.clsEntity;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import bw.utils.inspectors.clsInspectorUtils;


import sim.util.gui.PropertyField;

/**
 * Main Inspector for the ARSIN class, add values you want to see on the ARSIN tab here
 * don't forget to add values in the update function if they need to be updated too
 * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
public class clsInspectorFungusEater extends Inspector {

	/**
	 * Main Inspector for the ARSIN, displays all values we want on this
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsFungusEater moFungusEater;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;

	

	/**
	 * CTOR ARSIN Inspectors, only give the Entity when it is a ARSIN! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsFungusEater poFungusEater
	 */
	public clsInspectorFungusEater(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsFungusEater poFungusEater) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moFungusEater = poFungusEater;
		
		//get the default things
		clsInspectorBasic moDefaultInspector = new clsInspectorBasic(poOriginalInspector, poWrapper, poGuiState, (clsEntity)poFungusEater);
		add(moDefaultInspector,  BorderLayout.AFTER_LAST_LINE);
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		moProp1 = new  PropertyField("IntEnergyConsuptionSum", clsInspectorUtils.FormatDouble(moFungusEater.getInternalEnergyConsuptionSUM()), false, null, PropertyField.SHOW_TEXTFIELD);
		
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
		
		moProp1.setValue(clsInspectorUtils.FormatDouble(moFungusEater.getInternalEnergyConsuptionSUM()));
		
	}

}
