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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;

//import decisionunit.clsBaseDecisionUnit;

//import pa._v38.clsProcessor;
//import pa._v38.modules.clsPsychicApparatus;

import bw.ARSIN.clsARSIN;
import bw.body.clsComplexBody;
import bw.body.itfget.itfGetBody;
import bw.entities.base.clsEntity;
import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import bw.utils.inspectors.body.clsInspectorBodyOverview;


import sim.util.gui.PropertyField;

/**
 * Main Inspector for the ARSIN class, add values you want to see on the ARSIN tab here
 * don't forget to add values in the update function if they need to be updated too
 * 
 * @author muchitsch
 * Jul 15, 2009, 1:20:23 PM
 * 
 */
public class clsInspectorARSin extends Inspector {

	/**
	 * Main Inspector for the ARSIN, displays all values we want on this
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsARSIN moARSIN;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;
	private clsInspectorBodyOverview moInspectorBodyOverview;
	private clsInspectorBasic moDefaultInspector;
	private clsInspectorSensor moInspectorSensor;
	//private clsDriveInspector moDriveInspector;

	

	/**
	 * Constructor ARSIN Inspectors, only give the Entity when it is a ARSIN! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsARSIN poARSIN
	 */
	public clsInspectorARSin(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsARSIN poARSIN) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moARSIN = poARSIN;
		
		Box oBoxHorizontal1 = new Box(BoxLayout.X_AXIS);
		Box oBoxHorizontal2 = new Box(BoxLayout.X_AXIS);
		
		//*** Initilize Default Values Inspector ***
		//get the default things
		moDefaultInspector = new clsInspectorBasic(poOriginalInspector, poWrapper, poGuiState, (clsEntity)poARSIN);
		
		
		//inspected fields....
		//Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		//moProp1 = new  PropertyField("IntEnergyConsuptionSum", clsInspectorUtils.FormatDouble(moARSIN.getInternalEnergyConsuptionSUM()), false, null, PropertyField.SHOW_TEXTFIELD);
		
		//oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
	
		//*** Initialize Body Overview ***
		Box oBoxBodyGraphs = new Box(BoxLayout.Y_AXIS);
		oBoxBodyGraphs.setBorder(BorderFactory.createTitledBorder("Body Graphs"));
		clsComplexBody oBody = (clsComplexBody)((itfGetBody) poARSIN).getBody();
		moInspectorBodyOverview = new clsInspectorBodyOverview(poOriginalInspector, poWrapper, poGuiState, oBody);
		oBoxBodyGraphs.add(moInspectorBodyOverview);
		
		//*** Perception ***
		Box oBoxPerception = new Box(BoxLayout.Y_AXIS);
		oBoxPerception.setBorder(BorderFactory.createTitledBorder("Perception - not workin yet!"));
		moInspectorSensor = new clsInspectorSensor(poOriginalInspector, poWrapper, poGuiState, poARSIN);
		oBoxPerception.add(moInspectorSensor);
		
		//*** Drives ***
		Box oBoxDrives = new Box(BoxLayout.Y_AXIS);
		oBoxDrives.setBorder(BorderFactory.createTitledBorder("Drives"));
		//moDriveInspector = new clsDriveInspector();
		//oBoxDrives.add(moDriveInspector);
		
		//*** Goals ***
		Box oBoxGoals = new Box(BoxLayout.Y_AXIS);
		oBoxGoals.setBorder(BorderFactory.createTitledBorder("Goals"));
		//clsBaseDecisionUnit poDU = (clsBaseDecisionUnit) poItfDU;
		//clsPsychicApparatus oPsyApp = ((clsProcessor)poPA.getProcessor()).getPsychicApparatus();
		//oBoxGoals.add(moInspectorBodyOverview);
		
		//*** Imaginary Actions ***
		Box oBoxImaginary = new Box(BoxLayout.Y_AXIS);
		oBoxImaginary.setBorder(BorderFactory.createTitledBorder("Imaginary Actions"));
		
		//*** Plans ***
		Box oBoxPlans = new Box(BoxLayout.Y_AXIS);
		oBoxPlans.setBorder(BorderFactory.createTitledBorder("Plans"));
		
		//*** Motion Control ***
		Box oBoxMotionControl = new Box(BoxLayout.Y_AXIS);
		oBoxMotionControl.setBorder(BorderFactory.createTitledBorder("Motion Control"));

		//add all inspectors to the component
		//first the horizontal placeholders
		add(oBoxHorizontal1, BorderLayout.LINE_START);
		add(oBoxHorizontal2, BorderLayout.LINE_START);
		//add to line 1
		oBoxHorizontal1.add(moDefaultInspector,  BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal1.add(oBoxBodyGraphs, BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal1.add(oBoxPerception, BorderLayout.AFTER_LAST_LINE);
		//add to line 2
		oBoxHorizontal2.add(oBoxDrives, BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal2.add(oBoxGoals, BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal2.add(oBoxImaginary, BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal2.add(oBoxPlans, BorderLayout.AFTER_LAST_LINE);
		oBoxHorizontal2.add(oBoxMotionControl, BorderLayout.AFTER_LAST_LINE);

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
		
		//moProp1.setValue(clsInspectorUtils.FormatDouble(moARSIN.getInternalEnergyConsuptionSUM()));
		moDefaultInspector.updateInspector();
		moInspectorBodyOverview.updateInspector();
		moInspectorSensor.updateInspector();
		
	}

}
