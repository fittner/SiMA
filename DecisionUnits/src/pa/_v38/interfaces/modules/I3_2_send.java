/**
 * I1_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;

/**
 * Libidinous and aggressive drives represented by more or less complex associated thing presentations containing at least drive source, aim of drive, and drive object together with the tensions at the various drive sources are forwarded from F3 to F4.
 * 
 * @author deutsch
 * 18.05.2010, 13:40:52
 * 
 */
public interface I3_2_send {
	public void send_I3_2(ArrayList< clsPair<clsDriveMeshOLD, clsDriveDemand> >poHomeostaticDriveDemands);
}
