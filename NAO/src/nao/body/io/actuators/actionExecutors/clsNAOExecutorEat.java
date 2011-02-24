/**
 * @author Benny D�nz
 * 13.05.2009, 21:44:44
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package nao.body.io.actuators.actionExecutors;

import NAOProxyClient.CommandGenerator;
import NAOProxyClient.Sensor;
import NAOProxyClient.SensorValueTuple;
import NAOProxyClient.SensorValueVision;
import NAOProxyClient.eSensors;
import config.clsBWProperties;
import java.util.ArrayList;
import java.util.Vector;

import nao.body.clsNAOBody;
import nao.body.io.actuators.clsActionExecutor;
import du.enums.eSensorExtType;
import du.itf.actions.*;

/**
 * Action Executor for eating
 * Proxy itfAPEatable
 * Parameters:
 *   poRangeSensor = Visionsensor to use
 * 	 prBiteSize = Size of bite taken when eating (default = weight 1)
 * 
 * @author Benny D�nz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsNAOExecutorEat extends clsActionExecutor{

	static double srStaminaDemand = 0; //0.5f;		//Stamina demand 			
	
	private ArrayList<Class<?>> moMutEx = new ArrayList<Class<?>>();
	private eSensorExtType moRangeSensor;
	
	private clsNAOBody moNAOBody;	

	public static final String P_RANGESENSOR = "rangesensor";
	public static final String P_BIZESIZE = "bitesize";

	public clsNAOExecutorEat(clsNAOBody poNAOBody) {
		super();
		
		moNAOBody = poNAOBody;
	}
	
	/*
	 * Set values for SensorActuator base-class
	 */
	@Override
	protected void setBodyPartId() {
		//mePartId = bw.utils.enums.eBodyParts.ACTIONEX_EAT;
	}
	@Override
	protected void setName() {
		//moName="Eat executor";	
	}

	/*
	 * Mutual exclusions (are bi-directional, so only need to be added in order of creation 
	 */
	@Override
	public ArrayList<Class<?>> getMutualExclusions(clsActionCommand poCommand) {
		return moMutEx; 
	}
	
	private Sensor extractVisionSensor() {
		Sensor vision = null;
		
		for (Sensor s:moNAOBody.moSensordata) {
			if (s.id == eSensors.VISION) {
				vision = s;
				break;
			}
		}
		
		return vision;
	}
	
	private Vector<SensorValueVision> getCloseEntries(Sensor poVision, double prMaxdistance) {
		Vector<SensorValueVision> entries = new Vector<SensorValueVision>();
		
		if (poVision != null) {
			for (SensorValueTuple v:poVision.values) {
				SensorValueVision e = (SensorValueVision)v;
				if (e.getR() < prMaxdistance) {
					entries.add(e);
				}
			}
		}
		
		return entries;
	}
	
	/*
	 * Executor 
	 */
	@Override
	public boolean execute(clsActionCommand poCommand) {
//		clsActionEat oCommand =(clsActionEat) poCommand; 
		
		//Is something in range - if one and only one entry is visible and close enough - this one can be eaten!
		Vector<SensorValueVision> closeEntries = getCloseEntries( extractVisionSensor(), 0.5 ); // 0.5 is arbitrary value to simulated eatablearea
		if (closeEntries != null && closeEntries.size()==1) {
			SensorValueVision entry = closeEntries.get(0);  //one and only one entry found in vicinty
			int id = Integer.parseInt( entry.getName() );
			moNAOBody.addCommand( CommandGenerator.consume( id ) );
			
			System.out.println("EXCMD: eat:'" + id + "'");
		} else {
			System.out.println("EXCMD: eat: FAILED!!!");
		}
		
		
		return true;
	}	

}
