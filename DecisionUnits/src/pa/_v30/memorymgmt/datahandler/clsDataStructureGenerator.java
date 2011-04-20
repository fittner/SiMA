/**
 * clsDataTypeCreator.java: DecisionUnits - pa._v30.memorymgmt.creator.datatypes
 * 
 * @author zeilinger
 * 11.08.2010, 20:50:42
 */
package pa._v30.memorymgmt.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.memorymgmt.datatypes.clsAct;
import pa._v30.memorymgmt.datatypes.clsAffect;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v30.memorymgmt.datatypes.clsAssociationTime;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v30.memorymgmt.datatypes.clsTemplateImage;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eDataType;

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
			Class<?> clzz = Class.forName("pa._v30.memorymgmt.datahandler.clsDataStructureGenerator");
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
	
	public static int setID(){
		int dummyID = -1; 
		return dummyID;
	}
	
	public static clsThingPresentation generateTP(clsPair <String, Object> poContent){
		clsThingPresentation oRetVal = new clsThingPresentation(new clsTripple<Integer, eDataType, String> (setID() , eDataType.TP, poContent.a), poContent.b); ;  
		return oRetVal;
	}
	
	public static clsThingPresentationMesh generateTPM(clsTripple <String, ArrayList<clsPhysicalRepresentation>, Object> poContent){
		clsThingPresentationMesh oRetVal; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		oRetVal = new clsThingPresentationMesh(new clsTripple<Integer, eDataType, String>(setID(), eDataType.TPM, oContentType),oAssociatedContent, oContent); 
		
		for(clsPhysicalRepresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String> (setID(), eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.toString()), 
													 oRetVal, 
													 oElement)); 
		}

		return oRetVal;
	}
	
	public static clsTemplateImage generateTI(clsTripple <String, ArrayList<clsPhysicalRepresentation>, Object> poContent){
		clsTemplateImage oRetVal; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c;
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		oRetVal = new clsTemplateImage(new clsTripple<Integer, eDataType, String>(setID(), eDataType.TI, oContentType), oAssociatedContent, oContent); 

		for(clsPhysicalRepresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationTime(new clsTripple<Integer, eDataType, String> (setID(), eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
													 oRetVal, 
													 oElement)); 
		}

		return oRetVal;
	}
	
	public static clsAffect generateAFFECT(clsPair <String, Object> poContent){
		clsAffect oRetVal; 
		String oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsAffect(new clsTripple<Integer, eDataType, String>(setID(), eDataType.AFFECT, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsDriveDemand generateDRIVEDEMAND(clsPair <String, Object> poContent){
		clsDriveDemand oRetVal; 
		String oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsDriveDemand(new clsTripple<Integer, eDataType, String>(setID(), eDataType.DRIVEDEMAND, oContentType), oContent); 
		return oRetVal;
	}
		
	public static clsDriveMesh generateDM(clsTripple <String, ArrayList<clsThingPresentation>, Object> poContent){
		clsDriveMesh oRetVal; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		double oPleasure = 0.0;
		double [] oCathegories = {0.0,0.0,0.0,0.0};
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		oRetVal = new clsDriveMesh(new clsTripple<Integer, eDataType, String>(setID(), eDataType.DM, oContentType), oPleasure, oCathegories, oAssociatedContent, oContent);
		
		for(clsThingPresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationAttribute(new clsTripple<Integer, eDataType, String> (setID(), eDataType.ASSOCIATIONATTRIBUTE, eDataType.ASSOCIATIONATTRIBUTE.toString()), 
													 oRetVal, 
													 oElement)); 
		}
		
		return oRetVal;
	}
	
	public static clsWordPresentation generateWP(clsPair <String, Object> poContent){
		String oContentType = poContent.a; 
		String oContent = (String)poContent.b;
		clsWordPresentation oRetVal = new clsWordPresentation(new clsTripple<Integer, eDataType, String>(setID(), eDataType.WP, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsAct generateACT(clsTripple <String, ArrayList<clsSecondaryDataStructure>, Object> poContent){
		clsAct oRetVal; 
		String oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsSecondaryDataStructure> oAssociatedContent = poContent.b; 
		//HZ: The content type has to be defined - a decision has to be made between
		//ArrayList<clsAssociation> and ArrayList<clsWordPresentation>
//		for(clsWordPresentation oElement : poContent.b){
//			//tbd
//		}
		oRetVal = new clsAct(new clsTripple<Integer, eDataType, String>(setID(), eDataType.ACT, oContentType), oAssociatedContent, oContent);
		return oRetVal;
	}
}
