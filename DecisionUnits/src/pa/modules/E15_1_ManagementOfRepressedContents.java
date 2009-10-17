/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsDriveContentCathegories;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class E15_1_ManagementOfRepressedContents extends clsModuleBase implements I2_5 {

	ArrayList<clsPrimaryInformation> moEnvironmentalTP_Input;
	ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moAttachedRepressed_Output;
	
	
	public E15_1_ManagementOfRepressedContents(String poPrefix, clsBWProperties poProp,
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
	 * 07.10.2009, 11:20:02
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		moEnvironmentalTP_Input = deepCopy( poEnvironmentalTP );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		
		clsPair<clsDriveContentCathegories, clsPrimaryInformation> oCathInput;
		oCathInput = cathegorize( moEnvironmentalTP_Input );
		
		moAttachedRepressed_Output = matchWithRepressedContent(oCathInput);
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 17.10.2009, 18:54:27
	 *
	 * @param cathInput
	 * @return
	 */
	private ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> matchWithRepressedContent(
			clsPair<clsDriveContentCathegories, clsPrimaryInformation> cathInput) {
		// TODO (langr) - Auto-generated method stub
		return null;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 17.10.2009, 18:52:32
	 *
	 * @param moEnvironmentalTP_Input2
	 * @return
	 */
	private clsPair<clsDriveContentCathegories, clsPrimaryInformation> cathegorize(
			ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		// TODO (langr) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_6)moEnclosingContainer).receive_I2_6(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

}
