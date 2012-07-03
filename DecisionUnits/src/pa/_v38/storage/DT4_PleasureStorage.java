/**
 * CHANGELOG
 *
 * 12.10.2011 zottl					- first proper implementation according to current specification
 * 14.07.2011 deutsch 			- refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D4_1_receive;
import pa._v38.interfaces.modules.D4_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.toText;

/**
 * Storage module for pleasure coming from the drive system
 * 
 * @author muchitsch
 * @since 12.10.2011
 */
public class DT4_PleasureStorage 
implements itfInspectorInternalState, itfInterfaceDescription, D4_1_receive, D4_1_send{

	private double mnSystemPleasureValue;

	public DT4_PleasureStorage() {
		
	}


	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return  "Storage module for pleasure coming from the drive system.";
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D4_1) );
	}

	

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.h1("System Pleasure");
		text += toText.valueToTEXT("mnSystemPleasureValue", mnSystemPleasureValue);
		
		return text;
	}



	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_receive#receive_D3_1(double)
	 */
	@Override
	public void receive_D4_1(double pnActualPleasureValue) {
		mnSystemPleasureValue = pnActualPleasureValue;
	}


	/* (non-Javadoc)
	 *
	 * @since 13.06.2012 14:01:53
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		// TODO (muchitsch) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:33:59
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
	 */
	@Override
	public double send_D4_1() {
		return mnSystemPleasureValue;
		
	}
	

}