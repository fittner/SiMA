/**
 * clsDataStructureConverter.java: DecisionUnits - pa._v38.memorymgmt.generator
 * 
 * @author zeilinger
 * 13.08.2010, 12:07:07
 */
package base.datahandlertools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import prementalapparatus.symbolization.representationsymbol.clsSymbolVisionEntryExpression;
import prementalapparatus.symbolization.representationsymbol.itfGetDataAccessMethods;
import prementalapparatus.symbolization.representationsymbol.itfGetSymbolName;
import prementalapparatus.symbolization.representationsymbol.itfIsContainer;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.clsAssociationAttribute;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsPhysicalRepresentation;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 13.08.2010, 12:07:07
 * 
 */
public class clsDataStructureConverter {
	//tbd - actually not defined and required - may replace the actual conversion of homeostatic input (Bloodsugar, stomachtension, etc.)  

//	public static clsDataStructurePA convertHomeostaticSymbolsToPsychicDataStructures(){
//		//tbd - actually not defined and required - may replace the actual conversion of homeostatic input (Bloodsugar, stomachtension, etc.)  
//		return null; 
//	}
	
	/**
	 * Converts the incomming external symbols to either TP's or TPM's and returns them in the form of a clsDataStructurePA.
	 * This is done by invoking the methods given by the interface of every symbol type.
	 *
	 * @since 20.07.2011 11:12:29
	 *
	 * @param poSymbolObject
	 * @return clsDataStructurePA
	 */
	public static clsDataStructurePA convertExtSymbolsToPsychicDataStructures(itfSymbol poSymbolObject){
			if(poSymbolObject instanceof itfIsContainer) {return convertSymbolsToTPM(poSymbolObject);} 
			else {return convertSymbolsToTP(poSymbolObject);}				
	}
	
	private static clsDataStructurePA convertSymbolsToTP(itfSymbol poSymbolObject){
		Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();
		
		eDataType eDataStructureType = eDataType.TP;
		eContentType oContentType = eContentType.valueOf(((itfGetSymbolName)poSymbolObject).getSymbolType());
		Object oContent = "DEFAULT";
						
		if (oMethods.length != 2) {
			throw new java.lang.IllegalArgumentException("can only convert symbols with excatly 1 getMethod (except getSymbolObjects()). ("+eDataStructureType.toString()+"; "+oContentType+"; "+oMethods.length+")");
		}
		
		//the interface forces to have the methods, so this is fairly secure, not very beautiful though. 
		//see pa._v38.symbolization.representationsymbol for the interfaces and the implementing classes
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
		
		return clsDataStructureGenerator.generateDataStructure(eDataStructureType, new clsPair <eContentType, Object>(oContentType, oContent)); 
	}
	
