/**
 * clsDataStructureConverter.java: DecisionUnits - pa._v38.memorymgmt.generator
 * 
 * @author zeilinger
 * 13.08.2010, 12:07:07
 */
package pa._v38.memorymgmt.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ListIterator;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.symbolization.representationsymbol.itfGetDataAccessMethods;
import pa._v38.symbolization.representationsymbol.itfGetSymbolName;
import pa._v38.symbolization.representationsymbol.itfIsContainer;
import pa._v38.symbolization.representationsymbol.itfSymbol;

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
		String oContentType = ((itfGetSymbolName)poSymbolObject).getSymbolType();
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
		
		return clsDataStructureGenerator.generateDataStructure(eDataStructureType, new clsPair <String, Object>(oContentType, oContent)); 
	}
	
	private static clsDataStructurePA convertSymbolsToTPM(itfSymbol poSymbolObject){
		Method[] oMethods = ((itfGetDataAccessMethods)poSymbolObject).getDataAccessMethods();
		
		clsThingPresentationMesh oTPM =  null; 
		String oContentType = ((itfGetSymbolName)poSymbolObject).getSymbolType();
		String oContent = ((itfIsContainer)poSymbolObject).getSymbolMeshContent().toString();
		ArrayList<clsPhysicalRepresentation> oAssociatedContent = new ArrayList<clsPhysicalRepresentation>();
		
		for(Method oM : oMethods){
			if (oM.getName().equals("getSymbolObjects")) {
				continue;
			}
			
			clsThingPresentation oTP = null;
			String oContentTypeTP = "DEFAULT"; 
			Object oContentTP = "DEFAULT";
			//TODO HZ 16.08.2010: The method removePrefix is used in order to read out�the content type of a sub-symbol. 
			//As this operation is not supported by the provided interfaces (It is presumed that objects are 
			//received in the form of TPMs), removePrefix is taken from the ARSi09 implementation - however it is a dirty hack
			//and has to be removed when the symbolization is restructured.
			oContentTypeTP = removePrefix(oM.getName()); 
					
			//FIXME HZ! For this part a new solution has to be found 
			//Certain types of content types are mapped together
			
			if(oContentTypeTP.equals("ObjectPosition") || oContentTypeTP.equals("Distance")){
				oContentTypeTP = "LOCATION"; 
			}
		
			//Method oTest = oM.getClass().getDeclaredMethod("", arg1)
	
			try {
				oContentTP = oM.invoke(poSymbolObject,new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair <String, Object>(oContentTypeTP, oContentTP)); 
			oAssociatedContent.add(oTP); 
		}
		oTPM = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, 
										new clsTriple<String, Object, Object>(oContentType, oAssociatedContent, oContent)); 
		return oTPM; 	
	}
	
	private static String removePrefix(String poName) {
		if (poName.startsWith("get")) {
			poName = poName.substring(3);
		}
				
		return poName;
	}
	
	
	/**
	 * Convert an ArrayList with TPM-Containers to a TI-Container
	 *
	 * @since 06.07.2011 09:55:25
	 *
	 * @param oInput
	 * @return
	 * 
	 * 
	 */
	public static clsPrimaryDataStructureContainer convertTPMContToTICont(ArrayList<clsPrimaryDataStructureContainer> oInput) {
		//Convert ArrayLists-Containers with TP and TPM to one container TI
		
		//New data structures for a Template Image
		ArrayList<clsPhysicalRepresentation> oDataStructures = new ArrayList<clsPhysicalRepresentation>();	//The Datastructures have to be converted to from clsDataStructurePA to clsPhysicalRepresentation to fit the template image
		//Total List of associations for the container
		ArrayList<clsAssociation> oNewContainerAssociations = new ArrayList<clsAssociation>();
		
		//For each container in the arraylist of containers
		for (clsPrimaryDataStructureContainer oContainer : oInput) {
			//Add the Data structure to the list for the template image
			oDataStructures.add((clsPhysicalRepresentation)oContainer.getMoDataStructure());
			for (clsAssociation oContainerAss : oContainer.getMoAssociatedDataStructures()) {
				try {
					if ((oContainerAss.getRootElement().getMoDSInstance_ID() != oContainer.getMoDataStructure().getMoDSInstance_ID()) && (oContainerAss.getLeafElement().getMoDSInstance_ID() != oContainer.getMoDataStructure().getMoDSInstance_ID())) {
						throw new Exception("Error in convertTPMContToTICont: The associated element is not associated with the data structure in the container");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				oNewContainerAssociations.add(oContainerAss);
			}
			
		}
		clsTriple<String, ArrayList<clsPhysicalRepresentation>, Object> oContent = new clsTriple<String, ArrayList<clsPhysicalRepresentation>, Object>("PERCEIVEDIMAGE", oDataStructures, "PERCEPTION");
		clsTemplateImage oConstructedImage = (clsTemplateImage)clsDataStructureGenerator.generateTI(oContent);
		//Set instanceID
		//oConstructedImage.setMoDSInstance_ID(oConstructedImage.hashCode());
		
		clsPrimaryDataStructureContainer oRetVal = new clsPrimaryDataStructureContainer(oConstructedImage, oNewContainerAssociations);
		
		return oRetVal;
	}
	
	
	//AW 2011-05-19 New function
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 06.07.2011 09:54:41
	 *
	 * @param oInput
	 * @return
	 * 
	 * Convert an container with a TI to an ArrayList with TPM-containers
	 */
	public static ArrayList<clsPrimaryDataStructureContainer> convertTIContToTPMCont(clsPrimaryDataStructureContainer oInput) {
		//Convert one container with TI to ArrayLists-Containers with TP and TPM
		
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		ArrayList<clsAssociation> oAllAss = new ArrayList<clsAssociation>();
		oAllAss.addAll(oInput.getMoAssociatedDataStructures());
		
		try {
			if (oInput.getMoDataStructure() instanceof clsTemplateImage) {
				
				clsTemplateImage oInputDataStructure = (clsTemplateImage)oInput.getMoDataStructure();
				
				for (clsAssociation oAss : oInputDataStructure.getMoAssociatedContent()) {
					clsPhysicalRepresentation oDS = (clsPhysicalRepresentation)oAss.getLeafElement();
					ArrayList<clsAssociation> oContainerAss = new ArrayList<clsAssociation>();

					ListIterator<clsAssociation> oAllAssLI = oAllAss.listIterator();	//IMPORTANT: The listiterator has to be initialized here, for each time this list is used
					
					while (oAllAssLI.hasNext()) {
						clsAssociation oSingleAss = oAllAssLI.next();
						//In this conversation information is lost, i.e. all clsAssociationPrimary, because they are only linked between template images. 
						//Therefore these structures have to be removed here
						if (oSingleAss instanceof clsAssociationPrimary) {
							oAllAssLI.remove();
						}
						else if (oSingleAss.getRootElement().getMoDSInstance_ID()==oDS.getMoDSInstance_ID()) {	//Compare ID of the structure in the TI and the root element in the association

							oContainerAss.add(oSingleAss);
							oAllAssLI.remove();
						}

					}
					
					oRetVal.add(new clsPrimaryDataStructureContainer(oDS, oContainerAss));
				}
				
				if (oAllAss.isEmpty()==false) {
					throw new Exception("Error in convertTIContToTPMCont: Not all associations could be assigned a data structure container");
				}
				
			} else {
				throw new Exception("Error in convertTIContToTPMCont: The Input data structure is no clsTemplateImage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oRetVal;
	}
	
	public static ArrayList<clsPrimaryDataStructureContainer> convertTPMImageToTPMContainer(clsThingPresentationMesh poTPM) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for (clsAssociation oEnhTPMAss : poTPM.getMoAssociatedContent()) {
			clsThingPresentationMesh oTPM = (clsThingPresentationMesh) oEnhTPMAss.getLeafElement();
			
			ArrayList<clsAssociation> oExtAss = new ArrayList<clsAssociation>();
			oExtAss.addAll(oTPM.getExternalMoAssociatedContent());
			oRetVal.add(new clsPrimaryDataStructureContainer(oTPM, oExtAss));
		}
		
		return oRetVal;
	}

}
