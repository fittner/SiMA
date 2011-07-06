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

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.itfInterfaceInterfaceData;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 * 
 */
public abstract class clsModuleBase implements 
		itfInspectorInternalState, 
		itfInterfaceDescription, 
		itfInterfaceInterfaceData {
	public static String P_PROCESS_IMPLEMENTATION_STAGE = "IMP_STAGE"; 
	
	protected eProcessType mnProcessType;
	protected ePsychicInstances mnPsychicInstances;
	protected Integer mnModuleNumber;
		
	protected int mnTest = 0;
	
	private eImplementationStage mnImplementationStage;
	protected HashMap<Integer, clsModuleBase> moModuleList;
	protected SortedMap<eInterfaces, ArrayList<Object>> moInterfaceData;
		
	protected String moDescription;
	private ArrayList<eInterfaces> moInterfacesReceive;
	private ArrayList<eInterfaces> moInterfacesSend;
	private ArrayList<eInterfaces> moInterfaces;
	
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

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mnImplementationStage = eImplementationStage.valueOf(poProp.getPropertyString(pre+P_PROCESS_IMPLEMENTATION_STAGE));	
	}
	
	private void process() {
		if (mnImplementationStage == eImplementationStage.BASIC) {
			process_basic();
		} else if (mnImplementationStage == eImplementationStage.DRAFT) {
			process_draft();
		} else {
			process_final();
		}
	}
	
	protected abstract void process_basic();
	protected abstract void process_draft();
	protected abstract void process_final();
	
	protected abstract void send();
	
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
