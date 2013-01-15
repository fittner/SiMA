/**
 * CHANGELOG
 *
 * 09.11.2012 wendt - File created
 *
 */
package pa._v38.systemtest;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 09.11.2012, 11:49:18
 * 
 */
public class clsTester {
	private static clsTester moTesterInstance = null;
	
	private boolean mbActivated = false;
	
	private clsTester() {
		
	}
	
	public static clsTester getTester() {
		if (clsTester.moTesterInstance==null) {
			clsTester.moTesterInstance = new clsTester();
		}
		
		return clsTester.moTesterInstance;
	}
	

	/**
	 * @since 09.11.2012 11:52:55
	 * 
	 * @return the mbActivated
	 */
	public boolean isActivated() {
		return mbActivated;
	}

	/**
	 * @since 09.11.2012 11:52:55
	 * 
	 * @param mbActivated the mbActivated to set
	 */
	public void setActivated(boolean mbActivated) {
		this.mbActivated = mbActivated;
	}
	
	public void exeTestAssociationAssignment(clsThingPresentationMesh poImage) throws Exception {
		clsUnitTestTools.debugFindAllErroneousLinksInImage(poImage);
	}
	
}