	private static clsDataStructurePA convertSymbolsToTPM(itfSymbol poSymbolObject){
		Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();

		clsThingPresentationMesh oTPM =  null; 

		eContentType oContentType = eContentType.valueOf(((itfGetSymbolName)poSymbolObject).getSymbolType());
		String oContent = ((itfIsContainer)poSymbolObject).getSymbolMeshContent().toString();
		ArrayList<clsPhysicalRepresentation> oAssociatedContent = new ArrayList<clsPhysicalRepresentation>();
		ArrayList<clsPhysicalRepresentation> oExternalAssociatedTPM = new ArrayList<clsPhysicalRepresentation>();
		ArrayList<clsThingPresentation> oExternalAssociatedTP = new ArrayList<clsThingPresentation>();
		
		for(Method oM : oMethods){
			if (oM.getName().equals("getSymbolObjects")) {
				continue;
			}
			if (oM.getName().equals("getSymbolAction")){
			    Object oAction = null;
                try {
                    oAction = oM.invoke(poSymbolObject,new Object[0]);
                } catch (IllegalAccessException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                }
			    if(oAction!=null){
			  
			        clsThingPresentationMesh oTPMAction = (clsThingPresentationMesh) convertSymbolsToTPM((itfSymbol)oAction);
			        oExternalAssociatedTPM.add(oTPMAction);
			    }
			    
			    continue;
	        
		        
		            
			}
			if(oM.getName().equals("getObjectSymbolVisionEntry")){
	             Object oAction = null;
	                try {
	                    oAction = oM.invoke(poSymbolObject,new Object[0]);
	                } catch (IllegalAccessException e) {
	                    // TODO (herret) - Auto-generated catch block
	                    e.printStackTrace();
	                } catch (IllegalArgumentException e) {
	                    // TODO (herret) - Auto-generated catch block
	                    e.printStackTrace();
	                } catch (InvocationTargetException e) {
	                    // TODO (herret) - Auto-generated catch block
	                    e.printStackTrace();
	                }
	                if(oAction!=null){
	                    clsThingPresentationMesh oTPMObject = (clsThingPresentationMesh) convertSymbolsToTPM((itfSymbol)oAction);
	                    oExternalAssociatedTPM.add(oTPMObject);
	                }
	                
	                continue;
			}
			
			clsThingPresentation oTP = null;
			eContentType oContentTypeTP = eContentType.DEFAULT; 
			Object oContentTP = "DEFAULT";
			//TODO HZ 16.08.2010: The method removePrefix is used in order to read out´the content type of a sub-symbol. 
			//As this operation is not supported by the provided interfaces (It is presumed that objects are 
			//received in the form of TPMs), removePrefix is taken from the ARSi09 implementation - however it is a dirty hack
			//and has to be removed when the symbolization is restructured.
			oContentTypeTP = eContentType.valueOf(removePrefix(oM.getName())); 
			
			
	         if (oM.getName().equals("getExpressions")){
	             ArrayList<clsSymbolVisionEntryExpression> oExprList = new ArrayList<clsSymbolVisionEntryExpression>();
	             try {
                    oExprList =  (ArrayList<clsSymbolVisionEntryExpression>) oM.invoke(poSymbolObject,new Object[0]);
                } catch (IllegalAccessException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO (herret) - Auto-generated catch block
                    e.printStackTrace();
                }
	             for(clsSymbolVisionEntryExpression expr: oExprList){
	                 oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <eContentType, Object>(eContentType.valueOf(expr.getSymbolType()), expr.getExpressionValue())); 
	                 oExternalAssociatedTP.add(oTP); 
	             }

	              
	              continue;
	            }
			
			//FIXME HZ! For this part a new solution has to be found 
			//Certain types of content types are mapped together
			
//			if(oContentTypeTP.equals("ObjectPosition") || oContentTypeTP.equals("Distance")){
//				oContentTypeTP = "LOCATION"; 
//			}
			
			if(oContentTypeTP.equals(eContentType.ObjectPosition)) {
				oContentTypeTP = eContentType.POSITION; 
			}
			
			if (oContentTypeTP.equals(eContentType.Distance)) {
				oContentTypeTP = eContentType.DISTANCE;
			}
			
	         if(oContentTypeTP.equals(eContentType.CarringVariable)) {
	                oContentTypeTP = eContentType.CARRYING; 
	            }
			
			//          if (oContentTypeTP.equals(eContentType.Brightness)) {
			//             oContentTypeTP = eContentType.BRIGHTNESS;
			//         }
			
		
			//Method oTest = oM.getClass().getDeclaredMethod("", arg1)
			
//			if(oContentTypeTP.equals(eContentType.Odor)) {
//                oContentTypeTP = eContentType.Odor; 
//                oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <eContentType, Object>(oContentTypeTP, oContentTP)); 
//                oAssociatedContent.add(oTP); 
//            }
			
			if (oM.getName().equals("getDebugSensorArousal")){
//              oContentTypeTP = eContentType.Brightness;
//              oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <eContentType, Object>(oContentTypeTP, oContentTP)); 
//              oAssociatedContent.add(oTP); 
			}
			if ((oM.getName().equals("getDebugSensorArousal")) ||
	                (oM.getName().equals("getExactDebugAngle"))) {}
	
			/*if ((oM.getName().equals("getExactDebugX")) || 
				(oM.getName().equals("getExactDebugY")) || 
				(oM.getName().equals("getDebugSensorArousal")) ||
				(oM.getName().equals("getExactDebugAngle"))  )
				{
					//do nothing, as this is not wanted information in the psyApperatus
				}
		/*		else if(oM.getName().equals("getPerceivedAction")){
				    oContentTypeTP = eContentType.PERCEIVEDACTION;
				    
	                ArrayList<Object> oContents = new ArrayList<Object>();
				    try {
				        oContents = (ArrayList<Object>)oM.invoke(poSymbolObject,new Object[0]);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    for(Object oCont: oContents){
                        //clsThingPresentationMesh oExtTPM = (clsThingPresentationMesh)clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(oContentType, new ArrayList<clsThingPresentation>(), oCont.toString()));
                        //oExternalAssociatedContent.add(oExtTPM);
                        oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <eContentType, Object>(oContentTypeTP, oCont)); 
                        oAssociatedContent.add(oTP); 
                    }
                    continue;
				}*/
				{
					try {
						oContentTP = oM.invoke(poSymbolObject,new Object[0]);
						if (oContentTP != null)
						{
		                  oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <eContentType, Object>(oContentTypeTP, oContentTP)); 
		                   oAssociatedContent.add(oTP); 
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
					

				}
		}
		oTPM = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM,	new clsTriple<eContentType, Object, Object>(oContentType, oAssociatedContent, oContent)); 
		for(clsPhysicalRepresentation oVal: oExternalAssociatedTPM){
		    oTPM.addExternalAssociation(clsDataStructureGenerator.generateASSOCIATIONPRI(eContentType.TPM, oTPM, (clsThingPresentationMesh) oVal, 1.0));
		    
		}
		for(clsThingPresentation tp : oExternalAssociatedTP){
		    oTPM.addExternalAssociation(new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.ASSOCIATIONATTRIBUTE), 
		            oTPM, 
		            tp)); 
		}
		return oTPM; 	
	}
	
	/**
     * DOCUMENT - insert description
     *
     * @author herret
     * @since 25.09.2014 09:34:15
     *
     * @return
     */
    private static Integer setID() {
        // TODO (herret) - Auto-generated method stub
        return null;
    }

    private static String removePrefix(String poName) {
		if (poName.startsWith("get")) {
			poName = poName.substring(3);
		}
				
		return poName;
	}
	
	public static ArrayList<clsSecondaryDataStructure> convertALtoALSecondaryDS(ArrayList<clsDataStructurePA> poInput) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		for (clsDataStructurePA oElement : poInput) {
			oRetVal.add((clsSecondaryDataStructure) oElement);
		}
		
		return oRetVal;
	}
	
	
