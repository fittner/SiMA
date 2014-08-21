/**
 * clsDataTypeCreator.java: DecisionUnits - pa._v38.memorymgmt.creator.datatypes
 * 
 * @author zeilinger
 * 11.08.2010, 20:50:42
 */
package base.datahandlertools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.enums.ePredicate;
import base.datatypes.clsAct;
import base.datatypes.clsAffect;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsAssociationPrimary;
import base.datatypes.clsAssociationPrimaryDM;
import base.datatypes.clsAssociationSecondary;
import base.datatypes.clsAssociationTime;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveDemand;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

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
			Class<?> clzz = Class.forName("base.datahandlertools.clsDataStructureGenerator");
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
	
	public static clsThingPresentation generateTP(clsPair <eContentType, Object> poContent){
		clsThingPresentation oRetVal = new clsThingPresentation(new clsTriple<Integer, eDataType, eContentType> (setID() , eDataType.TP, poContent.a), poContent.b); ;  
		return oRetVal;
	}
	
	public static clsThingPresentationMesh generateTPM(clsTriple <eContentType, ArrayList<clsThingPresentation>, Object> poContent){
		clsThingPresentationMesh oRetVal; 
		eContentType oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
		
		oRetVal = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.TPM, oContentType),oAssociatedContent, oContent); 
		
		for(clsThingPresentation oElement : poContent.b){
			oAssociatedContent.add(new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType> (setID(), eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
													 oRetVal, 
													 oElement)); 
		}

		return oRetVal;
	}
	
