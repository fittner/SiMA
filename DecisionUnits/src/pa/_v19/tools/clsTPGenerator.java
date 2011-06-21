/**
 * clsTPGenerator.java: DecisionUnits - pa.tools
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 */
package pa._v19.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.datatypes.clsAssociationContext;
import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.datatypes.clsPrimaryInformationMesh;
import pa._v19.datatypes.clsThingPresentationSingle;
import pa._v19.symbolization.representationsymbol.itfGetDataAccessMethods;
import pa._v19.symbolization.representationsymbol.itfGetSymbolName;
import pa._v19.symbolization.representationsymbol.itfIsContainer;
import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.enums.eSymbolExtType;

/**
 *
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 * @deprecated
 */
public class clsTPGenerator {

	public static ArrayList<clsPrimaryInformation> convertSensorToTP(HashMap<eSymbolExtType, itfSymbol> poSymbolDataExt) {
		ArrayList<clsPrimaryInformation> oResult = new ArrayList<clsPrimaryInformation>();
		
		for( itfSymbol oSymbolData : poSymbolDataExt.values() ) {
			if(oSymbolData != null) {
				ArrayList<itfSymbol> oSymbolObjectList = oSymbolData.getSymbolObjects();
				for(itfSymbol oSymbolObject : oSymbolObjectList) {
					
					
					clsPrimaryInformation oPI = null;
					
					if(oSymbolObject instanceof itfIsContainer) {
						oPI = mesh(oSymbolObject);
					} else { 
						oPI = single(oSymbolObject);
					}
					
					oResult.add(oPI);
				}
			}
		}
		
		return oResult;
	}
	
	private static String removePrefix(String poName) {
		if (poName.startsWith("get")) {
			poName = poName.substring(3);
		}
		
		return poName;
	}
	
	private static clsPrimaryInformation mesh(itfSymbol poDataObject) {
		//creating the thing presentation of the attribute 
		clsPrimaryInformationMesh oPrimMesh = new clsPrimaryInformationMesh(new clsThingPresentationSingle());
		
		oPrimMesh.moTP.meContentName = ((itfGetSymbolName)poDataObject).getSymbolType();
		oPrimMesh.moTP.meContentType = ((itfGetSymbolName)poDataObject).getSymbolType();
		oPrimMesh.moTP.moContent = ((itfIsContainer)poDataObject).getSymbolMeshContent();

		Method[] oMethods = ((itfGetDataAccessMethods)poDataObject).getDataAccessMethods();
		int i=0;
		for (Method oM : oMethods) {
			if (oM.getName().equals("getSymbolObjects")) {
				continue;
			}
			
			clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
			
			oPrimSingle.moTP.meContentName = removePrefix(oM.getName());
			oPrimSingle.moTP.meContentType = oM.getClass().getName();
					
			try {
				oPrimSingle.moTP.moContent = oMethods[i].invoke(poDataObject,  new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			//creating the association between the mesh and the attribute
			clsAssociationContext<clsPrimaryInformation> oAssoc = new clsAssociationContext<clsPrimaryInformation>();
			oAssoc.moElementA = oPrimMesh;
			oAssoc.moElementB = oPrimSingle;
			//storing the association in the mesh
			oPrimMesh.moAssociations.add(oAssoc);		
			i++;
		}
		
		return oPrimMesh;
	}
	
	private static clsPrimaryInformation single(itfSymbol poDataObject) {
		//creating the thing presentation of the attribute 
		clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
		
		oPrimSingle.moTP.meContentName = ((itfGetSymbolName)poDataObject).getSymbolName();
		oPrimSingle.moTP.meContentType = ((itfGetSymbolName)poDataObject).getSymbolType();

		Method[] oMethods = ((itfGetDataAccessMethods)poDataObject).getDataAccessMethods();
		
		if (oMethods.length != 2) {
			throw new java.lang.IllegalArgumentException("can only convert symbols with excatly 1 getMethod (except getSymbolObjects()). ("+oPrimSingle.moTP.meContentName+"; "+oPrimSingle.moTP.meContentType+"; "+oMethods.length+")");
		}
		
		try {
			if (oMethods[0].getName().equals("getSymbolObjects")) {
				oPrimSingle.moTP.moContent = oMethods[1].invoke(poDataObject,  new Object[0]);
			} else {
				oPrimSingle.moTP.moContent = oMethods[0].invoke(poDataObject,  new Object[0]);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return oPrimSingle;
	}
}
