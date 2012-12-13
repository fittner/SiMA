/**
 * @author horvath
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

import config.clsProperties;
import du.enums.eEntityType;
import bw.utils.enums.eShapeType;
import du.itf.itfDecisionUnit;
import bw.body.clsComplexBody;
import bw.body.itfGetExternalIO;
import bw.body.itfget.itfGetInternalEnergyConsumption;
import bw.body.itfget.itfGetRadiation;
import bw.body.itfget.itfGetSensorEngine;
import bw.entities.tools.clsShape2DCreator;
import bw.entities.tools.eImagePositioning;
import bw.utils.enums.eBodyType;
import bw.utils.inspectors.entity.clsInspectorFungusEater;


//import tstBw.*;

/**
 * Host of the Fungus Eater body and brain
 * 
 * @author horvath
 * 
 */
public class clsFungusEater extends clsAnimate implements itfGetSensorEngine, itfGetRadiation {

	
	public clsFungusEater(itfDecisionUnit poDU, String poPrefix, clsProperties poProp, int uid) {
		super(poDU, poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.putAll( clsAnimate.getDefaultProperties(pre) );
		
		// remove whatever body has been assigned by getDefaultProperties
		oProp.removeKeysStartingWith(pre+clsAnimate.P_BODY);
		//add correct body
		oProp.putAll( clsComplexBody.getDefaultProperties(pre+P_BODY) );
		oProp.setProperty(pre+P_BODY_TYPE, eBodyType.COMPLEX.toString());
		
  		oProp.setProperty(pre+P_SHAPE+"."+clsShape2DCreator.P_DEFAULT_SHAPE, P_SHAPENAME);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_TYPE, eShapeType.CIRCLE.name());
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_RADIUS, 10.0);
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_COLOR, new Color(0,200,0));
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_PATH, "/World/src/resources/images/arsin_red.png");
		oProp.setProperty(pre+P_SHAPE+"."+P_SHAPENAME+"."+clsShape2DCreator.P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());		
		
		oProp.setProperty(pre+P_STRUCTURALWEIGHT, 50.0);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		// String pre = clsProperties.addDot(poPrefix);
		// nothing to do
	}

	// TODO: this code should be transferred to the entities inspector class - used only for inspectors
	public double getInternalEnergyConsuptionSUM() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getSum();	} 
	public Object[] getInternalEnergyConsumption() { return ((itfGetInternalEnergyConsumption)moBody).getInternalEnergyConsumption().getMergedList().values().toArray();	}
	public Object[] getSensorExternal() {
		if (moBody instanceof itfGetExternalIO) {
			return ((itfGetExternalIO)moBody).getExternalIO().moSensorEngine.getMeRegisteredSensors().values().toArray();
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.FUNGUS_EATER;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 11, 2012 4:16:13 PM
	 * 
	 * @see bw.entities.clsEntity#addEntityInspector(sim.portrayal.inspector.TabbedInspector, sim.portrayal.Inspector, sim.portrayal.LocationWrapper, sim.display.GUIState, bw.entities.clsEntity)
	 */
	@Override
	public void addEntityInspector(TabbedInspector poTarget,
			Inspector poSuperInspector, LocationWrapper poWrapper,
			GUIState poState, clsEntity poEntity) {
		poTarget.addInspector( new clsInspectorFungusEater(poSuperInspector, poWrapper, poState, (clsFungusEater)poEntity), "Fungus Eater");
		
	}
}