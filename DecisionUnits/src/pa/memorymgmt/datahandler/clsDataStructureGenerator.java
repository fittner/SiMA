/**
 * clsDataTypeCreator.java: DecisionUnits - pa.memorymgmt.creator.datatypes
 * 
 * @author zeilinger
 * 11.08.2010, 20:50:42
 */
package pa.memorymgmt.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAffect;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationTime;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 11.08.2010, 20:50:42
 * 
 */
public abstract class clsDataStructureGenerator {
	public static clsDataStructurePA generateDataStructure(eDataType peDataType, Object poContent){
		clsDataStructurePA oRetVal = null; 
		try {
			Class<?> clzz = Class.forName("pa.memorymgmt.datahandler.clsDataStructureGenerator");
			Method method = clzz.getMethod("generate" + peDataType.name(), poContent.getClass());
			oRetVal = (clsDataStructurePA)method.invoke(null, poContent);
		} catch (SecurityException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO (zeilinger) - Auto-generated catch block
			e.printStackTrace();
		}
		return oRetVal; 
	}
	
	public static clsThingPresentation generateTP(clsPair <String, Object> poContent){
		clsThingPresentation oRetVal = new clsThingPresentation(new clsTripple<String, eDataType, String> (null, eDataType.TP, poContent.a), poContent.b); ;  
		return oRetVal;
	}
	
	public static clsThingPresentationMesh generateTPM(clsTripple <String, ArrayList<clsPhysicalRepresentation>, Object> poContent){
		clsThingPresentationMesh oRetVal = null; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		for(clsPhysicalRepresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationAttribute(new clsTripple<String, eDataType, String> (null, eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.toString()), 
													 oRetVal, 
													 oElement)); 
		}
		oRetVal = new clsThingPresentationMesh(new clsTripple<String, eDataType, String>(null, eDataType.TPM, oContentType),oAssociatedContent, oContent); 
		return oRetVal;
	}
	
	public static clsTemplateImage generateTI(clsTripple <String, ArrayList<clsPhysicalRepresentation>, Object> poContent){
		clsTemplateImage oRetVal = null; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c;
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		for(clsPhysicalRepresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationTime(new clsTripple<String, eDataType, String> (null, eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
													 oRetVal, 
													 oElement)); 
		}
		oRetVal = new clsTemplateImage(new clsTripple<String, eDataType, String>(null, eDataType.TI, oContentType), oAssociatedContent, oContent); 
		return oRetVal;
	}
	
	public static clsAffect generateAFFECT(clsPair <String, Object> poContent){
		clsAffect oRetVal = null; 
		String oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsAffect(new clsTripple<String, eDataType, String>(null, eDataType.AFFECT, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsDriveDemand generateDRIVEDEMAND(clsPair <String, Object> poContent){
		clsDriveDemand oRetVal = null; 
		String oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsDriveDemand(new clsTripple<String, eDataType, String>(null, eDataType.DRIVEDEMAND, oContentType), oContent); 
		return oRetVal;
	}
		
	public static clsDriveMesh generateDM(clsTripple <String, ArrayList<clsThingPresentation>, Object> poContent){
		clsDriveMesh oRetVal = null; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		double oPleasure = 0.0;
		double [] oCathegories = {0.0,0.0,0.0,0.0};
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		for(clsThingPresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationAttribute(new clsTripple<String, eDataType, String> (null, eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.toString()), 
													 oRetVal, 
													 oElement)); 
		}
		oRetVal = new clsDriveMesh(new clsTripple<String, eDataType, String>(null, eDataType.DM, oContentType), oPleasure, oCathegories, oAssociatedContent, oContent); 
		return oRetVal;
	}
	
	public static clsWordPresentation generateWP(clsPair <String, Object> poContent){
		String oContentType = poContent.a; 
		String oContent = (String)poContent.b;
		clsWordPresentation oRetVal = new clsWordPresentation(new clsTripple<String, eDataType, String>(null, eDataType.WP, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsAct generateACT(clsTripple <String, ArrayList<clsWordPresentation>, Object> poContent){
		clsAct oRetVal = null; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>(); 
		//HZ: The content type has to be defined - a decision has to be made between
		//ArrayList<clsAssociation> and ArrayList<clsWordPresentation>
//		for(clsWordPresentation oElement : poContent.b){
//			//tbd
//		}
		oRetVal = new clsAct(new clsTripple<String, eDataType, String>(null, eDataType.ACT, oContentType), oAssociatedContent, oContent);
		return oRetVal;
	}
	
}
