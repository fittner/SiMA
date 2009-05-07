/**
 * @author muchitsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities;

import java.awt.Color;
import bw.actionresponses.clsCakeResponses;
import bw.factories.clsRegisterEntity;
import bw.utils.container.clsConfigMap;
import enums.eEntityType;
import ARSsim.physics2D.util.clsPose;

/**
 * Mason representative (physics+renderOnScreen) for a stone. 
 * 
 * FIXME clemens die Steine kann man an den Ecken aus dem Grid rausschieben???
 * @author muchitsch
 * 
 */
public class clsCake extends clsInanimate {
	private static double mrDefaultMass = 30.0;
	private static double mrDefaultRadius = 10.0;
	private static String moImagePath = bw.sim.clsBWMain.msArsPath + "/src/resources/images/cake.gif";
	private static Color moDefaultColor = Color.pink;
	
	private float mrCakeWeight;
	private boolean mnTotallyConsumed;
	private boolean mnShapeUpdated;

	public clsCake(int pnId, clsPose poPose, sim.physics2D.util.Double2D poStartingVelocity, clsConfigMap poConfig)
    {
//		super(pnId, poPose, poStartingVelocity, new ARSsim.physics2D.shape.clsCircleImage(prRadius, clsStone.moDefaultColor, clsStone.moImagePath), prRadius * clsStone.mrDefaultRadiusToMassConversion);
		//todo muchitsch ... hier wird eine default shape �bergeben, nicht null, sonst krachts
		super(pnId, poPose, poStartingVelocity, null, clsCake.mrDefaultMass, clsCake.getFinalConfig(poConfig));
		
		applyConfig();
		
		mrCakeWeight = (float) mrDefaultMass;
		mnTotallyConsumed = false;
		mnShapeUpdated = false;
		
		setShape(new ARSsim.physics2D.shape.clsCircleImage(clsCake.mrDefaultRadius, moDefaultColor , moImagePath), clsCake.mrDefaultMass);
		
		setEntityActionResponse(new clsCakeResponses(this));
    } 
	
	private void applyConfig() {
		//TODO add ...

	}
	
	private static clsConfigMap getFinalConfig(clsConfigMap poConfig) {
		clsConfigMap oDefault = getDefaultConfig();
		oDefault.overwritewith(poConfig);
		return oDefault;
	}
	
	private static clsConfigMap getDefaultConfig() {
		clsConfigMap oDefault = new clsConfigMap();
		
		//TODO add ...
		
		return oDefault;
	}
	
	public float withdraw(float prAmount) {
		float rWeight = 0.0f;
		
		if (prAmount > 0.0f) {
			if (mrCakeWeight > prAmount) {
				rWeight = prAmount;
				mrCakeWeight -= prAmount;
			} else {
				rWeight = mrCakeWeight;
				mrCakeWeight = 0.0f;
				mnTotallyConsumed = true;
			}
		}
		
		return rWeight;
	}
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#setEntityType()
	 */
	@Override
	protected void setEntityType() {
		meEntityType = eEntityType.CAKE;
		
	}

	/* (non-Javadoc)
	 * @see bw.clsEntity#sensing()
	 */
	@Override
	public void sensing() {
		// TODO Auto-generated method stub
		
	}
	

	/* (non-Javadoc)
	 * @see bw.clsEntity#execution(java.util.ArrayList)
	 */
	@Override
	public void execution() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#processing(java.util.ArrayList)
	 */
	@Override
	public void processing() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.02.2009, 17:37:10
	 * 
	 * @see bw.entities.clsEntity#updateInternalState()
	 */
	@Override
	public void updateInternalState() {
		// TODO Auto-generated method stub
		
		if (mnTotallyConsumed && !mnShapeUpdated) {
			mnShapeUpdated = true;
			setShape(new sim.physics2D.shape.Circle(clsCake.mrDefaultRadius, Color.gray), clsCake.mrDefaultMass);
			
			//TODO langr: wohin damit
			//This command removes the cake from the playground
			clsRegisterEntity.unRegisterPhysicalObject2D(getMobileObject2D());
		}
	}

}