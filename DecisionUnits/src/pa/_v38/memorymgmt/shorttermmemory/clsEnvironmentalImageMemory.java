/**
 * CHANGELOG
 *
 * 17.07.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.shorttermmemory;

import java.util.ArrayList;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import pa._v38.interfaces.itfGraphData;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eRadius;
import secondaryprocess.datamanipulation.clsEntityTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import secondaryprocess.datamanipulation.clsSecondarySpatialTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.07.2012, 14:23:05
 * 
 */
public class clsEnvironmentalImageMemory extends clsShortTermMemory<clsWordPresentationMesh> implements itfGraphData, itfInspectorInternalState{

	private final clsWordPresentationMesh moEnvironmentalImage = clsMeshTools.createWPMImage(new ArrayList<clsSecondaryDataStructure>(), eContentType.ENVIRONMENTALIMAGE, eContent.ENVIRONMENTALIMAGE.toString());
	private final clsWordPresentationMesh moEnhancedEnvironmentalImage = clsMeshTools.createWPMImage(new ArrayList<clsSecondaryDataStructure>(), eContentType.ENHANCEDENVIRONMENTALIMAGE, eContent.ENHANCEDENVIRONMENTALIMAGE.toString());

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
		super(pnMaxTimeValue, pnMaxMemorySize, clsWordPresentationMesh.getNullObject());
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
	 * @since 09.10.2012 17:58:54
	 * 
	 * @return the moEnhancedEnvironmentalImage
	 */
	public clsWordPresentationMesh getEnhancedEnvironmentalImage() {
		return moEnhancedEnvironmentalImage;
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
		
		//Remove all already existing entities to make way for the new entities
		removeAllDuplicateEntitiesInImage(this.moEnvironmentalImage, poPerceivedImage);
		removeAllDuplicateEntitiesInImage(this.moEnhancedEnvironmentalImage, poPerceivedImage);
		
		
		ArrayList<clsWordPresentationMesh> oEntityListInPI = clsMeshTools.getAllSubWPMInWPMImage(poPerceivedImage);
		ArrayList<clsSecondaryDataStructure> oAddList = new ArrayList<clsSecondaryDataStructure>();
		//Save each entity separately in the STM structure. This is later used to delete entities from the environmental image
		for (clsWordPresentationMesh oEntity : oEntityListInPI) {
			//--- Prepare the entities --- //
			
			//Delete all connections to the PI
			clsMeshTools.removeAssociationInObject(oEntity, poPerceivedImage);
			//Remove all connections also in the PP-Part
			clsMeshTools.removeAllTemporaryAssociationsTPM(clsMeshTools.getPrimaryDataStructureOfWPM(oEntity));
			
			//--- Prepare the enhanced environmental image ---//
			//Remove all entities of the same type, which has no position from the enhanced entities image
			removeAllEntitiesOfTheSameTypeWithNoPosition(this.moEnhancedEnvironmentalImage, oEntity);
			
			//--- Save to memory ---//
			this.saveToShortTimeMemory(oEntity);
			
			//Add to the list of entities, which shall be added
			oAddList.add(oEntity);
		}
		
		//Create association with the environmental image
		clsMeshTools.addSecondaryDataStructuresToWPMImage(this.moEnvironmentalImage, oAddList);
		
		//Create association with the enhanced environmental image
		clsMeshTools.addSecondaryDataStructuresToWPMImage(this.moEnhancedEnvironmentalImage, oAddList);
		
		//Remove the associationWordPresentationFrom the PI
		poPerceivedImage.setMoExternalAssociatedContent(new ArrayList<clsAssociation>());
		
		//Move all entities from the PI to the EI
		//clsMeshTools.moveAllAssociations(this.moEnvironmentalImage, poPerceivedImage);
		
		//The perceived image is consumed and is no more used.

	}
	
	/**
	 * Remove all entities, which are duplicate in an image
	 * 
	 * (wendt)
	 *
	 * @since 09.10.2012 18:02:27
	 *
	 * @param poImage: This is the image, where the duplicate images exist
	 * @param poPerceivedImage: This is the image with the new entities
	 */
	private void removeAllDuplicateEntitiesInImage(clsWordPresentationMesh poImage, clsWordPresentationMesh poPerceivedImage) {
		//Get all entities in the perceived image, which are already exists in the environmental image
		ArrayList<clsWordPresentationMesh> oAlreadyExistingEntityList = clsSecondarySpatialTools.getAlreadyExistingEntitiesInImage(poImage, poPerceivedImage, true);
		
		//Remove all already existing entities from the image
		for (clsWordPresentationMesh oEntityToDelete : oAlreadyExistingEntityList) {
			//Delete the entity from the environmental image
			clsMeshTools.removeAssociationInObject(poImage, oEntityToDelete);
			
			//Delete the entitiy from the memory
			this.removeMemory(oEntityToDelete);
		}
	}
	
