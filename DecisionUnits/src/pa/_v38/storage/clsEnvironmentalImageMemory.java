/**
 * CHANGELOG
 *
 * 17.07.2012 wendt - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsSecondarySpatialTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.07.2012, 14:23:05
 * 
 */
public class clsEnvironmentalImageMemory extends clsShortTermMemory {

	private final clsWordPresentationMesh moEnvironmentalImage = clsMeshTools.createWPMImage(new ArrayList<clsSecondaryDataStructure>(), eContentType.ENVIRONMENTALIMAGE, eContent.ENVIRONMENTALIMAGE.toString());

	/**
	 * Constructor. Initialize the environental image
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 14:23:20
	 *
	 * @param pnMaxTimeValue
	 * @param pnMaxMemorySize
	 */
	public clsEnvironmentalImageMemory(int pnMaxTimeValue, int pnMaxMemorySize) {
		super(pnMaxTimeValue, pnMaxMemorySize);
	}
	
	/**
	 * @since 17.07.2012 20:25:59
	 * 
	 * @return the moEnvironmentalImage
	 */
	public clsWordPresentationMesh getEnvironmentalImage() {
		return moEnvironmentalImage;
	}
	
	/**
	 * Add all entities of the perceived image to the environmental image
	 * 
	 * (wendt)
	 *
	 * @since 17.07.2012 20:02:27
	 *
	 * @param poPerceivedImage
	 */
	public void addNewImage(clsWordPresentationMesh poPerceivedImage) {
		
		//Get all entities in the perceived image, which are already exists in the environmental image
		ArrayList<clsWordPresentationMesh> oAlreadyExistingEntityList = clsSecondarySpatialTools.getAlreadyExistingEntitiesInImage(this.moEnvironmentalImage, poPerceivedImage, true);
		
		//Remove all already existing entities from the image
		for (clsWordPresentationMesh oEntityToDelete : oAlreadyExistingEntityList) {
			//Delete the entity from the environmental image
			clsMeshTools.removeAssociationInObject(this.moEnvironmentalImage, oEntityToDelete);
			
			//Delete the entitiy from the memory
			this.removeMemory(oEntityToDelete);
		}
		
		ArrayList<clsWordPresentationMesh> oEntityListInPI = clsMeshTools.getAllSubWPMInWPMImage(poPerceivedImage);
		ArrayList<clsSecondaryDataStructure> oAddList = new ArrayList<clsSecondaryDataStructure>();
		//Save each entity separately in the STM structure. This is later used to delete entities from the environmental image
		for (clsWordPresentationMesh oEntity : oEntityListInPI) {
			//Delete all connections to the PI
			clsMeshTools.removeAssociationInObject(oEntity, poPerceivedImage);
			//Remove all connections also in the PP-Part
			clsMeshTools.removeAllTemporaryAssociationsTPM(clsMeshTools.getPrimaryDataStructureOfWPM(oEntity));
			
			this.saveToShortTimeMemory(oEntity);
			
			oAddList.add(oEntity);
		}
		
		 
		
		//Create association with the environmental image
		clsMeshTools.addSecondaryDataStructuresToWPMImage(this.moEnvironmentalImage, oAddList);
		
		//Remove the associationWordPresentationFrom the PI
		poPerceivedImage.setMoExternalAssociatedContent(new ArrayList<clsAssociation>());
		
		//Move all entities from the PI to the EI
		//clsMeshTools.moveAllAssociations(this.moEnvironmentalImage, poPerceivedImage);
		
		//The perceived image is consumed and is no more used.

	}
	
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 31.08.2011 07:29:39
	 *
	 */
	@Override
	public void updateTimeSteps() {
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oRemoveObjects = new ArrayList<clsPair<Integer, clsWordPresentationMesh>>();
		for (clsPair<Integer, clsWordPresentationMesh> oSingleMemory : moShortTimeMemory) {
			//Update the step count
			oSingleMemory.a++;
			//Remove memories, which are too old and haven´t been transferred to the long time memory
			if (oSingleMemory.a>mnMaxTimeValue) {
				oRemoveObjects.add(oSingleMemory);
			}
		}
		//Remove the overdue objects
		for (clsPair<Integer, clsWordPresentationMesh> oSingleRemoveObject : oRemoveObjects) {
			//Remove the entity from the environmental image
			clsMeshTools.removeAssociationInObject(this.moEnvironmentalImage, oSingleRemoveObject.b);
			
			//Remove the entity from the memory
			removeMemory(oSingleRemoveObject);
		}
	}
	

}
