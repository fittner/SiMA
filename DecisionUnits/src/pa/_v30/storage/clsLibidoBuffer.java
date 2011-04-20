/**
 * clsLibidoBuffer.java: DecisionUnits - pa.buffer
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 */
package pa._v30.storage;

import pa._v30.interfaces.receive.D1_1_receive;
import pa._v30.interfaces.receive.D1_3_receive;
import pa._v30.interfaces.send.D1_2_send;
import pa._v30.interfaces.send.D1_4_send;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 * 
 */
public class clsLibidoBuffer implements D1_2_send, D1_4_send, D1_1_receive, D1_3_receive  {
	private double mrBufferedLibido;
	
	public clsLibidoBuffer() {
		mrBufferedLibido = 0;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:16:00
	 * 
	 * @see pa.interfaces.receive._v30.D1_3_receive#receive_D1_3(double)
	 */
	@Override
	public void receive_D1_3(double prValue) {
		mrBufferedLibido = prValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:16:00
	 * 
	 * @see pa.interfaces.receive._v30.D1_1_receive#receive_D1_1(double)
	 */
	@Override
	public void receive_D1_1(double prValue) {
		mrBufferedLibido += prValue;		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v30.D1_4_send#send_D1_4()
	 */
	@Override
	public double send_D1_4() {
		return mrBufferedLibido;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v30.D1_2_send#send_D1_2()
	 */
	@Override
	public double send_D1_2() {
		return mrBufferedLibido;
	}

	@Override
	public String toString() {
		return "libido: "+mrBufferedLibido;
	}
}
