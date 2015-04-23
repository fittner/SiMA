/**
 * @author zeilinger
 * 22.07.2009, 15:43:50
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package entity;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;

import physics2D.physicalObject.clsCollidingObject;
import sim.display.GUIState;
import sim.physics2D.physicalObject.PhysicalObject2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.PropertyField;
import body.itfget.itfGetSensorEngine;

import complexbody.io.sensors.datatypes.enums.eSensorExtType;
import complexbody.io.sensors.external.clsSensorVision;

import entities.abstractEntities.clsEntity;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 22.07.2009, 15:43:50
 * 
 */
public class clsInspectorSensor extends Inspector {

	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.07.2009, 15:44:47
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;
	clsEntity moHostEntity; 
	clsSensorVision moEntityVision; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 22.07.2009, 16:47:33
	 *
	 * @param originalInspector
	 * @param wrapper
	 * @param guiState
	 * @param poEntity
	 */
	public clsInspectorSensor(Inspector poOriginalInspector,
							  LocationWrapper poWrapper, 
							  GUIState poGuiState, 
					          clsEntity poEntity) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moHostEntity = poEntity;
		
		moEntityVision = (clsSensorVision)((itfGetSensorEngine)poEntity)
									.getSensorEngine().getMeRegisteredSensors().get(eSensorExtType.VISION); 
		
		//get the default things
		//clsInspectorBasic moDefaultInspector = new clsInspectorBasic(poOriginalInspector, poWrapper, poGuiState, moHostEntity);
		//add(moDefaultInspector,  BorderLayout.AFTER_LAST_LINE);
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		moProp1 = new  PropertyField("Objects in Sight","", false, null, PropertyField.SHOW_TEXTFIELD);
		oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
	

		add(oBox1, BorderLayout.AFTER_LAST_LINE);
			
	}

	public String getString(ArrayList<clsCollidingObject> ListTemp){
		String dummyString = ""; 
		
		Iterator <clsCollidingObject> itrCO = ListTemp.iterator(); 
		while(itrCO.hasNext()){
				PhysicalObject2D oCollider= itrCO.next().moCollider; 
				dummyString = dummyString + (oCollider.index+"\n");
		}
		return dummyString; 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.07.2009, 17:18:53
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		String tempString = "null";
		if(moEntityVision!=null)
			tempString = getString(moEntityVision.getSensorData());// moSensorData.getMeDetectedObject().values()); 
		moProp1.setValue(tempString);
	}
}
