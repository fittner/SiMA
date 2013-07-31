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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import bw.ARSIN.clsARSIN;
import bw.body.clsComplexBody;
import bw.body.itfget.itfGetBody;
import bw.entities.base.clsEntity;
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
public class clsInspectorARSinDebugActions extends Inspector implements ActionListener{

	/**
	 * shows default inspectors, common for all entities
	 * change this only if the values can be found in the root entity class! 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:24:40 PM
	 */
	private static final long serialVersionUID = 1L;
	public Inspector moOriginalInspector;
	private clsEntity moEntity;
	private clsARSIN moARSin;
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moPropID;

	private JButton moBtnClearStomach;
	private JButton moBtnFeedArsin;
	private JButton moBtnFeedUndigestable;
	private JButton moBtnHurtArsin;
	private JButton moBtnHealArsin;

	/**
	 * CTOR Default Inspectors, 4 all entities 
	 * 
	 * @author muchitsch
	 * Jul 15, 2009, 1:53:51 PM
	 *
	 * @param Inspector originalInspector
	 * @param LocationWrapper wrapper
	 * @param GUIState guiState
	 * @param clsEntity poEntity = is a ARSIN
	 */
	public clsInspectorARSinDebugActions(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsARSIN poARSinEntity) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moEntity = poARSinEntity;
		moARSin = poARSinEntity;
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		TitledBorder oTitle = BorderFactory.createTitledBorder("Debug Actions");
		oBox1.setBorder(oTitle);
		
		moBtnClearStomach = new JButton("Clear Stomach");
		moBtnFeedArsin = new JButton("Feed ARSin");
		moBtnHurtArsin = new JButton("Hurt ARSin");
		moBtnHealArsin = new JButton("Heal ARSin");
		moBtnFeedUndigestable = new JButton("Feed Undigestable");
		
		//inspected defalt clsEntity fields, nothing else!....
		moPropID = new  PropertyField("ID", ""+moEntity.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		
		oBox1.add(moPropID, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnClearStomach, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnFeedArsin, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnFeedUndigestable, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnHealArsin, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moBtnHurtArsin, BorderLayout.AFTER_LAST_LINE);
		
		add(oBox1, BorderLayout.WEST);
		
		moBtnClearStomach.addActionListener(this);
		moBtnFeedArsin.addActionListener(this);
		moBtnFeedUndigestable.addActionListener(this);
		moBtnHurtArsin.addActionListener(this);
		moBtnHealArsin.addActionListener(this);
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

		//update the values that could change

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		clsComplexBody oBody = (clsComplexBody)((itfGetBody) moARSin).getBody();
		
		if (source == moBtnClearStomach) {
			oBody.DEBUG_DestroyAllNutritionAndEnergyForModelTesting();
		}
		else if (source == moBtnFeedArsin){
			oBody.DEBUG_EatFoodPackage();
		}
		else if (source == moBtnHealArsin){
			oBody.DEBUG_HealBody(25);
		}
		else if (source == moBtnHurtArsin){
			oBody.DEBUG_HurtBody(25);
		}
		else if (source == moBtnFeedUndigestable){
			oBody.DEBUG_EatUndigestablePackage();
		}
	}
	
}
