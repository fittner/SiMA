package physics2D.physicalObject.sensors;

import interfaces.itfEntity;

import java.awt.Color;

import physics2D.shape.clsFullCircleBorder;

import sim.engine.Steppable;
import singeltons.clsSingletonProperties;



	/**
	 * DOCUMENT (MW) - insert description 
	 * 
	 * @author MW
	 * 04.03.2013, 11:03:37
	 * 
	 */
	public class clsEntitySensorEnginePhysical extends clsEntitySensorEngine implements Steppable {
		/** DOCUMENT (MW) - insert description; @since 04.03.2013 16:13:03 */
		private static final long serialVersionUID = -6271582442455918699L;

		public clsEntitySensorEnginePhysical(itfEntity poHostEntity,
				double pnRadius) {
			super(poHostEntity, pnRadius);
		}

		@Override
		public void setDrawOptions(double pnRadius){
			 double pnAngle = 0.001;
			 Color oColor = Color.darkGray;
			 
			 if(clsSingletonProperties.drawSensors()){
				 pnAngle = 2*Math.PI; 
			 }
			
			 setMoShape(new clsFullCircleBorder(pnRadius, pnAngle, oColor));		
		}
	}