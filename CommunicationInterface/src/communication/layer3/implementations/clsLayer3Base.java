package communication.layer3.implementations;

import java.util.ArrayList;

import commication.exceptions.exNeigbourLayerNullPointerException;
import communication.datatypes.clsDataContainer;
import communication.layer1.interfaces.itfLayer1;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;

public abstract class clsLayer3Base implements itfLayer3{

	
	private itfLayer1 moLayer1;
	private itfLayer4 moLayer4;
	
	

	@Override
	public String recvLayer1Data(String poData) {
		clsDataContainer oData = processRecv(poData);
		clsDataContainer oRetVal = moLayer4.recvLayer3Data(oData);
		String oProcessedRetVal = processSend(oRetVal);
		return oProcessedRetVal;
		
	}

	@Override
	public clsDataContainer recvLayer4Data(clsDataContainer poData) {
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

	public void setLayer4(itfLayer4 moLayer4) {
		this.moLayer4 = moLayer4;
	}

}
