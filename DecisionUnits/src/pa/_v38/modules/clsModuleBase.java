/**
 * clsModuleAbstract.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 */
package pa._v38.modules;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.itfInterfaceInterfaceData;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import config.clsBWProperties;

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
public abstract class clsModuleBase implements 
		itfInspectorInternalState, 
		itfInterfaceDescription, 
		itfInterfaceInterfaceData {
	
	/** property key where the selected implemenation stage is stored.; @since 12.07.2011 14:54:42 */
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 
	
	/** Primary or secondary function; @since 12.07.2011 14:57:12 */
	protected eProcessType mnProcessType;
	/** Id, Ego, Superego, or Body; @since 12.07.2011 14:57:27 */
	protected ePsychicInstances mnPsychicInstances;
	/** Module ID - unique among all modules of the model v38; @since 12.07.2011 14:57:40 */
	protected Integer mnModuleNumber;
		
	/** Variable for testing purposes.; @since 12.07.2011 14:58:06 */
	@Deprecated
	protected int mnTest = 0;
	
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
	
	/**
	 * This constructor creates all functional modules with the provided properties. Further, all attributes of the module like process type, 
	 * psychic instance, module naumber, description, and interfaces list are set.
	 *
	 * @since 12.07.2011 15:11:07
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsBWProperties.
	 * @param poModuleList A reference to an empty map that is filled with references to the created modules. Needed by the clsProcessor.
	 * @param poInterfaceData A reference to an empty map that is filled with data that is transmitted via the interfaces each step.
	 * @throws Exception
	 */
	public clsModuleBase(String poPrefix, clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		setProcessType();
		setPsychicInstances();
		setModuleNumber();
		setDescription();
		setInterfacesList();
		
		if (mnModuleNumber == null || mnModuleNumber == 0) {
			throw new java.lang.Exception("mnModuleNumber not set.");
		}
		
		moModuleList = poModuleList;
		moModuleList.put(mnModuleNumber, this);
		
		moInterfaceData = poInterfaceData;
		
		applyProperties(poPrefix, poProp);		
	}	

	/**
	 * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface. 
	 *
	 * @since 12.07.2011 15:14:56
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @return
	 */
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	

	/**
	 * Applies the provided properties to the class and sets the selected implementation stage.
	 *
	 * @since 12.07.2011 15:15:03
	 *
	 * @param poPrefix Prefix for the property-entries in the property file.
	 * @param poProp The property file in form of an instance of clsBWProperties.
	 */
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mnImplementationStage = eImplementationStage.valueOf(poProp.getPropertyString(pre+P_PROCESS_IMPLEMENTATION_STAGE));	
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
		process();
		send();
	}
	
	protected abstract void setProcessType();
	protected abstract void setPsychicInstances();	
	protected abstract void setModuleNumber();

	public eProcessType getProcessType() {return mnProcessType;}
	public ePsychicInstances getPsychicInstances() {return mnPsychicInstances;}
	public Integer getModuleNumber() {return mnModuleNumber;}
	
	//AW 20110521: new deepcopy function for single objects
	protected Object deepCopy(Object other) {
		Object clone = null;
		if (other != null) {
			clone = new Object();
		}
		
		try {
			if (!(other instanceof Cloneable)) {
				clone = other;	//not cloneable
			} else {
				//FIXME: AW 20110521: How are relative references kept? 
				// Before: Associated Datastructures ElementA = ID123, ElementB = ID122, Datastructure: ID123
				// After: Associated Datastructures ElementA = ID999, ElementB = ID888, Datastructure: ID777
				Class<?> clzz = other.getClass();
				Method   meth = clzz.getMethod("clone", new Class[0]);
				Object   dupl = meth.invoke(other, new Object[0]);
				clone = dupl;
				//clone.add(dupl);
			}
		} catch (Exception e) {
			//clone.add(entry);
			clone = other;
			// no deep copy possible.
		}
		return clone;
	}
	
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
	 * For each container, where the associations are not bound, the hash-code from the data structure was taken as id and
	 * all associations in the associated data structures root elements were set with the instance ID of the container
	 * data structures.
	 * 
	 * This function shall be executed as soon as more TPMs are used in one container and every time something is loaded 
	 * from the memory
	 * 
	 * @since 06.07.2011 15:03:52
	 *
	 * @param <E>
	 * @param poInput
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public <E extends clsDataStructureContainer> ArrayList<E> createInstanceFromType(ArrayList<E> poInput) {
		ArrayList<E> oRetVal = (ArrayList<E>)deepCopy(poInput);
		
		//Set Unique IDs for all root elements
		for (E oElement : oRetVal) {
			int oInstanceID;	//
			//Check if the root element already have an unique ID
			if (oElement.getMoDataStructure().getMoDSInstance_ID() == 0) {
				oInstanceID = oElement.getMoDataStructure().hashCode();
				oElement.getMoDataStructure().setMoDSInstance_ID(oInstanceID);
			} else {
				oInstanceID = oElement.getMoDataStructure().getMoDSInstance_ID();
			}
			
			//Go through all associations in the container and complete the ones, which are missing or different from the root element
			for (clsAssociation oAssStructure : oElement.getMoAssociatedDataStructures()) {
				//Change ID only if the association root element is the same type (ID) as the data structure
				if ((oAssStructure.getRootElement().getMoDSInstance_ID()!=oInstanceID) && (oElement.getMoDataStructure().getMoDS_ID()==oAssStructure.getRootElement().getMoDS_ID())) {
					oAssStructure.getRootElement().setMoDSInstance_ID(oInstanceID);
				}
			}
		}
		
		return oRetVal;
	}
	
	protected void putInterfaceData(@SuppressWarnings("rawtypes") Class poInterface, Object... poData) {
		eInterfaces nI = eInterfaces.getEnum(poInterface.getSimpleName());
		
		putInterfaceData(nI, poData);
	}
	
	protected void putInterfaceData(eInterfaces pnInterface, Object... poData) {
		ArrayList<Object> oData = new ArrayList<Object>();
		for (Object d:poData) {
			oData.add(d);
		}
		
		moInterfaceData.put(pnInterface, oData);
	}
	
	@Override
	public String getDescription() {
		return moDescription;
	}
	
	public abstract void setDescription();
	
	protected ArrayList<eInterfaces> getInterfacesFilter(String oSuffix) {
		ArrayList<eInterfaces> oResult = new ArrayList<eInterfaces>();
		for (@SuppressWarnings("rawtypes") Class oI:this.getClass().getInterfaces()) {
			if (oI.getSimpleName().endsWith(oSuffix)) {
				oResult.add(eInterfaces.getEnum(oI.getSimpleName()));
			}
		}
		return oResult;
	}
	
	protected void setInterfacesList() {
		moInterfacesSend = getInterfacesFilter("_send");
		moInterfacesReceive = getInterfacesFilter("_receive");
		
		moInterfaces = new ArrayList<eInterfaces>();
		moInterfaces.addAll(moInterfacesReceive);
		moInterfaces.addAll(moInterfacesSend);
	}
	
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return moInterfacesSend;
	}
	
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return moInterfacesReceive;
	}	
	
	public ArrayList<eInterfaces> getInterfaces() {
		return moInterfaces;
	}
}
