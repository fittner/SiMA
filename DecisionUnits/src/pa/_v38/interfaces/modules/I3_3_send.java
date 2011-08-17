/**
 * I2_17_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:37:55
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;

import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;

/**
 * The eight drives - the four partial sexual drives divided into libidinous and aggressive components - as well as the total amount of libido tension are transmitted from F43 to F42.
 * 
 * @author deutsch
 * 03.03.2011, 15:37:55
 * 
 */
public interface I3_3_send {
	public void send_I3_3(ArrayList< clsPair< clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates);
}

