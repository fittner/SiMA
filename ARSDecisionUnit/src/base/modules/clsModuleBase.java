/**
 * clsModuleAbstract.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 */
package base.modules;

import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInterfaceDescription;
import inspector.interfaces.itfInterfaceInterfaceData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

import logger.clsLogger;
import modules.interfaces.eInterfaces;

import org.slf4j.Logger;

import base.tools.clsInfluxDB;
import properties.clsProperties;
import externalmessager.MonitorExecutor;
import externalmessager.MonitorExecutorForModuleBaseInterface;

/**
 * The base class for all functional module implementations. Provides functionality like three different implementation stages for the 
 * process method that can be selected individually (per user and per module). The three stages are: basic, draft, and final. Basic is a first 
 * implementation that provides minimum functionality. Especially it guarantees the modules that are called after this module have at least some
 * data to process. Draft is the implementation stage where alpha and beta stage implementations should be done. If everything is done and fits the 
 * specs, the implementation is moved to final. Further tools like deepcopy are provided. 
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 * 
 */
public abstract class clsModuleBase implements itfInspectorInternalState, itfInterfaceDescription, itfInterfaceInterfaceData { 
    
	/** property key where the selected implemenation stage is stored.; @since 12.07.2011 14:54:42 */
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 
	
	/** Primary or secondary function; @since 12.07.2011 14:57:12 */
	protected eProcessType mnProcessType;
	/** Id, Ego, Superego, or Body; @since 12.07.2011 14:57:27 */
	protected ePsychicInstances mnPsychicInstances;
	/** Module ID - unique among all modules of the model v38; @since 12.07.2011 14:57:40 */
	protected Integer mnModuleNumber;
		
	/** The selected implementation stage; @since 12.07.2011 14:58:42 */
	private eImplementationStage mnImplementationStage;
	/** A map that contains an instance of each functional module. The integer is equivalent to mnModuleNumber and thus unique.; @since 12.07.2011 14:58:54 */
	protected HashMap<Integer, clsModuleBase> moModuleList;
	/** The data transmitted via the interfaces in the last round.; @since 12.07.2011 14:59:37 */
	protected SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
		
	/** The description of the functional module; @since 12.07.2011 15:00:04 */
	protected String moDescription;
	
	/** List of incoming interfaces. Filled at startup by introspection.; @since 12.07.2011 15:01:47 */
	private ArrayList<eInterfaces> moInterfacesReceive;
	/** List of outgoing interfaces. Filled at startup by introspection.; @since 12.07.2011 15:01:58 */
	private ArrayList<eInterfaces> moInterfacesSend;
	/** List of all interfaces. Filled at startup by introspection.; @since 12.07.2011 15:02:08 */
	private ArrayList<eInterfaces> moInterfaces;
	
	private final MonitorExecutorForModuleBaseInterface monitor = MonitorExecutor.getMonitor();
	
	protected final Logger log;
	private final Logger logTiming;
	private final int mnAgentIndex;
	protected final clsInfluxDB InfluxDB;
	
	/**
	 * This constructor creates all functional modules with the provided properties. Further, all attributes of the module like process type, 
	 * psychic instance, module naumber, description, and interfaces list are set.
	 *
	 * @since 12.07.2011 15:11:07
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 * @param poModuleList A reference to an empty map that is filled with references to the created modules. Needed by the clsProcessor.
	 * @param poInterfaceData A reference to an empty map that is filled with data that is transmitted via the interfaces each step.
	 * @throws Exception
	 */
	public clsModuleBase(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid) throws Exception {
		setProcessType();
		setPsychicInstances();
		setModuleNumber();
		setDescription();
		setInterfacesList();
		
		mnAgentIndex = pnUid;
		
		 log = logger.clsLogger.getLog("Agent" + mnAgentIndex + ".F" + mnModuleNumber);
		 clsLogger.getLog("sim").info("Created logger " + log.getName());
		 logTiming = logger.clsLogger.getLog("Timing");
		//System.out.println("Set logger: "+ log.getName() + ", root " + log.ROOT_LOGGER_NAME);
		
		if (mnModuleNumber == null || mnModuleNumber == 0) {
			throw new java.lang.Exception("mnModuleNumber not set.");
		}
		
		moModuleList = poModuleList;
		moModuleList.put(mnModuleNumber, this);
		
		moInterfaceData = poInterfaceData;
		
		applyProperties(poPrefix, poProp);	
		
		//Create timecollectors
		//this.monitor.createProbe("F"+this.mnModuleNumber);
		
		//create connection to influxDB
		InfluxDB = new clsInfluxDB();
	}	

