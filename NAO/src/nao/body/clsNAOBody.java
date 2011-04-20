package nao.body;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Vector;

import NAOProxyClient.Command;
import NAOProxyClient.CommandGenerator;
import NAOProxyClient.NAOProxyClient;
import NAOProxyClient.Sensor;

import nao.body.io.clsExternalIO;
import nao.main.clsSingletonNAOState;
import nao.utils.enums.eBodyType;
import nao.body.brainsocket.clsBrainSocket;
import nao.body.clsBaseBody;
import nao.body.itfGetBrain;


public class clsNAOBody extends clsBaseBody implements  itfGetBrain {
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
    private NAOProxyClient moClient;
    public Vector<Sensor> moSensordata;
    private Vector<Command> moCommands;
	
	public clsNAOBody(String URL, int port) throws UnknownHostException, IOException {
		super();
		
		moClient = new NAOProxyClient(URL, port);
		
		moSensordata = new Vector<Sensor>();
		moCommands = new Vector<Command>();
		
		initSequence();
		
//		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
//		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
//		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(this);
//		moInternalIO 	= new clsInternalIO(this);
		moBrain 		= new clsBrainSocket( moExternalIO.getActionProcessor(), moSensordata);
	}
	
	private void initSequence() {
		
		moCommands.clear();

		moCommands.add(CommandGenerator.reset());
		moCommands.add(CommandGenerator.say("Hi! I am NAO. Where is my shrink?"));
			try {
				communicate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		moCommands.clear();
		
	}
	
	@Override
	public clsBrainSocket getBrain() {
		return moBrain;
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
	public void stepExecution()  {
		moExternalIO.stepExecution();

		try {
			communicate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			clsSingletonNAOState.setKeeprunning(false);
		}
//		
//		moInternalIO.stepExecution();
		
	}

	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.NAO;		
	}	
	
	private void communicate() throws IOException {
		moSensordata.clear();
		moSensordata.addAll( moClient.communicate(moCommands) ); 
		moCommands.clear();
	}

	public void addCommand(Command cmd) {
		moCommands.add(cmd);
	}
	
	public void close() throws Exception  {
		moClient.close();
	}

	@Override
	public void stepSensing() {
		// not needed for NAO, leave empty
	}

}
