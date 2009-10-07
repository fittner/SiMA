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
import pa.datatypes.clsThingPresentationMesh;
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

	public static ArrayList<clsThingPresentationMesh> convertSensorToTP(HashMap<eSensorExtType, clsSensorExtern> poSensorDataExt) {
		
		ArrayList<clsThingPresentationMesh> oResult = new ArrayList<clsThingPresentationMesh>();
		
		for( clsSensorExtern oSensorData : poSensorDataExt.values() ) {
			
			if(oSensorData != null) {
			
				ArrayList<clsSensorExtern> oDataObjectList = oSensorData.getDataObjects();
				for(clsSensorExtern oDataObject : oDataObjectList) {
	
					boolean oMesh = false;
					boolean oSingle = false;
					clsThingPresentationMesh oTPMesh = new clsThingPresentationMesh();
					
					String oMeshAttributeName = oDataObject.getMeshAttributeName();
					Field[] oFields = oDataObject.getClass().getFields(); //get members of class
					for(Field oField : oFields) { //for each (public) member of the sensordata-class
	
						if( oField.getName().equals(oMeshAttributeName) ) { //this is the mesh-content
							
							oTPMesh.meContentName = oField.getName();
							oTPMesh.meContentType = oField.getClass().getName();
							try {
								oTPMesh.moContent = oField.get(oDataObject);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							oMesh = true;
							
						} else if(oField.getName().startsWith("m")) { //this is a connected TP-Single
							//creating the thing presentation of the attribute 
							clsThingPresentationSingle oTPSingle = new clsThingPresentationSingle();
							
							oTPSingle.meContentName = oField.getName();
							oTPSingle.meContentType = oField.getClass().getName();
							try {
								oTPSingle.moContent = oField.get(oDataObject);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
							
							//creating the association between the mesh and the attribute
							clsAssociationContext oAssoc = new clsAssociationContext();
							oAssoc.moElementA = oTPMesh;
							oAssoc.moElementB = oTPSingle;
							//storing the association in the mesh
							oTPMesh.moAssociations.add(oAssoc);
							oSingle = true;
						}
					}
					if( oMesh || oSingle ) {
						oResult.add(oTPMesh);
					}
				}
			}
		}
		
		return oResult;
	}
}
