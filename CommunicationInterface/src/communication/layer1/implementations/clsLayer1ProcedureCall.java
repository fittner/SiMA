package communication.layer1.implementations;

import communication.layer1.interfaces.itfLayer1;
import communication.layer3.interfaces.itfLayer3;

public class clsLayer1ProcedureCall implements itfLayer1 {

	clsLayer1ProcedureCall moCommunicationPartner;
	itfLayer3 moLayer3;
	
	
	
	public void setMoLayer3(itfLayer3 moLayer3) {
		this.moLayer3 = moLayer3;
	}

	public void setCommunicationPartner(clsLayer1ProcedureCall moCommunicationPartner) {
		this.moCommunicationPartner = moCommunicationPartner;
	}
	
	public String recvCommunicationPartnerData(String data){
		return moLayer3.recvLayer1Data(data);
		
	}

	@Override
	public String recvLayer3Data(String data) {
		return moCommunicationPartner.recvCommunicationPartnerData(data);
	}

}
