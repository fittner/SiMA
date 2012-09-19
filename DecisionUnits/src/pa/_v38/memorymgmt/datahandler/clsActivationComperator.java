/**
 * CHANGELOG
 *
 * Aug 30, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datahandler;

import java.util.Comparator;

//import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Aug 30, 2012, 5:25:29 PM
 * 
 */
public class clsActivationComperator  implements Comparator{
	
	/* (non-Javadoc)
	 *
	 * @since Aug 30, 2012 5:10:24 PM
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Object oTPM1, Object oTPM2) {
		try {
			return 0;//(int) Math.abs(((clsThingPresentationMesh)oTPM1).getOverallActivationLevel() - ((clsThingPresentationMesh)oTPM2).getOverallActivationLevel());
		}
		catch (Exception e) {
			return 0;
		}
		
	}
	
}