//	public static clsTemplateImage generateTI(clsTriple <String, ArrayList<clsThingPresentationMesh>, Object> poContent){
//		clsTemplateImage oRetVal; 
//		String oContentType = poContent.a;
//		String oContent = (String)poContent.c;
//		ArrayList<clsAssociation> oAssociatedContent = new ArrayList<clsAssociation>();
//		
//		oRetVal = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(setID(), eDataType.TI, oContentType), oAssociatedContent, oContent); 
//
//		for(clsThingPresentationMesh oElement : poContent.b){
//			oAssociatedContent.add(new clsAssociationTime(new clsTriple<Integer, eDataType, String> (setID(), eDataType.ASSOCIATIONTEMP, eDataType.ASSOCIATIONTEMP.toString()), 
//													 oRetVal, 
//													 oElement)); 
//		}
//
//		return oRetVal;
//	}
	
	public static clsEmotion generateEMOTION(clsTriple <eContentType, eEmotionType, Object> poContent, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr){
		clsEmotion oRetVal; 
		eContentType oContentType = poContent.a;
		eEmotionType oContent = poContent.b;
		
		double rIntensity = (Double)poContent.c;
		
		oRetVal = new clsEmotion(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.EMOTION, oContentType), rIntensity, oContent,  prSourcePleasure,  prSourceUnpleasure,  prSourceLibid,  prSourceAggr); 
		return oRetVal;
	}
	
	public static clsAffect generateAFFECT(clsPair <eContentType, Object> poContent){
		clsAffect oRetVal; 
		eContentType oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsAffect(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.AFFECT, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsDriveDemand generateDRIVEDEMAND(clsPair <eContentType, Object> poContent){
		clsDriveDemand oRetVal; 
		eContentType oContentType = poContent.a;
		double oContent = (Double)poContent.b;
		
		oRetVal = new clsDriveDemand(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.DRIVEDEMAND, oContentType), oContent); 
		return oRetVal;
	}
		

	public static clsDriveMesh generateDM(clsTriple <eContentType, ArrayList<clsThingPresentationMesh>, Object> poContent,  eDriveComponent poDriveComponent, ePartialDrive poPartialDrive){
		clsDriveMesh oRetVal = null; 
		eContentType oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		double oQuotaOfAffect = 0.0;
		ArrayList<clsAssociation> oInternalAssociatedContent = new ArrayList<clsAssociation>();
		
		for(clsThingPresentationMesh oElement : poContent.b){
			oInternalAssociatedContent.add(new clsAssociationDriveMesh(new clsTriple<Integer, eDataType, eContentType> (setID(), eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM), 
													 oRetVal, 
													 oElement)); 
		}
		
		
		oRetVal = new clsDriveMesh(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.DM, oContentType), oInternalAssociatedContent, oQuotaOfAffect, oContent, poDriveComponent, poPartialDrive);
		

		return oRetVal;
	}
	
	public static clsWordPresentation generateWP(clsPair <eContentType, Object> poContent){
		eContentType oContentType = poContent.a; 
		String oContent = (String)poContent.b;
		clsWordPresentation oRetVal = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.WP, oContentType), oContent); 
		return oRetVal;
	}
	
	public static clsWordPresentationMesh generateWPM(clsPair <eContentType, Object> poContent, ArrayList<clsAssociation> poAssociations){
		eContentType oContentType = poContent.a; 
		String oContent = (String)poContent.b;
		clsWordPresentationMesh oRetVal = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.WPM, oContentType), poAssociations, oContent); 
		return oRetVal;
	}
	
	public static clsAct generateACT(clsTriple <eContentType, ArrayList<clsSecondaryDataStructure>, Object> poContent){
		clsAct oRetVal; 
		eContentType oContentType = poContent.a;
		String oContent = (String)poContent.c; 
		ArrayList<clsSecondaryDataStructure> oAssociatedContent = poContent.b; 
		//HZ: The content type has to be defined - a decision has to be made between
		//ArrayList<clsAssociation> and ArrayList<clsWordPresentation>
//		for(clsWordPresentation oElement : poContent.b){
//			//tbd
//		}
		oRetVal = new clsAct(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ACT, oContentType), oAssociatedContent, oContent);
		return oRetVal;
	}
	
	public static clsAssociationPrimary generateASSOCIATIONPRI(eContentType poContentType, 
			clsThingPresentationMesh poRoot, clsThingPresentationMesh poLeaf, double prWeight) {
		clsAssociationPrimary oRetVal=null;
		eContentType oContentType = poContentType; 
		
		oRetVal = new clsAssociationPrimary(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONPRI, oContentType), poRoot, poLeaf, 1.0);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociationPrimaryDM generateASSOCIATIONPRIDM(eContentType poContentType, 
			clsDriveMesh poRoot, clsDriveMesh poLeaf, double prWeight) {
		clsAssociationPrimaryDM oRetVal=null;
		eContentType oContentType = poContentType; 
		
		oRetVal = new clsAssociationPrimaryDM(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONPRIDM, oContentType), poRoot, poLeaf);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociationTime generateASSOCIATIONTIME(eContentType poContentType, 
			clsThingPresentationMesh poRoot, clsThingPresentationMesh poLeaf, double prWeight) {
		clsAssociationTime oRetVal=null;
		eContentType oContentType = poContentType; 
		
		oRetVal = new clsAssociationTime(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONTEMP, oContentType), poRoot, poLeaf);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociation generateASSOCIATIONSEC(eContentType poContentType, 
			clsDataStructurePA poRoot, clsDataStructurePA poLeaf, ePredicate poPredicate, double prWeight) {
		clsAssociation oRetVal=null;
		eContentType oContentType = poContentType; 
		
		oRetVal = new clsAssociationSecondary(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONSEC, oContentType), (clsSecondaryDataStructure)poRoot, (clsSecondaryDataStructure)poLeaf, poPredicate);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociation generateASSOCIATIONDM(clsDriveMesh poDM, clsThingPresentationMesh poTPM, double prWeight) {
		clsAssociation oRetVal=null;
		
		oRetVal = new clsAssociationDriveMesh (new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM), poDM, poTPM);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociation generateASSOCIATIONATTRIBUTE(eContentType poContentType, 
			clsPrimaryDataStructure poRoot, clsPrimaryDataStructure poLeaf, double prWeight) {
		clsAssociation oRetVal=null;
		eContentType oContentType = poContentType;  
		
		oRetVal = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONATTRIBUTE, oContentType), poRoot, poLeaf);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociation generateASSOCIATIONWP(eContentType poContentType, clsSecondaryDataStructure poRoot, clsDataStructurePA poLeaf, double prWeight) {
		clsAssociation oRetVal=null;
		eContentType oContentType = poContentType; 
		
		oRetVal = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONSEC, oContentType), poRoot, poLeaf);
		oRetVal.setMrWeight(prWeight);
		
		return oRetVal;
	}
	
	public static clsAssociation generateASSOCIATIONEMOTION(eContentType poContentType, 
	        clsThingPresentationMesh poRoot, clsEmotion poLeaf, double prWeight) {
        clsAssociation oRetVal=null;
        eContentType oContentType = poContentType;  
        
        oRetVal = new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType>(setID(), eDataType.ASSOCIATIONEMOTION, oContentType), poRoot, poLeaf);
        oRetVal.setMrWeight(prWeight);
        
        return oRetVal;
    }
	
	
	
	}
