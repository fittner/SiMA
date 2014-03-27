package base;

import communication.layer1.implementations.clsLayer1ProcedureCall;
import communication.layer3.implementations.clsXMLData;
import communication.layer4.implementations.clsBufferContainer;
import communication.layer4.implementations.clsEventBuffer;
import communication.layer4.implementations.clsSignalBuffer;
import communication.layer5.implementations.clsLayer5Blocking;
import communication.layer5.implementations.clsLayer5NonBlocking;
import communication.layer5.implementations.clsLayer5NonBlockingThread;

public class clsCommunicationFactory {

	public static clsCommunicationInterface createBlockingInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer5Blocking oLayer5 = new clsLayer5Blocking();
		
		oLayer1.setMoLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer4(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer5(oLayer5);
		oLayer5.setLayer4(oLayer4);
		
		oLayer4.addBuffer("VISION_NEAR", new clsSignalBuffer());
		oLayer4.addBuffer("EVENT_STOMACH", new clsEventBuffer());
		
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
	
	public static clsCommunicationInterface createNonBlockingInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer5NonBlocking oLayer5 = new clsLayer5NonBlocking();
		
		oLayer1.setMoLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer4(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer5(oLayer5);
		oLayer5.setLayer4(oLayer4);
		
		oLayer4.addBuffer("VISION_NEAR", new clsSignalBuffer());
		oLayer4.addBuffer("EVENT_STOMACH", new clsEventBuffer());
		
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
	public static clsCommunicationInterface createNonBlockingThreadInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer5NonBlockingThread oLayer5 = new clsLayer5NonBlockingThread();
		
		oLayer1.setMoLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer4(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer5(oLayer5);
		oLayer5.setLayer4(oLayer4);
		
		oLayer4.addBuffer("VISION_NEAR", new clsSignalBuffer());
		oLayer4.addBuffer("EVENT_STOMACH", new clsEventBuffer());
		
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
}
