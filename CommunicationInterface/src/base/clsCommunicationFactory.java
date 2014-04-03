package base;

import communication.layer1.implementations.clsLayer1ProcedureCall;
import communication.layer2.implementations.clsXMLData;
import communication.layer3.implementations.clsBufferContainer;
import communication.layer3.implementations.clsEventBuffer;
import communication.layer3.implementations.clsSignalBuffer;
import communication.layer4.implementations.clsLayer4Blocking;
import communication.layer4.implementations.clsLayer4NonBlocking;
import communication.layer4.implementations.clsLayer4NonBlockingThread;

public class clsCommunicationFactory {

	public static clsCommunicationInterface createBlockingInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer4Blocking oLayer5 = new clsLayer4Blocking();
		
		oLayer1.setLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer3(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer4(oLayer5);
		oLayer5.setLayer3(oLayer4);
		
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
	
	public static clsCommunicationInterface createNonBlockingInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer4NonBlocking oLayer5 = new clsLayer4NonBlocking();
		
		oLayer1.setLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer3(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer4(oLayer5);
		oLayer5.setLayer3(oLayer4);
		
	
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
	public static clsCommunicationInterface createNonBlockingThreadInterface(){
		clsCommunicationInterface  oRetVal;
		clsLayer1ProcedureCall oLayer1 = new clsLayer1ProcedureCall();
		clsXMLData oLayer3 = new clsXMLData();
		clsBufferContainer oLayer4 = new clsBufferContainer();
		clsLayer4NonBlockingThread oLayer5 = new clsLayer4NonBlockingThread();
		
		oLayer1.setLayer3(oLayer3);
		oLayer3.setLayer1(oLayer1);
		oLayer3.setLayer3(oLayer4);
		oLayer4.setLayer3(oLayer3);
		oLayer4.setLayer4(oLayer5);
		oLayer5.setLayer3(oLayer4);
		
		oRetVal = new clsCommunicationInterface(oLayer1,oLayer3,oLayer4,oLayer5);
		
		return oRetVal;

		
	}
}