//	/**
//	 * Convert an ArrayList with TPM-Containers to a TI-Container
//	 *
//	 * @since 06.07.2011 09:55:25
//	 *
//	 * @param oInput
//	 * @return
//	 * 
//	 * 
//	 */
//	//FIXME AW: Remove this function as it is not used
//	public static clsPrimaryDataStructureContainer convertTPMContToTICont(ArrayList<clsPrimaryDataStructureContainer> oInput) {
//		//Convert ArrayLists-Containers with TP and TPM to one container TI
//		
//		//New data structures for a Template Image
//		ArrayList<clsPhysicalRepresentation> oDataStructures = new ArrayList<clsPhysicalRepresentation>();	//The Datastructures have to be converted to from clsDataStructurePA to clsPhysicalRepresentation to fit the template image
//		//Total List of associations for the container
//		ArrayList<clsAssociation> oNewContainerAssociations = new ArrayList<clsAssociation>();
//		
//		//For each container in the arraylist of containers
//		for (clsPrimaryDataStructureContainer oContainer : oInput) {
//			//Add the Data structure to the list for the template image
//			oDataStructures.add((clsPhysicalRepresentation)oContainer.getMoDataStructure());
//			for (clsAssociation oContainerAss : oContainer.getMoAssociatedDataStructures()) {
//				try {
//					if ((oContainerAss.getRootElement().getMoDSInstance_ID() != oContainer.getMoDataStructure().getMoDSInstance_ID()) && (oContainerAss.getLeafElement().getMoDSInstance_ID() != oContainer.getMoDataStructure().getMoDSInstance_ID())) {
//						throw new Exception("Error in convertTPMContToTICont: The associated element is not associated with the data structure in the container");
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				oNewContainerAssociations.add(oContainerAss);
//			}
//			
//		}
//		clsTriple<String, ArrayList<clsPhysicalRepresentation>, Object> oContent = new clsTriple<String, ArrayList<clsPhysicalRepresentation>, Object>("PERCEIVEDIMAGE", oDataStructures, "PERCEPTION");
//		clsTemplateImage oConstructedImage = null; //(clsTemplateImage)clsDataStructureGenerator.generateTI(oContent);
//		//Set instanceID
//		//oConstructedImage.setMoDSInstance_ID(oConstructedImage.hashCode());
//		
//		clsPrimaryDataStructureContainer oRetVal = new clsPrimaryDataStructureContainer(oConstructedImage, oNewContainerAssociations);
//		
//		return oRetVal;
//	}
//	
//	
//	//AW 2011-05-19 New function
//	/**
//	 * DOCUMENT (wendt) - insert description
//	 *
//	 * @since 06.07.2011 09:54:41
//	 *
//	 * @param oInput
//	 * @return
//	 * 
//	 * Convert an container with a TI to an ArrayList with TPM-containers
//	 */
//	public static ArrayList<clsPrimaryDataStructureContainer> convertTIContToTPMCont(clsPrimaryDataStructureContainer oInput) {
//		//Convert one container with TI to ArrayLists-Containers with TP and TPM
//		
//		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
//		
//		ArrayList<clsAssociation> oAllAss = new ArrayList<clsAssociation>();
//		oAllAss.addAll(oInput.getMoAssociatedDataStructures());
//		
//		try {
//			if (oInput.getMoDataStructure() instanceof clsTemplateImage) {
//				
//				clsTemplateImage oInputDataStructure = (clsTemplateImage)oInput.getMoDataStructure();
//				
//				for (clsAssociation oAss : oInputDataStructure.getMoAssociatedContent()) {
//					clsPhysicalRepresentation oDS = (clsPhysicalRepresentation)oAss.getLeafElement();
//					ArrayList<clsAssociation> oContainerAss = new ArrayList<clsAssociation>();
//
//					ListIterator<clsAssociation> oAllAssLI = oAllAss.listIterator();	//IMPORTANT: The listiterator has to be initialized here, for each time this list is used
//					
//					while (oAllAssLI.hasNext()) {
//						clsAssociation oSingleAss = oAllAssLI.next();
//						//In this conversation information is lost, i.e. all clsAssociationPrimary, because they are only linked between template images. 
//						//Therefore these structures have to be removed here
//						if (oSingleAss instanceof clsAssociationPrimary) {
//							oAllAssLI.remove();
//						}
//						else if (oSingleAss.getRootElement().getMoDSInstance_ID()==oDS.getMoDSInstance_ID()) {	//Compare ID of the structure in the TI and the root element in the association
//
//							oContainerAss.add(oSingleAss);
//							oAllAssLI.remove();
//						}
//
//					}
//					
//					oRetVal.add(new clsPrimaryDataStructureContainer(oDS, oContainerAss));
//				}
//				
//				if (oAllAss.isEmpty()==false) {
//					throw new Exception("Error in convertTIContToTPMCont: Not all associations could be assigned a data structure container");
//				}
//				
//			} else {
//				throw new Exception("Error in convertTIContToTPMCont: The Input data structure is no clsTemplateImage");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return oRetVal;
//	}
//	
//	public static ArrayList<clsPrimaryDataStructureContainer> convertTPMImageToTPMContainer(clsThingPresentationMesh poTPM) {
//		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
//		
//		for (clsAssociation oEnhTPMAss : poTPM.getMoAssociatedContent()) {
//			clsThingPresentationMesh oTPM = (clsThingPresentationMesh) oEnhTPMAss.getLeafElement();
//			
//			ArrayList<clsAssociation> oExtAss = new ArrayList<clsAssociation>();
//			oExtAss.addAll(oTPM.getExternalMoAssociatedContent());
//			oRetVal.add(new clsPrimaryDataStructureContainer(oTPM, oExtAss));
//		}
//		
//		return oRetVal;
//	}
//	
//	/**
//	 * Convert a mesh into the old container structure
//	 * 
//	 * (wendt)
//	 *
//	 * @since 02.01.2012 22:28:39
//	 *
//	 * @param poMesh
//	 * @return
//	 */
//	public static clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> TEMPconvertMeshToContainers(clsThingPresentationMesh poMesh) {
//		clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>> oRetVal = null;
//		clsPrimaryDataStructureContainer oContainerA = null;
//		ArrayList<clsPrimaryDataStructureContainer> oContainerListB = new ArrayList<clsPrimaryDataStructureContainer>();
//		
//		//1. Extract all images in the mesh
//		ArrayList<clsThingPresentationMesh> oTPMList = clsDataStructureTools.getAllTPMImages(poMesh, 1);
//		//2. For each Image
//		for (clsThingPresentationMesh oTPM : oTPMList) {
//			//2.1 Remove all AssPri in the external associations if the TPM is no RI or PI
//			if (((oTPM.getMoContentType().equals(eContentType.PI.toString())==true || oTPM.getMoContentType().equals(eContentType.RI.toString())==true))) {
//				//Go through external associations
//				ArrayList<clsAssociation> oRemoveObjectList  = new ArrayList<clsAssociation>();
//				for (clsAssociation oAss : oTPM.getExternalMoAssociatedContent()) {
//					if (oAss instanceof clsAssociationPrimary) {
//						oRemoveObjectList.add(oAss);
//					}
//				}
//				
//				//Remove objects from the list
//				for (clsAssociation oAss : oRemoveObjectList) {
//					oTPM.getExternalMoAssociatedContent().remove(oAss);
//				}
//			}
//			
//			//2.3 Convert the TPM-Image to a TI-Image
//			//Get all objects from the image
////			ArrayList<clsPhysicalRepresentation> oInternalTPM = new ArrayList<clsPhysicalRepresentation>();
////			for (clsAssociation oAss : oTPM.getMoAssociatedContent()) {
////				oInternalTPM.add((clsThingPresentationMesh) oAss.getLeafElement());
////			}
//			
//			//Create a new TI
//			clsTemplateImage oTI = new clsTemplateImage(new clsTriple<Integer, eDataType, String>(oTPM.getMoDS_ID(), eDataType.TI, oTPM.getMoContentType()), new ArrayList<clsAssociation>(), oTPM.getMoContent());
//			//clsTemplateImage oTI = clsDataStructureGenerator.generateTI(new clsTriple<String, new ArrayList<clsPhysical>, Object>(oTPM.getMoContentType(), oInternalTPM, oTPM.getMoContent()));
//			
//			//Move all associations to the TI
//			ArrayList<clsAssociation> oContainerAssociationList = clsDataStructureTools.TEMPmoveAllAssociations(oTI, oTPM);
//			
//			//Remove all temporal associations in the external association list
//			ArrayList<clsAssociation> oRemoveList = new ArrayList<clsAssociation>();
//			for (clsAssociation oAss : oContainerAssociationList) {
//				if (oAss instanceof clsAssociationTime) {
//					oRemoveList.add(oAss);
//				}
//			}
//			
//			for (clsAssociation oAss : oRemoveList) {
//				oContainerAssociationList.remove(oAss);
//			}
//			
//			
//			//2.2 Create a list of external associations and transfer the current list to this list
//			//ArrayList<clsAssociation> oContainerAssociationList = new ArrayList<clsAssociation>();
//			//oContainerAssociationList.addAll(oTPM.getExternalMoAssociatedContent());
//			//for (clsAssociation oAss : oContainerAssociationList) {
//			///7	
//			//}
//			
//			//2.4 Create a container with the TI + ext. associations
//			clsPrimaryDataStructureContainer oContainer = new clsPrimaryDataStructureContainer(oTI, oContainerAssociationList);
//			
//			//2.5 If the image is a PI, set it as perception
//			if (oTI.getMoContentType().equals(eContentType.PI.toString())) {
//				oContainerA = oContainer;
//			//2.6 If the image is a RI, set it as associated memory
//			} else if (oTI.getMoContentType().equals(eContentType.RI.toString())) {
//				oContainerListB.add(oContainer);
//			}
//		}
//		
//		oRetVal = new clsPair<clsPrimaryDataStructureContainer, ArrayList<clsPrimaryDataStructureContainer>>(oContainerA, oContainerListB);
//		
//
//		
//		return oRetVal;
//	}

}
