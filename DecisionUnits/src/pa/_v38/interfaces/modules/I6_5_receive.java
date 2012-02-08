/**
 * I5_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:37:21
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;


import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.tools.clsTriple;

/**
 * Drive wishes are transported from F8 to F20. The contents are in the form of word presentations, thing presentations, and affects.
 * 
 * @author deutsch
 * 11.08.2009, 14:37:21
 * 
 */
public interface I6_5_receive {
	public void receive_I6_5(ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> poDriveList);
}
