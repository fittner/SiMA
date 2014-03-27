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
import decisionunit.clsBaseDecisionUnit;
import base.clsCommunicationInterface;

/**
 * DOCUMENT (herret) - Defines hold teh data and the control interfaces to the vody
 * 
 * @author LuHe
 * 15.11.2013, 09:20:01
 * 
 */
public class clsCommunicationPortBodyData implements itfCommunicationPartner{

    clsCommunicationInterface moDataInterface;
    clsBaseDecisionUnit moDU;
    
    public clsCommunicationPortBodyData(clsBaseDecisionUnit poDU){
        moDU = poDU;
    }
    
    public void setCommunicationInterface(clsCommunicationInterface poInterface){
        moDataInterface = poInterface;
        moDataInterface.setCommunicationPartner(this);

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
        oRetVal.addDataPoint(new clsDataPoint("STATUSCODE","OK"));
        return oRetVal;
    }
    
    public clsDataContainer getSensorData(){
        return moDataInterface.recvData(null);
    }
    
    public void sendToBody(clsDataContainer poActionCommands, clsDataContainer poInternalActionCommands){
        clsDataContainer oSendData = new clsDataContainer();
        clsDataPoint oExt = new clsDataPoint("ACTION_COMMAND_LIST_EXTERNAL","");
        for (clsDataPoint oDataPoint : poActionCommands.getData()){
        	oExt.addAssociation(oDataPoint);
        }
        oSendData.addDataPoint(oExt);
        clsDataPoint oInt = new clsDataPoint("ACTION_COMMAND_LIST_INTERNAL","");
        for (clsDataPoint oDataPoint : poInternalActionCommands.getData()){
        	oInt.addAssociation(oDataPoint);
        }
        oSendData.addDataPoint(oInt);
        moDataInterface.sendData(oSendData);
        return;
    }
    

    
}
