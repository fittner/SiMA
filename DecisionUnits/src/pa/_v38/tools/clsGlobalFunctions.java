/**
 * CHANGELOG
 *
 * 25.06.2011 wendt - File created
 *
 */
package pa._v38.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 25.06.2011, 09:29:24
 * 
 * Here, "diverse" functions can be put, which are used in several modules
 * 
 */
public class clsGlobalFunctions {
	
	public static double calculateAbsoluteAffect(clsPrimaryDataStructureContainer poImage) {
		double rAbsoluteAffect;
		
		rAbsoluteAffect = 0;
		
		for (clsAssociation oAss: poImage.getMoAssociatedDataStructures()) {
			if (oAss instanceof clsAssociationDriveMesh) {
				rAbsoluteAffect += java.lang.Math.abs(((clsDriveMesh)oAss.getLeafElement()).getPleasure());
			}
		}
		return rAbsoluteAffect;
	}
	
	/**
	 * For each container, where the associations are not bound, the hash-code from the data structure was taken as id and
	 * all associations in the associated data structures root elements were set with the instance ID of the container
	 * data structures.
	 * 
	 * This function shall be executed as soon as more TPMs are used in one container and every time something is loaded 
	 * from the memory
	 * 
	 * @since 06.07.2011 15:03:52
	 *
	 * @param <E>
	 * @param poInput
	 * @return
	 **/
	@SuppressWarnings("unchecked")
	public static <E extends clsDataStructureContainer> ArrayList<E> createInstanceFromType(ArrayList<E> poInput) {
		ArrayList<E> oRetVal = (ArrayList<E>)deepCopy(poInput);
		
		//Set Unique IDs for all root elements
		for (E oElement : oRetVal) {
			int oInstanceID;	//
			//Check if the root element already have an unique ID
			if (oElement.getMoDataStructure().getMoDSInstance_ID() == 0) {
				oInstanceID = oElement.getMoDataStructure().hashCode();
				oElement.getMoDataStructure().setMoDSInstance_ID(oInstanceID);
			} else {
				oInstanceID = oElement.getMoDataStructure().getMoDSInstance_ID();
			}
			
			//Go through all associations in the container and complete the ones, which are missing or different from the root element
			for (clsAssociation oAssStructure : oElement.getMoAssociatedDataStructures()) {
				//Change ID only if the association root element is the same type (ID) as the data structure
				if ((oAssStructure.getRootElement().getMoDSInstance_ID()!=oInstanceID) && (oElement.getMoDataStructure().getMoDS_ID()==oAssStructure.getRootElement().getMoDS_ID())) {
					oAssStructure.getRootElement().setMoDSInstance_ID(oInstanceID);
				}
			}
		}
		
		return oRetVal;
	}
	
	//Copied from clsModuleBase.java
	/**
	 * Deepcopy for arraylist objects. Clones not only the arraylist but also all elements that are stored within the arraylist.
	 *
	 * @since 13.07.2011 13:27:48
	 *
	 * @param other
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static java.util.ArrayList deepCopy(java.util.ArrayList other) {
		java.util.ArrayList clone = null;
		if (other != null) {
			clone = new java.util.ArrayList();
			
			for (Object entry:other) {
				try {
					if (!(entry instanceof Cloneable)) {
						clone.add(entry);
					} else {
						Class<?> clzz = entry.getClass();
				    	Method   meth = clzz.getMethod("clone", new Class[0]);
				    	Object   dupl = meth.invoke(entry, new Object[0]);
				    	clone.add(dupl);
					}
				} catch (Exception e) {
					clone.add(entry);
					// no deep copy possible.
				}
			}
		}
				
		return clone;
	}
}


