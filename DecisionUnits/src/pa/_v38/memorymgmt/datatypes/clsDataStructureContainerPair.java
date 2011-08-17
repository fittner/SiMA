/**
 * CHANGELOG
 *
 * 24.07.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

/**
 * A pair of a primary and a secondary data structure container, 
 * in order to get the primary and secondary data structure container together 
 * 
 * @author wendt
 * 24.07.2011, 14:14:43
 * 
 */
public class clsDataStructureContainerPair {
	private clsSecondaryDataStructureContainer moSContainer;
	private clsPrimaryDataStructureContainer moPContainer;
	
	public clsDataStructureContainerPair(clsSecondaryDataStructureContainer poSContainer, clsPrimaryDataStructureContainer poPContainer) {
		moSContainer = poSContainer;
		moPContainer = poPContainer;
	}
	
	public clsPrimaryDataStructureContainer getPrimaryComponent() {
		return moPContainer;
	}
	
	public void setPrimaryComponent(clsPrimaryDataStructureContainer poPContainer)  {
		moPContainer = poPContainer;
	}
	
	public clsSecondaryDataStructureContainer getSecondaryComponent() {
		return moSContainer;
	}
	
	public void setSecondaryComponent(clsSecondaryDataStructureContainer poSContainer)  {
		moSContainer = poSContainer;
	}
	
}
