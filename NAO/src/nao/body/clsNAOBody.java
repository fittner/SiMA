package nao.body;


import java.util.Arrays;
import java.util.Vector;

import NAOProxyClient.Command;
import NAOProxyClient.NAOProxyClient;
import NAOProxyClient.Sensor;

import nao.body.io.clsExternalIO;
import nao.utils.enums.eBodyType;
import nao.body.brainsocket.clsBrainSocket;
import nao.body.clsBaseBody;
import nao.body.itfGetBrain;


public class clsNAOBody extends clsBaseBody implements  itfGetBrain {
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
    private NAOProxyClient moClient;
    private Vector<Sensor> moSensordata;
    private Vector<Command> moCommands;
	
	public clsNAOBody(String URL, int port) throws Exception {
		super();
		
		moClient = new NAOProxyClient(URL, port);
		moSensordata = new Vector<Sensor>();
		moCommands = new Vector<Command>();
		
//		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
//		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
//		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(this);
//		moInternalIO 	= new clsInternalIO(this);
		moBrain 		= new clsBrainSocket( moExternalIO.getActionProcessor(), moSensordata);
	}
	
	@Override
	public clsBrainSocket getBrain() {
		return moBrain;
	}

	@Override
	public void stepSensing() {
//		moExternalIO.stepSensing();
//		moInternalIO.stepSensing();
		
	}

	@Override
	public void stepUpdateInternalState() {
//		moInternalSystem.stepUpdateInternalState(); //call first!
//		moIntraBodySystem.stepUpdateInternalState();
//		moInterBodyWorldSystem.stepUpdateInternalState();
		
	}

	@Override
	public void stepProcessing() {
		moBrain.stepProcessing();
		
	}

	@Override
	public void stepExecution() {
		try {
			communicate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		moExternalIO.stepExecution();
//		moInternalIO.stepExecution();
		
	}

	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.NAO;		
	}	
	
	private void communicate() throws Exception {
		moSensordata = moClient.communicate(moCommands); 
		moCommands.clear();
	}

	public void addCommand(Command cmd) {
		moCommands.add(cmd);
	}
	
	public void close() throws Exception  {
		moClient.close();
	}

}
