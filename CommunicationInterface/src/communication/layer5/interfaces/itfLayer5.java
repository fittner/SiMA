package communication.layer5.interfaces;

import communication.datatypes.clsDataContainer;
import communication.interfaces.itfCommunicationPartner;

public interface itfLayer5 {

	public clsDataContainer recvLayer4Data();
	public clsDataContainer sendData(clsDataContainer poData);
	public clsDataContainer recvData(clsDataContainer poData);
	public void setCommunicationPartner(itfCommunicationPartner poPartner);
}
