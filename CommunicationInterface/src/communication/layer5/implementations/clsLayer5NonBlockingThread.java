package communication.layer5.implementations;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartner;
import communication.interfaces.itfCommunicationPartnerThread;
import communication.layer4.interfaces.itfLayer4;
import communication.layer5.interfaces.itfLayer5;

public class clsLayer5NonBlockingThread implements itfLayer5{

	private itfLayer4 moLayer4;
	private itfCommunicationPartnerThread moPartner;


	public void setLayer4(itfLayer4 moLayer4) {
		this.moLayer4= moLayer4;
	}
	
	@Override
	public clsDataContainer recvLayer4Data() {
		clsDataContainer oRetVal = new clsDataContainer();
		clsDataPoint oDataPoint = new clsDataPoint("RESPONSE","OK");
		oRetVal.addDataPoint(oDataPoint);
		
		moPartner.newDataAvailable();
		return oRetVal;
	}

	@Override
	public clsDataContainer sendData(clsDataContainer poData) {
		moLayer4.recvLayer5Data(poData);
		return null;
	}

	@Override
	public clsDataContainer recvData(clsDataContainer poData) {
		return moLayer4.getData();
	}

	@Override
	public void setCommunicationPartner(itfCommunicationPartner poPartner) {
		moPartner = (itfCommunicationPartnerThread) poPartner;
		
	}
	




}
