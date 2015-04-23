package communication.layer3.implementations;

import java.util.ArrayList;
import java.util.HashMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.layer2.interfaces.itfLayer2;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;

public class clsBufferContainer implements itfLayer3 {
	
	HashMap<String,clsBufferBase> moBuffers = new HashMap<String,clsBufferBase>();
	private itfLayer2 moLayer3;
	private itfLayer4 moLayer4;

	public void setLayer3(itfLayer2 moLayer3) {
		this.moLayer3= moLayer3;
	}

	public void setLayer4(itfLayer4 moLayer4) {
		this.moLayer4 = moLayer4;
	}
	
	
	@Override
	public clsDataContainer recvLayer2Data(clsDataContainer poData) {
		sendToBuffer(poData);
		return moLayer4.recvLayer3Data();
	}


	@Override
	public void recvLayer4Data(clsDataContainer poData) {
		clsDataContainer oRetData = moLayer3.recvLayer3Data(poData);
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
				//1) check if buffertype is attached
				String bufferType = oData.getBufferType();
				clsBufferBase oBuffer;
				if(bufferType.equals("EVENT")){
					oBuffer = new clsEventBuffer();

				}else if (bufferType.equals("SIGNAL")){
					oBuffer = new clsSignalBuffer();

				}else{
					//ADD Default Buffer
					oBuffer = new clsEventBuffer();
				}
				
				oBuffer.put(oData);
				moBuffers.put(oData.getType(), oBuffer);
			}
		

	}




}
