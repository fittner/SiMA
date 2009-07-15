/**
 * @author langr
 * 13.07.2009, 15:01:27
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.entity;
import inspectors.clsInspectorMapping;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Formatter;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import bw.body.clsBaseBody;
import bw.body.clsComplexBody;
import bw.body.itfget.itfGetBody;
import bw.entities.clsEntity;
import bw.utils.inspectors.body.clsFillLevelInspector;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;
import sim.util.gui.PropertyField;
/**
 * TODO (langr) - insert description 
 * 
 * @author langr
 * 13.07.2009, 15:01:27
 * 
 */
public class clsInspectorEntity extends Inspector implements ActionListener {

	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 13.07.2009, 17:42:21
	 */
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
	 * TODO (langr) - insert description 
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

		
		//SimpleProperties oProp = new SimpleProperties(poEntity.getEntityType().toString());

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
		// TODO Auto-generated method stub

		Formatter oDoubleFormatter = new Formatter();
		moProp3.setValue( oDoubleFormatter.format("%.2f", moEntity.getPosition().x).toString() );
		oDoubleFormatter = new Formatter();
		moProp4.setValue( oDoubleFormatter.format("%.2f", moEntity.getPosition().y).toString() );
		
		//TODO: opened windows are not deleted when closed!!!!!
		//FIXME: opened windows are not deleted when closed!!!!!  
		for( clsInspectorFrame oEntityWindow : moEntityWindows)
		{
			if(oEntityWindow != null) {
				oEntityWindow.updateContent();
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.07.2009, 16:10:28
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
//	@Override
//	public void itemStateChanged(ItemEvent e) {
//		Object source = e.getItemSelectable();
		
//		if( source == moCheckBoxCD)
//		{
//			moDU.setRoombaIntelligence(moCheckBoxCD.isSelected());
//		}
//		else if(source == moCheckBoxCA)
//		{
//			moDU.setCollisionAvoidance(moCheckBoxCA.isSelected());
//		}
//		
//		moConsole.refresh();		
//	}

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
			TabbedInspector oMasonInspector = new TabbedInspector();
			oMasonInspector.addInspector(moOriginalInspector, "Default Insp.");
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, "Entity Inspector") );
		}
		else if( source == moBtnBodyInspectors ) {
			TabbedInspector oMasonInspector = new TabbedInspector();
			clsBaseBody iBody = ((itfGetBody)moEntity).getBody();
			if(iBody instanceof clsComplexBody) {
				oMasonInspector.addInspector( new clsFillLevelInspector(moOriginalInspector, moWrapper, moGuiState, ((clsComplexBody)iBody).getInternalSystem().getStomachSystem()), "Stomach System");
			}
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, "Body Inspector") );
		}
		else if( source == moBtnBrainInspectors) {
			TabbedInspector oMasonInspector = new TabbedInspector();
			oMasonInspector.addInspector( clsInspectorMapping.getInspector(moOriginalInspector, moWrapper, moGuiState, ((itfGetBody)moEntity).getBody().getBrain().getDecisionUnit()), "Brain Insp.");
			moEntityWindows.add( clsInspectorFrame.getInspectorFrame(oMasonInspector, "Brain Inspector") );
		}
	}

}