	/**
	 * Provides the default entries for this class. See config.clsProperties in project DecisionUnitInterface. 
	 *
	 * @since 12.07.2011 15:14:56
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @return
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
        String pre = clsProperties.addDot(poPrefix);
        
        clsProperties oProp = new clsProperties();
        oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
                
        return oProp;
	}	

	/**
	 * Applies the provided properties to the class and sets the selected implementation stage.
	 *
	 * @since 12.07.2011 15:15:03
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsProperties.
	 */
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		mnImplementationStage = eImplementationStage.valueOf(poProp.getPropertyString(pre+P_PROCESS_IMPLEMENTATION_STAGE));
		
		if(mnImplementationStage != eImplementationStage.BASIC){
			System.out.printf("Implementationstage " +mnImplementationStage+ " for module: " + pre + " \n");
		}
	}
	
	/**
	 * Execute the main functionality of the module according to the selected implementation stage (basic, draf, final).
	 *
	 * @since 12.07.2011 15:16:34
	 *
	 */
	private void process() {
		if (mnImplementationStage == eImplementationStage.BASIC) {
			process_basic();
		} else if (mnImplementationStage == eImplementationStage.DRAFT) {
			process_draft();
		} else {
			process_final();
		}
	}
	 
	/**
	 * Basic implementation stage of the process. It is a first implementation that provides minimum functionality. Especially it guarantees 
	 * that the modules that are called after this module have at least some data to process. 
	 *
	 * @since 12.07.2011 15:20:09
	 *
	 */
	protected abstract void process_basic();
	/**
	 * Draft implementation stage of the process. This is the implementation stage where alpha and beta stage implementations should be done.
	 *
	 * @since 12.07.2011 15:21:29
	 *
	 */
	protected abstract void process_draft();
	/**
	 * If everything is done and fits the specs, the implementation is moved to final.
	 *
	 * @since 12.07.2011 15:22:00
	 *
	 */
	protected abstract void process_final();
	
	/**
	 * All outgoing interfaces should be called within this method.
	 *
	 * @since 12.07.2011 15:22:27
	 *
	 */
	protected abstract void send();
	
	/**
	 * Execution of the main functionality. First, the algorithm is processed, then the results are forwarded to the successor modules.
	 *
	 * @since 12.07.2011 15:22:50
	 *
	 */
	public final void step() {
	    //Start notify probes
	    //monitor.notifyProbes("F" + this.mnModuleNumber);
	    long start = System.currentTimeMillis();
		process();
		send();
		long stop = System.currentTimeMillis();
		
		//Stop notifyProbes
		//monitor.notifyProbes("F" + this.mnModuleNumber);
		if (monitor.isActivated()==true) {
		    monitor.writeToCodelet("F" + this.mnModuleNumber, (int) (stop-start));
		}
		
	}
	
	/**
	 * Setter for mnProcessType
	 *
	 * @since 13.07.2011 11:21:16
	 *
	 */
	protected abstract void setProcessType();
	/**
	 * Setter for mnPsychicInstances
	 *
	 * @since 13.07.2011 11:21:33
	 *
	 */
	protected abstract void setPsychicInstances();	
	/**
	 * Setter for mnModuleNumber
	 *
	 * @since 13.07.2011 11:21:46
	 *
	 */
	protected abstract void setModuleNumber();

	/**
	 * Getter for mnProcessType
	 *
	 * @since 13.07.2011 11:22:14
	 *
	 * @return
	 */
	public eProcessType getProcessType() {return mnProcessType;}
	/**
	 * Getter for mnPsychicInstances
	 *
	 * @since 13.07.2011 11:22:28
	 *
	 * @return
	 */
	public ePsychicInstances getPsychicInstances() {return mnPsychicInstances;}
	/**
	 * Getter for mnModuleNumber
	 *
	 * @since 13.07.2011 11:22:45
	 *
	 * @return
	 */
	public Integer getModuleNumber() {return mnModuleNumber;}

	/**
	 * Deepcopy for arraylist objects. Clones not only the arraylist but also all elements that are stored within the arraylist.
	 *
	 * @since 13.07.2011 13:27:48
	 *
	 * @param other
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected java.util.ArrayList deepCopy(java.util.ArrayList other) {
		java.util.ArrayList clone = null;
		if (other != null) {
			clone = new java.util.ArrayList();
			
			for (Object entry:other) {
				try {
					if (!(entry instanceof Cloneable)) {
						clone.add(entry);
					} else {
						Class<?> clzz = entry.getClass();
				    	Method   meth = clzz.getMethod("clone", new Class[0]);
				    	Object   dupl = meth.invoke(entry, new Object[0]);
				    	clone.add(dupl);
					}
				} catch (Exception e) {
					clone.add(entry);
					// no deep copy possible.
				}
			}
		}
				
		return clone;
	}	
	
	/**
	 * Deepcopy for hashmap objects. Clones not only the hashmap but also all keys and entries.
	 *
	 * @since 13.07.2011 13:28:26
	 *
	 * @param other
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected java.util.HashMap deepCopy(java.util.HashMap other) {
		java.util.HashMap clone = null;
		if (other != null) {
			clone = new java.util.HashMap();
			
			Iterator i = other.keySet().iterator();
			
			while (i.hasNext()) {
				Object key = i.next();
				Object value = other.get(key);
				
				Object clone_key = null;
				Object clone_value = null;
				
				try { 
					Class<?> clzz = key.getClass();
				    Method   meth = clzz.getMethod("clone", new Class[0]);
				    clone_key = meth.invoke(key, new Object[0]);
				} catch (Exception e) {
					clone_key = key;
					// no deep copy possible.
				}				
				
				try { 
					Class<?> clzz = value.getClass();
				    Method   meth = clzz.getMethod("clone", new Class[0]);
				    clone_value = meth.invoke(value, new Object[0]);
				} catch (Exception e) {
					clone_value = value;
					// no deep copy possible.
				}		

				clone.put(clone_key, clone_value);
			}
		}
				
		return clone;
	}
	
	/**
	 * Adds the data that is transmitted via an interface to a list that can be used for logging and debugging purposes.
	 *
	 * @since 13.07.2011 13:31:05
	 *
	 * @param poInterface
	 * @param poData
	 */
	protected void putInterfaceData(@SuppressWarnings("rawtypes") Class poInterface, Object... poData) {
		eInterfaces nI = eInterfaces.getEnum(poInterface.getSimpleName());
		
		putInterfaceData(nI, poData);
	}
	
	/**
	 * Adds the data that is transmitted via an interface to a list that can be used for logging and debugging purposes.
	 *
	 * @since 13.07.2011 13:31:48
	 *
	 * @param pnInterface
	 * @param poData
	 */
	protected void putInterfaceData(eInterfaces pnInterface, Object... poData) {
		ArrayList<Object> oData = new ArrayList<Object>();
		for (Object d:poData) {
			if (d==null) {
				throw new java.lang.NullPointerException("clsModuleBase.puInterfaceData: null is not valid value to be transmitted via the interfaces between the funcitonal modules.");
			}
			oData.add(d);
		}
		
		moInterfaceData.put(pnInterface, oData);
	}
	
	/* (non-Javadoc)
	 *
	 * @since 13.07.2011 13:32:08
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return moDescription;
	}
	
	/**
	 * Setter for moDescription.
	 *
	 * @since 13.07.2011 13:32:12
	 *
	 */
	public abstract void setDescription();
	
	/**
	 * Provides a list of all interfaces that are implemented in this class that end with the provided suffix. Typical usage is 
	 * to extract the list of all outgoing interfaces by using the param "_send".
	 *
	 * @since 13.07.2011 13:34:42
	 *
	 * @param oSuffix
	 * @return
	 */
	protected ArrayList<eInterfaces> getInterfacesFilter(String oSuffix) {
		ArrayList<eInterfaces> oResult = new ArrayList<eInterfaces>();
		for (@SuppressWarnings("rawtypes") Class oI:this.getClass().getInterfaces()) {
			if (oI.getSimpleName().endsWith(oSuffix)) {
				oResult.add(eInterfaces.getEnum(oI.getSimpleName()));
			}
		}
		return oResult;
	}
	
	/**
	 * Creates and fills the list that contains all outgoing and incoming interfaces of this functional module. Three lists are filled: outgoing 
	 * (moInterfacesSend), incoming (moInterfacesReceive), and both (moInterfaces). They do not contain the data that is transmitted. For this see moIntefaceData.
	 * 
	 *
	 * @since 13.07.2011 13:37:40
	 *
	 */
	protected void setInterfacesList() {
		moInterfacesSend = getInterfacesFilter("_send");
		moInterfacesReceive = getInterfacesFilter("_receive");
		
		moInterfaces = new ArrayList<eInterfaces>();
		moInterfaces.addAll(moInterfacesReceive);
		moInterfaces.addAll(moInterfacesSend);
	}
	
	/* (non-Javadoc)
	 *
	 * @since 13.07.2011 13:42:45
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return moInterfacesSend;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 13.07.2011 13:42:49
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return moInterfacesReceive;
	}	
	
	/**
	 * Getter for moInterfaces.
	 *
	 * @since 13.07.2011 13:42:51
	 *
	 * @return
	 */
	public ArrayList<eInterfaces> getInterfaces() {
		return moInterfaces;
	}
	
	protected int getAgentIndex() {
	    return mnAgentIndex;
	}
}
