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
import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 30.09.2009, 11:30:13
 * 
 */
public class clsTPGenerator {

	public static ArrayList<clsPrimaryInformation> convertSensorToTP(HashMap<eSensorExtType, clsSensorExtern> poSensorDataExt) {
		
		ArrayList<clsPrimaryInformation> oResult = new ArrayList<clsPrimaryInformation>();
		
		for( clsSensorExtern oSensorData : poSensorDataExt.values() ) {
			
			if(oSensorData != null) {
			
				ArrayList<clsSensorExtern> oDataObjectList = oSensorData.getDataObjects();
				for(clsSensorExtern oDataObject : oDataObjectList) {
	
					clsPrimaryInformationMesh oPrimMesh = new clsPrimaryInformationMesh(new clsThingPresentationSingle());
					
					String oMeshAttributeName = oDataObject.getMeshAttributeName();
					if(oDataObject.isContainer()) {
						Field[] oFields = oDataObject.getClass().getFields(); //get members of class
						for(Field oField : oFields) { //for each (public) member of the sensordata-class
		
							if( oField.getName().equals(oMeshAttributeName) ) { //this is the mesh-content
								
								oPrimMesh.moTP.meContentName = oField.getName();
								oPrimMesh.moTP.meContentType = oField.getClass().getName();
								try {
									oPrimMesh.moTP.moContent = oField.get(oDataObject);
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								}
								oResult.add(oPrimMesh);
								
							} else if(oField.getName().startsWith("m")) { //this is a connected TP-Single
								//creating the thing presentation of the attribute 
								clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
								
								oPrimSingle.moTP.meContentName = oField.getName();
								oPrimSingle.moTP.meContentType = oField.getClass().getName();
								try {
									oPrimSingle.moTP.moContent = oField.get(oDataObject);
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
					} 
					else //no container - just create an empty mesh
					{
						//creating the thing presentation of the attribute 
						clsPrimaryInformation oPrimSingle = new clsPrimaryInformation(new clsThingPresentationSingle());
						
						oPrimSingle.moTP.meContentName = oDataObject.getMeshAttributeName();

						try {
							Field oField = oDataObject.getClass().getField(oPrimSingle.moTP.meContentName);
							oPrimSingle.moTP.meContentType = oField.getClass().getName();
							oPrimSingle.moTP.moContent = oField.get(oDataObject);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						}
						oResult.add(oPrimSingle);
					}
				}
			}
		}
		
		return oResult;
	}
}
