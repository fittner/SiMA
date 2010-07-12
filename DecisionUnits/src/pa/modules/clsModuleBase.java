/**
 * clsModuleAbstract.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 */
package pa.modules;

import java.lang.reflect.Method;
import java.util.Iterator;

import pa.clsInterfaceHandler;
import config.clsBWProperties;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 11:16:13
 * 
 */
public abstract class clsModuleBase {
	protected clsModuleContainer moEnclosingContainer;
	protected eProcessType mnProcessType;
	protected ePsychicInstances mnPsychicInstances;
	
	protected int mnTest = 0;
	
	protected clsInterfaceHandler moInterfaceHandler;
	
	private eImplementationStage mnImplementationStage;
	
	public clsModuleBase(String poPrefix, clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		moEnclosingContainer = poEnclosingContainer;
		moInterfaceHandler = poInterfaceHandler;
		
		setProcessType();
		setPsychicInstances();
		
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		mnImplementationStage = eImplementationStage.BASIC;
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}
	
	private void process() {
		switch(mnImplementationStage) {
			case BASIC:	process_basic(); break;
			case DRAFT: process_draft(); break;
			case FINAL: process_final(); break;
			default: throw new java.lang.IllegalArgumentException(mnImplementationStage+" not handeld in switch.");
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

	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
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
	
}
