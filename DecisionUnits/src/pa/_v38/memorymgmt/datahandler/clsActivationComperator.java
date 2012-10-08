/**
 * CHANGELOG
 *
 * Aug 30, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datahandler;

import java.util.Comparator;

import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

//import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Aug 30, 2012, 5:25:29 PM
 * 
 */
public class clsActivationComperator  implements Comparator<clsDataStructureContainer>{
	
	/* (non-Javadoc)
	 *
	 * @since Aug 30, 2012 5:10:24 PM
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(clsDataStructureContainer oContainerTPM1, clsDataStructureContainer oContainerTPM2) {
		try {
			
			double rActivationLevel1 = ((clsThingPresentationMesh) oContainerTPM1.getMoDataStructure()).getAggregatedActivationValue();			
			double rActivationLevel2 = ((clsThingPresentationMesh) oContainerTPM2.getMoDataStructure()).getAggregatedActivationValue();
			
			if(rActivationLevel1 > rActivationLevel2){
				return -1;
			}
			else if(rActivationLevel1 == rActivationLevel2) {
				return 0;
			}
			else if(rActivationLevel1 < rActivationLevel2) {
				return 1;
			}
			
			return 0;//
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
}
