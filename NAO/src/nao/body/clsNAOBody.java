package nao.body;



import jnao.Command;
import jnao.Sensor;
import jnao.TCPClient;

import java.util.Arrays;
import java.util.Vector;

import nao.body.io.clsExternalIO;
import nao.utils.enums.eBodyType;
import nao.body.brainsocket.clsBrainSocket;
import nao.body.clsBaseBody;
import nao.body.itfGetBrain;


/**
 * @author muchitsch
 * 
 * This is the body implementation for the NAO connected body.
 * The body connects vis TCP to his real counterpart.
 */
public class clsNAOBody extends clsBaseBody implements  itfGetBrain {
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
    private TCPClient client;
	
	public clsNAOBody(String URL, int port) throws Exception {
		super();
		
		// test without tcp 
		client = new TCPClient(URL, port);
		
//		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
//		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
//		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(this);
//		moInternalIO 	= new clsInternalIO(this);
		moBrain 		= new clsBrainSocket( moExternalIO.getActionProcessor());
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
		moExternalIO.stepExecution();
//		moInternalIO.stepExecution();
		
	}

	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.NAO;		
		
	}	
	
	//nao tcp comm utilities...
	
	private Vector<Sensor> splitReturnMsg(String msg) {
		Vector<Sensor> sensors= new Vector<Sensor>();
		
		if (msg!=null && msg.length() > 0) {
			String[] temp = msg.split(Sensor.outerdelimiter);
	
			Vector<String> smsg = new Vector<String>(Arrays.asList(temp));
			for (String s:smsg) {
				Sensor sensor = Sensor.stringToSensor(s);
				sensors.add(sensor);
			}
		}
		
		return sensors;
	}

	
	
	public Vector<Sensor> communicate(Command cmd) throws Exception {
		client.send(cmd.toMsg());
		
		String received = client.recieve();
		
		Vector<Sensor> sensordata = splitReturnMsg(received);
		
		return sensordata;
	}

	
	public void close() throws Exception  {
		if(client != null)
		{
			client.close();
		}
	}

}
