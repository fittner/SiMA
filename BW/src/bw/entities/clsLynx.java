/**
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import du.utils.enums.eDecisionType;
//import sim.display.clsKeyListener;
//import simple.remotecontrol.clsRemoteControl;
import bw.entities.tools.clsShapeCreator;
import bw.utils.config.clsBWProperties;
import bw.utils.enums.eShapeType;
import enums.eEntityType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 12.05.2009, 19:37:43
 * 
 */
public class clsLynx extends clsAnimal {

	public clsLynx(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp );
		applyProperties(poPrefix, poProp);
		setAlive(true);
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
		//add additional fields here
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);

		clsBWProperties oProp = new clsBWProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		//TODO: (langr) - should pass the config to the decision unit!
		//oProp.putAll( clsDumbMindA.getDefaultProperties(pre) ); //clsDumbMindA.getDefaultProperties(pre)
		oProp.setProperty(pre+P_DECISION_TYPE, eDecisionType.LYNX_IFTHENELSE.name());
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 200.0);
		
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_RADIUS, 15.0);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_COLOR, Color.pink);
		oProp.setProperty(pre+P_SHAPE+"."+clsShapeCreator.P_IMAGE_PATH, "/BW/src/resources/images/luchs.gif");
		
//		oProp.setProperty(pre+P_MOBILE_SPEED, "6.0" );
//		oProp.setProperty(pre+P_ENTITY_WEIGHT, "200.0" ); //TODO: (creator) is this for the mass???
		
		return oProp;
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		// TODO (deutsch) - Auto-generated method stub
		meEntityType = eEntityType.LYNX;
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:36:09
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {

//	    ((clsRemoteControl)(moBody.getBrain().getDecisionUnit())).setKeyPressed(clsKeyListener.getKeyPressed());		
		super.processing();
	}
}
