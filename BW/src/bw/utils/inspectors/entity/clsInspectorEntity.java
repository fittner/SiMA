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

import du.enums.eDecisionType;

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
import statictools.clsExceptionUtils;

/**
 * The InspectorEntity is the main entry point for all further entity
 * inspectors. It provides common entity information (position, id, ...) and
 * three buttons for further popup-inspector-tab-windows:
 * 
 * 1.: EntityInspectors 2.: BodyInspectors 3.: BrainInspectors
 * 
 * All three are of the type clsInspectorFrame. This frame contains/handles the
 * inspectors and registers itself as a Steppable in the MASON's schedule to get
 * updated. It then calls the containing inspects.
 * 
 * Define the inspector-content for each entity in the responsible
 * InspectorMapping-classes: clsInspectorMappingEntity (for Entity and Body)
 * clsInspectorMappingDecision (for brain related things)
 * 
 * NOTE: each customized inspector has to implement the
 * updateInspector-function!
 * 
 * @author langr 13.07.2009, 15:01:27
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
	private JButton moBtnPropertiesInspectors;

	private PropertyField moPropEntityType;
	private PropertyField moPropBodyType;
	private PropertyField moPropMindyType;
	private PropertyField moPropEntityID;
	private PropertyField moPropUID;
	private PropertyField moPropPosX;
	private PropertyField moPropPosY;

	/**
	 * DOCUMENT (langr) - insert description
	 * Constructor of the ARS Inspector override.
	 * this method creates the basic properties to be shown on the inspector Tab of mason
	 * and 3 Buttons with detailed information for a specific area
	 * 
	 * @author langr 13.07.2009, 15:01:27
	 * 
	 */
	public clsInspectorEntity(Inspector originalInspector, LocationWrapper wrapper, GUIState guiState, clsEntity poEntity) {
		moOriginalInspector = originalInspector;
		moWrapper = wrapper;
		moEntity = poEntity;
		moGuiState = guiState;

		//add a box for inspector property holding
		Box oPropertyLayoutContainerBox = new Box(BoxLayout.Y_AXIS);
		

		
		//fill basic properties with inspector information
		moPropEntityType = new PropertyField("Type", poEntity.getEntityType()
				.toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		moPropEntityID = new PropertyField("ID", "" + poEntity.getId(), false,
				null, PropertyField.SHOW_TEXTFIELD);

		moPropBodyType = new PropertyField("Body", poEntity.getBody()
				.getBodyType().toString(), false, null,
				PropertyField.SHOW_TEXTFIELD);
		moPropUID = new PropertyField("UID", poEntity.uid()+"", false, null, PropertyField.SHOW_TEXTFIELD);

		String oMindType = eDecisionType.NONE.toString();
		try {
			oMindType = (((itfGetBrain) ((itfGetBody) moEntity).getBody())
					.getBrain().getDecisionUnit()).getDecisionUnitType()
					.toString();
		} catch (java.lang.ClassCastException ex) {
			// ignore - no mind present
		}

		moPropMindyType = new PropertyField("Mind", oMindType, false, null, PropertyField.SHOW_TEXTFIELD);

		moPropPosX = new PropertyField("Position X", ""
				+ moEntity.getPosition().x, false, null,
				PropertyField.SHOW_TEXTFIELD);
		moPropPosY = new PropertyField("Position Y", ""
				+ moEntity.getPosition().y, false, null,
				PropertyField.SHOW_TEXTFIELD);

		//add 3 buttons for detailes special inspectors
		moBtnEntityInspectors = new JButton("Entity Details...");
		moBtnBodyInspectors = new JButton("Body Details... ");
		moBtnBrainInspectors = new JButton("Brain Details...");
		moBtnPropertiesInspectors = new JButton("Prop. Details...");


		oPropertyLayoutContainerBox.add(moPropEntityID, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropUID, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropEntityType, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropBodyType, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropMindyType, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropPosX, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moPropPosY, BorderLayout.AFTER_LAST_LINE);

		oPropertyLayoutContainerBox.add(moBtnEntityInspectors, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moBtnBodyInspectors, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moBtnBrainInspectors, BorderLayout.AFTER_LAST_LINE);
		oPropertyLayoutContainerBox.add(moBtnPropertiesInspectors, BorderLayout.AFTER_LAST_LINE);

		add(oPropertyLayoutContainerBox, BorderLayout.AFTER_LAST_LINE);

		moBtnEntityInspectors.addActionListener(this);
		moBtnBodyInspectors.addActionListener(this);
		moBtnBrainInspectors.addActionListener(this);
		moBtnPropertiesInspectors.addActionListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author langr 13.07.2009, 15:01:27
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		Formatter oDoubleFormatter = new Formatter();
		moPropPosX.setValue(oDoubleFormatter.format("%.2f",	moEntity.getPosition().x).toString());
		oDoubleFormatter = new Formatter();
		moPropPosY.setValue(oDoubleFormatter.format("%.2f",	moEntity.getPosition().y).toString());

		for (Iterator<clsInspectorFrame> it = moEntityWindows.iterator(); it.hasNext();) 
		{
			clsInspectorFrame oFrame = it.next();
			if (oFrame != null) {
				// oFrame.updateContent();
				if (!oFrame.isVisible()) {
					oFrame.stopInspector(); // remove the inspector-steppables
											// from the schedule
					it.remove();// remove the window that should be updated from
								// the list when it is invisible (=closed)
				}
			} else { // remove the window that should be updated from the list
						// when it null (should not happen)
				it.remove();
			}
		}
	}


	/* (non-Javadoc)
	 *
	 * @author muchitsch
	 * 05.04.2011, 13:59:48
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * creates the java frames when one of the tree buttons are pressed, and puts the inspectors on them
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		String oTabName = moEntity.getId() + " - "	+ moEntity.getEntityType().toString();

		if (source == moBtnEntityInspectors) {
			// define the inspector-content for each entity in the responsible
			// InspectorMapping-class
			TabbedInspector oMasonInspector = clsInspectorMappingEntity
					.getInspectorEntity(moOriginalInspector, moWrapper,
							moGuiState, moEntity);
	
			moEntityWindows.add(clsInspectorFrame.getInspectorFrame(oMasonInspector, oTabName + " - Entity Inspector"));
			
		} else if (source == moBtnBodyInspectors) {
			try {
				clsBaseBody iBody = ((itfGetBody) moEntity).getBody();
				// define the inspector-content for each entity in the
				// responsible InspectorMapping-class
				TabbedInspector oMasonInspector = clsInspectorMappingEntity
						.getInspectorBody(moOriginalInspector, moWrapper,
								moGuiState, iBody);
				
				moEntityWindows.add(clsInspectorFrame.getInspectorFrame(oMasonInspector, oTabName + " - Body Inspector"));
				
			} catch (java.lang.ClassCastException ex) {
				System.out.println(clsExceptionUtils.getCustomStackTrace(ex));
			}
		} else if (source == moBtnBrainInspectors) {
			// define the inspector-content for each entity in the responsible
			// InspectorMapping-class
			try {
				TabbedInspector oMasonInspector = clsInspectorMappingDecision
						.getInspector(moOriginalInspector, moWrapper,
								moGuiState,
								((itfGetBrain) ((itfGetBody) moEntity)
										.getBody()).getBrain()
										.getDecisionUnit());
				
				moEntityWindows.add(clsInspectorFrame.getInspectorFrame(oMasonInspector, oTabName + " - Brain Inspector"));
				
			} catch (java.lang.ClassCastException ex) {
				System.out.println(clsExceptionUtils.getCustomStackTrace(ex));

			}

		}else if (source == moBtnPropertiesInspectors) {
			// define the inspector-content for each entity in the responsible
			// InspectorMapping-class
			try {
				//TODO call the property Inspector here
				System.out.print("Properties Inspector not finished yet, come again later (CM)\n");
//				TabbedInspector oMasonInspector = clsInspectorMappingEntity
//				.getInspectorEntity(moOriginalInspector, moWrapper,
//						moGuiState, moEntity);
//
//				moEntityWindows.add(clsInspectorFrame.getInspectorFrame(oMasonInspector, oTabName + " - Properties Inspector"));
				
			} catch (java.lang.ClassCastException ex) {
				System.out.println(clsExceptionUtils.getCustomStackTrace(ex));
	
			}
		}
		
	}
}
