package communication.layer2.implementations;

import java.util.ArrayList;

import commication.exceptions.exNeigbourLayerNullPointerException;
import communication.datatypes.clsDataContainer;
import communication.layer1.interfaces.itfLayer1;
import communication.layer2.interfaces.itfLayer2;
import communication.layer3.interfaces.itfLayer3;

public abstract class clsLayer2Base implements itfLayer2{

	
	private itfLayer1 moLayer1;
	private itfLayer3 moLayer3;
	
	

	@Override
	public String recvLayer1Data(String poData) {
		clsDataContainer oData = processRecv(poData);
		clsDataContainer oRetVal = moLayer3.recvLayer2Data(oData);
		String oProcessedRetVal = processSend(oRetVal);
		return oProcessedRetVal;
		
	}

	@Override
	public clsDataContainer recvLayer3Data(clsDataContainer poData) {
		String oData = processSend(poData);
		String oRetVal = moLayer1.recvLayer3Data(oData);
		clsDataContainer oProcessedRetVal = processRecv(oRetVal);
		return oProcessedRetVal;
	
	}
	

	
	protected abstract String processSend(clsDataContainer poInputData);
	protected abstract clsDataContainer processRecv(String poInputData);

	public void setLayer1(itfLayer1 moLayer1) {
		this.moLayer1 = moLayer1;
	}

	public void setLayer3(itfLayer3 moLayer3) {
		this.moLayer3 = moLayer3;
	}

}
