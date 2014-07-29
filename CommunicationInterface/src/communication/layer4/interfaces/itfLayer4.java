package communication.layer4.interfaces;

import communication.datatypes.clsDataContainer;
import communication.interfaces.itfCommunicationPartner;

public interface itfLayer4 {

	public clsDataContainer recvLayer3Data();
	public clsDataContainer sendData(clsDataContainer poData);
	public clsDataContainer recvData(clsDataContainer poData);
	public void setCommunicationPartner(itfCommunicationPartner poPartner);
}
