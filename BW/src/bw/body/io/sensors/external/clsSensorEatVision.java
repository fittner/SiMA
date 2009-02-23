/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.body.io.sensors.external;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import bw.clsEntity;
import bw.body.io.clsBaseIO;
import bw.factories.clsSingletonMasonGetter;
import bw.physicalObject.inanimate.mobile.clsMobile;
import bw.utils.enums.eBodyParts;

/**
 * Vision of lists of objects in front of mouth for eating
 * TODO crate a eat vision based on heimos one
 * @author muchitsch
 * 
 */
public class clsSensorEatVision extends clsSensorExt {

	private double mnViewDegree;
	private double mnVisRange; 
	
	/**
	 * @param poBaseIO
	 */
	public clsSensorEatVision(clsEntity poEntity, clsBaseIO poBaseIO) {
		super(poBaseIO);


		mnViewDegree = Math.PI;
		mnVisRange = 20; 
	}
	
	
	/**
	 * TODO (zeilinger) - insert description
	 *
	 * @param poEntity
	 */
	private void regVisionObj(clsEntity poEntity)	{
//		Double2D oEntityPos = ((clsMobile)poEntity).getMobile().getPosition(); 
//		Angle oEntityOrientation = ((clsMobile)poEntity).getMobile().getOrientation(); 
//		
//		PhysicsEngine2D oPhyEn2D = clsSingletonMasonGetter.getPhysicsEngine2D();
//		Continuous2D oFieldEnvironment = clsSingletonMasonGetter.getFieldEnvironment();
//		SimState oSimState = clsSingletonMasonGetter.getSimState();
//		
//		try
//		{
//			moVisionArea.setPose(oEntityPos, oEntityOrientation);
//			oPhyEn2D.register(moVisionArea);
//			oPhyEn2D.setNoCollisions(moVisionArea,((clsMobile)poEntity).getMobile());
//			oFieldEnvironment.setObjectLocation(moVisionArea, new sim.util.Double2D(oEntityPos.x, oEntityPos.y));
//	        oSimState.schedule.scheduleRepeating(moVisionArea);
//		}
//		catch( Exception ex )
//		{
//			System.out.println(ex.getMessage());
//		}
    }
	
	

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setBodyPartId()
	 */
	@Override
	protected void setBodyPartId() {
		mePartId = eBodyParts.SENSOR_EXT_EATVISION;

	}

	/* (non-Javadoc)
	 * @see bw.body.io.clsSensorActuatorBase#setName()
	 */
	@Override
	protected void setName() {
		moName = "ext. Sensor Eat Vision";

	}

	/* (non-Javadoc)
	 * @see bw.body.io.sensors.itfSensorUpdate#updateSensorData()
	 */
	@Override
	public void updateSensorData() {
		// TODO Auto-generated method stub

	}

}
