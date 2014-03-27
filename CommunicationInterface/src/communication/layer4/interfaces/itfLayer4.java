package communication.layer4.interfaces;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

public interface itfLayer4 {

	public clsDataContainer recvLayer3Data(clsDataContainer poData);
	public void recvLayer5Data(clsDataContainer poData);
	public clsDataContainer getData();
	public clsDataContainer getInputBuffer(String poInputBufferName);
	
}
