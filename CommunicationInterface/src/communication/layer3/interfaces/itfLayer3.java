package communication.layer3.interfaces;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

public interface itfLayer3 {

	public clsDataContainer recvLayer2Data(clsDataContainer poData);
	public void recvLayer4Data(clsDataContainer poData);
	public clsDataContainer getData();
	public clsDataContainer getInputBuffer(String poInputBufferName);
	
}
