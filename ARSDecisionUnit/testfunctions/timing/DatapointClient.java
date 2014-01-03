package timing;

import java.util.ArrayList;
import java.util.HashMap;

import logger.clsLogger;

import org.slf4j.Logger;

import externalmessager.DatapointClientInterface;
import externalmessager.Datapoint;
import externalmessager.DatapointHandlerForCodeletInterface;

public abstract class DatapointClient extends Thread implements DatapointClientInterface {
	protected final DatapointHandlerForCodeletInterface handler;
	private final String codeletName;
	
	private HashMap<String, Datapoint<?>> subscribedDatapoints = new HashMap<String, Datapoint<?>>();
	
	private boolean isActive = true;
	private boolean executeOnce = true;
	private int updateRate = 1000;
	
	protected static final Logger	log	= clsLogger.getLog("Timing");
	
	public DatapointClient(DatapointHandlerForCodeletInterface handler, String name) {
		this.handler = handler;
		this.codeletName = name;
		Thread.currentThread().setName(codeletName);
		log.info("=== Initialize codelet {} ===", this.codeletName);
		
	}
	
	protected void init(String subscribeDatapointName) {	//Init in constructor of child
		//Set properties
	    Datapoint<Integer> subscribeDatapoint = new Datapoint<Integer>(subscribeDatapointName);
	    this.subscribedDatapoints.put(subscribeDatapoint.getName(), subscribeDatapoint);
		
		
		//Subscribe datapoints
		ArrayList<Datapoint<?>> subscribeAddress =  new ArrayList<Datapoint<?>>(this.subscribedDatapoints.values());
		//subscribeAddress.add(commandDatapoint);
		try {
			if (subscribeAddress.isEmpty()==false) {
				this.handler.subscribeDatapoints(subscribeAddress, this);
				log.info("Codelet {} subscribed datapoints {}", this.codeletName, subscribeAddress);
			}
		} catch (Exception e) {
			log.error("Could not subscribe to datapoints");
		}
		
//		//Read initial value
//		try {
//			ArrayList<Datapoint<?>> readValues = this.handler.readDatapoints(subscribeAddress);
//			putDatapointsIntoHashMap(readValues, this.subscribedDatapoints);
//
//			log.debug("Codelet {} read initial values of the datapoints {}", this.codeletName, subscribeAddress);
//		} catch (Exception e) {
//			log.error("Could not read initial values of datapoints");
//		}
	}
	
	protected abstract void executeProgram() throws Exception;
	
	protected abstract void triggerEventAtInputDatapointsChanged(); 
	
		
	@Override
    public void run() {
		log.debug("Start codelet {}", this.codeletName);
		while(isActive==true) {
			//Stop the system at the end of the turn, if STOP command has been given
			executeWait();
			
			try {
				executeProgram();
			} catch (Exception e1) {
				log.error("Error in program execution", e1);
			}
			
			if (this.executeOnce==false) {
				try {
					sleep(updateRate);
				} catch (InterruptedException e) {
					log.warn("Sleep was interrupted", e);
				}
			}
		}
		
		log.debug("Stop codelet {}", this.codeletName);
	}
	
	private synchronized void executeWait() {
		try {
			//Block profile controller
			this.wait();
		} catch (InterruptedException e) {
			log.trace("Wait interrupted client");
		}
	}

	public boolean isActive() {
		return isActive;
	}


	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	public boolean isExecuteOnceSet() {
		return executeOnce;
	}
	
	@Override
    public void updateSubscribedValues(ArrayList<Datapoint<?>> datapointList) throws Exception {
		//Update input hashmap
		try {
			putDatapointsIntoHashMap(datapointList, this.subscribedDatapoints);
		} catch (Exception e) {
			log.error("Could not write values to hashmap", e);
		}
		triggerEventAtInputDatapointsChanged();
	}
	
	private void putDatapointsIntoHashMap(ArrayList<Datapoint<?>> datapointList, HashMap<String, Datapoint<?>> hashMap) {
		for (Datapoint<?> dp:  datapointList) {
			if (hashMap.containsKey(dp.getName())==true) {
			    hashMap.put(dp.getName(), dp);
			}
		}
	}

	protected boolean isExecuteOnce() {
		return executeOnce;
	}

	protected void setExecuteOnce(boolean executeOnce) {
		this.executeOnce = executeOnce;
	}

	@Override
    public String getCodeletName() {
		return codeletName;
	}

	protected HashMap<String, Datapoint<?>> getInputDatapoints() {
		return subscribedDatapoints;
	}

	protected int getUpdateRate() {
		return updateRate;
	}

	protected void setUpdateRate(int updateRate) {
		this.updateRate = updateRate;
	}

}
