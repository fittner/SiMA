/**
 * CHANGELOG
 *
 * 07.10.2013 wendt - File created
 *
 */
package secondaryprocess.datamanipulation.meshprocessor;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.10.2013, 20:32:16
 * 
 */
public interface MeshProcessorInterface {
    public void setSafeControlMode(boolean safeMode);
    public void complementMesh(clsWordPresentationMesh toMesh, clsWordPresentationMesh fromMesh, boolean transferThingPresentationMeshes);
    
}
