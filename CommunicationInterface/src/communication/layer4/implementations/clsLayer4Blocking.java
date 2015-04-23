package communication.layer4.implementations;

import communication.datatypes.clsDataContainer;
import communication.interfaces.itfCommunicationPartner;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;

public class clsLayer4Blocking implements itfLayer4{

	private itfLayer3 moLayer3;
	private itfCommunicationPartner moCommunicationPartner;


	public void setLayer3(itfLayer3 moLayer3) {
		this.moLayer3= moLayer3;
	}

	public void setCommunicationPartner(itfCommunicationPartner moCommunicationPartner) {
		this.moCommunicationPartner = moCommunicationPartner;
	}
	
	@Override
	public clsDataContainer recvLayer3Data() {
		clsDataContainer oData = moLayer3.getData();
		clsDataContainer oRetVal =moCommunicationPartner.recvData(oData);
		return oRetVal;
	}

	@Override
	public clsDataContainer sendData(clsDataContainer poData) {
		moLayer3.recvLayer4Data(poData);
		return moLayer3.getData();
	}

	@Override
	public clsDataContainer recvData(clsDataContainer poData) {
		// TODO Auto-generated method stub
		return null;
	}

	




}
