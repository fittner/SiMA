/**
 * E11_NeuroSymbolsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 */
package pa.modules._v19;

import java.util.HashMap;

import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa._v19.clsInterfaceHandler;
import pa.enums.eSymbolExtType;
import pa.interfaces.receive._v19.I2_1_receive;
import pa.interfaces.receive._v19.I2_2_receive;
import pa.interfaces.send._v19.I2_2_send;
import pa.symbolization.clsSensorToSymbolConverter;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 * 
 */
public class E11_NeuroSymbolsEnvironment extends clsModuleBase implements I2_1_receive, I2_2_send {

	HashMap<eSensorExtType, clsSensorExtern> moEnvironmentalData;
	HashMap<eSymbolExtType, itfSymbol> moSymbolData;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:19:45
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E11_NeuroSymbolsEnvironment(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
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
		mnProcessType = eProcessType.BODY;
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
		mnPsychicInstances = ePsychicInstances.BODY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:20:05
	 * 
	 * @see pa.interfaces.I2_1#receive_I2_1(HashMap<eSensorExtType, clsDataBase>)
	 */
	@Override
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moEnvironmentalData = poData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moSymbolData = clsSensorToSymbolConverter.convertExtSensorToSymbol(moEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_2(moSymbolData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:53:57
	 * 
	 * @see pa.interfaces.send.I2_2_send#send_I2_2(java.util.HashMap)
	 */
	@Override
	public void send_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		((I2_2_receive)moEnclosingContainer).receive_I2_2(moSymbolData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:14
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:14
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
