/**
 * clsBlockedContentStorage.java: DecisionUnits - pa.storage
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 */
package pa.storage;

import java.util.ArrayList;

import pa.interfaces.receive._v30.D2_1_receive;
import pa.interfaces.receive._v30.D2_3_receive;
import pa.interfaces.send._v30.D2_2_send;
import pa.interfaces.send._v30.D2_4_send;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 * 
 */
public class clsBlockedContentStorage implements D2_2_send, D2_4_send, D2_1_receive, D2_3_receive {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v30.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_3(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v30.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v30.D2_4_send#send_D2_4(java.util.ArrayList)
	 */
	@Override
	public void send_D2_4(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v30.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public void send_D2_2(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

}
