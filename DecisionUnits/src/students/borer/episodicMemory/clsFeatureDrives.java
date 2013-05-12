package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.clsContainerDrive;
import students.borer.episodicMemory.tempframework.clsDrive;

/**
 * The class clsFeatureDrives represents the feature drives. It inherites the basic functionalities which are equal for each feature and implements specific functionalities for the feature drives. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsFeatureDrives extends clsFeature  {
	private clsContainerDrive moContainerDrives;

	/**
	 * Instantiates a new feature drives with the respective elements indicated in the container in the parameter. If
	 * triggering encoding or spontaneous retrieval for this feature should be disabled, mnTriggerEncodingEnabled respectively
	 * mnSpontaneousRetrievalEnabled have to be set false.
	 * @param poDriveList The container with the individual drives of this situation
	 */
	@SuppressWarnings("unchecked")
	public clsFeatureDrives(clsContainerDrive poDriveList) {
		super();
		mnTriggerEncodingEnabled = true;
		mnSpontaneousRetrievalEnabled = false;
		
		moContainerDrives = poDriveList;
		for(int i=0; i<poDriveList.size(); i++){
			clsDrive oDrive = (clsDrive)poDriveList.get(i);
			moFeatureElements.add( new clsElementDrive( oDrive ));
		}
	}
	/**
	 * Returns the initially delivered drives in a container (clsContainerDrives)
	 */
	@Override
	public Object getContainer() {
		return moContainerDrives;
	}
	@Override
	public boolean checkIfSameType(clsFeature poFeature) {
		if(poFeature instanceof clsFeatureDrives) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Drives";
	}
}
