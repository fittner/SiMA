/**
 * E16_ManagmentOfMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I2_6;
import pa.interfaces.I2_7;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 * 
 */
public class E16_ManagementOfMemoryTraces extends clsModuleBase implements I2_6 {

	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPerceptPlusRepressed_Input;
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPerceptPlusMemories_Output;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:31:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E16_ManagementOfMemoryTraces(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:31:53
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		moPerceptPlusRepressed_Input = (ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>)deepCopy(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_7)moEnclosingContainer).receive_I2_7(mnTest);
	}
}
