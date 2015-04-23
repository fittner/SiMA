package externalmessager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.slf4j.Logger;


public class DatapointHandler implements DatapointHandlerForCodeletInterface, DatapointHandlerForClientInterface {	
	
	private HashMap<String, Datapoint<?>> datapointList = new HashMap<String, Datapoint<?>>();
	
	private HashMap<String, DatapointClientInterface> codeletList = new HashMap<String, DatapointClientInterface>();
	private HashMap<String, HashMap<String, DatapointClientSubscriberContainerInterface>> subscriberList = new HashMap<String, HashMap<String, DatapointClientSubscriberContainerInterface>>();
	private HashMap<String, Object> sharedVariables = new HashMap<String, Object>();
	
	private static final Logger	log	= logger.clsLogger.getLog("Timing");
	
	
	public DatapointHandler() {
		
		Thread.currentThread().setName("DatapointHandler");
	}
	

	@Override
	public synchronized void registerCodelet(DatapointClientInterface codelet) throws Exception {
		//Name has to be unique
		if (this.codeletList.get(codelet.getCodeletName())!=null) {
			throw new Exception("codelet " + codelet.getCodeletName() + " already registered");
		}
		this.codeletList.put(codelet.getCodeletName(), codelet);
		log.info("Codelet {} has registered", codelet.getCodeletName());
		
	}


	@Override
	public synchronized void unregisterCodelet(DatapointClientInterface codelet) {
		this.codeletList.remove(codelet.getCodeletName());
		log.info("Codelet {} has unregistered", codelet.getCodeletName());
		
	}


	@Override
	public synchronized void subscribeDatapoints(ArrayList<Datapoint<?>> datapointNames, DatapointClientInterface codelet) {
		ArrayList<String> dataPointAddress = new ArrayList<String>();
		for (Datapoint dp :datapointNames) {
			dataPointAddress.add(dp.getName());
		}
		
		//Add subscribe paths to subscribelist
		//Check if path exists
		for (String subscriberdp : dataPointAddress) {
			//create subscriber 
			DatapointClientSubscriberContainerInterface subscriber = new DatapointClientSubscriberContainer(codelet);
			if (this.subscriberList.containsKey(subscriberdp)==true) {
				//Add subscriber to list (and replace the old one)
				this.subscriberList.get(subscriberdp).put(subscriber.getCodeletName(), subscriber);
			} else {
				//Create new sunscriberlist
				this.subscriberList.put(subscriberdp, new HashMap<String, DatapointClientSubscriberContainerInterface>());
				this.subscriberList.get(subscriberdp).put(subscriber.getCodeletName(), subscriber);
			}
		}
	}


	@Override
	public synchronized void unsubscribeDatapoints(ArrayList<Datapoint<?>> datapoints, DatapointClientInterface codelet) {
		//TODO AW
		
	}


	@Override
	public synchronized boolean writeDatapoints(ArrayList<Datapoint<?>> datapoints) throws Exception {
	    boolean successfulWrite = true;
		for (Datapoint<?> dp : datapoints) {
		    this.datapointList.put(dp.getName(), dp);
		}
		
		return successfulWrite;
	}


	@Override
	public synchronized ArrayList<Datapoint<?>> readDatapoints(ArrayList<Datapoint<?>> datapoints) {
	    ArrayList<Datapoint<?>> result = new ArrayList<Datapoint<?>>();
	    
		for (Datapoint<?> dp :datapoints) {
		    Datapoint<?> dpFound = this.datapointList.get(dp.getName());
		    if (dpFound!=null) {
		        result.add(dpFound);
		    } else {
		        try {
		            throw new Exception("Datapoint " + dp.getName() + " not found. ");
		        } catch (Exception e) {
		            log.warn("Datapoint not found {}",e);
		        }
		    }
		}
		
		return result;
	}
	
