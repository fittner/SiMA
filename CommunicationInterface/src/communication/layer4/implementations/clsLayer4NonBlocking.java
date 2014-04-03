package communication.layer4.implementations;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartner;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;

public class clsLayer4NonBlocking implements itfLayer4{

	private itfLayer3 moLayer3;
	private itfCommunicationPartner moPartner;


	public void setLayer3(itfLayer3 moLayer3) {
		this.moLayer3= moLayer3;
	}
	
	@Override
	public clsDataContainer recvLayer3Data() {
		clsDataContainer oRetVal = new clsDataContainer();
		clsDataPoint oDataPoint = new clsDataPoint("RESPONSE","OK");
		oRetVal.addDataPoint(oDataPoint);
		return oRetVal;
	}

	@Override
	public clsDataContainer sendData(clsDataContainer poData) {
		moLayer3.recvLayer4Data(poData);
		return null;
	}

	@Override
	public clsDataContainer recvData(clsDataContainer poData) {
		return moLayer3.getData();
	}

	@Override
	public void setCommunicationPartner(itfCommunicationPartner poPartner) {
		moPartner = poPartner;
		
	}
	




}
