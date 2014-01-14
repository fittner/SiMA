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
import javax.swing.border.TitledBorder;

import bw.entities.base.clsEntity;
import bw.entities.base.clsMobile;
import bw.utils.inspectors.clsInspectorUtils;
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
public class clsInspectorBasic extends Inspector {

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
	LocationWrapper moWrapper;
	GUIState moGuiState;
	
	private PropertyField moProp1;
	private PropertyField moProp1_1;
	private PropertyField moProp2;
	private PropertyField moProp3;
	private PropertyField moProp4;
	private PropertyField moProp5;
	private PropertyField moProp6;
	private PropertyField moProp7;
	private PropertyField moProp8;
	private PropertyField moProp9;
	private PropertyField moProp10;
	private PropertyField moProp11;
	private PropertyField moProp12;

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
	public clsInspectorBasic(Inspector poOriginalInspector,
            LocationWrapper poWrapper,
            GUIState poGuiState,
            clsEntity poEntity) {
		
		moOriginalInspector = poOriginalInspector;
		moWrapper = poWrapper;
		moGuiState = poGuiState;
		moEntity = poEntity;
		
		//inspected fields....
		Box oBox1 = new Box(BoxLayout.Y_AXIS);
		
		TitledBorder oTitle = BorderFactory.createTitledBorder("Default Entity Values");
		oBox1.setBorder(oTitle);
		
		//inspected defalt clsEntity fields, nothing else!....
		moProp1 = new  PropertyField("ID", ""+moEntity.getId(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp1_1 = new  PropertyField("UID", ""+moEntity.uid(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp2 = new  PropertyField("Type", ""+moEntity.getEntityType().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp3 = new  PropertyField("Position X", clsInspectorUtils.FormatDouble(moEntity.getPose().getPosition().x), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp4 = new  PropertyField("Position Y", clsInspectorUtils.FormatDouble(moEntity.getPose().getPosition().y), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp5 = new  PropertyField("Color", ""+moEntity.get2DShape().getPaint().toString(), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp6 = new  PropertyField("Mass", clsInspectorUtils.FormatDouble(moEntity.getTotalWeight()), false, null, PropertyField.SHOW_TEXTFIELD);
		moProp7 = new  PropertyField("Angle [deg]", clsInspectorUtils.FormatDouble(moEntity.getPose().getAngle().radians *180/Math.PI), false, null, PropertyField.SHOW_TEXTFIELD);
		
		if(moEntity instanceof clsMobile){
			moProp8 = new  PropertyField("Velocity X", clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getVelocity().x), false, null, PropertyField.SHOW_TEXTFIELD);
			moProp9 = new  PropertyField("Velocity X", clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getVelocity().y), false, null, PropertyField.SHOW_TEXTFIELD);
			moProp10 = new  PropertyField("Ang. Velocity", clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getAngularVelocity()), false, null, PropertyField.SHOW_TEXTFIELD);
			moProp11 = new  PropertyField("Force X", clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getMobileObject2D().getForceAccumulator().x), false, null, PropertyField.SHOW_TEXTFIELD);
			moProp12 = new  PropertyField("Force Y", clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getMobileObject2D().getForceAccumulator().y), false, null, PropertyField.SHOW_TEXTFIELD);
		}

		oBox1.add(moProp1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp1_1, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp2, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp3, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp4, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp7, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp5, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp6, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp8, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp9, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp10, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp11, BorderLayout.AFTER_LAST_LINE);
		oBox1.add(moProp12, BorderLayout.AFTER_LAST_LINE);
		
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

		//update the values that could change
		moProp3.setValue(clsInspectorUtils.FormatDouble(moEntity.getPose().getPosition().x));
		moProp4.setValue(clsInspectorUtils.FormatDouble(moEntity.getPose().getPosition().y));
		moProp5.setValue(""+moEntity.get2DShape().getPaint().toString());
		moProp6.setValue(clsInspectorUtils.FormatDouble(moEntity.getTotalWeight()));
		moProp7.setValue(clsInspectorUtils.FormatDouble(moEntity.getPose().getAngle().radians *180/Math.PI));
		
		if(moEntity instanceof clsMobile){
			moProp8.setValue( clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getVelocity().x) );
			moProp9.setValue( clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getVelocity().y) );
			moProp10.setValue( clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getAngularVelocity()) );
			moProp11.setValue( clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getMobileObject2D().getForceAccumulator().x) );
			moProp12.setValue(  clsInspectorUtils.FormatDouble(((clsMobile)moEntity).getMobileObject2D().getForceAccumulator().y) );
		}
	}
	
}