    /* (non-Javadoc)
    *
    * @since 03.01.2014 10:55:49
    * 
    * @see externalmessager.CodeletHandlerForClientInterface#updateSubscribedValue(externalmessager.Datapoint)
    */
	@Override
	public synchronized void updateSubscribedValue(Datapoint<?> datapoint) {
       //Update the datapointlist of the server
       this.datapointList.put(datapoint.getName(), datapoint);
       log.trace("Set datapoint {}", datapoint);
       
//       //Notify subscriber    
         SubscriberNotifier notifier = new SubscriberNotifier(this, datapoint);
         notifier.start();
//       ArrayList<DatapointClientSubscriberContainerInterface> affectedSubscribers = new ArrayList<DatapointClientSubscriberContainerInterface>();
//       if (this.subscriberList.containsKey(datapoint.getName())==true) {
//           Collection<DatapointClientSubscriberContainerInterface> subscriberCodelets = this.subscriberList.get(datapoint.getName()).values();
//           
//           //Update subscribercodelets
//           for (DatapointClientSubscriberContainerInterface codelet : subscriberCodelets) {
//               //Add datapoint to codelet
//               codelet.addDatapoint(datapoint);
//               //Add codelet to list if not existent
//               if (affectedSubscribers.contains(codelet)==false) {
//                   affectedSubscribers.add(codelet);
//               }
//           }
//       } else {
////           try {
////               throw new Exception("The datapoint " + datapoint + " is not subscribed by any codelet");
////           } catch (Exception e) {
////               log.warn("Datapoint not subscribed", e);
////           }
//       }
//       
//       //Send all datapoints to the codelets
//       for (DatapointClientSubscriberContainerInterface codelet : affectedSubscribers) {
//           codelet.sendDatapointListToCodelet();
//       }
	}
   
	public synchronized void notifySubscriber(Datapoint<?> datapoint) {
	    //Notify subscriber       
	       ArrayList<DatapointClientSubscriberContainerInterface> affectedSubscribers = new ArrayList<DatapointClientSubscriberContainerInterface>();
	       if (this.subscriberList.containsKey(datapoint.getName())==true) {
	           Collection<DatapointClientSubscriberContainerInterface> subscriberCodelets = this.subscriberList.get(datapoint.getName()).values();
	           
	           //Update subscribercodelets
	           for (DatapointClientSubscriberContainerInterface codelet : subscriberCodelets) {
	               //Add datapoint to codelet
	               codelet.addDatapoint(datapoint);
	               //Add codelet to list if not existent
	               if (affectedSubscribers.contains(codelet)==false) {
	                   affectedSubscribers.add(codelet);
	               }
	           }
	       }
	       
	       //Send all datapoints to the codelets
	       for (DatapointClientSubscriberContainerInterface codelet : affectedSubscribers) {
	           codelet.sendDatapointListToCodelet();
	       }
	}
	
	@Override
	public synchronized void updateSubscribedValues(ArrayList<Datapoint<?>> datapointList) {
		ArrayList<DatapointClientSubscriberContainerInterface> affectedSubscribers = new ArrayList<DatapointClientSubscriberContainerInterface>();
		
		for (Datapoint<?> dp : datapointList) {
		    //Update the datapointlist of the server
		    this.datapointList.put(dp.getName(), dp);
			
		    //Notify subscribers
		    if (this.subscriberList.containsKey(dp.getName())==true) {
				Collection<DatapointClientSubscriberContainerInterface> subscriberCodelets = this.subscriberList.get(dp.getName()).values();
				
				//Update subscribercodelets
				for (DatapointClientSubscriberContainerInterface codelet : subscriberCodelets) {
					//Add datapoint to codelet
					codelet.addDatapoint(dp);
					//Add codelet to list if not existent
					if (affectedSubscribers.contains(codelet)==false) {
						affectedSubscribers.add(codelet);
					}
				}
			} else {
				try {
					throw new Exception("The datapoint " + dp + " is not subscribed by any codelet");
				} catch (Exception e) {
					log.warn("Datapoint not subscribed", e);
				}
			}
		}

		//Send all datapoints to the codelets
		for (DatapointClientSubscriberContainerInterface codelet : affectedSubscribers) {
			codelet.sendDatapointListToCodelet();
		}
	}


	@Override
	public synchronized void setSharedVariable(String key, Object value) {
		this.sharedVariables.put(key, value);
		log.trace("Updated shared variable {}.", key);
		
	}

	@Override
	public Object getSharedValue(String key) {
		Object o = this.sharedVariables.get(key);
		return o;
	}

}
