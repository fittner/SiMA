/**
 * clsTPGenerator.java: DecisionUnits - pa.tools
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 */
package pa.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsThingPresentationSingle;
import pa.enums.eSymbolExtType;
import pa.symbolization.representationsymbol.itfGetSymbolName;
import pa.symbolization.representationsymbol.itfIsContainer;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 * 
 */
public class clsTPGenerator {

	public static ArrayList<clsPrimaryInformation> convertSensorToTP(HashMap<eSymbolExtType, itfSymbol> poSymbolDataExt) {
		ArrayList<clsPrimaryInformation> oResult = new ArrayList<clsPrimaryInformation>();
		
		for( itfSymbol oSymbolData : poSymbolDataExt.values() ) {
			if(oSymbolData != null) {
				ArrayList<itfSymbol> oSymbolObjectList = oSymbolData.getSymbolObjects();
				for(itfSymbol oSymbolObject : oSymbolObjectList) {
					String oMeshAttributeName = "";
					
					if (oSymbolObject instanceof itfGetSymbolName) {
						oMeshAttributeName = ((itfGetSymbolName)oSymbolObject).getSymbolName();
					}
					
					clsPrimaryInformation oPI = null;
					
					if(oSymbolObject instanceof itfIsContainer) {
						oPI = mesh(oMeshAttributeName, oSymbolObject);
					} else { //no container - just create an empty mesh
						oPI = single(oMeshAttributeName, oSymbolObject);
					}
					
					oResult.add(oPI);
				}
			}
		}
		
		return oResult;
	}
	
	private static clsPrimaryInformation mesh(String poMeshAttributeName, itfSymbol poDataObject) { 
		clsPrimaryInformationMesh oPrimMesh = new clsPrimaryInformationMesh(new clsThingPresentationSingle());
		
		Field[] oFields = poDataObject.getClass().getFields(); //get members of class
		for(Field oField : oFields) { //for each (public) member of the sensordata-class

			if( oField.getName().equals(poMeshAttributeName) ) { //this is the mesh-content
				
				oPrimMesh.moTP.meContentName = oField.getName();
				oPrimMesh.moTP.meContentType = oField.getClass().getName();
				try {
					oPrimMesh.moTP.moContent = oField.get(poDataObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
			} else if(oField.getName().startsWith("m")) { //this is a connected TP-Single
				//creating the thing presentation of the attribute 
				clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
				
				oPrimSingle.moTP.meContentName = oField.getName();
				oPrimSingle.moTP.meContentType = oField.getClass().getName();
				try {
					oPrimSingle.moTP.moContent = oField.get(poDataObject);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
				//creating the association between the mesh and the attribute
				clsAssociationContext<clsPrimaryInformation> oAssoc = new clsAssociationContext<clsPrimaryInformation>();
				oAssoc.moElementA = oPrimMesh;
				oAssoc.moElementB = oPrimSingle;
				//storing the association in the mesh
				oPrimMesh.moAssociations.add(oAssoc);
			}
		}
		
		return oPrimMesh;
	}
	
	private static clsPrimaryInformation single(String poMeshAttributeName, itfSymbol poDataObject) {
		//creating the thing presentation of the attribute 
		clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
		
		oPrimSingle.moTP.meContentName = poMeshAttributeName;

		try {
			Field oField = poDataObject.getClass().getField(oPrimSingle.moTP.meContentName);
			oPrimSingle.moTP.meContentType = oField.getClass().getName();
			oPrimSingle.moTP.moContent = oField.get(poDataObject);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return oPrimSingle;
				
	}
}
