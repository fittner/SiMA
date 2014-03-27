package communication.layer3.interfaces;

import commication.exceptions.exNeigbourLayerNullPointerException;
import communication.datatypes.clsDataContainer;

public interface itfLayer3 {
	public String recvLayer1Data(String data);
	public clsDataContainer recvLayer4Data(clsDataContainer data);
}
