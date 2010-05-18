/**
 * clsInterfaceHandler.java: DecisionUnits - pa
 * 
 * @author deutsch
 * 18.05.2010, 13:54:22
 */
package pa;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import pa.interfaces.I_BaseInterface;
import pa.modules.clsModuleBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 13:54:22
 * 
 */
public class clsInterfaceHandler {
	private static String P_METHODPREFIX = "receive_I";
	
	private HashMap<Class, ArrayList<clsModuleBase>> moInterfaceModuleMap;
	private HashMap<Class, Method> moMethods;
	
	public clsInterfaceHandler() {
		moMethods = new HashMap<Class, Method>();
		moInterfaceModuleMap = new HashMap<Class,  ArrayList<clsModuleBase>>();
	}
	
	public void setModules(ArrayList<clsModuleBase> poModules) {
		extractInterfaceModuleMapping(poModules);
	}
	
	private void extractInterfaceModuleMapping(ArrayList<clsModuleBase> poModules) {
		for (clsModuleBase oModule:poModules) {
			Class[] oInterfaces = oModule.getClass().getInterfaces();
			for (Class oI:oInterfaces) {
				if (oI == I_BaseInterface.class) {
					continue;
				}
				
				ArrayList<clsModuleBase> oM = moInterfaceModuleMap.get(oI);
				
				if (oI == null) {
					oM = new ArrayList<clsModuleBase>();
					moInterfaceModuleMap.put(oI, oM);
					addInterfaceMethod(oI);
				}
				
				oM.add(oModule);
			}
		}		
	}
	
	private void addInterfaceMethod(Class poInterface) {
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

		Method oMethod = oMethods.get(0);
		
		moMethods.put(poInterface, oMethod);
	}
	
	public void sendData(Class poReceiverInterface, Object[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
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
