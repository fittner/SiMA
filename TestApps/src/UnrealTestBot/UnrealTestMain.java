/**
 * UnrealTestMain.java: TestApps - UnrealTestBot
 * 
 * @author muchitsch
 * 23.05.2012, 13:48:18
 */
package UnrealTestBot;

import java.util.Vector;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import statictools.clsGetARSPath;
import statictools.clsSimState;
import statictools.clsUniqueIdGenerator;
import config.clsProperties;
import creation.simplePropertyLoader.clsExternalARSINLoader;
import du.enums.eEntityType;
import du.itf.sensors.clsUnrealSensorValueVision;
import bw.body.brainsocket.clsBrainSocket;
import bw.entities.clsARSIN;
import bw.factories.clsSingletonMasonGetter;
import bw.body.clsComplexBody;



/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author muchitsch
 * 23.05.2012, 13:48:18
 * 
 */
public class UnrealTestMain {
	
	private clsARSIN ARSAgent;
	//private clsOutputActionProcessor outputActionProcessor = new clsOutputActionProcessor();
	private Vector<clsUnrealSensorValueVision> ARSObjectsList = new Vector<clsUnrealSensorValueVision>();
	
	public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
			public void run() {
            	 UnrealTestMain testInstance = new UnrealTestMain();
                 testInstance.testARS();
            }
        });
    }
	
	public void testARS()
    {
		createARSAgent();
		
		//outputActionProcessor.inicializeARSAgent(ARSAgent);	
		
		GenerateTestVisionData();
		
		clsBrainSocket ARSBrain = ((clsComplexBody)ARSAgent.getBody()).getBrain();
		ARSBrain.setUnrealVisionValues(ARSObjectsList);
		
		
		ARSAgent.processing();
		
        System.exit(0);
    }
	
	
	public void logic() {
//    	//Collection<Item> objects = bot.getWorldView().getAllVisible(Item.class).values();
//		interfacesManager.getUT2004VisibleObjects(objects, bot.getLocation());
//
//        
//		interfacesManager.getItfOutputActionProcessor().ARSProcessing();
//		UT2004ActionList= new ArrayList<clsUT2004Action>();
//		UT2004ActionList = interfacesManager.getItfOutputActionProcessor().getActions();
//		for (Iterator iterator = UT2004ActionList.iterator(); iterator.hasNext();) {
//			clsUT2004Action UT2004Action = (clsUT2004Action) iterator.next();
//			doAction(UT2004Action);
//		}
//		interfacesManager.getItfOutputActionProcessor().clearStack();
    }
	
	private void GenerateTestVisionData() {
		// TODO Auto-generated method stub
		clsUnrealSensorValueVision ARSObject;
		double[] ARSPosition;
	    ARSObjectsList = new Vector<clsUnrealSensorValueVision>();
	    
	   //test cake
    	ARSObject = new clsUnrealSensorValueVision();
    	ARSPosition = new double[2];
    	ARSObject.setID("CAKE01");
    	ARSObject.setType(eEntityType.CAKE);
     	ARSObject.setRadius(1.5);
    	ARSObject.setAngle(0.3);
		ARSObjectsList.add(ARSObject);
		
		ARSObject = new clsUnrealSensorValueVision();
    	ARSPosition = new double[2];
    	ARSObject.setID("CAN01");
    	ARSObject.setType(eEntityType.CAN);
     	ARSObject.setRadius(3.5);
    	ARSObject.setAngle(0.1);
		ARSObjectsList.add(ARSObject);
      
		
		
//		for(Item currentItemUT2004 : UT2004ObjectsList)
//        {
//        	ARSObject = new clsUnrealSensorValueVision();
//        	ARSPosition = new double[2];
//        	
//        	ARSObject.setID(defineUT2004ID (currentItemUT2004));
//        	ARSObject.setType(parseEntityType(ARSObject.getID()));
//        	ARSPosition = coordinatesToPolar(currentItemUT2004.getLocation(),botLocation);
//        	ARSObject.setRadius(ARSPosition[0]);
//        	ARSObject.setAngle(ARSPosition[1]);
//			
//			ARSObjectsList.add(ARSObject);
//        }
	}

	
	public void createARSAgent(){
		// Create empty property-object
		clsProperties oProp = new clsProperties();
		
		// Create Singleton (minimum-settings needed for Simulation)
		clsSingletonMasonGetter.setFieldEnvironment(new Continuous2D(25, 0, 0));
		clsSimState.setSimState(new SimState(clsUniqueIdGenerator.getUniqueId()));
		
		// Get properties from property-files. First get path to property files (expected to be in S:\ARSIN01\Sim\config
		String oPath = clsGetARSPath.getConfigPath();
		
		// ARSIN property file. 
		oProp = clsProperties.readProperties(oPath, "arsin.unreal.properties");
		
		// decision unit property file.
		clsProperties oPropDU = clsProperties.readProperties(oPath, "du.unreal.properties");
		//merge settings - overwrites existing entries
		oProp.putAll(oPropDU);
		
		// get the ARSIN
		clsExternalARSINLoader oLoader = new clsExternalARSINLoader(oProp);
		ARSAgent = (clsARSIN)oLoader.getARSINI();
	}

}
