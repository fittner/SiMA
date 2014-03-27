package communication.layer5.implementations;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartner;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;
import communication.layer5.interfaces.itfLayer5;

public class clsLayer5Blocking implements itfLayer5{

	private itfLayer4 moLayer4;
	private itfCommunicationPartner moCommunicationPartner;


	public void setLayer4(itfLayer4 moLayer4) {
		this.moLayer4= moLayer4;
	}

	public void setCommunicationPartner(itfCommunicationPartner moCommunicationPartner) {
		this.moCommunicationPartner = moCommunicationPartner;
	}
	
	@Override
	public clsDataContainer recvLayer4Data() {
		clsDataContainer oData = moLayer4.getData();
		clsDataContainer oRetVal =moCommunicationPartner.recvData(oData);
		return oRetVal;
	}

	@Override
	public clsDataContainer sendData(clsDataContainer poData) {
		moLayer4.recvLayer5Data(poData);
		return moLayer4.getData();
	}

	@Override
	public clsDataContainer recvData(clsDataContainer poData) {
		// TODO Auto-generated method stub
		return null;
	}

	




}
