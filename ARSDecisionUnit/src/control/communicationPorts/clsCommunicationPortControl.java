/**
 * CHANGELOG
 *
 * 15.11.2013 LuHe - File created
 *
 */
package control.communicationPorts;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import communication.interfaces.itfCommunicationPartnerThread;
import control.interfaces.clsBaseDecisionUnit;

import base.clsCommunicationInterface;

/**
 * DOCUMENT (herret) - Defines hold teh data and the control interfaces to the vody
 * 
 * @author LuHe
 * 15.11.2013, 09:20:01
 * 
 */
public class clsCommunicationPortControl extends Thread implements itfCommunicationPartnerThread {

    clsCommunicationInterface moControlInterface;
    clsBaseDecisionUnit moDU;
    
    public clsCommunicationPortControl(clsBaseDecisionUnit poDU){

        moDU    = poDU;
    }

    public void setControlInterface(clsCommunicationInterface moControlInterface) {
		this.moControlInterface = moControlInterface;
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
        for(clsDataPoint oCommand :poData.getData()){
            if(oCommand.getType().equals("COMMAND")){
                boolean executed = false;
                if(oCommand.getValue().equals("PROCESS")){
                    moDU.process();
                    executed=true;

                }
                
                if(executed){
                    oCommand.setType("ECECUTED_COMMAND");
                    oRetVal.addDataPoint(oCommand);
                }
                
            }
            
        }
        return oRetVal;
    }
    
    
    @Override
	public void run(){
    	try {
			this.wait();
		} catch (InterruptedException e) {
			
			 clsDataContainer poData = moControlInterface.recvData(null);
		        for(clsDataPoint oCommand :poData.getData()){
		            if(oCommand.getType().equals("COMMAND")){
		                boolean executed = false;
		                if(oCommand.getValue().equals("PROCESS")){
		                    moDU.process();
		                    executed=true;

		                }
      
		            }
		            
		        }
			
		}
    	
    }

	/* (non-Javadoc)
	 *
	 * @since 27.01.2014 08:31:27
	 * 
	 * @see communication.interfaces.itfCommunicationPartnerThread#newDataAvailable()
	 */
	@Override
	public void newDataAvailable() {
		this.interrupt();
		
	}
    
}
