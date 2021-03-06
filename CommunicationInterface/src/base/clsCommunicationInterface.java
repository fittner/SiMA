package base;

import communication.datatypes.clsDataContainer;
import communication.interfaces.itfCommunicationPartner;
import communication.layer1.interfaces.itfLayer1;
import communication.layer2.interfaces.itfLayer2;
import communication.layer3.interfaces.itfLayer3;
import communication.layer4.interfaces.itfLayer4;

public class clsCommunicationInterface {

	private itfLayer4 oLayer5;
	private itfLayer3 oLayer4;
	private itfLayer2 oLayer3;
	
	private itfLayer1 oLayer1;
	
	public clsCommunicationInterface(itfLayer1 layer1, itfLayer2 layer3, itfLayer3 layer4, itfLayer4 layer5){
		oLayer1 = layer1;
		oLayer3 = layer3;
		oLayer4 = layer4;
		oLayer5 = layer5;
	}
	public itfLayer4 getLayer5() {
		return oLayer5;
	}
	public void setLayer5(itfLayer4 oLayer5) {
		this.oLayer5 = oLayer5;
		 
	}
	public itfLayer1 getLayer1() {
		return oLayer1;
	}
	public void setLayer1(itfLayer1 oLayer1) {
		this.oLayer1 = oLayer1;
	}
	public itfLayer3 getLayer4() {
		return oLayer4;
	}
	public void setLayer4(itfLayer3 oLayer4) {
		this.oLayer4 = oLayer4;
	}
	public itfLayer2 getLayer3() {
		return oLayer3;
	}
	public void setLayer3(itfLayer2 oLayer3) {
		this.oLayer3 = oLayer3;
	}
	
	public void setCommunicationPartner(itfCommunicationPartner poPartner){
		oLayer5.setCommunicationPartner(poPartner);
	}
	
	
	public clsDataContainer sendData(clsDataContainer poData){
		return oLayer5.sendData(poData);
	}
	public clsDataContainer recvData(clsDataContainer poData){
		return oLayer5.recvData(poData);
	}
	
	
	
	
	
	
}
