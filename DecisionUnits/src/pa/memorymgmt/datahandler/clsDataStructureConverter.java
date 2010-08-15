/**
 * clsDataStructureConverter.java: DecisionUnits - pa.memorymgmt.generator
 * 
 * @author zeilinger
 * 13.08.2010, 12:07:07
 */
package pa.memorymgmt.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.enums.eDataType;
import pa.symbolization.representationsymbol.itfGetDataAccessMethods;
import pa.symbolization.representationsymbol.itfGetSymbolName;
import pa.symbolization.representationsymbol.itfIsContainer;
import pa.symbolization.representationsymbol.itfSymbol;
import pa.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.08.2010, 12:07:07
 * 
 */
public class clsDataStructureConverter {
	public static clsDataStructurePA convertHomeostaticSymbolsToPsychicDataStructures(){
		//tbd - actually not defined and required - may replace the actual conversion of homeostatic input (Bloodsugar, stomachtension, etc.)  
		return null; 
	}
	
	public static clsDataStructurePA convertExtSymbolsToPsychicDataStructures(itfSymbol poSymbolObject){
			if(poSymbolObject instanceof itfIsContainer) {return convertSymbolsToTPM(poSymbolObject);} 
			else {return convertSymbolsToTP(poSymbolObject);}				
	}
	
	private static clsDataStructurePA convertSymbolsToTP(itfSymbol poSymbolObject){
		Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();
		
		eDataType eDataStructureType = eDataType.TP;
		String oContentType = ((itfGetSymbolName)poSymbolObject).getSymbolType();
		Object oContent = null;
						
		if (oMethods.length != 2) {
			throw new java.lang.IllegalArgumentException("can only convert symbols with excatly 1 getMethod (except getSymbolObjects()). ("+eDataStructureType.toString()+"; "+oContentType+"; "+oMethods.length+")");
		}
		
		try {
			if (oMethods[0].getName().equals("getSymbolObjects")) {
				oContent = oMethods[1].invoke(poSymbolObject,  new Object[0]);
			} else {
				oContent = oMethods[0].invoke(poSymbolObject,  new Object[0]);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return clsDataStructureGenerator.generateDataStructure(eDataStructureType, new clsPair <String, Object>(oContentType, oContent)); 
	}
	
	private static clsDataStructurePA convertSymbolsToTPM(itfSymbol poSymbolObject){
		Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();
		
		clsThingPresentationMesh oTPM =  null; 
		String oContentType = ((itfGetSymbolName)poSymbolObject).getSymbolType();
		ArrayList<clsPhysicalRepresentation> oContent = new ArrayList<clsPhysicalRepresentation>();
		
		for(Method oM : oMethods){
			if (oM.getName().equals("getSymbolObjects")) {
				continue;
			}
			clsThingPresentation oTP = null; 
			String oContentTypeTP = oM.getClass().getName(); 
			Object oContentTP = null; 
			
			try {
				oContentTP = oM.invoke(poSymbolObject,  new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <String, Object>(oContentTypeTP, oContentTP)); 
			oContent.add(oTP); 
		}
		oTPM = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsPair<String, Object>(oContentType, oContent)); 
		return oTPM; 	
	}
}
