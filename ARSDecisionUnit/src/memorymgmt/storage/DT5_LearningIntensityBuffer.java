/**
 * CHANGELOG
 *
 * 12.07.2018 fittner 	- first implementation
 *
 */
package memorymgmt.storage;

import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInterfaceDescription;

import java.util.ArrayList;
import java.util.Arrays;

import modules.interfaces.D5_1_receive;
import modules.interfaces.D5_1_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsDriveMesh;
import base.tools.toText;

/**
 * Storage module for learning intensity 
 * set the actual learning intensity to DT5
 * 
 * @author fittner
 * @since 12.07.2018
 */
public class DT5_LearningIntensityBuffer 
implements itfInspectorInternalState, itfInterfaceDescription, D5_1_receive, D5_1_send{

	private double mnLearningIntensity = 0.0;

	public DT5_LearningIntensityBuffer() {
	}
	/**
	 * @since 18.07.2012 15:32:09
	 * 
	 * @param moAllDrivesLastStep the moAllDrivesLastStep to set
	 */
	public void setLearningIntensity(ArrayList<clsDriveMesh> moAllDrivesActualStep) {
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
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D5_1) );
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
		
		text += toText.h1("Learning Intensity");
		text += toText.valueToTEXT("mnLearningIntensity", mnLearningIntensity);
		
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D5_1_receive#receive_D5_1(double)
	 */
	@Override
	public void receive_D5_1(double LearningIntensity) {
	    mnLearningIntensity += LearningIntensity;
	}
   
   public void resetLearningIntensity(){
       mnLearningIntensity = 0;
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
	public double send_D5_1() {
		return mnLearningIntensity;
	}
}