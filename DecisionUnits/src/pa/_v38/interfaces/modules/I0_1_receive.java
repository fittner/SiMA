/**
 * I0_1_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:26:48
 */
package pa._v38.interfaces.modules;



/**
 * The inner somatic stimulation source which produces a constant flow of libido is represented by this interface. The circular loop in the figure defines that this source has a not identifiable bodily source and that the amount of libido produced cannot be influenced and stays constant. I0.1 connects the physical and chemical body with F39.
 * 
 * @author deutsch
 * 03.03.2011, 15:26:48
 * 
 */
public interface I0_1_receive {
	public void receive_I0_1(Double prLibido);
}
