/**
 * I2_17_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;

import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;

/**
 * The eight drives - the four partial sexual drives divided into libidinous and aggressive components - as well as the total amount of libido tension are transmitted from F43 to F42.
 * 
 * @author deutsch
 * 03.03.2011, 15:36:55
 * 
 */
public interface I3_3_receive {
	public void receive_I3_3(ArrayList< clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > > poDriveCandidates);
}
