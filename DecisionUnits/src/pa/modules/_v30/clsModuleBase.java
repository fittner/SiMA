/**
 * clsModuleAbstract.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 */
package pa.modules._v30;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces._v30.itfInspectorInternalState;
import pa.interfaces._v30.itfInterfaceDescription;
import pa.interfaces._v30.itfInterfaceInterfaceData;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 * 
 */
public abstract class clsModuleBase implements itfInspectorInternalState, itfInterfaceDescription, itfInterfaceInterfaceData {
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
	
	@SuppressWarnings("rawtypes")
	protected <E,V> String mapToHTML(String poName, Map<E,V> poMap) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poMap == null) {
			html += "<p><i>null</i></p>";
		} else if (poMap.size() == 0) {
			html += "<p><i>empty</i></p>";
		} else {
			html += "<ul>";
			for (Map.Entry e:poMap.entrySet()) {
				html +="<li>"+e.getKey()+": "+e.getValue()+"</li>";
			}
			html +="</ul>";
		}
		
		return html;
	}
	
	@SuppressWarnings("rawtypes")
	protected String listToHTML(String poName, List poList) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poList == null) {
			html += "<p><i>null</i></p>";
		} else if (poList.size() == 0) {
			html += "<p><i>empty</i></p>";
		} else {		
			html += "<ul>";
			for (Object e:poList) {
				html +="<li>"+e+"</li>";
			}
			html +="</ul>";
		}
		
		return html;
	}	
	
	protected String valueToHTML(String poName, Object poValue) {
		String html ="<h2>"+poName+"</h2>";
		
		if (poValue == null) {
			html += "<p><i>null</i></p>";
		} else {		
			html +="<p>"+poValue+"</p>";
		}
		
		return html;
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
	
	public ArrayList<eInterfaces> getInterfacesSend() {
		return moInterfacesSend;
	}
	
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return moInterfacesReceive;
	}	
	
	public ArrayList<eInterfaces> getInterfaces() {
		return moInterfaces;
	}		
}
