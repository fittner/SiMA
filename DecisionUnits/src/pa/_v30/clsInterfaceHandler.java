/**
 * clsInterfaceHandler.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 18.05.2010, 13:54:22
 */
package pa._v30;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.modules.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 13:54:22
 * 
 */
@Deprecated
public class clsInterfaceHandler {
	private static String P_METHODPREFIX = "receive_I";
	
	@SuppressWarnings("rawtypes")
	private HashMap<Class, ArrayList<clsModuleBase>> moInterfaceModuleMap;
	@SuppressWarnings("rawtypes")
	private HashMap<Class, Method> moMethods;
	
	@SuppressWarnings("rawtypes")
	public clsInterfaceHandler() {
		moMethods = new HashMap<Class, Method>();
		moInterfaceModuleMap = new HashMap<Class,  ArrayList<clsModuleBase>>();
	}
	
	public void setModules(ArrayList<clsModuleBase> poModules) {
		extractInterfaceModuleMapping(poModules);
	}
	
	@SuppressWarnings("rawtypes")
	private void extractInterfaceModuleMapping(ArrayList<clsModuleBase> poModules) {
		for (clsModuleBase oModule:poModules) {
			Class[] oInterfaces = oModule.getClass().getInterfaces();
			for (Class oI:oInterfaces) {
				if (oI == I_BaseInterface.class) {
					continue;
				}
				
				Method oMethod = null;
				try {
					oMethod = extractInterfaceMethod(oI);
				} catch (java.lang.IllegalArgumentException e) {
					continue;
				}
				
				ArrayList<clsModuleBase> oM = moInterfaceModuleMap.get(oI);
				
				if (oM == null) {
					oM = new ArrayList<clsModuleBase>();
					moInterfaceModuleMap.put(oI, oM);
					
					moMethods.put(oI, oMethod);
				}
				
				oM.add(oModule);
			}
		}		
	}
	
	@SuppressWarnings("rawtypes")
	private Method extractInterfaceMethod(Class poInterface) {
		ArrayList<Method> oMethods = new ArrayList<Method>();
		
		for (Method oM:poInterface.getMethods()) {
			if (oM.getName().startsWith(P_METHODPREFIX)) {
				oMethods.add(oM);
			}
		}
		
		if (oMethods.size() == 0) {
			throw new java.lang.IllegalArgumentException("poInterface ("+poInterface.getName()+") does not contain a method which starts with '"+P_METHODPREFIX+"'.");
		} else if (oMethods.size() > 1) {
			throw new java.lang.IllegalArgumentException("poInterface ("+poInterface.getName()+") contains more than one method which starts with '"+P_METHODPREFIX+"'.");
		}		
		
		return oMethods.get(0);
	}
	
	@SuppressWarnings("rawtypes")
	public void sendData(Class poReceiverInterface, Object... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (poReceiverInterface.getSuperclass() != I_BaseInterface.class) {
			throw new java.lang.IllegalArgumentException("poReceiverInterface ("+poReceiverInterface.getName()+") is not a specialization of interface I_BaseInterface.");
		}

		ArrayList<clsModuleBase> oModules = moInterfaceModuleMap.get(poReceiverInterface);
		Method oMethod = moMethods.get(poReceiverInterface);

		for (clsModuleBase oModule : oModules) {
			oMethod.invoke(oModule, args);
		}
	}
}
