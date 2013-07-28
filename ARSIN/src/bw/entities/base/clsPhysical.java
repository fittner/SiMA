package bw.entities.base;

import bw.factories.clsRegisterEntity;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObjects.sensors.clsEntitySensorEnginePhysical;
import config.clsProperties;


/**
 * DOCUMENT (MW) - insert description 
 * 
 * @author MW
 * 26.02.2013, 21:16:27
 * 
 */
public abstract class clsPhysical extends clsMobile {
	public static final String P_RADIUS = "radius";
	public final static double defaultScheduleStepWidth = 1.0;
	
	public clsEntitySensorEnginePhysical moEntitySensorEngine; 
	private double mdRadius;
	
	public clsPhysical(String poPrefix, clsProperties poProp, int uid) {
		super(poPrefix, poProp, uid);
		applyProperties(poPrefix, poProp);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		
		oProp.putAll(clsMobile.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_RADIUS, 100.0);		
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mdRadius = poProp.getPropertyDouble(pre+P_RADIUS);
	}
	
	public void addSensor(){
		if (moEntitySensorEngine == null){
			moEntitySensorEngine = new clsEntitySensorEnginePhysical(this, mdRadius);
			moEntitySensorEngine.step(clsSingletonMasonGetter.getSimState());
			clsRegisterEntity.registerPhysicalObject2D(moEntitySensorEngine);
			clsSingletonMasonGetter.getFieldEnvironment().setObjectLocation(moEntitySensorEngine, new sim.util.Double2D(moEntitySensorEngine.getPosition().x, moEntitySensorEngine.getPosition().y));
			clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(moEntitySensorEngine, 6, defaultScheduleStepWidth); 	
		}
	}
	
	@Override
	public void updateInternalState() {
	}
	
	public void unregisterPhysical(){
		clsRegisterEntity.unRegisterPhysicalObject2D(moEntitySensorEngine);
		clsSingletonMasonGetter.getFieldEnvironment().allObjects.remove(moEntitySensorEngine);
	}
	
	@Override
	public void sensing() {
	}

	@Override
	public void processing() {
	}
	
	@Override
	public void execution() {
	}

	@Override
	protected void setEntityType() {	
	}
}

