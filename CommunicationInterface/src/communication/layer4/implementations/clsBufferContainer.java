package communication.layer4.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;
import communication.layer5.interfaces.itfLayer5;

public class clsBufferContainer implements itfLayer4 {
	
	HashMap<String,clsBufferBase> moBuffers = new HashMap<String,clsBufferBase>();
	private itfLayer3 moLayer3;
	private itfLayer5 moLayer5;

	public void setLayer3(itfLayer3 moLayer3) {
		this.moLayer3= moLayer3;
	}

	public void setLayer5(itfLayer5 moLayer5) {
		this.moLayer5 = moLayer5;
	}
	
	
	@Override
	public clsDataContainer recvLayer3Data(clsDataContainer poData) {
		sendToBuffer(poData);
		return moLayer5.recvLayer4Data();
	}


	@Override
	public void recvLayer5Data(clsDataContainer poData) {
		clsDataContainer oRetData = moLayer3.recvLayer4Data(poData);
		sendToBuffer(oRetData);
		return;
	}


	@Override
	public clsDataContainer getInputBuffer(String poBufferName) {
		clsDataContainer oRetVal = new clsDataContainer();
		if(moBuffers.containsKey(poBufferName)){
			for(clsDataPoint oDataPoint : moBuffers.get(poBufferName).getData()){
				oRetVal.addDataPoint(oDataPoint);
			}
		}
		return oRetVal;
	}
	
	@Override
	public clsDataContainer getData(){
		//load from buffer
		clsDataContainer oRetVal= new clsDataContainer();
		
		for(clsBufferBase oBuffer :moBuffers.values()){
			for(clsDataPoint oDataPoint : oBuffer.getData()){
				oRetVal.addDataPoint(oDataPoint);
			}
		}
		
		return oRetVal;
	}
	
	public void addBuffer (String poName, clsBufferBase poBuffer){
		moBuffers.put(poName, poBuffer);
	}
	
	protected ArrayList<clsDataContainer> processSend(ArrayList<clsDataContainer> poInputData) {
		// there is no buffering in Send Direction
		return poInputData;
	}


	private void sendToBuffer(clsDataContainer poInputData) {
		//save input data to the buffers
			for(clsDataPoint oData :poInputData.getData()){
				if(moBuffers.containsKey(oData.getType())){
					moBuffers.get(oData.getType()).put(oData);
				}
				//if no Buffer is found create default Buffer
				clsBufferBase oBuffer = new clsEventBuffer();
				oBuffer.put(oData);
				moBuffers.put(oData.getType(), oBuffer);
			}
		

	}




}
