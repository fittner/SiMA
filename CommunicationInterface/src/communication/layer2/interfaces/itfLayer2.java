package communication.layer2.interfaces;

import commication.exceptions.exNeigbourLayerNullPointerException;
import communication.datatypes.clsDataContainer;

public interface itfLayer2 {
	public String recvLayer1Data(String data);
	public clsDataContainer recvLayer3Data(clsDataContainer data);
}
