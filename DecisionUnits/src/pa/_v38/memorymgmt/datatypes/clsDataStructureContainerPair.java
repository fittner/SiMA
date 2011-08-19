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
public class clsDataStructureContainerPair implements Cloneable {
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
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDataStructureContainerPair oClone = new clsDataStructureContainerPair(null, null);
        	
        	if (moSContainer != null) {
        		oClone.moSContainer = (clsSecondaryDataStructureContainer) this.moSContainer.clone();
        	}
        	
        	if (moPContainer != null) {
        		oClone.moPContainer = (clsPrimaryDataStructureContainer) this.moPContainer.clone();
        	}
        		
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
}