	/**
	 * Get all entities in the image, which is the same type as the entity. Then remove those entities, where there is no position
	 * 
	 * (wendt)
	 *
	 * @since 09.10.2012 20:26:09
	 *
	 * @param poImage
	 * @param poEntity
	 */
	private void removeAllEntitiesOfTheSameTypeWithNoPosition(clsWordPresentationMesh poImage, clsWordPresentationMesh poEntity) {
		//Get a list of all entities in the image, which are of the same type
		ArrayList<clsSecondaryDataStructure> oSameEntityList = clsMeshTools.searchSecondaryDataStructureInImage(poImage, ePredicate.HASPART, poEntity.getMoDS_ID(), false);
		
		for (clsSecondaryDataStructure oEntity : oSameEntityList) {
			if (oEntity instanceof clsWordPresentationMesh) {
				clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oEntityPosition = clsEntityTools.getPosition((clsWordPresentationMesh) oEntity);
				//Remove the positions of these entities
				if (oEntityPosition.b==null && oEntityPosition.c==null) {
					//Delete the entity from the environmental image
					clsMeshTools.removeAssociationInObject(poImage, oEntity);
					
					//Delete the entitiy from the memory
					this.removeMemory((clsWordPresentationMesh)oEntity);
					
				}
			}
		}
	}
	
	/**
	 * Remove all entities in the environmental image and remove all positions of those entities in the enhanced environmental image
	 * 
	 * (wendt)
	 *
	 * @since 09.10.2012 20:42:49
	 *
	 */
	public void clearEnvironmentalImage() {
		//Get all entities
		ArrayList<clsWordPresentationMesh> oEntitiesInImageList = clsMeshTools.getAllSubWPMInWPMImage(this.moEnvironmentalImage);
		
		for (clsWordPresentationMesh oEntity : oEntitiesInImageList) {
			//Remove all entities from the environmental image
			clsMeshTools.removeAssociationInObject(this.moEnvironmentalImage, oEntity);
			
			//Remove all positions of the entities of the enhanced environmental image
			clsEntityTools.removePosition(oEntity);
		}
		
	}
	
	/**
	 * Get all TPM from the enhanced environmental image
	 * 
	 * (wendt)
	 *
	 * @since 10.10.2012 12:03:20
	 *
	 * @return
	 */
	public ArrayList<clsThingPresentationMesh> getAllTPMFromEnhancedEnvironmentalImage() {
		ArrayList<clsThingPresentationMesh> oResult = new ArrayList<clsThingPresentationMesh>();
		
		ArrayList<clsWordPresentationMesh> oWPMList = clsMeshTools.getAllSubWPMInWPMImage(moEnhancedEnvironmentalImage);
		
		for (clsWordPresentationMesh oWPM : oWPMList) {
			oResult.add(clsMeshTools.getPrimaryDataStructureOfWPM(oWPM));
		}
		
		return oResult;
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
			//Remove the entity from the environmental image (if it is there)
			clsMeshTools.removeAssociationInObject(this.moEnvironmentalImage, oSingleRemoveObject.b);
			//Remove the entity from the enhanced environmental image
			clsMeshTools.removeAssociationInObject(this.moEnhancedEnvironmentalImage, oSingleRemoveObject.b);
			
			//Remove the entity from the memory
			removeMemory(oSingleRemoveObject);
		}
	}

	/* (non-Javadoc)
	 *
	 * @since Oct 31, 2012 10:28:04 AM
	 * 
	 * @see pa._v38.interfaces.itfGraphData#getGraphData()
	 */
	@Override
	public ArrayList<Object> getGraphData() {
		ArrayList<Object> oRetVal = new ArrayList<Object>();
		oRetVal.add(moEnhancedEnvironmentalImage);
		return oRetVal;
	}

	@Override
	public String stateToTEXT() {
		return moEnvironmentalImage.toString();
	}
	

}
