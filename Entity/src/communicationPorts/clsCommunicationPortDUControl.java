/**
 * CHANGELOG
 *
 * 15.11.2013 LuHe - File created
 *
 */
package communicationPorts;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartner;
import complexbody.brainsocket.clsBrainSocket;

import base.clsCommunicationInterface;

/**
 * DOCUMENT (herret) - Defines hold the data and the control interfaces to the DU
 * 
 * @author LuHe
 * 15.11.2013, 09:20:01
 * 
 */
public class clsCommunicationPortDUControl implements itfCommunicationPartner{

    clsCommunicationInterface moControlInterface;
    clsBrainSocket moBrainSocket;
    
    public clsCommunicationPortDUControl(clsBrainSocket poBrainSocket){

        moBrainSocket    = poBrainSocket;
    }
    
    public void setCommunicationInterface(clsCommunicationInterface poInterface){
        moControlInterface = poInterface;
        moControlInterface.setCommunicationPartner(this);
    }

    /* (non-Javadoc)
     *
     * @since 15.11.2013 09:27:02
     * 
     * @see communication.interfaces.itfCommunicationPartner#recvData(communication.datatypes.clsDataContainer)
     */
    @Override
    public clsDataContainer recvData(clsDataContainer poData) {
        clsDataContainer oRetVal = new clsDataContainer();

       oRetVal.addDataPoint(new clsDataPoint("STATUSCODE","ERROR"));

        return oRetVal;
    }
    
    public boolean stepDU(){
    	clsDataContainer oData = new clsDataContainer();
    	oData.addDataPoint(new clsDataPoint("COMMAND","PROCESS"));
    			
    	clsDataContainer oRetData = moControlInterface.sendData(oData);
    	return true;
    	
    }
    
}
